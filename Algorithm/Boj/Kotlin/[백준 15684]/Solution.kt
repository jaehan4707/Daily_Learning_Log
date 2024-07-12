import kotlin.math.*

class Solution {

    private val br = System.`in`.bufferedReader()
    private var n = 0
    private var m = 0
    private var h = 0
    private var answer = 4
    private lateinit var visited: Array<BooleanArray>


    fun run() {
        input()
        if (m != 0) { // 가로선이 있을 경우 탐색해봐야함
            solution()
            if (answer > 3) { // 만약 가로선의 개수가 갱신되지 않았다면 불가능함
                answer = -1
            }
        } else { // 가로선이 없을 경우 답은 0임
            answer = 0
        }
        print(answer)
    }

    private fun input() {
        br.readLine().split(" ").map { it.toInt() }.apply {
            n = this[0]
            m = this[1]
            h = this[2]
        }
        visited = Array(h + 1) { BooleanArray(n + 1) }
        repeat(m) {
            val (a, b) = br.readLine().split(" ").map { it.toInt() }
            visited[a][b] = true // 가로 a칸 b -> b+1은 방문 처리
        }
    }

    private fun solution() {
        //가로선을 하나 추가했을 때,
        //가로선을 두개 추가했을 때,
        //가로선을 세개 추가했을 떄,
        //그외에는 XX
        for (i in 0..3) {
            dfs(i, 0)
            if (answer != 4) { // 갱신되었을때
                return
            }
        }
    }

    private fun dfs(limit: Int, number: Int) {
        if (number == limit) { //여기서 각 세로선이 i로 가는지 확인
            for (i in 1..n) {
                if (search(i).not()) { // 세로선 i가 안갈 경우
                    return
                }
            }
            answer = min(answer, limit) // 모든 세로선이 정상적이라면 답을 갱신함.
            return
        }

        for (i in 1..h) {
            for (j in 1..<n) {
                if (visited[i][j] || visited[i][j - 1] || visited[i][j + 1]) { // i,j에 대해 선이 하나라도 있으면 선을 설치하지 않음.
                    continue
                }
                visited[i][j] = true
                dfs(limit, number + 1)
                visited[i][j] = false
            }
        }
    }

    private fun search(idx: Int): Boolean {
        var now = idx
        for (i in 1..h) {
            //선이 있다면
            if (visited[i][now - 1]) { // now-1 ~ now <-
                now -= 1 //왼쪽으로 꺾기
                continue
            }
            if (visited[i][now]) {
                now += 1 //오른쪽으로 꺾ㄱ
                continue
            }
        }
        return now == idx //같으면 true
    }
}

fun main() {
    Solution().run()
}
