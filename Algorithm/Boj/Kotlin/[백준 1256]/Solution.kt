import kotlin.text.StringBuilder
import kotlin.math.*

class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private var m = 0
    private var k = 0L
    private lateinit var comb: Array<IntArray>
    private val sb = StringBuilder()

    fun run() {
        input()
        solve()
        print(sb.toString())
    }

    private fun input() {
        br.readLine().split(" ").map { it.toLong() }.apply {
            n = this[0].toInt()
            m = this[1].toInt()
            k = this[2]
        }
        comb = Array(n + 1) { IntArray(m + 1) }
    }

    private fun solve() {
        val temp = calculateCombination(n, m)
        if (temp < k) { //k개 보다 적은 경우 -1
            print(-1)
        } else { //k개 보다 같거나 많은 경우 문자열을 찾아야 함.
            makeStrings(n, m, k)
        }
    }

    private fun calculateCombination(N: Int, M: Int): Int {
        if (N == 0 || M == 0) { // 0C1 or 1C0
            return 1
        }
        if (comb[N][M] != 0) return comb[N][M]

        comb[N][M] = min(calculateCombination(N - 1, M) + calculateCombination(N, M - 1), 1000000001)
        return comb[N][M]
    }

    private fun makeStrings(N: Int, M: Int, K: Long) {
        if (N == 0) { //
            for (i in 0 until M) {
                sb.append("z")
            }
            return
        } else if (M == 0) {
            for (i in 0 until N) {
                sb.append("a")
            }
            return
        }
        val result = calculateCombination(N - 1, M)
        if (K > result) {
            sb.append("z")
            makeStrings(N, M - 1, K - result)
        } else {
            sb.append("a")
            makeStrings(N - 1, M, K)
        }
    }
}

fun main() {
    Solution().run()
}