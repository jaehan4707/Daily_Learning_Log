> **Q1) Android에서 Activity와 Fragment의 주요 차이점은 무엇이며, 각각의 사용 사례를 설명해주세요.**
>

Activity와 Fragment의 차이점은 세가지가 있습니다.

첫번째로, 고유의 생명주기에서 차이를 보이고 있습니다.
Fragment는 Activity의 생명주기를 따르지만, onAttach, onDetach 등 Fragment 고유의 생명주기가 추가로 있습니다.

두번째로, UI 구성에서 차이가 있습니다.

Activity는 화면의 기본 단위이고, Fragment는 Activity 내에서 화면의 일부를 구성할 때 사용됩니다.

마지막으로, Activity는 독립적으로 동작할 수 있지만, Fragment는 반드시 Activity에 종속됩니다.

Fragment를 사용하는 경우는 화면을 탭으로 나누거나, 동일한 Activity 내에서 다양한 화면 전환이 이루어지는 상황에서 주로 사용합니다.

Activity를 주로 사용하는 경우는 전혀 다른 기능으로 전환하거나, 독립적인 화면 단위가 필요한 경우 주로 사용합니다.

> **Q2) Activity와 Fragment의 생명주기가 다르다는 점을 알아을 때, Fragment에서 생명주기 관리의 어려움을 어떻게 해결할 수 있을까요?**
>

Activity와 Fragment의 생명주기가 다른 점은 Fragment에서 추가적인 생명주기 onAttach, onDetatch 등의 단계가 있다는 점입니다. 이러한 추가적인 고유의 생명주기 덕분에 관리가 어려운데요,
onDetatch 단계에서는 화면이 보여지지 않는 상황이기 때문에 viewBinding의 할당을 해제하거나, UI 구성 요소를 할당 해제해 메모리를 관리하는 과정을 통해 어려움을 해결할 수 있을것 같습니다.

프래그먼트는 onDestroyView  단계에서 뷰가 제거되지만, Fragment 인스턴스는 여전히 메모리에 남아있을 수 있기 때문에  onDestroyView에서  viewBinding을 null로 초기화해 메모리 누수를 방지할 수 있습니다.

> **Q3) Fragment에서 ViewModel과 LiveData를 사용해 데이터를 관리한다고 가정할 때, ViewModel의 생명주기와 Fragment 생명주기가 충돌하지 않도록 설계하려면 어떤 점을 주의해야 할까요?**
>

ViewModel은 기본적으로 Activity, Fragment의 생명주기를 따르지 않고, 독립적인 생명주기를 가지고 있습니다. 따라서 뷰가 제거되어도 데이터를 유지합니다.

이러한 생명주기의 차이 때문에 Fragment의 view가 파괴된 상태에서 viewModel이 UI를 업데이트 하려고 하면 NPR이 발생할 수 있습니다.

이를 해결하기 위해서는 ViewModel이 Fragment 생명주기에 따르도록 초기화를 해야합니다.
만약 viewModel이 View의 Context를 참조로 가진다면, View가 메모리에서 해제되지 않아 메모리 누수가 발생할 우려가 있기 때문에 이 점도 주의해야 합니다.

추가로, 데이터바인딩 되는 LiveData나 StateFlow도 viewLifecycleOwner로 생명주기를 인식하도록 만들어주면, 뷰가 없어질 때 데이터를 업데이트 하는 상황을 방지할 수 있습니다.

> **Q4) Activity와 Fragment의 생명주기에 대해서 설명해주세요.(호출 순서, 간단하게 하는 일)**
>

**Activity**

1. onCreate() : Activity가 생성될 때 호출되며, UI 초기화, ViewBinding, 데이터 바인딩등을 수행
2. onStart() : Activity가 사용자와 상호작용을 시작할 준비가 되었을 때 호출되며, UI가 보이기 시작
3. onResume() : Activity가 화면에 완전히 표시되고 사용자와 상호작용을 할 준비가 되면 호출된다.
4. onPause() : Activity가 다른 Activity나 화면에 의해 가려질 때 호출되며, 자원을 해제하고 UI를 비활성화하며, 데이터 저장 등을 진행할 수  있다.
5. onStop() : Activity가 화면에 완전히 사라질 때 호출된다. 더 이상의 상호작용을 받지 않으며, Activity가 백그라운드로 이동
6. onRestart() : Activity가 다시 시작되기 전에 호출되며, onStop 이후 onStart가 호출되기 전에 실행
7. onDestory() : Activity가  완전히 종료되거나 시스템에서 제거되기 전에 호출된다. Activity가 사라지기 전에 자원을 해제하고 필요한 종료 작업을 수행할 수 있다.

**Frgament**

1. onAttach() : Fragment가 Activity에 연결될 때 호출, 상호작용이 시작되는 시점
2. onCreate() : Fragment가 생성될 떄 호출되며, 초기화 작업, 필드 설정 등을 진행. Activity의 onCreate와 유사한 역할
3. onCreateView() : Fragment의 UI를 생성할 때 호출되며, 레이아웃을 인플레이트하고, UI 요소를 바인딩하거나 설정. 이 단계에서 UI를 반환해야 화면이 표시된다.
4. onActivityCreated() : onCreateView가 완료되고, Activity의 onCreate가 호출된 후 호출된다. 이 시점에서 뷰와 관련된 초기화 작업을 진행할 수 있다.
5. onStart() : Fragment가 사용자와 상호작용을 시작할 준비가 되었을 때 호출된다. UI가 화면에 표시되며, 이 시점에서는 Fragment가 포그라운드에 등장
6. onResumse() : Fragment가 화면에서 활성화되고, 사용자가 상호작용을 시작하는 시점
7. onPause() : Fragment가 다른 Fragment나 Activity에 의해 가려질때 호출, Activity와 유사
8. onStop : Fragment가 화면에서 사라지거나 백그라운드로 이동할 때 호출된다.
9. onDestroyView() : Fragment의 뷰가 파괴될때 호출, UI 리소스나 뷰 관련 자원을 해제
10. onDestory() : Fragment가 완전히 종료될 때 호출, 객체가 메모리에서 제거되기 전에 자원 정리 작업을 할 수 있다.
11. onDetach() : Fragment가 Activity와 분리될 때 호출, Fragment와 Activity 간 연결이 끊김

**Fragment는 추가적인 생명주기(onAttach(), onCreateView, onDestoryView, onDetach()등)이 있기 때문에 화면을 동적으로 변경하는 데 유용하다.**