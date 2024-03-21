import java.util.PriorityQueue

class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private var m = 0
    private lateinit var graph: Array<IntArray>
    private val points = mutableListOf<Point>()
    private val islands = mutableListOf<Island>()
    private val dx = arrayOf(1, -1, 0, 0)
    private val dy = arrayOf(0, 0, 1, -1)
    private val pq = PriorityQueue<Bridge>(compareBy { it.length })
    private lateinit var parent: IntArray

    data class Point(val x: Int, val y: Int)
    data class Island(val number: Int, val areas: MutableList<Point> = mutableListOf())
    data class Bridge(val from: Int, val to: Int, val length: Int)

    fun run() {
        input()
        makeGroup()
        for (i in 0 until islands.size) { // 섬
            for (j in 0 until islands[i].areas.size) { //섬의 내부 좌표
                val now = islands[i].areas[j]
                for (d in 0 until 4) {
                    if (isNearSea(now.x, now.y, d)) { // 방향으로 갔을 때 바다인지 아닌지
                        makeConnection(now, d)
                    }
                }
            }
        }
        print(mst())
    }


    private fun input() {
        br.readLine().split(" ").map { it.toInt() }.apply {
            n = this[0]
            m = this[1]
        }
        graph = Array(n) { IntArray(m) { 0 } }
        for (i in 0 until n) {
            val line = br.readLine().split(" ").map { it.toInt() }
            for (j in 0 until m) {
                graph[i][j] = line[j]
                if (graph[i][j] == 1) {
                    points.add(Point(i, j))
                }
            }
        }
    }

    private fun makeGroup() {
        var group = 2
        points.forEach { point ->
            if (graph[point.x][point.y] == 1) {
                grouping(point, group)
                group++
            }
        }
        parent = IntArray(group + 1) { it }
    }


    private fun grouping(point: Point, group: Int) {
        val movePoints = mutableListOf<Point>()
        movePoints.add(point)
        val cur = Island(group)
        while (!movePoints.isEmpty()) {
            val now = movePoints.removeFirst()
            if (graph[now.x][now.y] == group) {
                continue
            }
            graph[now.x][now.y] = group
            cur.areas.add(now)
            for (dir in 0 until 4) {
                val mx = now.x + dx[dir]
                val my = now.y + dy[dir]
                if (checkRange(mx, my).not() || graph[mx][my] == 0) {
                    continue
                }
                if (graph[mx][my] == group) {
                    continue
                }
                movePoints.add(Point(mx, my))
            }
        }
        islands.add(cur)
    }

    private fun makeConnection(from: Point, dir: Int) { //from -> to
        val move = arrayListOf<Point>()
        move.add(from)
        val visit = Array(n) { BooleanArray(m) { false } }
        var cnt = -1
        while (!move.isEmpty()) {
            val now = move.removeFirst()
            if (visit[now.x][now.y]) {
                continue
            }
            if (graph[now.x][now.y] != graph[from.x][from.y] && graph[now.x][now.y] != 0) { //다른 땅을 만났을 경우
                if (cnt >= 2) { //길이가 2이상일 경우
                    pq.add(Bridge(graph[from.x][from.y], graph[now.x][now.y], cnt))
                }
                break
            }
            visit[now.x][now.y] = true
            val mx = now.x + dx[dir]
            val my = now.y + dy[dir]
            if (checkRange(mx, my).not()) {
                continue
            }
            if (graph[mx][my] == graph[from.x][from.y]) { // 같은 땅을 만나면 중단
                continue
            }
            move.add(Point(mx, my))
            cnt++
        }
    }

    private fun checkRange(x: Int, y: Int) = x in 0 until n && y in 0 until m

    private fun union(o1: Int, o2: Int) {
        val parent1 = find(o1)
        val parent2 = find(o2)
        if (parent1 == parent2) {
            return
        }
        if (parent1 < parent2) {
            parent[parent2] = parent[parent1]
        } else {
            parent[parent1] = parent[parent2]
        }
    }

    private fun find(idx: Int): Int {
        if (idx == parent[idx]) {
            return idx
        }
        parent[idx] = find(parent[idx])
        return parent[idx]
    }

    private fun mst(): Int {
        var answer = 0
        var edge = 0
        while (!pq.isEmpty()) {
            val cur = pq.poll()
            if (edge == islands.size - 1) { // 종료 조건
                break
            }
            if (find(cur.from - 2) != find(cur.to - 2)) {
                union(cur.from - 2, cur.to - 2)
                answer += cur.length
                edge++
            }
        }
        if (answer == 0 || edge < islands.size - 1) answer = -1
        return answer
    }

    private fun isNearSea(x: Int, y: Int, dir: Int): Boolean { // 해당 방향으로 한칸 전진 했을때 바다가 있는지
        val mx = x + dx[dir]
        val my = y + dy[dir]
        return !(checkRange(mx, my).not() || graph[mx][my] != 0)
    }

}

fun main() {
    val solution = Solution()
    solution.run()
}