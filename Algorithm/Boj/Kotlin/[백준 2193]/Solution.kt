import java.lang.StringBuilder

class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private lateinit var dp: Array<LongArray>
    private val sb = StringBuilder()

    fun run() {
        input()
        solution()
        print(dp[n].sum())
    }

    private fun input() {
        n = br.readLine().toInt()
        dp = Array(n + 1) { LongArray(2) }
    }

    private fun solution() {
        dp[1][0] = 0
        dp[1][1] = 1
        for (i in 2..n) {
            dp[i][0] = dp[i - 1][0] + dp[i - 1][1]
            dp[i][1] = dp[i - 1][0]
        }
    }
}

fun main() {
    Solution().run()
}
