class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private var m = 0
    private val ary = IntArray(8)
    private val sb = StringBuilder()

    fun run() {
        input()
        solution(0)
        print(sb.toString())
    }

    private fun input() {
        br.readLine().split(" ").map { it.toInt() }.apply {
            n = this[0]
            m = this[1]
        }
    }

    private fun solution(depth: Int) {
        if (depth == m) {
            ary.forEachIndexed { index, i ->
                if (index >= m) {
                    return@forEachIndexed
                }
                sb.append("$i ")
            }
            sb.append("\n")
            return
        }
        for (i in 1..n) {
            ary[depth] = i
            solution(depth + 1)
        }
    }
}

fun main() {
    Solution().run()
}
