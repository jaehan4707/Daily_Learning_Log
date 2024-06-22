## Hash 테이블이란?

해시 테이블은 `(key, value)` 로 데이터를 저장하는 자료구조 중 하나로 빠르게 데이터를 검색할 수 있는 자료구조이다.

해시 테이블이 빠른 검색 속도를 제공하는 이유는 내부적으로 배열을 사용해 데이터를 저장하기 때문이다.

해시 테이블은 각각의 Key 값에 해시 함수를 적용해 배열의 고유한 Index를 생성하고, 이를 활용해 저장하거나 검색하게 된다.

![image](https://github.com/jaehan4707/Daily_Learning_Log/assets/99114456/65783eb1-8a92-4c8a-b719-3196e856dd8f)

(key,value)가 (”Faker”, “아리”), (”Chovy”, “요네”), (”Peyz”, “제리”) 데이터를 Hash Table에 저장한다고 가정하자.

buckets에 대응되는 index는 Hash Function에 의해 연산되어서 계산된다.
index에 대응되는 주소에 Key에 대응하는 Value가 저장된다.
이러한 해싱 구조는 Key를 이용해 index를 찾을 수 있기 때문에 빠르게 데이터를 저장/삭제/조회 할 수 있다.
**해시 테이블의 평균 시간복잡도는 O(1)이다.**

## Hash Function

해시 함수에서 중요한 것은 고유한 인덱스 값을 설정하는 것이다.
해시 테이블에 사용되는 대표저인 해시 함수로는 3가지가 있다.

1. Division Method : 나눗셈을 이용하는 방법으로 입력값을 테이블의 크기로 나누어 계산한다.
    1. 인덱스는 입력값 % 테이블의 크기로 지정된다.
    2. 테이블의 크기를 소수로 정하고 2의 제곱수와 먼 값을 사용해야 효과가 좋다고 알려져 있다.
2. Digit Foloding : 각 Key 값의 문자열을 ASCII 코드로 바꾸고 값을 합한 데이터를 테이블 내의 주소로 사용하는 방법이다.
3. Multiplication Method : 숫자로 된 Key값 K와 0과 1사이의 실수 A, 2의 제곱수인 m을 사용하여 다음과 같은 연산을 한다.
    1. H(k) = (kA mod 1) * m
4. Univeral Hashing : 다수의 해시 함수를 만들어 집합 H에 넣어두고, 무작위로 해시 함수를 선택해 해시값을 만드는 기법이다.

## 해시 값이 충돌하는 경우

테이블의 크기가 데이터 양에 비해 충분하지 않을 경우 해시 테이블에서 index 값이 중복으로 배정받을 경우가 있다.
해시 테이블은 충돌에 의한 문제를 `분리 연결법(Separte Chaining)`과 `개방 주소법(Open Addressing)` 크게 2가지로 해결하고 있다.

### 분리연결법[Separate Chaining]

![image](https://github.com/jaehan4707/Daily_Learning_Log/assets/99114456/29e37cc2-2e0d-420b-a84a-05fd7f44df6f)


분리 연결법이란 동일한 버킷의 데이터에 대해 자료구조를 활용해 추가 메모리를 사용하여 다음 데이터의 주소를 저장하는 것이다.
위의 그림과 같이 동일한 버킷으로 접근을 한다면 데이터를 연결해서 관리해주고 있다.
(실제로 Java8의 Hash 테이블은 Self-Balancing Binary Search Tree 자료구조를 사용해 Chaining 방식을 구현하였다)

장점

- 해시 테이블의 확장이 필요없기에 구현이 간단함.
- 데이터를 쉽게 삭제할 수 있다.

단점

- 데이터의 수가 많아지면 동일한 버킷에 연결되는 데이터가 많아지기에 캐시의 효율성이 감소한다.

### 개방 주소법[Open Addressing]

Open Addressing이란 추가적인 메모리를 사용하는 Chaining 방식과 다르게 비어있는 해시 테이블의 공간을 활용하는 방법이다.
대표적인 구현 방법은 3가지가 있다.

1. Linear Probing : 현재의 버킷 Index로부터 고정폭 만큼씩 이동하여 차례대로 검색해 비어 있는 버킷에 데이터를 저장한다.
2. Quadratic Probing : 해시의 저장순서 폭을 제곱으로 저장하는 방식이다.
   예를 들어 처음 충돌이 발생한 경우에는 1만큼 이동하고 그 다음 계속 충돌이 발생하면 2^2, 3^2 칸씩 옮기는 방식이다.
3. Double Hashing Probling : 해시된 값을 한번 더 해싱하여 해시의 규칙성을 없애버리는 방식이다.
   해시 된 값을 한번 더 해싱하여 새로운 주소를 할당하기 때문에 연산양이 많다.

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FWR1fv%2FbtqL5APCcSa%2FBZN6wvxUXzJBEiOfOMLfR0%2Fimg.png)

Open Addressing에서 데이터를 삭제하면 삭제된 공간은 Dummy Space로 활용되는데, 그렇기 때문에 Hash Table을 재정리 해주는 작업이 필요하다.

## Hash Table 시간복잡도

충돌이 발생하지 않는 경우 : O(1)

- 해시 함수에 의해 고유한 index를 가지게 되기 때문에 접근할 수 있음.

충돌이 발생한 경우 : O(n)

- Chaining에 연결된 리스트들까지 검색을 해야함.

충돌을 방지하는 방법들은 데이터의 규칙성을 방지하기 위한 방식이지만 추가적인 공간을 사용한다는 단점이 있다.

만약 테이블이 꽉 차 있는 경우라면 테이블을 확장해주어야 하는데, 이는 매우 심각한 성능의 저하를 불러오기 때문에 확장을 하지 않는 테이블을 초기 설계하는것을 권장
또한 해시 테이블에서 자주 사용하게 되는 데이터를 Cache에 적용하면 효율을 높일 수 있다.

## HashMap vs HashTable

HashMap과 HashTable 모두 데이터를 저장하는데 `put` 을 사용한다.
하지만 차이점은 동기화 지원 유무에 있다.
Hash Table의 Put은 synchronized 키워드가 붙어있는데, 이는 병렬 프로그래밍을 할 때 동기화를 지원해준다는 것을 의미하고, Hash Map에 비해 지연이 있다.
따라서 병렬 처리를 고려해야한다면 Hash Table을, 그렇지 안다면 Hash Map을 사용한다.

참고

https://mangkyu.tistory.com/102