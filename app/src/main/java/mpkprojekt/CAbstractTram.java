package mpkprojekt;
import java.util.ArrayList;
public abstract class CAbstractTram implements Objects {
    int map_pos; // pozycja na liscie (pozycja na mapie)
    CLine line; // linia tramwaju
    int length=-1; // dlugosc
    ArrayList<CAbstractTram> abstractTrams; // lista wszystkich tramwajow
    public CAbstractTram(int length, int map_pos, CLine line, ArrayList<CAbstractTram> abstractTrams) {
        // Konstruktor inicjujacy
        this.length = length;
        this.map_pos = map_pos;
        this.line = line;
        this.abstractTrams=abstractTrams;
    }
    CPosition headPosition(){
        // zwraca pozycje toru na podstawie pozycji na liscie torow przechowywanych w linii
        int pos = map_pos%line.tracks.size();
        return line.tracks.get(pos).pos;
    }
    public boolean checkCollision(){
        // sprawdza czy na danej pozycji znajduje sie juz inny tramwaj
        for(CAbstractTram t: abstractTrams){ // iterowanie po liscie tramwajow
            if(t == this) continue; // jezeli sprawdzany tramaj to ten tramwaj, to sprawdzaj nastepny
            for(CPosition p: t.listToCheckColision()){ // iterowanwie po liscie pozycji rozwazanego tramwaju
                CPosition myPosition = line.tracks.get(map_pos).pos; // pobranie aktualnej wlasnej pozycji
                if(myPosition.x == p.x && myPosition.y == p.y) return false; // jezeli wspolrzedna x i y rozwazanej pozycji tramwaju
                // i aktualnego tramwaju sa takie same, to zwroc falsz (kolizja)
            }
        }
        return true; // zwroc prawde - brak kolizji
    }
    boolean checkIfCanGo(){
        return true;
    } // sprawdza czy tramwaj moze sie ruszyc
    //rozszerzana w klasach potomnych
    public void move (){
        // przemieszcza tramwaj o jedna pozycje
        if(!checkIfCanGo())return; // sprawdza czy tramwaj moze wykonac ruch
        int current = map_pos; // zapisuje aktualna pozycje
        if(map_pos+1>=line.tracks.size()){ // sprawdza czy pozycja tramwaju po wykonaniu jest juz na koncu linii
            abstractTrams.remove(this); // tramwaj usuwa sie z listy wszystkich tramwajow
            saveToStats(); // zapisuje swoje statystyki do listy
            return;
        }
        map_pos=(map_pos+1); // zwiekszenie pozycji o 1
        if (!checkCollision()) map_pos = current; //sprawdza czy wystepuje kolizja. Jezeli taak - przywraca poprzednia pozycje
    }
    public void saveToStats(){ // rozszerzana przez klase CTram
    }
    public ArrayList<CPosition> listToDraw() {
        // zwraca liste pozycji na mapie, na ktorych znajduje sie tramwaj
        ArrayList<CPosition> positions = new ArrayList<>(); // inicjacja listy
        for (int i = 0; i < length; i++) { // iterowanie od 0 do dlugosci tramwaju-1
            int x = (map_pos - i + line.tracks.size())%line.tracks.size(); // pobranie map_pos-i pozycji z listy
            positions.add(line.tracks.get(x).pos); // dodanie pozycji do listy
        }
        return positions; // zwraca liste pozycji
    }
    public ArrayList<CPosition> listToCheckColision() {
        // zwraca liste pozycji, zajetych przez aktualny trmawaj
        // dziala podobnie do listToDraw
        // dodatkowo zwraca jeszcze jedna pozycje w tyl, aby trawaje utrzymywaly jedna pozycji przerwy
        ArrayList<CPosition> positions = new ArrayList<>();
        for (int i = 0; i <= length; i++) {
            int x = (map_pos - i+line.tracks.size())%line.tracks.size();
            positions.add(line.tracks.get(x).pos);
        }
        return positions;
    }


}
