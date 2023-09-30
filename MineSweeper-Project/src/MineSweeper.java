import java.util.Random;
import java.util.Scanner;

public class MineSweeper {
    int rowSize;    // Oyun tahtasının satır sayısı
    int colSize;    // Oyun tahtasının sütun sayısı
    char[][] board; // Oyun tahtası
    boolean[][] mines;  // Mayınların konumunu saklayan dizi
    boolean[][] revealed;   // Hangi hücrelerin açıldığını saklayan dizi
    boolean gameOver;   // Oyunun bitip bitmediğini kontrol eden bir değişken.

    // MineSweeper sınıfının kurucu metodu
    public MineSweeper(int rowSize, int colSize) {
        this.rowSize = rowSize; // Kullanıcının belirlediği satır sayısını alır.
        this.colSize = colSize; // Kullanıcının belirlediği sütun sayısını alır.
        this.board = new char[rowSize][colSize];    // Oyun tahtasını oluşturur.
        this.mines = new boolean[rowSize][colSize]; // Mayınların konumunu saklamak için kullanılır.
        this.revealed = new boolean[rowSize][colSize];  // Hangi hücrelerin açıldığını saklamak için kullanılır.
        this.gameOver = false;
        initializeBoard();  // Oyun tahtasını "-" karakteri ile başlatır.
        placeMines();   // Mayınları oyun tahtasına rastgele yerleştirir.
    }
    // Oyun tahtasını '-' karakteri ile dolduran metod
    public void initializeBoard() {
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                board[i][j] = '-';
            }
        }
    }
    // Mayınları rastgele yerleştiren metod
    //Değerlendirme Formu-8
    public void placeMines() {
        Random rand = new Random();
        int mineCount = ((rowSize * colSize) / 4);  // Oyun tahtasındaki hücre sayısının çeyreği kadar mayın yerleştirilir

        int minesPlaced = 0;

        while (minesPlaced < mineCount) {
            int row = rand.nextInt(rowSize);
            int col = rand.nextInt(colSize);

            if (!mines[row][col]) {
                mines[row][col] = true; // Mayını yerleştirir
                minesPlaced++;
            }
        }
    }
    // Oyun tahtasını ekrana basan metod
    public void printBoard(boolean showMines) {
        System.out.println("Mayın Tarlası Oyunu");
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                if (showMines && mines[i][j]) {
                    System.out.print("* "); // Mayınları gösterir
                } else if (!revealed[i][j]) {
                    System.out.print("- "); // Henüz açılmamış hücreleri gösterir
                } else {
                    System.out.print(board[i][j] + " ");    // Açılan hücreleri gösterir
                }
            }
            System.out.println();
        }
    }
    // Kullanıcının girdiği koordinatların geçerli olup olmadığını kontrol eden metod
    boolean isValidCoordinate(int row, int col) {
        return row >= 0 && row < rowSize && col >= 0 && col < colSize;
    }
    // Belirli bir hücreyi açan metod
    public void revealCell(int row, int col) {
        if (isValidCoordinate(row, col) && !revealed[row][col]) {
            revealed[row][col] = true;  // Hücreyi açar
            if (mines[row][col]) {  //Değerlendirme Formu-13
                gameOver = true;
                System.out.println("Mayına bastınız! Oyunu kaybettiniz.");
            } else {
                //Değerlendirme Formu-12
                int adjacentMines = countAdjacentMines(row, col);   // Komşu hücrelerdeki mayınları sayar
                if (adjacentMines == 0) {
                    for (int y = -1; y <= 1; y++) {
                        for (int x = -1; x <= 1; x++) {
                            revealCell(row + y, col + x);   // Eğer komşu hücrede mayın yoksa, o hücreyi açar
                        }
                    }
                } else {
                    board[row][col] = (char) ('0' + adjacentMines); // Komşu hücrelerdeki mayın sayısını tahtada gösterir
                }
            }
        }
    }
    // Belirli bir hücredeki komşu mayınları sayan metod
    public int countAdjacentMines(int row, int col) {
        int count = 0;
        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {
                int newRow = row + y;
                int newCol = col + x;
                if (isValidCoordinate(newRow, newCol) && mines[newRow][newCol]) {
                    count++;
                }
            }
        }
        return count;
    }
    // Oyunu başlatan ve kontrol eden metod
    public void play() {
        Scanner scanner = new Scanner(System.in);

        // Oyun başlamadan önce mayınları gösterir
        printBoard(true);
        System.out.println("Mayınlar (* işareti ile gösteriliyor)");

        while (!gameOver) {
            printBoard(false);
            //Değerlendirme Formu-9 Kullanıcıdan İşaretlemek İstediği Satır Sütun Bilgisi Alınır.
            System.out.print("Satır giriniz: ");
            int row = scanner.nextInt();

            System.out.print("Sütun giriniz: ");
            int col = scanner.nextInt();
            //Değerlendirme Formu -10
            if (!isValidCoordinate(row, col) || revealed[row][col]) {
                System.out.println("Geçersiz koordinat veya zaten açılmış hücre. Lütfen tekrar girin.");
                continue;
            }

            revealCell(row, col);

            if (isGameWon()) {
                gameOver = true;
                printBoard(false);
                System.out.println("Tebrikler! Oyunu kazandınız.Hiç Mayına Basmadınız!!");  //Değerlendirme Formu -15
            }
        }
    }
    // Oyunun kazanılıp kazanılmadığını kontrol eden metod
    //Değerlendirme Formu -14
    boolean isGameWon() {
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                if (!mines[i][j] && !revealed[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
