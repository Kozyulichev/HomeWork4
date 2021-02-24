import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class HomeWokr4 {
    static final char DOT_EMPTY = '*';
    static final char DOT_HUMAN = '#';
    static final char DOT_AI = '$';
    static final char HEADER_FIRST_SYMBOL = '@';
    static final String EMPTY = " ";
    public static char[][] map;
    public static int simsForVictory;
    static final Scanner in = new Scanner(System.in);
    static final Random random = new Random();
    static int size;
    static int turnsCount;


    public static void main(String[] args) {
        playGame();
    }

    private static void playGame() {
        sizeOfGame();
        initMap();
        printMap();
        play();
    }

    private static void sizeOfGame() {
        do {
            System.out.print("Введите размер игрового поля от 3х до 9");//больше не делаю, потому что начинает плыть
            size = in.nextInt();
        } while (size < 3 || size > 9);
        do {
            System.out.println("Сколько символов в ряд должно быть для победы от 3х");
            simsForVictory = in.nextInt();
        } while (simsForVictory < 3 || simsForVictory > size);


    }

    private static void initMap() {
        map = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }

    private static void printMap() {
        printHead();
        printBody();

    }

    private static void printHead() {
        System.out.print(HEADER_FIRST_SYMBOL + EMPTY);
        for (int i = 0; i < size; i++) {
            System.out.print(i + 1 + EMPTY);
        }
        System.out.println();
    }

    private static void printBody() {
        for (int i = 0; i < size; i++) {
            System.out.print((i + 1) + EMPTY);
            for (int j = 0; j < size; j++) {
                System.out.print(map[i][j] + EMPTY);
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void play() {
        turnsCount = 0;
        while (true) {
            humanTurn();
            printMap();
            checkEnd(DOT_HUMAN);

            turnAI();
            printMap();
            checkEnd(DOT_AI);
        }
    }

    private static void humanTurn() {
        int column;
        int line;

        do {
            System.out.println("Выбери полоску");
            column = in.nextInt() - 1;
            System.out.println("Выбери колонку");
            line = in.nextInt() - 1;
        } while (!isInputValid(column, line));
        map[column][line] = DOT_HUMAN;
        turnsCount++;
    }

    public static boolean isInputValid(int column, int line) {
        if (column < 0 || column >= size || line < 0 || line >= size)
            return false;
        return map[column][line] == DOT_EMPTY;
    }

    private static void checkEnd(char symbol) {
        if (checkWin(symbol)) {
            if (symbol == DOT_HUMAN)
                System.out.println("Человек выйграл");
            else System.out.println("Компьютер выйграл");
            System.exit(0);
        } else if (isMapFull()) {
            System.out.println("Ничья!");
            System.exit(0);
        }
    }

    private static boolean checkWin(char symbol) {
        for (int i = 0; i < size - simsForVictory + 1; i++) {
            for (int j = 0; j < size - simsForVictory + 1; j++) {
                if (checkDiagonal(symbol, i, j) || checkLines(symbol, i, j)) return true;
            }
        }
        return false;

    }


    private static boolean checkDiagonal(char symbol, int column, int line) {
        boolean toRight, toLeft;
        toRight = true;
        toLeft = true;
        for (int i = 0; i < simsForVictory; i++) {
            toRight &= (map[i + column][i + line] == symbol);
            toLeft &= (map[simsForVictory - i - 1 + column][i + line] == symbol);
        }

        return toRight || toLeft;
    }

    private static boolean checkLines(char symbol, int column, int line) {
        boolean cols, rows;
        for (int col = column; col < simsForVictory + column; col++) {
            cols = true;
            rows = true;
            for (int row = line; row < simsForVictory + line; row++) {
                cols &= (map[col][row] == symbol);
                rows &= (map[row][col] == symbol);
            }

            if (cols || rows) return true;
        }

        return false;
    }

    private static void turnAI() {
        int column;
        int line;

        System.out.println("\nХод компьютера!");
        do {


            column = random.nextInt(size);
            line = random.nextInt(size);
        } while (!isCellOccupancy(column, line));

        map[column][line] = DOT_AI;
        turnsCount++;

    }

    private static boolean isCellOccupancy(int column, int line) {
        return map[column][line] == DOT_EMPTY;
    }

    private static boolean isMapFull() {
        return turnsCount == size * size;


    }
}

