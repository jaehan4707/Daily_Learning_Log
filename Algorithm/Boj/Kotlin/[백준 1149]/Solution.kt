import kotlin.math.*

class `1149` {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private lateinit var dp: Array<IntArray>
    private lateinit var graph: Array<IntArray>

    fun run() {
        input()
        solution()
        print(dp[n - 1].min())
    }

    private fun input() {
        n = br.readLine().toInt()
        dp = Array(n) { IntArray(3) { 1_000_001 } }
        graph = Array(n) {
            br.readLine().split(" ").map { it.toInt() }.toIntArray()
        }
    }

    private fun solution() {
        for (c in 0..<3) { //R G B
            dp[0][c] = graph[0][c] //처음 색깔 고정
            for (i in 1..<n) {
                dp[i][0] = min(dp[i - 1][1], dp[i - 1][2]) + graph[i][0]
                dp[i][1] = min(dp[i - 1][0], dp[i - 1][2]) + graph[i][1]
                dp[i][2] = min(dp[i - 1][1], dp[i - 1][0]) + graph[i][2]
            }
        }
    }
}

fun main() {
    `1149`().run()
}
