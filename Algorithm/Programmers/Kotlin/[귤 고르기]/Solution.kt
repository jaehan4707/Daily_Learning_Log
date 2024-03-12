/*
크기 별로 분류했을 때 서로 다른 종류의 수를 최소화하고싶다.
여기서 귤을 k개 골라 상자에 담는다.
고로 k개를 어떻게 담을건지가 중요한 문제
k개를 담아서 상자에는 서로 다른 종류의 수를 적게하는것이 목표
*/
import java.util.*
class Solution {

    private lateinit var fruits : IntArray
    private val q = PriorityQueue<Pair<Int,Int>>(compareBy{-it.first})

    fun solution(k: Int, tangerine: IntArray): Int {
        fruits = IntArray(10000001)
        makeSubTotal(tangerine)
        sortFruitBySum()
        var remain = k
        var answer = 0
        while(!q.isEmpty()){
            val now = q.poll()
            if(remain <= 0){
                break
            }
            remain -= now.first
            answer++
        }
        return answer
    }

    fun makeSubTotal(tangerine : IntArray){
        tangerine.forEachIndexed{ index, _ ->
            fruits[tangerine[index]]++
        }
    }
    fun sortFruitBySum(){
        fruits.forEachIndexed{ index, sum->
            if(sum!=0){
                q.add(Pair(sum,index))
            }
        }
    }
}