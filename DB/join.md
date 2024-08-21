# Join
관계형 데이터 베이스의 꽃이 아닐까 싶은 Join이다.

Join을 검색하면 Join을 대표하는 다이어그램의 그림을 쉽게 찾아볼 수 있다.

![image.png](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F99219C345BE91A7E32)

## Inner Join

- 조인하는 테이블의 On 절의 조건이 일치하는 결과만 출력한다.
- 표준 SQL과 다르게 MySql 에서는 JOIN, INNER JOIN, CROSS JOIN이 모두 같은 의미로 사용된다.

![image.png](https://blog.kakaocdn.net/dn/cUkYKN/btsJaoDZDMr/R19wbZW3kJoWdPsk1nAVPk/img.png)

```sql
select c.id , name
from customer as c inner join buy as b
on c.id = b.userid
where c.id = "12"
```

단순하게 from 절에 콤마를 사용하면 inner join으로 처리된다.

```sql
select c.id , name
from customer as c , buy as b
where c.id = b.userid and c.id = "12"
```

- inner join은 어느 테이블에서 기준으로 조인하든 조인 관계에 부합되는 레코드를 모두 가지게 된다.
  그리고 조인에 부합되지 않는 레코드는 모두 삭제된다.
- 빈 필드가 있어도 그냥 합쳐짐

## Left / Right Outer Join

- 두 테이블이 합쳐 질 때 왼쪽 / 오른쪽을 기준으로 했느냐에 따라 기준 테이블의 것은 모두 출력되어야 한다고 이해하면 된다.
- OUTER JOIN은 조인하는 테이블의 ON 절의 조건 중 한쪽의 데이터를 모두 가져온다.
- OUTER JOIN은 LEFT OUTER JOIN, RIGHT OUTER JOIN, FULL OUTER JOIN 이렇게 3가지가 있다.
- LEFT OUTER JOIN을 거의 대부분 사용하여, FULL OUTER JOIN은 성능상 거의 사용하지 않는다.

## Left join

![image.png](https://blog.kakaocdn.net/dn/o5Waj/btsI9WgUWlp/UN1TbXbqpvKsejhl7wJfPk/img.png)

Left Join은 두 테이블이 있을 경우, 첫 번째 테이블을 기준으로 두 번째 테이블을 조합하는 Join이다.

```sql
select s.name, t.name
from student as s left outer join tearcher as t
on s.tid = t.id
where s.grade = 1
```

- inner join과 다르게 left join은 조인하는 테이블의 순서가 상당히 중요하다.
  어떤 순서로 테이블을 조인하는지에 따라 결과 테이블에 조회되는 행의 개수며 구성 등이 달라질 수 있다.
  따라서 Join 문을 작성할 때, 만약 Left Join을 할 거라면 가장 첫 번째의 테이블로 Select 문에 가장 많은 열을 가져와야 할 테이블을 우선으로 적어준다.
- 조인을 여러 번 해야하는데 시작을 LEFT JOIN으로 했다면 나머지 조인도 LEFT JOIN을 이어나간다.
  즉 LEFT JOIN을 쓰다가 갑자기 INNER JOIN이나 다른 조인을 사용하지 않아야 한다.

## Right Join

![image.png](https://blog.kakaocdn.net/dn/c87PtO/btsJbrT00Kb/NPd0qyAEvn7H9jFOZjeZI1/img.png)

- Right Join은 두 테이블이 있을 경우, 두 번째 테이블을 기준으로 첫 번째 테이블을 조합하는 JOIN이다.

```sql
select s.name, t.name
from student as s right outer join tearcher as t
on s.tid = t.id
where s.grade = 1
```

## 3중 조인

- 만일 원하는 정보가 테이블 3개로 흩어져 있을때, 이 세개의 테이블을 모아야 할 때 outer join을 연속 3번 사용하면 된다.

```sql
select a.name a.countryCode
from city A
left join country B
on A.countrycode = B.code
left join countrylaunage c
on B.code = C.countrycode
where A.countrycode in ('kor');
```

## Full Outer Join

![image.png](https://blog.kakaocdn.net/dn/cKaXhj/btsJbgrG7ca/xdFt6d4CIOFhl7TLUTK321/img.png)

```sql
select * from user Full Outer Join post
on user.id = post.userId
```

- 대부분의 DB는 FULL OUTER JOIN을 지원하지 않는다.

간접적인 방식

```sql
(select * from user left join post on user.id = post.userid))
UNION
(select * from user right join post on user.id = post.userid))
```

- left 조인한 테이블과 right 조인한 테이블을 UNION으로 합집합 해주면 된다.
- UNION은 DISTICT가 자동 포함이라 중복 제거를 따로 할 필요가 없다.

## UNION

- Union은 여러 개의 Select 문의 결과를 하나의 테이블이나 결과 집합으로 표현할 때 사용
- 이때 각각의 SELECT 문으로 선택된 필드의 개수와 타입은 모두 같아야 하며, 필드의 순서 또한 같아야 한다.
- 기본 집합 쿼리에는 (DISTINCT) 중복 제거가 자동 포함되어있다.

## UNION ALL

- 중복되는 레코드까지 모두 출력하고 싶다면 UNION ALL 키워드를 사용하면 된다.

## Exclusive left join

- 어느 특정 테이블에 있는 레코드만 가져오는 것

![image.png](https://blog.kakaocdn.net/dn/cp4kRC/btsJarm8vlr/l51b8QgzykHKMPxXC9PSvK/img.png)

- Exclusive join은 만약 테이블 두개를 join 한다면 둘 중 한가지 테이블에만 있는 데이터를 가져온다.
- 다른 Join 들과는 다르게 별도의 Exclusive join 함수가 있는것은 아니고, 기존의 left join과 where 절의 조건을 함께 사용하여 만드는 Join 문법이다.

```sql
select * from table1 A left join table2 B
on A.id_seq = B.id_seq
where B.id_seq is Null
// 즉 join 처리 되지 않는 a 레코드들을 모두 출력
```

## Self Join

- 말 그대로 자기 자신을 조인한 것

```sql
select e.name as 사원, m.empname as 상관
from employee e, employee m
where e.manager = m.empno;
```

# Join에서 중복된 레코드 제거하기

## Distinct 사용

- mysql에서 지원하는 distince 문법을 사용하면 된다.
- 매우 쉬운 방법이지만 레코드 수가 많은 경우 성능이 느리다는 단점이 있다.

```sql
SELECT DISTINCT person.id, person.name, job.job_name
from person inner join job
on person.name = join.person_name;
```

## Join 전에 중복을 제거하기

- 위에 Select Distinct는 간단하지만 성능이 느리다.
- 중복이 없도록 테이블을 설계하는것이 좋지만, 성능을 위해서 Join 전에 중복을 제거하는 작업을 해주는것이 좋다.

```sql
select A.name, A.countryCode
from city A
left join (select distince name, Code from country) as B
on A.countrycode = B.code;
// 조인할 테이블에 먼저 distince로 중복을 제거한 뒤
// select 문을 서브 쿼리로 불러와 임시 테이블로 만든 뒤 조인한다.
```