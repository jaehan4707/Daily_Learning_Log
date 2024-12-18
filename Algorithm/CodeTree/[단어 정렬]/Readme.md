# 문제 풀이

입력 받은 문자열들을 주어진 기준에 따라 정렬하는 것이다.   
나는 우선순위큐를 사용해서 정렬했는데, 해설을 보니 따로 Comparator 구현체를 만들어서 한 것 같다.   

나는 길이가 같을 경우, 문자열의 우선순위를 글자 하나하나 비교해가면서 했다.   
```java
for(int i=0; i<minLen; i++){
    if(w1.str.charAt(i)!=w2.str.charAt(i)){
        return Character.compare(w1.str.charAt(i),w2.str.charAt(i));
    }
}
```

하지만 그것보다  w1.str.compareTo(w2.str) 라는 간단한 방식이 있어서 놀랐다.