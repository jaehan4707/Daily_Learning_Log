# 문제 풀이
최소 신장 트리(MST)를 구현하기 위해선 Union & Find 알고리즘을 사용한다.

그 중에서 나는 크루스칼 알고리즘을 즐겨 사용하고, 해당 알고리즘이 프림보다 쉽다고 생각한다.

우선순위큐를 사용해서, 가중치를 오름차순으로 정렬하고, 같은 집합인지 검사 후 두 노드를 같은 집합으로 묶어낸다.