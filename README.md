### back-worker

<br/>

- MSA 서비스 구현을 위한 작업자 관리 백엔드 서버입니다.

<br/>

- 구축 환경
  - `Eclipse IDE` - eGovFrameDev-4.1.0-Win-64bit
  - `JDK 8`
  - `Spring 2.7.16`
  - `PostgreSQL`
  - `Mybatis`

<br/>

- 개발 기획
  - API 명세서
    - 다음 표와 같습니다.

      | 기능 | Method | PATH |
      |------|------|--------|
      | 로그인 | `POST` | /worker/login/ |
      | 로그아웃 | `POST` | /worker/logout/ |
      | 작업자 가입 | `POST` | /worker/ |
      | 작업자 탈퇴 | `DELETE` | /worker/ |
      | 작업자 정보조회 | `GET` | /worker/:workerId |
      | 작업자 세부정보조회 | `GET` | /worker/:workerId/detailed |
      | 작업자 검색 | `GET` | /worker?col={id/name}&sort={asc/desc} |
      | 작업자 개인정보 재설정 | `PUT` | /worker/:workerId |
      | 작업자 아이디 찾기 | `POST` | /worker/findid/ |
      | 작업자 비밀번호 찾기 | `POST` | /worker/:workerId/findpw/ |
  
    - 더 자세한 내용은  [다음 링크](https://www.notion.so/6929db3c04ea46fbb977262765995b95?v=414db9f4262042429ebccb4ed7926f61&pvs=4)에 있습니다.

  - 데이터베이스 ERD
    - 다음 표와 같습니다.
 
      | 구분 | 컬럼 | Domain | NULL / NOT NULL | ETC |
      |------|------|--------|-----------------|-----|
      | 작업자 주키 ID | worker_pk | INT | NOT NULL | AutoIncrement |
      | 아이디 | id | VARCHAR(50) | NOT NULL |  |
      | 비밀번호 | pw | VARCHAR(50) | NOT NULL | 비밀번호 정책 정규성 검증 진행 |
      | 이름 | name | VARCHAR(30) | NOT NULL |  |
      | 전화번호 | phone_number | VARCHAR(15) | NOT NULL | 정규식 검증 진행 |
      | 이메일 | email | VARHCHAR(30) | NOT NULL | 정규식 검증 진행 |
      | 계정 상태 | acc_status | BOOLEAN | NOT NULL | 기본값 TRUE, TRUE:활성화 / FALSE:비활성화 |

  - 클래스 다이어그램
    - `WorkerApplication.java` : Java Spring Main Program
    - `api/WorkerApiService.java` : `CRUD` 를 바탕으로 `serviceImpl` 코드가 작동하도록 함수 제공
    - `controller/WorkerController.java` : API Interface (위 API 명세서와 같음)
    - `serviceImpl/WorkerApiServiceImpl.java` : API Interface Implementation 진행
  
<br/>

- 프론트와의 협업을 위한 도커 이미지 제공
  1. `cd back-worker`
  2. `.\gradlew.bat build` (윈도우) / `./gradlew build` (맥북 M1 / 리눅스)
  3. `docker build . -t back-worker` / `docker build . -t back-worker --platform linux/arm64` (맥북 M1 의 경우)
  4. `docker run -p 8080:8080 -v ${pwd}:/workspace back-worker`
  5. `http://localhost:8080/` 링크를 바탕으로 테스트용 백엔드 서버를 활용하면 됨.

<br/>

- Git 형상관리 전략
  - 현재 개발 단계에서 `develop` 브랜치에 대한 필요성이 비교적 없어 보임
  - `기능 및 API 단위`를 바탕으로 `이슈 등록`하며 `브랜치를 확장`하여 개발하는 방식으로 진행
  - `develop` 브랜치를 제외하고 `main` 브랜치에서 기능별 브랜치를 확장하여 병합하는 방식으로 진행

<br/>
