import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
학생들은 3번 ~ N+2번의 번호
출석 코드 -> 입장 번호의 배수 -> 출석 코드 전달
k명은 출석코드 제출 x, 보내지도 않음
무작위로 한명에게 출석코드 보내는 행위를 Q번 , 특정 구간의 입장 번호를 받은 학생들 중 출석 X인 학생 수
 */
public class Main {

    static BufferedReader br;
    static int n, k, q, m;
    static int[] student;
    static int[] sum;

    static ArrayList<Integer> qr;

    static StringBuffer sb;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuffer();
        input();
        br.close();
        System.out.print(sb.toString());
    }

    public static void input() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        q = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        student = new int[n + 3];
        sum = new int[n + 3];
        qr = new ArrayList<>();
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < k; i++) { //졸고 있는 학생
            student[Integer.parseInt(st.nextToken())] = -1;
        }
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < q; i++) { //출석 코드 받을 학생
            int next = Integer.parseInt(st.nextToken());
            if (student[next] == -1) { //조는 사람들임
                continue;
            }
            student[next] = 1;
            qr.add(next);
        }
        search();
        for (int i = 0; i < m; i++) { // 구간의 범위
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            sb.append(end-start+1-(+sum[end] - sum[start-1])).append("\n");
        }
    }

    public static void search() { //모든 구간에 대해 계산하고 , 출력
        for (int i = 0; i < qr.size(); i++) {
            for (int j = qr.get(i); j < n + 3; j++) {
                if(student[j]==-1){
                    sum[j] = Math.max(sum[j],sum[j-1]);
                    continue;
                }
                if (j % qr.get(i) == 0) { // qr을 받을 수 있으면
                    student[j] = 1;
                }
                sum[j] = Math.max(sum[j], sum[j - 1]+student[j]); //누적합 업데이트
            }
        }
    }
}
