package mpkprojekt;

import java.awt.*;
import java.util.ArrayList;

public class CPlatform extends CStop implements Objects{
    ArrayList <CPosition> platformPositions = new ArrayList<>(); // pozycje przystanku
    int counter = 0; // licznik
    public CPlatform(String position, String controlPosition) {
        // konstruktor
        super(controlPosition, controlPosition);
        String [] platform = position.split(";");
        for(String s: platform){
           CPosition platformPos = new CPosition(s);
           platformPositions.add(platformPos);
        }
    }
    public void drawMe (Graphics2D G2D) {
        // rysowanie wszystkich pozycji na panelu
        G2D.setColor(Color.yellow.darker());
        for (CPosition p : platformPositions) {
            G2D.fillRect(p.x * 10, p.y * 10, 10, 10);
        }
    }
    boolean checkifcango(CTram tram){
        CPosition position = tram.headPosition();
        // zwraca czy tramwaj moze sie dalej przemieszczac (zatrzymuje i uruchamia tramwaj)
        if((position.x == controlPos.x)&&(position.y == controlPos.y)){ // sprawdza czy pierwsza pozycja tramwaju pokrywa sie z pozycja kontrolna przystanku
            if(stoppedTram == tram&&counter <= 0) return true; // sprawdza czy tramwaj jest juz zatrzymany i czy minal czas jego postoju
            if(counter>0&&stoppedTram == tram)return false; // jezeli nie minal czas jego postoju zwraca, ze tramwaj musi dalej stac
            stoppedTram=tram; // zapamietuje, ktroy tramwaj zatrzymal
            counter=20; // ustawia czas postoju na 20
            return false;
        }
        return true;
    }
    public void changetime(int currentTime) {
        // dekrementuje licznik odpowiedzialny za czas zatrzymania tramwaju
        counter--;
    }
}