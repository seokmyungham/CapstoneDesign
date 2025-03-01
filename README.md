<div align="center">

  # CapstoneDesign
  
  안녕하세요. 축구를 좋아하는 세명이 모여서 교내 축구 소셜 네트워크 서비스(SNS) 웹 페이지를 제작하였습니다.  
</div>

---

## Box-to-Box
> 명지대학교 2023년 1학기 캡스톤 디자인  
> 개발기간 : 2023.04.13 ~ 2023.06.12

## 개발 팀원 소개
> Backend : 명지대 정보통신공학과 함석명  
> Frontend : 명지대 정보통신공학과 정경완, 명지대 정보통신공학과 최승근 

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

---

## 화면 페이지

|메인페이지|게시글 페이지|
|---|---|
|<img src="https://github.com/seokmyungham/CapstoneDesign/assets/97608735/615eda2d-fd4d-447f-88a4-80f6d50775d3" width="576" height="307"/>|<img src="https://github.com/seokmyungham/CapstoneDesign/assets/97608735/d6567a3f-e0b7-424c-a005-02c02835d071" width="576" height="307"/>|
|<div align="center"/>회원 검색|<div align="center"/>마이페이지|
|<img src="https://github.com/seokmyungham/CapstoneDesign/assets/97608735/f65df7de-2f55-4a1d-9daf-6839e4b619b1" width="576" height="307"/>|<img src="https://github.com/seokmyungham/CapstoneDesign/assets/97608735/952c1872-78c8-4a45-80e1-b1145ac3434f" width="576" height="307"/>|
|<div align="center"/>채팅방 리스트|<div align="center"/>1대1 채팅|
|<img src="https://github.com/seokmyungham/CapstoneDesign/assets/97608735/d0c4cde6-1c49-4a7b-8a3f-63307eb89f4d" width="500"/>|<img src="https://github.com/seokmyungham/CapstoneDesign/assets/97608735/1bbf4839-bc3d-42f6-bcab-e2bf7c2fb79b" width="576" height="307"/>|
|<div align="center"/>스카이스포츠 뉴스 크롤링|
|<img src="https://github.com/seokmyungham/CapstoneDesign/assets/97608735/a3302a7e-f61c-4162-a103-8c686e7c345e" width="576" height="307"/>|

## API 문서

Notion link: https://harvest-skirt-b55.notion.site/API-4167e5f7adeb41e5ba30179479248593?pvs=4

---

## 주요 서비스

### 1. 회원 가입 및 로그인

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

