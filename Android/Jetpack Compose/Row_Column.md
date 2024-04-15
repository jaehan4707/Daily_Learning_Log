# Compose Row & Column

## Column

![image](https://github.com/jaehan4707/Daily_Learning_Log/assets/99114456/ec9ac851-99bf-45c1-a839-884d2aff1adb)

```kotlin
세로방향으로 컴포넌트를 배치한다.
```

- verticalArrangement : Column을 세로 방향으로 배치한다.
- horizontalAlignment : Column을 가로 방향으로 정렬한다.

### 세로 배치(verticalArrangement)

![image](https://github.com/jaehan4707/Daily_Learning_Log/assets/99114456/67a4aeed-fd20-4bc1-8094-8a348019410c)|![image](https://github.com/jaehan4707/Daily_Learning_Log/assets/99114456/4dc254f8-c6d9-4253-80f4-74de3dadf091)
---|---|

**정렬 옵션**

![image](https://github.com/jaehan4707/Daily_Learning_Log/assets/99114456/2d40d5de-9d94-4c95-aa9c-01b7c7d837e1)

배치된 텍스트 두개를 가운데 정렬 하고 싶어서 다음과 같은 속성을 줬다.

![image](https://github.com/jaehan4707/Daily_Learning_Log/assets/99114456/6d621056-50f8-4037-a84e-a7f9b1f6b6be)


결과는 바뀌지 않았다.

이유는 내가 선언한 Column 블록의 크기를 지정해주지 않았기 때문이다.

wrap content 처럼 기본값은 딱 컴포넌트들이 들어갈 정도로만 설정된다.

따라서 휴대폰에 가운데 정렬을 위해선 Height 값을 바꿔줘야 한다.

![image](https://github.com/jaehan4707/Daily_Learning_Log/assets/99114456/28106319-2bd7-4eaf-ab76-65bb7e14f50a)|![image](https://github.com/jaehan4707/Daily_Learning_Log/assets/99114456/d37a15b7-97c5-4def-bd6e-bc6c6c8c7448)
---|---|

### 가로 정렬(horizontalAlignment)

![image](https://github.com/jaehan4707/Daily_Learning_Log/assets/99114456/6c6c89bf-6aa9-43ad-b65e-7c429316aa98)

End 값을 주니 정렬이 바뀐것을 알 수있다.

위에서 Height 경우에도 그렇듯이 width 값을 주지 않았기 때문에

width값은 가장 길이가 긴 밑에 텍스트 크기의 width 값에 맞춰져있기 때문에

밑에 텍스트는 정렬 조건을 부여해도 바뀌지 않았다.

밑에 텍스트까지 end 정렬을 하기 위해선 Modifer의 width 크기를 키워줬다.

![](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/3a2e1d05-0827-479b-8d31-1c630ebeb50c/Untitled.png?id=c70a2d46-7a89-42aa-81f6-27bbd4c0417d&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708272000000&signature=g8lvS4vIweDQQSQLLBs-WWSwxikjpJD0xd1I1rhCn8k&downloadName=Untitled.png)|![](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/e6bc515a-866e-4202-ac02-041c2e2e7297/Untitled.png?id=1895dc85-958a-40cb-9bd2-9a41a9c6fef7&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708272000000&signature=-q4TzHtz6zaTIdvNUi2O_KUswvSsxFW9eageKBprBa4&downloadName=Untitled.png)
---|---|

---

# Row

![image](https://github.com/jaehan4707/Daily_Learning_Log/assets/99114456/be4e9320-9a46-4499-a694-f05b9b74c7e9)

```kotlin
Row는 컴포넌트를 가로방향으로 배치한다.
```

![image](https://github.com/jaehan4707/Daily_Learning_Log/assets/99114456/d7a83af5-af8a-4173-a249-ede91f8a3d26)|![image](https://github.com/jaehan4707/Daily_Learning_Log/assets/99114456/f93af8c5-625f-44b0-a989-4f240892e395)
---|---|

**정렬 옵션**

![image](https://github.com/jaehan4707/Daily_Learning_Log/assets/99114456/bc3f4113-b7ac-4571-9cee-b2096a7d42a8)
Row도 동일하게 Modifier에 크기를 변경해주지 않는다면 정렬 옵션을 주더라도 바뀌는건 없습니다.

# 참고
https://developer.android.com/reference/kotlin/androidx/compose/foundation/layout/Arrangement

