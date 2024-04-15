# CustomView

```kotlin
일반적인 뷰로는 원하는 UI를 구현할 수 없는 경우
직접 커스텀 뷰를 만들어서 구현할 수 있다.
그리고 만든 커스텀뷰는 재사용할 수 있어서 사용성이 높다.
```

View는 다음과 같이 구성되어 있다.

![image](https://github.com/jaehan4707/Daily_Learning_Log/assets/99114456/4cf4cdb1-9341-494c-8b8f-25c48fddb548)

최상단에 위치하고 있으며, 사용자 인터페이스를 구축하고, 유저의 입력 이벤트를 처리한다.

커스텀 뷰의 핵심은 onMeasure, onDraw, onLayout이다.

예를 들어서 설명하면 커다란 종이(뷰)에 우리는 원하는 그림(UI)를 그려야 한다.

거기서 **종이의 가로, 세로 등 크기를 측정**하는것이 `OnMeasure`, 도화지에서 **그릴 위치를 정하는것**이 `onLayout`, 어떤 **그림을 그릴것**인지를 결정하는것이 `onDraw`입니다.

위에 그림에서 보이는것처럼 View는 ViewGroup을 가질 수 있습니다.

뷰가 그려지는 순서는 부모가 그려지고, 그 후에 자식들이 그려집니다.

예를 들어 ZoomView라는것이 있고, 하위에 NodeView, LineView라는 자식이 있을 경우

우선적으로 ZoomView를 그리고, NodeView, LineView를 순서대로 그릴것입니다.

---

VIew class는 4개의 생성자를 가질 수 있습니다.

- View(Context context) 코드에서 동적으로 뷰를 생성할 때 사용할 수 있는 간단한 생성자
- View(Context context, AttributeSet attrs) : xml에서 생성할 때
- View(Context context, AttributeSet attrs, int defStyleArr) : ThemeStyle과 함께 뷰를 생성할 때
- View(Context context, AttributeSet attrs, int defStyleArr, int defStyleRes) : ThemeStyle 또는 Style로 xml에서 뷰를 생성할 때

```kotlin
class NodeView(
    val mindMapContainer: MindMapContainer,
    context: Context,
    attrs: AttributeSet?,
)
```

## View LifeCycle

![image](https://github.com/jaehan4707/Daily_Learning_Log/assets/99114456/1045fb91-a285-416e-ace0-4f88879ace96)

VIew 계층 구조는 부모 노드(ViewGroups)에서 리프노드(Child Views)의 트리구조와 같기 때문에 순회단계라고 한다. 따라서 각 메서드는 부모에서 시작해서 마지막노드까지 순회를 한다.

![image](https://github.com/jaehan4707/Daily_Learning_Log/assets/99114456/04d1b192-08aa-497b-a419-2e35f1b2d89b)

## OnMeasure()

```kotlin
이 뷰 및 모든 하위 요소의 크기 요구사항을 확인하기 위해 호출됩니다.
```

![image](https://github.com/jaehan4707/Daily_Learning_Log/assets/99114456/d3a01355-a4ec-43cf-bcd5-0f8c5881a3f0)

파라미터로는 2개의 값을 받는다.

- widthMeasureSpec : 부모뷰에 의해 적용된 width 요구사항
- heightMeasureSpec : 부모뷰에 의해 적용된 height 요구사항

### MeasureSpec

부모에서 자식으로 전달되는 레이아웃 요구사항을 캡슐화한다.

MeasureSpec은 크기와 모드로 구성되며 세가지 모드가 있다.

1. MeasureSpec.EXACTLY : 부모뷰가 자식뷰의 정확한 크기를 결정한다.
2. MeasureSpec.AT_MOST : 자식뷰는 지정된 크기까지 원하는 만큼 커질 수 있다.
3. MeasureSpec.UNSPECIFIED : 부모뷰가 자식뷰에 제한을 두지 않기 대문에 자식뷰는 원하는 크기가 될 수 있다.

```kotlin
override fun onMeasure(
        widthMeasureSpec: Int,
        heightMeasureSpec: Int,
    ) {
        var width = 0
        var height = 0
        for (index in 0 until childCount) {
            val child = getChildAt(index)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            width = maxOf(width, child.measuredWidth)
            height = maxOf(height, child.measuredHeight)
        }
        setMeasuredDimension(
            resolveSize(width, widthMeasureSpec),
            resolveSize(height, heightMeasureSpec),
        )
    }
```

우리는 부모뷰에서 자식뷰의 크기를 측정해서 가장 큰 크기의 width와 height를 구했고, setMeasureDimension을 호출해서 너비와 높이를 설정했다.

---

## onLayout

```kotlin
뷰가 모든 하위에 크기와 위치를 할당해야 할 때 호출됩니다.
```

```kotlin
protected void onLayout (boolean changed, 
                int left, 
                int top, 
                int right, 
                int bottom)
```

- 일반적으로 View가 현재 범위 내에서 더이상 맞지 않는다고 판단하면 자체적으로 호출된다.

```kotlin
override fun onLayout(
        changed: Boolean,
        l: Int,
        t: Int,
        r: Int,
        b: Int,
    ) {
        val childCount = childCount
        for (index in 0 until childCount) {
            val child = getChildAt(index)
            val childLeft =
                (paddingLeft + (child.layoutParams as MarginLayoutParams).leftMargin)
            val childTop =
                (paddingTop + (child.layoutParams as MarginLayoutParams).topMargin)
            val childRight = Int.MAX_VALUE
            val childBottom = Int.MAX_VALUE
            child.layout(childLeft, childTop, childRight, childBottom)
        }
    }
```

우리는 마인드맵의 진행방향이 고정되어있었고, 화면에 대한 스크롤을 계속해서 늘려주고 싶어서

left와 top을 고정시키고, right와 bottom은 무한해서 늘어나도록 자식뷰를 위치시켰다.

---

## onDraw

```kotlin
뷰가 그림을 그릴 때 호출된다.
```

```kotlin
protected void onDraw(@NonNull Canvas canvas) {
        throw new RuntimeException("Stub!");
    }
```

- canvas 객체가 반드시 필요하다.
- onDraw는 많은 시간이 소요되고, 여러번 호출되므로, 해당 단계에서 객체를 생성하는것은 오버헤드가 크다.
- 해당 단계를 최적화하는것이 Custom View의 핵심이다.

```kotlin
override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawAttachedNode(canvas)
        drawTree(canvas)
        mindMapContainer.selectNode?.let { selectedNode ->
            makeStrokeNode(canvas, selectedNode)
        }
    }
```

---

## Invalidate() vs RequsetLayout

### Invalidate

> 커스텀 뷰에서 UI를 갱신하기 위해 뷰를 다시 그리도록 요청하는 메소드이고, 호출의 결과는 onDraw이다.
>

### RequestLayout

> 뷰의 레이아웃을 다시 요청하는 메소드이다.
해당 메서드를 호출하면 뷰의 크기와 위치를 다시 계산하는 Measure 단계부터 다시 시작한다.
>

각각의 쓰임이 다른 만큼 호출되는 상황은 다르다.

invalidate()는 단순히 뷰를 다시 그릴 때 사용되는 함수로, 버튼의 색깔이나, text가 변경될때

호출되며, onDraw 함수를 호출해서 뷰를 업데이트한다.

requestLayout은 뷰의 크기와 위치를 다시 계산할 때 사용되는 함수이고, 뷰의 사이즈가 변경될 때

호출된다.

# 참고

https://developer.android.com/develop/ui/views/layout/custom-views/create-view?hl=ko

https://labs.brandi.co.kr//2021/10/14/jeonhs.html

https://www.charlezz.com/?p=29013