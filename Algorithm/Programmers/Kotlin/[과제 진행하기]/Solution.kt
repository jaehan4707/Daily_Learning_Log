import java.util.*
class Solution {

    private val stopTasks = arrayListOf<Task>()
    fun solution(plans: Array<Array<String>>): Array<String> {
        var answer = Array(plans.size){""}
        val sortedPlans = plans.sortedBy{calculateTime(it[1])} // 시작 시간이 빠른 순서로 정렬하기
        var cnt = 0
        var idx = 0
        var nowTime = calculateTime(sortedPlans[0][1])
        var nextTime = calculateTime(sortedPlans[1][1])
        while(idx<sortedPlans.size|| !stopTasks.isEmpty()){

            if(!stopTasks.isEmpty()){ //비어있지 않다면
                if(idx==sortedPlans.size){
                    answer[cnt++] = stopTasks.removeLast().name
                    continue
                }
                if(nowTime< nextTime){ //할 수 있다면
                    val task = stopTasks.last()
                    var restoreTime = task.length
                    stopTasks.removeLast()
                    if(restoreTime <= nextTime - nowTime){ //처리할 수 있다면
                        answer[cnt++] = task.name
                        nowTime += restoreTime
                    } else {
                        restoreTime -= nextTime-nowTime
                        task.length = restoreTime
                        nowTime = nextTime
                        stopTasks.add(task)
                    }
                    continue
                }
            }
            nowTime = calculateTime(sortedPlans[idx][1]) + sortedPlans[idx][2].toInt()
            if(idx+1 == sortedPlans.size){
                nextTime = 24*60
            } else{
                nextTime = calculateTime(sortedPlans[idx+1][1])
            }
            if(nowTime > nextTime){ //일을 하지 못할 때
                //줄여줘야 하는데 이걸 안햇네
                stopTasks.add(Task(sortedPlans[idx][0],sortedPlans[idx][1],nowTime-nextTime))
                nowTime = nextTime
            } else{ //일을 할 수 있을 때
                answer[cnt++] = sortedPlans[idx][0]
            }
            idx+=1
        }
        return answer
    }

    fun calculateTime(time : String) : Int{
        val line = time.split(":")
        val hour = line[0].toInt()
        val miniute = line[1].toInt()
        // println("$line $hour $miniute")
        return hour*60+miniute
    }
    data class Task(val name : String, val start : String, var length : Int)
}