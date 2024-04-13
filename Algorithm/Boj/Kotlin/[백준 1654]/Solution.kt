import kotlin.math.max

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
        graph = IntArray(n) {
            br.readLine().toInt()
        }
        graph.sort()
    }

    private fun solve() : Long {
        var left = 1L
        var right = graph.last().toLong()

        while (left <= right) {
            val mid : Long = (left + right) / 2
            val cnt = count(mid)
            if (cnt >= m) { //m개를 만들 수 있을 경우 거리를 더 늘려도됨
                left = mid + 1
            } else {
                right = mid - 1
            }
        }
        return right
    }

    private fun count(length: Long): Long {
        var sum = 0L
        graph.forEach {
            sum += it / length
        }
        return sum
    }
}

fun main() {
    val solution = Solution().run()
}