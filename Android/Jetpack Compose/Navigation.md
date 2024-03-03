## 의존성 설정

```kotlin
dependencies {
    val nav_version = "2.7.7"

    implementation("androidx.navigation:navigation-compose:$nav_version")
}
```

Compose에서 NavController를 만들려면 `rememberNavController()` 를 사용

NavController를 만들었다면 `NavGraph`를 만들어야 한다.

NavGraph를 만들기 위해서 Compose에서는 `NavHost` 컴포저블을 사용해서 `NavGraph`를 만든다.

```kotlin
val navController = rememberNavController()

NavHost(navController = NavController, startDestination = "Home"){
	composable(route = "Login"){ LoginScreen() }
	composable(route="Menu"){ MenuScreen()}
}
```

- `NavHost` 컴포저블 호출은 startDestination에 해당하는 `NavController` 및 route 문자열을 전달한다.
- `NavHost` 에 전달된 람다는 최종적으로 `NavController.createGraph()` 를 호출하고 `NavGraph` 를 반환
- `NavGraphBuilder.composable()` 를 호출하면 결과에 `NavGraph` 가 추가된다.
- route가 경로 문자열을 뜻하며 composable을 구별하는 키가 된다.

위와 동일한 코드는

```kotlin
val navGraph by remeber(navController) {
	navController.createGraph(startDestination = "Home") {
		composable(route = "Login"){ LoginScreen() }
		composable(route = "Menu"){ MenuScreen() }
	}
}
NavHost(navController,navGraph)
```

## Composable 이벤트 노출

Composable 함수가 새 화면으로 이동해야 하는 경우 `navigate()` 를 직접 호출할 수 있도록 `NavController` 참조를 전달하면 안된다. → 이는 UDF 원칙에 따라 컴포저블은 NavController가 처리하는 이벤트를 노출해야 한다.

즉 컴포저블에는 () → Unit 형태의 매개변수가 있어야 하지, 직접적인 navController를 전달하면 안된다.

즉 NavHost에서만 navigate를 호출하고, 아래 composable에는 해당 람다식을 사용하는것

```kotlin
@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "Home"
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("Login") {
            LoginScreen(
                onNavigateToMenu = { navController.navigate("Menu") },
                /*...*/
            )
        }
        composable("Menu") { MenuScreen(/*...*/) }
    }
}

@Composable
fun LoginScreen(
    onNavigateToMenu: () -> Unit,
) {
    Button(onClick = onNavigateToMenu) {
        Text(text = "Navigate to Menu")
    }
}
```

이렇게 navigate를 콜백으로 전달해서 호출하면 Recomposition마다 navigate가 호출되지 않는다.

반대로 navcontroller를 직접적으로 넘겨줘서 navigate를 호출하면 매 Recomposition 마다 navigation이 호출되므로 ,굉장히 위험한 상황이다.

Navigation Arguments 전달

`composable()` 의 `arguments` 를 이용해서 전달할 객체를 만들 수 있다.

```kotlin
 NavHost(navController = navController, startDestination = Router.BoardList.route) {
      composable(
          route = Router.MindMap.route,
          arguments = listOf(
              navArgument("boardId") { type = NavType.StringType },
              navArgument("boardName") { type = NavType.StringType },
          ),
      ) { entity ->
          val boardId = entity.arguments?.getString("boardId")
          val boardName = entity.arguments?.getString("boardName")
          val action = BoardListFragmentDirections.actionBoardListFragmentToMindMapFragment(
              boardId = entity.arguments?.getString("boardId").orEmpty(),
              boardName = entity.arguments?.getString("boardName").orEmpty(),
          )
          navController.navigate(action)
      }
  }
```