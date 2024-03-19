class Solution {

    private lateinit var graph: Array<Array<Int>>
    private lateinit var receiveGift: IntArray
    private var n = 0

    fun solution(friends: Array<String>, gifts: Array<String>): Int {
        n = friends.size
        receiveGift = IntArray(n)
        graph = Array(n, { Array(n) { 0 } })
        connectionGifts(friends, gifts)
        for (i in 0 until n) {
            for (j in i + 1 until n) {
                if (graph[i][j] == graph[j][i] || (graph[i][j] == 0 && graph[j][i] == 0)) { //주고받은게 같거나 없는 경우
                    val iScore = calculateGift(i)
                    val jScore = calculateGift(j)
                    if (iScore > jScore) {
                        receiveGift[i]++
                    } else if (iScore < jScore) {
                        receiveGift[j]++
                    }
                } else if (graph[i][j] > graph[j][i]) {
                    receiveGift[i]++
                } else if (graph[i][j] < graph[j][i]) {
                    receiveGift[j]++
                }
            }
        }
        val maxGift = receiveGift.maxOrNull() ?: 0
        return maxGift
    }

    fun connectionGifts(friends: Array<String>, gifts: Array<String>) {
        gifts.forEach { gift ->
            val connection = gift.split(" ")
            val giveFriendIdx = friends.indexOf(connection[0])
            val receiveFriendIdx = friends.indexOf(connection[1])
            graph[giveFriendIdx][receiveFriendIdx]++
        }
    }

    fun calculateGift(idx: Int): Int {
        var score = 0
        for (j in 0 until n) {
            if (idx == j) {
                continue
            }
            score += graph[idx][j] //준 선물 더하기
            score -= graph[j][idx] //받은 선물 빼기
        }
        return score
    }

}