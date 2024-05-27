class `2225` {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private val mod = 1_000_000_000
    private var k = 0
    private lateinit var dp: Array<IntArray>

    fun run() {
        input()
        solution()
        print(dp[n][k])

    }

    private fun input() {
        br.readLine().split(" ").map { it.toInt() }.apply {
            n = this[0]
            k = this[1]
        }
        dp = Array(n + 1) { IntArray(k + 1) }
    }

    private fun solution() {
        for (i in 0..k) {
            dp[0][i] = 1
        }
        for (i in 1..n) {
            for (j in 1..k) {
                dp[i][j] = (dp[i - 1][j] + dp[i][j - 1]) % mod
            }
        }
    }
}

fun main() {
    `2225`().run()
}
