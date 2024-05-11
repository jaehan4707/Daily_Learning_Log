import java.util.*

class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private lateinit var ary: IntArray
    private val count = IntArray(1000001)
    private val sb = StringBuilder()

    fun run() {
        input()
        solution()
        print(sb.toString())
    }

    private fun input() {
        n = br.readLine().toInt()
        ary = IntArray(n)

        val line = br.readLine().split(" ").map { it.toInt() }
        for (i in line.indices) {
            ary[i] = line[i]
            count[ary[i]]++
        }
    }

    private fun solution() {
        val s = Stack<Int>()
        val answer = IntArray(n)
        s.push(ary.last())
        answer[n - 1] = -1
        for (i in n - 2 downTo 0) {
            while (!s.isEmpty() && count[s.peek()] <= count[ary[i]]) {
                s.pop()
            }
            if (s.size == 0) {
                answer[i] = -1
            } else {
                answer[i] = s.peek()
            }
            s.push(ary[i])
        }
        answer.forEach {
            sb.append(it).append(" ")
        }
    }
}

fun main() {
    Solution().run()
}
