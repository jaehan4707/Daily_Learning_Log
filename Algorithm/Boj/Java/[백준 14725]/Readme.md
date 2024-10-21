# 문제 풀이
트라이를 응용한 조금 새로운 문제였다.
처음에 입력을 처리하는것이 헷갈려서 A -> B -> C -> D 순서대로 넣어야하는데 실수해버렸다.   
따라서 입력에 대해 String [] keywords를 만들어서, 하나하나 넣으면서 노드를 이동시키면서 자식에 추가해줬다.

이 후 출력을 할 때는 Node를 순회하면서, key에 대해서 자식들을 출력하면 끝이었다.   
여기서 Node의 key 값들을 배열로 변환했는데, Map에 들어갈 때는 정렬이 되어있지만, String [] 로 변경하면 정렬이 되어있지 않아서,
정렬을 추가해줬다.

Map에서 key를 Array []로, value를 Array[]로 자주 바꾸는데, 사용방법을 익혀야겠다.

```java
<T> [] ary = map.keySet().toArray(new T[0]); 
// ex String [] ary = map.keySet().toArray(new String[0]);
```