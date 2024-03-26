# inline function

고차 함수와 함숫값을 사용하면 함수가 객체로 표현되기 때문에 성능 차원에서 부가 비용이 발생한다.

이러한 부가 비용을 줄일 수 있는 해법이 Inline 기법이다.

```
fun testInLine() {
    val time = 5
    repeatInline(time) { println("inlie : $it") }
}

fun repeatInline(time: Int, action: (Int) -> Unit) {
    for (i in 0 until time) {
        action(i)
    }
}

fun main() {
    testInLine()
}
```

코틀린 코드를 자바로 디컴파일해보면 아래와 같은 결과를 얻을 수 있다.

```
public final class SolutionKt {
   public static final void testInLine() {
      int time = 5;
      repeatInline(time, (Function1)null.INSTANCE);
   }

   public static final void repeatInline(int time, @NotNull Function1 action) {
      Intrinsics.checkNotNullParameter(action, "action");
      int i = 0;

      for(int var3 = time; i < var3; ++i) {
         action.invoke(i);
      }

   }
}

```

람다식을 파라미터로 받아서 사용하는 **repeatLine**을 호출하는 **testInline**을 주목해 보자.

**(Function1). null.Instance**가 추가된 것을 확인할 수 있다.

kotlin의 람다가 FunctionN으로 변환되는데, 파라미터가 1개이기 때문에 위와 같은 클래스로 변환되었다.

즉 repaeLine을 호출할 때마다 Function1이라는 객체가 생성되고, 이는 **불필요한 객체를 생성하기에 부가적인 비용이 발생**된다.

이를 해결하기 위해 Kotlin에서는 **Inline이라는 키워드를 제공**한다.

말 그대로 작성한 람다식의 내용을 **익명객체가 아닌 코드 형태 그대로 들어가게 된다**.

```
fun testInLine() {
    val time = 5
    repeatInline(time) { println("inlie : $it") }
}

inline fun repeatInline(time: Int, action: (Int) -> Unit) {
    for (i in 0 until time) {
        action(i)
    }
}
```

```
public final class SolutionKt {
   public static final void testInLine() {
      int time = 5;
      int $i$f$repeatInline = false;
      int i$iv = 0;

      for(byte var3 = time; i$iv < var3; ++i$iv) {
         int var5 = false;
         String var6 = "inlie : " + i$iv;
         System.out.println(var6);
      }

   }

   public static final void repeatInline(int time, @NotNull Function1 action) {
      int $i$f$repeatInline = 0;
      Intrinsics.checkNotNullParameter(action, "action");
      int i = 0;

      for(int var4 = time; i < var4; ++i) {
         action.invoke(i);
      }

   }
}
```

repeatInLine을 호출하는 testInLine에서 매번 람다가 익명클래스로 변환되는 과정이

람다의 내용이 그대로 코드로 삽입되는 것을 확인할 수 있다.

Inline Function의 람다식은 **다른 함수의 파라미터로 전달하는 것이 불가능**합니다.

```
fun testInLine() {
    val time = 5
    repeatInline(time) { println("inlie : $it") }
}

inline fun repeatInline(time: Int, action: (Int) -> Unit) {
    for (i in 0 until time) {
        action(i)
        repeatNoInline(i,action)
    }
}

fun repeatNoInline(num: Int, action: (Int) -> Unit) {
    action.invoke(num)
}
```

다음과 같은 repeatNoInline라는 함수에서 인자로 받은 action을 inline에서 넘겨줘야 하는 상황이 필요할 수 있습니다.

![](https://blog.kakaocdn.net/dn/bTuRvk/btsF7RH5ZEc/yFg4IGjFlLdemujFjYTBMk/img.png)

inline 함수는 람다식을 객체로 만들지 않고, 본문에 그대로 작성됩니다.

따라서 repeatNoInline에 파라미터로 넘길 **action 객체가 없기 때문에 에러**가 발생합니다.

이러한 문제점을 해결하기 위해서 내가 전달한 람다식은 inline으로 처리하지 않을 거야!라고 명시하는 키워드가 바로 **noinline**입니다.

말 그대로 repeatNoInline에서 파라미터로 받은 action앞에 noinline을 명시해 주면 됩니다.

```
fun testInLine() {
    val time = 5
    repeatInline(time) { println("inlie : $it") }
}

inline fun repeatInline(time: Int, noinline action: (Int) -> Unit) {
    for (i in 0 until time) {
        action(i)
        repeatNoInline(i,action)
    }
}

fun repeatNoInline(num: Int, action: (Int) -> Unit) {
    action.invoke(num)
}
```

#### 모든 고차함수 inline 키워드는 효율적일까?

사실은 그렇지만도 않습니다.

코틀린은 보통 inline을 지원하고, 강력한 성능을 자랑하고 있습니다.

하지만 inline을 통한 퍼포먼스의 상승이 미비하다면 다음과 같은 정보를 사용자에게 알립니다.

![](https://blog.kakaocdn.net/dn/c40V8Q/btsF7TsmAoA/4sexRUbsYaDUKFbleCpK41/img.png)

실제로 inline function은 람다식을 객체로 전달이 아닌 바이트 코드가 본문 그대로 삽입되기 때문에

**바이트 코드의 양이 절대적으로 늘어나게 됩니다**.

이러한 경우 오히려 성능이 약화될 우려가 있습니다.

따라서 대부분의 kotlin 라이브러리의 inline 함수는 1~3줄로 구성되어 있고, 적은 양의 코드를 권장합니다.

---

## 참고

[https://leveloper.tistory.com/171](https://leveloper.tistory.com/171)

[https://munseong.dev/kotlin/inlinefunction/](https://munseong.dev/kotlin/inlinefunction/)

[https://effortguy.tistory.com/293](https://effortguy.tistory.com/293)