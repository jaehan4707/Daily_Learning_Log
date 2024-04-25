import kotlin.math.*

class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private lateinit var graph: Array<BooleanArray>
    private var frontCoin = Int.MAX_VALUE
    fun run() {
        input()
        solve()
        print(frontCoin)
    }

    private fun input() {
        n = br.readLine().toInt()
        graph = Array(n) { BooleanArray(n) }
        for (i in 0 until n) {
            val line = br.readLine()
            for (j in 0 until n) {
                if (line[j] == 'H') {
                    graph[i][j] = false
                } else {
                    graph[i][j] = true
                }
            }
        }
    }

    private fun solve() {
        flipCoin(0, 0)
    }

    private fun flipCoin(now: Int, rowVisited: Int) {
        if (now == n) {
            frontCoin = min(frontCoin, checkFrontCoin(rowVisited))
            return
        }
        val next = 1 shl now
        flipCoin(now + 1, rowVisited or next)
        flipCoin(now + 1, rowVisited)
    }

    private fun checkFrontCoin(rowVisited: Int): Int {
        var sum = 0
        for (j in 0 until n) {
            var front = 0
            for (k in 0 until n) {
                val next = 1 shl k
                if (rowVisited and next != 0) { //뒤집어야 행인 경우
                    if (!graph[k][j]) {
                        front += 1
                    }
                    continue
                }
                if (graph[k][j]) {
                    front += 1
                }
            }
            sum += min(front, n - front)
        }
        return sum
    }
}

fun main() {
    Solution().run()
}