import java.util.*

class Solution {

    private val br = System.`in`.bufferedReader()
    private var line = ""

    fun run() {
        input()
        print(solution())
    }

    private fun input() {
        line = br.readLine()
    }

    private fun solution(): Int {
        var length = 0
        val stack = Stack<Int>() //곱하는 숫자가 있는 곳
        for (i in line.indices) {
            if (line[i].isDigit()) { //숫자일 경우 그대로 더하기
                if (i + 1 < line.length && line[i + 1] == '(') {
                    stack.push(line[i].digitToInt()) //곱하는 숫자일
                } else {
                    stack.push(1) //그냥 숫자일뿐
                }
            } else if (line[i] == '(') { //왼쪽 괄호일 경우
                stack.push(-1)
            } else if (line[i] == ')') {
                var len = 0
                while (stack.isNotEmpty() && stack.peek() != -1) { // ()안 숫자 구하기
                    len += stack.pop()
                }
                stack.pop() // ( 빼기
                val k = stack.pop()
                len *= k
                stack.push(len)
            }
        }
        while (stack.isNotEmpty()){
            length += stack.pop()
        }
        return length
    }
}

fun main() {
    Solution().run()
}
