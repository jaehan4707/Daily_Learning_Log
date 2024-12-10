import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int n,m;
    static ArrayList<ArrayList<Integer>> graph;
    static int[] dp;
    static boolean [] visit;

    public static void main(String[] args) throws IOException{
        input();
        ArrayList<Item> items = new ArrayList<>();
        for(int i=1; i<=n; i++){
            visit = new boolean[n+1];
            solution(i,i);
            items.add(new Item(i,dp[i]));
        }
        Collections.sort(items, new Comparator<Item>(){

            @Override
            public int compare(Item o1, Item o2){
                if(o2.cnt==o1.cnt){
                    return Integer.compare(o1.idx,o2.idx);
                }
                return Integer.compare(o2.cnt,o1.cnt);
            }
        });
        int maxCnt = 0;
        for(int i=0; i<items.size(); i++){
            if(maxCnt<=items.get(i).cnt){
                sb.append(items.get(i).idx+" ");
                maxCnt = items.get(i).cnt;
            } else{
                break;
            }
        }
        System.out.print(sb.toString());
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        dp = new int[n+1];
        Arrays.fill(dp,-1);
        graph = new ArrayList<>();
        for(int i=0; i<=n; i++){
            graph.add(new ArrayList<>());
        }

        for(int i=0; i<m; i++){
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            graph.get(s).add(e);
        }
        // 가장 많이 방문할 수 있는 정점~!
    }

    public static void solution(int cur, int now){
        dp[cur]+=1;
        visit[now] = true;
        for(int i=0; i<graph.get(now).size(); i++){
            int next = graph.get(now).get(i);
            if(visit[next]){
                continue;
            }
            solution(cur, next);
        }
    }

    static class Item{
        int idx, cnt;

        Item(int idx, int cnt){
            this.idx=idx;
            this.cnt=cnt;
        }
    }
}

