class Solution {

    private val br = System.`in`.bufferedReader()
    private lateinit var ary: Array<Pair<Int, Int>>
    private var t = 0
    private var n = 0
    private val sb = StringBuilder()

    fun run() {
        input()
        print(sb.toString())
    }

    private fun input() {
        t = br.readLine().toInt()
        repeat(t) {
            n = br.readLine().toInt()
            ary = Array(n) { Pair(0, 0) }
            repeat(n) {
                val (n, m) = br.readLine().split(" ").map { it.toInt() }
                ary[it] = Pair(n, m)
            }
            ary.sortBy { it.first }
            sb.append(solution()).append("\n")
        }
    }

    private fun solution(): Int {
        var answer = 1
        var lastScore = ary[0].second
        for (i in 1..<n) {
            if (lastScore > ary[i].second) {
                lastScore = ary[i].second
                answer++
            }
        }
        return answer
    }
}

fun main() {
    Solution().run()
}
