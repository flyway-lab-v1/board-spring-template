# 팀 게시판 (Spring Boot + MySQL + Flyway)

팀 프로젝트용 Spring Boot 템플릿 저장소입니다. Node 프로젝트에서 쓰던 마이그레이션 SQL을 `src/main/resources/db/migration`에 그대로 옮겨 시작합니다.

## 처음 한 번

1. `flyway.toml.example`을 복사해 `flyway.toml`을 만들고 `CHANGE_ME`를 내 MySQL 비밀번호로 바꿉니다.
2. `src/main/resources/application-local.yml.example`을 복사해 `application-local.yml`을 만들고 같은 비밀번호를 적습니다.
3. 두 파일은 `.gitignore`에 들어 있어서 Git에 올라가지 않습니다.

## 매일 쓰는 명령

| 명령 | 하는 일 |
|------|--------|
| `flyway migrate` | 아직 적용하지 않은 마이그레이션을 내 DB에 적용 |
| `flyway info` | 적용된 마이그레이션과 남은 마이그레이션 목록 |
| `flyway validate` | 내 DB 기록과 파일이 일치하는지 검사 |
| `.\gradlew.bat bootRun` | 서버 실행 (http://localhost:8080/api/posts) |
| `.\gradlew.bat test` | 테스트 (로컬 DB에 migrate한 뒤 실행) |

## 꼭 지킬 설정

- `spring.flyway.enabled: false` — 마이그레이션은 Flyway CLI로 직접 실행합니다. 앱 시작 시 자동 실행은 가이드 부록 A를 보세요.
- `spring.jpa.hibernate.ddl-auto: validate` — 테이블은 Flyway만 만듭니다. `update`로 바꾸면 Hibernate가 테이블을 만들어 버려서 `flyway migrate`가 `Found non-empty schema(s) ... but no schema history table` 오류로 멈춥니다.

## 마이그레이션 파일 규칙

- 이름: `V` + 연월일시분초 14자리 + `__` + 영어 소문자·밑줄 설명 + `.sql`
- 한 번 적용(병합)한 파일은 고치지 않습니다. 바꿀 내용은 새 파일로 만듭니다.
- 테이블·컬럼 이름은 소문자와 밑줄만 씁니다.

자세한 절차는 팀 협업 가이드(노션)를 보세요.
