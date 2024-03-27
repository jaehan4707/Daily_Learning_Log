class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private val dp = IntArray(31)
    fun run() {
        input()
        if (n % 2 == 0) {
            solve()
        }
        print(dp[n])
    }

    private fun input() {
        n = br.readLine().toInt()
    }

    private fun solve() {
        dp[0] = 1
        dp[2] = 3
        for (i in 4..n step 2) {
            dp[i] += 3*dp[i-2]
            for(j in  i-4 downTo  0 step 2){
                dp[i] += 2*dp[j]
            }
        }
    }
}

fun main() {
    val solution = Solution().run()
}