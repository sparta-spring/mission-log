#  🪖미션로그 - 스파르타의 봄
### 효율적인 업무수행, 빠른 목표달성, 미션로그!
<img width="300px" alt="미션로그 아이콘" src="https://github.com/sparta-spring/mission-log/assets/146910663/c0c756ef-eb46-4a61-b863-37b99c6c1516">

###  🪖팀과제 기획관련
- 필수 구현 기능
    - **사용자 관리 기능**
        - [ ]  로그인 / 회원가입 기능
        - [ ]  사용자 정보 수정 및 삭제 기능
    - **보드 관리 기능**
        - [ ]  보드 생성
        - [ ]  보드 수정
            - 보드 이름
            - 배경 색상
            - 설명
        - [ ]  보드 삭제
            - 생성한 사용자만 삭제를 할 수 있습니다.
        - [ ]  보드 초대
            - 특정 사용자들을 해당 보드에 초대시켜 협업을 할 수 있어야 합니다.
    - **컬럼 관리 기능**
        - [ ]  컬럼 생성
            - 보드 내부에 컬럼을 생성할 수 있어야 합니다.
            - 컬럼이란 위 사진에서 Backlog, In Progress와 같은 것을 의미해요.
        - [ ]  컬럼 이름 수정
        - [ ]  컬럼 삭제
        - [ ]  컬럼 순서 이동
            - 컬럼 순서는 자유롭게 변경될 수 있어야 합니다.
                - e.g. Backlog, In Progress, Done → Backlog, Done, In Progress
    - **카드 관리 기능**
        - [ ]  카드 생성
            - 컬럼 내부에 카드를 생성할 수 있어야 합니다.
        - [ ]  카드 수정
            - 카드 이름
            - 카드 설명
            - 카드 색상
            - 작업자 할당
            - 작업자 변경
        - [ ]  카드 삭제
        - [ ]  카드 이동
            - 같은 컬럼 내에서 카드의 위치를 변경할 수 있어야 합니다.
            - 카드를 다른 컬럼으로 이동할 수 있어야 합니다.
    - **카드 상세 기능**
        - [ ]  댓글 달기
            - 협업하는 사람들끼리 카드에 대한 토론이 이루어질 수 있어야 합니다.
        - [ ]  날짜 지정
            - 카드에 마감일을 설정하고 관리할 수 있어야 합니다.

##  🪖Demo Video

##  🪖 Links
- [Github](https://github.com/sparta-spring/mission-log)
- [Notion](https://www.notion.so/04345ca0ede5460692c051f380ad00d7)
- [Figma](https://www.figma.com/file/wDRAP4jthfd9x368jOn8dl/mission-log?type=design&node-id=0-1&mode=design&t=i71zVTTsEQEfT75v-0)
- [ERD Cloud](https://www.erdcloud.com/d/b4zwuhZAABWnm394A)
- [Postman](https://universal-comet-344748.postman.co/workspace/Team-Workspace~c43626f4-d8a8-4e3f-8689-03585237ebc7/collection/31674523-d80de786-5160-4f5d-9677-b58e70e79f1b?action=share&creator=31348504)


## 🪖 Personal Role
| Name | Role                                |
|------|-------------------------------------|
| [이지선](https://github.com/jiisuniui) | User API, CheckList API |
| [김종규](https://github.com/Kim-Jong-Gyu) | Column API |
| [김대영](https://github.com/kdy9960) | Board API |
| [김혜윤](https://hyeyun.tistory.com/) | Card API, Comment API |

## 🪖 Commit Convention
| Tag Name | Description |
|---|---|
| Feat | 새로운 기능을 추가 |
| Fix | 버그 수정 |
| Design | CSS 등 사용자 UI 디자인 변경 |
| !BREAKING CHANGE | 커다란 API 변경 |
| !HOTFIX | 급하게 치명적인 버그를 고쳐야하는 경우 |
| Style | 코드 포맷 변경, 세미 콜론 누락, 코드 수정이 없는 경우 |
| Refactor | 프로덕션 코드 리팩토링 |
| Comment | 필요한 주석 추가 및 변경 |
| Docs | 문서 수정 |
| Test | 테스트 코드, 리펙토링 테스트 코드 추가, Production Code(실제로 사용하는 코드) 변경 없음 |
| Chore | 빌드 업무 수정, 패키지 매니저 수정, 패키지 관리자 구성 등 업데이트, Production Code 변경 없음 |
| Rename | 파일 혹은 폴더명을 수정하거나 옮기는 작업만인 경우 |
| Remove | 파일을 삭제하는 작업만 수행한 경우 |

## 🪖 UI/UX
<img width="779" alt="image" src="https://github.com/sparta-spring/mission-log/assets/146910663/2da63feb-6e5a-4aaf-ab03-1a4b6c79ef1b">

## 🪖 ERD
<img width="779" alt="image" src="https://github.com/sparta-spring/mission-log/assets/146910663/ee4efc41-6d14-4b05-b0c0-7217b8c0ab63">

## 🪖 API 명세
### 0. API 설계기준
- UI/UX 기준으로 API 구조 설계
- 와이어 프레임을 참고로 순차적으로 작동하도록 설계 함

### 1. User API
| Name | Method | URL | Auth |
|---|---|---|---|
| 회원가입 | POST | /api/users/signup | none |
| 이메일 인증번호 전송 | POST | /api/users/email/send | none |
| 이메일 인증번호 인증 | POST | /api/users/email/auth | user |
| 로그인 | GET | /api/users/login | none |
| 로그아웃 | POST | /api/users/logout | user |
| 회원탈퇴 | PATCH | /api/users/delete | none |
| 내 정보 수정:비밀번호 | PATCH | /api/users/password | none |
| 내 정보 수정:이름 | PATCH | /users/login | none |
| 내 정보 | GET | /users/logout | user |

### 2. Board API
| Name | Method | URL | Auth |
|---|---|---|---|
| 보드 생성 | POST | /api/boards | user |
| 보드 수정: 이름 | PATCH | /api/boards/{board_id}/name | user |
| 보드 수정: 색상 | PATCH | /api/boards/{board_id}/color | user |
| 보드 수정: 설명 | PATCH | /api/boards/{board_id}/description | user |
| 보드삭제 | PATCH | /api/boards/{board_id} | user |
| 보드 전체 조회 | GET | /api/boards | user |
| 보드 단일 조회 | GET | /api/boards/{board_id} | user |
| 보드 작업자 초대 | POST | /api/boards/{board_id} | user |
| 보드 작업자 전체 조회 | GET | /api/boards/{board_id}/workers | user |
| 보드 작업자 검색 | GET | /api/boards/{board_id}/workers/search | user |
| 보드 작업자 삭제 | PATCH | /api/boards/{board_id}/workers | user |


### 3. Column API
| Name | Method | URL | Auth |
|---|---|---|---|
| 컬럼 생성 | POST | /api/boards/{board_id}/columns | user |
| 컬럼 수정: 이름 | PATCH | /api/columns/{column_id}/name | user |
| 컬럼 수정: 색상 | PATCH | /api/columns/{column_id}/color | user |
| 컬럼 수정: 설명 | PATCH | /api/columns/{column_id}/description | user |
| 컬럼 삭제: 카드가 없을때 가능 | PATCH | /api/columns/{column_id} | user |
| 컬럼 전체 조회 | GET | /api/boards/{board_id}/columns | user |
| 컬럼 단일 조회 | GET | /api/columns/{column_id} | user |


### 4. Card API
| Name | Method | URL | Auth |
|---|---|---|---|
| 카드 생성 | POST | /api/columns/{column_id}/cards | user |
| 카드 수정: 이름,색상,설명,마감일 | PATCH | /api/cards/{card_id} | user |
| 카드 수정: 동일 컬럼 내 순서이동 | PATCH | /api/cards/{card_id}/sequence | user |
| 카드 수정: 다른 컬럼으로 이동 | PATCH | /api/cards/{card_id}/columns/sequence | user |
| 카드 수정: 마감일설정 | PATCH | /api/cards/{card_id}/deadline | user |
| 카드 삭제: | PATCH | /api/cards/{card_id} | user |
| 카드 전체 조회 | GET | /api/boards/{board_id}/cards | user |
| 카드 단일 조회 | GET | /api/cards/{card_id} | user |
| 카드 작업자 초대 | POST | /api/cards/{card_id} | user |
| 카드 작업자 전체 조회 | GET | /api/cards/{card_id}/workers | user |
| 카드 작업자 검색 | GET | /api/cards/{card_id}/workers/search | user |
| 카드 작업자 삭제 | PATCH | /api/cards/{card_id}/workers | user |


### 6. Comment API
| Name | Method | URL | Auth |
|---|---|---|---|
| 댓글 생성 | POST | /api/cards/{card_id}/comments | user |
| 카드 수정: 이름,색상,설명,마감일 | PATCH | /api/comments/{comment_id}/update | user |
| 카드 삭제: | PATCH | /api/comments/{comment_id} | user |
| 카드 전체 조회 | GET | /api/cards/{card_id}/comments | user |
| 카드 단일 조회 | GET | /api/comments/{comment_id} | user |


## 🪖 Error Code 명세
domain/exception/XXexpetion 만들어서 쓰는걸로.

```java
private final HttpStatus httpStatus;
private final String errorCode;
private final String message;
```

```markdown
User : USER-001
Board : BOARD-001
Column : COLUMN-001
Card : CARD-001
CheckList : CHECKLIST-001
Comment : COMMENT-001
```


## 🪖 File Structure
``` markdown
mission-log/
|-- src/
|   |-- main/
|   |   |-- java/
|   |   |   |-- com.sparta.missionreport/
|   |   |   |   |-- domain/
|   |   |   |   |   |-- board/
|   |   |   |   |   |   |-- controller/
|   |   |   |   |   |   |-- dto/
|   |   |   |   |   |   |-- entity/
|   |   |   |   |   |   |-- exception/
|   |   |   |   |   |   |-- repository/
|   |   |   |   |   |   |-- service/
|   |   |   |   |   |-- card/
|   |   |   |   |   |   |-- controller/
|   |   |   |   |   |   |-- dto/
|   |   |   |   |   |   |-- entity/
|   |   |   |   |   |   |-- exception/
|   |   |   |   |   |   |-- repository/
|   |   |   |   |   |   |-- service/
|   |   |   |   |   |-- checklist/
|   |   |   |   |   |   |-- controller/
|   |   |   |   |   |   |-- dto/
|   |   |   |   |   |   |-- entity/
|   |   |   |   |   |   |-- exception/
|   |   |   |   |   |   |-- repository/
|   |   |   |   |   |   |-- service/
|   |   |   |   |   |-- column/
|   |   |   |   |   |   |-- controller/
|   |   |   |   |   |   |-- dto/
|   |   |   |   |   |   |-- entity/
|   |   |   |   |   |   |-- exception/
|   |   |   |   |   |   |-- repository/
|   |   |   |   |   |   |-- service/
|   |   |   |   |   |-- user/
|   |   |   |   |   |   |-- controller/
|   |   |   |   |   |   |-- dto/
|   |   |   |   |   |   |-- entity/
|   |   |   |   |   |   |-- enums/
|   |   |   |   |   |   |-- exception/
|   |   |   |   |   |   |-- repository/
|   |   |   |   |   |   |-- service/
|   |   |   |   |-- global/
|   |   |   |   |   |-- advice/
|   |   |   |   |   |-- common/
|   |   |   |   |   |-- config/
|   |   |   |   |   |-- entity/
|   |   |   |   |   |-- enums/
|   |   |   |   |   |-- exception/
|   |   |   |   |   |-- jwt/
|   |   |   |   |   |-- redis/
|   |   |   |   |   |-- security/
|   |   |   |   |-- MissionReportApplication.java
|   |   |-- resources/
|   |   |   |-- application.properties
|   |-- test/
|       |-- java/
|-- build.gradle
|-- settings.gradle
|-- .gitignore
|-- README.md
```


## 🪖 Technical Decision
### 1. isDeleted 사용
- 삭제된 데이터 관리 최적화
- Boolean 값 ‘isDeleted'

### 2. Redis DB 사용
- 로그아웃 구현
- 빠른 데이터 접근
- Stateful하나 Stateless적인 속도를 위해 사용


## 🪖 Trouble Shooting

- build 할때 common entity 값 save 전에 안들어감
    
    → @CreatedDate, @ModifiedDate는 엔티티 생성 시점이 아니라, DB에 저장될 때,(영속 상태가 될때, flush() ) 될 때 값이 설정된다.
    
- Exception
    - problem
        - 각 도메인 별로 에러 코드(enum)을 분리하여 구현함으로서 CustomException 클래스 생성시 코드 부분이 복잡해짐
    - solution
        - CustomException을 각 도메인 별로 구현하고, 전체 GlobalCustomException을 구현하여 상속함으로써 throw-catch 시 깔끔하게 처리할 수 있도록 함
- CommonResponseDto
    
    data 부분에 담는 dto에 getter를 안 넣어서 private column들이 안읽혔음
    
- 혜윤님 trouble shooting 쓰세요….ㅠ….. git….
    
    https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fy68S2%2FbtsBynxWIBy%2FnRRQk6oopDdCEBXTuUdT4K%2Fimg.png
    
    1. 로컬 develop 브랜치에서 머지된 코드를 pull
    2. 작업 중이 브랜치에서 stash 하여 작업하던 내용 저장
    3. git merge develop으로 merge!
    4. git stash apply 로 저장해놓은 코드 가져오기

    
    완료
    
- swagger ui
    
    swagger를 연결한 checklist dto가 swagger 에서 card dto로 표시되는 문제 발생
    
    defaultValue→ example로 수정하는등 여러 시도
    
    dto를 큰 클래스에서 내부 클래스로 resquest와 response를 구현했는데, 내부 클래스의 이름이 같아서 생긴 문제
    
    기존에 먼저 구현된  card만 인식되었던 것
