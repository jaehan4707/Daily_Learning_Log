## 문제 풀이

문제를 읽으면서 조건을 파악하고 차근차근 단계에 맞는 함수를 작성하면 되는 문제였다.

그 외에도 자료구조의 선택이 중요하다.

봄, 여름, 가을, 겨울에 맞는 동작을 구현해야 한다.

#### 봄

```
private fun spring(): ArrayList<Tree> {
    val aliveTrees = PriorityQueue<Tree>(compareBy { it.age })
    val deadTrees = arrayListOf<Tree>()
    while (!trees.isEmpty()) {
        val tree = trees.poll()
        if (graph[tree.x][tree.y] >= tree.age) {
            graph[tree.x][tree.y] -= tree.age
            aliveTrees.add(tree.copy(age = tree.age + 1))
        } else {
            deadTrees.add(tree)
        }
    }
    trees = aliveTrees
    return deadTrees
}
```

봄에서 중요한 점은 나이가 어린 나무부터 양분을 주는 것이다.

이를 위해 초기 코드에는 List 형태로 시작할 때 sort 해서 나이가 어린 나무순으로 정렬을 했다.

시간초과가 발생했고, 우선순위가 정해져 있다면 우선순위큐를 사용해도 될 것 같아서 우선순위큐를 사용했고 시간초과를 해결할 수 있었다.

봄은 문제에서 요구한 대로 나무가 있는 칸에 양분과 나무의 나이를 비교하는 것이다.

비교하는 과정에서 나무의 나이보다 적으면 해당 나무는 죽는다.

반대로 나이보다 양분이 많다면 나무는 나이가 한 살 증가한 상태로 살아남기 때문에 aliveTrees에 저장한다.

이는 곧 봄이 끝난 뒤 나무를 업데이트하기 위함이다.

죽은 나무들은 여름에서 사용되기 때문에 반환타입으로 처리했다.

#### 여름

```
private fun summer(dyingTrees: List<Tree>) { //죽은 나무 영양분 나눠주기
    dyingTrees.forEach { tree ->
        val treeEnergy = tree.age / 2
        graph[tree.x][tree.y] += treeEnergy
    }
}
```

여름은 죽은 나무들의 나이에 절반값을 나무가 위치했던 곳에 양분으로 더해준다.

#### 가을

```
private fun fall() { //가을엔 5의 배수인 나이가 번식하기
    val extraTrees = PriorityQueue<Tree>(compareBy { it.age })
    extraTrees.addAll(trees)
    while (!trees.isEmpty()) {
        val tree = trees.poll()
        if (tree.age % 5 == 0) {
            for (d in 0 until dx.size) {
                val mx = tree.x + dx[d]
                val my = tree.y + dy[d]
                if (checkRange(mx, my)) {
                    extraTrees.add(Tree(mx, my, 1))
                }
            }
        }
    }
    trees = extraTrees
}
```

가을은 나이가 5의 배수인 나무가 번식을 하는 동작이다.

그에 맞게 8방향을 탐색하고, 배열의 범위를 넘어가지 않는다면 나이가 1인 새로운 나무를 추가한다.

#### 겨울

```
private fun winter() {
    for (i in 1..n) {
        for (j in 1..n) {
            graph[i][j] += energy[i][j]
        }
    }
}
```

겨울은 기존 양분이 저장된 배열에 양분을 더해주는 작업이고, n\*n 반복문으로 해결할 수 있다.