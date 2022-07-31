package Sea_Battle_ZLT;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int number = 0;
        int count = 0;
        int flag = 1;
        Scanner input = new Scanner(System.in);
        System.out.println("请输入军舰个数");
        number = input.nextInt();
        ChessBoard CB = new ChessBoard(number);
        for(int i=1;i<=number;i++){
            System.out.println("请输入第"+i+"艘军舰的长度");
            int length = input.nextInt();
            boolean success = CB.GenerateBoat(length);
            if(!success){
                i--;
                System.out.println("当前生成的随机位置无法放置该长度的军舰，请重试");
            }
        }
        System.out.println("====================游戏开始=====================");
        while(flag == 1){
            CB.showWhenPlay();
            System.out.println("被击中的军舰会显示为#，*表示尚未打击到的位置，+表示打击到军舰的位置，-表示打击到但没有打中军舰");
            System.out.println("请输入行坐标,从0开始，由上至下(0~8)");
            int x = input.nextInt();
            System.out.println("请输入列坐标,从0开始，右左至右(0~8)");
            int y = input.nextInt();
            flag = CB.play(x,y);
            if(flag == -1){
                System.out.println("本次打击无效！");
                flag = 1;
            }
            count++;
            if(count==25||count==35){
                System.out.println("您是否需要显示答案？是1否0");
                int needAnswer = input.nextInt();
                if(needAnswer == 1){
                    System.out.println("被击中的军舰会显示为#，+、-是打击到的位置，其他是未打击的位置，" +
                            "1表示军舰，0表示没有军舰");
                    CB.showFinally();
                    System.out.println("====================游戏结束=====================");
                    return;
                }
            }
        }
        System.out.println("被击中的军舰会显示为#，+、-是打击到的位置，其他是未打击的位置，"+"1表示军舰，0表示没有军舰");
        CB.showFinally();
        System.out.println("====================游戏结束=====================");
    }
}