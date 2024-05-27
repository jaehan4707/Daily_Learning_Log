class `11057` {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private val mod = 10_007
    private lateinit var dp: Array<IntArray>

    fun run() {
        input()
        solution()
        print(dp[n].sum() % mod)
    }

    private fun input() {
        n = br.readLine().toInt()
        dp = Array(n + 1) { IntArray(10) }
        for (i in 0..9) {
            dp[1][i] = 1
        }
    }

    private fun solution() {
        for (i in 1..n) {
            dp[i][0] = 1
            for (j in 1..9) {
                dp[i][j] = (dp[i - 1][j] + dp[i][j - 1]) % mod
            }
        }
    }
}

fun main() {
    `11057`().run()
}
