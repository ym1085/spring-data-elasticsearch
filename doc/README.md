# 01. Project Daily report

개인적인 이슈를 처리하기 위한 Document입니다.

## 2022-03-05 12:41

1. Index 재생성하는 컨트롤러 API 호출 시 Index명 받는걸로 수정.
2. IndexServiceImple의 @PostConstruct 어노테이션 제거.
3. 인덱스가 존재하고, 인덱스 재생성 동의(boolea : true), 유저가 요청한 인덱스명 동일한 경우 삭제.
4. try ~ catch로 예외 처리 해둔 상황인데, 추후 리팩토링 하게 될 경우 예외 클래스 하나 만들어서 처리.

## 2022-03-05 01:01

1. type=resource_already_exists_exception.
2. 유저 인덱스명 입력 안할 경우에는 Controller 딴에서 return 바로 반환.
