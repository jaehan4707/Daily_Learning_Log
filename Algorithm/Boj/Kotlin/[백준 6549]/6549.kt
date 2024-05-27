import java.lang.StringBuilder
import java.util.Stack
import kotlin.math.*

class `6549` {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private lateinit var graph: LongArray
    private val sb = StringBuilder()
    private var s = 0L

    data class Item(val value: Long, val idx: Int)

    fun run() {
        input()
        print(sb.toString())
    }

    private fun input() {
        while (true) {
            val line = br.readLine().split(" ").map { it.toLong() }
            if (line[0] == 0L) {
                sb.setCharAt(sb.lastIndex, ' ')
                break
            }
            n = line[0].toInt()
            graph = LongArray(n)
            for (i in 1..<line.size) {
                graph[i - 1] = line[i]
            }
            s = 0L
            solution()
            sb.append("\n")
        }
    }

    private fun solution() {
        val st = Stack<Item>()
        graph.forEachIndexed { index, number ->
            if (st.isEmpty()) {
                st.push(Item(number, index))
            } else {
                while (st.isNotEmpty() && st.peek().value > number) {
                    val item = st.pop()
                    var width = index
                    if (st.isNotEmpty()) {
                        width = index - st.peek().idx - 1
                    }
                    s = max(s, width * item.value) //너비 최대값 찾기
                }
                st.push(Item(number, index))
            }
        }
        while (!st.isEmpty()) { //남아있는 기둥이 있는 경우 뒤에 있는 기둥 중 자기보다 작은 기둥이 없는 경우
            val item = st.pop()
            var width = n//가로 길이
            if (st.isNotEmpty()) {
                width = n - st.peek().idx - 1
            }
            s = max(s, width * item.value)
        }
        sb.append(s)
    }
}

fun main() {
    `6549`().run()
}
