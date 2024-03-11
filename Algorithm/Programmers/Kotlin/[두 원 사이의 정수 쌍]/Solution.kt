import kotlin.math.*

class Solution {
    fun solution(r1: Int, r2: Int): Long {
        var answer: Long = 0
        var minCircle = r1 * 1.0 * r1
        var maxCircle = r2 * 1.0 * r2
        for (i in 1..r2) { //x를 이동시킴.
            val nowX = i * 1.0 * i
            val minY = ceil(sqrt(minCircle - nowX)).toLong()
            val maxY = floor(sqrt(maxCircle - nowX)).toLong()
            answer += (maxY - minY + 1)
        }
        return answer * 4
    }
}