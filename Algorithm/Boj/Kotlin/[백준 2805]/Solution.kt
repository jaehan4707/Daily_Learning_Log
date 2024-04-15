class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0L
    private var m = 0L
    private lateinit var trees: IntArray
    fun run() {
        input()
        print(solve())
    }

    private fun input() {
        br.readLine().split(" ").map { it.toLong() }.apply {
            n = this[0]
            m = this[1]
        }
        trees = br.readLine().split(" ").map { it.toInt() }.toIntArray()

    }

    private fun solve(): Long {
        var left = 1L//자르려는 길이
        var right = trees.max().toLong()
        while (left <= right) {
            val mid = (left + right) / 2
            if (cutTree(mid) >= m) { //m개 이상일 때
                left = mid + 1
            } else { //길이가 모자를 경우 더 작게 짤라야 한다.
                right = mid - 1
            }
        }
        return right
    }

    private fun cutTree(height: Long): Long {
        var sum = 0L
        trees.forEach { tree ->
            if (tree >= height) {
                sum += tree - height
            }
        }
        return sum
    }

}

fun main() {
    Solution().run()
}