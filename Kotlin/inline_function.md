# Inline Function

고차 함수와 함수값을 사용하면 함수가 객체로 표현되기 때문에 성능 차원에서 부가 비용이 발생한다.

이러한 부가 비용을 줄일수 있는 해법이 Inline 기법이다.

```kotlin
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

```kotlin
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

람다식을 파라미터로 받아서 사용하는 repeatLine을 호출하는 testInline을 주목해보자.

(Function1).null.Instance가 추가된 것을 확인할 수 있다.

추가된 것은 kotlin의 람다가 FunctionN으로 변환되는데, 파라미터가 1개이기 때문에 위와 같은 클래스로 변환되었다.

즉 repaeLine을 호출할 때마다 Function1이라는 객체가 생성되고, 이는 불필요한 객체를 생성하기에 부가적인 비용이 발생된다.

이를 해결하기 위해 Kotlin에서는 Inline이라는 키워드를 제공한다.

말 그대로 작성한 람다식의 내용을 익명객체가 아닌 코드 형태로 그대로 들어가게 된다.

```kotlin
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

```kotlin
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

repeatInLine을 호출하는 testInLine에서 변화한것을 볼 수 있을 것이다.

매번 람다가 익명클래스로 변환되는 과정이 람다의 내용이 그대로 코드로 삽입되는것을 확인할 수 있다.

### 그렇다면 모든 고차함수에 inline을 붙이는것이 효율적일까?

```kotlin
그렇지만도 않다.
왜냐하면 inline 키워드를 붙이면 바이트코드의 양이 늘어난다.
이러한 경우 오히려 성능이 악화될 우려가 있다.
따라서 대부분의 kotlin 라이브러리의 inline 함수는 1~3줄로 구성되어 있다.
```

https://leveloper.tistory.com/171

https://munseong.dev/kotlin/inlinefunction/

https://effortguy.tistory.com/293