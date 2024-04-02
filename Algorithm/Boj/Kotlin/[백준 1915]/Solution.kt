import kotlin.math.*

class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private var m = 0
    private lateinit var graph: Array<IntArray>
    private var answer = 0
    fun run() {
        input()
        solve()
        print(answer * answer)
    }

    private fun input() {
        br.readLine().split(" ").map { it.toInt() }.apply {
            n = this[0]
            m = this[1]
        }
        graph = Array(n) {
            val line = br.readLine()
            IntArray(m) { idx ->
                line[idx].toString().toInt()
            }
        }
    }

    private fun solve() {
        for (i in 0 until n) {
            for (j in 0 until m) {
                if (graph[i][j] == 0) {
                    continue
                }
                if (i - 1 >= 0 && j - 1 >= 0) {
                    graph[i][j] = minOf(graph[i - 1][j - 1], graph[i - 1][j], graph[i][j - 1]) + 1
                }
                answer = max(answer, graph[i][j])
            }
        }
    }
}

fun main() {
    val solution = Solution().run()
}