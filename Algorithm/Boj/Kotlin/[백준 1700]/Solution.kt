class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private var m = 0
    private lateinit var graph: IntArray
    private lateinit var visit: BooleanArray
    private lateinit var index: Array<ArrayList<Int>>
    private lateinit var hole: IntArray

    fun run() {
        input()
        print(solution())
    }

    private fun input() {
        br.readLine().split(" ").map { it.toInt() }.apply {
            n = this[0]
            m = this[1]
        }
        graph = IntArray(m)
        index = Array(m + 1) { ArrayList() }
        hole = IntArray(n)
        visit = BooleanArray(m + 1)
        val line = br.readLine().split(" ").map { it.toInt() }
        for (i in line.indices) {
            graph[i] = line[i]
            index[line[i]].add(i)
        }
    }

    private fun solution(): Int {
        var check = 0
        var sum = 0
        for (i in graph.indices) {
            if (check < n && hole[check] == 0 && visit[graph[i]].not()) {
                hole[check] = graph[i]
                check += 1
                index[graph[i]].removeFirst()
                visit[graph[i]] = true
            } else {
                var minValue = 0
                var minIndex = 0
                var flag = false
                for (j in 0..<n) {
                    if (hole[j] == graph[i]) {
                        minIndex = j
                        flag = true
                        break
                    }
                    if (index[hole[j]].isNotEmpty() && minValue < index[hole[j]].first()) {
                        minValue = index[hole[j]].first()
                        minIndex = j
                    } else if (index[hole[j]].isEmpty()) {
                        minIndex = j
                        minValue = m + 1
                    }
                }
                if (flag.not()) {
                    visit[hole[minIndex]] = false
                    visit[graph[i]] = true
                    hole[minIndex] = graph[i]
                    index[graph[i]].removeFirst()
                    sum += 1
                } else if (flag && index[hole[minIndex]].size != 0) {
                    index[hole[minIndex]].removeFirst()
                }
            }
        }
        return sum
    }
}

fun main() {
    Solution().run()
}
