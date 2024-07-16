class Solution {

    private var n = 0
    private var m = 0
    private var l = 0
    private val graph = arrayListOf<Int>()

    init {
        input()
        print(solution())
    }

    private fun input() = with(System.`in`.bufferedReader()) {
        readLine().split(" ").map { it.toInt() }.apply {
            n = this[0]
            m = this[1]
            l = this[2]
        }
        if (n != 0) {
            val line = readLine().split(" ").map { it.toInt() }
            for (i in line.indices) {
                graph.add(line[i])
            }
        }
        graph.add(0)
        graph.add(l)
        graph.sort()
    }

    private fun solution(): Int {
        var start = 1
        var end = l //설치할 수 있는 구간
        while (start <= end) {
            val mid = (start + end) / 2
            // mid가 최대 조각인 길이로 휴게소를 몇개 설치할 수 있는가?
            if (count(mid) <= m) { // m개 이하로 설치한다면 거리를 줄여야 함.
                end = mid - 1
            } else { // m개 초과로 설치한다면 거리를 늘려야 함.
                start = mid + 1
            }
        }
        return start
    }

    private fun count(length: Int): Int {
        var sum = 0
        for (i in 1..<graph.size) {
            val dist = graph[i] - graph[i - 1]
            sum += dist / length
            if (dist % length == 0) {
                sum--
            }
        }
        return sum
    }
}

fun main() {
    Solution()
}
