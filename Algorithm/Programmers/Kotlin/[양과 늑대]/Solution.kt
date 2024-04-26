import kotlin.math.*
class Solution {
    private var n = 0
    private lateinit var graph : Array<ArrayList<Int>>
    private var answer = 0
    private var wolf = 0
    private var sheep = 0
    fun solution(info: IntArray, edges: Array<IntArray>): Int {
        n = info.size
        makeGraph(edges)
        solve(info,0,0,0,arrayListOf<Int>())
        return answer
    }
    private fun makeGraph(edges:Array<IntArray>){
        graph = Array(n){arrayListOf()}
        edges.forEach{ edge->
            graph[edge[0]].add(edge[1])
        }
    }
    private fun solve(info: IntArray,cur : Int,sheep:Int,wolf:Int, next:ArrayList<Int>){
        var curSheep = sheep
        var curWolf = wolf
        if(info[cur] == 0){
            curSheep+=1
        } else {
            curWolf+=1
        }
        if(curSheep<=curWolf){
            return
        }
        answer = max(answer,curSheep)
        val nextVertex = arrayListOf<Int>()
        nextVertex.addAll(next)
        for(i in 0 until graph[cur].size){
            nextVertex.add(graph[cur][i])
        }
        val size = nextVertex.size
        for(i in 0 until size){
            val vertex = nextVertex.removeFirst()
            solve(info,vertex,curSheep,curWolf,nextVertex)
            nextVertex.add(vertex)
        }
    }
}