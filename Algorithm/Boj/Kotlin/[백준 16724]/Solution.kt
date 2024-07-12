import java.util.*

class Solution {

    private var n = 0
    private var m = 0
    private val br = System.`in`.bufferedReader()
    private val sb = StringBuilder()
    private lateinit var graph: Array<String>
    private val direction = mapOf('D' to Point(1, 0), 'L' to Point(0, -1), 'R' to Point(0, 1), 'U' to Point(-1, 0))
    private var answer = 0

    data class Point(val x: Int, val y: Int)

    private val q: Queue<Point> = LinkedList()
    private lateinit var visited: Array<IntArray>

    init {
        input()
        solution()
        print(answer)

    }

    private fun input() {
        br.readLine().split(" ").map { it.toInt() }.apply {
            n = this[0]
            m = this[1]
        }
        graph = Array(n) {
            br.readLine()
        }
        visited = Array(n) { IntArray(m) }
    }

    private fun solution() {
        var cnt = 1
        for (i in graph.indices) {
            for (j in graph[i].indices) {
                if (visited[i][j] != 0) { // 사이클을 통해 방문했는지를 찾아봐유~~@!@~!@
                    continue
                }
                if (bfs(i, j, cnt)) {
                    answer += 1
                }
                cnt += 1
            }
        }
    }

    private fun bfs(x: Int, y: Int, cnt: Int): Boolean { // 유효한 탐색과 아닌것을 구별할 수 있어야 한다.
        q.add(Point(x, y))
        while (q.isNotEmpty()) {
            val now = q.poll()
            val nowDirection = findDirections(graph[now.x][now.y])
            val mx = now.x + nowDirection.x
            val my = now.y + nowDirection.y
            if (checkRange(mx, my).not() || visited[mx][my] == cnt) {
                continue
            }

            if (visited[mx][my] != 0 && visited[mx][my] != cnt) { // 첫방문도아니고, 내 그룹라벨이 아닐 때
                return false
            }

            if (mx == x && my == y) {  //다시 원래대로 돌아왔을때
                return true
            }
            q.add(Point(mx, my))
            visited[mx][my] = cnt
        }
        return true
        // 사이클 실패했을때 원상복구하는 작업이 필요하다.
    }

    private fun findDirections(dir: Char): Point {
        return direction[dir] ?: Point(0, 0)
    }

    private fun checkRange(x: Int, y: Int) = x in 0 until n && y in 0 until m
}

fun main() {
    Solution()
}