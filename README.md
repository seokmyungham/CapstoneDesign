<div align="center">

  # CapstoneDesign
  
  안녕하세요. 본 프로젝트는 명지대 4학년 1학기 캡스톤 디자인1 강의중 진행된 개발 프로젝트입니다.  
  축구를 좋아하는 세명이 모여서 축구 소셜 네트워크 서비스(SNS) 웹 페이지를 제작하였습니다.  
</div>

---

## Box-to-Box
> 명지대학교 2023년 1학기 캡스톤 디자인  
> 개발기간 : 2023.04.13 ~ 2023.06.12

## 개발 팀원 소개
> Backend : 명지대 정보통신공학과 함석명  
> Frontend : 명지대 정보통신공학과 정경완, 명지대 정보통신공학과 최승근 
  
## 프로젝트 개요
축구 커뮤니티로 불리는 기존 사이트들이 존재하지만, 익명성 뒤에 숨어 무분별한 악플 및 팀과 선수를 비방하는 모습을 보면서  
자유로운 의사소통과 정보 공유, 인맥 확대 등을 통해 사회적 관계를 강화하는 실명제 SNS의 순기능과 축구를 접목시켜 보았습니다.

## Stacks

### Environment
<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> 
<img src="https://img.shields.io/badge/Visual Studio Code-007ACC?style=for-the-badge&logo=Visual Studio Code&logoColor=white">

### Development

<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=white">
<img src="https://img.shields.io/badge/react-61DAFB?style=for-the-badge&logo=react&logoColor=white"> <img src="https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=MariaDB&logoColor=white">

### Deploy & Infrastructure

<img src="https://img.shields.io/badge/Github Actions-2088FF?style=for-the-badge&logo=Github Actions&logoColor=white"> <img src="https://img.shields.io/badge/amazon s3-569A31?style=for-the-badge&logo=amazon s3&logoColor=white">
<img src="https://img.shields.io/badge/amazon ec2-FF9900?style=for-the-badge&logo=amazon ec2&logoColor=white"> <img src="https://img.shields.io/badge/nginx-009639?style=for-the-badge&logo=nginx&logoColor=white">

### Communication

<img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white">

### Etc

<img src="https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">

---

## 화면 페이지

|메인페이지|게시글 페이지|
|---|---|
|<img src="https://github.com/seokmyungham/CapstoneDesign/assets/97608735/615eda2d-fd4d-447f-88a4-80f6d50775d3" width="500" height="300"/>|<img src="https://github.com/seokmyungham/CapstoneDesign/assets/97608735/d6567a3f-e0b7-424c-a005-02c02835d071" width="500" height="300"/>|
|<div align="center"/>회원 검색|<div align="center"/>마이페이지|
|<img src="https://github.com/seokmyungham/CapstoneDesign/assets/97608735/f65df7de-2f55-4a1d-9daf-6839e4b619b1" width="500" height="300"/>|<img src="https://github.com/seokmyungham/CapstoneDesign/assets/97608735/952c1872-78c8-4a45-80e1-b1145ac3434f" width="500" height="300"/>|
|<div align="center"/>채팅방 리스트|<div align="center"/>1대1 채팅|
|<img src="https://github.com/seokmyungham/CapstoneDesign/assets/97608735/d0c4cde6-1c49-4a7b-8a3f-63307eb89f4d" width="500"/>|<img src="https://github.com/seokmyungham/CapstoneDesign/assets/97608735/1bbf4839-bc3d-42f6-bcab-e2bf7c2fb79b" width="500" height="300"/>|
|<div align="center"/>스카이스포츠 뉴스 크롤링|
|<img src="https://github.com/seokmyungham/CapstoneDesign/assets/97608735/a3302a7e-f61c-4162-a103-8c686e7c345e" width="500" height="300"/>|

## API 문서

Notion link: https://harvest-skirt-b55.notion.site/API-4167e5f7adeb41e5ba30179479248593?pvs=4

---

## 주요 서비스

### 1. 회원 가입 및 로그인
- Spring Security 및 jwt 토큰을 이용한 인증, 인가 방식으로 개발하였습니다.

### 2. 게시글, 댓글, 좋아요
- 자유롭게 게시글과 댓글을 작성 및 삭제하는 것이 가능하고 게시글에 좋아요를 누르는 것이 가능합니다.

### 3. 회원 검색 및 마이페이지
- 회원의 닉네임으로 회원을 검색하는 것이 가능하고, 회원의 프로필 페이지로 이동하는 것이 가능합니다.
- 자신의 마이페이지에서는 프로필 이미지, 자기소개, 닉네임을 설정하여 자신의 개성을 나타낼 수 있습니다

### 4. 다이렉트 메시지
- WebSocket과 Stomp를 활용하여 간단한 실시간 채팅을 구현하였습니다.
- 다른 회원과 실시간 1대1 메시지를 주고받는 것이 가능합니다.
- 채팅 방은 자유롭게 생성하고 나가는 것이 가능하며, DM 페이지에서 따로 관리할 수 있습니다.

### 5. 뉴스
- jsoup 라이브러리를 활용해서 뉴스를 크롤링, 데이터를 정제하여 시각화 하였습니다.
- 회원들은 자신이 원하는 클럽의 뉴스를 직접 찾아다니지 않고 빠르게 접할 수 있습니다.
- 기사를 클릭하면 해당 언론사의 뉴스 링크로 바로 이동하게 됩니다.

---

## 진행하면서

### 1. DDL 원칙 준수
- 김영한 개발자 선배님의 JPA 강의에서 학습했던 도메인 주도 설계 방법을 프로젝트를 진행하며 적용시켜보려 노력하였습니다.
- 아직 많이 미흡하지만 도메인을 중심으로 코드를 작성하며 서비스 코드를 순수한 자바코드로 분리하는 경험은 개발자로써 한 단계 성장하는 느낌을 받을 수 있었습니다.

### 2. 문서화의 중요성
- 팀 단위의 프로젝트를 진행하면서 항상 강조되었던 데이터를 문서화하고 커뮤니케이션 하는 것의 중요성을 알 수 있었습니다.
- 프로젝트 초반에는 아무런 틀도 없이 진행했던 프로젝트였지만, 이후 데이터베이스 구조와 API 명세를 작성하며 팀원들과 소통하는 과정에서 팀 단위 협업의 의미를 알 수 있었습니다.

### 3. 발생했던 문제 해결

- REST API를 설계하며 발생한 CORS 에러
> 프로젝트 진행 당시 초기에 해결했던 CORS 에러가 진행 도중에 또 다시 발생한 적이 있었습니다.  
> 이 에러가 발생한 이유는 Nginx를 리버스 프록시 서버로 사용하였기 때문이었는데  
> 프론트로부터 들어오는 호출은 백 이전에 Nginx로 모두 들어오기 때문에  
> 스프링뿐만 아니라 Nginx의 설정 파일에서도 적절히 header의 접근을 허용해주어야 했습니다.

<img src="https://github.com/seokmyungham/CapstoneDesign/assets/97608735/14fe77d8-21ac-4079-94e8-8d44b4e7b3bc" width="500" height="300"/>

> 처음에는 Ngnix의 내부 구조를 정확하게 이해하지 못하고 반나절동안 해맸던 경험이었는데  
> 책을 뒤져보고 구글에 검색해보아도 원하는 내용을 찾을 수 없어서  
> "나는 고작 이런 것도 해결 못하나?"라는 생각으로 중간에 밥도 안먹고 끝내 해결해냈었던 경험이었습니다.

- CI/CD 구축 당시 겪었던 문제
> 서버를 구축 하면서 이동욱 개발자 선배님이 쓰신 "스프링 부트와 AWS로 혼자 구현하는 웹 서비스" 책의 도움을 많이 받았는데  
> Travis가 유료화가 됨에 따라, 책에 나와있는 TravsCI를 이용하여 파이프라인을 구축하는 방식대신  
> Github Actions를 활용하여 프로젝트를 진행하였습니다.
> 
> 이 과정에서 AWS CodeDeploy에 알수 없는 배포 실패, ScriptTimeOut을 겪게 되었었는데  
> 로그를 확인해보니 health.sh이 8081포트를 찾지 못하는 문제였습니다.  
> 문제가 발생한 이유는 ec2 username과 프로젝트 이름이 일치하지 않아 발생하는 문제였는데  
> 이 문제도 단순 검색으로는 해결할 수 없었던 문제여서 까다로웠던 경험이었습니다.
> 
> 돌이켜보면 정말 단순한 문제였지만, 의도치않게 이 간단한 문제를 해결하는 과정에서  
> Deployments-log나 AWS 홈페이지의 로그를 엄청 많이 확인하였는데  
> 로그를 남기고 남아있는 것을 확인하는 일이, 개발자에게 얼마나 도움이 되는 것인지 깨달을 수 있었습니다.

- HardCode 개선 및 쿼리 최적화
> 미숙한 실력으로 시간에 쫓기며 코드를 작성하다보니  
> 프로젝트 진행과정에서는 확인하지 못한 BadCode가 너무 많았습니다.
>
> 프로젝트가 끝난 후 제 나름대로 코드 개선을 해봤는데, 20개씩 페이징이 되어있는 게시글 페이지는  
> 각 게시글의 title(사진), 좋아요(recommend), 회원정보(member)가 보이도록 구성되어집니다.  
> 그런데 보이는 게시글 수 만큼 select post, select recommend, select member 각각 쿼리가 실행되는  
> 너무 안좋은 문제를 발견하였고 service 코드와 dto 전반을 수정 및  
> 엔티티 연관관계를 lazy로 설정한 후 원하는 인덱스만큼 fetch 조인으로 한방에 조회할 수 있도록 코드를 수정해서  
> 단 한번의 쿼리문으로 원하는 결과를 얻을 수 있었던 경험이었습니다.
