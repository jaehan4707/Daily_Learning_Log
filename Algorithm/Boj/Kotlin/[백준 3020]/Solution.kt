class Solution {
    private val br = System.`in`.bufferedReader()
    private var n = 0
    private var m = 0
    private lateinit var graph: IntArray
    private lateinit var stalagmite: IntArray // 석순
    private lateinit var stalactite: IntArray // 종유석
    private var answer = arrayOf(Int.MAX_VALUE, Int.MAX_VALUE)

    fun run() {
        input()
        solution()
        print("$answer[0] $answer[1]")
    }

    private fun input() {
        br.readLine().split(" ").map { it.toInt() }.apply {
            n = this[0]
            m = this[1]
        }
        graph = IntArray(n)
        stalagmite = IntArray(n / 2)
        stalactite = IntArray(n / 2)
        repeat(n) { idx ->  //짝수 석순 홀수 종유석
            if (idx % 2 == 0) {
                stalagmite[idx / 2] = br.readLine().toInt()
            } else {
                stalactite[idx / 2] = br.readLine().toInt()
            }
        }
        stalagmite.sortDescending()
        stalactite.sortDescending()
    }

    private fun solution() {
        for (i in 1..m) {
            val result = stalagmiteSearch(i) + stalactiteSearch(m - i)
            if (answer[0] > result) {
                answer[0] = result
                answer[1] = 1
            } else if (answer[0] == result) {
                answer[1] += 1
            }
        }
    }

    private fun stalactiteSearch(h: Int): Int {
        var start = 0
        var end = stalactite.size - 1
        while (start <= end) {
            val mid = (start + end) / 2
            if (stalactite[mid] >= h) { // 종유석이 h보다 작거나 같으면 깰 수 있다.
                start = mid + 1
            } else {
                end = mid - 1
            }
        }
        return start
    }

    private fun stalagmiteSearch(h: Int): Int {
        var start = 0
        var end = stalagmite.size - 1
        while (start <= end) {
            val mid = (start + end) / 2
            if (stalagmite[mid] > h) { // 석순의 높이가 h보다 크거나 같으면 깰 수 있다.
                start = mid + 1
            } else {
                end = mid - 1
            }
        }
        return start
    }
}

fun main() {
    Solution().run()
}