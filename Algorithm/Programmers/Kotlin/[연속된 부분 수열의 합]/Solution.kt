class Solution {

    fun solution(sequence: IntArray, k: Int): IntArray {
        var answer = IntArray(2)
        var left = 0
        var right = 0
        var sum = 0
        answer[0] = 0
        answer[1] = 1000000
        while(left<=right){
            if(sum == k){
                if(answer[1]-answer[0] > right- left-1){
                    answer[1] = right-1
                    answer[0] = left
                } else if(answer[0] > left){
                    answer[1] = right-1
                    answer[0] = left
                }
                sum -= sequence[left]
                left+=1
            } else if(sum > k){
                sum -= sequence[left]
                left+=1
            } else if(sum<k){
                if(right>=sequence.size){
                    break
                }
                sum += sequence[right]
                right+=1
            }
        }
        return answer
    }
}