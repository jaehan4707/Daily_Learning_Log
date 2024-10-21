# Trie

![image.png](https://upload.wikimedia.org/wikipedia/commons/thumb/b/be/Trie_example.svg/375px-Trie_example.svg.png)

- 문자열을 저장하고 효율적으로 탐색하기 위한 트리 형태의 자료구조이다.
- 위에 보이는 트리의 루트에서부터 자식들을 따라가면서 생성된 문자열들이 트라이 자료구조에 저장되어 있다.
  저장된 단어는 끝을 표시하는 변수를 추가해서 저장된 단어의 끝을 구분할 수 있다.
- Apple이라는 단어를 검색하기 위해 A→p→p→l→e의 순서로 단어를 찾는다.

## 사용목적

- 문자열의 탐색을 하고자 할 때 시간복잡도를 보면 알 수 있다.
  단순하게 하나씩 비교하면서 탐색을 하는것보다 훨씬 효율적이다.

## 장점

- 문자열 탐색할 때 하나하나씩 전부 비교하면서 탐색을 하는 것보다 시간 복잡도 측면에서 훨씬 더 효율적이다.

## 단점

- 각 노드에서 자식들에 대한 포인터들을 배열로 모두 저장하고 있다는 점에서 저장 공간의 크기가 크다.

## 시간 복잡도

- 제일 긴 문자열의 길이를 `L`, 총 문자열들의 수를 `M`이라고 할 때 시간복잡도는 다음과 같다.
- 생성 시 시간 복잡도 :  `O(MxL)` , 모든 문자열들을 넣어야 하니 `M` 개에 대해서 트라이 자료구조에 넣는건 가장 긴 문자열 길이만큼 걸리니 `L` 만큼 걸려서  `O(MxL)` 만큼 걸린다. (물론 삽입은 `O(L)` 만큼 걸린다.
- 탐색 시 시간복잡도 :  `O(L)` , 트리를 타고 들어가봤자 가장 긴 문자열의 길이만큼 탐색하기 때문에 `O(L)` 만큼 걸린다.

## 트라이 구조의 특징

### 루트 노드

- 항상 비어있다.
- 루트노드의 자식노드는 각 단어들의 첫 글자들이다.

### endOfWord 표시

- 각 문자열의 마지막 글자를 boolean 변수로 표시한다.

### 각 노드 구성

- 각 노드의 자식들을 Map에 저장한다.
- 해당 노드가 단어의 마지막을 뜻하는 endOfWord를 저장할 boolean형 필드를 갖는다.

```
class Node {
	HashMap<Character,Node> child;
	boolean endOfWord;
}
```

### 삽입

루트노드부터 자식노드를 담은 Map에서 삽입할 문자열의 한 문자씩 찾아본 뒤, 없으면 추가하고 있으면 타고 들어간다.
문자열의 마지막 문자가 되면 노드에 마지막 노드라는 표시를 한다.
그림에서는 파란색 색칠을, 코드로는  `endOfWord` = true로 바꿔준다.

![image.png](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FO4VXc%2Fbtsj0eR8i5x%2F6ccOnlHKetBxliuo4c42K0%2Fimg.png)

### 탐색

Trie에 “best”가 존재하는 지 탐색해보자.

중간에 한번이라도 원하는 값이 존재하지 않으면 트라이에는 “best”가 들어있지 않는 것이다.

![image.png](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fn3DKG%2FbtsjYyjBfNc%2FDuq1u6zOaDXwWaqfAyUvB0%2Fimg.png)

1. 루트 노드의 자식노드들 중 ‘b’가 있는지 찾아본다. → b로 이동한다.
2. ‘b’ 노드의 자식노드들 중에서 ‘e’가 있는지 찾아본다. → e로 이동한다.
3. ‘e’ 노드의 자식노드들 중에서 ‘s’가 있는지 찾아본다. → s로 이동한다.
4. ‘s’ 노드의 자식노드들 중에서 ‘t’가 있는지 찾아본다. → t로 이동한다.
5. ‘t’는 “best”의 마지막 글자인데, ‘t’와 endOfWord를 확인해보고 **endOfWord = true**면 탐색 성공이다.
6. ‘t’의 endOfWord를 확인했는데, **endOfWord = false**면 탐색 실패이다.

### 삭제

![image.png](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fn3DKG%2FbtsjYyjBfNc%2FDuq1u6zOaDXwWaqfAyUvB0%2Fimg.png)

“apple”을 삭제한다고 할 때

1. 위의 탐색방법으로 “apple”을 탐색하여 “apple”의 마지막 노드로 이동한다.
2. ‘e’의 endOfWord를 확인하여 true면 삭제 성공이다. ‘e’의 **endOfWord**를 **false**로 바꿔준다.
3. ‘e’의 자식노드가 있는지 확인한다. 없으면 이제 거꾸로 타고 올라가면서 노드를 삭제해준다.
4. ‘l’에 ‘e’를 제외한 다른 자식 노드가 있는지 확인한 후 없으면 ‘l’ 노드를 삭제한다.
5. apple 까지 도착했다. 찾아보닌 p를 제외한 다른 자식노드 ‘r’이 존재한다. ‘p’는 삭제하지 않는다.

## 구현

구현은 `Node` 클래스와 `Trie` 클래스로 나뉜다.

Node 클래스는 자식노드들을 담을 **Map,** 마지막 글자 여부를 확인할 boolean 필드를 갖는다.

### Node

생성할 때 hashMap으로 child를 만들어주고, endOfWord를 false로 설정한다.

```java
class Node{
	HashMap<Character, Node> child;
	boolean endOfWord;
	
	Node(){
		this.child = new HashMap<>();
		this.endOfWord = false;
	}
}
```

### Trie

Trie 클래스는 root 노드를 필드로 갖는다.

또한, 생성할 때 root에 new Node() 객체를 만들어준다.

```java
public class Trie {
	Node root;
	
	Trie() {
		this.root = new Node();
	}
	
	public void insert(String str);
	boolean search(String str);
	public boolean delete(String str);
}
```

### 삽입

맨 처음에 node = this.root로 루트 노드로 시작한다.

for 문을 돌며 한 문자씩 각 노드의 child 노드에 값이 있나 확인하고, 있으면 해당 노드로 이동하고, 없으면 이번 문자 노드를 생성하고 그 노드로 이동한다.

for문이 종료되면 node 에는 마지막 문자에 해당하는 노드가 저장되어 있을테니, 해당 노드의 endOfWord를 true로 바꿔준다.

```java
public void insert(String str){
	Node node = this.root;
	for(int i=0; i<str.length(); i++){
		char c = str.charAt(i);
		node.child.putIfAbsent(c,new Node());
		// 있으면 value를 반환, 없으면 새로운 노드를 생성해서 넣음.
		node = node.child.get(c); //자식 노드로 이동
	}
	// for문이 끝나면 현재 노드는 마지막 글자이기 때문에 단어의 끝임을 명시
	node.endOfWord = true;
}
```

### 탐색

삽입 메서드와 비슷하게 자식노드들 중 원하는 문자가 있는지 확인한다.
중간에 하나라도 없으면 탐색 실패이다.
마지막 문자까지 찾으면 마지막으로 endOfWord가 true인지 확인한다.

```java
boolean search(String str){
	Node node = this.root;
	
	for(int i=0; i<str.length(); i++){
		char c = str.charAt(i);
		if(node.child.containsKey(c)){
			node = node.child.get(c);
		} else {
			return false;
		}
	}
	return node.endOfWord; // 마지막 노드의 endOfWord를 반환
}
```

### 삭제

삭제는 사용자가 삭제 요청 시 사용하는 `public boolean delete(String str)` 과 내부에서 재귀를 통해 삭제하는 `private boolean delete(Node node, String str, int idx)` 메서드로 나뉜다.

위의 메서드에서 아래 메서드를 한번 호출해주고 아래 메서드에서 재귀적으로 노드들을 찾는다.

```java
public boolean delete(String str){
	boolean result = delete(this.root,str,0);
	return result;
}

private boolean delete(Node node, String str, int idx){
	char c = str.charAt(idx);
	
	if(!node.child.containsKey(c)){
		return false;
	}
	
	Node cur = node.child.get(c);
	idx++;
	if(idx==str.length()){
		if(!cur.endOfWord){
			return false;
		}
		
		//false로 바꿀 경우 찾을 수 없음.
		cur.endOfWord = false;
		
		if(cur.child.isEmpty()){
			node.child.remove(c);
		}
		
	} else {
			if(!this.delete(cur,str,idx)){
				return false;
			}
			
			// true & 자식노드가 비어있으면 현재 노드를 삭제
			if(!cur.endOfWord && cur.child.isEmpty()){
				node.child.remove(c);
			}
		}
	return true;
}
```

### 출처

https://twpower.github.io/187-trie-concept-and-basic-problem

https://innovation123.tistory.com/116