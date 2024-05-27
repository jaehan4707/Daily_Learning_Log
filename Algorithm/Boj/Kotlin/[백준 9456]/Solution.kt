import java.lang.StringBuilder
import kotlin.math.*

class Solution {

    private val br = System.`in`.bufferedReader()
    private var t = 0
    private var n = 0
    private lateinit var dp: Array<IntArray>
    private lateinit var graph: Array<IntArray>
    private val sb = StringBuilder()

    fun run() {
        input()
        print(sb.toString())
    }

    private fun input() {
        t = br.readLine().toInt()
        repeat(t) {
            n = br.readLine().toInt()
            dp = Array(3) { IntArray(n + 1) }
            graph = Array(3) { IntArray(n + 1) }
            for (i in 1..2) {
                val line = br.readLine().split(" ").map { it.toInt() }
                for (j in 1..n) {
                    graph[i][j] = line[j - 1]
                }
            }
            solution()
            sb.append(max(dp[1][n], dp[2][n])).append("\n")
        }

    }

    private fun solution() {
        dp[1][1] = graph[1][1]
        dp[2][1] = graph[2][1]
        for (j in 2..n) {
            dp[1][j] = max(dp[2][j - 1], dp[2][j - 2]) + graph[1][j]
            dp[2][j] = max(dp[1][j - 1], dp[1][j - 2]) + graph[2][j]
        }
    }
}

fun main() {
    Solution().run()
}
