import java.util.Deque
import java.util.LinkedList

class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private var k = 0
    private var l = 0

    data class Point(val x: Int, val y: Int, val time: Int = 0, val dir: Int = 0)

    private val options = hashMapOf<Int, Char>()

    val dx = arrayOf(0, 1, 0, -1)
    val dy = arrayOf(1, 0, -1, 0)

    private lateinit var graph: Array<IntArray>

    fun run() {
        input()
        print(solution())
    }

    private fun input() {
        n = br.readLine().toInt()
        k = br.readLine().toInt()
        graph = Array(n + 1) { IntArray(n + 1) }
        repeat(k) { // 사과 위치
            val (a, b) = br.readLine().split(" ").map { it.toInt() }
            graph[a][b] = 2 //2는 사과라는뜻
        }
        l = br.readLine().toInt()
        repeat(l) {
            val (a, b) = br.readLine().split(" ")
            options[a.toInt()] = b.first()
        }
        br.close()
    }

    private fun solution(): Int {
        val dq: Deque<Point> = LinkedList()
        graph[1][1] = 1 //1은 뱀이 지나간 자리
        dq.addFirst(Point(1, 1))
        while (true) {
            val now = dq.last() //머리를 꺼냄
            var nowDir = now.dir
            options[now.time]?.let { option ->
                nowDir = changeDirections(now.dir, option) // 방향 전환
            }
            val mx = now.x + dx[nowDir]
            val my = now.y + dy[nowDir]
            if (checkRange(mx, my).not() || graph[mx][my] == 1) { // 벽에 부딪혔거나 몸통을 만날 경우
                return now.time + 1
            }
            if (graph[mx][my] == 0) { // 사과가 없을 경우 꼬리를 잘라냄.
                val tail = dq.removeFirst()
                graph[tail.x][tail.y] = 0
            }
            graph[mx][my] = 1
            dq.addLast(Point(mx, my, now.time + 1, nowDir)) // 머리를 이동
        }
    }

    private fun changeDirections(dir: Int, opt: Char): Int {  // opt가 D면 오른쪽으로 90도, 왼쪽으로 90도
        if (opt == 'D') {
            return (dir + 1) % 4
        } else {
            return (dir + 3) % 4
        }
    }

    private fun checkRange(x: Int, y: Int) = x in 1..n && y in 1..n

}

fun main() {
    Solution().run()
}
