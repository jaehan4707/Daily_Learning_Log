class Solution {

    private var n = 0
    private var m = 0
    private var t = 0
    private val br = System.`in`.bufferedReader()
    private val sb = StringBuilder()
    private lateinit var ary: Array<IntArray>
    private lateinit var sum: Array<IntArray>
    init {
        input()
        print(sb.toString())
    }

    private fun input() {
        br.readLine().split(" ").map { it.toInt() }.apply {
            n = this[0]
            m = this[1]
        }
        ary = Array(n + 1) { IntArray(m + 1) }
        sum = Array(n + 1) { IntArray(m + 1) }
        for (i in 1..n) {
            val line = br.readLine().split(" ").map { it.toInt() }
            for (j in 1..m) {
                ary[i][j] = line[j - 1]
                sum[i][j] = line[j - 1] + sum[i][j - 1]
            }
        }
        t = br.readLine().toInt()
        repeat(t) {
            val (i, j, x, y) = br.readLine().split(" ").map { it.toInt() }
            sb.append(solution(i, j, x, y)).append("\n")
        }

    }

    private fun solution(r1: Int, c1: Int, r2: Int, c2: Int): Int {
        var sum = 0
        for (i in r1..r2) {
            sum += this.sum[i][c2] - this.sum[i][c1] + ary[i][c1]
        }
        return sum
    }

}

fun main() {
    Solution()
}