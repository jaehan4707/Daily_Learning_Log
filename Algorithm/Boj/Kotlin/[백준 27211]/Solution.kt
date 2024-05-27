class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private var m = 0
    private lateinit var graph: Array<IntArray>
    private val dx = arrayOf(1, -1, 0, 0)
    private val dy = arrayOf(0, 0, -1, 1)
    private var answer = 0

    data class Point(val x: Int, val y: Int)

    fun run() {
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
            br.readLine().split(" ").map { it.toInt() }.toIntArray()
        }
    }

    private fun solution() {
        for (i in 0..<n) {
            for (j in 0..<m) {
                if (graph[i][j] == 0) {
                    bfs(i, j)
                    answer++
                }
            }
        }
    }

    private fun bfs(row: Int, col: Int) {
        val q = arrayListOf<Point>()
        q.add(Point(row, col))
        while (q.isNotEmpty()) {
            val now = q.removeFirst()
            for (dir in 0..<4) {
                val mx = changePoint(now.x + dx[dir], n)
                val my = changePoint(now.y + dy[dir], m)
                if (graph[mx][my] == 1) {
                    continue
                }
                graph[mx][my] = 1
                q.add(Point(mx, my))
            }
        }
    }

    private fun changePoint(loc: Int, limit: Int): Int {
        if (loc < 0) {
            return limit - 1
        } else if (loc >= limit) {
            return 0
        }
        return loc
    }
}

fun main() {
    Solution().run()
}
