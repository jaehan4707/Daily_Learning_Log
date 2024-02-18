# Composable 생명주기

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/583a21b1-c9bf-4e2f-9b1c-56791798ed08/Untitled.png?id=edfd87c3-782a-4198-ac26-41f9fbc3b10d&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708365600000&signature=2TP0O0HOBeO4jVv7suQ74yD9eFA9u-ZBQhY1bo7vbE4&downloadName=Untitled.png)

## Initial Composition

```kotlin
Composable이 처음 생성될 때
```

## Recomposition

```kotlin
UI를 구성하는 데이터가 변경되었을 때
```

- State<T>가 변경되거나, Composable 함수의 파라미터의 값이 변화가 트리거
- Recomposition은 0번 혹은 그 이상이 일어날 수도 있다.

## Decomposition

```kotlin
Composable이 파괴될 때
```

---

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/d8d94f1d-87c4-4c45-af9b-b40ce88e07d9/Untitled.png?id=03499305-1804-4b8f-8767-8bed9c37e771&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708365600000&signature=YMwj9Ayo7YX4rHTKa1KhP_8ho81lbr0rZmrJGYJ-42k&downloadName=Untitled.png)

```kotlin
@Composable
fun MyComposable(){
	Column{
		Text("hello1")
		Text("hello2")
	}
}
```

MyComposable의 코드를 도식화한 결과가 왼쪽 그림이다.

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/07c94ecf-465f-4aa5-a7ef-ec76d8c46ff3/Untitled.png?id=4e19c9ea-dcb0-41e2-aef4-18b7821d4d32&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708365600000&signature=wADHcSKoCnoNIDs0xHLrmtUOrIy5N16ZY3qYkty6TLY&downloadName=Untitled.png)

```kotlin
@Composable
fun MovieScreen(movies: List<Movie>){
	Column {
		for(movie in movies) {
					MovieDetailView(movie)
		}
	}
}
```

특이한점은 이미 Composition에 있는 경우 입력이 stable하고 변경되지 않았다면 Recomposition을 skip 할 수 있다.
그렇다면 그 데이터가 stable 한지 어떻게 확인할 수 있을까?

stable 한 유형은 다음과 같은 특징이 있다.

- 두 인스턴스의 equals 결과가 동일해야 한다.
- 타입의 public property가 변경되면 Composition에 알린다.
- 모든 public property 타입은 stable 하다.

@Stable 어노테이션을 사용하여 명시적으로 stable하다고 표시하지 않고도 stable한것으로 처리되는 몇가지 타입이 있다.

- 모든 Primitive 타입 : Boolean, Int,Long,FLoat,Char 등
- Strings
- 모든 함수 타입(lambdas)

이러한 타입은 모두 불변이기 때문에 stable하다고 할 수 있다. 즉 변경되지 않으므로 Composition에 변경사항을 알릴 필요가 없다.

Compose에서 stable하면서 변경 가능한 타입은 MutableState 타입이다.

위에서 언급한것처럼 Compose는 모든 입력이 stable하고 변경되지 않은 경우 equals 연산을 이용한다.

UI 트리의 Composable의 위치를 기준으로 매개변수 값을 비교하고, 값이 변경되지 않은 경우 Recomposition을 스킵한다.
