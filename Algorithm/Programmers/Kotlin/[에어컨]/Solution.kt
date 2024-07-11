//실내 온도를 유지하기 위한 최소 소비 전력
//언제 에어컨을 트냐 이건가?
//승객이 탑습중일때 실내 온도를 유지하려고 에어컨을 켜야 함.
//희망온도를 어떻게 지정하느냐에 따라 또 다르네
//시작온도는 temperature
//에어컨을 켤때마다 희망 온도를 조절할 수 있음.
//a와 b에 따라 에어컨의 희망온도를 조절해야 함.
import kotlin.math.min
class Solution {

    private lateinit var dp : Array<IntArray>

    fun solution(temperature: Int, t1: Int, t2: Int, a: Int, b: Int, onboard: IntArray): Int {
        var answer: Int = 100*1000
        var temp = 1
        dp = Array(onboard.size+1) { IntArray(51){100*1000}} //a분에 b 온도를 맞추는데 필요한 코스트
        dp[0][temperature+10] = 0
        if(temperature+10>t2+10){
            temp=-1
        }
        for(i in 1 until onboard.size){
            //i분에 승객이 있니 없니에 따라 달라질듯
            for(j in 0 .. 50){ // 0~51도까지 해보자.
                // i분에 j 온도를 맞추기 위해선 어떻게 해야할까요?
                if((onboard[i]==1 && j>=t1+10 && j<=t2+10) || onboard[i]==0){
                    if(0<=j+temp && j+temp<=50){
                        dp[i][j] = min(dp[i][j],dp[i-1][j+temp])
                    }
                    if(j==temperature+10){
                        dp[i][j] = min(dp[i][j],dp[i-1][j])
                    }
                    if(0<=j-temp && j-temp<=50){
                        dp[i][j] = min(dp[i][j],dp[i-1][j-temp]+a)
                    }
                    if(t1+10<=j && j<=t2+10){
                        dp[i][j] = min(dp[i][j],dp[i-1][j]+b)
                    }
                }
            }
        }
        answer = dp[onboard.size-1][0]
        dp[onboard.size-1].forEach{
            answer = min(answer,it)
        }
        return answer
    }

}