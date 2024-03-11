## 문제 풀이

접근까지는 쉽게 했는데 IDE 적응과 자료형에 대한 계산이 정말 귀찮았던 문제이다.

실제로 로직은 똑같은데 자료형 처리 순서에 따라서 계산 결과가 달라져서 틀렸던 경험이 있다.

우선 두 원 사이의 영역에 대해서 접점을 구하는 공식은 수학 시간에 배워서 알 수 있었다.

두 원의 반지름이 각각 4와 5일 경우

각 원의 방정식은 다음과 같다.

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcDb0wt%2FbtsFFMIvkab%2FMJYL6o12OR5nvqKl77HAgk%2Fimg.png)|![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F1O83G%2FbtsFI7dAsnt%2FsXaUhgTfKJTUD1hCd31hq0%2Fimg.png)
---|---|

두 원 사이의 공통영역은 당연히 작은 원 위에 있고, 큰 원 아래에  위치해야 한다.

그림으로 표현하면 아래와 같다.

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FoyEul%2FbtsFGR3BRTi%2FDvEpzcDKkz08K6Cgj7FH11%2Fimg.png)

우리는 위 영역중에서 x와 y 좌표가 정수인 것을 찾아야 한다.

2가지의 접근 방법이 있지만 나는 x좌표를 r2까지 이동시키면서 들어갈 수 있는 y좌표의 후보를 찾아줬다.

두 원에 대해서 y좌표 값에 후보는 아래와 같다.

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FkUva3%2FbtsFGNfEkNV%2FtZtHkVAKGBtk7YknDDun7K%2Fimg.png)|![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcNIDg1%2FbtsFF6Uie2X%2F7jd3xCmgyJVjQJWAYDGmK0%2Fimg.png)
---|---|

즉 우리는 왼쪽의 y좌표에서 오른쪽 y좌표의 차이를 정수로 바꾸면 될 것이다.

주의해야 할점은 2와 3의 경우 차이는 1이지만, 경계가 포함되므로 실제 값에서 +1을 해줘야 한다.

여기서 주의해야 할 점은 x좌표가 0일 때의 경우 x좌표가 r1, r2일 때의 영역이 중복되어서 계산되기 때문에

중복을 제거하기 위해서 x좌표는 1에서부터 시작한다는 점이다.

만약 0에서부터 시작할 경우 각각 중복을 제거해야 하므로 그에 맞는 코드가 추가하면 된다.

추가로 다 풀고 구글에서 최적화된 코드를 봤는데 괜찮아서 최적화된 코드로 수정했다.

최적화된 부분은 다음과 같다.

실제로 작은 원 영역의 y좌표는 해당 좌표보다 항상 위에 있어야 하기 때문에 정수 변환 과정에서 올림 처리를 해준다.

반대로 큰 원의 영역의 y좌표는 해당 좌표보다 항상 아래에 있어야 하기 때문에 정수 변환 과정에서 내림 처리를 해준다.

이럴 경우 실제 점 간의 차이를 계산하기 굉장히 용이해진다.

전체 코드를 보면 알 수 있듯이 `r1 * r1 * 1.0`을 한 결과와 `r1*1.0*r1`을 한 실행결과가 달랐고,

계산의 영역에서 값이 소실되는 포인트를 알 지 못해서 많이 돌아간 문제였다.