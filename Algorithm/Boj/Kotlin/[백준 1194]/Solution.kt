import java.util.*

class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private var m = 0
    private lateinit var graph: Array<CharArray>
    private val dx = arrayOf(1, -1, 0, 0)
    private val dy = arrayOf(0, 0, 1, -1)
    private lateinit var visit: Array<Array<BooleanArray>>
    private val key = arrayOf('a', 'b', 'c', 'd', 'e', 'f')
    private val door = arrayOf('A', 'B', 'C', 'D', 'E', 'F')

    data class Point(val x: Int, val y: Int, val visited: Int = 0, val move: Int = 0)

    private lateinit var start: Point


    fun run() {
        input()
        print(solution())
    }

    private fun input() {
        br.readLine().split(" ").map { it.toInt() }.apply {
            n = this[0]
            m = this[1]
        }
        visit = Array(n) { Array(m) { BooleanArray(1 shl 6) } }
        graph = Array(n) { CharArray(m) }
        repeat(n) { idx ->
            graph[idx] = br.readLine().toCharArray()
            for (i in graph[idx].indices) {
                if (graph[idx][i] == '0') {
                    start = Point(idx, i)
                }
            }
        }
    }

    private fun solution(): Int {
        val q: Queue<Point> = LinkedList()
        q.add(start)
        visit[start.x][start.y][start.visited] = true
        while (q.isNotEmpty()) {
            val now = q.poll()
            for (dir in 0..3) {
                val mx = now.x + dx[dir]
                val my = now.y + dy[dir]
                if (!checkRanged(mx, my) || graph[mx][my] == '#' || visit[mx][my][now.visited]) {
                    continue
                }
                if (graph[mx][my] == '1') {
                    return now.move + 1
                }
                val keyIdx = key.indexOf(graph[mx][my])
                val doorIdx = door.indexOf(graph[mx][my])
                var next = now.visited
                if (keyIdx != -1) { // 열쇠일때
                    next = now.visited or (1 shl keyIdx)
                    if (visit[mx][my][next]) { //이미 방문한 경우
                        continue
                    }
                } else if (doorIdx != -1) { //문일때
                    if ((now.visited and (1 shl doorIdx)) < 1) { // 문에 대응되는 열쇠가 없는 경우
                        continue
                    }
                }
                visit[mx][my][next] = true
                q.add(Point(mx, my, next, now.move + 1))
            }
        }
        return -1
    }

    private fun checkRanged(x: Int, y: Int) = x in 0..<n && y in 0..<m
}

fun main() {
    Solution().run()
}