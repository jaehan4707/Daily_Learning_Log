import kotlin.math.*

class Solution {
    private val br = System.`in`.bufferedReader()
    private lateinit var items: IntArray
    private lateinit var accumulateSum: IntArray
    private lateinit var dp: IntArray
    private var n = 0
    private var m = 0
    private var answer = 0

    init {
        input()
        calculateMaxValue()
        print(answer)
    }

    private fun input() {
        val line = br.readLine().split(" ")
        n = line[0].toInt()
        m = line[1].toInt()
        items = IntArray(n + 1)
        accumulateSum = IntArray(n + 1)
        dp = IntArray(n + 1)
        for (i in 1..n) {
            items[i] = br.readLine().toInt()
            accumulateSum[i] = accumulateSum[i - 1] + items[i]
        }
        dp[m] = accumulateSum[m]
    }

    private fun calculateMaxValue() {
        for (i in m + 1..n) {
            dp[i] = max(dp[i - 1] + items[i], accumulateSum[i] - accumulateSum[i - m])
            answer = max(answer, dp[i])
        }
    }
}

fun main() {
    val solution = Solution()
}