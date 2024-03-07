# Heap

## Heap이란?

- 완전 이진 트리의 일종으로 우선순위 큐를 위하여 만들어진 자료구조이다.
- 여러 개의 값들 중에서 최대값이나 최솟값을 빠르게 찾아내도록 만들어진 자료구조이다.
- 힙은 일종의 반정렬 상태(느슨한 정렬 상태)를 유지한다.
    - 우선순위가 큰 값이 상위에 있다.
- 힙 트리에서는 중복된 값을 허용한다. (이진 탐색 트리에서는 중복된 값을 허용하지 않는다)

## Heap의 종류

### max heap

```kotlin
부모 노드의 키 값이 자식 노드의 키 값보다 크거나 같은 완전 이진 트리
```

### min heap

```kotlin
부모 노드의 키 값이 자식 노드의 키 값보다 작거나 같은 완전 이진 트리
```

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/a2d8f38a-1e2a-4bc6-bede-27aea7be0ccc/Untitled.png?id=2a7f5c93-de41-4fd6-8b02-e09a9531e7b9&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1709913600000&signature=yylBido32FJ74PXA_g5fqwJ5yjtDDQWKB0HTWiOhtlA&downloadName=Untitled.png)| ![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/9e6c805a-dafa-47e1-a025-dd6032ad038c/Untitled.png?id=c045e0ce-10c7-40f5-826d-0ecd8a347d15&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1709913600000&signature=-DtUWsTfxWFizrVj0hm-jS9tBtCOxjuqhGb8Thtmekw&downloadName=Untitled.png) 
---|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
**maxHeap**| **minHeap**                                                                                                                                                                                                                                                                                                                                           |


항상 루트 노드의 값은 우선순위가 가장 높다.
max heap이라면 최대값, min heap이라면 최소값이다.    
이러한 성질 때문에 최대값이나 최소값을 가장 빠르게 찾아내는 연산(O1)을 하기 위해서 max heap이나 , min heap이 사용된다.

### 자료를 추가하는 경우

1. 새로운 노드를 트리의 맨 뒤에 추가한다.
    1. 항상 완전 이진 트리 형태를 만족 해야한다.
2. 추가된 노드와 부모 노드를 비교해서, 우선순위에 따라 자리를 교체한다.

새로운 노드를 8에 추가하는 상황

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/9ce9916e-e1e0-4f96-9471-c4ca34d74a1c/Untitled.png?id=9d7eeb36-50cb-4403-b840-039bf174dd40&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1709913600000&signature=Saux7MYudGYfWlkFEOeQ-iHldeKVbQxSoNBIMaveqzc&downloadName=Untitled.png)|![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/e9a1abb0-6a6e-4715-b60c-96e76a0691e5/Untitled.png?id=9b80d97b-bfd1-4b6f-8b46-925bb12d186f&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1709913600000&signature=wquGkpG4q94O8zP7d-jYla8n8tBT_UBuxDqD2fDOdvM&downloadName=Untitled.png)
---|---|
- 8은 트리의 맨 뒤인 5의 자식으로 추가된다.
- 8과 5를 비교한다.
- 8보다 5가 크기 때문에 위치를 바꾼다.
- 8과 7을 비교한다.

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/752dccfa-d069-4428-8589-b2e07b99306d/Untitled.png?id=a984aa99-fa7f-476b-a796-cbe036e18eb2&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1709913600000&signature=5-z34ec8FilPaIdvPSu5TZmhJYaZFQPLI6auHZvmcE0&downloadName=Untitled.png)

- 8이 더 크기 때문에 7과 자리를 바꾼다.
- 8과 9를 비교한다.
- 8이 더 작기 때문에 위치를 바꾸지 않고, 삭제가 종료된다.

```kotlin
heap에서의 추가하는데 최악의 상황은 숫자가 가장 클 경우이다.
추가된 노드가 부모 노드의 위치까지 이동해야하고,이동하는 과정에서 비교하기 때문이다. 
여기서 발생하는 연산의 수는 트리의 depth가 될것이다.
트리 노드의 개수를 n이라고 할 때 높이는 log2n이다.
n이 4일때, h는 2, n이 8일때는 h가3이다. 
따라서 heap에서의 삽입 시간복잡도는 O(logN)이다.
```

### 자료를 삭제하는 경우

max heap이나, min heap에서 삭제되는 자료는 우선순위가 가장 높은 root이다.

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/87c20ad3-2d9d-4130-b119-807c955354e9/Untitled.png?id=07db1e5a-d626-440b-8d44-a46e070719ff&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1709913600000&signature=a67T1jSUNv3Kh-1qPyUXcr3mng30Gy90-C_XzSd8yaI&downloadName=Untitled.png)|![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/366a1521-740c-4326-84a4-1a5ba06133b3/Untitled.png?id=8b55a4a4-2f15-4e83-8382-b95bf178eab3&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1709913600000&signature=fWzbj8UR2evBsQT1jikCnxTjsdwLEht0RIOWVTdOu04&downloadName=Untitled.png)
---|---|

- 루트노드가 삭제되고, 가장 뒤에 있는 노드를 루트로 옮긴다.

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/2ba88f4a-e2df-4713-b5c5-8b794c3a40bc/Untitled.png?id=c9dc43ea-db1a-4206-bf98-3eb7d711901e&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1709913600000&signature=PAAYk7168gvYCi1SZLSTS3U1HK1f4CDgcgUFrCuMLcI&downloadName=Untitled.png)|![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/7eb1f4e3-9a08-456f-b358-d9d2ae2b4bd1/Untitled.png?id=52bb8db6-c31d-49ca-b33a-4b4502f7ab1c&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1709913600000&signature=wkHrFJVwrj4U8Dwi3RE_fOvGSaWWKUTr7nQfDfJ5nBU&downloadName=Untitled.png)
---|---|

- 자식중에서 가장 큰 노드인 8과 5를 비교한다.
- 5가 더 작기 때문에 8과 5의 위치를 바꾼다.
- 5와 자식 중 큰 노드인 7을 비교한다.
- 7과 5의 위치를 바꾼다.

```kotlin
삭제도 삽입과 유사하게 최악의 경우는 트리의 제일 하단까지 이동하는 것이다.
O(logN)의 시간복잡도를 가진다.
```

## maxHeap 구현
```kotlin
import kotlin.math.max

class MaxHeap {

    private val nodes = mutableListOf<Int>()
    val _nodes = nodes

    fun insert(value: Int) {
        nodes.add(value)
        heapifyUp(nodes.size - 1)
    }

    private fun heapifyUp(idx: Int) {
        if (idx == 0)
            return

        val parentIdx = (idx-1)/ 2
        if (nodes[idx] > nodes[parentIdx]) { //자식이 더 클 경우
            swap(idx, parentIdx)
            heapifyUp(parentIdx)
        }
    }

    fun delete(): Int {
        if (nodes.isEmpty()) {
            return -1
        }
        val root = nodes.first()
        nodes[0] = nodes.last()
        nodes.removeLast()
        heapifyDown(0)
        return root
    }

    private fun heapifyDown(idx: Int) {
        val leftChild = idx * 2 + 1
        val rightChild = idx * 2 + 2
        var maxChild = idx
        if (leftChild >= nodes.size) { //더 바꿀 자식이 없다면
            return
        }
        if (rightChild >= nodes.size) {
            maxChild = leftChild
        } else {
            maxChild = if ( nodes[leftChild] > nodes[rightChild]) {
                leftChild
            } else {
                rightChild
            }
        }
        swap(idx, maxChild)
        heapifyDown(maxChild)
    }

    private fun swap(old: Int, new: Int) {
        val temp = nodes[old]
        nodes[old] = nodes[new]
        nodes[new] = temp
    }
}

fun main() {
    val maxHeap = MaxHeap()
    maxHeap.insert(2)
    maxHeap.insert(7)
    maxHeap.insert(6)
    maxHeap.insert(9)
    maxHeap.insert(5)
    maxHeap.insert(4)
    maxHeap.insert(3)
    println("after")
    maxHeap._nodes.forEach {
        print("$it ")
    }
    println("\ndelete")
    while (maxHeap._nodes.isNotEmpty()){
        val top = maxHeap.delete()
        print("$top ")
    }
}
```
![](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/73d8222c-48ae-4d2c-a64c-734aa9756cd0/Untitled.png?id=bb5bfa94-30f3-44b0-896f-c270b897fc7d&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1709913600000&signature=_x3y8ZJ3_cHSEEtXsK7TT5sHHnXtArMPTovY9lPnErw&downloadName=Untitled.png)