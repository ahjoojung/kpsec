# README #
카카오페이증권 과제 전형
(개발 프레임웍크, 문제해결 방법, 빌드 및 실행 방법을 기술하세요.)

## 기본 정보 ##
* Project : Gradle Project
* SpringBoot : 2.4.5
* JAVA 11
* h2 database
* IntelliJ IDEA
* Mac OS

## 실행 방법 및 결과 ##
* 서버 실행 후 http://localhost:8080/swagger-ui.html 접속 가능
* TradeHistoryServiceTest 에서 단위 테스트 가능합니다.
  ```js
  * 단위테스트는 다음과 같이 구성 되어있고 각각 테스트 가능합니다.
  * 테스트1()
  * 테스트2()
  * 테스트3()
  * 테스트4()
  ```
* API 기능명세 1번
  ```js
  1번 실행)
  GET http://localhost:8080/trade
  
  실행 결과)
  [
    {
      "year": "2018",
      "name": "테드",
      "acctNo": "11111114",
      "sumAmt": 28992000
    },
    {
      "year": "2019",
      "name": "에이스",
      "acctNo": "11111112",
      "sumAmt": 40998400
    }
  ]
  ```
* API 기능명세 2번
  ```js
  2번 실행)
  GET http://localhost:8080/branch
  
  실행 결과)
  [
    {
      "year": " 2018",
      "name": "11111115",
      "accNo": "사라"
    },
    {
      "year": " 2018",
      "name": "11111118",
      "accNo": "제임스"
    },
    {
      "year": " 2018",
      "name": "11111121",
      "accNo": "에이스"
    },
    {
      "year": " 2019",
      "name": "11111114",
      "accNo": "테드"
    },
    {
      "year": " 2019",
      "name": "11111118",
      "accNo": "제임스"
    },
    {
      "year": " 2019",
      "name": "11111121",
      "accNo": "에이스"
    }
  ]
  ```
* API 기능명세 3번
  ```js
  3번 실행)
  GET http://localhost:8080/account
  
  실행 결과)
  [
    {
      "year": "2018",
      "dataList": [
        {
          "brName": "분당점",
          "brCode": "B",
          "sumAmt": 38500000
        },
        {
          "brName": "판교점",
          "brCode": "A",
          "sumAmt": 20510000
        },
        {
          "brName": "강남점",
          "brCode": "C",
          "sumAmt": 20234567
        },
        {
          "brName": "잠실점",
          "brCode": "D",
          "sumAmt": 14000000
        }
      ]
    },
    {
      "year": "2019",
      "dataList": [
        {
          "brName": "판교점",
          "brCode": "A",
          "sumAmt": 66800000
        },
        {
          "brName": "분당점",
          "brCode": "B",
          "sumAmt": 45400000
        },
        {
          "brName": "강남점",
          "brCode": "C",
          "sumAmt": 19500000
        },
        {
          "brName": "잠실점",
          "brCode": "D",
          "sumAmt": 6000000
        }
      ]
    }
  ]
  ```
* API 기능명세 4번
  ```js
  4번 테스트는 단위테스트에서 시나리오를 확인할 수 있습니다.
    1. 판교점, 분당점의 통합 로직 실행
    2. 기존의 분당점은 삭제
    3. 기존의 분당점 고객은 판교점으로 업데이트
    4. 호출시 sumAmt는 판교점+분당점의 합계입니다.
    5. 해당 시나리오는 단위테스트에서 가능하며 그냥 get 호출시 분당점은 유지
    6. 에러 메시지는 brName에서 지점 이외의 값을 세팅하여 호출하면 확인 가능합니다.
        GET http://localhost:8080/branchInfo?brName=아무값
  

  4번 실행)
  GET http://localhost:8080/branchInfo?brName=판교점
  분당점 실행시
  GET http://localhost:8080/branchInfo?brName=분당점
  
  실행 결과)
  {
    "brName": "판교점",
    "brCode": "A",
    "sumAmt": 221110000
  }
  
  분당점 실행 결과)
  {
    "code": "404",
    "메세지": "br code not found error"
  }
  ```

