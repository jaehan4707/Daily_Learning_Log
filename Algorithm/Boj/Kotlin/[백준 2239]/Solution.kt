import kotlin.system.exitProcess

class Solution {

    private val br = System.`in`.bufferedReader()
    private val graph = Array(9) { IntArray(9) }

    data class Point(val x: Int, val y: Int, var number: Int = 0)

    private val candidates = arrayListOf<Point>()
    fun run() {
        input()
        solve(0)
    }

    private fun input() {
        for (i in 0 until 9) {
            val line = br.readLine()
            for (j in 0 until 9) {
                graph[i][j] = line[j].toString().toInt()
                if (graph[i][j] == 0) {
                    candidates.add(Point(i, j))
                }
            }
        }
    }

    private fun solve(depth: Int) {
        if (depth == candidates.size) {
            for (i in 0 until 9) {
                for (j in 0 until 9) {
                    print(graph[i][j])
                }
                println()
            }
            exitProcess(0)
        }
        for (i in 1..9) {
            val point = candidates[depth]
            graph[point.x][point.y] = i
            if (check(point.x, point.y)) {
                solve(depth + 1)
            }
            graph[point.x][point.y] = 0
        }
    }

    private fun check(x: Int, y: Int): Boolean {
        for (i in 0 until 9) {
            if (graph[i][y] == graph[x][y] && i != x) {
                return false
            }
            if (graph[x][i] == graph[x][y] && i != y) {
                return false
            }
        }
        val startX = x / 3 * 3
        val startY = y / 3 * 3
        for (i in startX..startX + 2) {
            for (j in startY..startY + 2) {
                if (i == x && j == y) {
                    continue
                }
                if (graph[i][j] == graph[x][y]) {
                    return false
                }
            }
        }
        return true
    }

}

fun main() {
    val solution = Solution().run()
}