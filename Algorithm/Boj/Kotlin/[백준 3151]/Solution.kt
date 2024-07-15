class Solution {

    private var n = 0
    private lateinit var ary: IntArray
    private var answer = 0L

    fun run() {
        input()
        solution()
        print(answer)
    }

    private fun input() = with(System.`in`.bufferedReader()) {
        n = readLine().toInt()
        ary = readLine().split(" ").map { it.toInt() }.toIntArray().sortedArray()
    }

    private fun solution() {
        for (i in 0..<ary.size - 1) {
            for (j in i + 1..<ary.size) { //i,j를 고른다
                val lowerBound = lowerBound(i, j, ary[i] + ary[j])
                val upperBound = upperBound(i, j, ary[i] + ary[j])
                answer += (upperBound - lowerBound)
            }
        }
    }

    private fun upperBound(s: Int, e: Int, sum: Int): Long { //s와 e 2곳에 대해 합을 찾을 수 가 있는가?
        var start = e + 1
        var end = ary.size - 1
        while (start <= end) {
            val mid = (start + end) / 2
            if (ary[mid] + sum <= 0) {
                start = mid + 1
            } else {
                end = mid - 1
            }
        }
        return end.toLong()
    }

    private fun lowerBound(s: Int, e: Int, sum: Int): Long { //s와 e 2곳에 대해 합을 찾을 수 가 있는가?
        var start = e + 1
        var end = ary.size - 1
        while (start <= end) {
            val mid = (start + end) / 2
            if (ary[mid] + sum < 0) {
                start = mid + 1
            } else {
                end = mid - 1
            }
        }
        return end.toLong()
    }


}

fun main() {
    Solution().run()
}
