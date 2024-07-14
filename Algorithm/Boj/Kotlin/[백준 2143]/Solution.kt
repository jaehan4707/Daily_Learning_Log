class Solution {

    private var t = 0
    private var n = 0
    private var m = 0
    private lateinit var a: IntArray
    private lateinit var b: IntArray
    private var answer = 0L

    fun run() {
        input()
        solution()
        print(answer)
    }

    private fun input() = with(System.`in`.bufferedReader()) {
        t = readLine().toInt()
        n = readLine().toInt()
        a = readLine().split(" ").map { it.toInt() }.toIntArray()
        m = readLine().toInt()
        b = readLine().split(" ").map { it.toInt() }.toIntArray()
    }

    private fun solution() { //a와 b의 원소로 T를 만들 수 있는가
        val aSum = makeCombinationSum(a)
        val bSum = makeCombinationSum(b)
        binarySearch(aSum, bSum)
    }

    private fun makeCombinationSum(ary: IntArray): HashMap<Int, Long> {
        val map = hashMapOf<Int, Long>()
        for (i in ary.indices) {
            var sum = ary[i]
            map[sum] = map.getOrDefault(sum, 0) + 1
            for (j in i + 1..<ary.size) {
                sum += ary[j]
                map[sum] = map.getOrDefault(sum, 0) + 1
            }
        }
        return map
    }

    private fun binarySearch(aSum: HashMap<Int, Long>, bSum: HashMap<Int, Long>) {
        aSum.keys.forEach { key ->
            bSum[t - key]?.let {
                answer += aSum[key]?.times(it) ?: 0
            }
        }
    }
}

fun main() {
    Solution().run()
}
