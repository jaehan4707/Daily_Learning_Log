class Solution {

    private val br = System.`in`.bufferedReader()
    private val sb = StringBuilder()
    private var n = 0
    private lateinit var inOrder: IntArray
    private lateinit var inOrderIdx: IntArray
    private lateinit var postOrder: IntArray

    fun run() {
        input()
        solution(0, n - 1, 0, n - 1)
        print(sb.toString())
    }

    private fun input() {
        n = br.readLine().toInt()
        var line = br.readLine().split(" ").map { it.toInt() }
        inOrderIdx = IntArray(line.size + 1)
        inOrder = IntArray(line.size + 1)
        for (i in line.indices) {
            inOrder[i] = line[i]
            inOrderIdx[inOrder[i]] = i
        }
        postOrder = br.readLine().split(" ").map { it.toInt() }.toIntArray()
    }

    private fun solution(inLeft: Int, inRight: Int, postLeft: Int, postRight: Int) {
        if (inLeft > inRight || postLeft > postRight) {
            return
        }
        val rootIdx = inOrderIdx[postOrder[postRight]]
        sb.append(inOrder[rootIdx]).append(" ")
        val diff = rootIdx - inLeft
        solution(inLeft, rootIdx - 1, postLeft, postLeft + diff - 1)
        solution(rootIdx + 1, inRight, postLeft + diff, postRight - 1)
    }
}

fun main() {
    Solution().run()
}
