class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private var m = 0
    private var k = 0
    private lateinit var dp: Array<LongArray>
    private val roads = hashSetOf<Road>()
    data class Point(val x: Int, val y: Int)
    data class Road(val start: Point, val end: Point)

    fun run() {
        input()
        solve()
        print(dp[n][m])
    }

    private fun input() {
        br.readLine().split(" ").map { it.toInt() }.apply {
            n = this[0]
            m = this[1]
        }
        k = br.readLine().toInt()
        dp = Array(n + 1) { LongArray(m + 1) }
        repeat(k) {
            br.readLine().split(" ").map { it.toInt() }.apply {
                val (x1, y1, x2, y2) = this
                if (y1 == y2) { //세로 방향
                    roads.add(Road(Point(Math.min(x1, x2), y1), Point(Math.max(x1, x2), y1)))
                } else if (x1 == x2) { //가로
                    roads.add(Road(Point(x1, Math.min(y1, y2)), Point(x1, Math.max(y1, y2))))
                }
            }
        }
        dp[0][0] = 1
        for (i in 1..n) {
            if (roads.contains(Road(Point(i - 1, 0), Point(i, 0)))) {
                break
            }
            dp[i][0] = 1
        }
        for (i in 1..m) {
            if (roads.contains(Road(Point(0, i - 1), Point(0, i)))) {
                break
            }
            dp[0][i] = 1
        }
    }

    private fun solve() {
        for (i in 1..n) {
            for (j in 1..m) {
                val up = Point(i - 1, j)
                val left = Point(i, j - 1)
                val target = Point(i, j)
                if (!roads.contains(Road(up, target))) {
                    dp[i][j] += dp[i - 1][j]
                }
                if (!roads.contains(Road(left, target))) {
                    dp[i][j] += dp[i][j - 1]
                }
            }
        }
    }
}

fun main() {
    val solution = Solution().run()
}