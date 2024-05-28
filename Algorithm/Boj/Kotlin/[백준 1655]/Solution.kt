import java.util.*

class Solution {

    private val br = System.`in`.bufferedReader()
    private val sb = StringBuilder()
    private var n = 0
    private val minHeap = PriorityQueue<Int>()
    private val maxHeap = PriorityQueue<Int>(reverseOrder())


    fun run() {
        input()
        print(sb.toString())
    }

    private fun input() {
        n = br.readLine().toInt()
        repeat(n) {
            val number = br.readLine().toInt()
            sb.append(solution(number)).append("\n")
        }
    }

    private fun solution(number: Int): Int {
        if (maxHeap.size <= minHeap.size) { // 최대힙의 크기가 더 작다면 최대힙이 넣어줌. 항상 최대힙이 최소힙보다 크기가 커야 함.
            maxHeap.add(number)
        } else {
            minHeap.add(number)
        }

        if(minHeap.isNotEmpty() && maxHeap.isNotEmpty() && minHeap.peek()<maxHeap.peek()){ // 항상 최대힙의 루트는 최소힙의 루트보다 작아야함.
            val minNumber = minHeap.poll()
            val maxNumber = maxHeap.poll()
            minHeap.add(maxNumber)
            maxHeap.add(minNumber)
        }
        return maxHeap.peek()
    }
}

fun main() {
    Solution().run()
}
