class Solution {

    private val br = System.`in`.bufferedReader()
    private lateinit var ary: IntArray
    private lateinit var input: IntArray
    private val sb = StringBuilder()

    fun run() {
        input()
        print(sb.toString())
    }

    private fun input() {
        while (true) {
            val line = br.readLine().split(" ").map { it.toInt() }.toIntArray()
            if (line[0] == 0) {
                break
            }
            input = line.copyOfRange(1, line.size)
            ary = IntArray(6)
            solution(0, 0)
            sb.append("\n")
        }
    }

    private fun solution(depth: Int, cnt: Int) {
        if (depth == 6) {
            ary.forEach {
                sb.append("$it ")
            }
            sb.append("\n")
            return
        }
        for (i in cnt..<input.size) {
            ary[depth] = input[i]
            solution(depth + 1, i + 1)
        }
    }
}

fun main() {
    Solution().run()
}
