import java.io.*;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static int n,m,k;
    static int[] building;
    static ArrayList<ArrayList<Path>> paths;
    static int [] dist1;
    static int [] dist2;
    static int INF = 10000000;


    public static void main(String[] args) throws IOException {
        input();
        int answer = INF;
        solution(1,dist1);
        solution(n,dist2);
        for(int i=2; i<n; i++){
            if(building[i]==1){
                answer = Math.min(answer,dist1[i]+dist2[i]);
            }
        }
        if(answer >= INF){
            answer = -1;
        }
        System.out.print(answer);
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        building = new int[n+1];
        dist1 = new int[n+1];
        dist2 = new int[n+1];
        Arrays.fill(dist1,INF);
        Arrays.fill(dist2,INF);
        paths = new ArrayList<>();
        for(int i=0; i<=n; i++){
            paths.add(new ArrayList<>());
        }
        st = new StringTokenizer(br.readLine());
        for(int i=0; i<k; i++){ //보물이 있는 건물
            int build = Integer.parseInt(st.nextToken());
            building[build] = 1;
        }
        for(int i=0; i<m; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            paths.get(a).add(new Path(b,d));
            paths.get(b).add(new Path(a,d));
        }
    }

    static void solution(int from, int [] dist){
        PriorityQueue<Path> pq = new PriorityQueue<>((p1,p2)->{
            return Integer.compare(p1.l,p2.l);
        });
        pq.add(new Path(from, 0));
        while(!pq.isEmpty()){
            Path now = pq.poll();
            for(int i=0; i<paths.get(now.e).size(); i++){
                Path next = paths.get(now.e).get(i);
                int nextDist = now.l+next.l;
                if(dist[next.e]<nextDist){ //거리가 더 크다면
                    continue;
                }
                dist[next.e] = nextDist;
                pq.add(new Path(next.e,nextDist));
            }
        }
    }

    static class Path {
        int e,l;

        Path(int e, int l){
            this.e=e;
            this.l=l;
        }
    }
}