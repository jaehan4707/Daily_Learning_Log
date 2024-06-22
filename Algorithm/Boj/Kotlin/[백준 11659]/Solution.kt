class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private var m = 0
    private lateinit var graph: IntArray
    private lateinit var sum: IntArray
    private val sb = StringBuilder()


    fun run() {
        input()
        print(sb.toString())
    }

    private fun input() {
        br.readLine().split(" ").map { it.toInt() }.apply {
            n = this[0]
            m = this[1]
        }
        graph = IntArray(n + 1)
        sum = IntArray(n + 1)
        val line = br.readLine().split(" ").map { it.toInt() }
        for (i in 1..line.size) {
            graph[i] = line[i - 1]
            sum[i] = sum[i - 1] + graph[i]
        }
        repeat(m) {
            val (s, f) = br.readLine().split(" ").map { it.toInt() }
            sb.append(solution(s, f)).append("\n")
        }
    }

    private fun solution(s: Int, f: Int) = sum[f] - sum[s] + graph[s]
}

fun main() {
    Solution().run()
}
