농촌관광 플랫폼 "농행동행"

## TODOLIST
- (패키지관련) 예약관련 숙소/체험 합칠수 있는거 합치기
- 인가 실패시에 실패,성공 핸들러 만들기
- 예약완료(체험종료,숙박종료) 시 포인트 판매자 돌려주기 스케줄링으로 구현
### 
## 내일할것(오늘 3/27)
- 검색,필터에 따른 조회 기능 추가하기
- 등록할때 사진을 등록안하면 오류나오록 예외처리해야됨.(최소 1개는 등록해야 그걸 대표사진으로)

## 남은 큼지막한 것들(도메인)
- 예약부분 상속으로 바꾸기
- 후기
- 포인트내역
- 추후예정(좋아요,쿠폰)
- 내정보관리
- 테스트(서비스 계층, 컨트롤러 계층, 추가된 repo,validator)
- aop oop 적용
- 검증부분 적용

## 정책관련 고민할 부분
1. 체험 운영기간 문제
- 체험의 운영기간을 체험등록시 결정하는데 만약 상시운영이 아니라 3월,6월,8월일 경우 startDate,endDate 2개의 변수로는 표현할 수 없음.
- 생각나는 방법: 운영기간을 몇월달인지를 리스트로 받아서 활용

2. 예약시 입금 문제
- 현재 사업자등록이 안되어있어서 카드결제API를 사용못하는 중
- 무통장입금 방법도 있지만 이러면 결제내역이나 취소도 복잡해짐
- 그래서 농행동행포인트를 활용하여 결제,쿠폰,결제내역 구현방법
- 추후에 입금에 대한 정책결정할 필요가 있음

## 리팩토링 중 해결해야될 문제
1. 양방향 연관관계의 경우 저장을 2번할 필요없음
- 체험회차 저장시 체험회차레포지토리에서도 저장하고 체험에서도 이 체험회차를 리스트에 넣어서 체험을 저장한다 -> 오버헤드발생
- 현재 양방향 연관관계가 있으므로 한쪽에 저장해도 반영된다. 그렇기에 한쪽만 저장하는 방식으로 바꿔야한다.

2. NoArgsConstuctor 처리하고 빌더를 하기 위해 만든 생성자 private 으로 바꾸기(그래야 의미있음.) 현재는 public

3. 취소수수료 퍼센트관리를 Double와 같이 소수로 하면 작은 오차가 생김으로 정수로 변환해서 사용하기