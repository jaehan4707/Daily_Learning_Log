import java.io.*;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static int n, m;
    static int[] parent;
    static long[] answer, cache;
    static int[][] height;
    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};
    static ArrayList<Spot> spots;

    public static void main(String[] args) throws IOException {
        input();
        process();
        printAnswer();
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        parent = new int[n * m];
        answer = new long[n * m];
        cache = new long[n * m];
        height = new int[n][m];
        spots = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                height[i][j] = Integer.parseInt(st.nextToken());
                spots.add(new Spot(i, j, height[i][j]));
            }
        }

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                int idx = i * m + j;
                cache[idx] = Long.parseLong(st.nextToken());
                answer[idx] = cache[idx];
                parent[idx] = idx; // 초기 부모 설정
            }
        }

        // 높이를 기준으로 정렬
        spots.sort((a, b) -> Integer.compare(a.h, b.h));
    }

    public static void process() {
        for (int i = 0; i < spots.size(); i++) {
            int idx1 = spots.get(i).x * m + spots.get(i).y;

            // 같은 높이를 가진 칸 그룹 처리
            int i2 = i;
            while (i2 < spots.size() && spots.get(i2).h == spots.get(i).h) {
                i2++;
            }
            i2--; // 마지막 동일 높이의 인덱스

            // 병합 진행
            for (int j = i; j <= i2; j++) {
                Spot spot = spots.get(j);
                for (int d = 0; d < 4; d++) {
                    int nx = spot.x + dx[d];
                    int ny = spot.y + dy[d];
                    if (isInMap(nx, ny) && height[spot.x][spot.y] >= height[nx][ny]) {
                        union(spot.x * m + spot.y, nx * m + ny);
                    }
                }
            }

            // 결과 갱신
            for (int j = i; j <= i2; j++) {
                Spot spot = spots.get(j);
                int idx = spot.x * m + spot.y;
                answer[idx] = answer[find(idx)];
            }

            i = i2; // 처리된 마지막 높이로 이동
        }
    }

    public static void union(int x, int y) {
        int px = find(x);
        int py = find(y);
        if (px != py) {
            parent[py] = px;
            answer[px] += answer[py]; // 병합 시 값 합산
        }
    }

    public static int find(int x) {
        if (parent[x] == x) {
            return x;
        }
        return parent[x] = find(parent[x]); // 경로 압축
    }

    public static boolean isInMap(int x, int y) {
        return x >= 0 && x < n && y >= 0 && y < m;
    }

    public static void printAnswer() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(answer[i * m + j] + " ");
            }
            System.out.println();
        }
    }

    static class Spot {
        int x, y, h;

        Spot(int x, int y, int h) {
            this.x = x;
            this.y = y;
            this.h = h;
        }
    }
}