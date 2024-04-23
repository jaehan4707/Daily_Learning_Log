import kotlin.math.*

class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private var m = 0
    private lateinit var graph: IntArray
    fun run() {
        input()
        print(solve())
    }

    private fun input() {
        br.readLine().split(" ").map { it.toInt() }.apply {
            n = this[0]
            m = this[1]
        }
        graph = br.readLine().split(" ").map { it.toInt() }.toIntArray()
    }

    private fun solve(): Int {
        var left = 0 // 구간의 점수 최대가 0이 될 수 있음.
        var right = graph.max()

        while (left <= right) {
            val mid = (left + right) / 2
            if (countGroup(mid)) { //mid가 최대인 구간을 만들 수 있니?
                right = mid - 1
            } else {
                left = mid + 1
            }
        }
        return left
    }

    private fun countGroup(maxSum: Int): Boolean {
        var minGroup = Int.MAX_VALUE
        var maxGroup = Int.MIN_VALUE
        var groupCnt = 1
        graph.forEach { num ->
            minGroup = min(minGroup, num)
            maxGroup = max(maxGroup, num)

            if (maxGroup - minGroup > maxSum) { //최대와 최소의 차이가 maxSum보다 클 경우 구간을 자름.
                groupCnt++
                minGroup = num
                maxGroup = num
            }
        }
        return groupCnt <= m
    }
}

fun main() {
    Solution().run()
}