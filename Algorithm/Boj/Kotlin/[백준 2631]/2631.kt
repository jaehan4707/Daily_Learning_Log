import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.math.max

class `2631` {

    lateinit var ary : Array<Int>
    lateinit var dp : Array<Int>
    init{
        input()
        print(ary.size-solution())
    }
    fun input(){
        val br = BufferedReader(InputStreamReader(System.`in`))
        var size = br.readLine().toInt()
        ary = Array(size, { 0 })
        dp = Array(size , { 1 })
        for(i in 0 until size){
            ary[i] = br.readLine().toInt()
        }
    }
    fun solution() : Int{
        for(i in 0 until ary.size){
            for(j in i+1 until ary.size){
                if(ary[i]<ary[j])
                    dp[j] = max(dp[j], dp[i]+1)
            }
        }
        return if(dp.isEmpty()) 0
        else dp.maxOrNull()!!
    }
}
fun main(){
    val solution = `2631`()
}