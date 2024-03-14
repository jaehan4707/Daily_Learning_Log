package com.ssafy.ui6

import java.util.PriorityQueue

class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private var m = 0
    private var oil = 0
    private lateinit var graph: Array<IntArray>
    private lateinit var start: Point
    private var guest = mutableListOf<Pair<Point, Point>>()
    private val dx = arrayOf(1, -1, 0, 0)
    private val dy = arrayOf(0, 0, 1, -1)

    init {
        run()
    }

    private fun run() {
        input()
        while (oil != 0 && guest.isNotEmpty()) {
            val find = findGuest() //손님 찾기
            if (find == null) {
                oil = -1
                break
            }
            val dist = moveGuest(find.first, find.second) //손님 데려다주기
            oil -= find.third
            if (dist <= oil) { //갈 수 있는 경우
                oil = oil - dist + 2 * dist
                start = find.second //출발점 옮기기
                guest.remove(Pair(find.first, find.second))
            } else {
                oil = -1
                break
            }
            //데려다 줄 수 있는지 파악하고 데려다 줘야함.
        }
        print(oil)
    }


    private fun input() {
        br.readLine().split(" ").map { it.toInt() }.apply {
            n = this[0]
            m = this[1]
            oil = this[2]
        }

        graph = Array(n) {
            br.readLine().split(" ").map { it.toInt() }.toIntArray()
        }
        br.readLine().split(" ").map { it.toInt() }.apply {
            start = Point(this[0] - 1, this[1] - 1)
        }
        repeat(m) {
            val line = br.readLine().split(" ").map { it.toInt() }
            guest.add(Pair(Point(line[0] - 1, line[1] - 1), Point(line[2] - 1, line[3] - 1)))
        }
    }

    private fun moveGuest(from: Point, to: Point): Int {
        val pq = PriorityQueue<Pair<Int, Point>>(compareBy { it.first })
        val visit = Array(n) { BooleanArray(n) { false } }
        pq.add(Pair(0, from))
        while (!pq.isEmpty()) {
            val now = pq.poll()
            if (visit[now.second.x][now.second.y]) {
                continue
            }
            visit[now.second.x][now.second.y] = true
            for (d in 0 until 4) {
                val mx = now.second.x + dx[d]
                val my = now.second.y + dy[d]
                if (!checkRange(mx, my) || graph[mx][my] == 1 || visit[mx][my]) {
                    continue
                }
                if (mx == to.x && my == to.y) {
                    return now.first + 1
                }
                pq.add(Pair(now.first + 1, Point(mx, my)))
            }
        }
        return Int.MAX_VALUE
    }


    //현재 지점에서 가장 가까운 승객을 고른다. //중요하
    private fun findGuest(): Triple<Point, Point, Int>? {
        val visit = Array(n) { BooleanArray(n) { false } }
        var result: Triple<Point, Point, Int>
        val pq = PriorityQueue<Pair<Int, Point>>(
            compareBy(
                { it.first },
                { it.second.x },
                { it.second.y }
            )
        )
        pq.add(Pair(0, start))
        while (!pq.isEmpty()) {
            val now = pq.poll()!!
            if (visit[now.second.x][now.second.y]) {
                continue
            }
            visit[now.second.x][now.second.y] = true
            val temp = guest.find { it.first == now.second }
            if (temp != null) {
                result = Triple(temp.first, temp.second, now.first)
                return result
            }
            for (d in 0 until 4) {
                val mx = now.second.x + dx[d]
                val my = now.second.y + dy[d]
                if (!checkRange(mx, my) || graph[mx][my] == 1 || visit[mx][my]) {
                    continue
                }
                pq.add(Pair(now.first + 1, Point(mx, my)))
            }
        }
        return null
    }

    private fun checkRange(x: Int, y: Int) = x in 0 until n && y in 0 until n

    data class Point(val x: Int, val y: Int)

}

fun main() {
    val solution = Solution()
}