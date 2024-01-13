import java.io.BufferedReader
import java.io.InputStreamReader
fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val line = br.readLine().split(" ")
    row = line[0].toInt()
    col = line[1].toInt()
    val graph = Array(row, { Array(col, { 0 }) })
    for (i in 0 until row) {
        val line = br.readLine().split(" ")
        for (j in 0 until col) {
            graph[i][j] = line[j].toInt()
            if (graph[i][j] == 0)
                b.add(Pair(i, j))
            if (graph[i][j] == 2)
                q.add(Pair(i, j))
        }
    }
    var answer = 0
    for (i in 0 until b.size) {
        for (j in i + 1 until b.size) {
            for (k in j + 1 until b.size) {
                val list: MutableList<Pair<Int, Int>> = LinkedList()
                list.add(b[i])
                list.add(b[j])
                list.add(b[k])
                answer = max(answer, bfs(graph, list))
            }
        }
    }
    println(answer)
}

fun bfs(array: Array<Array<Int>>, list: MutableList<Pair<Int, Int>>): Int {  //최대한 바이러스가 덜  퍼지게 해야함.
    val visit = Array(row, { Array(col, { false }) })
    var origin = b.size - list.size
    val tempQueue: Queue<Pair<Int, Int>> = LinkedList()
    for (queue in q) {
        tempQueue.add(queue)
    }
    var tempArray = array.copyOf()
    for (l in list)
        tempArray[l.first][l.second] = 1
    while (!tempQueue.isEmpty()) {
        val now = tempQueue.poll()
        visit[now.first][now.second] = true
        for (i in 0 until 4) {
            val mx = now.first + x[i]
            val my = now.second + y[i]
            if (mx < 0 || my < 0 || mx >= row || my >= col)
                continue
            if (tempArray[mx][my] == 1 || visit[mx][my] || tempArray[mx][my] == 2) //벽이나  바이러스는 안들어감.
                continue
            origin--
            tempArray[mx][my] = 2
            tempQueue.add(Pair(mx, my))
        }
    }
    for (l in b)
        array[l.first][l.second] = 0
    return origin
}