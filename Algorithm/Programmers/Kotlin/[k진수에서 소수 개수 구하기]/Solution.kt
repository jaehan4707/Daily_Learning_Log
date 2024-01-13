import kotlin.math.*

class Solution {
    fun solution(n: Int, k: Int): Int {
        return converteDivideK(n, k)
    }

    fun converteDivideK(n: Int, k: Int): Int {
        var num = n
        var sum = 0
        var result = ""
        while (num > 0) {
            val divide = num % k
            result += divide.toString()
            num /= k
        }
        val numbers = result.reversed().split("0")
        for (number in numbers) {
            if (isPrime(number)) {
                sum++
            }
        }
        return sum
    }

    fun isPrime(n: String): Boolean {
        if (n.toLongOrNull() == null || n.toLong() == 1L)
            return false
        val num = n.toLong()
        for (i in 2..sqrt(num.toDouble()).toInt()) {
            if (num % i == 0L) {
                return false
            }
        }
        return true
    }
}