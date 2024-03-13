class Solution {
    private val br = System.`in`.bufferedReader()
    private var n = 0
    private var m = 0
    private lateinit var graph: Array<IntArray>
    private val dir =
        arrayOf(intArrayOf(1, 0), intArrayOf(-1, 0), intArrayOf(0, 1), intArrayOf(0, -1))
    private var cheese = arrayListOf<Pair<Int, Int>>()
    private var answer = 0

    init {
        run()
    }

    private fun run() {
        input()
        meltingCheese()
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

    private fun meltingCheese() {
        while (!isMelted()) { // 치즈가 전부 녹았는지를 검사
            divideAir() // 외부공기 내부공기 구분
            while (cheese.isNotEmpty()) {
                val now = cheese.removeFirst()
                if (isAroundAir(now.first, now.second).not()) { //공기가 주변에 없다면
                    graph[now.first][now.second] = 0 //치즈를 녹임
                }
            }
            answer++
        }
    }

    private fun divideAir() {
        val q = arrayListOf<Pair<Int, Int>>()
        val visit = Array(n) { BooleanArray(m) { false } }
        q.add(Pair(0, 0))
        while (q.isNotEmpty()) {
            val now = q.removeAt(0)
            if (visit[now.first][now.second]) {
                continue
            }
            visit[now.first][now.second] = true
            graph[now.first][now.second] = -1 //진짜 외부 공기
            for (d in dir.indices) {
                val mx = now.first + dir[d][0]
                val my = now.second + dir[d][1]
                if (!checkedRange(mx, my)) {
                    continue
                }
                if (graph[mx][my] == 1) {
                    cheese.add(Pair(mx, my))
                    continue
                }
                if (visit[mx][my]) {
                    continue
                }
                q.add(Pair(mx, my))
            }
        }

    }

    private fun isAroundAir(row: Int, col: Int): Boolean {
        var meetAir = 0
        for (d in dir.indices) {
            val mx = row + dir[d][0]
            val my = col + dir[d][1]
            if (!checkedRange(mx, my)) {
                continue
            }
            if (graph[mx][my] != -1) { //1과 -1은 치즈, -1은 외부공기
                continue
            }
            meetAir++
        }
        return meetAir < 2 //인접한 변이 2변 이상이어야 함 따라서 0과 1이면 false
    }

    private fun checkedRange(row: Int, col: Int) = row in 0 until n && col in 0 until m

    private fun isMelted(): Boolean {
        for (i in 0 until n) {
            for (j in 0 until m) {
                if (graph[i][j] == 1)
                    return false
            }
        }
        return true
    }
}

fun main() {
    val solution = Solution()
}