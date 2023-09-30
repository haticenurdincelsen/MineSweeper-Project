import java.util.Scanner;
public class Main {
    public static void main(String[] args) {

        //Değelendirme Formu-7 Matris Boyutu Kullanıcı Tarafından Belirlenir.
        Scanner info=new Scanner(System.in);
        int row,col;
        System.out.print("Satır giriniz: ");
        row=info.nextInt();
        System.out.print("Sütun giriniz: ");
        col=info.nextInt();
        System.out.println("!! OYUN BAŞLADI !!");
        MineSweeper mine=new MineSweeper(row,col);
        mine.play();

    }
}