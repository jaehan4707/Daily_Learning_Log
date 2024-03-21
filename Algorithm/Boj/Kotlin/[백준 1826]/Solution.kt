import java.util.PriorityQueue

class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private val pq = PriorityQueue<Int>(compareBy { -it })
    private lateinit var stations: Array<Station>
    private var endDistance = 0
    private var initAmount = 0

    data class Station(val distance: Int, val amount: Int)

    fun run() {
        input()
        print(move())
    }

    private fun input() {
        br.readLine().split(" ").map { it.toInt() }.apply {
            n = this[0]
        }
        stations = Array(n) {
            val line = br.readLine().split(" ").map { it.toInt() }
            Station(line[0], line[1])
        }
        stations.sortBy { it.distance }
        br.readLine().split(" ").map { it.toInt() }.apply {
            endDistance = this[0]
            initAmount = this[1]
        }
    }

    private fun move(): Int {
        var stationsNumber = 0
        var myDistance = initAmount
        var stop = 0
        while (stationsNumber < stations.size) {
            if (myDistance >= endDistance) {
                return stop
            }
            val result = findStation(myDistance, stationsNumber)
            if (result == -1) { //더이상 가지 못하는 경우
                val maxAmount = pq.poll() ?: break
                myDistance += maxAmount
                stop++
            } else {
                stationsNumber = result
            }
        }
        //끝까지 갔는데 앞에 충전을 안해서 못간 경우가 있음.
        while (!pq.isEmpty()) {
            myDistance += pq.poll()
            stop++
            if (myDistance >= endDistance) {
                return stop
            }
        }
        return -1
    }

    private fun findStation(dist: Int, idx: Int): Int { //현재 갈 수 있는 주유소중 가장 연료가 많은 곳
        if (stations[idx].distance > dist) {
            return -1
        }
        var maxIdx = -1
        for (i in idx until stations.size) {
            if (stations[i].distance > dist) {
                maxIdx = i
                break
            }
            pq.add(stations[i].amount)
        }
        return maxIdx
    }
}

fun main() {
    val solution = Solution()
    solution.run()
}