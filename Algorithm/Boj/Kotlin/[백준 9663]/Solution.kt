class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private var answer = 0
    private lateinit var result: IntArray
    fun run() {
        input()
        solve(0)
        print(answer)
    }

    private fun input() {
        n = br.readLine().toInt()
        result = IntArray(n)
    }

    private fun solve(depth: Int) {
        if (depth == n) { //그만하기
            answer += 1
            return
        }

        for (i in 0 until n) {
            result[depth] = i
            if (isCorrect(depth)) {
                solve(depth + 1)
            }
            result[depth] = 0
        }
    }

    private fun isCorrect(limit: Int): Boolean {
        for (i in 0 until limit) {
            if (result[i] == result[limit] || Math.abs(i - limit) == Math.abs(result[i] - result[limit])) {
                return false
            }
        }
        return true
    }
}

fun main() {
    val solution = Solution().run()
}