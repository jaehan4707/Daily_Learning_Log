class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private var primeNumber = ArrayList<Int>()
    private lateinit var isPrime: BooleanArray
    private var answer = 0
    fun run() {
        input()
        makePrimeNumber()
        solve()
        print(answer)
    }

    private fun input() {
        n = br.readLine().toInt()
    }

    private fun makePrimeNumber() {
        isPrime = BooleanArray(n + 1) { true }
        isPrime[1] = false
        for (i in 2..Math.sqrt(n.toDouble()).toInt()) { // 최대범위 까지
            if (isPrime[i].not()) {
                continue
            }
            for (j in i + i..n step i) { //자기 자신으로 나눌 수 있는지
                isPrime[j] = false
            }
        }
        for (i in 1 until isPrime.size) {
            if (isPrime[i]) {
                primeNumber.add(i)
            }
        }
    }

    private fun solve() {
        var left = 0
        var right = 0
        var sum = 0
        while (left <= right) {
            if (sum == n) {
                answer++
                sum -= primeNumber[left]
                left += 1
            } else if (sum < n) {
                if (right >= primeNumber.size) {
                    break
                }
                sum += primeNumber[right]
                right += 1
            } else {
                sum -= primeNumber[left]
                left += 1
            }
        }
    }
}

fun main() {
    val solution = Solution()
    solution.run()
}