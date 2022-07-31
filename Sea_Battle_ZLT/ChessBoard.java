package Sea_Battle_ZLT;

import javax.swing.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ChessBoard {
    // 棋盘格
    private final int[][] board;
    // 军舰的集合
    private final HashSet<ship> ships = new HashSet<>();
    // 剩余军舰总数
    private int number;
    /**
     * 生成一张9*9的棋盘格
     * 0表示没有军舰，1表示有军舰，军舰不可重叠，初始化全为0
     * 游戏时，-1表示打击到军舰，-2表示没有打击到军舰，-3表示打击到军舰的位置，
     * 未被打击的位置，按照军舰个数0，1原样显示
     */
    public ChessBoard(int number) {
        this.number = number;
        this.board = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
    }

    /**
     * 在棋盘上随机生成1艘船
     *
     * @param length 船的长度
     * @return 成功与否
     */
    public boolean GenerateBoat(int length) {
        boolean flag = false;// 能放下的标志
        int count = 10;// 防止一次不成功，允许生成的最大次数
        while(!flag&&count>0){
            boolean occupied = false;// 位置是否被其他船占用
            count--;
            long seed = System.nanoTime();// 随机数种子
            Random r = new Random(seed);
            int x = r.nextInt(9);// 行坐标，从0开始，由上至下
            int y = r.nextInt(9);// 列坐标，从0开始，由左至右
            // 只考虑大小，不考虑是否有其他船，竖着能放下
            if (x + length <= 9) {
                int i = x;
                for (; i < x + length; i++) {
                    // 有其他船，不能重叠放置
                    if(board[i][y]==1){
                        occupied = true;
                        break;
                    }
                }
                // 位置没有被别的船占用
                if(!occupied){
                    for (i = x; i < x + length; i++) {
                        board[i][y] = 1;
                    }
                    ship s = new ship(x,y,x+length-1,y,length);
                    ships.add(s);
                    flag = true;
                }
            }
            // 只考虑大小，不考虑是否有其他船，竖着放不下
            else if (x + length > 9) {
                // 只考虑大小，不考虑是否有其他船，横着能放下
                if (y + length < 9) {
                    int i = y;
                    // 有其他船，不能重叠放置
                    for (; i < y + length; i++) {
                        if(board[x][i]==1){
                            occupied = true;
                            break;
                        }
                    }
                    if(!occupied){
                        for (i = y; i < y + length; i++) {
                            board[x][i] = 1;
                        }
                        ship s = new ship(x,y,x,y+length-1,length);
                        ships.add(s);
                        flag = true;
                    }
                }
                // 只考虑大小，不考虑是否有其他船，横着竖着都放不下
                else {}
            }

        }
        return flag;
    }

    /**
     * 玩家通过输入行列坐标进行打击
     * @param x 行坐标，从0开始，由上至下
     * @param y 列坐标，从0开始，由左至右
     * @return 游戏结束或输入错误
     */
    public int play(int x,int y){
        if(x<0||x>8||y<0||y>8){
            System.out.println("输入范围不正确");
            return -1;
        }
        int pre_status = board[x][y];
        // 击中军舰
        if(pre_status == 1){
        	// 对被击中的军舰做标记
            for(ship s:ships){
                if(s.isHit(x,y)){
                    int start_x = s.getStart_x();
                    int start_y = s.getStart_y();
                    int end_x = s.getEnd_x();
                    int end_y = s.getEnd_y();
                    for(int i=start_x;i<=end_x;i++){
                        for(int j=start_y;j<=end_y;j++){
                            board[i][j] = -1;
                        }
                    }
                    break;
                }
            }
            // 标记击中的位置
            board[x][y] = -3;
            number = number-1;
            if(number == 0){
                // 标记击中的位置
//                board[x][y] = -3;
                System.out.println("恭喜！您击中了全部军舰");
                return 0;
            }
            else{
                System.out.println("====================恭喜！您击中了1艘军舰，还有"+number+"艘====================");
            }
            
        }
        // 击中军舰，但之前击中过
        else if(pre_status == -1||pre_status == -3){
            System.out.println("====================您多次击中同一艘军舰，还有"+number+"艘====================");
        }
        // 没有击中任何军舰
        else if(pre_status == 0){
            board[x][y] = -2;
            System.out.println("====================没有击中，还有"+number+"艘====================");
        }
        return 1;
    }
    /**
     * 游戏时显示棋盘，*表示尚未打击到的位置，+表示打击到且打中军舰，被击中的军舰会显示为#，-表示打击到但没有打中军舰
     */
    public void showWhenPlay() {
        System.out.print("\t");
        for (int i = 0; i < 9; i++) {
            System.out.print(i + "\t");
        }
        System.out.print("\n");
        for (int i = 0; i < 9; i++) {
            System.out.print(i+"\t");
            for (int j = 0; j < 9; j++) {
                int status = board[i][j];
                // 尚未打击到的位置
                if (status >= 0) {
                    System.out.print("*\t");
                }
                // 被击中的军舰
                else if(status == -1){
                    System.out.print("#\t");
                }
                // 打击到但没有打中军舰
                else if (status == -2) {
                    System.out.print("-\t");
                }
                // 打击到且打中军舰
                else if (status == -3) {
                    System.out.print("+\t");
                }
            }
            System.out.print("\n");
        }
    }

    /**
     * 游戏结束时显示棋盘，+、-是打击到的位置，被击中的军舰会显示为#，其他是未打击的位置
     */
    public void showFinally() {
        System.out.print("\t");
        for (int i = 0; i < 9; i++) {
            System.out.print(i + "\t");
        }
        System.out.print("\n");
        for (int i = 0; i < 9; i++) {
            System.out.print(i+"\t");
            for (int j = 0; j < 9; j++) {
                int status = board[i][j];
                // 尚未打击到的位置
                if (status >= 0) {
                    System.out.print(status+"\t");
                }
                // 被击中的军舰
                else if(status == -1){
                    System.out.print("#\t");
                }
                // 打击到但没有打中军舰
                else if (status == -2) {
                    System.out.print("-\t");
                }
                // 打击到且打中军舰
                else if (status == -3) {
                    System.out.print("+\t");
                }
            }
            System.out.print("\n");
        }
    }
}
