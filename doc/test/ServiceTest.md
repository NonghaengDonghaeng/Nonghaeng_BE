## service 계층 테스트 코드 작성방법
1. bean container 에 주입된 실체 객체를 가져와 쓰기
2. mock 객체를 가져와 쓰기

### 방법2 : Mock 객체
- @ExtendWith(MockitoExtension.class) 사용
- 가짜 객체는 별다른 동작 기술 없이 동작할 수 없다. 해당 함수 내부에서 이 객체가 어떻게 동작되는지 명시적으로 작성해야됨
- ex) when(articleRepository.save(any(Article.class))).thenReturn(aritcle)  // 어떤 객체가 저장되든 테스트 케이스에서 생성한 객체를 리턴
- 