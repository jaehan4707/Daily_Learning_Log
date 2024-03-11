## 문제 풀이

### 초기 풀이

초기 풀이에는 각 인덱스별로 누적합을 구해서

n^2으로 특정 인덱스를 기준 삼아 처음부터 인덱스까지의 누적합의 차이를 이용해서 구간의 합을 계산하려고 했다.

```
class Solution {

    fun solution(sequence: IntArray, k: Int): IntArray {
        var answer = IntArray(2)
        println(sequence.size)
        val sum = IntArray(sequence.size+1)
        var startIdx = Int.MAX_VALUE
        for(i in 1 .. sequence.size){
            sum[i] =  sum[i-1] + sequence[i-1]
            if(sum[i]==k){
                startIdx = Math.min(startIdx,i)
            }
        }
        answer[0] = 0
        answer[1] = startIdx-1
        for(i in 1 .. sequence.size){
            for(j in 1 .. i){
                val temp = sum[i] - sum[j] + sequence[j-1]
                if(temp==k){
                    val length = answer[1]-answer[0]
                    if(i-j < length){
                        answer[0] = j-1
                        answer[1] = i-1
                    }
                    else if(i-j == length && j-1<answer[0]){
                        answer[0]=j-1
                        answer[1]=i-1
                    } 
                }
            }
        }
        return answer
    }
}
```

-   누적합을 구해서 처음으로 k값을 확보한 경우 초기 구간을 지정한다.
-   1부터 끝까지 인덱스를 기준으로 반복문을 실행한다.
-   각 반복문에서는  j~ i 까지의 구간합을 temp에 저장한다.
-   구간합을 바탕으로 k와 같다면 문제 조건에 맞게 길이를 비교하고, 시작 인덱스를 비교해서 구간을 업데이트한다.

![](https://blog.kakaocdn.net/dn/cqZ3xn/btsFJngeLMT/mIWQknmuQLgfPkZmOWF4m0/img.png)

결과는 시간초과였다.

아마 n^2이라는 알고리즘이 특정 테스트케이스에선 돌아가지 않는다고 판단이 들었다.

시간을 어떻게 줄일 수 있을까에 대해서 고민하다가 1차원 배열에 대해서 구간의 합은 투 포인터로 충분히 해결할 수 있다는 생각이 들었고,

투포인터로 알고리즘을 변경했다.

#### 수정된 풀이

[투 포인터 알고리즘(Two Pointer)](https://jja2han.tistory.com/284)


> 투포인터를 간략하게 말하면 left와 right라는 두 개의 포인터를 이동시키면서 조건에 맞는 구간합을 탐색하는 알고리즘이다.

구간합을 찾기 위해서 right 포인터를 증가시켜서 배열의 탐색범위를 넓혀가다가, 구간합이 목표보다 커지면 배열의 범위를 줄일 수 있게

left 포인터를 한 칸 증가시켜서 구간합을 조정한다.

그러다 구간합이 목표와 일치하다면 구간 사이의 길이를 비교해서 구간을 업데이트하고 left 포인터를 증가시키고 탐색을 계속한다.

탐색이 중단되는 조건은 right 포인터가 탐색하려는 배열의 크기를 넘어서는 경우이다.