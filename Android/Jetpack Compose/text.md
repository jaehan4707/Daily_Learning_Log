# Jetpack Compose

![](https://private-user-images.githubusercontent.com/99114456/301540041-fe992a65-0276-4ecc-9ffb-0c2dcba53eb1.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MDY3OTg3NzEsIm5iZiI6MTcwNjc5ODQ3MSwicGF0aCI6Ii85OTExNDQ1Ni8zMDE1NDAwNDEtZmU5OTJhNjUtMDI3Ni00ZWNjLTlmZmItMGMyZGNiYTUzZWIxLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDAyMDElMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwMjAxVDE0NDExMVomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPWZlM2YxZjhlNmMyY2RjODRkZDlkNDM4NzZhOTdlYjljZDJjNDRjMDBmZTYxOTA0OWY4OTIyMjlmNGE3NDU3MWYmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.cKMhUlMjPSIo-icN-pWctcmSnhJB7R5fL31WByauGh4)



XML에서의 TextView의 속성을 들고있다.

속성값을 하나하나 설정하면 xml에서 설정한것과 동일한 결과를 받을 수있다.

하나하나 뜯어보자면

## text

- 말 그대로 내용물을 뜻한다.

```kotlin
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }
}
```

![](https://private-user-images.githubusercontent.com/99114456/301540144-ebf402ce-66fb-4f35-8f2d-480242f8b7ea.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MDY3OTg3NzEsIm5iZiI6MTcwNjc5ODQ3MSwicGF0aCI6Ii85OTExNDQ1Ni8zMDE1NDAxNDQtZWJmNDAyY2UtNjZmYi00ZjM1LThmMmQtNDgwMjQyZjhiN2VhLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDAyMDElMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwMjAxVDE0NDExMVomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPWM5NmM1MWZiN2ExODZhZGVkOGE2NzFjOWNhNWM0MmYyN2U4ZjdjMTIyZGU1NjNkNWYwMTc5OTMxYjFiZjRkZTMmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.SODrRyM8LKnU91KGC3fF9EoirBM5w5Q_PB6Zye5ZZHA)

Preview에서 Greeting을 호출하는데 name 인자에 Jetpack Compose를 전달했고, 그대로를 보여주는 모습이다.

## Color

- 텍스트의 색깔을 의미한다.
- 신기한것은 Color.@ 로 접근을 한다.
- Compose 프로젝트를 시작하면 Color.kt가 생성되는데 거기서 컬러를 선언하고 사용가능하다.
    - 아래파일에서 제공되는 색깔 말고 기본적으로 제공하는 색깔도 있다.

![](https://private-user-images.githubusercontent.com/99114456/301540229-2697fa83-95a7-4cc0-a195-32835e88c5cb.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MDY3OTg3NzEsIm5iZiI6MTcwNjc5ODQ3MSwicGF0aCI6Ii85OTExNDQ1Ni8zMDE1NDAyMjktMjY5N2ZhODMtOTVhNy00Y2MwLWExOTUtMzI4MzVlODhjNWNiLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDAyMDElMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwMjAxVDE0NDExMVomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTFhMjczZDNhNTQ0OGIwZjMyMTFiODc5ODc3OTliYjE4YjEyYjI0YTM4NTQyNjFmMWY2YjE3N2EyMDM0MzNlYjAmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.Jl05iOH52nTUgyUN33OYaR7p0_IC6yw7-K8NP0sOEmE)

## fontSize

- 글자의 크기를 의미한다.
- Color와 비슷하게 fontSize = 35sp가 아닌 35.sp로 접근을 한다.
- xml에서는 dp로 접근이 가능했지만, Compose에서는 불가능하다.

![](https://private-user-images.githubusercontent.com/99114456/301540272-3a373ec4-1121-42ba-a636-64bbca844d73.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MDY3OTg3NzEsIm5iZiI6MTcwNjc5ODQ3MSwicGF0aCI6Ii85OTExNDQ1Ni8zMDE1NDAyNzItM2EzNzNlYzQtMTEyMS00MmJhLWE2MzYtNjRiYmNhODQ0ZDczLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDAyMDElMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwMjAxVDE0NDExMVomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTNlZjNlZTgzYjkzYThjYjkzY2JjMDljYzMxZmY3ZmViN2M4MmI5NTcyYzQ0MGQ0Y2I5MDJiNTMzMjI0NDIzNWImWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.6UP4u_mzpDY3yd-_TSNCpBNV1_IM7AhPaMUN3pxNFFg)

## fontStyle

- 글자의 스타일을 의미한다.
- defualt는 normal이고, Italic도 제공한다.
- 접근은 FontStyle.@

### fontWeight

- 글자의 굵기를 의미한다.
- bold와 semiBold등 다양하게 지원한다.
- 접근은 FontStyle.@로 접근한다

## fontFamily

- 글자의 폰트를 의미한다.
- font를 추가하기위해선 기존에 했던것 처럼 폰트를 추가하고, Type.kt에 폰트를 정의한다.
- 접근은 FontFamily.@로 접근한다.

## letterSpacing

- 글자간의 가로 간격을 의미한다.
- 접근은 @.sp로 접근한다.

## textDecoration

- 글자를 꾸밀 수 있다.
- 취소선이나 밑줄을 적용가능하다.
- 접근ㄷ은 TextDecoration.@로 접근한다.

## textAlign

- 글자의 정렬을 의미한다.
- 접근은 TextAlign.@로 접근한다.

## overFlow

- 글자가 짤렸을때의 대처옵션을 의미한다.
    - clip : 짤린 경우 짤린걸 보여주지 않음.
    - visible: 짤린 경우 보여줌
    - Ellipsis : …으로 보여준다.


![](https://private-user-images.githubusercontent.com/99114456/301540320-ccb5e395-653e-4a6f-b992-8ce1ac8b7bf2.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MDY3OTg3NzEsIm5iZiI6MTcwNjc5ODQ3MSwicGF0aCI6Ii85OTExNDQ1Ni8zMDE1NDAzMjAtY2NiNWUzOTUtNjUzZS00YTZmLWI5OTItOGNlMWFjOGI3YmYyLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDAyMDElMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwMjAxVDE0NDExMVomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTgxNWZkZjMxNTI4MTlhMWM0MzE1MzgwZWY1YmY3NGU4ZDc0ODFiNjQ0ZjYxZTZhNTE5NGFkZjIyNzkwMGZmNjkmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.7fOP1ON4gzgxBWhm6dtGRIGD5MQdaaMLz7DED1OkarU)

# Layout 배치

![](https://private-user-images.githubusercontent.com/99114456/301540444-5db99ff5-c19b-4cb5-ba4b-d4891b6fb778.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MDY3OTg3NzEsIm5iZiI6MTcwNjc5ODQ3MSwicGF0aCI6Ii85OTExNDQ1Ni8zMDE1NDA0NDQtNWRiOTlmZjUtYzE5Yi00Y2I1LWJhNGItZDQ4OTFiNmZiNzc4LnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDAyMDElMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwMjAxVDE0NDExMVomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPThlZWVlMWFlNDc0YzgxZWZiMzNhOTg2Y2FiMjRlYzUyNzI2NDlkZDRmNDE2YWU2M2Y5YWM3MzUyY2QyMWJkNGQmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.KJPVbh6_XVMtgS0b-II8iN2SFbNF9h590TizkiwsWXw)

기존 코드에서 하나의 TextView를 추가했다.

예상한 결과는 가로나 세로로 붙여질것이라고 생각했지만,

![](https://private-user-images.githubusercontent.com/99114456/301540542-734ac23b-2eb9-421e-92e1-4eefb75943e9.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MDY3OTg3NzEsIm5iZiI6MTcwNjc5ODQ3MSwicGF0aCI6Ii85OTExNDQ1Ni8zMDE1NDA1NDItNzM0YWMyM2ItMmViOS00MjFlLTkyZTEtNGVlZmI3NTk0M2U5LnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDAyMDElMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwMjAxVDE0NDExMVomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPWI4NjExZDA4Y2ExNGE3NDUwOGU0YWE0ZDBjN2VlZDY1MWFjZWRkNjdkMzU4ODE3ZjMxYmVmNDg0YWFmZTQ2OWMmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.OY_X8rMmdqL1P3WRWMCsuBbD-4chNVqa8uXmk1n66ws)

위에 덮어져 그려졌다.

Column: 세로로 배치

Row: 가로로 배치

Box : 추가되는 순서로 UI element를 쌓을 수 있다. 먼저 추가되는것이 아래로 내려감.

![](https://private-user-images.githubusercontent.com/99114456/301540582-1fac3973-b6c2-4565-9244-f5573a2869ee.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MDY3OTg3NzEsIm5iZiI6MTcwNjc5ODQ3MSwicGF0aCI6Ii85OTExNDQ1Ni8zMDE1NDA1ODItMWZhYzM5NzMtYjZjMi00NTY1LTkyNDQtZjU1NzNhMjg2OWVlLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDAyMDElMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwMjAxVDE0NDExMVomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTcyMGI3NGU2YjAyMWY3YWU1NTU3MjZjYzJhOTk2NmJmNjNmY2JmODJlYjBjNTNlNDdmODg4NzMxYjM0YzgyNDUmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.GRhLJqZqPpte0IS4NH8ZFeIQtL6MvySBK-D5RgWdgKU)