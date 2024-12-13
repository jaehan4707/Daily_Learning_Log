import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int n,k,x;
    static Customer[] customers;

    public static void main(String[] args) throws IOException{
        input();
        solution();
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        n = Integer.parseInt(st.nextToken()); // n명의 고객 최대값 10만
        k = Integer.parseInt(st.nextToken()); // k개의 계산대 최대값 10만
        x = Integer.parseInt(st.nextToken()); // x번째로 계산하는 사람이 누구니
        customers = new Customer[n];
        for(int i=0; i<n; i++){
            st = new StringTokenizer(br.readLine());
            // 고유 번호, 계산 걸리는 시간
            int num = Integer.parseInt(st.nextToken());
            int time = Integer.parseInt(st.nextToken());
            customers[i] = new Customer(num,time);
        }
    }

    public static void solution(){
        // 빨리 계산할 수 있는 계산대로 안내.
        // 기다려야 하는 시간이 같은 경우 번호가 작은 계산대로 안내.
        // 예를 들어 1번과 7번 모두 기다리는 시간이 7분일 경우 1번 계산대로 안내
        // 계산대에서 계산을 마친 고객의 시간이 같을 경우 낮은 번호 계산대의 고객부터 나감
        // X번째로 계산을 하고 나간 손님의 고유 번호를 구해라.
        PriorityQueue<Counter> pq = new PriorityQueue<Counter>((p1,p2)->{
            if(p1.res==p2.res){ //기다려야 하는 시간이 같을 경우
                return Integer.compare(p1.id, p2.id); // 가장 작은 번호 계산대로
            }
            // 아닐 경우 시간이 적은 계산대
            return Integer.compare(p1.res, p2.res);
        }); //계산대
        int time = 0;
        for(int i=0; i<Math.min(n,k); i++){ // 계산대 할당
            pq.add(new Counter(i,customers[i]));
        }
        int seq = k; // k번째 손님부터 시작할게유!
        int exitSeq = 0;
        while(!pq.isEmpty()){ //계산대가 빌 때까지?
            Counter now = pq.poll(); //현재 계산대
            int counterId = now.id; //계산기 넘버
            int customerTime = now.customer.time; // 고객 걸리는 시간
            int counterRes = now.res; // 카운터 기다리는 시간
            int customerNum = now.customer.num; //고객 번호
            exitSeq++;
            if(exitSeq==x){
                System.out.println(customerNum);
                break;
            }

            if(seq<n){
                int nowCustomNum = customers[seq].num;
                int nowtime = customers[seq].time;
                pq.add(new Counter(counterId,new Customer(nowCustomNum,nowtime+customerTime)));
                seq++; // 고객 순서 땅기기
            }
        }
    }

    static class Customer {
        int num,time;

        Customer(int num, int time){
            this.num = num;
            this.time = time;
        }
    }

    static class Counter {
        int id,res;
        Customer customer;

        Counter(int id, Customer customer){
            this.id=id;
            this.customer=customer;
            this.res = customer.time;
        }
    }
}

