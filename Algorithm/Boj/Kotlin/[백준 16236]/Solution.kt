import java.util.PriorityQueue

class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private lateinit var graph: Array<IntArray>
    private lateinit var start: Shark
    private val dx = arrayOf(0, -1, 1, 0)
    private val dy = arrayOf(-1, 0, 0, 1)

    data class Shark(val x: Int, val y: Int, val size: Int = 2, var eat: Int = 0, var time: Int = 0)
    data class Fish(val x: Int, val y: Int, val distance: Int = 0)

    fun run() {
        input()
        print(solve())
    }

    private fun input() {
        n = br.readLine().toInt()
        graph = Array(n) { IntArray(n) }
        for (i in 0..<n) {
            val line = br.readLine().split(" ").map { it.toInt() }
            for (j in 0..<n) {
                graph[i][j] = line[j]
                if (graph[i][j] == 9) {
                    start = Shark(i, j)
                    graph[i][j] = 0
                }
            }
        }
    }

    private fun solve(): Int {
        val q = arrayListOf<Shark>()
        q.add(start)
        while (!q.isEmpty()) { // 아기 상어
            val now = q.removeFirst()
            val result = canEating(now)
            if (result != null) {
                now.eat += 1
                if (now.eat == now.size) {
                    q.add(
                        now.copy(
                            x = result.x,
                            y = result.y,
                            size = now.size + 1,
                            eat = 0,
                            time = now.time + result.distance
                        )
                    )
                } else {
                    q.add(
                        now.copy(
                            x = result.x,
                            y = result.y,
                            eat = now.eat,
                            time = now.time + result.distance
                        )
                    )
                }
            } else {
                return now.time
            }
        }
        return 0
    }

    private fun canEating(shark: Shark): Fish? {
        val q = arrayListOf<Shark>()
        q.add(shark.copy(time = 0))
        val fish = PriorityQueue<Fish>(compareBy({ it.distance }, { it.x }, { it.y }))
        val visit = Array(n) { BooleanArray(n) }
        visit[shark.x][shark.y] = true
        while (!q.isEmpty()) {
            val now = q.removeFirst()
            for (d in 0..<4) {
                val mx = now.x + dx[d]
                val my = now.y + dy[d]
                if (checkRange(mx, my).not() || graph[mx][my] > now.size || visit[mx][my]) {
                    continue
                }
                if (graph[mx][my] != 0 && graph[mx][my] < now.size) { //0이 아니고 사이즈가 작을때 먹을 수 있음
                    fish.add(Fish(x = mx, y = my, distance = now.time + 1))
                }
                visit[mx][my] = true
                q.add(now.copy(x = mx, y = my, time = now.time + 1))
            }
        }
        if (fish.size == 0) {
            return null
        } else {
            val resultFish = fish.poll()
            graph[resultFish.x][resultFish.y] = 0
            return resultFish
        }
    }

    private fun checkRange(x: Int, y: Int) = x in 0 until n && y in 0 until n
}

fun main() {
    val solution = Solution().run()
}