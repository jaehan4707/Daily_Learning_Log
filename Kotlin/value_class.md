## 등장 배경

---

원시타입과 문자열을 wrapping 하기 위해 사용했던 데이터 클래스와 클래스는 추가적인 객체 생성과 메모리 할당이 필요했다.

하지만 value class가 추가되면서, 객체 생성 및 메모리 할당을 최소화하고 성능을 개선할 수 있게 되었다.

kotlin 1.5에서는 inline class로 사용되었지만, 현재는 Deperecated 되었다.

현재 1.6에서는 value class로 사용하고 있다.

## 정의

---

> primitive 타입이나, 다른 value class를 감사는 wrapper 클래스로서의 역할을 할 수 있다.

## 특징

---

#### **상속이 불가능하다**

```
@JvmInline
value class Email(private val email:String)
value class SchoolEmail(private val school : String) : Email
```

![](https://blog.kakaocdn.net/dn/Gn3uV/btsGfiz0KP5/Zom7eXtOOELx4ChNarCPQk/img.png)

위 사진과 같이 value class는 기본적으로 final이기에, 상속이 불가능하다.

#### **\=== 비교 연산이 불가능하다**

```
val knu  = Email("www.knu.ac.kr")
val gnu = Email("www.gnu.ac.kr")
if(knu===gnu){
      
}
```

![](https://blog.kakaocdn.net/dn/c4x4di/btsGis2czmg/DCKIfsrD4Y8JKKx21gtxVK/img.png)

당연하게도 value class는 컴파일 타입에 **객체 타입**이 아닌 **primitive type**으로 변경되기 때문에 불가능하다.

#### **자동 메서드 구현**

```
public final class Amount {
    private final int amount;
    /*
    
    */
    
    public String toString() {
      return toString-impl(this.amount);
    }

    public int hashCode() {
      return hashCode-impl(this.amount);
    }

    public boolean equals(Object var1) {
      return equals-impl(this.amount, var1);
    }
 }
```

toString(), equals(), hashcode() 메서드가 자동으로 구현된다.

#### **하나의 프로퍼티만을 가진다**

![](https://blog.kakaocdn.net/dn/b1gYwr/btsGfzhme9x/1QESoQk22VJx4tBaxNJPR0/img.png)

두 개 이상의 파라미터는 허용하지 않는다. 객체가 아닌 value로 나타내기 위해선 하나의 프로터티만을 가져야 한다.

이제 value class를 사용해야 하는 이유를 알아보자

#### **타입 안정성**

```
data class Food(
  val name: String,
  val price: Int,
  val amount: Int,
)
```

data class를 통해 새로운 객체를 만들려고 할 때, 파라미터를 넣는 과정에서 실수가 발생할 수 있다.

```
val ramenName = "ramen"
val ramenPrice = 5000
val ramenAMount = 100
val ramen = Food(
    ramenName,
    ramenAMount,
    ramenPrice,
)
```

다음과 같이 price와 amount는 동일한 타입이기 때문에, 실수로 넘어갈 가능성이 있다.

컴파일 타임에서 오류를 잡아내지 못한다.

value class가 등장하기 전에는 wrapper class로 특정 type에 대한 대처를 했었다.

```
class Price(val price : Int)
class Amount(val amount : Int)
data class Food(
    val name: String,
    val price: Price,
    val amount: Amount,
)
```

이렇게 wrapper class로 특정 Type을 구현해, 어떻게 보면 컴파일 타임에서 오류가 발생하는 것을 확인할 수 있고, 실수를 줄일 수 있다.

하지만 해당 방법은 추가적인 힙 메모리 할당으로 런타임 오버헤드가 발생하게 된다.

디컴파일의 결과를 확인해 보자

value class가 아닌 일반 class로 wrapper class를 구현했을 때의 디컴파일 결과이다.

![](https://blog.kakaocdn.net/dn/BgP6k/btsGhnAAzSU/1tvNmJJdB2dgEvaXe4fIy1/img.png)

Price와 Amount **객체를 생성**해서, **추가적인 메모리 할당을** 확인할 수 있다.

그렇다면 value class의 디컴파일 결과는 어떨까?

![](https://blog.kakaocdn.net/dn/bNNElu/btsGfyJvNwA/0DndVS9aIS0kFPcYgad5VK/img.png)

Value class로 바꿨을 뿐인데, price와 amount가 클래스가 아닌 int 값으로 직접 저장되는 것을 확인할 수 있다.

추가적인 객체의 생성도 없고, 추가적인 메모리의 사용이 일어나지 않는다.

## 맹글링(Mangling)

---

```
맹글링이란, 컴파일러가 이름 충돌을 방지하기 위해 함수나 변수의 이름을 변형하는 과정을 의미한다.
```

![](https://blog.kakaocdn.net/dn/dtYPFG/btsGff4merU/0FQE5VQarX57Q9kVBySqcK/img.png)

객체지향의 다형성 중 오버로드에 의해 함수의 이름은 중복되지만, 다른 파라미터, 다른 반환 타입을 가질 수 있다.

이럴 경우 이름이 중복되는 현상을 방지하기 위해, 컴파일러는 맹글링의 과정을 거친다.

![](https://blog.kakaocdn.net/dn/S4LcJ/btsGiebZhwD/R4TtQfyb2OznewpyiC49g0/img.png)

함수 이름에 해시코드를 추가해, 충돌 문제를 해결한다.

## Data class vs Value Class

---

1.  Value class는 equals, toString, hashCode가 자동생성되지만, Data class는 추가적으로 copy, componentN이 생성된다.
2.  Data class는 ===연산을 지원하는 반면, Value class는 === 연산을 지원하지 않는다.
3.  Data class는 val, var 프로터티를 지원하지만, Value class는 val 프로퍼티만을 지원해 항상 불변성을 보장할 수 있다.
4.  Data class는 프로퍼티 개수에 제한이 없지만, Value class는 “값”을 나타내기 때문에 하나의 프로퍼티만을 가질 수 있다.

---

객체지향에서 모든 원시값과 문자열을 포장하는 것을 강조했고, 이를 통해 가독성과 타입 안전성을 얻을 수 있는 것을 알게 되었다.

Wrapper 클래스는 추가적인 heap 영역을 필요로 했고, 만약 primitive type을 Wrapping 했다면, 런타임 성능에 오버헤드가 컸다.

이를 value class를 통해 해결할 수 있다는 것을 배울 수 있었고, 기존 프로젝트에서 직접 구현한 DP, PX 클래스에서 가독성과 타입 안정성을 위해 Wrapping을 했지만, 잘못된 wrapping을 했다는 것을 알게 되었던 것 같다.

추후 수정을 해봐야겠다.

```
data class Dp(val dpVal: Float) {
    operator fun plus(dpValue: Dp): Dp {
        return Dp(dpVal + dpValue.dpVal)
    }

    operator fun minus(dpValue: Dp): Dp {
        return Dp(dpVal - dpValue.dpVal)
    }

    operator fun times(dpValue: Dp): Dp {
        return Dp(dpVal * dpValue.dpVal)
    }

    operator fun div(dpValue: Dp): Dp {
        return Dp(dpVal / dpValue.dpVal)
    }
    /*
    ..
    */
}
```

# 참고

[https://kotlinlang.org/docs/inline-classes.html](https://kotlinlang.org/docs/inline-classes.html)

[https://itstory1592.tistory.com/113](https://itstory1592.tistory.com/113)

[https://kotlinworld.com/535](https://kotlinworld.com/535)

[https://jinseong-dev.tistory.com/36](https://jinseong-dev.tistory.com/36)