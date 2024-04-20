import java.lang.StringBuilder
import java.util.PriorityQueue


class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private var m = 0
    private lateinit var graph: Array<ArrayList<Int>>
    private lateinit var inEdge: IntArray
    private val sb = StringBuilder()

    fun run() {
        input()
        solve()
        println(sb.toString())
    }

    private fun input() {
        br.readLine().split(" ").map { it.toInt() }.apply {
            n = this[0]
            m = this[1]
        }
        graph = Array(n + 1) { arrayListOf() }
        inEdge = IntArray(n + 1)
        repeat(m) {
            br.readLine().split(" ").map { it.toInt() }.apply {
                graph[this[0]].add(this[1])
                inEdge[this[1]]++
            }
        }
    }

    private fun solve() {
        val q: PriorityQueue<Int> = PriorityQueue()
        for (i in 1..n) {
            if (inEdge[i] == 0) {
                q.add(i)
            }
        }

        while (!q.isEmpty()) { //q에 들어간 원소들은 inEdge가 0인 놈들
            val now = q.poll()
            sb.append(now).append(" ")
            for (i in 0 until graph[now].size) {
                val connectVertex = graph[now][i]
                inEdge[connectVertex]--
                if (inEdge[connectVertex] == 0) { //이제 연결된 간선이 없을 경우
                    q.add(connectVertex)
                }
            }
        }
    }
}

fun main() {
    val solution = Solution().run()
}