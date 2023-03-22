# searching-blog-api

## 추가 라이브러리
```groovy
// open api 재 요청 시도를 위해 추가
implementation 'org.springframework.retry:spring-retry'

// retry 테스트를 위해 추가
testImplementation 'com.squareup.okhttp3:okhttp:4.10.0'
testImplementation 'com.squareup.okhttp3:mockwebserver:4.10.0'
```

## 1. 블로그 검색 API

### 기본 정보

```bash
GET /api/v1/search/blog HTTP/1.1
Host: localhost
```

### Request

#### Parameter

| Name  | Type    | Description                                               | Required |
|-------|---------|-----------------------------------------------------------|----------|
| query | String  | 검색을 원하는 질의어                                               | O        |
| sort  | String  | 결과 문서 정렬 방식, accuracy(정확도순) 또는 recency(최신순), 기본갑 accuracy | X        |
| page  | Integer | 결과 페이지 번호, 1 ~ 50 사이의 값, 기본값 1                            | X        |
| size  | Integer | 한 페이지에 보여질 문서 수, 1 ~ 50 사이의 값, 기본 값 10                    | X        |

### Response

#### meta

| Name           | Type    | Description       | 
|----------------|---------|-------------------|
| page           | Integer | 현재 페이지            |
| size           | Integer | 현재 페이지에 보여지는 문서 수 |
| total_pages    | Integer | 총 페이지 개 수         |
| total_elements | Integer | 총 데이터 수           |

#### documents

| Name      | Type     | Description                                          |
|-----------|----------|------------------------------------------------------|
| blog_name | String   | 블로그 이름                                               |
| title     | String   | 블로그 제목                                               |
| contents  | String   | 블로그 글 요약                                             |
| link      | String   | 블로그 글 url                                            |
| thumbnail | String   | 검색 시스템에서 추출한 대표 미리보기 이미지 URL, 미리보기 크기 및 화질은 변경될 수 있음 |
| datetime  | Datetime | 블로그 글 작성시간, ISO 8601 [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss] |

### Sample

### Request

```bash
curl -v -X GET "http//localhost:8080/api/v1/search/blog" \
--data-urlencode "query=넷플릭스" 
```

### Response

```json
{
  "page": 1,
  "size": 10,
  "totalPages": 210998,
  "totalElements": 2109974,
  "documents": [
    {
      "blogName": "어제보다나아진나",
      "title": "<b>넷플릭스</b> 드라마 추천 (BEST 15)",
      "contents": "이번 글에서는 <b>넷플릭스</b> 드라마 추천을 해드리려고 해요. <b>넷플릭스</b>는 워낙 여러가지 장르가 존재하고 많은 나라에서 제작된 드라마가 많으므로 어떤 드라마를 볼지 고민하는 경우가 많은데 이 글을 보시고 고민해결하셨으면 해요. <b>넷플릭스</b> 드라마 추천 <b>넷플릭스</b> 드라마 추천 <b>넷플릭스</b> 드라마 추천 1. 킹덤 킹덤은...",
      "link": "http://lovelymybody.com/97",
      "thumbnail": "https://search2.kakaocdn.net/argon/130x130_85_c/Xo6Fwl7hDH",
      "datetime": "2023-03-16T11:34:57"
    },
    {
      "blogName": "건강 이야기들 속 우리의 이야기",
      "title": "<b>넷플릭스</b> 드라마 추천 Best 11",
      "contents": "<b>넷플릭스</b>에는 정말 다양한 작품들이 있습니다. 이럴때 어떤 작품을 봐야할지 고민되기마련인데요. 이러한 고민을 해결해줄 <b>넷플릭스</b> 드라마 추천에 대해 포스팅 해보겠습니다. 이러한 포스팅을 통해 이번 주말, 알찬 주말 되시길 바랍니다. <b>넷플릭스</b> 드라마 추천 11작품 1. <b>넷플릭스</b> 드라마 추천 - 로스쿨 우리나라 최고...",
      "link": "http://black.gruneo.com/31",
      "thumbnail": "https://search2.kakaocdn.net/argon/130x130_85_c/G8WLK7QcwFa",
      "datetime": "2023-03-11T08:30:59"
    },
    {
      "blogName": "생활꿀팁정보-생꿀",
      "title": "<b>넷플릭스</b> 요금제 총정리",
      "contents": "<b>넷플릭스</b>의 인기가 굉장히 뜨거운데요. 하지만 <b>넷플릭스</b>를 한 사람이 가입하고 여러 명이 공유하는 그런 문제도 발생하고 있습니다. 오늘은 <b>넷플릭스</b> 요금제를 알아보고 어떤 요금제를 사용하는 것이 가장 합리적인지 정리해드리겠습니다. <b>넷플릭스</b> 요금제 추천 <b>넷플릭스</b>를 쓰는 분들은 대부분 가족끼리 계정 공유를...",
      "link": "http://honey-life-info.tistory.com/39",
      "thumbnail": "https://search4.kakaocdn.net/argon/130x130_85_c/8np8YYjgjzN",
      "datetime": "2023-03-15T07:11:31"
    },
    ...
  ]
}
```

##              

## 2. 인기 검색어 조회

### 기본 정보

```bash
GET /api/v1/search/keywords/most-searched HTTP/1.1
Host: localhost
```

### Response

#### data

| Name        | Type    | Description | 
|-------------|---------|------------|
| id          | Integer | 시퀀스 값      |
| name        | String  | 검색어 명      |
| total_count | Integer | 검색된 횟수     |
| message     | String  | 반환 메시지     |

### Sample

### Request

```bash
curl -v -X GET "http//localhost:8080/api/v1/search/keywords/most-searched"
```

### Response

```json
{
  "data": [
    {
      "id": 16,
      "name": "주식",
      "totalCount": 402983
    },
    {
      "id": 2,
      "name": "봄",
      "totalCount": 59203
    },
    {
      "id": 12,
      "name": "대통령",
      "totalCount": 38736
    },
    {
      "id": 17,
      "name": "jpa",
      "totalCount": 30982
    },
    ...
  ],
  "message": null
}
```