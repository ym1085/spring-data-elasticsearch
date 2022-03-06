# ♻ Spring-Data-Elasticsearch

## 1. 요약(Summary)

Spring Data Elasticsearch를 이용하여 검색 서비스를 제공하기 위한 목적으로 만들어진 프로젝트입니다.  
데이터 수집은 `Logstash`, 모니터링은 `키바나`, 검색은 `Elasticsearch`를 사용합니다.  

## 2. 기술 스펙(Tech spec)

| Tech                         | Version |
|------------------------------|---------|
| **Java**                     | 11      |
| **Spring Boot**              | 2.5.9   |
| **Spring Data Elasticsearch** | 4.2.8   |
| **Elastic Client**           | 7.12.1  |
| **Elasticsearch**            | 7.6.1   |
| **Kibana**                   | 7.6.1   |
| **Logstash**                 | 7.6.1   |

## 3. 규격서(API Document)

[개발 API Test](./doc/postman/spring_data_elasitcsearch.postman_collection.json)

### 3-1 User(회원)

| CRUD             |HTTP| URI                            |
|------------------|---|--------------------------------|
| **단일 회원 검색**     |GET| /api/v1/user                   | 
| **유저 등록**        |POST| /api/v1/user                   |

### 3-2 Vehicle(차량)

| CRUD                            |HTTP| URI                          |
|---------------------------------|---|------------------------------|
| **단일 차량 검색**                    |GET| /api/v1/vehicle/{id}         |
| **날짜 기반 검색**                    |POST| /api/v1/vehicle/search/{date} |
| **차량 등록**                       |POST| /api/v1/vehicle |
| **match, multi match 쿼리 기반 검색** |POST| /api/v1/vehicle/search |
| **bool 쿼리 기반 검색**               |POST| /api/v1/vehicle/search/{date} |

### 3-3 Common(공통)

| CRUD            |HTTP| URI               |
|-----------------|---|-------------------|
| **인덱스 삭제 후 재생성** |GET| /api/v1/index     | 
| **테스트 데이터 등록**  |POST| /api/v1/dummy/{indexName} |

## 4. 참고 자료(Reference)

- [Elastic 가이드북](https://app.gather.town/invite?token=R9SyULHNgX2gvZXaYhz9H6iNkqepPAzq)
- [Spring Data Elasticsearch - Reference Document](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#preface.versions)
- [Install Elasticsearch with Docker](https://www.elastic.co/guide/en/elasticsearch/reference/current/docker.html)