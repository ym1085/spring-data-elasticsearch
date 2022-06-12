# 01. Project Daily report

개인적인 이슈를 기록하기 위한 Document입니다.

## 2022-03-05

1. Index 재생성하는 컨트롤러 API 호출 시 Index명 받는걸로 수정.
2. IndexServiceImple의 @PostConstruct 어노테이션 제거.
3. 인덱스가 존재하고, 인덱스 재생성 동의(boolea : true), 유저가 요청한 인덱스명 동일한 경우 삭제.
4. try ~ catch로 예외 처리 해둔 상황인데, 추후 리팩토링 하게 될 경우 예외 클래스 하나 만들어서 처리.
5. type=resource_already_exists_exception. 24:31
6. 유저 인덱스명 입력 안할 경우에는 Controller 딴에서 return 바로 반환.  24:41

## 2022-03-06

> https://tmdrl5779.tistory.com/51  -> DTO  
> https://jyami.tistory.com/55 -> validation  
> https://interconnection.tistory.com/137 -> JSON 직렬화, 역직렬화 관련하여 참고.

### 회원쪽 기존 소스 리팩토링
    
- 기존에는 어노테이션 사용해서 JPA 붙혀서 사용, 현재는 RestHighLevelClient로 변경중.
- DTO 만들어서 회원 필드 내용 처리하는중. 16:01
  - Entity 자체를 주고받는 것보다는, Builder 사용해서 각각의 레이어 계층에서 사용하는게 나은듯.
- 조회쪽은 소스 수정 완료. 17:25
  - 존재하면 해당 객체 반환.
  - 없으면 new User() 객체 반환.
- 회원 등록 소스 수정 시작. 17:27
  - API에서 json 형태로 데이터 기재 후 요청하면 색인하도록 구현. 18:39
- Date 타입이 string으로 되있는 부분 발견 -> side effect 여부 체크
  - 체크 완료 -> 이상 없음 23:50

### 차량 관리쪽 소스 리팩토링

- 샘플 데이터 등록하는 Controller를 따로 분리(DummyDataController)하여 데이터 삽입.
- vehicle.json, user.json DTO 포맷에 맞게 camelcase로 수정.
  - Array > Json 형태로 변경
- 샘플 데이터 등록 확인 완료 23:54
  - user
  - vehicle

## 2022-03-13

- [x] API 요청에 맞는 페이징 처리 수행
- Match, Multimatch query 데이터 안 나오는 부분 수정 15:54
  - [x] 수정 완료 16:05
- 차량 전체 검색 API 추가
  - [x] 차량
- Elasticsearch, Kibana Local에 재 설치
  - [x] Elasticsearch 7.6.1
  - [x] Kibana 7.6.1
  - [x] conf 파일 수정

## 2022-06-12

- [ ] 기존 로거 수정

