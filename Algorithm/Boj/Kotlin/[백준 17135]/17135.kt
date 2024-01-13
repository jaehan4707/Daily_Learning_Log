import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.math.abs
import kotlin.math.max
class `17135` {
    var br = BufferedReader(InputStreamReader(System.`in`))
    var row = 0
    var col = 0
    var dis = 0
    var answer = 0
    lateinit var graph : Array<Array<Int>>
    lateinit var origin : Array<Array<Int>>
    init{
        input()
        select()
        print(answer)
    }
    fun input(){
        val token = br.readLine().split(" ")
        row = token[0].toInt() // N
        col = token[1].toInt() // M
        dis = token[2].toInt() // D
        graph = Array(row) { Array(col, { 0 }) }  //원래배열
        origin = Array(row) { Array(col, { 0 }) } //복사할 배열

        for(i in 0 until  row){
            val line = br.readLine().split(" ")
            for(j in 0 until  col){
                graph[i][j] = line[j].toInt()
                origin[i][j] = graph[i][j]
            }
        }
    }
    fun loadOrigin(){ //한번 실행시킨 배열을 원래 배열로 다시 바꾸는 작업
        for(i in 0 until row){
            for(j in 0 until col){
                graph[i][j]=origin[i][j]
            }
        }
    }
    fun select(){ //궁수의 위치를 선택하는 작업.
        for(i in 0 until col){
            for( j in i+1 until col){
                for(k in j+1 until  col){
                    answer = max(answer,attack(i,j,k))
                    loadOrigin()
                }
            }
        }
    }
    fun attack(one : Int,two:Int,three : Int) : Int{ //궁수의 위치는 (N,one) , (N,two), (N,three)
        var arc : MutableList<Int> = mutableListOf()
        var result=0
        arc.apply { //궁수의 위치를 넣어줌.
            add(one)
            add(two)
            add(three)
        }
        var loc = row //궁수가 초기 위치한 행은 맨 밑임.
        while(loc>=1) { //종료조건 : 적이 한칸 내리는 대신 궁수를 한칸씩 올려서 끝까지 함.
            val target : MutableList<Triple<Int,Int,Int>> = mutableListOf()
            for (peo in arc) {
                var enemy : MutableList<Triple<Int,Int,Int>> = mutableListOf()
                for (i in 0 until loc) {
                    for (j in 0 until col) {
                        if (graph[i][j] != 1) //적이 아닐 경우
                            continue
                        val enemyDis = dist(Pair(loc,peo),Pair(i,j))
                        if(enemyDis>dis) //거리가 크다면
                            continue
                        enemy.add(Triple(i,j,enemyDis)) //배열에 적의 위치와 거리를 같이 넣음.
                    }
                }
                if(enemy.isEmpty()) continue //적이 비어있다면 궁수는 공격할 적이 없다는 뜻.
                enemy.sortWith(compareBy({ it.third }, { it.second })) //적을 거리순으로 오름차순후, 같은 거리라면 열로 오름차순함.
                target.add(enemy.first()) //가장 가깝고 왼쪽에 있는 적을 공격
            }
            for(ele in target){  //여기서는 중복된 적을 공격할수도 있기에, 아래와 같이 검사함.
                if(graph[ele.first][ele.second]==1) { //적일 경우
                    graph[ele.first][ele.second]=0
                    result++
                }
            }
            loc-- //궁수를 한칸 올림.
        }
        return result
    }
    fun dist(me : Pair<Int,Int>, side : Pair<Int,Int>) : Int{ //거리를 구하는 함수
        return abs(me.first-side.first) + abs(me.second-side.second)
    }
}
fun main(){
    var solution = `17135`()
}