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

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/c7c9963a-6b02-4e47-a706-c4c320ece7df/Untitled.png?id=a8710344-b3db-4509-a9a7-d08e27416b4d&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708444800000&signature=PurJpHblgpmPX_TpFIIeBKNyX4P7yp7xN4OQ6fqNzDc&downloadName=Untitled.png)

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