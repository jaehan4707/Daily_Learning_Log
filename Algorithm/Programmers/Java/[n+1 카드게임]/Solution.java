import java.util.*;

class Solution {
    static int n,coin;
    static ArrayList<Integer> hands;
    static ArrayList<Integer> deck;
    static ArrayList<Integer> candidate;
    static int [] cards;

    public int solution(int c, int[] card) {
        int answer = 0;
        init(c,card);
        return game();
    }

    public void init(int c, int [] card){
        hands = new ArrayList<>();
        deck = new ArrayList<>();
        candidate = new ArrayList<>();
        n = card.length;
        coin = c;
        for(int i=0; i<n; i++){
            deck.add(card[i]);
        }
        for(int i=0; i<n/3; i++){
            hands.add(deck.get(0));
            deck.remove(0);
        }
    }

    public int game(){
        // 두턴
        int turn = 1;
        while(deck.size()!=0){
            int card1 = deck.remove(0); // 두장 뽑기
            int card2 = deck.remove(0); // 두장 뽑기
            candidate.add(card1);
            candidate.add(card2);
            if(validateByHand()){ // 손패로 가능하다면, 카드를 가질 수도 있고, 아닐 수도 있음.
                turn++;
                continue;
            }
            // 불가능 하다면? 앞에서 지나쳤던 카드들로 할 수 있는가?
            if(coin>=1){ //잔여 코인이 있다면 후보 카드들을 하나 뽑아서 , 현재 내 패랑 엮을 수 있는지 확인함.
                if(validateByHandCandidate()){
                    turn++;
                    continue;
                }
            }

            if(coin>=2){ //후보 카드에서 2개 뽑을 수 있다면?
                if(validateByCandidate()){
                    turn++;
                    continue;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        return turn;
    }

    static boolean validateByHand(){
        for(int i=0; i<hands.size(); i++){
            int first = hands.get(i);
            for(int j=i+1; j<hands.size(); j++){
                int second = hands.get(j);

                if(first+second==n+1){ //n+1이 된다면,
                    hands.remove(j);
                    hands.remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    static boolean validateByHandCandidate(){
        for(int i=0; i<hands.size(); i++){
            int card = hands.get(i);
            int target = n+1 - card;
            if(candidate.contains(target)){ //값이 있다면
                coin--;
                hands.remove(i);
                candidate.remove((Integer)target); // 값 기준으로 지우려면 Object 타입을 넘겨줘야 함
                return true;
            }
        }
        return false;
    }

    static boolean validateByCandidate(){
        for(int i=0; i<candidate.size(); i++){
            int first = candidate.get(i);
            for(int j=i+1; j<candidate.size(); j++){
                int second = candidate.get(j);

                if(first+second==n+1){ //n+1이 된다면,
                    candidate.remove(j);
                    candidate.remove(i);
                    coin-=2;
                    return true;
                }
            }
        }
        return false;
    }
}
