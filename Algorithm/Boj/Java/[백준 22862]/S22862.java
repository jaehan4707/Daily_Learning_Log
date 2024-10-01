import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int n, k;
    static int[] graph;

    public static void main(String[] args) throws IOException {
        input();
        solution();
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        graph = new int[n];
        sb = new StringBuilder();
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            graph[i] = Integer.parseInt(st.nextToken());
        }
    }

    public static void solution() {
        int answer = 0;
        int left = 0, right = 0, length = 0;
        int nowK = k;
        while (left <= right) {
            answer = Math.max(answer, length); // 길이 계속 업데이트
            if(right>=n){
                break;
            }
            if (graph[right] % 2 != 0) { //홀수 일 때
                if (nowK > 0) { // 뺄 수 있다면
                    right += 1; // 한 칸 이동
                    if (length > 0) {
                        nowK--;
                    } else {
                        left += 1;
                    }

                } else { // 뺄 수 없을 때
                    if (graph[left] % 2 == 0) { //짝수 일 경우
                        length -= 1; // 길이 뺌
                    } else {
                        nowK += 1; // 홀수 일 경우 제외시키니 nowK 복원
                    }
                    left += 1; //left 한칸 전진
                }
            } else {// 짝수일 경우 더하고
                length+=1;
                right+=1;
            }
        }
        System.out.print(answer);
    }
}