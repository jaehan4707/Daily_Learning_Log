import java.util.PriorityQueue

class Solution {

    private val br = System.`in`.bufferedReader()
    private var a = 0L
    private var b = 0L

    fun run() {
        input()
        print(solution())
    }

    private fun input() {
        br.readLine().split(" ").map { it.toLong() }.apply {
            a = this[0]
            b = this[1]
        }
    }

    private fun solution(): Long {
        val pq = PriorityQueue<Pair<Long, Long>>(compareBy { it.second })
        pq.add(Pair(a, 0L))
        while (pq.isNotEmpty()) {
            val now = pq.poll()
            if (now.first == b) {
                return now.second + 1
            } else if (now.first > b) {
                continue
            }
            pq.add(Pair(now.first * 2, now.second + 1)) //2곱하기
            pq.add(Pair(now.first * 10 + 1, now.second + 1)) //1 오른쪽 추가하기
        }
        return -1
    }
}

fun main() {
    Solution().run()
}
