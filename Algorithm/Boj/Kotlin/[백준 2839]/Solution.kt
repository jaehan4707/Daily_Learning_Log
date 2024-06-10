import kotlin.math.*

class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private val dp = IntArray(5001) { 1700 }
    private val sugar = arrayOf(3, 5)

    fun run() {
        input()
        print(solution())
    }

    private fun input() {
        n = br.readLine().toInt()
    }

    private fun solution(): Int {
        for (i in sugar.indices) {
            dp[sugar[i]] = 1
            for (j in sugar[i]..n) {
                dp[j] = min(dp[j], dp[j - sugar[i]] + 1)
            }
        }
        return if (dp[n] == 1700) {
            -1
        } else {
            dp[n]
        }
    }
}

fun main() {
    Solution().run()
}
