# RecyclerView

## lazyColumn

```kotlin
현재 표시되는 항목만 구성하고 배치하는 세로 스크롤 목록입니다.
블록 content는 다양한 유형의 항목을 내보낼 수 있는 DSL을 정의한다.
LazyListScope.item은 단일 항목을 추가하고,
LazyListScope.items는 항목 목록을 추가하는데 사용할 수 있다.
LazyListScope.itemsIndex는 항목 목록을 추가하고, 항목의 index를 파악할 수 있다.

```

코드로 간단하게 살펴보면

```kotlin
LazyColumn(
      state = scrollState,
      modifier = Modifier.fillMaxWidth(),
  ) {

      for (cat in uiState.cats) {
          item{
              CatsRow(cat = cat, onRemoveClicked = onRemoveClicked)
          }
      }
  }
```

item은 단일 항목을 추가하기 때문에 배열을 돌면서 item을 쌓아줄 수 있다.

하지만 item이 여러개라면 굳이 이렇게 할 필요가 없다.

```kotlin
LazyColumn(
    state = scrollState,
    modifier = Modifier.fillMaxWidth(),
) {
    items(
        items = uiState.cats,
        itemContent = { CatsRow(cat = it, onRemoveClicked = onRemoveClicked) },
    )
}
```

items는 컴포저블 함수를 반복해서 사용할 수 있다.

다음은 index를 함께 뽑을 수 있는 itemsIndexed

```kotlin
LazyColumn(
        state = scrollState,
        modifier = Modifier.fillMaxWidth(),
    ) {
        itemsIndexed(
            items = uiState.cats,
        ) { index, item ->
            CatsRow(cat = item, onRemoveClicked = onRemoveClicked)
        }
    }
```

index마다 특정 효과나 디자인을 주고싶다면 사용할 수 있을것같다.

## 이벤트 처리

리사이클러뷰의 아이템의 이벤트 처리를 위해서 -버튼을 추가해서 아이템을 제거하도록 해봤다. 그 외에도 FAB를 넣어서 화면에서 활용하는 Cats라는 배열에 아이템을 추가해서 화면에 바로 적용이 되는지 확인해보았다.

![image](https://github.com/jaehan4707/Daily_Learning_Log/assets/99114456/70cf2ce1-796e-4f65-a9c3-00ddbe3095e3)

+버튼과 -버튼을 눌렀지만 화면에는 변화가 없었다.

로그를 찍어서 이벤트 호출 여부를 확인했다.

![image](https://github.com/jaehan4707/Daily_Learning_Log/assets/99114456/098fd8e0-f989-4999-97e4-cc5f1f6edc3b)|![image](https://github.com/jaehan4707/Daily_Learning_Log/assets/99114456/5f1e6c92-a54c-47ff-8c57-cd8a47aa14a2)
---|---|

이벤트가 발생하지만 데이터에 반영이 안됨.

문제는 데이터 갱신에 있었다.

```kotlin
  val cats = remember { Datasource.cats }
    val scrollState = rememberLazyListState()
    Scaffold(topBar = { topBar() }) { innerPadding ->
        BoxWithConstraints(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
        ) {
            LazyColumn(
                state = scrollState,
            ) {
                items(
                    items = cats,
                    itemContent = { CatsRow(cat = it) },
                )
            }
        }
    }
```

remember로 감쌌지만, add와 event를 통해서 상태를 변경시켰지만, 반영이 되지 않았던 문제였다.

이걸 해결하기 위해서 viewModel과 uistate를 간단하게 만들어봤다.

```kotlin
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  Scaffold(
      topBar = { topBar() },
      floatingActionButton = { addCat(onAddClicked) },
  ) { innerPadding ->
      BoxWithConstraints(
          modifier = Modifier
              .padding(innerPadding)
              .fillMaxWidth(),
      ) {
          RecyclerScreenComponent(
              uiState = uiState,
              onRemoveClicked = onRemoveClicked,
          )
      }
  }
```

Composable 내에서는 변수 cats를 바라보는것이 아닌 stateFlow를 관찰하게 해서 데이터의 변화를 반응하게 만들어봤다.

```kotlin
fun addCat() {
    _uiState.update { uiState ->
        val newCats = uiState.cats.toMutableList().apply {
            add(Cat("고양이", R.drawable.cat_1, "고양이입니다"))
        }
        uiState.copy(cats = newCats)
    }
}

fun removeCat(cat: Cat) {
    _uiState.update { uiState ->
        val newCats = uiState.cats.toMutableList().apply {
            remove(cat)
        }
        uiState.copy(cats = newCats)
    }
}
```

각각 +버튼과 -버튼에 대한 클릭 이벤트를 viewModel에 정의하고,

클릭할때마다 해당 함수가 실행되도록 했고, 데이터가 변화하고, UI가 변화하는것을 확인할 수 있었다.

[리사이클러뷰.webm](https://github.com/jaehan4707/Daily_Learning_Log/assets/99114456/cda139ab-6f9a-450c-b5a5-070d2c145798)