import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.math.max

class `1106` {
    val br = BufferedReader(InputStreamReader(System.`in`))
    var C = 0
    var list : ArrayList<Pair<Int,Int>> = ArrayList()
    var DP : Array<Int> = Array(1000 * 100+1 , { 0 })
    init{
        input()
        solution()
    }
    fun input(){
        var line = br.readLine().split(" ")
        C = line[0].toInt()
        var N = line[1].toInt()
        for(i in 0 until N){
            line = br.readLine().split(" ")
            list.add(Pair(line[0].toInt(),line[1].toInt()))
        }
    }
    fun solution(){ //가장 ㅂ비율이 좋은 도시로 최대한 쪼개고.
        for(l in list){ //k명을 뽑는데 최소한의 비용을 결정하자.
            for(i in 0 until DP.size){ //i원을 사용해서 뽑을 수 있는 최대의 고객수를 결정하자
                if(i-l.first>=0){ //비용이 넘는다면
                    DP[i] = max(DP[i],DP[i-l.first]+l.second)
                }
            }
        }
        for(i in 0 until DP.size){
            if(DP[i]>=C){
                print(i)
                break
            }
        }
    }
}
fun main(){
    val solution = `1106`()
}