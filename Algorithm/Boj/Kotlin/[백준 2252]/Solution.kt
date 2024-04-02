import kotlin.text.StringBuilder

class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private var m = 0
    private lateinit var graph: Array<ArrayList<Int>>
    private lateinit var edges: IntArray
    private val sb = StringBuilder()
    fun run() {
        input()
        solve()
        print(sb.toString())
    }

    private fun input() {
        br.readLine().split(" ").map { it.toInt() }.apply {
            n = this[0]
            m = this[1]
        }
        graph = Array(n + 1) { ArrayList() }
        edges = IntArray(n + 1) // 들어오는 간선의 개수
        repeat(m) {
            br.readLine().split(" ").map { it.toInt() }.apply {
                graph[this[0]].add(this[1]) // this[0] -> this[1]
                edges[this[1]]++ // this[1]로 들어오는 간선을 저장
            }
        }
    }

    private fun solve() {
        val dq = ArrayDeque<Int>()
        for(i in 1 ..  n){
            if(edges[i]==0){
                dq.add(i)
            }
        }
        while (!s.isEmpty()) {
            val now = dq.removeFirst()
            sb.append(now).append(" ")
            for (i in 0 until graph[now].size) {
                edges[graph[now][i]]--
                if(edges[graph[now][i]]==0){
                    dq.addFirst(graph[now][i])
                }
            }
        }
    }
}


fun main() {
    val solution = Solution().run()
}