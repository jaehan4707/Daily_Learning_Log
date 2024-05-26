import java.lang.StringBuilder

class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private val mod = 1_000_000_009
    private lateinit var dp: Array<LongArray>
    private lateinit var numbers: IntArray
    private val sb = StringBuilder()
    private var maxNum = 0

    fun run() {
        input()
        solution()
        print(sb.toString())
    }

    private fun input() {
        n = br.readLine().toInt()
        numbers = IntArray(n) { br.readLine().toInt() }
        maxNum = numbers.max()
        dp = Array(maxNum + 1) { LongArray(4) }
    }

    private fun solution() {
        dp[1][1] = 1
        dp[2][2] = 1
        dp[3][1] = 1
        dp[3][2] = 1
        dp[3][3] = 1
        for (i in 4..maxNum) {
            dp[i][1] = (dp[i - 1][2] + dp[i - 1][3]) % mod
            dp[i][2] = (dp[i - 2][1] + dp[i - 2][3]) % mod
            dp[i][3] = (dp[i - 3][1] + dp[i - 3][2]) % mod
        }
        numbers.forEach {
            sb.append(dp[it].sum() % mod).append("\n")
        }
    }
}

fun main() {
    Solution().run()
}
