import java.util.Scanner;
import java.io.File;
import java.io.*;
public class Assignment6 {
    public static class ColumnFull extends Exception {
        ColumnFull(String a) {
            super(a);
        }
    }
    public static class ConnectFour {
        Scanner sc = new Scanner(System.in);
        char[][] array = new char[6][7];
        String turn;
        char nextToken;

        ConnectFour() {
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) {
                    array[i][j] = ' ';
                }
            }
            turn = "Red";
            nextToken = 'R';
        }

        public void nextTurn() {
            if (turn.equals("Red")) {
                turn = "Yellow";
                nextToken = 'Y';
            } else if (turn.equals("Yellow")) {
                turn = "Red";
                nextToken = 'R';
            }
        }

        public int nextAvailablePosition(int colNum) { //returns row in the given col

            for (int i = 5; i >= 0; i--) {
                if (array[i][colNum] == ' ') {
                    return i;
                }
            }
            return -1;
        }

        public void dropPiece(int column, char token) throws Exception {
            int row = nextAvailablePosition(column);
            if (row == -1) {
                throw new ColumnFull("That column is full try again");
            }
            array[row][column] = token;
        }

        public String toString() {
            String to_return = "  0   1   2   3   4   5   6";
            for (int i = 0; i < 6; i++) {
                to_return += "\n-----------------------------\n";
                to_return += "| ";
                for (int j = 0; j < 7; j++) {
                    to_return += array[i][j] + " | ";
                }
            }
            to_return += "\n-----------------------------\n";
            return to_return;
        }

        public void saveGame() {
            try {
                System.out.println("Enter a filename: ");
                String fileName = sc.nextLine();
                File saveFile = new File(fileName);
                PrintWriter pw = new PrintWriter(saveFile);
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 7; j++) {
                        pw.print(array[i][j]);
                        if (j != 6) {
                            pw.print(",");
                        }
                    }
                    pw.println();
                }
                pw.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        public void loadGame() {
            try {
                System.out.println("Enter a filename: ");
                String fileName = sc.nextLine();
                File file = new File(fileName);
                Scanner loadScan = new Scanner(file);
                for (int i = 0; i<6;i++){
                    for (int j = 0; j<7;j++){
                        array [i][j] = ' ';
                    }
                }
                int row = 0;

                while (loadScan.hasNextLine()) {
                    String line = loadScan.nextLine();
                    String[] characters = line.split(",");
                    for (int col = 0; col < 7; col++) {
                        array[row][col] = characters[col].charAt(0);
                    }
                    row++;
                }
                loadScan.close();
            }
            catch (FileNotFoundException e) {
                System.out.println("Unable to read that file");
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        public boolean checkWin(char token){

            for (int row = 0; row <6;row++){
                for (int col = 0; col<4;col++){
                    if (array[row][col]==token && array[row][col+1]==token && array[row][col+2]==token && array[row][col+3]==token){
                        return true;
                    }
                }
            }

            for (int col = 0; col<7;col++){
                for (int row = 0; row<3;row++){
                    if(array[row][col]==token && array[row+1][col]==token && array[row+2][col]==token && array[row+3][col]== token){
                        return true;
                    }
                }
            }

            for (int row = 3; row <6;row++){
                for (int col = 0; col <4;col++){
                    if(array[row][col]==token && array[row-1][col+1]==token && array[row-2][col+2]==token&& array[row-3][col+3]==token){
                        return true;
                    }
                }
            }

            for (int row = 0; row<3; row++){
                for (int col = 0; col<4;col++){
                    if (array[row][col]==token && array[row+1][col+1]==token && array[row+2][col+2]==token && array[row+2][col+2]==token){
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        ConnectFour connectFour = new ConnectFour();
        System.out.println(connectFour.toString());

        boolean quit = false;
        while (!quit) {
            System.out.println("What column would " + connectFour.turn + " like to go in (7 to save, 8 to load, 9 to quit)");
            int choice = sc.nextInt();
            if (choice>=0 && choice <=6) {
                try {
                    connectFour.dropPiece(choice, connectFour.nextToken);
                    if (connectFour.checkWin(connectFour.nextToken)){
                        System.out.println(connectFour.toString());
                        System.out.println(connectFour.turn+" wins!");
                        quit = true;
                    }
                    connectFour.nextTurn();
                } catch (ColumnFull e) {
                    System.out.println("That column is full try again");
                }
            }
            if (choice == 7) {
                connectFour.saveGame();
            }
            if (choice == 8) {
                connectFour.loadGame();
            }
            if (choice == 9) {
                quit = true;
            }
            System.out.println(connectFour.toString());
        }
    }
}


