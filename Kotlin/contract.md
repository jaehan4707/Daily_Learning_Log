# Contract

1.3.60 버전부터 사용되었으며, 컴파일러가 이해할 수 있게 명시적으로 자신의 동작을 설명할 수 있게 한다.

컴파일러가 이해할 수 있게 라는 말이 조금은 헷갈릴 여지가 있다고 생각한다. 코드를 통해 살펴보자

회원가입을 구현하려고 할 때의 시나리오를 생각해 보자.

```
@JvmInline
value class Password(val password: String)

@JvmInline
value class Id(val id: String)

fun validate(id: Id?, password: Password?): Boolean {
    return id != null && password != null
}
fun signUp(id:Id?, password: Password?){
    if(validate(id,password)){
        println("Id : ${id.id} Password: ${password.password}")
    }
}
```

분명히 이런 경험이 있을 것이다.

어? 난 분명히 함수를 통해서 nullcheck를 해줬는데 왜 밑에서 스마트캐스트가 되지 않았지?

![](https://blog.kakaocdn.net/dn/BFDsz/btsGmAMz56N/PvwP0WfKJ6HSuikj1Po3R0/img.png)

위 코드도 id와 password의 nullcheck를 함수를 통해서 했지만, 이후 id와 password가 스마트캐스트 되지 않아 중복으로 널 체크를 해야 하는 상황이다.

이유는 간단하다.

우리는 코드를 작성하면서 validate의 반환값이 true면 id와 password가 null이 아니란 것을 알고 있다.

하지만 컴파일러는 알지 못하기 때문에 스마트캐스트가 되지 않아, 널 체크를 또 해줘야 하는 것이다.

이를 위한 간단한 해결방법으로 타입체크를 직접 하는 방법이 있다.

![](https://blog.kakaocdn.net/dn/Kquna/btsGlWP6I7U/Uk63Wckw3JAPKVDe6MmX31/img.png)

해당 방법은 컴파일타임에서 컴파일러가 if문의 내용에 따라 id와 password가 null이 아니라는 것을 알 수 있다.

하지만 이렇게 직접적인 타입 체크는 비즈니스 로직이 변경됨에 따라, 기능이 추가됨에 따라 함수로 분리를 하는 것이 훨씬 효율적이고, 가독성이 좋은 코드이기에 이렇게 해결하는 방법은 추천하지는 않는다.

이렇게 컴파일러에게 내가 원하는 결괏값이 전달되지 않아서 생긴 문제를 해결하기 위해 등장한 것이 contract이다.

```
@OptIn(ExperimentalContracts::class)
fun validate(id: Id?, password: Password?): Boolean {
    contract {
        returns(true) implies (id != null && password != null)
    }
    return id != null && password != null
}
```

contract는 아직 실험적인 기능이기 때문에 **@OptIn(ExperimentalContracts::class)** 어노테이션을 사용해야 한다.

contract 블록은 다음과 같이 동작한다.

id, password 모두 null이 아니라면 true가 반환될 것이라고 컴파일러에게 전달하는 것이다.

![](https://blog.kakaocdn.net/dn/bcOxLB/btsGmB5MCw1/CtuyaFNIbVkVwTcvoV0db0/img.png)

contract 블록을 통해 컴파일러가 validate의 반환값이 true일 경우 id와 password는 null이 아님을 알고 있기 때문에 smart cast가 가능하다.

## callsInPlace

```
fun callsInPlaceTest(action: () -> Unit) {
    action()
}

fun callsInPlace() {
    val number: Int
    callsInPlaceTest {
        number = sum(5)
    }
    println(number)
}

fun sum(time: Int): Int {
    var sum = 0
    repeat(time){
        sum++
    }
    return sum
}
```

해당 코드를 보면 전혀 어색하지 않아 보이지만, 컴파일타임에서 두 가지 오류가 발생한다.

![](https://blog.kakaocdn.net/dn/RBRtb/btsGmBLtt9r/QVsk7XVGQPjMc2vv1cPfn0/img.png)

**Captured values initialization is forbidden due to possible reassignment**

number의 재할당 가능성 때문에 오류가 발생한다라는 문구이다.

코드를 작성한 우리는 **callsInPlaceTest**의 파라미터인 action이 딱 한번 실행되어서, number의 값을 할당하는 것을 알고 있다.

하지만 컴파일러 입장에선 람다함수인 action이 몇 번 호출되는지 알 수 없다는 뜻이다.

그렇기에 val로 선언된 number가 여러 번 할당될 우려가 있기 때문에 컴파일타임에서 var로 바꾸라고 권장하는 것이다.

![](https://blog.kakaocdn.net/dn/ckxpwr/btsGmcSHp57/DQHRc5J2rWLJMs4w2G4kB0/img.png)

**Variable 'number' must be initialized**

number가 초기화되지 않았다는 의미이다.

두 번째 오류 역시 람다식의 action이 몇 번 호출되는지 알 수 없기 때문에 number의 할당 여부를 알지 못해서 생기는 오류이다.  
따라서 초기화하라고 권장하는 것이다.

이렇게 컴파일타임에서 람다 **함수의 호출 횟수를 명시적으로 컴파일러에게 알려주기 위해** 사용하는 것이 **callsInPlace**이다.

람다식의 호출 횟수를 정하는 유형은 다음과 같다.

-   UNKNOWN - 컴파일러의 기본 값
-   AT\_LEAST\_ONCE : 최소 1번 이상 실행
-   AT\_MOST\_ONCE : 최대 1번 실행 (0 or 1)
-   EXACTLY\_ONCE : 정확히 1번 실행

아래와 같은 코드를 통해 컴파일러에게 람다 함수는 한 번만 호출될 거야!라고 명시해 줄 수 있다.

```
@OptIn(ExperimentalContracts::class)
fun callsInPlaceTest(action: () -> Unit) {
    contract {
        callsInPlace(action, InvocationKind.EXACTLY_ONCE)
    }
    action()
}
```

---

contract는 아직까지 실험적인 기능이다.

컴파일러에게 명시적으로 결과를 가르쳐주는 contract를 통해 nullCheck를 중복으로 했던 코드, 스마트캐스트 때문에 스파게티코드로 작성했던 부분도 개선할 수 있을 것이란 생각이 들었다.

하지만 아직 실험적인 기능이라서 프로젝트에 적용하는 것이 조금은 위험하지 않을 것이란 생각이 들지만, 현재 코틀린의 버전이 1.9까지 올라갔기 때문에 걱정하지 않아도 될 것 같다.

# 참고

[https://oguzhanaslann.medium.com/exploring-the-power-of-kotlin-contracts-for-better-code-quality-80bb279d7d2d](https://oguzhanaslann.medium.com/exploring-the-power-of-kotlin-contracts-for-better-code-quality-80bb279d7d2d)

[https://pspdfkit.com/blog/2018/kotlin-contracts/](https://pspdfkit.com/blog/2018/kotlin-contracts/)

[https://medium.com/harrythegreat/kotlin-contracts-문법-쉽게-배워보기-9ffdc399aa75](https://medium.com/harrythegreat/kotlin-contracts-문법-쉽게-배워보기-9ffdc399aa75)