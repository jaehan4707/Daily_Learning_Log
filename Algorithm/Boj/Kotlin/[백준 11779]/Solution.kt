import java.util.PriorityQueue

class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private var m = 0
    private var start = 0
    private var end = 0
    private val INF = (100_000 * 1000).toLong()
    private lateinit var graph: Array<ArrayList<Node>>
    private lateinit var dist: LongArray
    private val sb = StringBuilder()

    data class Node(val vertex: Int, val weight: Long)
    data class Edge(val start: Int, val weight: Long)

    private lateinit var path: IntArray

    fun run() {
        input()
        solution()
        print(sb.toString())
    }

    private fun input() {
        n = br.readLine().toInt()
        m = br.readLine().toInt()
        dist = LongArray(n + 1) { INF }
        graph = Array(n + 1) { ArrayList() }
        path = IntArray(n + 1)
        repeat(m) {
            val (a, b, c) = br.readLine().split(" ").map { it.toInt() }
            graph[a].add(Node(b, c.toLong()))
        }
        br.readLine().split(" ").map { it.toInt() }.apply {
            start = this[0]
            end = this[1]
        }
    }

    private fun solution() {
        val pq = PriorityQueue<Edge>(compareBy { it.weight })
        pq.add(Edge(start, 0))
        dist[start] = 0
        path[start] = start
        while (!pq.isEmpty()) {
            val now = pq.poll()
            if (now.start == end) {
                break
            }
            graph[now.start].forEach { node ->
                if (dist[node.vertex] > node.weight + now.weight) {
                    dist[node.vertex] = node.weight + now.weight
                    path[node.vertex] = now.start
                    pq.add(Edge(node.vertex, now.weight + node.weight))
                }
            }
        }
        sb.append("${dist[end]}\n")
        var nowPath = end
        var count = 1
        val arrive = ArrayList<Int>()
        while (true) {
            if (nowPath == start) {
                break
            }
            arrive.add(nowPath)
            nowPath = path[nowPath]
            count++
        }
        arrive.add(start)
        sb.append("${count}\n")
        for (i in arrive.size - 1 downTo 0) {
            sb.append("${arrive[i]} ")
        }
    }
}

fun main() {
    Solution().run()
}
