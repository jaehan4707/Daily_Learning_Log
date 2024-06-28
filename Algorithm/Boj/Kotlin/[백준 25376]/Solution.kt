import java.util.LinkedList
import java.util.Queue

class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private lateinit var switches: Array<ArrayList<Int>>
    private var visited = 0
    private var answer = 0


    fun run() {
        input()
        if (visited == answer - 1) {
            print(0)
        } else {
            print(bfs())
        }
    }

    private fun input() {
        n = br.readLine().toInt()
        answer = 1 shl (n)
        switches = Array(n) { arrayListOf() }
        val line = br.readLine().split(" ").map { it.toInt() }.toIntArray()
        for (i in line.indices) {
            visited = visited or (line[i] shl (i))
        }
        repeat(n) { idx ->
            val line = br.readLine().split(" ").map { it.toInt() }.toIntArray()
            for (i in 1..line[0]) {
                switches[idx].add(line[i] - 1)
            }
        }
    }

    private fun bfs(): Int { //idx를 시작으로 스위치를 켭니다.
        val q: Queue<Pair<Int, Int>> = LinkedList()
        val on = BooleanArray(answer)
        q.add(Pair(visited, 0))
        on[visited] = true
        while (q.isNotEmpty()) {
            val now = q.remove()
            for (i in 0..<n) {
                val next = 1 shl i //i번째 스위치
                var nowVisited = now.first
                if ((now.first and next) == 0) { //꺼진 스위치라면
                    nowVisited = nowVisited or next //해당 스위치 켜고
                    switches[i].forEach { //영향 받는 스위치 쫙
                        nowVisited = nowVisited xor (1 shl it)
                    }
                    if (on[nowVisited]) {
                        continue
                    }
                    if (nowVisited == answer - 1) {
                        return now.second + 1
                    }
                    on[nowVisited] = true
                    q.add(Pair(nowVisited, now.second + 1))
                }
            }
        }
        return -1
    }


}

fun main() {
    Solution().run()
}
