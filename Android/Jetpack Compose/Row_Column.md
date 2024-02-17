# Compose Row & Column

## Column

![](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/9693dab4-d57e-4254-bfba-3c025f0cd88b/Untitled.png?id=13cc494b-48d9-4c2b-9940-56f46a10d2d7&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708272000000&signature=tR7ip2l_dFq-Tb7Yrh0urJ1LJgJrXL5Zwy6fEE_8zoU&downloadName=Untitled.png)

```kotlin
세로방향으로 컴포넌트를 배치한다.
```

- verticalArrangement : Column을 세로 방향으로 배치한다.
- horizontalAlignment : Column을 가로 방향으로 정렬한다.

### 세로 배치(verticalArrangement)

![](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/63b39fed-1b41-4f28-a2c4-1a3ba5b3d09a/Untitled.png?id=b4f2028d-7b95-4fe0-bb85-f96bbad04198&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708272000000&signature=t0HBOWZabecPheYTA7VwsC3GARIbDO0Zou8TXyBcEsA&downloadName=Untitled.png)|![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/b567e643-a5fa-44ea-ac59-eb84b1f00404/Untitled.png?id=830c189c-535a-4b63-bb8b-4758791f953d&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708272000000&signature=DByKsKDiGFdme9iHYJfAKB9cLt4QcyFeOSvQ5Bl4AyA&downloadName=Untitled.png)
---|---|

**정렬 옵션**

![](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/63cbfafc-0170-44cf-9260-8ce26c13796a/Untitled.png?id=52e10ffa-2f97-4043-b390-27186032b1d9&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708272000000&signature=f6TfEAj4SJBwNg14kuVhiY540ImWcKiWyZxcLx4F8_A&downloadName=Untitled.png)

배치된 텍스트 두개를 가운데 정렬 하고 싶어서 다음과 같은 속성을 줬다.

![](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/cbd131c8-f649-4977-8f3a-1e62177c975d/Untitled.png?id=f7693a1f-04c9-4322-b55c-a052dc4d074b&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708272000000&signature=GdmS_PAV4LzYSGFW15rP2RJa-DKB3OKIXZgpIfkerJs&downloadName=Untitled.png)

결과는 바뀌지 않았다.

이유는 내가 선언한 Column 블록의 크기를 지정해주지 않았기 때문이다.

wrap content 처럼 기본값은 딱 컴포넌트들이 들어갈 정도로만 설정된다.

따라서 휴대폰에 가운데 정렬을 위해선 Height 값을 바꿔줘야 한다.

![](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/1296efd4-0141-44f9-8a15-3b8c27a92c67/Untitled.png?id=d9472865-8b59-438a-a12b-0d158a6fed0d&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708272000000&signature=leQBdUtNRyqbyg5QH9b3xgTRNsJsK84y1VXd_FffL0c&downloadName=Untitled.png)|![](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/88437aea-4f1c-4f7f-8642-0ac763bfd613/Untitled.png?id=2aecb8d5-8df5-4468-a516-03b781014e75&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708272000000&signature=7JocaIkiaQpyUmrpHEY-uDrIVDnZ50Eexo9ZH_iIALQ&downloadName=Untitled.png)
---|---|

### 가로 정렬(horizontalAlignment)

![](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/a2b08018-3def-4a89-9545-38cdd13f4390/Untitled.png?id=b72cd605-dd4d-408b-9c7a-b7b0acd11a2c&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708272000000&signature=TJX7p_ole00lrbqqZlFKpUEVLNmYUrhSs8hNGR5aHME&downloadName=Untitled.png)|![](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/22f87dc9-ee2c-41c8-93bc-f8e9160d3556/Untitled.png?id=b9da5daa-a4f9-4f84-9dad-9b31adfe9f67&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708272000000&signature=eMUzSAOzoufWCk6x6AeiCf0TQfhS-otiWLpIk78gpto&downloadName=Untitled.png)
---|---|

End 값을 주니 정렬이 바뀐것을 알 수있다.

위에서 Height 경우에도 그렇듯이 width 값을 주지 않았기 때문에

width값은 가장 길이가 긴 밑에 텍스트 크기의 width 값에 맞춰져있기 때문에

밑에 텍스트는 정렬 조건을 부여해도 바뀌지 않았다.

밑에 텍스트까지 end 정렬을 하기 위해선 Modifer의 width 크기를 키워줬다.

![](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/3a2e1d05-0827-479b-8d31-1c630ebeb50c/Untitled.png?id=c70a2d46-7a89-42aa-81f6-27bbd4c0417d&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708272000000&signature=g8lvS4vIweDQQSQLLBs-WWSwxikjpJD0xd1I1rhCn8k&downloadName=Untitled.png)|![](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/e6bc515a-866e-4202-ac02-041c2e2e7297/Untitled.png?id=1895dc85-958a-40cb-9bd2-9a41a9c6fef7&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708272000000&signature=-q4TzHtz6zaTIdvNUi2O_KUswvSsxFW9eageKBprBa4&downloadName=Untitled.png)
---|---|

---

# Row

![](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/ce4bf2d8-c63c-41bf-9239-7d2a5359dbc1/Untitled.png?id=75157db9-8775-4640-8964-75ac0967373f&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708272000000&signature=9wclq7gNYD0gy7TsJ4psNaF7ZzkuWgGZXhMtw0OsP5A&downloadName=Untitled.png)

```kotlin
Row는 컴포넌트를 가로방향으로 배치한다.
```

![](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/1e233b95-89ec-4d99-bcb4-c3e2598ac515/Untitled.png?id=c5fbfa04-99f7-443e-9931-eb5c56ebee28&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708272000000&signature=PcnQ4cHjYZfO0IWSrZ_QDb3YvWdnjDp7nWyb9fQBC18&downloadName=Untitled.png)|![](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/ead778e8-162d-48fa-a61f-00c5c814a21d/Untitled.png?id=e981c1b2-17b3-4983-b48c-40cc6c9088ff&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708272000000&signature=57Mj2VQu0toeWA4ePJJbizawYdSzUMDFMGHx5D1YWxg&downloadName=Untitled.png)
---|---|

**정렬 옵션**

![](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/a4e20d9c-7a97-4d06-9736-5ff38539ea29/Untitled.png?id=beea2437-d33f-4ace-88d9-608500e7ea3a&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708272000000&signature=3AzoPjP9bTYyK8cG32j2TCxmP71NTgCGY5z9pr2ECKY&downloadName=Untitled.png)
Row도 동일하게 Modifier에 크기를 변경해주지 않는다면 정렬 옵션을 주더라도 바뀌는건 없습니다.

# 참고
https://developer.android.com/reference/kotlin/androidx/compose/foundation/layout/Arrangement

