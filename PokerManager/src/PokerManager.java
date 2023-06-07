/* run() 하면 실행되는 포커 매니저이다.
 *  */

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;

public class PokerManager {
    private LinkedList<Player> players;
    private LinkedList<Card> communutyCards;
    private Deck cardDeck;
    private int numberOfPlayer;
    private OddCalculator oddCalculator;

    /* 생성자 */
    public PokerManager() {
        players = new LinkedList<>();
        communutyCards = new LinkedList<>();
        cardDeck = new Deck();
        oddCalculator = new OddCalculator();
        players.add(new Player());
    }

    /* 공유패 추가 */
    public void addCommunityCard(int n, char s) {
        if (cardDeck.isThere(n, s))
            communutyCards.add(cardDeck.removeCard(n, s));
    }

    /* 공유패 제거 */
    public void removeCommunityCard(int n, char s) {
        for (int i = 0; i < communutyCards.size(); i++) {
            if (communutyCards.get(i).getNumber() == n)
                if (communutyCards.get(i).getShape() == s)
                    cardDeck.addCard(communutyCards.remove(i));
        }
    }

    /* 플레이어 카드 추가
     *  p : 플레이어 번호, 0은 사용자 */
    public void addPlayerHand(int p, int n, char s) {
        if (cardDeck.isThere(n, s))
            players.get(p).pushHand(cardDeck.removeCard(n, s));
    }

    /* 플레이어 카드 제거 */
    public void removePlayerHand(int p, int n, char s) {
        if (players.get(p).isThere(n, s))
            cardDeck.addCard(players.get(p).removeHand(n, s));
    }

    /* 플레이어 수 설정 */
    public void setNumberOfPlayer(Scanner scanner){
        System.out.print("플레이어의 수를 입력하세요 >> ");
        scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        numberOfPlayer = num;
    }
    /* 실행 */
    public void run() {
        int input = 0;
        boolean flag = false;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            clear();
            printMenu();
            input = scanner.nextInt();

            char shape = ' ';
            int num = 0;
            switch (input) {
                case 1:
                    System.out.print("(s = ♠), (h = ♥), (d = ◆), (c = ♣) >> ");
                    shape = scanner.next().charAt(0);
                    System.out.print("(2, 3, 4, 5, 6, 7, 8, 9, 10, <11 = J>, <12 = Q>, <13 = K>, <14 = A> >>");
                    num = scanner.nextInt();
                    addPlayerHand(0, num, shape);
                    break;
                case 2:
                    System.out.print("(s = ♠), (h = ♥), (d = ◆), (c = ♣) >> ");
                    shape = scanner.next().charAt(0);
                    System.out.print("(2, 3, 4, 5, 6, 7, 8, 9, 10, <11 = J>, <12 = Q>, <13 = K>, <14 = A> >>");
                    num = scanner.nextInt();
                    removePlayerHand(0, num, shape);
                    break;
                case 3:
                    System.out.print("(s = ♠), (h = ♥), (d = ◆), (c = ♣) >> ");
                    shape = scanner.next().charAt(0);
                    System.out.print("(2, 3, 4, 5, 6, 7, 8, 9, 10, <11 = J>, <12 = Q>, <13 = K>, <14 = A> >>");
                    num = scanner.nextInt();
                    addCommunityCard(num, shape);
                    break;
                case 4:
                    System.out.print("(s = ♠), (h = ♥), (d = ◆), (c = ♣) >> ");
                    shape = scanner.next().charAt(0);
                    System.out.print("(2, 3, 4, 5, 6, 7, 8, 9, 10, <11 = J>, <12 = Q>, <13 = K>, <14 = A> >>");
                    num = scanner.nextInt();
                    removeCommunityCard(num, shape);
                    break;
                case 5:
                    setNumberOfPlayer(scanner);
                    break;
                case 6:
                    flag = true;
                    break;
                default:
                    System.out.println("유효한 값을 입력하십시오");
                    break;
            }
            if(flag)
                break;
        }
    }

    /* 화면 비우기 */
    public void clear() {
        for (int i = 0; i < 80; i++) {
            System.out.println();
        }
    }

    /* 출력 */
    public void printMenu() {
        System.out.print("현재 패 정보 : ");

        LinkedList<Card> temp = new LinkedList<>();
        temp.addAll(players.get(0).getHands());
        temp.addAll(communutyCards);
        oddCalculator.whatCombination(temp);

        System.out.println("|    hand    ||            Community            |");
        LinkedList<Card> playerHands = players.get(0).getHands();
        LinkedList<Card> communityCards = communutyCards;
        temp = new LinkedList<>();
        /* 카드리스트 생성하기 */
        temp.addAll(playerHands);
        for (int i = temp.size(); i < 2; i++) {
            temp.add(cardDeck.getNullCard());
        }
        temp.addAll(communityCards);
        for (int i = temp.size(); i < 7; i++) {
            temp.add(cardDeck.getNullCard());
        }
        /* 첫째줄 그리기 */
        for (Card card : temp) {
            card.drawLine();
        }
        System.out.println();
        /* 문자줄 그리기 */
        for (Card card : temp) {
            card.drawShapeLine();
        }
        System.out.println();
        /* 숫자줄 그리기 */
        for (Card card : temp) {
            card.drawNumberLine();
        }
        System.out.println();
        /* 마지막 줄 그리기 */
        for (Card card : temp) {
            card.drawLine();
        }

        System.out.println();
        System.out.println("*=================================== 수행할 동작을 선택하세요 ==================================*");
        System.out.println("|1 : 손패추가, 2 : 손패제거, 3 : 공유패추가, 4 : 공유패제거, 5 : 플레이어 수 설정, 6 : 프로그램 초기화|");
        System.out.println("*============================================================================================*");
        System.out.print(">> ");
    }
}

/* 플레이어이다 */
class Player {
    private LinkedList<Card> hands;

    /* 생성자 */
    public Player() {
        hands = new LinkedList<>();
    }

    /* 핸드에 카드 집어넣기 */
    public void pushHand(Card card) {
        hands.addLast(card);
    }

    /* 핸드에서 카드 빼기 */
    public Card removeHand(int n, char s) {
        for (int i = 0; i < hands.size(); i++) {
            if (hands.get(i).getNumber() == n)
                if (hands.get(i).getShape() == s)
                    return hands.remove(i);
        }
        return null;
    }

    /* 핸드 카드 숫자 */
    public int size() {
        return hands.size();
    }

    /* 핸드에 이 카드가 있는가? */
    public boolean isThere(int n, char s) {
        for (int i = 0; i < hands.size(); i++) {
            if (hands.get(i).getNumber() == n)
                if (hands.get(i).getShape() == s)
                    return true;
        }
        return false;
    }

    /* hands 반환 */
    public LinkedList<Card> getHands() {
        return hands;
    }
}

/* 덱 이다 */
class Deck {
    private LinkedList<Card> deck;
    private int cardCount;

    public Deck() {
        cardCount = 0;
        resetDeck();
    }

    /* 카드덱 리셋 */
    public void resetDeck() {
        /* 카드를 생성해 덱에 삽입한다. */
        deck = new LinkedList<>();
        char shape[] = {'s', 'd', 'h', 'c'};
        int number[] = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
        for (char s : shape) {
            for (int n : number) {
                deck.add(new Card(n, s));
            }
        }
        cardCount = deck.size();
    }

    /* 카드 추가하기 */
    public void addCard(Card card) {
        deck.add(card);
    }

    /* 카드 빼기 */
    public Card removeCard(int n, char s) {
        for (int i = 0; i < cardCount; i++) {
            if (deck.get(i).getShape() == s)
                if (deck.get(i).getNumber() == n)
                    return deck.remove(i);
        }
        return null;
    }

    /* 덱에 남은 카드 수 */
    public int size() {
        return deck.size();
    }

    /* 덱에 이 카드가 있는가? */
    public boolean isThere(int n, char s) {
        for (int i = 0; i < deck.size(); i++) {
            if (deck.get(i).getNumber() == n)
                if (deck.get(i).getShape() == s)
                    return true;
        }
        return false;
    }

    /* 빈 카드를 준다 */
    public Card getNullCard() {
        return new Card('0', ' ');
    }

}

/* 카드 정보를 담고있는 카드 클래스이다. */
class Card {
    /* 2 ~ 14, 14 = A */
    private int number;
    /* (s : ♠), (d : ◆), (h : ♥), (c : ♣) */
    private char shape;

    /* 생성자 */
    public Card(int number, char shape) {
        this.number = number;
        this.shape = shape;
    }

    /* 숫자를 반환 */
    public int getNumber() {
        return number;
    }

    /* 문양을 반환 */
    public char getShape() {
        return shape;
    }

    /* 상부, 하부를 그린다 */
    public void drawLine() {
        System.out.print("*-----*");
    }

    /* 문양 줄을 그린다. */
    public void drawShapeLine() {
        //if 문양 따라 그리기
        char s;
        switch (shape) {
            case 's':
                s = '♠';
                break;
            case 'h':
                s = '♥';
                break;
            case 'd':
                s = '◆';
                break;
            case 'c':
                s = '♣';
                break;
            default:
                s = ' ';
                break;
        }
        System.out.printf("|  %c  |", s);
    }

    /* 숫자 줄을 그린다 */
    public void drawNumberLine() {
        char n;
        switch (number) {
            case 14:
                n = 'A';
                break;
            case 13:
                n = 'K';
                break;
            case 12:
                n = 'Q';
                break;
            case 11:
                n = 'J';
                break;
            case 10:
                n = 'T';
                break;
            default:
                n = (char) (number + (int) '0');
                break;
        }
        System.out.printf("|  %c  |", n);
    }
    /*
    *-----*
    |  ♠  |
    |  T  |
    *-----*
     */
}

/* 확률 계산 클래스 */
class OddCalculator {
    /* 생성자 */
    public OddCalculator() {

    }

    /* 카드 리스트주면 검사한다 */
    public void whatCombination(LinkedList<Card> cardList) {
        if (isRoyalStraightFlush(cardList)) {
            System.out.println("현재 당신은 로얄 스트레이트 플러시입니다");
        } else if (isStraightFlush(cardList)) {
            System.out.println("현재 당신은 스트레이트 플러시입니다");
        } else if (isFourCard(cardList)) {
            System.out.println("현재 당신은 포카드입니다");
        } else if (isFullHouse(cardList)) {
            System.out.println("현재 당신은 풀하우스입니다");
        } else if (isFlush(cardList)) {
            System.out.println("현재 당신은 플러시입니다");
        } else if (isStraight(cardList)) {
            System.out.println("현재 당신은 스트레이트입니다");
        } else if (isTriple(cardList)) {
            System.out.println("현재 당신은 트리플입니다");
        } else if (isTwoPair(cardList)) {
            System.out.println("현재 당신은 투페어입니다");
        } else if (isOnePair(cardList)) {
            System.out.println("현재 당신은 원페어입니다");
        } else {
            System.out.println("현재 당신은 탑입니다");
        }
    }

    /* 로얄 스트레이트 플러시인가? */
    public boolean isRoyalStraightFlush(LinkedList<Card> cardList) {
        //A K Q J T 이면서 플러시라면 그렇다
        /* 우선 숫자 내림차순으로 정렬 */
        cardList.sort(new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                return Integer.compare(o2.getNumber(), o1.getNumber());
            }
        });

        int flag = 0;
        for (Card card : cardList) {
            /* A면 flag 1로 설정 */
            if (card.getNumber() == 14) {
                flag = 1;
                continue;
            }
            /* flag가 1이고 카드가 K면 flag 2로 설정 */
            else if (flag == 1 && card.getNumber() == 13) {
                flag = 2;
                continue;
            }
            /* flag가 2고 카드가 Q면 flag 3으로 설정 */
            else if (flag == 2 && card.getNumber() == 12) {
                flag = 3;
                continue;
            }
            /* flag가 3이고 카드가 J면 flag 4로 설정 */
            else if (flag == 3 && card.getNumber() == 11) {
                flag = 4;
                continue;
            }
            /* flag가 4이고 카드가 10이면 로얄 스트레이트 플러시이다. */
            else if (flag == 4 && card.getNumber() == 10) {
                flag = 5;
                return true;
            }
        }
        return false;
    }

    /* 스트레이트 플러시인가? */
    public boolean isStraightFlush(LinkedList<Card> cardList) {
        // 스트레이트 + 플러시 라면 그렇다
        if (isFlush(cardList) && isStraight(cardList))
            return true;
        else return false;
    }

    /* 포카드인가? */
    public boolean isFourCard(LinkedList<Card> cardList) {
        // 같은 숫자의 카드가 4장 존재하는가?
        /* 우선 숫자 내림차순으로 정렬 */
        cardList.sort(new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                return Integer.compare(o2.getNumber(), o1.getNumber());
            }
        });
        /* 같은 카드가 몇개인지 검색한다 */
        int flag = 0;
        int prevCard = -1;
        for (Card card : cardList) {
            if (prevCard == -1) {
                prevCard = card.getNumber();
                flag = 1;
                continue;
            }
            /* 같은 카드가 4개 존재한다면 포카드이다. */
            if (prevCard == card.getNumber()) {
                flag++;
                if (flag == 4)
                    return true;
            } else {
                prevCard = card.getNumber();
                flag = 1;
            }

        }
        return false;
    }

    /* 풀하우스인가? */
    public boolean isFullHouse(LinkedList<Card> cardList) {
        // 트리플 + 트리플에 사용된 숫자가 아닌 원페어 하나
        /* 우선 숫자 내림차순으로 정렬 */
        cardList.sort(new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                return Integer.compare(o2.getNumber(), o1.getNumber());
            }
        });
        /* 우선 트리플 검사 후 트리플이면 그 카드를 빼버린다.*/
        if (isTriple(cardList)) {
            int flag = 0;
            int prevCard = 0;
            int tripleIndex = 0;
            LinkedList<Card> temp = new LinkedList<>();
            temp.addAll(cardList);

            for (int i = 0; i < temp.size(); i++) {
                if (prevCard == 0) {
                    prevCard = temp.get(i).getNumber();
                    flag = 1;
                    continue;
                }
                if (temp.get(i).getNumber() == prevCard) {
                    flag++;
                } else {
                    prevCard = temp.get(i).getNumber();
                    flag = 1;
                }
                if (flag == 3) {
                    tripleIndex = i;
                }
            }

            /* 트리플 카드를 빼버린다 */
            for (int i = 0; i < 3; i++) {
                temp.remove(tripleIndex--);
            }
            /* 원페어도 있으면 풀하우스다 */
            if (isOnePair(temp))
                return true;
        }

        return false;
    }

    /* 플러시인가? */
    public boolean isFlush(LinkedList<Card> cardList) {
        // 같은 그림이 5장 존재하는가?
        /* 우선 그림 순으로 정렬 */
        cardList.sort(new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                return Character.compare(o1.getShape(), o2.getShape());
            }
        });

        /* 그림이 같은 카드가 몇개인지 검색한다 */
        int flag = 0;
        char prevCard = ' ';
        for (Card card : cardList) {
            if (prevCard == ' ') {
                prevCard = card.getShape();
                flag = 1;
                continue;
            } else if (prevCard == card.getShape()) {
                flag++;
            }
        }
        /* 같은 카드가 5개 이상 존재한다면 플러시이다. */
        if (flag > 4)
            return true;
        else return false;
    }

    /* 스트레이트인가? */
    public boolean isStraight(LinkedList<Card> cardList) {
        // 연속되는 숫자 5개가 존재하는가?
        /* 우선 숫자 내림차순으로 정렬 */
        cardList.sort(new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                return Integer.compare(o2.getNumber(), o1.getNumber());
            }
        });
        int flag = 0;
        int prevCard = -1;
        for (Card card : cardList) {
            if (prevCard == -1) {
                prevCard = card.getNumber();
                flag = 1;
                continue;
            }
            if (card.getNumber() == (prevCard - 1)) {
                flag++;
                prevCard = card.getNumber();
            } else {
                prevCard = card.getNumber();
                flag = 1;
            }
        }
        if (flag > 4)
            return true;
        else return false;
    }

    /* 트리플인가? */
    public boolean isTriple(LinkedList<Card> cardList) {
        //같은 숫자가 3장 존재하는가?
        /* 우선 숫자 내림차순으로 정렬 */
        cardList.sort(new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                return Integer.compare(o2.getNumber(), o1.getNumber());
            }
        });
        /* 같은 카드가 몇개인지 검색한다 */
        int flag = 0;
        int prevCard = -1;
        for (Card card : cardList) {
            if (prevCard == -1) {
                prevCard = card.getNumber();
                flag = 1;
                continue;
            }
            /* 같은 카드가 3개 존재한다면 트리플이다. */
            if (card.getNumber() == prevCard) {
                flag++;
                if (flag > 2)
                    return true;
            } else {
                prevCard = card.getNumber();
                flag = 1;
            }
        }
        return false;
    }

    /* 투페어인가? */
    public boolean isTwoPair(LinkedList<Card> cardList) {
        //다른 원페어가 2개 존재하는가?
        /* 우선 숫자 내림차순으로 정렬 */
        cardList.sort(new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                return Integer.compare(o2.getNumber(), o1.getNumber());
            }
        });
        int pair = 0;
        int prevCard = 0;
        for (Card card : cardList) {
            if (prevCard == 0) {
                prevCard = card.getNumber();
                continue;
            }

            if (prevCard == card.getNumber()) {
                pair++;
                prevCard = 0;
            } else {
                prevCard = card.getNumber();
            }
        }
        if (pair > 1)
            return true;
        else return false;
    }

    /* 원페어인가? */
    public boolean isOnePair(LinkedList<Card> cardList) {
        //같은 숫자가 2장 존재하는가?
        /* 우선 숫자 내림차순으로 정렬 */
        cardList.sort(new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                return Integer.compare(o2.getNumber(), o1.getNumber());
            }
        });
        int pair = 0;
        int prevCard = 0;
        for (Card card : cardList) {
            if (prevCard == 0) {
                prevCard = card.getNumber();
                continue;
            }

            if (prevCard == card.getNumber()) {
                pair++;
                prevCard = 0;
            } else {
                prevCard = card.getNumber();
            }
        }
        if (pair > 0)
            return true;
        else return false;
    }

    /* 숫자순으로 정렬 */
    public LinkedList<Card> sortByNumber(LinkedList<Card> cardList) {
        LinkedList<Card> sortedCardList = new LinkedList<>();
        sortedCardList.addAll(cardList);

        /* 숫자가 큰 걸 앞으로 보낸다. */
        sortedCardList.sort(new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                return Integer.compare(o2.getNumber(), o1.getNumber());
            }
        });

        return sortedCardList;
    }

    /* 그림 순으로 정렬 */
    public LinkedList<Card> sortByShape(LinkedList<Card> cardList) {
        LinkedList<Card> sortedCardList = new LinkedList<>();
        sortedCardList.addAll(cardList);

        /* 문자 순으로 정렬한다. */
        sortedCardList.sort(new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                return Character.compare(o1.getShape(), o2.getShape());
            }
        });

        return sortedCardList;
    }
}