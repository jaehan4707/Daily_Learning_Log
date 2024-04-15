# 의존성 주입

## 의존성 주입이란?

의존성 주입이란 클래스 간 의존성을 클래스 외부에서 주입하는 것을 뜻한다.

의존성 주입 그 자체는 클래스간 의존성을 외부에서 주입하는것을 뜻하지만,

일반적으로 우리가 사용하는 의존성 주입은 클래스에 대한 의존성의 인터페이스화를 통한  코드 유연성 증대 + 클래스의 인스턴스를 외부에서 생성하여 주입을 동시에 하는 방향으로 진행된다.

---

## 의존성이란?

객체지향 프로그래밍에서 클래스 간에 의존성이 있다는 것은 클래스간에 의존 관계가 있다는 것을 뜻한다.

즉 클래스간에 `의존(Dependency)` 관계가 있다는 것은 한 클래스가 바뀔 때 다른 클래스가 **영향을 받는다는것**을 뜻한다.

코드를 통한 예시이다.

```kotlin
class BlahBlah {
    private val language = Korean()
    
    fun say(){
            language.speakKorean()
    }
}
class Korean{
    fun speakKorean(){
            println("Speak Korean")
        }
}
```

BlahBlah class는 Korean 클래스를 인스턴스로 가지고 있다.

이는 곧 BlahBlah 클래스는 Korean에 의존한다는 뜻이다.

여기서 왜 의존성이 있다는것이 문제인 이유는 다음과 같은 상황이 생길 수 있기 때문이다.

우리의 BlahBlah의 언어가 한국어가 아닌 영어로 바뀐것이다.

```kotlin
class English{
	//기존의 speakKorean을 수정한 함수
    fun speakEnglish(){
        println("Speak English")	
    }
}
```

문제는 English 클래스는 speakKorean이 없다는 사실이다.

이런 경우 기존의 코드 BlahBlah는 오류가 생길것이다.

이렇게 하나의 클래스가 다른 클래스에 의존할 경우 의존하는 클래스가 변경된다면, 의존하고 있는 클래스의 코드는 병견된다.

**해당 케이스에선 적은 양의 코드가 영향을 받았지만, 실제로 규모가 큰 프로젝트나 클래스에서는  클래스 하나를 수정하면 많은 양의 코드를 수정해야하고, 이는 굉장히 비효율적이고, 프로그램적으로도 안정성이 떨어진다.**

이를 방지하기 위해 클래스 사이에 `인터페이스`와 같이 **추상화된 객체**를 사용해서 클래스로부터 의존성을 없앤다.

Korean과 English는 인터페이스를 상속받아 해당 함수를 구현하고, 구체화 된 함수의 내용이 바뀌더라도 호출자체는 변경되지 않기 때문에 BlahBlah는 변경될 일이 없다.

```kotlin
interface Language{ 
    fun speakLanguage()
}
```

```kotlin
class English : Language {
    fun speakLanguage(){
            speakEnglish()
    }
    fun speakEnglish(){
            println("BlahBlah: English")
    }
}
class Korean : Language{
    fun speakLanguage(){
        speakKorean()
    }
    fun speakKorean(){
        println("BlahBlah: Korean")
    }
}
```

BlahBlah 클래스는 인터페이스의 메서드를 통해 speak하기 때문에 BlahBlah의 Language가 바뀌더라도 변경될 일은 없어진다.

이렇게 Interface를 통해 특정 클래스에 대한 의존성을 보다 유연하게 만들 수 있다.

---

## 주입

> 주입은 클래스 외부에서 객체를 생성하여 해당 객체를 클래스 내부에 주입하는 것이다.
>

예를 들어보자.

Person[A,B,C]가 각각 게임을 하고 있고, 해당 게임은 하나의 캐릭터를 이용해서 진행된다.

```kotlin
class Person {
    val champion : Champion = Jax()
    
    fun playGame(){
        champion.attack()
        champion.move()
        ...
    }
}
```

Person A, Person B, Person C는 각 클래스마다 Jax 챔피언을 가지고 있다.

Person은Champion에게 강하게 결합되어있다.

만약 A,B,C가 챔피언을 다른 챔피언으로 바꾼다면 어떻게 될까?

A,B,C 클래스들의 코드들을 전부 바꿔야 한다.

이를 해결하기 위해 `주입`을 사용한다.

즉 champion을 결정짓는 행위를 외부에서 한다는 뜻이다.

이러한 champion 인스턴스를 저장하는 공간을 Container라고 하고, 이 Container를 Person class에 주입한다.

```kotlin
class PersonA {
    lateinit var champion : Champion
    fun setChampion(Champion champion)
        this.champion = champion
    fun playGame(){
        ...
    }
}
```

PersonA의 챔피언은 해당 클래스가 아닌 주입하는곳에서 권한이 있는것을 확인할 수 있다.

이것을 IOC(Inversion Of Control)=제어의 역전 이라고 부른다.

이렇게 바꾼 구조를 통해 Person class는 Champion의 구현체를 외부에서 주입 받을뿐이다.

---

## 의존성 주입의 의의

- 클래스 간의 `결합도`가 약해져, `리펙토링`이 쉬워진다.
- 클래스간의 결합도가 약해져 특정 클래스를 `테스트`하기 편해진다.(Stub,mock 객체)
- 인터페이스 기반 설계는 `코드를 유연`하게 한다.[확장에 용이]

---

Android에서 의존성 주입이 어려운이유

- Android 클래스가 Framework에 의해 인스턴스화 됨.

## Dagger2

> 자바와 안드로이드를 위한 강력하고 빠른 의존성 주입 프레임워크
>

특징

- 컴파일 타임에 그래프를 구성
- 생성된 코드는 명확하고 디버깅이 가능함
- 리플랙션 사용X,  런타임 바이트 코드 생성X
- 자원 공유의 단순화
- 작은 라이브러리 크기

단점

- 배우기 어렵고 , 프로젝트 설정이 힘들다.(의존성 주입 등의 개념)
- 간단한 프로그램을 만들 때는 배보다 배꼽이 더 클 정도로 번거로운 작업이다.
- 같은 결과에 대한 다양한 방법이 존재한다.

### 의존성 주입 프레임워크의 궁극적인 목표

- 정확한 사용방법을 제안
- 쉬운 설정 방법
- 중요한 것들에 집중할 수 있도록 한다.

---

# Hilt

애플리케이션에서 DI를 사용하는 표준적인 방법을 제공한다.

목표

- Dagger 사용의 단순화
- 표준화된 컴포넌트 세트와 스코프로 설정과 가독성/이해도 쉽게 만들기
- 쉬운 방법으로 다양한 빌드 타입에 대해 다른 바인딩을 제공

특징

- Dagger 2 기반의 라이브러리이며, 표준화된 Dagger2 사용법을 제시한다.
- 보일러플레이트 코드 감소
- 프로젝트 설정 간소화
- 쉬운 모듈 탐색과 통합
- 개선된 테스트 환경
- Androidx 라이브러리의 호환

```kotlin
class foodRepository @Inject constructr(
    private val db: FoodDatabase
) {
    fun fetch(id:String){}
}
```

# Hilt 설정 방법

### Application 설정

```kotlin
@HiltAndroidApp
class myApp: Application()
```

모든 의존성 주입의 시작점이다.

### Activity, Fragment

```kotlin
@AndroidEntryPoint
class myActivity:AppCompatActivity(){
    @inject lateinit var repository: FoodRepository
    
    .../
}
```

### Module

```kotlin
@Installin(ApplicationComponent::class)
@Module
object FoodDataModule{
    @Provides
    fun provideFoodDB(@ApplicationContext context : Context)=
      .../
} 
```

---


![image](https://github.com/jaehan4707/Daily_Learning_Log/assets/99114456/c25b7e3d-413c-4d9f-98e8-2d06b3f83b75)

@HiltAndroidApp → ApplicationComponent 생성

@InstallIn → 해당 컴포넌트에 모듈이 추가된다.

@AndroidEntryPoint → ApplicationComponent의 하위인 ActivityComponent가 생성되고,
MemoRepository를 주입받을 수 있다.

---

## Hilt Annotation

### @HiltAndroidApp

- 반드시 Application 클래스에 추가해야한다.
- onCreate에서 byte 코드로 변환

```kotlin
@HiltAndroidApp
class MyApplication:Application()
```

MyApplication은 아래와 같은 코드를 생성한다.

```kotlin
class Hilt_MyApplication:Application(){
	//여기서 컴포넌트 생성 및 주입 코드가 작성됨.
}
```

### ByteCode transformation

MyApplication 소스 코드을 컴파일하면 MyApplication 바이트 코드가 만들어진다.

생성된 바이트 코드는 Gradle에 의해 Hilt_MyApplication을 상속하는 MyApplication 바이트 코드

즉 Gradle에 의해 자동으로 MyApplication은 Hilt_MyApplication을 상속

### @AndroidEntryPoint

- 어노테이션이 추가된 안드로이드 클래스에 DI 컨테이너를 추가
- @HiltAndroidApp의 설정 후 사용 가능

vs @HiltAndroidApp

```kotlin
@HiltAndroidApp -> Component 생성
@AndroidEntryPoint -> SubComponent 생성
```

@AndroidEntryPoint를 지원하는 타입

- Activity
- Fragent
- View
- Service
- BroadcastReceiver

## Hilt Component Hierarchy

![image](https://github.com/jaehan4707/Daily_Learning_Log/assets/99114456/43e6e717-2cec-4553-b34f-42b5215a53fc)

### Hilt Component의 특징

- Dagger와 다르게 직접적으로 인스턴스화 할 필요가 없음
- 표준화된 컴포넌트 세트와 스코프를 제공한다.
- 컴포넌트들은 계층으로 이루어져 있으며, 하위 컴포넌트는 상위 의존성에 접근할 수 있다.(직계)

## Hilt Scope

![image](https://github.com/jaehan4707/Daily_Learning_Log/assets/99114456/4db5fcd4-59cc-401c-8fe1-9f36093aa287)

```kotlin
scope 어노테이션을 통해 동일 인스턴스를 공유할 수 있다.
```

Scope Annotation이 없는 코드

```kotlin
class FoodRepository @Inject constructor(
    private val db: FoodDatabase
) {
	//....
}
```

여러 액티비티에서 같은 레포지토리를 사용하더라도, Singleton 어노테이션이 없어서 각기 다른 인스턴스를 사용한다. → 레포지토리 인스턴스가 여러번 생성 → 메모리 낭비

```kotlin
@Singleton
class FoodRepository @Inject constructor(
    private val db: FoodDatabase
) {
	//....
}
```

모듈에서 사용하는 스코프는 반드시 InstallIn에 명시된 component와 쌍을 이뤄야한다.

### Default Component Binding

- ApplicationComponent → Application
- ActivityRetainedComponent → Application
- ActivityComponent → Application,Activity
- FragmentComponent → Application,Activity,Fragment
- ViewComponent → Application,Activity,View
- ViewWithFragmentComponent → Application, Activity, Fragment, View
- ServiceComponent → Application, Service

## Hilt Module

![image](https://github.com/jaehan4707/Daily_Learning_Log/assets/99114456/6097ea30-aaf9-4bd6-9ab9-ea1dbb828cd9)

## @InstallIn

- hilt가 생성하는 DI 컨테이너에 어떤 모듈을 사용할 지 가리킨다.
- 해당 모듈이 어떤 컴포넌트에 설치될지 명시하고, 컴파일 타임에 관련 코드를 생성한다.
- 올바르지 않은 컴포넌트 or 스코프일시 컴파일 에러가 발생

![image](https://github.com/jaehan4707/Daily_Learning_Log/assets/99114456/b464bc08-9b50-4b32-b078-7141f6ebcd48)

```java
InstallIn에 명시한 컴포넌트에 설치된다.
```

만약에 ActivityComponent와 FragmentComponent 둘다 MyModule이
필요할 경우는 어떻게 해야할까?

![image](https://github.com/jaehan4707/Daily_Learning_Log/assets/99114456/cd6eaa46-7528-46f7-80a5-30b8d623fcf0)

상위 컴포넌트의 모듈을 설치하면 된다.

하위컴포넌트는 상위 컴포넌트 의존성에 접근할 수 있기 때문이다.

### 제약사항

- @Module 클래스에 @InstallIn이 없으면 컴파일 에러가 발생함.

## @EntryPoint

- Hilt가 지원하지 않는 클래스에서 의존성이 필요한 경우 사용
  Ex) ContentProvider, DFM, Dagger를 사용하지 않는 3rd-party 라이브러리

특징

- @EntryPoint는 인터페이스에만 사용
- @InstallIn이 반드시 함께 있어야 한다.
- EntryPoints 클래스의 정적 메서드를 통해 그래프에 접근

```kotlin
@EntryPoint
@InstallIn(ApplicationComponent::class)
interface FooBarInterface{
    fun getBar(): Bar
}

val bar = EntryPoints.get(
        applicationContext,
        FooBoarInterface::class.java
).getBar()
					
```

Hilt는 Jetpack 라이브러리 클래스를 위해 Extension을 제공한다.

- ViewModel, WorkManager

---

# 참고

https://www.youtube.com/watch?v=gkUCs6YWzEY