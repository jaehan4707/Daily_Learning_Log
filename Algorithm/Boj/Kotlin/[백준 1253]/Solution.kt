class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private lateinit var graph: LongArray
    private var answer = 0


    fun run() {
        input()
        solution()
        print(answer)
    }

    private fun input() {
        n = br.readLine().toInt()
        graph = br.readLine().split(" ").map { it.toLong() }.toLongArray().sortedArray()
    }

    private fun solution() {
        graph.forEachIndexed { index, number ->
            if (search(number, index)) {// number를 두개의 숫자 합으로 표현할 수 있는지 확인
                answer++
            }
        }
    }

    private fun search(number: Long, idx: Int): Boolean { //지금 무한반복이 도는데,
        var left = 0
        var right = n - 1
        while (left < right) {
            val sum = graph[left] + graph[right]
            if (number == sum) {
                if (left != idx && right != idx) {
                    return true
                }
                if (left == idx) {
                    left += 1
                }
                if (right == idx) {
                    right -= 1
                }
            } else if (number > sum) { // number가 더 클 경우
                left += 1
            } else { // number가 더 작은 경우
                right -= 1
            }
        }
        return false
    }


}

fun main() {
    Solution().run()
}
