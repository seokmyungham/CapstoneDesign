import com.project.capstone.domain.dto.member.MemberRequestDto;
import com.project.capstone.domain.dto.auth.TokenDto;
import com.project.capstone.config.jwt.TokenProvider;
import com.project.capstone.domain.dto.member.MemberResponseDto;
import com.project.capstone.domain.entity.Member;
import com.project.capstone.domain.entity.Role;
import com.project.capstone.repository.MemberRepository;
import com.project.capstone.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private AuthenticationManagerBuilder managerBuilder;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenProvider tokenProvider;

    @MockBean
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void 회원가입_성공() {
        //given
        MemberRequestDto requestDto = MemberRequestDto.builder()
                .email("test@example.com")
                .password("password")
                .nickname("test")
                .image("test.jpg")
                .introduction("hello")
                .build();

        Member savedMember = Member.builder()
                .id(1L)
                .email(requestDto.getEmail())
                .password("encodedPassword")
                .nickname(requestDto.getNickname())
                .image(requestDto.getImage())
                .introduction(requestDto.getIntroduction())
                .role(Role.USER)
                .build();

        when(memberRepository.existsByEmail(requestDto.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(requestDto.getPassword())).thenReturn("encodedPassword");
        when(memberRepository.save(any(Member.class))).thenReturn(savedMember);

        //when
        MemberResponseDto responseDto = authService.signup(requestDto);

        // hen
        assertEquals(savedMember.getEmail(), responseDto.getEmail());
        assertEquals(savedMember.getNickname(), responseDto.getNickname());
        assertEquals(savedMember.getImage(), responseDto.getImage());
        assertEquals(savedMember.getIntroduction(), responseDto.getIntroduction());
    }

    @Test
    public void 회원가입_실패_이미_가입되어_있는_유저() {
        //given
        MemberRequestDto requestDto = MemberRequestDto.builder()
                .email("test@example.com")
                .password("password")
                .nickname("test")
                .image("test.jpg")
                .introduction("hello")
                .build();

        when(memberRepository.existsByEmail(requestDto.getEmail())).thenReturn(true);

        //when & then
        assertThrows(RuntimeException.class, () -> authService.signup(requestDto));
        verify(memberRepository, times(1)).existsByEmail(requestDto.getEmail());
        verify(memberRepository, never()).save(any(Member.class));
    }

    @Test
    public void 닉네임_중복_검사_테스트_중복() {
        //given
        String nickname = "testNickName~";
        when(memberRepository.existsByNickname(nickname)).thenReturn(true);

        //when
        boolean result = authService.checkNicknameDuplicate(nickname);

        //then
        assertTrue(result);
        verify(memberRepository, times(1)).existsByNickname(nickname);
    }

    @Test
    public void 닉네임_중복_검사_테스트_중복X() {
        //given
        String nickname = "testNickName~";
        when(memberRepository.existsByNickname(nickname)).thenReturn(false);

        //when
        boolean result = authService.checkNicknameDuplicate(nickname);

        //then
        assertFalse(result);
        verify(memberRepository, times(1)).existsByNickname(nickname);
    }

    @Test
    public void 이메일_중복_검사_테스트_중복() {
        //given
        String email = "test@example.com";
        when(memberRepository.existsByEmail(email)).thenReturn(true);

        //when
        boolean result = authService.checkEmailDuplicate(email);

        //then
        assertTrue(result);
        verify(memberRepository, times(1)).existsByEmail(email);
    }

    @Test
    public void 이메일_중복_검사_테스트_중복X() {
        //given
        String email = "test@example.com";
        when(memberRepository.existsByEmail(email)).thenReturn(false);

        //when
        boolean result = authService.checkEmailDuplicate(email);

        //then
        assertFalse(result);
        verify(memberRepository, times(1)).existsByEmail(email);
    }

    /**
     * NPE 발생: "this.building" is null, "java.util.concurrent.atomic.AtomicBoolean.get()" invoke fail
     * stub 과정 중 when 절의 managerBuilder.getObject() 호출 과정에서 발생
     * AuthenticationManagerBuilder -> AbstractConfiguredSecurityBuilder -> AbstractSecurityBuilder
     * AtomicBoolean 이 생성 되어있는데, 여기서 building 객체가 null 이어서 발생하는 문제로 추정
     * 더 깊은 스프링 시큐리티 학습 필요
     */
    @Test
    public void 로그인_테스트_성공() {
        //given
        MemberRequestDto requestDto = mock(MemberRequestDto.class);
        UsernamePasswordAuthenticationToken authenticationToken = mock(UsernamePasswordAuthenticationToken.class);
        Authentication authentication = mock(Authentication.class);
        TokenDto tokenDto = mock(TokenDto.class);

        when(requestDto.toAuthentication()).thenReturn(authenticationToken);
//        when(managerBuilder.getObject().authenticate(any())).thenReturn(authentication);

        when(managerBuilder.getObject()).thenReturn(authenticationManager);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        when(tokenProvider.generateTokenDto(authentication)).thenReturn(tokenDto);

        //when
        TokenDto result = authService.login(requestDto);

        //then
        assertNotNull(result);
        assertEquals(tokenDto, result);
        verify(requestDto, times(1)).toAuthentication();
        verify(managerBuilder.getObject(), times(1)).authenticate(authenticationToken);
        verify(tokenProvider, times(1)).generateTokenDto(authentication);
    }
}
