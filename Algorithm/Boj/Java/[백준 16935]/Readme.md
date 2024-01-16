## 문제 풀이

총 6가지의 연산이 있는데,

1번과 2번은 각각 상하반전과 좌우 반전이다.
![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbvswQJ%2FbtsDAGIXjeh%2FUNM6xq1FWd7thq3AbegRa0%2Fimg.png)

1번과 2번은 각각 가운데 행을 기준으로 끝과 끝을 바꿔주면 된다.

3번과 4번은 반시계와 시계방향으로 90도 회전하는 것이다.
![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FdxKuZ0%2FbtsDByX0dvu%2FcyoWnOAbdjKeYki7ulQMzk%2Fimg.png)

여기서 중요한 점은 시계방향으로 한번 돌릴 때마다 N과 M이 바뀐다는 점이다.

따라서 해당 연산을 한 후 N과 M을 바꿔줘야 다음 연산에서 index 오류가 발생하지 않는다.



오른쪽 90도 회전인 경우

첫 번째 행을 첫번째 열로, 두 번째 행을 두번째 열로 ,,, n번째 행을 n번쨰 열로 옮겨주면 된다.
왼쪽 90도 회전인 경우

마지막 행을 첫번째 열로, 마지막 -1 번째 행을 두번째 열로 ,,, 0번째 행을 n번째 열로 옮겨주면 된다.

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FdFVLij%2FbtsDAscUNpR%2FxuCs6NYAe2tdqCxFOzIr40%2Fimg.png)
5번과 6번의 경우 배열을 4개로 분할해서 분할한 배열을 옮겨준다.

그림과 같이 배열을 통째로 구역에 옮겨주면 된다.