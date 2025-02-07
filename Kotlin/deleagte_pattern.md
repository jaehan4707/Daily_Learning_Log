## 위임 패턴이란?

- 위임 패턴은 객체 지향 기반의 디자인 패턴으로 상속처럼 코드의 재사용성을 향상 시켜주기 위한 패턴이다.
- 객체 A가 특정 기능을 직접 처리하지 않고 다른 객체 B에게 위임하여 처리하도록 하는 방식이다.

## 위임을 사용해야 하는 이유

- 상속은 다양한 문제점이 있다.
- 오버라이딩 상황에서 상위 클래스의 내용이 변경될 경우 상위 클래스를 참조하고 있는 하위 클래스의 내용들이 틀어지게 되며 이는 곧 에러를 발생시키게 된다.
- 상속 관계라면 하위 클래스는 반드시 상위 클래스의 메소드까지 구현해야 한다.(사용 안해도 빈 깡통으로라도)
- 상속의 깊이가 깊어질 수록 파악이 어렵다.

—> 이러한 종속성, 의존성 문제를 해결하기 위해 위임 패턴이 상속의 대안으로 증명되어 왔다.

코틀린에서는 이러한 문제를 해결하고자 위임 패턴을 지원하게 되었고, 위임 사용을 권장하고 있다.

## 상속과 위임

- 상속과 위임 모두 객체지향 프로그래밍에서 사용되는 디자인 패턴으로, 두 방식 모두 클래스를 다른 클래스로부터 확장한다.
- 차이점은 상속은 해당 클래스에 귀속이라, 클래스가 다른 클래스들 사이에서 선택할 권한을 주지 않는다.
- 하지만 위임의 경우 객체 자신이 처리해야 할 일을 다른 클래스 인스턴스에게 위임할 수 있는 것으로 상속보다 유연하게 활용할 수 있다.
- 상속 : 클래스의 객체가 다른 클래스의 객체가 들어갈 자리에 쓰여야 한다.
- 위임 : 클래스의 객체가 단순히 다른 클래스의 객체를 사용만 해야 한다.

## 상속

- A is B 관계를 가지며, 부모 클래스의 속성들을 자식 클래스가 상속 받아서 사용한다.
- 부모 클래스에서 작성된 속성들을 물려 받기 때문에 자식 클래스에서는 해당 속성들을 정의하지 않고 사용 가능하다.
    - 코드의 중복을 방지할 수 있음.

```kotlin
class Car {
	open fun move(){
		//
	}
}

class Bus : Car {
	//
}
val bus = Bus()
bus.move()
```

- 위 코드 처럼 중복 코드 작성 없이 부모 클래스의 속성을 사용할 수 있다.
- 하지만 자식 클래스가 부모 클래스에 종속되기 때문에 부모 클래스의 변경에 영향을 받는다.
    - 여기서 말하는 영향은 대표적으로 코드의 수정을 의미

## 위임

- 위임은 has a 관계를 가지며 A 클래스 내에 위임 관계를 가지고 있는 B 클래스의 인스턴스를 가지고 있는 구조이다.
    - Manager has employee

```kotlin
interface Employee {
	fun work()
	fun workOut()
}

class Employee_1 : Employee {
	override fun work () {} 
	override fun workOut() {}
}

class Employee_2 : Employee {
	override fun work () {} 
	override fun workOut() {}
}
	
```

상속의 경우 Employee와 Manager에 관계를 구현하기 위해선 다음과 같은 코드가 작성된다.

```kotlin
open class Employee_1 : Employee {
	override fun work () {} 
	override fun workOut() {}
}

class Manager : Employee_1()

val manager = Manager()
manager.work() 
```

- Manager 클래스가 Employee_1을 상속하기 때문에 work()를 사용할 수 있다.
- 동작 방식은 manager가 일을 하지만 마치 Employee_1이 작업하는 것과 같이 동작한다.
- 하지만 이 Manager 클래스는 Employee_1에 종속되어 버린다.

위임의 경우 다음과 같이 작성된다. (Java)

```kotlin
class Manager(val employee : Employee) {
	fun work() = employee.work()
	fun workOut = employee.work()
}

val firstEmployee = Employee_1()
val manager = Manager(firstEmployee)
```

- 위 예제는 Manager 객체를 만든 후 firstEmployee를 생성자로 전달했다.
- 이런 디자인이 상속을 사용하는 것 보다 좋은 점은 Manager가 Employee_1 클래스에 강하게 묶이지 않아 언제든지 새로운 Employee에 대한 인터페이스를 구현이 가능하다는 점이다.
- 이를 다르게 말해 Manager의 인스턴스는 Employee 인터페이스를 구현하는 클래스의 인스턴스에게 위임할수 있다는 뜻이다.

- 하지만 단점으로 많은 언어에서 지원하지 않는다.
- 그렇기 때문에 변경 사항이 발생할 경우(메소드 추가) A,B 클래스 모두 수정되어야 하기 때문에 OCP 원칙을 위반하게 된다.
- 하지만 이러한 문제를 해결하기 위해 코틀린은 언어적인 레벨에서 위임을 by 키워드로 구현하고, 지원한다.

## by 키워드

자바는 위 처럼 Manager 클래스에 Employee 인스턴스를 생성자로 넘겨줘야 한다.
하지만 코틀린에서는 간단한 방법으로 구현이 가능하다.

```kotlin
class Manager() : Employee by firstEmployee
```

- 위 코드의 Manager는 어떤 메소드도 따로 작성해주지 않았다.
- Manager는 FirstEmployee를 이용해 Employee 인터페이스를 구현하고 있다.
- 코틀린 컴파일러는 Employee에 속하는 Manager 클래스의 메소드를 바이트 코드 수준에서 구현하고, by 키워드 뒤에 나오는 Employee_1 클래스의 인스턴스로 호출을 요청한다.
    - 즉 by 키워드가 자바로 작성한 코드의 일을 대신 해준다.
- 클래스 정의 수준에서 사용되는 코틀린의 by 키워드의 왼쪽에는 인터페이스가, 오른쪽엔 인터페이스를 구현한 클래스가 필요하다.
    - 즉 class A () : `Interface` by `Implement`

```kotlin
val manager = Manager()
manager.work()
```

- 위 코드는 상속과 비슷하지만 다르다.
- 첫번째로, Manager 클래스는 Employee 인터페이스를 구현하지만, FirstEmployee 클래스를 상속받지 않는다.
- 두번째로, 상속을 사용한 솔루션에서 메소드를 위임하기 위해 그대로 호출하는 보일러 플레이트 코드가 작성되지 않는다. (사용되지 않는 빈 깡통 메서드 작성 X)
    - work()의 호출은 Employee_1의 메서드 work()를 호출하는 것

## 위임의 장점

- 코드의 재사용 성
- 관심사의 분리
- 확장성 & 동적 행위 변경

## 기본 사용법

말 그대로 속성의 접근을 다른 객체에 위임하는 방법이다.

즉 속성의 값을 읽거나 쓰는 두 가지 동작을 다른 객체에 위임할 수 있다는 말이 됩니다.

### 코틀린에서 제공하는 내장 Delegate

**Lazy Properties**

속성의 지연 초기화를 지원합니다.

```kotlin
val lazyValue : String by lazy {
	println("!!")
}
```

위 예에서 lazyValue는 최초 접근 시 lazy 함수에 전달되는 객체의 초기화 로직을 수행하는 람다 함수를 호출함으로써 객체를 초기화 합니다. 이 로직은 최초 접근 시 한번만 호출되며 이후에는 초기화 된 값으로 데이터 읽기가 수행됩니다.

### 실제 코드(mutableStateOf)

compose에서는 UI에 노출할 데이터를 관리하는 특별한 인터페이스 State를 제공한다.

```kotlin
@Stable
interface State<out T> {
	val value : T
}
```

State안에는 val value : T 형태로 value 속성을 가지고 있는데, State의 특별함은 Composable function에서 State의 상태 변화를 구독해서 State가 변경되면 자동으로 재구성 트리거 된다는 점이다.

```kotlin
var uiState : MutableState<Boolean> = mutableStateOf(false)

// 접근할 때 value가 필요함. -> if(uiState.value) 

var uiState: MutableState<Boolean> by mutableStateOf(falsae)
// 접근할 때 value 없이 접근 가능 -> if(uiState)
```

**MutableState**

```kotlin
@Stable
interface MutableState<T> : State<T> {
    override var value: T
    operator fun component1(): T
    operator fun component2(): (T) -> Unit
}
```

MutableState의 값은 변경이 가능하다. 따라서 setValue의 구현을 필수적이다.

```
//setter
inline operator fun <T> MutableState<T>.setValue(thisObj: Any?, property: KProperty<*>, value: T) {
    this.value = value
}

//getter
inline operator fun <T> State<T>.getValue(thisObj: Any?, property: KProperty<*>): T = value

```