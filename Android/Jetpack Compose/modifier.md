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

![image](https://github.com/jaehan4707/Daily_Learning_Log/assets/99114456/ef19bec0-e9a5-45ed-bcb3-0e65c34784d9)

## 수정자의 순서가 중요

체감이 안되는데 코드로 보면 확실하게 실감이 간다.


![image](https://github.com/jaehan4707/Daily_Learning_Log/assets/99114456/1c288149-8e85-42d3-af6f-d0054f82796d)

위 코드와 내용은 동일하지만 전혀 다른 결과가 나온다.

이유는 padding 수정자가 background 수정자 뒤에 적용되었기 때문이다.

## Modifier의 사이즈는 더욱 세밀하게 조정이 가능하다.

![image](https://github.com/jaehan4707/Daily_Learning_Log/assets/99114456/998cbd01-fd98-4bb3-b2de-9a66739d1b8c)

### requireSize

- 상위 요소에 제약조건과 상관없이 절대적인 크기를 주고 싶을 때 사용
- 우선적으로 적용된다.

나는 두 텍스트를 담는 row의 크기를 지정해줬고, 그 결과 위와같이 텍스트가 잘렸다.

하지만 requireSize를 통해서 크기를 고정하니 다음과 같은 결과가 나왔다.

![image](https://github.com/jaehan4707/Daily_Learning_Log/assets/99114456/55397f41-7545-48f7-9ba7-072cd143605f)

## Clickable

Modifer의 click 옵션을 줄 수 도 있다.

![image](https://github.com/jaehan4707/Daily_Learning_Log/assets/99114456/4a11580f-c19e-467c-a31b-670cc024f7d1)

default로 제공되는 파라미터가 있기에, onClick의 람다식으로만 선언이 가능하다

# 참고
https://developer.android.com/jetpack/compose/modifiers?hl=ko