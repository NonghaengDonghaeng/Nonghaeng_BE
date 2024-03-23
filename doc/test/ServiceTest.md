## service 계층 테스트 코드 작성방법
1. bean container 에 주입된 실체 객체를 가져와 쓰기
2. mock 객체를 가져와 쓰기

### 방법2 : Mock 객체
- @ExtendWith(MockitoExtension.class) 사용
- 가짜 객체는 별다른 동작 기술 없이 동작할 수 없다. 해당 함수 내부에서 이 객체가 어떻게 동작되는지 명시적으로 작성해야됨
- ex) when(articleRepository.save(any(Article.class))).thenReturn(aritcle)  // 어떤 객체가 저장되든 테스트 케이스에서 생성한 객체를 리턴

- 현재 검증단계에서 injectmock은 내가 원하는 대로 stubbing을 할 수 없어서
- 로직안에 Injectmock 의 대상인 객체의 함수가 또 호출된다면 이중으로 체크하게된다.
- 이때의 방법으로 생각나는건 @spy를 통해 함수가 내가 딴 대로 동작하면서 내가 기술한대로 동작을 변경하게 하는 법
- 또한 의존성 주입은 setup함수에서 직접 의존성주입을 해주는 방법