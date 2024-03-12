class Solution {
    private val br = System.`in`.bufferedReader()
    private var n = 0
    private var m = 0
    private var t = 0
    private var x = 0
    private var d = 0
    private var k = 0
    private lateinit var circle: Array<DoubleArray>
    private var sum = 0.0
    private var number = 0
    private val set = hashSetOf<Pair<Int, Int>>()

    init {
        run()
    }

    private fun input() {
        br.readLine().split(" ").map { it.toInt() }.apply {
            n = this[0]
            m = this[1]
            t = this[2]
        }
        circle = Array(n + 1) { DoubleArray(m + 1) { 0.0 } }
        for (i in 1..n) {
            val line = br.readLine().split(" ").map { it.toDouble() }
            for (j in 1..line.size) {
                circle[i][j] = line[j - 1]
            }
        }
    }

    private fun run() {
        input()

        repeat(t) {
            br.readLine().split(" ").map { it.toInt() }.apply {
                x = this[0]
                d = this[1]
                k = this[2]
            }
            for (i in x..n step x) {
                rotate(i)
            }
            if (!findNearNumber()) { //몾찻았을 경우
                searchCircle()
                val avg = (sum / number.toDouble())
                for (i in 1..n) {
                    for (j in 1..m) {
                        if (circle[i][j] == 0.0)
                            continue
                        if (circle[i][j] > avg) {
                            circle[i][j] -= 1.0
                        } else if (circle[i][j] < avg) {
                            circle[i][j] += 1.0
                        }
                    }
                }
            }
        }
        var answer = 0.0
        for (i in 1..n) {
            for (j in 1..m) {
                if (circle[i][j] != 0.0) {
                    answer += circle[i][j].toInt()
                }
            }
        }
        print(answer.toInt())
    }

    private fun searchCircle() {
        sum = 0.0
        number = 0
        for (i in 1..n) {
            for (j in 1..m) {
                if (circle[i][j] != 0.0) {
                    sum += circle[i][j]
                    number += 1
                }
            }
        }
    }

    private fun rotate(step: Int) {
        var temp = 0.0
        for (t in 0 until k) {
            if (d == 0) { //시계
                temp = circle[step][m]
                for (j in m downTo 1) {
                    if (j == 1) {
                        circle[step][j] = temp
                    } else {
                        circle[step][j] = circle[step][j - 1]
                    }
                }
            } else { //반시계
                temp = circle[step][1]
                for (j in 1..m) {
                    if (j == m) {
                        circle[step][j] = temp
                    } else {
                        circle[step][j] = circle[step][j + 1]
                    }
                }
            }
        }
    }

    private fun findNearNumber(): Boolean {
        set.clear()
        for (step in 1..n) {
            for (j in 1..m) {
                val temp = circle[step][j]
                if (temp == 0.0 || set.contains(Pair(step, j)))
                    continue
                var right = j + 1
                var left = j - 1
                var up = step + 1
                var down = step - 1
                if (j == 1) {
                    left = m
                    right = 2
                } else if (j == m) {
                    left = m - 1
                    right = 1
                }
                if (step == 1) {
                    up = step + 1
                    down = 0
                } else if (step == n) {
                    up = 0
                    down = step - 1
                }

                if (isSame(step, temp, left) || isSame(step, temp, right)
                    || isSame(up, temp, j) || isSame(down, temp, j)
                ) {
                    set.add(Pair(step, j))
                }
            }
        }
        set.forEach { point ->
            circle[point.first][point.second] = 0.0
        }

        return set.isNotEmpty()
    }

    private fun isSame(step: Int, value: Double, target: Int): Boolean {
        if (value == circle[step][target]) {
            set.add(Pair(step, target))
            return true
        }
        return false
    }
}

fun main() {
    val solution = Solution()
}