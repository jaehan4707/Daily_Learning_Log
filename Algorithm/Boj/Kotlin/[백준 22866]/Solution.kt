import java.util.Stack
import kotlin.math.*

class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private val sb = StringBuilder()
    private lateinit var building: IntArray
    private lateinit var watchBuilding: IntArray
    private lateinit var nearBuilding: IntArray
    fun run() {
        input()
        solution()
        print(sb.toString())
    }

    private fun input() {
        n = br.readLine().toInt()
        building = br.readLine().split(" ").map { it.toInt() }.toIntArray()
        watchBuilding = IntArray(n)
        nearBuilding = IntArray(n) { Int.MAX_VALUE }
    }

    private fun solution() {
        val left = Stack<Int>()
        for (i in 0..<n) { //0번부터 n까지 index 돌면서
            while (left.isNotEmpty() && building[left.peek()] <= building[i]) {
                left.pop()
            }
            watchBuilding[i] = left.size
            if (left.size > 0) { //볼 수 있는 잔여 건물이 있을 경우
                nearBuilding[i] = left.peek()
            }
            left.push(i)
        }
        val right = Stack<Int>()
        for (i in n - 1 downTo 0) {
            while (right.isNotEmpty() && building[right.peek()] <= building[i]) {
                right.pop()
            }
            watchBuilding[i] += right.size
            if (right.size > 0) { //가장 가까운 빌딩 업그레이드하기.
                if (abs(nearBuilding[i] - i) > abs(i - right.peek())) {
                    nearBuilding[i] = right.peek()
                }
            }
            right.push(i)
        }
        for (i in 0..<n) {
            if (watchBuilding[i] > 0) {
                sb.append(watchBuilding[i]).append(" ").append(nearBuilding[i] + 1).append("\n")
            } else {
                sb.append(0).append("\n")
            }
        }
    }
}

fun main() {
    Solution().run()
}