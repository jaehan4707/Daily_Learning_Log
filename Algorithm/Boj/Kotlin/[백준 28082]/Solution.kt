class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private var k = 0
    private lateinit var battery: IntArray
    private val dp = IntArray(50001) { 5000001 }
    private val sb = StringBuilder()
    private var cnt = 0
    fun run() {
        input()
        solve()
        answer()
        println(cnt)
        print(sb)
    }

    private fun input() {
        br.readLine().split(" ").map { it.toInt() }.apply {
            n = this[0]
            k = this[1]
        }
        battery = br.readLine().split(" ").map { it.toInt() }.toIntArray()
    }

    private fun solve() {
        dp[0] = 0
        for (i in 1..n) {
            for (j in dp.size - 1 downTo battery[i - 1]) { // 전력량 1~ maxE *n
                dp[j] = Math.min(dp[j], dp[j - battery[i - 1]] + 1)
            }
        }
    }

    private fun answer() {
        dp.forEachIndexed { index, element ->
            if (element in 1..k) {
                cnt++
                sb.append(index).append(" ")
            }
        }
    }
}

fun main() {
    val solution = Solution().run()
}