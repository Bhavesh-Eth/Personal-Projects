import java.util.*;

public class tictactoe {
    static ArrayList<Integer> playerposition=new ArrayList<Integer>();
    static ArrayList<Integer> pcposition=new ArrayList<Integer>();
    public static void main(String[] args) {
        char[][] gameboard = {{' ', '|', ' ', '|', ' '},
                {'-', '*', '-', '*', '-'},
                {' ', '|', ' ', '|', ' '},
                {'-', '*', '-', '*', '-'},
                {' ', '|', ' ', '|', ' '}};
        printgameboard(gameboard);

        while(true){
            Scanner s = new Scanner(System.in);
            System.out.println("0-9");
            int playerpos = s.nextInt();
            while(playerposition.contains(playerpos) || pcposition.contains(playerpos)){
                System.out.println(playerpos+" is Position taken");
                playerpos=s.nextInt();
            }
            placepiece(gameboard, playerpos, "player");

            String result=winner();
            if(result.length()>0){
                System.out.println(result);
                break;
            }

            Random r=new Random();
            int pcpos=r.nextInt(9)+1;
            while(pcposition.contains(pcpos) || playerposition.contains(pcpos)){
                pcpos=r.nextInt(9)+1;
            }
            placepiece(gameboard,pcpos,"pc");

            printgameboard(gameboard);

            result=winner();
            if(result.length()>0){
                System.out.println(result);
                break;
            }
        }
    }

    public static void printgameboard(char[][] gameboard) {
        for (char[] row : gameboard) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    public static void placepiece(char[][] gameboard, int pos, String user) {
        char symbol = ' ';
        if (user.equals("player")) {
            symbol = 'X';
            playerposition.add(pos);
        } else if (user.equals("pc")) {
            symbol = 'O';
            pcposition.add(pos);
        }
        switch (pos) {
            case 1 -> gameboard[0][0] = symbol;
            case 2 -> gameboard[0][2] = symbol;
            case 3 -> gameboard[0][4] = symbol;
            case 4 -> gameboard[2][0] = symbol;
            case 5 -> gameboard[2][2] = symbol;
            case 6 -> gameboard[2][4] = symbol;
            case 7 -> gameboard[4][0] = symbol;
            case 8 -> gameboard[4][2] = symbol;
            case 9 -> gameboard[4][4] = symbol;
            default -> {}
        }
    }
    public static String winner(){
        List toprow=Arrays.asList(1,2,3);
        List midrow=Arrays.asList(4,5,6);
        List botrow=Arrays.asList(7,8,9);
        List leftcol=Arrays.asList(1,4,7);
        List midcol=Arrays.asList(2,5,8);
        List rightcol=Arrays.asList(3,6,9);
        List diag1=Arrays.asList(1,5,9);
        List diag2=Arrays.asList(3,5,7);

        List<List> winning=new ArrayList<List>();
        winning.add(toprow);
        winning.add(midrow);
        winning.add(botrow);
        winning.add(leftcol);
        winning.add(midcol);
        winning.add(rightcol);
        winning.add(diag1);
        winning.add(diag2);

        for(List l: winning){
            if(playerposition.containsAll(l)){
                return "PLAYER WON";
            } else if (pcposition.containsAll(l)){
                return "PC WON";
            }
            else if(playerposition.size()+pcposition.size()==9){
                return "DRAW";
            }
        }

        return "";
    }
}
