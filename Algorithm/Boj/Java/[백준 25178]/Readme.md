# 문제 풀이
 문자열은 정말 풀기 힘든 문제인것 같다.   
 받을때부터 String으로 받을지, char 배열로 받을지 고민된다.    
 이번에 문제를 풀면서 알게되었던 점은 a,e,i,o,u를 모두 제거한 문자열을 만들기 위해서 고민하다가 replaceAll이라는 메서드를 사용해봤는데, 안의 파라미터가 정규식을 뜻하는 regex를 string으로 받고있었고, 그에 대한 문법도 알 수 있었다.   
 그 외에는 문자열의 끝과 처음을 비교하고, 문자열의 구성이 같은지를 cnt 배열을 통해서 비교하면 되는 간단한 문제이다.