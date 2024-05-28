import java.util.*

class Solution {

    private val br = System.`in`.bufferedReader()
    private val sb = StringBuilder()
    private lateinit var str: String
    private lateinit var bomb: String
    private val NO_ANSWER = "FRULA"


    fun run() {
        input()
        solution()
        print(sb.toString())
    }

    private fun input() {
        str = br.readLine()
        bomb = br.readLine()
    }

    private fun solution() {
        val st = Stack<Char>()
        str.forEach { ch ->
            st.push(ch)
            if (ch == bomb.last()) { //폭탄 내 같은 글자가 존재하지 않기 때문에 가능함.
                if (st.size >= bomb.length) { // 지금 들어간 스택의 크기가 폭탄보다 작다면 폭탄의 가능성은 없음.
                    for (i in bomb.length - 1 downTo 0) { //폭탄의 크기만큼 문자열을 뺀다
                        if (st[st.size - 1 - (bomb.length - 1 - i)] != bomb[i]) { //폭탄 문자열이 아니라면
                            return@forEach
                        }
                    }
                    repeat(bomb.length) {//폭탄 문자열과 일치할 경우 폭탄 문자열을 없앰
                        st.pop()
                    }
                }
            }
        }
        if (st.isEmpty()) { // 스택에 비었을 경우
            sb.append(NO_ANSWER)
        } else {
            st.forEach {
                sb.append(it)
            }
        }
    }
}

fun main() {
    Solution().run()
}
