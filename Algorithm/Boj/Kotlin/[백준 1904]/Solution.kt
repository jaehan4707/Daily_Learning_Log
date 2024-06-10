class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private val dp = IntArray(1000001)
    private val mod = 15746

    fun run() {
        input()
        solution()
        print(dp[n] % mod)
    }

    private fun input() {
        n = br.readLine().toInt()
        dp[1] = 1
        dp[2] = 2
    }

    private fun solution() {
        for (i in 3..n) {
            dp[i] = (dp[i - 1] + dp[i - 2]) % mod
        }
    }
}

fun main() {
    Solution().run()
}
