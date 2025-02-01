> Q1) Context란 무엇이며, 왜 중요한가요?
>

Context란 Android에서 애플리케이션 환경에 대한 정보와 시스템 리소스에 접근할 수 있도록 하는 인터페이스입니다.

Context를 올바르게 사용해야 하는 이유는 잘못된 Context 참조로 인해 메모리 누수가 발생할 수 있기 때문입니다. 예를 들어, Activity의 Context를 잘못 보관하면 Activity가 종료되었음에도 참조가 남아 GC 대상이 되지 않아 메모리 누수가 발생할 수 있습니다.
따라서 상황에 따라 적절한 Context를 사용하는것이 중요합니다.

> Q2) 그렇다면 ApplicationContext**와** ActivityContext**의 차이점**은 무엇인가요?
>
>
> 각각 어떤 상황에서 사용해야 할까요?
>

ApplicationContext와 ActivityContext 차이는 생명주기와 활용 범위에 있습니다.

ApplicationContext는 애플리케이션 전역에서 유지되는 Context로, 앱이 실행되는 동안 계속 살아있습니다.
따라서 Service, 내부 DB, 앱 전체 설정 저장과 같은 경우에 적합합니다.

ActivityContext는 해당 액티비티의 생명주기에 따라 생성되고 소멸되는 Context로, UI 관련 작업에서 주로 사용됩니다.

메모리 누수를 방지하기 위해 올바른 Context를 선택하는 것이 중요합니다.

예를 들어 ApplicationContext를 사용해야 할 곳에서 ActivityContext를 사용하면 액티비티 종료가 되었을 때 메모리 누수가 발생할 수 있습니다.

> Q3) Fragment에서 Context를 사용할 수 있는데, Fragment에서 Context를 사용할 때 주의해야 할 점은 무엇인가요?
>

Fragment는 독립적인 Context가 아닌, 속해 있는 Activity의 Context를 참조합니다.

따라서 Activity가 아직 attach 되지 않은 경우에 getContext, requireContext와 같은 context를 얻기 위한 함수를 호출할 경우 NPR이 발생할 수 있습니다.

즉 생명주기에 맞춰 Context를 사용해야 합니다.

> Q4) ApplicationContext와 ActivityContext를 사용할 때,
>
>
> **잘못된 Context 사용으로 인해 발생할 수 있는 문제점**에는 어떤 것들이 있을까요?
>

**잘못된 Context 사용으로 인해 발생할 수 있는 문제점은 다음과 같습니다.**

**메모리 누수 (Memory Leak) 발생 가능**

• ActivityContext를 장기간 유지하는 객체 (예: Room, Glide, ViewModel)에서 사용하면,

해당 Activity가 종료된 후에도 Context가 해제되지 않아 **GC에서 제거되지 않고 메모리 누수가 발생**할 수 있습니다.

따라서, **전역적으로 유지되는 객체에서는 반드시** ApplicationContext**를 사용해야 합니다.**

**UI 관련 작업에서 ApplicationContext를 사용할 경우 문제 발생**

• ApplicationContext는 **UI 요소 (예: Dialog, View, WindowManager)를 직접 조작할 수 없습니다.**

• 예를 들어, AlertDialog.Builder(applicationContext)를 호출하면 **BadTokenException**이 발생할 수 있습니다.

UI와 관련된 작업(예: Theme가 적용되는 뷰)은 반드시 ActivityContext를 사용해야 합니다.

3️⃣ **리소스 접근 문제**

• ApplicationContext를 사용할 경우, 일부 리소스가 Activity의 테마에 의존하는 경우 문제가 발생할 수 있습니다.

• 특히 **스타일/테마가 적용된 UI 컴포넌트는** ApplicationContext**에서 올바르게 동작하지 않을 수 있습니다.**

> Q5) Room에서 ApplicationContext를 사용해야 하는 이유
>

Room은 데이터 베이스 객체를 생성하고 관리하는 라이브러리입니다.
Room 인스턴스는 앱의 전반적인 데이터 관리를 위해 전역적으로 유지되는것이 일반적입니다.
만약 ActivityContext를 사용하면, Activity가 종료될 때 데이터베이스 인스턴스도 함께 종료될 위험이 있습니다.
따라서 앱이 실행되는 동안 계속 유지될 필요가 있는 Room DB 객체는 ApplicationContext를 사용해야 합니다.