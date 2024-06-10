class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private var m = 0
    private lateinit var dp: Array<IntArray>
    private val sb = StringBuilder()

    fun run() {
        input()
        print(sb.toString())
    }

    private fun input() {
        br.readLine().split(" ").map { it.toInt() }.apply {
            n = this[0]
            m = this[1]
        }
        dp = Array(n + 1) { IntArray(n + 1) }
        repeat(n) {
            val line = br.readLine().split(" ").map { it.toInt() }
            line.forEachIndexed { index, i ->
                dp[it + 1][index + 1] = dp[it + 1][index] + i + dp[it][index + 1] - dp[it][index]
            }
        }
        repeat(m) {
            val (x1, y1, x2, y2) = br.readLine().split(" ").map { it.toInt() }
            sb.append(solution(x1, y1, x2, y2)).append("\n")
        }
    }

    private fun solution(x1: Int, y1: Int, x2: Int, y2: Int): Int {
        return dp[x2][y2] - dp[x1 - 1][y2] - dp[x2][y1 - 1] + dp[x1 - 1][y1 - 1]
    }
}

fun main() {
    Solution().run()
}
