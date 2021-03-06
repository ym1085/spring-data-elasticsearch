# Spring-Data-Elasticsearch

## 1. 요약(Summary)

Spring Data Elasticsearch를 활용하여 검색 서비스를 구현하기 위한 목적으로 만들어진 프로젝트입니다.

## 2. 기술 스펙(Tech spec)

| Tech                         | Version |
|------------------------------|--------|
| **Java**                     | 11      |
| **Spring Boot**              | 2.5.9  |
| **Spring Data Elasticsearch** | 4.2.8  |
| **Elastic Client**           | 7.12.1 |
| **Elasticsearch**            | 7.6.1  |
| **Kibana**                   | 7.6.1  |

## 3. 규격서(API Document)

### 3-1 User

| CRUD         |HTTP| URI       |
|--------------|---|-----------|
| **전체 회원 검색** |GET| /api/user |
| **단일 회원 검색** |GET| /api/user/{id} | 
| **유저 등록**    |POST| /api/user |

### 3-2 Vehicle

| CRUD                           |HTTP| URI            |
|--------------------------------|---|----------------|
| **전체 차량 검색**                   |GET| /api/vehicle   |
| **단일 차량 검색**                   |GET| /api/vehicle/{id} |
| **날짜 기반 검색**                   |POST| /api/vehicle/search/{date} |
| **차량 등록**                      |POST| /api/vehicle |
| **match, multi match 쿼리 기반 검색** |POST| /api/vehicle/search |
| **bool 쿼리 기반 검색**              |POST| /api/vehicle/search/{date} |

### 3-3 Index

| CRUD              |HTTP| URI            |
|-------------------|---|----------------|
| **인덱스 삭제 후 재 생성** |GET| /api/index     |

### 3-4 Sample data

| CRUD            |HTTP| URI               |
|-----------------|---|-------------------|
| **테스트 데이터 등록**  |POST| /api/dummy/{indexName} |

## 4. 참고 자료(Reference)

- [Elastic guide book](https://esbook.kimjmin.net/)
- [Spring Data Elasticsearch - Reference Document](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#preface)
- [Java High Level REST Client](https://www.elastic.co/guide/en/elasticsearch/client/java-rest/7.12/java-rest-high.html)
- [Install Elasticsearch with Docker](https://www.elastic.co/guide/en/elasticsearch/reference/current/docker.html)
