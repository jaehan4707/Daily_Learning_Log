import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import kotlin.math.min

var size = 0
val q: Queue<Pair<Int, Int>> = LinkedList()
val edgeQ: Queue<Pair<Int, Int>> = LinkedList()
val x = arrayOf(1, -1, 0, 0)
val y = arrayOf(0, 0, 1, -1)
fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    size = br.readLine().toInt()
    val graph = Array(size) { Array(size, { 0 }) }
    var visit = Array(size, { Array(size, { false }) })
    var cnt = 1
    for (i in 0 until size) {
        val token = br.readLine().split(" ")
        for (j in 0 until size) {
            graph[i][j] = token[j].toInt()
        }
    }
    for (i in 0 until size) {
        for (j in 0 until size) {
            if (graph[i][j] == 1 && !visit[i][j]) {
                island(graph, visit, Pair(i, j), cnt)
                cnt++
            }
        }
    }
    print(connection(graph))
}

fun island(ary: Array<Array<Int>>, visit: Array<Array<Boolean>>, loc: Pair<Int, Int>, cnt: Int) { //라벨링
    q.add(loc)
    while (!q.isEmpty()) {
        val now = q.poll()
        if (visit[now.first][now.second])
            continue
        visit[now.first][now.second] = true
        ary[now.first][now.second] = cnt
        var check = false
        for (i in 0 until 4) {
            val mx = now.first + x[i]
            val my = now.second + y[i]
            if (mx !in ary.indices || my !in ary.indices) //배열 범위가 아닌 경우
                continue
            if (visit[mx][my]) //방문처리.
                continue
            if (ary[mx][my] == 0) {//바다 일 경우
                check = true
                continue
            }
            q.add(Pair(mx, my))
        }
        if (check) //한면이라도 바다와 인접한 경우
            edgeQ.add(now)
    }
}

fun connection(ary: Array<Array<Int>>): Int { //다리 연결
    val tempQ: Queue<Pair<Pair<Int, Int>, Int>> = LinkedList()
    var answer = 100000
    while (!edgeQ.isEmpty()) {
        tempQ.add(Pair(edgeQ.poll(), 0))
        val marker = ary[tempQ.peek().first.first][tempQ.peek().first.second] //현재 대륙의 라벨 값
        var visit = Array(size, { Array(size, { false }) })
        while (!tempQ.isEmpty()) {
            val now = tempQ.poll()
            if (visit[now.first.first][now.first.second])
                continue
            visit[now.first.first][now.first.second] = true
            for (i in 0 until 4) {
                val mx = now.first.first + x[i]
                val my = now.first.second + y[i]
                if (mx !in ary.indices || my !in ary.indices) //배열 범위이 아닌 경우
                    continue
                if (visit[mx][my]) //방문한 경우
                    continue
                if (ary[mx][my] == marker) //같은 대륙일 경우
                    continue
                if (ary[mx][my] != 0) { //다른대륙이고 바다가 아닌 경우
                    answer = min(answer, now.second + 1)
                    continue
                }
                tempQ.add(Pair(Pair(mx, my), now.second + 1))
            }
        }
    }
    return answer - 1
}