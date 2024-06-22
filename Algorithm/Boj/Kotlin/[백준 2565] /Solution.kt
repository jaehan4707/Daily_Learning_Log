class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0

    data class Edge(val from: Int, val to: Int)

    private lateinit var graph: Array<Edge>
    private val answer = ArrayList<Edge>()
    private lateinit var index: IntArray


    fun run() {
        input()
        solution()
        print(n - answer.size)
    }

    private fun input() {
        n = br.readLine().toInt()
        graph = Array(n) { Edge(0, 0) }
        index = IntArray(n)
        repeat(n) {
            val (f, t) = br.readLine().split(" ").map { it.toInt() }
            graph[it] = Edge(f, t)
        }
        graph.sortBy { it.from }
    }

    private fun solution() {
        for (i in graph.indices) {
            if (answer.isEmpty() || answer.last().to < graph[i].to) {
                index[i] = answer.size
                answer.add(graph[i])
            } else {
                val idx = find(graph[i])
                index[i] = idx
                answer[idx] = graph[i]
            }
        }
    }

    private fun find(edge: Edge): Int {
        var left = 0
        var right = answer.size
        while (left <= right) {
            val mid = (left + right) / 2
            if (answer[mid].to < edge.to) {
                left = mid + 1
            } else {
                right = mid - 1
            }
        }
        return left
    }
}

fun main() {
    Solution().run()
}
