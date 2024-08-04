//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import org.w3c.dom.ls.LSOutput;

import java.util.Scanner;
import java.util.Random;
import java.lang.Thread;

public class Main {
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    public static void main(String[] args) {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        String[][] board = new String[5][5];
        int[] numbers = new int[9];
        int player_score = 0, computer_score = 0;
        for (int a = 0; a < 3; a++) {
            CreateBoard(board, numbers);
            int board_score = isSequential(board);
            do {
                CreateBoard(board, numbers);
                board_score = isSequential(board);
            } while (board_score != 0);

            int turn = 1;
            while (board_score == 0) {
                board_score = isSequential(board);

                display(board, turn, board_score, player_score, computer_score, a);
                sleep();
                System.out.println("Human Player... Choose your move: a,b,c,d,e or f");
                String choice = scanner.nextLine().toLowerCase();
                System.out.println("Command : " + choice);
                System.out.println("---------------------------------------------------------------------------------------");
                char ascii = choice.charAt(0);
                if (!((int) ascii <= 102 && (int) ascii >= 97)) {
                    while (!((int) ascii <= 102 && (int) ascii >= 97)) {
                        System.out.println("Please choose your move one of a,b,c,d,e or f");
                        choice = scanner.nextLine().toLowerCase();
                        ascii = choice.charAt(0);
                    }
                }
                movement(board, choice);

                board_score = isSequential(board);

                if (board_score > 0 && turn % 2 == 1) {   // if human player makes a sequential
                    player_score += board_score;
                    display(board, turn, board_score, player_score, computer_score, a);
                    sleep();
                    System.out.println("END OF THE ROUND");
                    System.out.println("---------------------------------------------------------------------------------------");
                    board_score = 0;
                    turn = 1;
                    break;
                } else if (board_score == 0) {
                    turn++;
                }
                System.out.println("Computer's doing its movement");
                display(board, turn, board_score, player_score, computer_score, a);
                sleep();
                char computer_choice_number = (char) random.nextInt(97, 103);
                String computer_choice = String.valueOf(computer_choice_number);
                System.out.println("Command : " + computer_choice);
                System.out.println("---------------------------------------------------------------------------------------");

                movement(board, computer_choice);
                board_score = isSequential(board);
                if (board_score > 0&&turn%2==0) {    // if Computer makes a sequential
                    computer_score += board_score;
                    display(board, turn, board_score, player_score, computer_score, a);
                    sleep();
                    System.out.println("END OF THE ROUND");
                    System.out.println("---------------------------------------------------------------------------------------");
                    board_score = 0;
                    turn = 1;
                    break;
                } else if (board_score == 0) {
                    turn++;
                }

            }
        }
        if(player_score>computer_score)
        {
            System.out.println("Human player's score : "+GREEN+ player_score+RESET);
            System.out.println("Computer's score : "+ RED+computer_score+RESET);
            System.out.println(YELLOW+"Congratulations. You won the game."+RESET);
        }
        else
        {
            System.out.println("Human player's score : "+GREEN+ player_score+RESET);
            System.out.println("Computer's score : "+ RED+computer_score+RESET);
            System.out.println(YELLOW+"Sorry !!! Try for another challenge."+RESET);
        }

    }


    public static void display(String[][] Pboard, int Pturn, int Pboard_score, int Pplayer_score, int Pcomputer_score, int a) {
        System.out.println(BLUE+"Round : " + (a + 1)+RESET);
        for (int i = 0; i < Pboard[0].length; i++) {
            for (int j = 0; j < Pboard[1].length; j++) {
                if((int)Pboard[i][j].charAt(0)>=49&&(int)Pboard[i][j].charAt(0)<=57)
                {System.out.print(GREEN+Pboard[i][j]+RESET);}
                else
                {System.out.print(CYAN+Pboard[i][j]+RESET);}
            }
            if (i == 0 && Pturn % 2 == 0) {
                System.out.println("          Turn: " + Pturn + RED+"/Computer"+RESET);
            } else if (i == 0 && Pturn % 2 == 1) {
                System.out.println("          Turn: " + Pturn +BLUE+ "/Human"+RESET);
            } else if (i == 1) {
                System.out.println("          Board Score : " + Pboard_score);
            } else if (i == 2) {
                System.out.println(BLUE+"          Player Score : " +RESET+ Pplayer_score);
            } else if (i == 3) {
                System.out.println(RED+"          Computer Score : " +RESET+ Pcomputer_score);
            } else {
                System.out.println();
            }
        }
    }


    public static void CreateBoard(String[][] board, int[] numbers) {
        Random random = new Random();
        for (int i = 0; i < 9; i++) {
            numbers[i] = (i + 1);
        }
        int letter = 97;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if ((i == 0 && j == 0) || (i == 0 && j == 4) || (i == 4 && j == 0) || (i == 4 && j == 4)) {
                    board[i][j] = "+";
                } else if ((i == 1 && j == 0) || (i == 2 && j == 0) || (i == 3 && j == 0) || (i == 0 && j == 1) || (i == 0 && j == 2) || (i == 0 && j == 3)) {
                    board[i][j] = String.valueOf((char) letter);
                    letter++;
                } else if (i == 0 || i == 4 || j == 0 || j == 4) {
                    board[i][j] = "-";
                } else {
                    int number;
                    do {
                        number = random.nextInt(0, 9);
                    }
                    while (numbers[number] == 0);
                    board[i][j] = String.valueOf(numbers[number]);
                    numbers[number] = 0;
                }
            }
        }
    }

    public static void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void movement(String[][] Pboard, String choice) {
        switch (choice) {
            case "a": {
                String temp = Pboard[2][1];
                Pboard[2][1] = Pboard[1][1];
                Pboard[1][1] = Pboard[3][1];
                Pboard[3][1] = temp;
                break;
            }
            case "b": {
                String temp = Pboard[2][2];
                Pboard[2][2] = Pboard[1][2];
                Pboard[1][2] = Pboard[3][2];
                Pboard[3][2] = temp;
                break;
            }
            case "c": {
                String temp = Pboard[2][3];
                Pboard[2][3] = Pboard[1][3];
                Pboard[1][3] = Pboard[3][3];
                Pboard[3][3] = temp;
                break;
            }
            case "d": {
                String temp = Pboard[1][2];
                Pboard[1][2] = Pboard[1][1];
                Pboard[1][1] = Pboard[1][3];
                Pboard[1][3] = temp;
                break;
            }
            case "e": {
                String temp = Pboard[2][2];
                Pboard[2][2] = Pboard[2][1];
                Pboard[2][1] = Pboard[2][3];
                Pboard[2][3] = temp;
                break;
            }
            case "f": {
                String temp = Pboard[3][2];
                Pboard[3][2] = Pboard[3][1];
                Pboard[3][1] = Pboard[3][3];
                Pboard[3][3] = temp;
                break;
            }
        }
    }

    public static int isSequential(String[][] x) {
        int result = 0;
        for (int i = 1; i <= 3; i++) {
            int j = 1;
            char first = x[i][j].charAt(0), second = x[i][j + 1].charAt(0);
            char third = x[i][j + 2].charAt(0);
            if (Math.abs((int) first - (int) second) == 1 && Math.abs((int) second - (int) third) == 1) {
                result++;
            }
        }
        for (int i = 1; i <= 3; i++) {
            int j = 1;
            char first = x[j][i].charAt(0), second = x[j + 1][i].charAt(0);
            char third = x[j + 2][i].charAt(0);
            if (Math.abs((int) first - (int) second) == 1 && Math.abs((int) second - (int) third) == 1) {
                result++;
            }
        }
        char first = x[1][1].charAt(0), second = x[2][2].charAt(0), third = x[3][3].charAt(0);
        if (Math.abs((int) first - (int) second) == 1 && Math.abs((int) second - (int) third) == 1) result++;
        first = x[1][3].charAt(0);
        second = x[2][2].charAt(0);
        third = x[3][1].charAt(0);
        if (Math.abs((int) first - (int) second) == 1 && Math.abs((int) second - (int) third) == 1) result++;
        return (int)Math.pow(result,2);
    }
}