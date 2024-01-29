# UI Layer

공식문서에서 권장하는 Clean Architecture중 하나의 영역입니다.

![](https://blog.kakaocdn.net/dn/diHxKt/btsDSqGci65/apiF87jMNzsxfuuGkvxaz0/img.png)

UI 레이어에 대한 역할은 다음과 같습니다.

```
화면에 어플리케이션 데이터를 표시하고, 사용자 상호작용의 기본 지점으로의 역할을 수행하는것
```


말이 조금은 딱딱한 감이 있는데, 쉽게 말하면 **사용자가 상호작용할 수 있는 시각적인 화면을 그리는것입니다.**

여기서 상호작용은 많은 시나리오가 있지만, 대표적으로는 `버튼을 누르거나`, `스와이프` 하거나, `회전`하거나 이런 행동들이 있겠죠.

우리는 이러한 사용자들의 행동에 대해서 `변경사항`을 `UI에 업데이트` 되도록해야합니다.

그 외에도 `네트워크 응답`으로 인한 데이터가 변경되었을때도 UI에 업데이트를 해야합니다.

**즉 UI 레이어는 사용자 상호작용, 외부 입력에 반응해서 UI의 상태를 변화시키는 작업을 합니다.**


여기서 중요한 점은 UI는 `UI의 상태를 변화`시키는 작업만 할 뿐 **내부 로직을 알 지 못한다는 점**입니다.

즉 시키는 대로, 데이터를 받는 그대로의 정보를 UI에 표시합니다.


## UI Layer의 흐름
그렇다면 **UI 레이어에서의 흐름**을 알아봅시다.

#### 앱 데이터를 UI에서 사용할 수 있는 데이터로 변환한다.

UI 레이어는 Data 레이어에서 가져온 애플리케이션 상태를 시각적으로 보여주는 역할을 한다.

하지만 데이터 레이어에서 가져온 데이터는 UI에서 표시해야 하는 정보랑은 보통 다른 형태이다.

즉 모든 데이터를 UI에서 사용하지 않는다는 점이다.

그러기 때문에 UI는 가져온 데이터를 필요로 하는 형태로 가공을 한다.

예를 들면 뉴스 목록을 보여주는 화면에서

뉴스에 대한 제목과 사진, 날짜등등을 표시하는데 해당 뉴스에 대한 전체 정보를 해당 화면에서 굳이 가지고 있을 필요는 없다.

예시가 어색할 수도 있지만

**내가 말하고 싶은것은 UI 레이어에서는 Data 레이어에서 받은 정보를 그대로 사용하지 않고, 자신이 필요한 부분만 추출해서
사용하는 데이터로 변환을 한다는 점이다.**

#### 변환한 UI 데이터를 통해서 사용자에게 표시할 수 있는 UI 요소로 변환한다.

즉 데이터를 통해서 화면을 그리는 작업을 한다.

뉴스 목록에 대한 아이템 화면이 있다면 해당 화면에다가 적절한 데이터를 집어넣는 작업이다.



#### 사용자 입력 이벤트를 사용하고, 입력 이벤트의 결과를 필요에 따라 UI 데이터에 반영한다.

사용자의 상호작용(버튼 누르기, 사이드바 화면 클릭)에 따라서 화면을 반영하는데,

예를 들면 즐겨찾기 버튼을 누른다면 즐겨찾기 버튼이 활성화된다거나, 사이드바를 펼치면 사이드바가 보여지고,

토스트, 스낵바와 같이 이벤트의 결과를 UI에 반영한다.


## UI 레이어를 구현하는 방법

UI 레이어를 구현하기 전에 공식문서에서 강조한 기본 적인 내용은 UI state를 정의하는 방법이다.

![](https://blog.kakaocdn.net/dn/bvyyru/btsDQThOlwq/uvlkBxkm0Kgkewr5dRFKoK/img.png)

UI는 `UI elements`와 `UI state`로 구성되어 있다.

Ui Elements는 화면에 그려져 있는 `View`를 의미합니다.

UI State는 위에서 얘기했던 북마크의 여부등과 같이 앱에서 사용자에게 표시하는 정보를 의미합니다.
당연하게도 UI 상태가 변경되면 사용자에게 표시하는 정보가 변경된다는 뜻이기 때문에 변경사항이 즉시 UI에 반영됩니다.

```kotlin
data class BoardUiState(
    val spaceId: String = "",
    val boards: List<Board> = listOf(),
    val selectBoards: List<Board> = listOf(),
)
```

보드목록을 보여주는 화면에 대한 UiState에 코드이다.

공식문서에서는 기능 +UiState로 네이밍하는것을 권장하고 있습니다.

Uistate는 LiveData 또는 StateFlow와 같이 관찰 가능한 데이터를 사용합니다.

ViewModel에서 데이터를 직접 가져오지 않고, 변경 사항에 대해 대응하기 위해서입니다.

이렇게 관찰 가능한 데이터를 사용할 때는 Ui의 생명주기를 고려해줘야 하기 때문에

lifecycleScope나, repeatOnLifeCycle과 같이 수명주기를 관리해 주는 것이 필요합니다.



이렇게 정의된 Ui State는 `불변성`을 원칙으로 합니다.

즉 UI에서 직접 수정할 수 없도록해서 UI는 UI 상태를 읽어서 UI 요소를 업데이트하는 한가지 역할에 집중할 수 있습니다.

흔히 사용하는 Activity나 Fragment에서 UI의 상태를 직접적으로 변경하는 코드는 작성하지 않고, UI의 상태를 받아서

UI에 반영하는 코드만을 작성하는것이 올바른 UI 레이어의 역할입니다.

Ui state는 val로 선언을 하고 불변성을 원칙으로 하기 때문에 UI layer에서 직접적으로 수정할 수 없습니다.

그러면 UI State는 어디서, 어떻게 관리를 해야 할까요?

공식문서에서는 UI state를 생성, 관리하고 로직을 포함하는 클래스를 State Holder라고 합니다.

대표적인 State Holder의 예는 ViewModel이 있습니다.

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FpImAV%2FbtsD4TBZbUs%2FHU8ooOiGQIBIV9gNyD6R0K%2Fimg.png)


위 그림은 앱 아키텍처에서 UDF의 작동 방식을 보여주는 다이어그램입니다.



흐름은 다음과 같습니다.

ViewModel이 UI에 사용될 Ui state를 보유하고, 노출합니다.
여기서 Ui state는 VIewModel에 의해 변환된 애플리케이션 데이터를 의미합니다.
UI가 ViewModel에 사용자 이벤트를 알립니다.
ViewModel이 사용자의 작업을 처리하고, State를 변화시킵니다.
이렇게 변화된 State를 UI가 반영해서 View를 변화시킵니다.
그림을 보면 알 수 있듯이 그림 속 화살표가 양방향이 아닌 한 방향으로 흘러가는 것을 알 수 있습니다.

앱 아키텍처에서는 UI state 관리를 위해서 UDF를 강조하고 있는 것을 알 수 있습니다.



UDF를 준수하면 얻는 이점은 무엇일까요?

데이터의 일관성을 보장
UI가 전달하는 정보는 Data source에서 제공받는 Data 하나입니다.
테스트가 용이하다.
상태가 분리되므로, UI와 별개로 테스트할 수 있다.
지금까진 UI layer와 UI State에 관한 내용이었다면 이제는 UI 이벤트에 대해 알아보겠습니다.

# UI Event
   UI 레이어에서 UI 또는 ViewModel이 처리해야 하는 작업
   대표적인 이벤트 유형으로는 사용자 이벤트가 있습니다.

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fbbm9nP%2FbtsD4sxP9m0%2Fk9Oh5WX3j5RflG4C6f7o9K%2Fimg.png)


위에서 언급했던 것처럼 State Holder인 ViewModel이 UI에 관한 로직을 처리합니다.


위 그림은 이벤트 처리를 위한 결정 트리입니다.



첫 번째 분기점은 이벤트가 어디서 발생했는가?입니다.

UI와 ViewModel로 나뉩니다.



ViewModel의 예로는 API 호출로 데이터를 Fetch 한 경우가 있습니다.

이러한 이벤트를 ViewModel에서 수행하고, 최종적으로 Ui State를 업데이트할 것입니다.

그러면 UI는 업데이트된 UI State를 UI에 반영하는 흐름으로 이어질 것입니다.

ViewModel -> UI



UI에서 발생하는 이벤트는 다음 분기점인 이벤트가 요구하는 것이 무엇인가에 따라서 또 나뉩니다.

이벤트로 인해 요구하는것이 비즈니스 로직인지, UI 로직인지에 따라 이벤트 처리를 위한 방식이 나뉩니다.



비즈니스 로직을 요구하는 경우입니다.

이러한 예로는 장바구니 담기 버튼을 클릭하는 경우가 있고,

viewModel에 비즈니스 로직을 위임하고, ViewModel은 비즈니스 로직을 수행하고, 결과로 UI State를 업데이트, UI에 반영이 됩니다.

UI -> VIewModel -> UI



UI 로직을 요구하는 경우입니다.

이러한 예로는 화면 전환, 토스트, 스낵바 출력등이 있고, 이벤트는 UI에서 직접적으로 처리합니다.

UI 단독 처리

여기서 주목할 점은 ViewModel 이벤트는 항상 UI State 업데이트로 이어진다.

비즈니스 로직이 있는 경우 비즈니스 로직이 항상 UI 로직보다 먼저 적용된다.(이 말은 곧 ViewModel -> UI로 흐른다)

세 가지 방식은 단방향으로 흐른다는 사실입니다.

-----

UI Layer의 역할에 따라 저도 Fragment나 Activity와 같이 UI에서의 코드 양을 줄이려는 노력을 많이 하고 있습니다.

Data Binding을 사용한다거나, Ui State, Event를 사용해 UI에서 상태를 변경하지 않고 , 변경된 상태를 반영하는 등..

UI layer의 역할과 UI state, event에 경계를 헷갈려서 공부하고 적었던 글인데 아직 많이 모자란 거 같네요..


읽어 주셔서 감사합니다.

-----

# 참고

https://developer.android.com/topic/architecture/ui-layer?hl=ko
https://developer.android.com/topic/architecture/ui-layer/events?hl=ko
https://medium.com/@apfhdznzl/android-ui-layer-ui-event-20a02b7bdada
https://todaycode.tistory.com/189