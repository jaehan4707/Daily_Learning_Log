class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private var m = 0
    private lateinit var graph: Array<IntArray>
    private val candidate = arrayListOf<Point>()
    private lateinit var visit: Array<BooleanArray>
    private val ai = arrayListOf<Point>()
    private val dx = arrayOf(1, -1, 0, 0)
    private val dy = arrayOf(0, 0, 1, -1)
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
            val line = br.readLine().split(" ").map { it.toInt() }.toIntArray()
            for (i in line.indices) {
                if (line[i] == 0) {
                    candidate.add(Point(it, i))
                } else if (line[i] == 2) {
                    ai.add(Point(it, i))
                }
            }
            line
        }
    }

    private fun solution() {
        for (i in 0..<candidate.size) {
            for (j in i + 1..<candidate.size) {
                search(candidate[i], candidate[j])
            }
        }
    }

    private fun search(p1: Point, p2: Point) {
        graph[p1.x][p1.y] = 1
        graph[p2.x][p2.y] = 1
        visit = Array(n) { BooleanArray(m) }
        var sum = 0
        for (i in ai.indices) {
            if (visit[ai[i].x][ai[i].y]) {
                continue
            }
            sum += bfs(ai[i])
        }
        answer = Math.max(answer,sum)
        graph[p1.x][p1.y] = 0
        graph[p2.x][p2.y] = 0
    }

    private fun bfs(p: Point): Int {
        val q = arrayListOf<Point>()
        q.add(p)
        visit[p.x][p.y] = true
        var flag = false
        var sum = 0
        while (q.isNotEmpty()) {
            val now = q.removeFirst()
            sum++
            for (dir in 0..3) {
                val mx = now.x + dx[dir]
                val my = now.y + dy[dir]

                if (checkRange(mx, my).not() || visit[mx][my]) {
                    continue
                }
                if (graph[mx][my] == 2) {
                    q.add(Point(mx, my))
                    visit[mx][my] = true
                } else if (graph[mx][my] == 0) {
                    flag = true
                }
            }
        }
        if (flag) {
            return 0
        } else {
            return sum
        }
    }

    private fun checkRange(x: Int, y: Int) = x in 0..<n && y in 0..<m
}

fun main() {
    Solution().run()
}
