### back-worker

<br/>

- MSA 서비스 구현을 위한 작업자 백엔드 서버입니다.

<br/>

- 구축 환경
  - `Eclipse IDE` - eGovFrameDev-4.1.0-Win-64bit
  - `JDK 8`
  - `Spring 2.7.16`

<br/>

- 개발 기획
  - API 명세서는 [다음 링크](https://www.notion.so/6929db3c04ea46fbb977262765995b95?v=414db9f4262042429ebccb4ed7926f61&pvs=4)에 있습니다.

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

<br/>

- 프론트와의 협업을 위한 도커 이미지 제공
  - 추후에 세팅 진행하겠습니다.

<br/>
