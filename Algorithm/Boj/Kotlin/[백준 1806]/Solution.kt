import kotlin.math.min

class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private var s = 0
    private lateinit var graph: IntArray
    private var answer = Int.MAX_VALUE
    fun run() {
        input()
        solve()
        if (answer == Int.MAX_VALUE) {
            answer = 0
        }
        print(answer)
    }

    private fun input() {
        br.readLine().split(" ").map { it.toInt() }.apply {
            n = this[0]
            s = this[1]
        }
        graph = br.readLine().split(" ").map { it.toInt() }.toIntArray()
    }

    private fun solve() {
        var start = 0
        var end = 0
        var sum = 0
        while (start <= end) {
            if (sum < s) {
                if (end >= graph.size) {
                    break
                }
                sum += graph[end]
                end += 1
            } else {
                answer = min(answer, end - start)
                sum -= graph[start]
                start += 1
            }
        }
    }
}

fun main() {
    Solution().run()
}