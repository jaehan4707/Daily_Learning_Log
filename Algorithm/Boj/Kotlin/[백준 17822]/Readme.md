## 문제 풀이

시물레이션을 연습하고 있는데, 정말 복잡한 것 같다.

문제가 정말 길고, 생각해야 될 케이스가 너무 많아서 굉장히 오래 걸렸다.

우선 문제의 중요 포인트는 다음과 같다.

#### 원판 회전

원판 회전은 방향에 따라 달라진다.

행렬을 시계방향으로 옮겨주거나, 반시계방향으로 옮겨주는 것과 동일하다.

```
private fun rotate(step: Int) {
    var temp = 0.0
    for (t in 0 until k) {
        if (d == 0) { //시계
            temp = circle[step][m]
            for (j in m downTo 1) {
                if (j == 1) {
                    circle[step][j] = temp
                } else {
                    circle[step][j] = circle[step][j - 1]
                }
            }
        } else { //반시계
            temp = circle[step][1]
            for (j in 1..m) {
                if (j == m) {
                    circle[step][j] = temp
                } else {
                    circle[step][j] = circle[step][j + 1]
                }
            }
        }
    }
}
```

시계방향인지, 반시계방향인지에 따라서 마지막 도달 지점을 기록해 두고, 중복되는 끝점에 값을 넣어준다.

여기서 중요한 점은 x의 배수에 해당하는 원판만 회전해야하는것이고, 그에 대한 처리는 파라미터로 했다.

1층~N층까지 유효한 원판을 회전했다면 다음은 인접한 수에 대한 처리이다.

#### 인접한 수 처리

```
private fun findNearNumber(): Boolean {
    set.clear()
    for (step in 1..n) {
        for (j in 1..m) {
            val temp = circle[step][j]
            if (temp == 0.0 || set.contains(Pair(step, j)))
                continue
            var right = j + 1
            var left = j - 1
            var up = step + 1
            var down = step - 1
            if (j == 1) {
                left = m
                right = 2
            } else if (j == m) {
                left = m - 1
                right = 1
            }
            if (step == 1) {
                up = step + 1
                down = 0
            } else if (step == n) {
                up = 0
                down = step - 1
            }

            if (isSame(step, temp, left) || isSame(step, temp, right)
                || isSame(up, temp, j) || isSame(down, temp, j)
            ) {
                set.add(Pair(step, j))
            }
        }
    }
    set.forEach { point ->
        circle[point.first][point.second] = 0.0
    }
    return set.isNotEmpty()
}
```

인접한 수도 행렬을 회전했던 것처럼 끝에서 따로 처리해줘야 한다.

여기서 주의해야 할 점은 인접한 점은 기록을 했다가 한꺼번에 처리해야 한다는 점이다.

하나의 지점에서 인접 여부를 따지고 0으로 초기화한다면 그 다음 검사에서 인접한 수지만, 0으로 초기화한 덕분에 

오류가 나는 경우가 생긴다.

따라서 한꺼번에 모았다가 일괄적으로 처리해준다.

그리고 set에 하나라도 원소가 들어있다면 인접한 수가 있다는 뜻이다.

```
private fun searchCircle() {
    sum = 0.0
    number = 0
    for (i in 1..n) {
        for (j in 1..m) {
            if (circle[i][j] != 0.0) {
                sum += circle[i][j]
                number += 1
            }
        }
    }
}
```

인접한 숫자가 없는 경우에는 원판의 숫자값이 0이 아닌 경우에 sum과 number를 각각 계산해 준다.

이렇게 계산한 값을 통해서 평균을 계산할 수 있다.

```
if (!findNearNumber()) { //몾찻았을 경우
    searchCircle()
    val avg = (sum / number.toDouble())
    for (i in 1..n) {
        for (j in 1..m) {
            if (circle[i][j] == 0.0)
                continue
            if (circle[i][j] > avg) {
                circle[i][j] -= 1.0
            } else if (circle[i][j] < avg) {
                circle[i][j] += 1.0
            }
        }
    }
}
```

계산한 평균을 통해서 크면 -1, 작으면 +1을 해준다.

여기서 주의해야 할 점은 평균값은 double 값이고, int 형으로 비교하면 오류가 생긴다.

예를 들어 평균은 3.6인데 원판의 숫자는 3인경우 해당 숫자는 평균보다 작지만 int 형으로 계산할 경우 같다고 처리되는 문제가 생긴다.

이렇게 인접한 수를 처리하고, 모든 회전을 마무리하면 원판의 숫자가 0이 아닌 모든 합을 계산하면 된다.