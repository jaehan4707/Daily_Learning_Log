import kotlin.math.max

class Solution {

    private val br = System.`in`.bufferedReader()
    private lateinit var setting: Setting
    private lateinit var graph: Array<IntArray>
    private val points = mutableListOf<Point>()
    private var answer = 0
    private val dx = arrayOf(1, -1, 0, 0)
    private val dy = arrayOf(0, 0, 1, -1)

    init {
        run()
    }

    private fun run() {
        input()
        dfs(0, setting.r, setting.g)
        print(answer)
    }

    private fun input() {
        br.readLine().split(" ").map { it.toInt() }.apply {
            setting = Setting(this[0], this[1], this[2], this[3])
        }
        graph = Array(setting.n) { IntArray(setting.m) { 0 } }
        for (i in 0 until setting.n) {
            val line = br.readLine().split(" ").map { it.toInt() }
            for (j in 0 until setting.m) {
                graph[i][j] = line[j]
                if (graph[i][j] == 2) {
                    points.add(Point(i, j))
                }
            }
        }
    }

    private fun dfs(depth: Int, rCnt: Int, gCnt: Int) {
        if (depth == points.size) {
            if (rCnt != 0 || gCnt != 0) {
                return
            }
            answer = max(answer, bfs())
            return
        }
        if (rCnt - 1 >= 0) {
            points[depth].color = 1
            dfs(depth + 1, rCnt - 1, gCnt)
            points[depth].color = 0
        }
        if (gCnt - 1 >= 0) {
            points[depth].color = 2
            dfs(depth + 1, rCnt, gCnt - 1)
            points[depth].color = 0
        }
        points[depth].color = 0
        dfs(depth + 1, rCnt, gCnt)
        points[depth].color = 0

    }

    private fun bfs(): Int {
        var flower = 0

        val originPoints = mutableListOf<Point>()
        originPoints.addAll(points)
        val visit = Array(setting.n) { Array(setting.m) { Garden(0, 0) } }
        points.forEach { point ->
            visit[point.x][point.y] = Garden(0, point.color)
        }
        var time = 0
        while (true) {
            val tempPoints = mutableListOf<Point>()
            while (!points.isEmpty()) {
                val now = points.removeFirst()
                if (visit[now.x][now.y].energy == 3) {
                    continue
                }
                if (now.color == 0) {
                    continue
                }
                for (dir in 0 until 4) {
                    val mx = now.x + dx[dir]
                    val my = now.y + dy[dir]

                    if (!checkRange(mx, my) || graph[mx][my] == 0) { //배열을 벗어나거나, 호수일 경우
                        continue
                    }
                    if (visit[now.x][now.y].energy == visit[mx][my].energy) { //양분이 같을 경우는 무시함
                        continue
                    }
                    if (visit[mx][my].energy == 3) { //이미 꽃을 형성한 경우
                        continue
                    }
                    if (visit[mx][my].energy == 0) { //양분이 없을 경우 시간이 달라도 넣어줌.
                        visit[mx][my].energy += visit[now.x][now.y].energy
                        visit[mx][my].time = time + 1
                        tempPoints.add(Point(mx, my, visit[now.x][now.y].energy))
                    } else if (visit[now.x][now.y].energy != visit[mx][my].energy && visit[now.x][now.y].time + 1 == visit[mx][my].time) { //양분이 다르지만 시간이 같을 경우
                        visit[mx][my].energy += visit[now.x][now.y].energy //에너지 더해주기\
                        visit[mx][my].time = time + 1
                        tempPoints.add(Point(mx, my, now.color))
                    }

                }
            }
            if (tempPoints.size == 0) {
                break
            }
            points.addAll(tempPoints)
            time++
        }
        for (i in 0 until setting.n) {
            for (j in 0 until setting.m) {
                if (visit[i][j].energy == 3) {
                    flower++
                }
            }
        }
        points.addAll(originPoints)
        return flower
    }

    private fun checkRange(x: Int, y: Int) = x in 0 until setting.n && y in 0 until setting.m

    data class Setting(val n: Int, val m: Int, val r: Int, val g: Int)
    data class Point(val x: Int, val y: Int, var color: Int = 0)
    data class Garden(var time: Int, var energy: Int)
}

fun main() {
    val solution = Solution()
}