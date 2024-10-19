import java.io.*;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;

    static int n = 0;

    static int[] a;
    static int[] b;
    static int[] c;
    static int[] d;

    static int[] fir;
    static int[] sec;


    public static void main(String[] args) throws IOException {
        input();
        setValue();
        System.out.print(solution());
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        a = new int[n];
        b = new int[n];
        c = new int[n];
        d = new int[n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            a[i] = Integer.parseInt(st.nextToken());
            b[i] = Integer.parseInt(st.nextToken());
            c[i] = Integer.parseInt(st.nextToken());
            d[i] = Integer.parseInt(st.nextToken());
        }
        fir = new int[n * n];
        sec = new int[n * n];
    }

    public static void setValue() {
        int idx = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                fir[idx] = a[i] + b[j];
                sec[idx] = c[i] + d[j];
                idx++;
            }
        }
        Arrays.sort(fir);
        Arrays.sort(sec);
    }

    public static long solution() {
        long answer = 0;
        int left = 0; //왼쪽은 fir 포인터
        int right = n * n - 1; // 오른쪽은 sec 포인터
        while (left <= n * n - 1 && right >= 0) { //
            long sum = fir[left] + sec[right];
            if (sum > 0) {
                right--;
            } else if (sum < 0) {
                left++;
            } else { //0일 경우 몇개 있는지 찾아야 함.
                long leftCnt = 1, rightCnt = 1; //우선 반드시 한개
                while (left +1 <= n * n - 1 && fir[left] == fir[left + 1]) { //left 땡기기
                    leftCnt++;
                    left++;
                }
                while (right-1 >= 0 && sec[right] == sec[right - 1]) {
                    rightCnt++;
                    right--;
                }
                answer += leftCnt * rightCnt;
                right -= 1;
            }
        }
        return answer;
    }
}