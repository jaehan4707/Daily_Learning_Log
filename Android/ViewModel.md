# ViewModel

ViewModel에 대해선 많이 들어봤지만,

AAC ViewModel과 MVVM 아키텍처의 ViewModel이 어떤 차이가 있는지에 대해서 적어볼 예정이다.

## MVVM ViewModel

```
MVVM의 ViewModel의 역할dms View와 Model 사이에서 데이터를 관리해주고 
바인딩 해주는 역할이다.
```

## AAC ViewModel

```
AAC의 ViewModel은 화면 회전과 같은 환경에서 데이터를 보관하고 
라이프사이클을 알고있어서 Activity나 Fragment의 Destory시
Onclear 함수를 통한 데이터 해제의 역할을 하고 있다.
```

쉽게 얘기하면 회전과 같은 상태 변화가 일어나는 환경에서 데이터가 변화하지 않았지만, 회전으로 인한 트리거때문에 Activity가 재생성되는 경우 데이터를 다시 로딩하는것은 불필요하고, 이를 방지하기 위해 AAC ViewModel이 탄생했다.

즉 두 `ViewModel`은 다른 개념이고 연관성은 없다.

**하지만 AAC의 ViewModel을 MVVM의 ViewModel로 사용은 가능하다.**

`MVVM ViewModel`의 역할은 뷰와 모델 사이에 데이터를 관리해주고 바인딩해주는것이다.

`ViewModel`이 가지고 있는 데이터를 관찰가능하게 해주고, 뷰는 데이터 바인딩으로 그것을 구독하고 있으면 반응형으로 구현할 수 있다.

추가로 `AAC ViewModel`로 구현했기 때문에 화면 회전 시 `데이터를 유지`시켜주는 기능까지 있다.

---

# 결론
```
MVVM 패턴에서 ViewModel은 AAC의 ViewModel과 다르다.
굳이 MVVM 패턴을 구현하기 위해서 AAC의 ViewModel을 사용하지 않아도 되지만, 
이는 굉장히 난이도가 있는 개발이다.
MVVM 패턴을 사용하는데 DataBinding을 사용하지 않는다면 그것은 잘못된 구현이다!
MVVM의 목적은 View와 ViewModel의 연결을 느슨하게 하는것이다.
이러한 view 관련 코드를 Activity나 Fragment 딴에서 하는것은 
MVVM 패턴을 잘못이해하고 있는것이다.
```