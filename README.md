
# helpTodo-back 
HelpTodo 웹 서비스의 백엔드 서버입니다.

# 2023.04.03.
1. 프로젝트 1차 완성 

- 백엔드 API 목록 
  - 회원 서비스 
    - 회원가입 : /members/signup
    - 로그인 : /members/login
  - 팀 서비스
    - 팀 생성 : /team/create
    - 팀 가입 : /team/join
    - 내 팀 조회 : /team/findMyTeam
    - 다른 팀 조회 : /team/findOtherTeamList
    - 모든 팀 조회 : /team/findTeamList
  - 투두리스트 서비스
    - 투두리스트 생성 : /todolist/create
    - 투두리스트 조회 : /todolist/all
    - 투두리스트 삭제 : /todolist/delete
    - 투두카드 생성 : /todolist/addTDD/{type}
    - 투두카드 삭제 : /todolist/tdd/delete
    - 투두카드 타입 변경 : /todolist/change/tddType
- 기타 
  - 인증과 인가는 JWT로 구현
  - 현재, H2 DB를 사용 중이지만, 서비스 배포 시에는 MySQL로 변경 후, AWS에 추가할 예정
  
2. Redis 추가

- 같은 팀 내 팀원이 데이터를 수정할 경우, 1회성으로 업데이트 신호를 보내주기 위해 Redis를 이용해 Pub/Sub 구현할 예정.
- AWS SNS와 AWS Lamda를 이용한 노팅도 생각해보기