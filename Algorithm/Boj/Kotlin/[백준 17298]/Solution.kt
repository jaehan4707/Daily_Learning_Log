import java.lang.StringBuilder
import java.util.Stack

class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private lateinit var graph: IntArray
    private lateinit var answer: IntArray
    private val sb = StringBuilder()

    data class Item(val value: Int, val idx: Int)

    fun run() {
        input()
        solution()
        print(sb.toString())
    }

    private fun input() {
        n = br.readLine().toInt()
        answer = IntArray(n) { -1 }
        graph = br.readLine().split(" ").map { it.toInt() }.toIntArray()
    }

    private fun solution() {
        val st = Stack<Item>()
        graph.forEachIndexed { index, number ->
            if (st.isEmpty()) {
                st.push(Item(number, index))
            } else {
                while (st.isNotEmpty() && st.peek().value < number) {
                    val item = st.peek()
                    if (item.value < number) { // 위에 있는것보다 큰 수가 나올 때
                        answer[item.idx] = number //오큰수
                        st.pop()
                    }
                }
                st.push(Item(number, index))
            }
        }
        answer.forEach {
            sb.append("$it ")
        }
    }
}

fun main() {
    Solution().run()
}
