import java.util.*;

class Solution {
    public int solution(int[] citations) {
        Arrays.sort(citations);
        int answer = search(citations);
        return answer;
    }

    public int search(int [] citations){
        int answer = 0;
        for(int i=0; i<citations.length; i++){
            int highCount = citations.length-i;
            answer = Math.max(answer,Math.min(citations[i],highCount));
        }
        return answer;
    }
}