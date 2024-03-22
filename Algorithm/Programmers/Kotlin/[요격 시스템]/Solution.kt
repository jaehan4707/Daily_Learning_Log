class Solution {
    fun solution(targets: Array<IntArray>): Int {
        return  decideMissileNumber(targets)
    }

    fun decideMissileNumber(targets: Array<IntArray>) : Int{
        var endPoint = 0
        val sortTargets = targets.sortedBy{it[1]}
        var cnt = 0
        sortTargets.forEach{ target->
            if(target[0]>=endPoint){
                endPoint = target[1]
                cnt++
            }
        }
        return cnt
    }
}