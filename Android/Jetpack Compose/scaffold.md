# Scaffold

```
머테리얼에서 제공하는 디자인 컴포넌트들을 
편하게 사용할 수 있도록 미리 디자인 된 레이아웃이다.
```

그 예는

- TopBar
- BottomBar
- SnackBar
- FloatingActionButon
- Drawer

![image](https://github.com/jaehan4707/Daily_Learning_Log/assets/99114456/0a23c3ff-71bc-496a-853e-32661479e957)


- floatingActionButtonPosition : floating action 버튼의 위치
    - default는 End이며, 가능한 값은 Center와 End이다.
- contentColor : Scaffold body의 내용 색상, 대부분의 경우 이 매개변수는 기본값이다.

### SnackbarHost

- 최대 하나의 Snackbar를 표시하도록 보장하며, 나머지는 대기열에 있다.
- snackBoarHostState는 remember{ SnackBarHostState() }로 선언해줘야한다.

```kotlin
val snackBarHostState = remember { SnackbarHostState() }
```
```kotlin
addSpaceViewModel.event.collectLatest { event ->
    when (event) {
        is SpaceUiEvent.SuccessAdd -> onBack()

        is SpaceUiEvent.ShowMessage -> snackBarHostState.showSnackbar(event.message)
        else -> {}
    }
}
```
- snackbar는 `suspend` 함수이기 때문에 `코루틴 scope`가 필요하다.