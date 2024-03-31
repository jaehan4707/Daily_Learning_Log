class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private var answer = 0
    fun run() {
        input()
        print(answer)
    }

    private fun input() {
        n = br.readLine().toInt()
        val line = br.readLine().split(" ").map { it.toInt() }
        var left = 0
        var right = 0
        for (i in 0 until line.size) {
            if (line[i] == 2) {
                right++;
                left--
            } else {
                left++;
                right--
            }
            if (left < 0) {
                left = 0
            }
            if (right < 0) {
                right = 0
            }
            answer = Math.max(answer, Math.max(left, right))
        }
    }
}

fun main() {
    val solution = Solution().run()
}