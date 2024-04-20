import kotlin.math.*

class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private lateinit var graph: Array<IntArray>
    private lateinit var dp: Array<IntArray>
    private var answer = Int.MAX_VALUE

    fun run() {
        input()
        solve()
        print(answer)
    }

    private fun input() {
        n = br.readLine().toInt()
        graph = Array(n + 1) { IntArray(4) }
        for (i in 1..n) {
            val line = br.readLine().split(" ").map { it.toInt() }
            for (j in 1..3) {
                graph[i][j] = line[j - 1]
            }
        }
        dp = Array(n + 1) { IntArray(n + 1) }
    }

    private fun solve() {

        for (c in 1..3) {
            for (i in 1..3) {
                if (i != c) {
                    dp[1][i] = 1000 * 1000 *10
                } else {
                    dp[1][i] = graph[1][i]
                }
            }
            for (i in 2..n ) {
                dp[i][1] = min(dp[i - 1][2], dp[i - 1][3]) + graph[i][1]
                dp[i][2] = min(dp[i - 1][1], dp[i - 1][3]) + graph[i][2]
                dp[i][3] = min(dp[i - 1][1], dp[i - 1][2]) + graph[i][3]
            }

            for(i in 1 .. 3){
                if(i!=c){
                    answer = min(answer,dp[n][i])
                }
            }
        }
    }
}

fun main() {
    val solution = Solution().run()
}