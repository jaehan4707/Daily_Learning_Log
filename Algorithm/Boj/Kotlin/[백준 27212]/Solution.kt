class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private var m = 0
    private var c = 0
    private lateinit var graph: Array<LongArray>
    private lateinit var dp: Array<LongArray>
    private lateinit var a: IntArray
    private lateinit var b: IntArray


    fun run() {
        input()
        print(solution())
    }

    private fun input() {
        br.readLine().split(" ").map { it.toInt() }.apply {
            n = this[0]
            m = this[1]
            c = this[2]
        }
        dp = Array(n + 1) { LongArray(m + 1) }
        graph = Array(c + 1) { LongArray(c + 1) }
        repeat(c) { idx ->
            val line = br.readLine().split(" ").map { it.toLong() }
            for (i in 1..line.size) {
                graph[idx + 1][i] = line[i - 1]
            }
        }
        a = IntArray(n + 1)
        b = IntArray(m + 1)
        br.readLine().split(" ").map { it.toInt() }.toIntArray().forEachIndexed { index, value ->
            a[index + 1] = value
        }
        br.readLine().split(" ").map { it.toInt() }.toIntArray().forEachIndexed { index, value ->
            b[index + 1] = value
        }
    }

    private fun solution(): Long {
        for (i in 1..n) {
            for (j in 1..m) {
                dp[i][j] = maxOf(dp[i - 1][j - 1] + graph[a[i]][b[j]], dp[i - 1][j], dp[i][j - 1])
            }
        }
        return dp[n].max()
    }
}

fun main() {
    Solution().run()
}
