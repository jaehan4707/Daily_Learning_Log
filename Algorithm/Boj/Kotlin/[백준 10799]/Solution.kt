import java.util.Stack

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
        var answer = 0
        val stack = Stack<Char>()
        for (i in line.indices) {
            if (line[i] == '(') {
                stack.push(line[i])
            } else { // )
                if (stack.peek() == '(' && line[i - 1] == '(') { //레이저 발동
                    stack.pop()
                    answer += stack.size
                } else if (stack.peek() == '(' && line[i - 1] == ')') {
                    stack.pop()
                    answer += 1
                }
            }
        }
        return answer
    }
}

fun main() {
    Solution().run()
}
