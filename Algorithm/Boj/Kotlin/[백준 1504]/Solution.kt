import java.util.PriorityQueue
import kotlin.math.min

class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private var m = 0
    private val INF = 1000 * 20000 + 1
    private lateinit var dist: IntArray
    private var v1 = 0
    private var v2 = 0

    data class Edge(val vertex: Int, val weight: Int = 0)

    private lateinit var graph: Array<ArrayList<Edge>>

    fun run() {
        input()
        print(solution())
    }

    private fun input() {
        br.readLine().split(" ").map { it.toInt() }.apply {
            n = this[0] // 정점의 개수
            m = this[1] // 간선의 개수
        }
        graph = Array(n + 1) { arrayListOf() }
        repeat(m) {
            val (a, b, c) = br.readLine().split(" ").map { it.toInt() }
            graph[a].add(Edge(b, c)) // a->b (c)
            graph[b].add(Edge(a, c)) // b->a (c) 양방향 길
        }
        br.readLine().split(" ").map { it.toInt() }.apply {
            v1 = this[0]
            v2 = this[1]
        }
    }

    private fun solution(): Int {
        var dist1 = 0
        dist1 += dijkstra(1, v1) + dijkstra(v1, v2) + dijkstra(v2, n)
        var dist2 = 0
        dist2 += dijkstra(1, v2) + dijkstra(v2, v1) + dijkstra(v1, n)

        var answer = min(dist1, dist2)
        if (answer >= INF) {
            answer = -1
        }
        return answer
    }

    private fun dijkstra(s: Int, e: Int): Int {
        val pq = PriorityQueue<Edge>(compareBy { it.weight })
        dist = IntArray(n + 1) { INF }
        dist[s] = 0
        pq.add(Edge(s, 0))
        while (!pq.isEmpty()) {
            val now = pq.poll()
            if (now.vertex == e) {
                break
            }
            graph[now.vertex].forEach { edge ->
                if (dist[edge.vertex] > now.weight + edge.weight) { //거리값이 더 작다면
                    pq.add(edge.copy(weight = now.weight + edge.weight))
                    dist[edge.vertex] = edge.weight + now.weight//거리값 업데이트
                }
            }
        }
        return dist[e]
    }
}

fun main() {
    Solution().run()
}
