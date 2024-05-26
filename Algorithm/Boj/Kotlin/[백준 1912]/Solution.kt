import java.lang.StringBuilder
import kotlin.math.max

class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private lateinit var dp: IntArray
    private lateinit var numbers: IntArray
    private val sb = StringBuilder()

    fun run() {
        input()
        solution()
        print(dp.max())
    }

    private fun input() {
        n = br.readLine().toInt()
        dp = IntArray(n + 1) { -1000 * 100000 }
        numbers = br.readLine().split(" ").map { it.toInt() }.toIntArray()
    }

    private fun solution() {
        dp[0] = numbers[0]
        for (i in 1..<n) { //더하거나, 새로 시작하기
            dp[i] = max(dp[i - 1] + numbers[i], numbers[i])
        }
    }
}

fun main() {
    Solution().run()
}
