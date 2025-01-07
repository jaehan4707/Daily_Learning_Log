import java.util.*;

class Solution {

    static int [] dx = {1,0,0,-1}; // 하, 왼, 오, 상
    static int [] dy = {0,-1,1,0}; // 하, 왼, 오, 상
    static char[] dir = {'d','l','r','u'};
    static int N,M,K,startX, startY, endX,endY;
    static String answer = "impossible";

    public String solution(int n, int m, int x, int y, int r, int c, int k) {
        init(n,m,x,y,r,c,k);
        if(diff(startX,startY)>k){
            return "impossible";
        }
        dfs(0,startX,startY,"");
        return answer;
    }

    public void init(int n, int m, int x,int y, int r, int c, int k){
        N=n;
        M=m;
        K=k;
        startX=x;
        startY=y;
        endX=r;
        endY=c;
    }


    public void dfs(int depth, int x, int y, String str){
        if(answer!="impossible"){
            return;
        }
        int diff = diff(x,y);
        if(diff>K-depth){ //남은 거리가 잔여횟수보다 크다면 볼 필요 없음,
            return;
        }

        if((K-depth-diff)%2==1){ // 잔여횟수가 홀수인 경우 불가능
            return;
        }

        if(depth==K && x == endX && y == endY){
            answer = str;
            return;
        }

        for(int i=0; i<4; i++){
            int mx = x+dx[i];
            int my = y+dy[i];
            if(!isInMap(mx,my)){
                continue;
            }
            dfs(depth+1,mx,my,str+dir[i]);
        }
    }

    public int diff(int x, int y){
        return Math.abs(x-endX)+Math.abs(y-endY);
    }

    public boolean isInMap(int x, int y){
        if(x<1 || x>N || y<1 || y>N){
            return false;
        }
        return true;
    }
}