# Compose Modifier

# Modifier

```
Modifer를 사용하면 Composable을 장식하거나 강화할 수 있다.
```

작업

- 컴포저블의 크기, 레이아웃, 동작 및 모양 변경
- 접근성 라벨과 같은 정보 추가
- 사용자 입력 처리
- 요소를 클릭 가능, 스크롤 가능, 드래그 가능 또는 확대/축소 가능하게 만드는 높은 수준의 상호작용 추가

![](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/c51aa8d8-7426-4dc8-980d-77a195adcc05/Untitled.png?id=f38cc084-e3f2-446d-9efa-4deb20a169e7&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708272000000&signature=eGrCKmmIFG_9UaoET_qRfpfgnldzUEtKTwgx7-NFO4k&downloadName=Untitled.png)|![](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/353d57c1-3657-47cf-b9f2-aa4ab213bc5a/Untitled.png?id=5c16dded-5bed-4ae2-8731-62beba3a77ef&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708272000000&signature=ZFCcuLy-hC6PdUllShAiqzD6lyYEx-VLc1wggeYscWY&downloadName=Untitled.png)
---|---|

## 수정자의 순서가 중요

체감이 안되는데 코드로 보면 확실하게 실감이 간다.


![](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/602423b7-3a01-431e-87f9-26a2878ea9a9/Untitled.png?id=c665e745-57c9-49da-8ef0-1f49a2566b87&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708272000000&signature=jMczUvrpzrQB36WzjhP7C0h7jrDw2uDe-pJyy4KfQ2c&downloadName=Untitled.png)|![](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/f8fdbec9-5c51-4aaf-8a18-4ac51ba1797c/Untitled.png?id=0c147c39-11fb-4064-af22-73e4b7a28951&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708272000000&signature=xfIH4zRPrWiUtKud0h-IrasT6QLedq46kcztopZvx_s&downloadName=Untitled.png)
---|---|

위 코드와 내용은 동일하지만 전혀 다른 결과가 나온다.

이유는 padding 수정자가 background 수정자 뒤에 적용되었기 때문이다.

## Modifier의 사이즈는 더욱 세밀하게 조정이 가능하다.

![](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/c2ba5242-1f63-44e3-a806-42eb53bc5277/Untitled.png?id=2561c1ba-1553-4129-98ed-508021282e79&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708272000000&signature=JaeoY0ILTTJ5kh3DA6eN5p2SnBR1_CAGVNgxO5fFwUo&downloadName=Untitled.png)|![](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/03a1bc4f-92b1-4e0c-ae87-03c1524d094d/Untitled.png?id=1e7102dc-d46c-4a36-aeb4-32e51d7cca54&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708272000000&signature=eipyIZ3fXskIGEiWo4PJAzsFyFN7dmz2qujpXpCZZXk&downloadName=Untitled.png)
---|---|

### requireSize

- 상위 요소에 제약조건과 상관없이 절대적인 크기를 주고 싶을 때 사용
- 우선적으로 적용된다.

나는 두 텍스트를 담는 row의 크기를 지정해줬고, 그 결과 위와같이 텍스트가 잘렸다.

하지만 requireSize를 통해서 크기를 고정하니 다음과 같은 결과가 나왔다.

![](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/0485d36e-653e-40ac-859a-cbb845b47557/Untitled.png?id=a8a037bd-edc3-4182-9961-2407d939a3de&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708272000000&signature=-MIxBSbS3Wkr43LsjVx8v-oA-aJorjaueiVaAUUOErw&downloadName=Untitled.png)|![](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/51046d65-1423-4c09-a150-e8b0f5b8f5f2/Untitled.png?id=de111fdf-7289-4829-acf5-f9c84f8ce223&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708272000000&signature=1fLRmNPN9LbSe4t53cDI645GPHNDZ7bVuWu8aR88DEI&downloadName=Untitled.png)
---|---|

## Clickable

Modifer의 click 옵션을 줄 수 도 있다.

![](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/56221374-4be9-4d4b-a789-6555347cb4c7/Untitled.png?id=052919a2-0268-4a18-8e4d-f2cee38870fe&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708272000000&signature=48b2eQpPiBPkuVb6cc9jUyVsWZNL0jXUJvH8y1dqpLE&downloadName=Untitled.png)

default로 제공되는 파라미터가 있기에, onClick의 람다식으로만 선언이 가능하다

# 참고
https://developer.android.com/jetpack/compose/modifiers?hl=ko