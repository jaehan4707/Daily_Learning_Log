import java.util.*

class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0

    data class Task(val deadLine: Int, val weight: Int)

    private val pq = PriorityQueue<Task>(compareBy({ it.deadLine }, { -it.weight }))

    fun run() {
        input()
        print(solution())
    }

    private fun input() {
        n = br.readLine().toInt()
        repeat(n) {
            val (d, w) = br.readLine().split(" ").map { it.toInt() }
            pq.add(Task(d, w))
        }
    }

    private fun solution() : Int{
        var time = 1
        val task = PriorityQueue<Int>()
        while (!pq.isEmpty()) {
            val now = pq.poll()
            if (time <= now.deadLine) {
                task.add(now.weight)
                time += 1
            } else {
                if(task.peek()<now.weight){
                    task.poll()
                    task.add(now.weight)
                }
            }
        }
        var answer = 0
        while(task.isNotEmpty()){
            answer+=task.poll()
        }
        return answer
    }
}

fun main() {
    Solution().run()
}
