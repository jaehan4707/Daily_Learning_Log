class Solution {
    private val br = System.`in`.bufferedReader()
    private var n = 0
    private var k = 0
    private lateinit var coins: IntArray
    private lateinit var dp: IntArray
    fun run() {
        input()
        solve()
        print(dp[k])
    }

    private fun input() {
        br.readLine().split(" ").map { it.toInt() }.apply {
            n = this[0]
            k = this[1]
        }
        coins = IntArray(n + 1)
        dp = IntArray(k + 1) //가치 합
        for (i in 1..n) {
            coins[i] = br.readLine().toInt()
        }

    }

    private fun solve() {
        dp[0]=1
        for (i in 1..n) {
            for (j in coins[i]..k) {
                dp[j] += dp[j - coins[i]]
            }
        }
    }
}

fun main() {
    val solution = Solution().run()
}
