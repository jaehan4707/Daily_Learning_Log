# 문제 풀이

전형적인 그래프 탐색 문제이고, 그래프 내에서 구역을 그룹핑하는 문제이다.   
문제에서 요구하는 바는 하나의 그래프를 두 가지 방식으로 그룹핑하는것이다.   
1. RGB 각각의 컬러를 구별할 수 있는 영역(적록색약 X)
2. RGB 중 R과 G를 하나의 영역으로 처리하는 영역(적록색약 O) 

여러 가지 방식이 있겠지만, 두 번의 탐색을 통해서 각 영역을 구별했다.

    public static boolean CheckAlpha(char origin, char other){
        if(origin == other)
            return true;
        else return origin != 'B' && other != 'B';
    }
위 함수를 통해서 두 영역의 색깔이 같으면 true, 둘 중 하나라도 B가 있으면 false를 반환하고, 나머지 경우의 수는 적록색약의 영역으로 구별했다.