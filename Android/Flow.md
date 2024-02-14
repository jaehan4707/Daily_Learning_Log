# Flow

물을 긷는 상황을 예로 해서,

A가 물을 가져와야 할때 일반적인 과정은 호수에 가서, 물을 긷고 다시 돌아오는것이다.

하지만 호수를 갔을대 호수가 물이 없는 상황이 생길 수 있다.

이러면 호수를 가는 시간도 낭비되고 다른 호수를 찾아야 하는 번거로움이 생긴다.

이러한 번거로움을 해결하기 위해 일종의 `인프라`를 구축하는것이 편하다.

그래서 호수에 파이프를 설치했다.

A는 물이 필요하다면 파이프를 열고 물을 받는다.

이러한 과정에서 호수에 물이 없다면 아쉬운거지 손해는 아니다.

즉 호수가 말랐는지에 대한 여부를 신경쓸 필요가 없다.

이런 예를 `Android`에서 확인해보자.

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/ba0a010a-65ec-41e0-ae69-d48f0e4c9eec/Untitled.png?id=a9d3d239-555d-49d8-acaf-7366529112e7&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708041600000&signature=VaE0O7uBU6m6rThEJTCyGdcOh6EojFg3j83pzYUqZtg&downloadName=Untitled.png)

뷰가 시작되면 뷰는 ViewModel에 데이터를 요청하고, ViewModel은 Data layer에 데이터를 요청할 것이다.

suspend 함수로 손쉽게 해결할 수 있지만 이러한 방법도 비효율적인 방법이다.

즉 인프라에 투자를 해야한다.

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/7dc488de-f008-4af9-879b-1adeb9536468/Untitled.png?id=87c41bc1-69fb-4b42-97fb-d749d075fba3&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708012800000&signature=h_wYv-9nlmqdEHAZXcUNOt5vTHR3yK631hq88b7Wg84&downloadName=Untitled.png)

데이터를 요청하는 대신 관찰하는것이다.(데이터를 관찰하는것은 수도관을 설치하는것과 동일하다)

관을 설치하면 데이터 소스 업데이트가 자동으로 View 까지 전달이 된다.

이러한 패턴을 사용하는 시스템을 반응형(Reactor)라고 한다.

관찰하는 쪽(View)가 관찰 대상(Data)의  변화에 자동으로 반응하기 때문이다.

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/1cee7c48-ba50-4611-8063-66fa5af3b778/Untitled.png?id=a2f35192-308a-4fc9-a39b-7653fe3c09a2&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708012800000&signature=wUE9gBOhSvq4ywTffFJOdL_-awBZhk923xVwgRRKPpE&downloadName=Untitled.png)

안드로이드 아키텍처에서도 강조하듯이 데이터는 UDF 패턴을 따르는것이 좋다.

어디서 오류가 생겼고, 추적하기가 쉽기 때문이다.

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/0f654831-4cf7-497c-9302-9e1746ca5786/Untitled.png?id=42db6b72-81ac-43c0-8de2-5ad24c6f579b&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708012800000&signature=inmjS5kxTYbZW9tfaksJ4HnBaQV56AmhWsnO8sQmcOU&downloadName=Untitled.png)

즉 데이터 방향을 하나로 흐르게하고, 수도관 역할을 하는  인프라를 구현해서,

데이터 스트림을 결합하고 변환하는것이 좋다.

Flow는 어떠한 데이터 타입이 될 수 있다.

예를 들어 사용자 데이터나 UI State가 될 수 있다.

몇가지 단어가 등장하는데

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/20d5c654-4ec1-47dc-99e5-59da3b5bbe31/Untitled.png?id=13241444-8fb2-4e4f-aa02-c57bcd13dda6&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708012800000&signature=HyCNSuSRYv1MZkT-_PPd2ppc22Ai5y-LU3lJ2POkBiU&downloadName=Untitled.png)

### Producer

- 데이터를 Flow에 입력(Emit)한다.
- Data source나 Repository가 이에 해당한다.

### Consumer

- Flow에서 데이터를 수집(Collect)한다.
- 화면에 최종적으로 표시하는 UI Layer가 이에 해당한다.

Flow의 좋은점은 데이터를 다룰 때 사용하는 대부분의 라이브러리가 코루틴과 Flows에 이미 통합되어 있다.

따라서 Flow를 직접 만들 필요가 없다.

자주 사용하는 라이브러리는

1. DataStore
2. Retrofit
3. Room
4. WorkManager

이 라이브러리들은  댐 역할을 한다.

라이브러리가 Flows를 사용하여 데이터를 제공하고,

개발자는 데이터 생성 방법을 모른채 파이프에 연결하면 데이터를 제공받을 수 있다.(호수에서 호수 물이 있는지 없는지와 같음)

Flow를 직접 만들어야한다면 여러가지 옵션이 있다.

그 중 하나가

### Flow builder

Flow builder는 suspend 블록을 매개변수로 받는다. → suspend 함수를 호출할 수 있다.

왜냐하면 Flow는 코루틴의 컨텍스트에서 실행되기 때문이다.

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/a7f5aa90-4a01-43f1-ac15-e37065de19e1/Untitled.png?id=eedad1e4-f0b7-4081-a9d3-a41c28f83e2f&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708012800000&signature=to3CZQPExi5cSrYKs8F20fj1OhT3Va_XkL6NMNRUX2k&downloadName=Untitled.png)

관찰자가 떠나고 항목 수집을 중단하면 Flow도 중단한다.

Flow builder에 전달된 suspend 블록을 생산자 블록이라고 한다.

생성자와 소비자간의 계층에서 요구사항에 맞게 데이터 스트림을 수정할 수 있고,

변환하려면 중간연산자가 필요하다.

ex) map

```java
val userMessages : Flow<MessageUiModel> = 
	userMessageDataSource.latestMessages
		.map{ userMessages->
				userMessages.toUiModel()
		}
```

데이터를 UI에서 사용할 수 있는 uiModel로 바꾸고, 이는 더욱 우수한 추상화 방법을 제공한다.

그 외에도 filter를 통해서 특정 정보를 플로우로 가져올 수 있다.

오류 처리 방법

Flow.catch

- 항목을 처리하는 동안 발생할만한 예외를 찾아낸다.

Upstream Flow

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/0a247c25-a766-4981-9c97-d77d96a2b609/Untitled.png?id=fb9a333a-a01e-4bab-8e10-54e23efb314f&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708012800000&signature=i9EpzZKekGIqygALVWe2Jea8pb1eMQ6WeQ_Xn1sumrA&downloadName=Untitled.png)

프로듀소 블록에서 생성한 플로우이고, 현재 연산자 전에 이들을 호출한다.

DownStream Flow

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/e9acd941-d609-467b-9c39-b5e63e5a073b/Untitled.png?id=41e7375f-8dc5-4b25-a394-ed624eea64b4&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708012800000&signature=e1gzPp1oBTk3uMTqVbJfUVijdZSodFMcB9jQ2MklZ4Q&downloadName=Untitled.png)

현재 연산자 이후에 발생하는 모든것을 다운스트림 플로우라고한다.

# Observing Flows

```java
일반적으로 Flow 수집은 UI 계층에서 일어난다.
화면에서 데이터를 표시할 위치이기 때문이다.
```

```java
userMessages.collect{ messages->
	listAdapter.submitList(messages)
}
```

- collect는 새로운 값이 생길 때 마다 호출되는 함수를 매개변수로 받는다.
- 당연하게도 suspend 함수이기 때문에 코루틴 내에서 호출할 수 있다.

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/83c7c2f9-a7d1-43fe-9d51-4e7d9b43e82e/Untitled.png?id=ff349156-cf8c-4d3c-b14c-b0e6ff25e688&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708012800000&signature=kqfScXDzotbpF0g8rND1I-ngm7bt1tPng6_GjO151K8&downloadName=Untitled.png)

collect를 호출할 때마다 새로운 Flow가 생성된다.

생산자 블록은 정해진 간격에 따라 API에서 메시지를 새로고침한다.

이러한 Flow를 코루틴에서 `cold flow`라고 한다.

### cold Flow

```java
필요에 따라 생성되고 관찰되는 중에만 데이터를 전송한다.
```

## Flows in Android UI

고려해야할점 2가지

1. 앱이 백그라운드에 있을 때 리소스를 낭비하면 안된다.
2. 구성변경과 관련이 있다.

액티비티에서 데이터를 관찰할 때 얼마나 오래 플로에서 수집해야할까?

UI가 적절히 동작하고, 화면에 UI가 표시되지 않을때는 플로우에서 수집을 중단해야 한다.

예를 들어 화장실에서 물을 사용할 때 사용하지 않을 경우 수도꼭지를 틀면 안된다.

이유는 물을 낭비해서는 안되니까

그와 동일하게 UI에서도 데이터가 필요하지 않는다면 수집을 중단해야 한다. (리소스 낭비)

여러가지 옵션이 있는데,

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/2f7d33a7-baa7-4a47-acec-a3c2cdf27350/Untitled.png?id=153d850e-8256-4f30-bb51-b5a432cb78aa&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708012800000&signature=Uymc9p1xAYvGGre7GNaROVNI81rBIoPxH1vM15aI2zA&downloadName=Untitled.png)

모든 옵션이 UI 수명 주기와 연관이 있다.

`asLiveData` 연산자는 플로우를 LiveData로 변환해서 UI가 화면에 표시되는 동안에만 관찰한다.

또 다른 방법으론

UI 계층에서 플로우를 수집할 때 `repeatOnLifeCycle` 을 사용하는것을 권장한다.

이는 Lifecycle.State를 매개변수로 받는 suspend 함수이다.

이는 수명주기를 인식해서 수명주기가 해당 상태에 도달하면 블록을 전달할 새 코루틴이 자동으로 시작된다.

만약 수명주기가 그 상태 아래로 떨어지면 진행중인 코루틴이 취소된다.

repeatOnLifeCycle은 suspend 함수이기 때문에 UI에서 보통 lifecycleScope.launch에서 호출한다,.

repeatOnLifeCycle은 UI 수명 주기를 자동으로 고려한다.

특히 repeatOnLifeCycle을 호출한 코루틴은 수명주기가 destoryed 때까지 실행을 다시 하지 않는다.

따라서 여러 플로우를 수집해야 할 경우 repeatOnLifeCycle 블록안에서 launch 를 사용해 collect 해라.

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/980b627a-08ec-49c3-9880-7e71caa1be98/Untitled.png?id=38cbe53f-bf45-4c2d-8d62-5a70b1e0f182&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708012800000&signature=6A5BTrWtc1rrex9r0ZQvv_ejt4wbayDPj8k1HMBEnOQ&downloadName=Untitled.png)

### flowWithLifeCycle

- 수집할 플로우가 하나뿐일 때 사용할 수 있다.

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/b447bcb1-8f51-466a-b610-7788e8b71686/Untitled.png?id=7c92317f-b520-4a86-b65b-fa2072ce87ef&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708012800000&signature=f0BfkZppOt0QVD3XU1GRBZm_AQl9yRcBBI5fIRFAicA&downloadName=Untitled.png)|![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/a6dfd78b-6891-4348-adc9-7d450dfeeaa9/Untitled.png?id=465ee704-2e15-4aa6-93d1-a130c2de7ac6&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708012800000&signature=Ivt_r7u-_VQXvbcJvF4GBvG9nlMKQ8HlXI5NVezjE5U&downloadName=Untitled.png)
---|---|

보통의 동작으로 홈화면을 누른다면 해당 앱이 백그라운드 영역에 가고 수명주기는 onStop에 머무른다.

그러다가 앱을 다시 실행하면 onStart

repeatOnLifeCycle은 view가 화면에 표시되는 동안 collect를 하다가, 화면에서 없어지면 중단한다.

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/ef96befe-66a0-4705-9662-66779b2438f9/Untitled.png?id=63973b9c-35be-4e27-a018-ef75284517ea&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708012800000&signature=fbT_6OGG8Zd3P8F8HD3WVRXWYLtvUnoGCW4ZbR98MAg&downloadName=Untitled.png)

이런 방식은 위험하다.

왜냐하면 백그라운드에 있어도 앱이 collect를 계속해서 UI state를 업데이트 하기 떄문이다.

따라서 우리는 onStart에서 수집을 시작하고 onStop에서  수집을 멈춰야한다.

이러한 코드적인 불편함은 repeatOnLifeCycle을 사용하면 해결된다.

### launchWhenStarted

- 앱이 백그라운드에 있을대 플로 수집을 중단한다.
- 플로 builder를 계속 활성화 시켜 화면에 표시되지않을 항목으로 메모리를 채울 수 있는
  백그라운드에서 항목을 전달할 수 도 있다

따라서 안전하게 repeatOnLifeCycle이나 flowWithLifeCycle을 사용해라.

Flow를 뷰에 노출할 경우 수명 주기가 서로 다른 두 요소 사이에 데이터를 전달해야하는것을 고려해야한다.

액비비티와 프래그먼트가 대표적인 예

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/377d86a0-6660-44b9-83b2-3bf1c3c50d32/Untitled.png?id=27c2c7a2-bc12-4430-af6b-89b1cacb8024&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708012800000&signature=UAk8uJgIN4p4WUKfK37LouPD_cfTLH9bG2ovdRtyUAw&downloadName=Untitled.png)

기기가 회전되었거나 구성 변경을 수신하면 모든 액티비티를 다시 시작하지만 viewmodel은 그렇지 않다.

viewModel에서 모든 플로우를 노출하는것은 아니다.

```java
val result : Flow<Result<UiState>> = flow {
	emit(repository.fetchItem())
}
```

콜드 플로우는 처음으로 수집될때마다 다시 시작하기 때문에 레포지토리는 회전 후 다시 호출된다.

일종의 버퍼가 필요하다.

데이터를 보관하고 있다가 여러 컬렉터 사이에 공유하면 된다.

재생성 횟수는 관계가 없다.

이러한 목적으로 StateFlow가 생성되었다.

## StateFlow

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/ee4abced-b3b7-49ff-b19d-6196e31ea186/Untitled.png?id=bb989889-4b10-4031-b296-933ffe3d6eec&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708012800000&signature=o0850VOxGH9MRftcKYvUJnPxhXCJ5b3aYi_YBec71bg&downloadName=Untitled.png)

StateFlow는 물탱크에 비유할 수 있다.

컬렉터가 없더라도 데이터를 보관한다.

일회성 수집이 아닐 수 있으므로 액티비티나 프래그먼트와 함께 사용하는것이 안전하다.

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/1c211bcd-2502-4509-8185-16543239fa0a/Untitled.png?id=6430265f-9c15-42ea-8ba9-3be2e4d96d3a&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708012800000&signature=ezEUHqlMWqKYyX1OMcuJj7ieRXqrJxaT_O6-aP8KTr8&downloadName=Untitled.png)

Flow를 StateFlow로 변환하는데 `stateIn` 연산자를 사용한다.

세가지 매개변수

- 초기값이 있기 때문에 초기값을 받는다.
- 코루틴 범위는 공유가 시작되는 시점을 제어하는데, 여기에 viewModelScope를 사용가능하다.

started에 대한 두가지 시나리오

### 플로의 컬레겉인 액티비티가 일정 시간 파괴되었다가 다시 생성시키는 회전.

```java
최대한 빠르게 전환하려면 플로우를 다시 시작해선 안된다.
```

### 홈으로 이동해서 앱을 백그라운드에 넣는다.

```java
배터리와 다른 리소르를 아끼기 위해 모든 플로를 중단해야 한다.
```

어떻게 탐지하는가?

TimeOut 을 사용한다.

StateFlow의 수집이 중단되었을 때, 모든 업스트림 플로우를 즉시 중단하는것이 아닌 잠시 기다린다.

Timeout 전에 플로우를 수집하면 업스트림 플로우가 취소되지 않는다.

이러한 일을 마지막 매개변수인 started가 한다.

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/26803249-fdb2-42dc-b1b7-cef00fa6a1c8/Untitled.png?id=ae290dc2-621c-49f6-80de-bae407f5680e&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708012800000&signature=ZkbrcQwr4SQvsbA6rh4KZjPeMlV_sdcQj5Gac--t10I&downloadName=Untitled.png)

홈으로 이동한경우와 같이 Timeout 동안 플로우를 수집 못할경우 자동으로 cancel 되고,

onStart가 호출되면 다시 플로우가 시작된다

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/01f5df30-3e4e-4386-9bf9-1ddfcff7d402/Untitled.png?id=8a06da8e-17c9-44b4-9138-5f6ca83e9e94&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708012800000&signature=8dr0qd_X-lyUtgpdkpabaQd188c4zsAYIGVH-tgPBso&downloadName=Untitled.png)

회전시나리오에서 뷰는 잠시 중단된다.

5초이내에 복원됬고, Flow는 취소되지 않는다.

모든 업스트림 플로우를 활성 상태로 유지한다.

요약하면

StateFlow를 사용하여 ViewModel에서 Flow를 노출하거나, asLiveData를 사용해서 동일한 작업을 실행하는것이 좋다

---

Coroutine의 Flow는 데이터 스트림이며, 코루틴 상에서 리액티브 프로그래밍을 지원하기 위한 구성요소다.

## 리액티브 프로그래밍이란?

```java
리액티브 프로그래밍이란 데이터가 변경될 때 이벤트를 발생시켜서 데이터를 계속해서 전달하도록 하는 프로그래밍 방식을 뜻한다.
```

기존 명령형 프로그래밍에서는 데이터의 소비자는 데이터를 요청한 후 받은 결과값을 일회성으로 수신한다.

하지만 이러한 방식은 굉장히 비효율적(데이터가 필요할 때 마다 결과값을 요청해야 하기 때문)

따라서 리액티브 프로그래밍에서는 데이터를 발행하는 발행자가 있고, 데이터의 소비자는 데이터의 발행자에 구독 요청을 한다.

그러면 데이터의 발행자는 새로운 데이터가 들어오면 데이터의 소비자에게 지속적으로 발행한다.

리액티브 프로그래밍에는 하나의 데이터를 발행하는 Producer가 있고, 해당 발행자는 데이터의 Consumer에게 지속적으로 데이터를 전달하는 역할을 한다.

이러한 흐름을 `데이터 스트림` 이라고 한다.

---

Coroutine Flow는 코루틴 상에선 리액티브 프로그래밍을 지원하기 위해 만들어진 구현체이다.

코루틴에서 데이터 스트림을 구현하기 위해서는 Flow를 사용해야 한다.

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/7efc2dff-6616-41b8-b412-1f55ed7e353d/Untitled.png?id=1149458e-6c04-40c2-931b-72ae55252c1f&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708012800000&signature=u9zWDTG3nBvV1oqO9RqZch_Fe2BFLSilUxwT1dOcXf0&downloadName=Untitled.png)

## 데이터 스트림의 구성요소

### Producer(생산자)

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/f140c645-14d2-4487-883c-292b39834da7/Untitled.png?id=66fe2cc4-de78-4a5b-a693-7e3f38ec85fb&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708012800000&signature=2txRAtBNwYKSC7EhBUHuwHX_mt3l-hAcdvHLzZj8a1o&downloadName=Untitled.png)

- 생산자는 데이터를 발행하는 역할을 한다. Flow에서의 Producer는 flow {} 블록 내부에서의 emit()을 통해 데이터를 생성한다.

대표적인 DataSource는

- 서버의 데이터로 받아오는 Rest API를 이용해 가져오는 데이터.
- 휴대폰 상의 DB에서 가져오는 데이터

### Intermediary(중간 연산자)

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/85f9b1de-a7fa-4c8c-8cc3-2000c966ad68/Untitled.png?id=4094fc85-9d63-40c2-b1b3-71f40f6d7c35&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708012800000&signature=4g8C0Ur44TTADnEzQX8dSg3WaRC39mJsMZdX8ODDh44&downloadName=Untitled.png)

- 생산자가 데이터를 생성했으면 중간 연산자는 생성된 데이터를 수정한다. 여기서의 중간 연산자는 생산자가 생성한 데이터를 수정한다. 예를 들어 생산자는 A라는 객체로 이루어진 데이터를 발행했는데 우리는 B라는 객체 데이터가 필요한 경우 Flow에서 지원하는 중간 연산자를 이용해 A객체를 B객체로 바꿀 수 있다.
- 대표적인 중간 연산자는 map, filter, onEach등의 중간연산자가 있다.

### Consumer(소비자)

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/ba9f8a56-6ff3-4018-a9d4-df8d5d889844/Untitled.png?id=99de991f-1a1d-4030-bfc6-783f640c5d48&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708012800000&signature=HpHLxKAoYUXTaxU6yuwA8jX4eiwmrqy2N_rNYeLeARA&downloadName=Untitled.png)

- 중간 연산자가 생산자가 생성한 데이터를 변환하여 소비자로 데이터를 전달한다. Flow에서는 collect를 이용해 전달된 데이터를
  소비할 수 있다.

---

## Flow vs StateFlow

### Flow의 한계

Flow는 데이터의 흐름이다. Flow는 데이터의 흐름을 발생시키기만 할 뿐 데이터가 저장되지 않는다.

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/0b7b7f98-5142-422f-aa90-0882557dc879/Untitled.png?id=f4a9b1d8-1e32-4284-aaa9-3d0881a8989c&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708012800000&signature=jNkhdy-MqtnnJbWn_c8MUtVq3-31AOa8Ludye-s-x28&downloadName=Untitled.png)

flow 만을 이용해 안드로이드의 UIState를 업데이트 하기 위해서는 두가지 방법이 가능했다.

1. 화면이 재구성될때마다(회전) 다시 서버 혹은 DB로부터 데이터를 가져오기
2. Flow로 부터 collect한 데이터를 ViewModel에 저장해놓고 사용하기

1번 방법은 onDestroy가 호출된 후 onCreate, onStop이 호출된 후 onStart 모두 화면을 재구성하는데, 이때마다

새로운 데이터를 서버 혹은 DB로부터 가져와야하는것은 비효율적이다.

2번 방법은 ViewModel의 생명주기는 Activity가 살아있는 동안 유지되기 때문에 해당 데이터를 viewModel에서 들고 있으면 된다.

viewModel에서 데이터를 저장하려면 별도의 데이터 홀더 변수가 필요하고, 이러한 홀더 변수는 관찰가능한 데이터 홀더로 만들어야 한다.

그러기 위해서 flow를 사용하고, ui는 flow로 만들어진 데이터 홀더를 구독하고, 데이터 홀더는 flow에서 마지막으로 발행한 데이터를 저장하면 된다.

안드로이드에서 수집하는 UI State가 하나가 아닌 여러개이고, 이 모두를 구독하기 위해서 비슷한 코드를 매번 작성하는것은 굉장히 비효율적이다.

이를 위해서 등장한 것이 StateFlow이다.

StateFlow는 데이터 홀더 역할을 하면서 Flow의 데이터 스트림 역할까지 수행한다.

UI 단에서 StateFlow를 구독해 UiState를 업데이트 하면 화면이 재구성될 때마다 다시 서버로 데이터를 요청할 필요가 없어진다.

Ui는 단순하게 StateFlow를 구독만 하면 된다.

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/e59394c7-fd08-4e98-af01-1b4abaa69792/Untitled.png?id=7cedf3b4-640b-4dcd-9e4a-674efc78e09d&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708012800000&signature=LF8whEmCxe3Bmyfkd7DADLNsfUDpcqZVnsNPGf5QbOc&downloadName=Untitled.png)

---

## Cold Stream vs Hot Stream

```java
1. 데이터가 생성되는 위치
2. 생산자가 발행한 데이터를 동시에 여러 소비자들이 수신할 수 있는지 여부
3. 스트림이 데이터를 생산하는 시점
```

### Cold Stream

- 데이터가 내부에서 생성
- 소비자가 소비를 시작 할 때 데이터 생산
- 하나의 생산자에는 하나의 소비자가 문제

### Hot Stream

- 데이터가 외부에서 생성
- 하나의 생산자에 다수의 소비자가 존재
- 생산자가 소비자의 소비를 신경쓰지 않고 생산