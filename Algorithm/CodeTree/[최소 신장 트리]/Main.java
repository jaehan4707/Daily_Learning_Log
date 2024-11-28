import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static int n,m;
    static PriorityQueue<Node> tree;
    static int[] parent;

    public static void main(String[] args) throws IOException{
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        tree = new PriorityQueue<>((n1,n2)->{
            return Integer.compare(n1.v,n2.v);
        });
        parent = new int[n+1];
        for(int i=1; i<=n; i++){
            parent[i] = i;
        }
        for(int i=0; i<m; i++){
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            tree.add(new Node(s,e,v));
        }
        int answer = 0;
        while(!tree.isEmpty()){
            Node now = tree.poll();
            //방문했는지 검사하고,
            if(find(now.s)==find(now.e)){ // 부모가 같다면 할 필요 없음
                continue;
            }
            union(now.s,now.e);
            answer += now.v;
        }
        System.out.print(answer);
    }

    static int find(int x){
        if(parent[x] == x){ //자기 자신이 부모일 경우 반환
            return parent[x];
        }
        return find(parent[x]);
    }

    static void union(int s, int e){
        int parentS = find(s);
        int parentE = find(e);

        if(parentS < parentE){
            parent[parentE] = parentS;
        } else {
            parent[parentS] = parentE;
        }
    }

    static class Node{
        int s,e,v;

        Node(int s,int e,int v){
            this.s=s;
            this.e=e;
            this.v=v;
        }
    }
}
