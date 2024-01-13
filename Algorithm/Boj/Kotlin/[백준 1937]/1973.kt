import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.math.max
class `1937` {
    val br =BufferedReader(InputStreamReader(System.`in`))
    var size =0
    var answer =0
    private lateinit var graph : Array<Array<Int>>
    private lateinit var accum : Array<Array<Int>>
    val x = arrayOf(1,-1,0,0)
    val y = arrayOf(0,0,1,-1)
    init{
        input()
        search()
        print(answer)
    }
    fun input(){
        size = br.readLine().toInt()
        graph = Array(size, {Array(size, {0})})
        accum = Array(size, {Array(size, {1})})
        for(i in 0 until size){
            val line = br.readLine()
            val token = line.split(" ")
            for(j in 0 until size){
                graph[i][j] = token[j].toInt()
            }
        }
    }
    fun search() : Int{
        for(i in 0 until size){
            for(j in 0 until size){
                answer = max(answer,dfs(i,j))
            }
        }
        return answer
    }
    fun dfs(row : Int, col : Int) : Int{
        val now = graph[row][col]
        if(accum[row][col]!=1) //이미 한번 거친 좌표라면 탐색을 하지 않고 넣어줌.
            return accum[row][col]
        for(dir in 0 until 4){
            val mx = row + x[dir]
            val my = col + y[dir]
            if(mx !in graph.indices || my !in graph.indices) //배열 범위를 벗어날 경우
                continue
            if(now >= graph[mx][my]) //now가 작아야함. 반.드.시
                continue
            accum[row][col] = max(accum[row][col],dfs(mx,my)+1)
        }
        return accum[row][col]
    }
}
fun main(){
    val solution = `1937`()
}