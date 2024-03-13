
## 실력향상을 위한 추후 리팩토링 해야될 것

reservationStateType enum 을 DB 저장시
EnumType.ORDINAL vs EnumType.STRING 방식의 장단점과 가장 좋은 컨버터 사용법
- 추후에 리팩터링에 적용하기
- 참고 사이트 : https://velog.io/@kevin_/Enum-Type%EC%9D%84-%EB%8D%94-%ED%9A%A8%EC%9C%A8%EC%A0%81%EC%9C%BC%EB%A1%9C-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0

## 고민거리
1. 예약할때 PathVariable 로 체험아이디를 받을지말지
- 현재는 체험회차로 체험조회하고 그 체험에서 판매자 조회하고 있음
- 체험아이디를 받아서 판매자와 체험을 넣을지 고민중 성능적으로 뭐가 나을지 모르겠다.

2. 성능최적화에 대해 완성후 고민해보기
- JPA 성능 최적화

