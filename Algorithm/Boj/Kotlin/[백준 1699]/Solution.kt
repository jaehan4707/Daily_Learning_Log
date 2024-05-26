import kotlin.math.*

class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private lateinit var dp: IntArray
    private lateinit var numbers: IntArray

    fun run() {
        input()
        solution()
        print(dp[n])
    }

    private fun input() {
        n = br.readLine().toInt()
        dp = IntArray(n + 1) { Int.MAX_VALUE }
        numbers = IntArray(n + 1)
        var step = 1
        for (i in 0..n) { //제곱수를 다 구한다
            if (i < (step + 1) * (step + 1)) {
                numbers[i] = step
            } else {
                step += 1
                numbers[i] = step
            }
        }
    }

    private fun solution() {
        dp[0] = 0
        for (i in 1..n) {
            for (j in 1..numbers[i]) {
                val number = i - (j * j)
                dp[i] = min(dp[i], dp[number] + 1)
            }
        }
    }
}

fun main() {
    Solution().run()
}
