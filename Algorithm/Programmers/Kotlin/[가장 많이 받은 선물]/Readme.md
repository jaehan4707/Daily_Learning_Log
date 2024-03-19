## 문제 풀이
​
문제가 길고 설명이 장황하지만 LV1 문제답게 문제 흐름 그대로 쭉쭉 있어나가면 된다.

우선 해당 문제에서는 선물을 주고 받은 기록이 중요하다.   
따라서 선물을 주고 받을 기록을 저장할 자료구조를 나는 2차원 배열로 설정했다.
​
**인접리스트도 상관없지만 친구수가 50명밖에 되지 않기도 했고, 인덱스로 바로 접근할 수 있다는 장점이 컸던 것 같다.**
​ 인접리스트를 한다면 해당 인덱스의 친구가 누구인지 같이 저장해야하는건 조금 번거로운 것 같다.
​
```
graph = Array(n, {Array(n){0 } })
```
​
친구를 저장하기 위한 자료구조이다. 여기서 n은 입력으로 받은 friends 배열의 크기이다.   
다음은 gifts 배열에 따라 주고 받은 선물을 기록해야 한다.   
**graph [i][j]는 i->j에게 선물을 준 횟수이다.**   
**나중 선물을 주고받은 기록을 비교하기 편하게 하기 위해서**  **인접리스트가 아닌 인접행렬을 선택한 이유기도 하다.**
​
   
graph [i][j]가 i->j라면 graph [j][i]는 j->i로 바로 비교가 가능하다.
​
```
fun connectionGifts(friends: Array<String> ,gifts:Array<String>){
    gifts.forEach{ gift->            
        val connection = gift.split(" ")            
        val giveFriendIdx = friends.indexOf(connection[0])
        val receiveFriendIdx = friends.indexOf(connection[1])
        graph[giveFriendIdx][receiveFriendIdx]++                   
    }
}
```
​
**선물의 주고받음을 기록하는 함수**이다.   

gifts는 ["muzy frodo"]와 같이 주는 사람, 받는 사람이 공백단위로 나눠져있다.
​
따라서 공백단위로 split을 한 뒤 이름에 해당하는 인덱스를 찾았다.
​
매번 인덱스를 찾는 것이 번거롭다고 생각하긴 했지만, 인접리스트가 아닌 인접행렬로 만들 때의 장점이 더 커서 매번 인덱스를 찾아줬다.
​
이제 찾은 다음 give -> receieve의 형태로 2차원 배열에 접근했고, 선물의 개수를 증가시켰다.
​
   
이제 주고받은 기록을 바탕으로 다음 달에 누가 선물을 가장 많이 받을지 예측해야 한다.
​
인접행렬을 사용했기에 반복문을 정직하게 돌았다.
​
```
for(i in 0 until n){
    for(j in i+1 until n){            
        if(graph[i][j] == graph[j][i] || (graph[i][j]==0&&graph[j][i]==0)){ //주고받은게 같거나 없는 경우
            val iScore = calculateGift(i)
            val jScore = calculateGift(j)                
            if(iScore>jScore){
                receiveGift[i]++
            } else if(iScore<jScore){
                receiveGift[j]++
            }
        } else if(graph[i][j] > graph[j][i]){                    
            receiveGift[i]++
        } else if(graph[i][j]<graph[j][i]){                    
            receiveGift[j]++
        }
    }
}
```
​
문제에서 선물지수를 파악하기 위해선 2가지 조건이 필요하다.    
하나는 두 명의 선물을 주고받은 개수가 같거나, 둘 다 보내지 않았거나    
첫 if문은 그것을 파악할 수 있다. 만약 해당 조건문에서 걸리지 않는다면 한쪽이 많이 보냈기 때문에 다음 달에 받을 선물을 증가시켜 준다.
​
   
선물 지수를 계산하기 위한 함수는 calculateGift이다.
​
```
fun calculateGift(idx: Int) : Int{                
    var score = 0    
    for(j in 0 until n){
        if(idx==j){
            continue
        }            
        score+=graph[idx][j] //준 선물 더하기
        score-=graph[j][idx] //받은 선물 빼기                        
    }   
    return score
}
```
​
선물지수를 구하기 위해선 **자신이 보낸 선물의 합과 자신이 받은 선물의 합**이 필요하다.
​
만약 여기서 인접리스트를 사용했다면 계산이 굉장히 복잡해졌을 것이다.
​
이렇게 구한 선물지수를 통해서 비교하고, 다음 달에 선물을 받을 사람에게 주거나, 없다면 주지 않으면 된다.