class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private lateinit var graph: IntArray
    private var answer = arrayListOf<Int>()


    fun run() {
        input()
        solution()
        print(answer.size)
    }

    private fun input() {
        n = br.readLine().toInt()
        graph = br.readLine().split(" ").map { it.toInt() }.toIntArray()
    }

    private fun solution() {
        for (i in graph.indices) {
            if (answer.size == 0 || answer.last() < graph[i]) {
                answer.add(graph[i])
            } else {
                val idx = search(graph[i])
                answer[idx] = graph[i]
            }
        }
    }

    private fun search(number: Int): Int {
        var left = 0
        var right = answer.size
        while (left <= right) {
            val mid = (left + right) / 2
            if (answer[mid] >= number) { // number가 더 작으면
                right = mid - 1
            } else {
                left = mid + 1
            }
        }
        return left
    }


}

fun main() {
    Solution().run()
}
