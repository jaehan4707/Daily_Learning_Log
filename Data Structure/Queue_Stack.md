# Stack & Queue
# Stack
![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F253AA138564FC93010)

```
Stack의 예는 테니스공을 넣는 보관함과 같다.

통 안에 테니스공이 순서대로 차곡차곡 쌓여있고,

공을 넣을때마다 공은 통 아래에서 순차적으로 하나씩 쌓여간다.

넣는 순서와 반대로, 통에서 꺼내지는데,

Stack도 이렇다.

이러한 Stack의 특징을 `LIFO` 라고 한다.
```

## 후입선출(LIFO)

Last in First out이라는 뜻인데,

가장 늦게 들어온 데이터가 먼저 나간다는 의미이다.

그렇기 때문에 Stack은 데이터를 한 방향으로만 삽입가능하고, 가장 위에 있는 원소를 `Top` 이라고 한다.

## 주요 메서드

- isEmpty()
    - 스택이 비어있는지에 대한 결과를 `boolean`으로 반환
- isFull()
    - 스택이 가득 차 있는지에 대한 결과를 `boolean`으로 반환
- push()
    - 스택에 새로운 원소를 삽입, 더이상 넣을 수 없다면 예외를 던짐.
- peek()
    - `Top` 에 있는 데이터를 읽어옴.
- pop()
    - top에 있는 데이터를 읽고, 데이터를 제거함.
- clear()
    - stack을 초기화 함.

---

# Queue

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F4xNC4%2Fbtq5pEMF0sO%2FHz54KOz8oU8QwR8uCqyIMK%2Fimg.png)

```
queue의 예로는 급식소나 식당에서의 웨이팅줄이 있다.
들어온 순서에 따라 일을 처리한다.
```

## 선입선출(FIFO)

First in First out이라는 뜻으로,

들어온 데이터에 순서대로 나간다는 뜻이다.

`Front` 로 정한곳에서 조회/삭제와 같은 연산이 일어나고, `Rear` 로 정한곳에서 삽입 연산이 발생한다.

## 주요 메서드

- isEmpty()
    - 큐가 비어있는지에 대한 결과를 `boolean`으로 반환
- isFull()
    - 큐가 가득 차있는지에 대한 결과를 `boolean`으로 반환
- enqueue()
    - `rear`에 새로운 원소를 삽입한다.
    - 추가에는 두가지 `add`와 `offer`이 있다.
    - `add`는 실패시 `에러`를 던지고, `offer`는 `boolean` 값을 반환한다.
- dequeue()
    - `front`가 가리키는 원소를 제거한다.
    - 삭제에는 `remove`, `poll` 이 있다.
    - `remove` 는 실패 시 `에러`를 던지고, `poll`은 실패시 `boolean` 값을 반환한다.
- peek()
    - `front` 에 위치한 데이터를 읽어온다.
- clear()
    - queue를 초기화함.

# Stack 직접 구현

```java
import java.util.LinkedList;
import java.util.List;

public interface Stack<T> {
    boolean isEmpty();

    boolean isFull();

    void push(T element);

    void clear();

    void pop();

    T peek();
}

public class StackImpl<T> implements Stack<T> {
    public List<T> s;
    private int limit;

    public StackImpl(int limit) {
        this.s = new LinkedList<>();
        this.limit = limit; //여기선 아마 생성자로 받아야 할 거 같음.
    }

    @Override
    public boolean isEmpty() {
        return this.s.isEmpty(); //비어있다면 true, 아니라면 false.
    }

    @Override
    public boolean isFull() {
        return this.s.size() == limit; //limit와 같다면 true, 아니라면 false,
    }

    @Override
    public void push(T element) {
        if (this.s.size() == limit) {
            System.out.println("[에러]:스택이 가득 찼습니다");
            return;

        }
        this.s.add(element);
    }

    @Override
    public void clear() {
        this.s.clear();
    }

    @Override
    public void pop() {
        if (this.s.isEmpty()) {
            System.out.println("[에러]:스택이 비어있습니다");
            return;
        }
        this.s.remove(s.size() - 1);
    }

    @Override
    public T peek() {
        if (this.s.isEmpty()) {
            System.out.println("[에러]:스택이 비어있습니다.");
            return null;
        }
        return this.s.get(this.s.size() - 1);
    }

}
```

# Queue 직접 구현

```java
import java.util.LinkedList;
import java.util.List;

public interface Queue<T>{
    boolean isEmpty();
    boolean isFull();
    void enqueue(T element);
    T dequeue();
    T peek();
    void clear();
}
public class QueueImpl<T> implements Queue<T> {

    private List<T> q;
    private int limit;

    public QueueImpl(int limit) {
        this.q = new LinkedList<>();
        this.limit = limit;
    }

    @Override
    public boolean isEmpty() {
        return this.q.isEmpty();
    }

    @Override
    public boolean isFull() {
        return this.q.size() == limit;
    }

    @Override
    public void enqueue(T element) {
        if (this.q.size() == limit) {
            System.out.println("[에러]:큐는 가득차있습니다");
            return;
        }
        q.add(element);
    }

    @Override
    public T dequeue() {
        if (this.q.size() == 0) {
            System.out.println("[에러]:큐는 비어있습니다");
            return null;
        }
        return q.remove(q.size() - 1);
    }

    @Override
    public T peek() {
        if (this.q.size() == 0) {
            System.out.println("[에러]:큐는 비어있습니다");
            return null;
        }
        return q.get(q.size() - 1);
    }

    @Override
    public void clear() {
        this.q.clear();
    }
}
```

# 질문 리스트

> 1. 스택만으로 큐를 구현할 수 있는가?
>

```
스택 A,B 2개를 사용해서 스택A 에서는 푸쉬만을 담당한다.
그러다가 pop이 발생하면 A에 있는 모든 원소를 pop한 순서대로 B에 push한다.
결과로 B에는 큐와 같이 넣은 순서대로 원소가 쌓일것이다.
A : 1, 2, 3 
B: 3, 2, 1
즉 두개의 stack으로 queue와 동일한 결과를 얻을 수 있는 B를 만들 수 있다.
```
----
> 2. 스택과 큐의 특성을 모두 사용하고 싶을때?
>
```text
deque라는 자료구조를 이용하면 된다.
deque는 양끝단에서 삭제,조회,삽입이 가능한 자료구조로,
stack과 queue의 특징을 모두 이용할 수 있다.
```