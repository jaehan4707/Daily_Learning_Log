JWT 토큰

```
유저의 신원이나 권한을 결정하는 정보를 담고 있는
데이터 조각이다. JWT 토큰을 사용해서 클라이언트와 서버는 
안전하게 통신한다. 왜냐하면 JWT 토큰 인증 방식은
대칭키로 암호화하기 때문이다.
```

대칭키 암호화 방식의 문제점

대칭키 암호화 방식은 암호화 복호화에 같은 암호가 사용된다. 즉 서버,클라이언트에서 같은 암호를 사용해서 통신을 진행한다.
이는 간단하게 암호화 방식을 구현할 수 있는 장점이 있지만, 암호를 탈취당할경우 서버와 클라이언트 모두 통신에 문제가 생긴다. 이러한 탈취 관리가 대칭키 방식의 가장 큰 문제점이다.

어떻게 클라이언트가 유효한 클라이언트인지 구분할 수 있을까?

많은 방법이 있겠지만, 대중적으로 사용하는 방법은 2개의 JWT 토큰을 사용하는것이다.

이 두개의 토큰이 많이 들어봤을법한  
Access Token과 Refresh 토큰이다.

각가의 토큰은 클라이언트의 진위 여부를 판단하기 위해 사용되고, 성질이 다르다.

Access Token은 유효기간이 짧고, Refresh Token은 유효기간이 길다.
평소에 API 통신할 때는 Access Token을 사용하고, Refresh Token은 Access Token이 만료되어 갱신될 때만 사용한다.

이렇게 상대적으로 유효기간이 짧은 토큰을 API 통신할 때 사용해서,
암호키의 하이재킹에 대한 피해를 최소화한다.

![Untitled](https://velog.velcdn.com/images/chuu1019/post/8fb38aff-496e-4385-a56a-fbbb58390ba8/image.png)

## 간단한 통신 과정

1. 로그인 인증에 성공하면 클라이언트는 Refresh 토큰과 Access Token을 받는다.
2. 클라이언트는 Refresh Token과 Acess Token을 로컬에 저장한다.
3. 클라이언트는 헤더에 Access Token을 넣고 API 통신을 한다.
4. 일정 기간이 지나 Access Token이 만료되었을 경우, 사용자는 권한이 없는 상태가 된다.
5. 클라이언트로부터 기한이 지난 Access Token을 받는다면 서버는 401 에러로 응답한다.
6. 헤더에 Access Token 대신 Refresh Token을 넣어 API를 재요청한다.
7. Refresh Token으로 사용자의 권한을 확인한 서버는 응답쿼리 헤더에 새로운 Access Token을 발행후 포함시켜 응답한다.
8. 만약 Refresh Token도 만료되었다면 서버는 동일하게 401 에러코드를 보내고, 클라이언트는 재로그인해야한다.

JWT를 이용한 API 통신은 상대적으로 짧은 기한을 가지고 있는 Access Token을 이용하고,  기한이 만료될 경우 사용자 인증을 위해 Refresh Token을 사용한다고 했다.

그렇다면 Refresh Token을 탈취당한다면 어떻게 될까?

## Refresh Token의 탈취

탈취자는 Refresh Token의 유효기간만큼 다시 Access Token을 생성해 사용자인척 위장할 수 있다. 그렇게 때문에 또다른 검증 로직이 필요하고, 대안책으로 서버에서 AccessToken과 Refresh Token을 쌍으로 저장하는것이다.

1. 정상적인 사용자는 기존의 Access Token으로 접근하며, 서버측에서는 데이터베이스에 저장된 Access Token과 비교하여 검증한다.
2. 공격자는 탈취한 Refresh Token으로 새로운 Access Token을 생성하지만, 서버의 데이터베이스에 저장된 Access Token과 다름을 확인할 수 있다.
3. 만약 데이터베이스에 저장된 토큰이 아직 만료되지 않은 경우, 굳이 Access Token을 새로 생성할 이유가 없기 때문에 서버는 Refresh Token이 탈취당했다고 가정해 두 토큰을 만료시킨다
4. 정상적인 사용자는 자신의 토큰이 만료되었기 때문에 재 로그인을 시도한다. 하지만 탈취자의 토큰 역시 만료됐기 때문에 공격자는 위장할 수 없게 된다.

[https://velog.io/@chuu1019/Access-Token과-Refresh-Token이란-무엇이고-왜-필요할까](https://velog.io/@chuu1019/Access-Token%EA%B3%BC-Refresh-Token%EC%9D%B4%EB%9E%80-%EB%AC%B4%EC%97%87%EC%9D%B4%EA%B3%A0-%EC%99%9C-%ED%95%84%EC%9A%94%ED%95%A0%EA%B9%8C)

[https://velog.io/@park2348190/OAuth는-어떻게-동작하는가](https://velog.io/@park2348190/OAuth%EB%8A%94-%EC%96%B4%EB%96%BB%EA%B2%8C-%EB%8F%99%EC%9E%91%ED%95%98%EB%8A%94%EA%B0%80)

[https://velog.io/@gs0351/대칭키-vs-공개키비대칭키](https://velog.io/@gs0351/%EB%8C%80%EC%B9%AD%ED%82%A4-vs-%EA%B3%B5%EA%B0%9C%ED%82%A4%EB%B9%84%EB%8C%80%EC%B9%AD%ED%82%A4)

https://mccoy-devloper.tistory.com/59