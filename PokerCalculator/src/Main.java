import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /* 카드 그리기
         * 카드 정보를 리스트에 저장
         * 돌아가며 카드객체의 첫 줄 그리기를 실행
         * 돌아가며 카드객체의 2 줄 그리기 실행
         * ...
         * 완료
         */
        Scanner scanner = new Scanner(System.in);
        System.out.println("8. 승리확률 계산하기");
        System.out.println("-> 승리할 확률을 계산합니다.\n");
        System.out.println("현재 패 정보");
        System.out.println("|♥||♥|  |♥||♠||♥ ||♥| ");
        System.out.println("|A||Q|  |J||7||10||K| ");
        System.out.println("승리 확률 : 100%");
        System.out.println("플레이어 수 : 8 명");
        System.out.println("================ 수행할 동작을 선택하세요 ==============");
        System.out.println("1 : 손패추가, 2 : 손패제거, 3 : 공유패추가, 4 : 공유패제거, \n5 : 플레이어 수 설정, 6 : 가능한 조합보기, 7 : 주의할 조합보기, 8 : 승리확률 계산하기");
        System.out.println("=====================================================");
        System.out.print(">> ");
        scanner.nextLine();

        System.out.println("1 : 손패추가");
        System.out.println("문양을 선택하세요");
        System.out.print("1 : ♠, 2 : ◆, 3 : ♥, 4 : ♣");
        System.out.print(">> ");
        scanner.nextLine();
        System.out.print("숫자를 입력하세요 >> ");
        scanner.nextLine();

        System.out.println("7. 주의할 조합보기");
        System.out.println("현재 당신의 조합은 ♥로얄스트레이트플러시♡ 입니다.");
        System.out.println("주의할 조합은 없습니다. 올인을 하셔도 좋습니다.");
    }
}