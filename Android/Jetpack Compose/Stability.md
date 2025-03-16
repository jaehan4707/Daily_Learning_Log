# Jetpack Compose Stability

Jetpack Compose를 최적화하기 위한 내부 동작방식이나 안정성을 이해하기 전에 Compose UI는 어떠한 과정을 거쳐 화면에 렌더링하는지 알아 볼 필요성이 있다.

<img width="558" alt="Image" src="https://github.com/user-attachments/assets/c4256255-5960-4b40-ac89-b5cf3a3b9b34" />

Jetpack Compose는 세 가지 단계에 걸쳐 렌더링을 실행한다.

- **구성(Composition)**
    - 해당 단계에서는 Composable 함수의 설명을 생성하고, 여러 메모리 슬롯을 할당하는 과정이 시작된다.
    - 여기서 생성된 슬롯들은 각 Composable 함수를 메모이즈하여 런타임동안 효율적인 호출 및 실행을 가능하게 한다.
- **레이아웃(Layout)**
    - 해당 단계에서는 Composable 트리 내에서 각 Composable 노드의 위치가 설정된다.
    - 주로 각 Composable 노드의 측정 및 적절한 배치를 포함하며, UI의 전체 구조 내에서 모든 요소가 정확하게 배치되도록 보장한다.
- **그리기(Drawing)**
    - 해당 단계에서는 Composable 노드가 캔버스에 랜더링됩니다.
    - Composable 함수들에 대한 정보를 사용하여 사용자 상호작용이 가능하도록 시각적 표현을 생성하는 단계입니다.

만약 랜더링이 끝난 Composable 함수의 내부에서 변경이 생길 경우는 어떤식으로 동작할까? **ex) 색상이나 UI 요소 수정**

<img width="550" alt="Image" src="https://github.com/user-attachments/assets/07743994-a22a-4200-ba25-27b17d5e55a6" />


Recomposition은 매개변수의 변화에 반응하여 Composable 함수를 새롭게 실행할 때 발생하며, 이 과정은 구성(Composition)단계부터 시작한다. 또한, 상태 관찰과 같은 다양한 요인에 의해 트리거될 수 있으며, 내부적으로는 Compose 런타임 및 컴파일러 메커니즘과 밀접하게 연관되어 있다.

UI는 트리 구조이기 때문에 전체 UI와 그 아래 요소들을 다시 구성하는 것은 굉장히 많은 자원을 요구하며, 앱의 성능에 직접적으로 좋지 않은 영향을 미친다.

**Compose에서는 이러한 문제를 예방하기 위해 불필요한 경우 recompostion을 건너뛰고 필요한 경우에만 recompostion을 트리거하는 `smart recompostion`을 지원한다.**

이로써 오버헤드를 최소화하고 UI 성능을 향상시킬 수 있게 된다.

Compose에서 가장 중요한 부분이 최소한의 recompostion을 발생시키는 것이고, recomposition을 트리거하는 요인을 잘 이해하면 성능 최적화를 기대할 수 있다.

## Understanding Stability

### Restartable

- Composable 함수는 표준 함수와 달리 recompostion을 통해 재실행이 가능하다.
- Recompostion은 메모리 내 표현을 항상 최신 상태로 유지하기 위해 입력값의 변경이나 관찰중인 State에 변경이 발생할 때 일어남.
- Compose 컴파일러는 상태를 관찰하는 모든 Composable 함수를 찾아서 Compose 런타임에게 정보를 전달함.

<img width="561" alt="Image" src="https://github.com/user-attachments/assets/78e90c84-615b-4a03-a5af-a2d1cbe26031" />


### Recomposition

#### 1. 관찰중인 State의 변경

Compose 런타임은 상태 변경을 관찰하고 recompostion을 트리거하는 State라는 효율적인 메커니즘을 제공


```kotlin
var text by remember { mutableIntStateOf(0) }
Text(
    modifier = Modifier.clickable { text++ }, 
    text = "$text times clicked!"
)
```

#### 2. Composable 함수의 매개변수 변경

Compose 런타임은 `equals` 함수를 사용해 stable한 매개변수에 대해 입력값의 변경사항을 감지함. **(false → 데이터 변경으로 해석)**


```kotlin
@Composable
fun MyProfile(name : String, image : String) {
	//
}
```

### Stable vs Unstable

<img width="563" alt="Image" src="https://github.com/user-attachments/assets/af3a202a-2c6d-4d77-b0f9-9e9a6ef5eb8f" />

- Compose 컴파일러는 Composable 함수에 사용된 매개변수에게 stable 혹은 unstable 타입을 부여
- Composable 함수에 unstable한 매개변수가 하나 이상 포함되어 있으면 recompostion이 항상 발생
- Composable 함수가 모두 stable한 매개변수로 이루어져 있다면 recomposition을 건너뛰고 불필요한 작업 생략

**코틀린 컴파일러는 매개변수를 어떤 기준으로 안정정인지, 불안정한지 분류할까?**

코틀린 컴파일러는 Composable 함수에서 사용되는 매개변수의 유형을 검사하고 기준에 따라 안정성 여부를 분류한다.

**Stable로 간주되는 유형**

1. 원시 타입(Primitive)
2. (Int) → String과 같은 람다 표현식으로는 표현된 함수 유형은 안정적인 것으로 간주된다. **(람다에서 Unstable한 값을 캡처하는 경우는 잠재적인 Unstable로 분류됨)**
3. public property가 불변(val 선언)이고 stable한 속성을 가진 클래스
4. @Stable @Immutable과 같은 stability 어노테이션을 사용하여 명시적으로 표기된 경우

**Unstable로 간주되는 유형**

1. 컴파일 타임에 구현체를 예측할 수 없는 Any 유형과 같은 추상클래스와 List, MAp 등을 포함한 모든 인터페이스
2. var 프로퍼티 혹은 unstable한 경우

```kotlin
// Stable
data class Apple(
    val id : Int, 
    val price : Int
)
```

primitive 타입과 불변인 value로 정의된 프로퍼티만을 가지는 Apple 클래스는 Compose 컴파일러에 의해 안정적인(Stable) 것으로 간주됩니다.

```kotlin
// UnStable
data class Apple(
    val id : Int, 
    var price : Int
)
```

stable로 간주되는 primitive 타입으로 구성되어 있음에도 불구하고, 가변적인 price 프로터피가 존재하기 때문에 Compose 컴파일러는 이를 불안정한(Unstable) 것으로 간주한다.

```kotlin
data class Apple(
    val id : Int, 
    val weight : List<Int>
)
```

모든 프로퍼티가 val인 불변으로 설정되어있지만, Unstable로 간주되는 인터페이스(List)를 포함하기 때문에 Unstable로 간주한다.

**단 하나의 가변적인 프로퍼티만으로 클래스 전체를 unStable 하게 만들수 있다!**

## Smart Recompostion


<img width="561" alt="Image" src="https://github.com/user-attachments/assets/d503601a-5264-4c24-9723-07a96a60fecd" />


Compose 컴파일러는 Composable 함수의 각 매개변수의 안정성을 평가하여 Compose 런타임이 이 정보를 효율적으로 활용할 수 있는 토대를 마련합니다.

클래스의 안정성이 결정되면, Compose 런타임은 스마트 recompostion이라고 알려진 내부 메커니즘을 통해 recompostion을 시작합니다.

→ 제공된 안정성 정보를 활용하여 선택적으로 skip하고 Compose의 전체 성능을 향상시킨다.

**어떠한 기준으로 smart recompostion이 작동할까?**

1. **안정성에 따른 결정**
    1. 매개변수가 안정적이고 그 값이 변경되지 않은 경우 Compose는 관련 UI 컴포넌트의 recompostion을 건너뛴다.
    2. 반면, 매개변수가 불안정하거나, 안정적이지만 그 값이 변경된 경우 런타임은 recomposition을 시작하여 UI 레이아웃을 무효화하고 다시 그립니다.
2. **동등성 검사**
    1. 위에서 설명한 equals 함수를 통한 동등성 비교는 해당 타입이 안정적으로 간주되는 경우에만 수행한다.
    2. 새로운 입력값이 Composable 함수에 전달될 때마다, 항상 해당 타입의 equals 메서드를 사용하여 이전 값과 비교한다.

## Composable 함수 추론하기

Compose 컴파일러는 매개변수 타입 뿐만 아니라 함수의 유형까지 추론할 수 있다.

- **Restartable : 재실행 가능함 여부**
- **Skippable : 리컴포지션 생략 여부**
- **Movable : 컴포저블 트리 내에서 고유성을 잃지 않고 트리 내에서 위치를 옮겨 다닐 수 있는 여부**

### Restartable

> 대부분의 Composable 함수는 재실행 가능하고 멱등성의 성질을 가진다.
>

<img width="562" alt="Image" src="https://github.com/user-attachments/assets/b91b1930-6ea5-4256-808c-882dd1fbdcdd" />


입력이나 상태가 변경될 때마다 Compose 런타임이 Composable 함수에 대해 recomposition을 트리거할 수 있다는 것을 의미함.

### Skippable

> Composable 함수가 stable한 매개변수만으로 구성된 경우, recompostion을 생략할 수 있는 Skippable로 분류
>

<img width="557" alt="Image" src="https://github.com/user-attachments/assets/7e1b575f-a102-4ec7-a8e7-f144604e1659" />


`Skippable`한 Composable 함수는 특정 상황에 따라 Recomposition을 건너뛰고 UI 성능을 향상시킬 가능성과 직접적인 연관성이 있다.

해당 유형은 특히 함수 호출의 방대한 계층 구조의 정점에 위치한 루트 Composable 함수의 성능을 향상시키는 데 중요한 역할을 한다.
하위 함수를 호출할 필요성을 효과적으로 제거하여 전체 recompostion 프로세스를 간소화할 수 있다.

**최대한 Composable 함수를 restartable, skippable하게 만드는 것이 불필요한 리컴포지션을 방지, 앱 성능을 향상시킬 수 있다.**

## Stability Annotations

<img width="549" alt="Image" src="https://github.com/user-attachments/assets/42509ed7-4dc7-4603-87dd-4d85170fbd97" />


클래스를 안정적이라고 간주되도록 표기할 수 있는 주요 어노테이션은 @Immutable과 @Stable 두 가지가 있다.

### @Immutable

- Immutable 어노테이션은 Compose 컴파일러에 대한 강력한 약속이며, class의 모든 public 프로퍼티와 필드가 초기화 된 이후에 절대 변경되지 않도록(불변) 보장

**어노테이션 사용에 대한 두 가지 규칙**

1. 모든 public 프로퍼티에 대하여 val keyword를 사용하여 불변임을 확인
2. 커스텀 setter 사용을 피하고, 모든 public 프로퍼티에 가변성이 없는지 확인

```kotlin
data class User(
    val id : Int, 
    val name : String, 
    val images : List<String>
)
```

**모든 프로퍼티가 val이지만, Unstable한 프로퍼티 images 때문에 User 클래스는 Unstable로 간주된다.**

```kotlin
@Immutable
data class User(
    val id : Int, 
    val name : String, 
    val images : List<String>
)
```
**하지만 User 객체가 생성된 이후 프로퍼티가 변하지 않는 확신이 있다면, @Immutable 어노테이션을 명시적으로 표기해 Stable하게 만들 수 있다.**

### @Stable

- @Stable 어노테이션 또한 val 키워드 보다 Compose 컴파일러에 대한 강력한 약속이지만, @Immutable 보다는 조금 느슨한 약속을 의미
- 문맥적으로 “Stable”이라는 용어는 함수가 동일한 입력값에 대해 일관되게 동일한 결과를 반환하여, 잠재적인 변경 가능성에도 불구하고 예측 가능한 동작을 보장한다는 것을 의미
- @Stable 어노테이션은 프로퍼티가 불변인 클래스 혹은 인터페이스에 가장 적합하지만, 클래스 자체나 인터페이스의 구현체가 안정적이지 않을 수 있는 경우에 사용됨.

### @Immutable vs @Stable

```kotlin
@Immutable
data class User(
    val id : Int, 
    val name : String, 
    val images : List<String>,
)

```

```kotlin
@Stable
interface UiState<T : Result<T>> { 
    val value : T? 
    val exception : Throwable? 
    val hasSuccess : Boolean 
        get() = exception == null
}
```

@Immutable
- **일반적으로 네트워크, 데이터베이스와 같이 비즈니스 로직으로 부터 받은 응답이 생성된 이후 절대 수정이 발생하지 않는 경우**

@Stable
- **외부로 노출된 프로퍼티는 불변이지만, 잠재적으로 가변성을 가질 수 있고 값의 동일성을 equality 체크를 통해 확인할 수 있는 경우**

### @NonRestartableComposable

- Composable 함수에 사용할 경우 Restartable 속성을 부여하지 않고 recompostion을 생략
- Recomposition의 영향을 받지 않아야 하는 경우 사용하기에 적합
- Composable 함수 내 상태 관리 및 사이드 이펙트 처리 및 표준 함수만을 호출하는 사례

<img width="443" alt="Image" src="https://github.com/user-attachments/assets/243decfe-4d2b-4a3c-bf65-3cd0376e0825" />

## Composable 함수 안정화

### Immutable Collection

**interface의 경우 구현체는 어떤 식으로 생성될 지 알 수 없기 때문에 Unstable로 간주된다.**

```kotlin
var mutableUserList : MutableList<User> = mutableListOf()
val userList : List<User> = mutableUserList

@Composable  // Unstable
fun Profile(images : List<String>){

}
```

코틀린의 List, Set도 예외는 아니다. 따라서 List나 Set과 같이 코틀린의 Collection을 Composable 내부의 파라미터로 사용할 경우 Skippable 하지 않게 되고, Recomposition을 생략할 수 없게 된다.

이에 대해서 Compose는 성능 최적화를 위한 솔루션을 제공하고 있다.

```kotlin
@Composable
fun Profile(images : ImmutableList<String>){
	//
}
```

**Immutable 라이브러리나 guava의 immutable collections들은 기본적으로 stable로 간주되고, Composable을 Skippable하게 만들 수 있다.**

### Wrapper Class

자바의 표준 라이브러리나 서드 파티 라이브러리에서 제공되는 클래스처럼 개발자가 의존하는 클래스의 경우 Wrapper Class를 만들고 어노테이션을 활용해 Stable 하게 만들 수 있다.

<img width="552" alt="Image" src="https://github.com/user-attachments/assets/5957bd12-6e74-40ee-81aa-2349ee476b05" />

## 참고

https://velog.io/@skydoves/compose-stability

https://www.youtube.com/watch?v=bDyhdJk3uZM&t=1365s