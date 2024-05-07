import java.util.Stack

class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private val sb = StringBuilder()
    private lateinit var ary: IntArray
    private lateinit var near: IntArray

    fun run() {
        input()
        solution()
        print(sb.toString())
    }

    private fun input() {
        n = br.readLine().toInt()
        ary = br.readLine().split(" ").map { it.toInt() }.toIntArray()
        near = IntArray(n){-1}
    }

    private fun solution() {
        val s = Stack<Int>()
        for (i in n - 1 downTo 0) { //오른쪽에 있으면서 가장 왼쪽에 있는 수
            while (s.isNotEmpty() && ary[s.peek()] <= ary[i]) {
                s.pop()
            }
            if (s.size > 0) {
                near[i] = ary[s.peek()]
            }
            s.push(i)
        }
        for (i in 0..<n) {
            sb.append(near[i]).append(" ")
        }
    }
}

fun main() {
    Solution().run()
}