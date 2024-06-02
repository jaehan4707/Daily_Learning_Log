class Solution {

    private val br = System.`in`.bufferedReader()
    private val sb = StringBuilder()
    private var n = 0
    private lateinit var graph: Array<CharArray>

    fun run() {
        input()
        solution(1, n, n)
        for (i in 1..n) {
            for (j in 1..2 * n) {
                sb.append(graph[i][j])
            }
            sb.append("\n")
        }
        print(sb.toString())
    }

    private fun input() {
        n = br.readLine().toInt()
        graph = Array(n + 1) { CharArray(n * 2 + 1) { ' ' } }
    }

    private fun solution(x: Int, y: Int, size: Int) { // 층, 크기,
        if (size == 3) {
            graph[x][y] = '*'
            graph[x + 1][y - 1] = '*'
            graph[x + 1][y + 1] = '*'
            for (i in y - 2..y + 2) {
                graph[x + 2][i] = '*'
            }
            return
        }

        solution(x, y, size / 2) //가운데
        solution(x + size / 2, y - size / 2, size / 2) //왼쪽
        solution(x + size / 2, y + size / 2, size / 2) //오른쪽
    }
}

fun main() {
    Solution().run()
}
