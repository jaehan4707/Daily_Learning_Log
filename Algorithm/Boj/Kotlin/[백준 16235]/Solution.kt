import java.util.PriorityQueue

class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private var m = 0
    private var k = 0
    private lateinit var energy: Array<IntArray>
    private lateinit var graph: Array<IntArray>
    private val dx = arrayOf(1, 1, 1, -1, -1, -1, 0, 0)
    private val dy = arrayOf(0, 1, -1, 0, 1, -1, 1, -1)
    private var trees = PriorityQueue<Tree>(compareBy { it.age })

    data class Tree(val x: Int, val y: Int, var age: Int)

    init {
        run()
    }

    private fun run() {
        input()
        for (year in 1..k) {
            val springResult = spring()
            summer(springResult)
            fall()
            winter()
            if (trees.size == 0) {
                break
            }
        }
        print(trees.size)
    }

    private fun input() {
        br.readLine().split(" ").map { it.toInt() }.apply {
            n = this[0]
            m = this[1]
            k = this[2]
        }
        energy = Array(n + 1) { IntArray(n + 1) }
        for (i in 1..n) {
            val line = br.readLine().split(" ").map { it.toInt() }
            for (j in 1..n) {
                energy[i][j] = line[j - 1]
            }
        }
        graph = Array(n + 1) { IntArray(n + 1) { 5 } }
        repeat(m) {
            br.readLine().split(" ").map { it.toInt() }.apply {
                trees.add(Tree(this[0], this[1], this[2]))
            }
        }
    }

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

    private fun summer(dyingTrees: List<Tree>) { //죽은 나무 영양분 나눠주기
        dyingTrees.forEach { tree ->
            val treeEnergy = tree.age / 2
            graph[tree.x][tree.y] += treeEnergy
        }
    }

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

    private fun winter() {
        for (i in 1..n) {
            for (j in 1..n) {
                graph[i][j] += energy[i][j]
            }
        }
    }

    private fun checkRange(x: Int, y: Int) = x in 1..n && y in 1..n

}

fun main() {
    val solution = Solution()
}