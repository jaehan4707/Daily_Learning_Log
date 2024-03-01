# MVI

### Model

----
> UI에 반영될 상태를 의미한다.
주의할 점은 MVP와 MVVM의 모델과는 다른 의미라는 점.
>

### View

-----
> UI 그자체. View, Activity, Fragment, Compose가 될 수 있다.
>

### Intent

------
> 사용자 액션 및 시스템 이벤트에 따른 결과
>


![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/8c4d988e-3fe6-4e42-85d4-85a8843c26cc/Untitled.png?id=34ce2001-7260-4388-81fc-9342a5c93a6a&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1709373600000&signature=8PkxPupqAFo_sj1xP7LIN007PpKl5Fe9uLNBRGBupFw&downloadName=Untitled.png)

MVI는 UDF 구조이다.

사용자와의 상호작용으로 인해 새로운 Intent로 변경되고, 해당 Intent로 부터 새로운 Model을 만들어 View를 갱신한다.

어떻게 보면 MVVM과 크게 다르지 않다고 생각한다.

MVVM도 사용자와의 상호작용, 데이터 변경으로 인해 Model이 변경되고, 그러한 변경사항을 VM이 View에 바인딩해서 UI를 갱신하는 구조이기 때문이다.

MVI 패턴에서는 이벤트의 동시성 문제를 해결하기 위해

Channel을 도입한다.

Channel을 통해 이벤트를 순차적으로 처리하고, 이로 인해 스레드 안정성이 보장된다.


## Reducer

-----

```kotlin
현재의 상태와 전달받은 이벤트를 바탕으로 새로운 상태를 만드는것을 말한다.
Reducer = (State,Event) -> State
```

```kotlin
val state = events.receiveAsFlow()
	.runningFold(State(), ::reduceState)
	.stateIn(viewModelScope, SharingStarted.Eagerly, State())
```

이러한 상태 정의를 통해서 이벤트 채널로부터 상태를 변경하기 때문에 외부에서 상태를 변경할 수 있는 요인은 없다.

즉 상태 관리를 한 곳에서 할 수 있게 되면서, 동시성 문제도 해결할 수 있다.

## MVI 장단점 정리

---

### 장점

- 상태 관리가 쉽다.
- 데이터가 단방향으로 흐른다.
- 스레드 안정성을 보장한다.
- 디버깅 및 테스트가 쉽다

### 단점

- 러닝커브가 가파르다
- 보일러플레이트 코드가 양산된다
- 작은 변경도 Intent로 처리해야 한다.
- Intent, State, Side Effect 등 모든 상태에 대한 객체를 생성해야 하므로
  파일 및 메모리 관리에 유의해야 한다.

위에서도 말한것처럼 작은 변경에도 Intent로 처리해야 하는 단점이 있다.

그리고 우리는 상태를 변경할 필요가 없는 이벤트가 발생할 수 도 있다.

Logging, 토스트 노출, Navigation등이 있는데, 이는 상태를 변경할 필요가 없는 이벤트이기 때문에 Side Effets라는 개념을 써서 이를 처리한다.

즉 이벤트 채널을 하나 더 생성하는 것이다.

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/686cc1ae-a6bc-417d-b392-fd7bcb6f9776/Untitled.png?id=beab4987-5664-490d-8c15-cd21f67d565d&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1709373600000&signature=yb-8yM6Zx5Zymvdg9cahvtbPhixswYIcucTxOD0Lr2w&downloadName=Untitled.png)



안드로이드 앱을 개발하는 일은 복잡한 작업이지만, 상태를 관리한다고 생각할 수 있다.

사용자들에게 전달하고싶은 정보, 오류사항 등 화면에 표시되는 모든것은 앱의 상태이다.

앱에서 발생하는 문제는 앱의 상태가 의도대로 처리되지 않아서 생기는 문제가 대부분이다.

ex) 로딩중을 표현하고 싶은 상태와 로딩 후 리스트를 표시해야하는 상태가 충돌하는 경우

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/74337a53-f236-456d-ae7b-9f49828217c1/Untitled.png?id=964688bf-18bf-4c86-9cf8-a0b09944a91b&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1709373600000&signature=L2vnU21Z8TdLz2yjGdqREcZ69g2sE0K7yFAtfNWmsrI&downloadName=Untitled.png)

### 앱의 상태 충돌이 일어나는 이유

1. 분산된 데이터

View,ViewModel,Model에 분산되어 각각  관리될때, 상태의 충돌이 발생할 수 있다.

1. 복잡한 데이터 흐름

네트워크 통신등 여러 비동기적인 작업이 섞여서 서로 앱의 상태를 변경하려고 시도하면 상태의 충돌이 발생할 수 있다.

MVI

상태의 충돌을 피하고, 데이터 흐름 추적을 쉽게 한다.

어떻게?

상태의 충돌을 피하는것은 불변 객체를 사용

데이터의 흐름은 UDF로 설계

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/da89773f-cf0f-4809-ac0c-046a4aa7e272/Untitled.png?id=d95db47a-c3a9-4718-b086-ff2d74df3fc1&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1709373600000&signature=5yLJwzlRfxkMDT7SW9XJ2KOzm7ZXoVDFLRcLzMaU1qI&downloadName=Untitled.png)

단방향으로 흐르고 있다.

유저의 액션이 intent가 되고, intent에 의해 model이 변경되고, 변경된 view를 사용자에게 제공할 수 있다.

intent : 의도

→ model을 변경하는 트리거 (event) 모델의 입력으로 제공

model : 앱 상태

view : 화면에 상태에 맞는 view를 렌더링한다.

즉 MVI는

```kotlin
의도를(Intent)를 상태(Model)로 만들어 표시하는것(View)
```

다른 디자인 패턴의 Controller, Presenter, VM은 앱의 주요 로직을 처리하는 컴포넌트이지만, MVI의 I는  앱의 상태를 변화하려는 의도, → 구조적인 컴포넌트가 아니다.

즉 MVI는 안드로이드 앱을 어떤 구조로 구상한다는 접근보다는 , 앱의 상태와 데이터 흐름을 어떻게 다룰까에 대한 접근방법이다.

MVI에서

Intent는 의도를 담고 있고,

View는 Model(상태)에 따라 표시만 한다.

따라서 우리는 Model을 어떻게 설계하느냐에 따라서 MVI를 구현할 수 있을것이다.

# 참고

https://www.youtube.com/watch?v=_ePUpzECd8c

https://www.charlezz.com/?p=46365