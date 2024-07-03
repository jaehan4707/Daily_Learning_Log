class Solution {

    private var n = 0
    private var m = 0
    data class Point(val x : Int, val y:Int)
    data class Skill(val type : Int, val p1 : Point, val p2 : Point, val degree: Int)
    private lateinit var skills : Array<Skill>
    private lateinit var sum : Array<IntArray>

    fun solution(board: Array<IntArray>, skill: Array<IntArray>): Int {
        n = board.size
        m = board[0].size
        sum = Array(n){ IntArray(m) {0} }
        initData(skill)
        run(board)
        return calculate(board)
    }

    private fun initData(skill : Array<IntArray>){
        var idx = 0
        skills = Array(skill.size){Skill(0,Point(0,0),Point(0,0),0)}
        skill.forEach{
            skills[idx++] = Skill(it[0], Point(it[1],it[2]),Point(it[3],it[4]),it[5])
        }
    }
    private fun run(board : Array<IntArray>){
        skills.forEach{ skill->
            when(skill.type){
                1 -> doing(skill.p1,skill.p2,skill.degree * -1)
                else -> doing(skill.p1,skill.p2,skill.degree)
            }
        }
    }

    private fun doing(p1 : Point, p2 : Point, degree : Int){
        sum[p1.x][p1.y] += degree
        if(p2.y+1<m){ // 열 끝점이 아닐 경우
            sum[p1.x][p2.y+1] += degree * -1
        }
        if(p2.x+1<n){ // 행 끝점이 아닐 경우
            sum[p2.x+1][p1.y] += degree * -1
        }
        if(p2.x+1 < n && p2.y+1 < m){
            sum[p2.x+1][p2.y+1] += degree
        }
    }

    private fun calculate(board : Array<IntArray>) : Int {
        var answer = 0
        for(i in 0 until n){
            for(j in 0 until m-1){
                sum[i][j+1] += sum[i][j]
            }
        }
        for(i in 0 until m){
            for(j in 0 until n-1){
                sum[j+1][i] += sum[j][i]
            }
        }

        for(i in 0 until n){
            for(j in 0 until m){
                board[i][j]+=sum[i][j]
                if(board[i][j]>=1){
                    answer+=1
                }
            }
        }
        return answer
    }
}