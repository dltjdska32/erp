
# ERP 시스템

사원 관리 및 근태 관리 기능을 제공하는 기업 자원 관리 시스템입니다.

---
<br>

## 🎥 시연 영상

https://www.youtube.com/watch?v=6ssCFEIZnbM

**소리가 있으니 소리를 꺼주세요**


## 👨‍🏫 프로젝트 소개
SpringBoot, HTML, CSS, JS 를 활용한기업 그룹웨어 프로젝트

## ⏲️ 개발 기간 
- 24.10.03 ~ 24.12.03
  
## 🧑‍🤝‍🧑 개발자  
- **이성남** : 프로젝트 총괄 및 DB 구축
- **김동진** : 프론트엔드

## 💻 개발환경
- **Version** : Java 17
- **IDE** : IntelliJ
- **DataBase** : MySQL

## 🛠️ 기술 스택

- **Backend**: Spring Boot 3.4.2, Java 17
- **Database**: MySQL
- **ORM**: JPA/Hibernate
- **Template Engine**: Thymeleaf (SSR)
- **Build Tool**: Gradle

---
<br>


## ✨ 주요 기능

- **사원 관리**: 사원 정보 조회, 수정, 검색
- **근태 관리**: 출퇴근 기록, 근태 현황 조회
- **휴가 관리**: 휴가 신청 및 승인/거절 처리
- **급여 관리**: 급여 조회, 보너스 지급
- **게시판**: 사내 게시판 및 답변 기능
- **메일 시스템**: 사내 메일 발송 및 수신
- **편의 기능**: ChatGPT API를 활용한 문서 요약 및 번역

---



## 🏗️ 적용된 설계 패턴 및 기술

### 1. 퍼사드 패턴 (Facade Pattern)
복잡한 서비스 로직을 단순한 인터페이스로 제공
- `BoardFacadeService`, `MailFacadeService`, `UserFacadeService`, `SalaryLogFacadeService`, `LeaveLogFacadeService`

### 2. QueryDSL
타입 안전한 동적 쿼리 작성 및 복잡한 조회 성능 최적화
- Repository Custom 패턴으로 복잡한 조회 로직 분리

### 3. 레코드 (Record)
불변 DTO 객체로 데이터 전송
- 24개 이상의 Record 클래스 사용 (`AddBonusInfo`, `AdminBonusInfoDto`, `EmployeeInfoDto` 등)

### 4. 빌더 패턴 (Builder Pattern)
Lombok `@Builder`를 활용한 객체 생성 패턴
- 엔티티의 안전한 객체 생성 및 가독성 향상

### 5. 데이터베이스 인덱스
조회 성능 최적화를 위한 인덱스 적용
- `Position`: `position_name` 인덱스
- `Dept`: `dept_name` 인덱스

### 6. Argument Resolver
커스텀 어노테이션을 통한 세션 정보 주입
- `@Login` 어노테이션으로 로그인 사용자 정보 자동 주입

### 7. 인터셉터 (Interceptor)
요청 전/후 공통 로직 처리
- `LogInterceptor`: 요청 로깅
- `LoginCheckInterceptor`: 로그인 상태 확인
- `RoleCheckInterceptor`: 권한 체크


### 8. 스케줄링
`@Scheduled`를 활용한 정기 작업 실행
- 매월 10일 9시: 급여 지급
- 평일 6시: 근태 기록 초기화

### 10. SSR (Server-Side Rendering)
Thymeleaf를 활용한 서버 사이드 렌더링
- 서버에서 HTML 생성 후 클라이언트에 전달

---

## 📁 프로젝트 구조

```
src/main/java/com/example/erp/
├── domain/                    # 도메인별 패키지
│   ├── board/                # 게시판
│   ├── user/                 # 사용자
│   ├── worklog/              # 근태 로그
│   ├── leavelog/             # 휴가 로그
│   ├── salarylog/            # 급여 로그
│   ├── mail/                 # 메일
│   └── ...
└── global/                    # 공통 설정
    ├── config/               # 설정 클래스
    ├── exception/            # 예외 처리
    ├── responseDto/          # 공통 응답 DTO
    └── utils/                # 유틸리티
```

---

## 📦 주요 도메인 구조

각 도메인은 다음 구조를 따릅니다:
- `entity/`: 엔티티 클래스
- `dto/`: 데이터 전송 객체 (Record 활용)
- `repository/`: Repository 인터페이스 및 Custom 구현
- `service/`: 비즈니스 로직 (Facade Service 포함)
- `controller/`: 컨트롤러
- `exception/`: 도메인별 예외 처리

