import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int n,m,k;
    static Item[] items;

    static HashMap<Long,int[][]> map;

    public static void main(String[] args) throws IOException{
        br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();
        input();
        solution();
    }

    public static void input() throws IOException{
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        map = new HashMap<>();
        items = new Item[n];
        for(int i=0; i<n; i++){
            st = new StringTokenizer(br.readLine());
            String v = st.nextToken();
            long n = Long.parseLong(st.nextToken());
            items[i] = new Item(v,n);
            if(map.get(n)==null){
                int[][] ary = new int[m][26];
                for(int j=0; j<m; j++){
                    int ch = v.charAt(j)-'a';
                    ary[j][ch]+=1;
                }
                map.put(n,ary);
            } else {
                int [][] ary = map.get(n);
                for(int j=0; j<m; j++){
                    int ch = v.charAt(j)-'a';
                    ary[j][ch]+=1;
                }
                map.put(n,ary);
            }
        }
    }

    public static void solution(){
        for(int i=0; i<n; i++){
            Item now = items[i];
            if(map.get(k-now.number)==null){
                sb.append(0).append("\n");
                continue;
            }
            int [][] ary = map.get(k-now.number);
            for(int j=0; j<m; j++){
                int ch = now.voca.charAt(j)-'a';
                now.score+=ary[j][ch];
            }
            if(k-now.number == now.number){
                now.score-=m;
            }
            sb.append(now.score).append("\n");
        }
        System.out.print(sb.toString());
    }

    static class Item{
        String voca;
        long score = 0;
        long number;

        Item(String voca, long number){
            this.voca = voca;
            this.number = number;
        }
    }
}
