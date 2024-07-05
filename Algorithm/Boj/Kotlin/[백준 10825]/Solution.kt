import java.util.*

class Solution {

    private var n = 0
    private val br = System.`in`.bufferedReader()
    private val sb = StringBuilder()
    private val pq = PriorityQueue<Item>(
        compareBy({ -it.first },
            { it.second },
            { -it.third },
            { it.name })
    )


    data class Item(val name: String, val first: Int, val second: Int, val third: Int)


    fun run() {
        input()
        solution()
        print(sb.toString())
    }

    private fun input() {
        n = br.readLine().toInt()
        repeat(n) {
            br.readLine().split(" ").apply {
                pq.add(Item(this[0], this[1].toInt(), this[2].toInt(), this[3].toInt()))
            }
        }
    }

    private fun solution() {
        while (pq.isNotEmpty()) {
            sb.append(pq.poll().name).append("\n")
        }
    }

}

fun main() {
    Solution().run()
}