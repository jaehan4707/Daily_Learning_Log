import java.util.*;

class Solution {

    static class Point {
        int x, y;

        Point(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    Point character;
    Point item;
    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};
    static int[][] graph;
    static boolean[][] visited;

    public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {
        init(rectangle, characterX, characterY, itemX, itemY);
        return bfs();
    }

    public void init(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {
        character = new Point(characterY * 2, characterX * 2);
        item = new Point(itemY * 2, itemX * 2);

        graph = new int[101][101];
        visited = new boolean[101][101];

        for (int[] rect : rectangle) {
            int y1 = rect[1] * 2;
            int x1 = rect[0] * 2;
            int y2 = rect[3] * 2;
            int x2 = rect[2] * 2;

            // 경계선과 내부를 구분하여 처리
            for (int i = y1; i <= y2; i++) {
                for (int j = x1; j <= x2; j++) {
                    if (graph[i][j] == 1) continue; // 이미 내부로 설정된 영역은 덮어쓰지 않음
                    graph[i][j] = 1; // 내부 영역

                    if (i == y1 || i == y2 || j == x1 || j == x2) {
                        graph[i][j] = 2; // 경계선
                    }
                }
            }
        }
    }

    public int bfs() {
        Queue<Point> q = new LinkedList<>();
        q.add(character);
        visited[character.y][character.x] = true;

        int steps = 0;
        while (!q.isEmpty()) {
            int size = q.size();

            for (int i = 0; i < size; i++) {
                Point now = q.poll();

                // 목표 지점 도달
                if (now.y == item.y && now.x == item.x) {
                    return steps / 2;
                }

                for (int d = 0; d < 4; d++) {
                    int nx = now.x + dx[d];
                    int ny = now.y + dy[d];

                    if (nx < 0 || ny < 0 || nx >= 101 || ny >= 101) continue; // 배열 범위 초과
                    if (visited[ny][nx] || graph[ny][nx] != 2) continue; // 방문 여부 및 경계선 확인

                    visited[ny][nx] = true;
                    q.add(new Point(ny, nx));
                }
            }
            steps++;
        }

        return 0;
    }
}