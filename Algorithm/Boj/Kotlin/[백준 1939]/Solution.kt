import kotlin.math.max
import kotlin.math.min

class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private var m = 0
    private lateinit var graph: Array<ArrayList<Edge>>
    private var maxWeight = 0L
    private var minWeight = 1000000000L
    private var startFactory = 0
    private var endFactory = 0
    fun run() {
        input()
        print(solve())
    }

    private fun input() {
        br.readLine().split(" ").map { it.toInt() }.apply {
            n = this[0]
            m = this[1]
        }
        graph = Array(n + 1) { ArrayList<Edge>() }
        repeat(m) {
            br.readLine().split(" ").map { it.toInt() }.apply {
                graph[this[0]].add(Edge(this[1], this[2].toLong()))
                graph[this[1]].add(Edge(this[0], this[2].toLong()))
                maxWeight = max(maxWeight, this[2].toLong())
            }
        }
        br.readLine().split(" ").map { it.toInt() }.apply {
            startFactory = this[0]
            endFactory = this[1]
        }
    }

    private fun solve(): Long {
        var left = 1L
        var right = maxWeight
        while (left <= right) {
            val mid: Long = (left + right) / 2
            //운반할 수 있는지 없는지 찾아야 함
            if (isTransfer(mid)) { //배달가능
                left = mid + 1
            } else { // 배달 불가능
                right = mid - 1
            }
        }
        return right
    }

    private fun isTransfer(weight: Long): Boolean {
        val q = arrayListOf<Int>()
        val visit = BooleanArray(n + 1)
        visit[startFactory] = true
        q.add(startFactory)
        while (q.isNotEmpty()) {
            val now = q.removeFirst()
            for (i in 0 until graph[now].size) {
                val nowVertex = graph[now][i]
                if (nowVertex.weight < weight || visit[nowVertex.vertex]) { //비용보다 비싸다면 no
                    continue
                }
                if(nowVertex.vertex==endFactory){
                    return true
                }
                visit[nowVertex.vertex] = true
                q.add(nowVertex.vertex)
            }
        }
        return false
    }

    data class Edge(val vertex: Int, val weight: Long)
}

fun main() {
    val solution = Solution().run()
}