package mpkprojekt;
import java.awt.*;
import java.util.ArrayList;
public class CLine implements Objects {
    public ArrayList <CTrack> tracks = new ArrayList<>();// lista torow
    int failure = 0; // liczba awarii
    int lineNumber = 0; // numer linii
    float delay = 0; // laczny czas spoznien ramwajow
    int countTrams = 0; // liczba tramwajow
    public CLine ( int lineNumber, String line){
        // konstruktor
        this.lineNumber = lineNumber;
       String [] lines = line.split(";"); //zwraca tablice
        for (String s: lines){ // tworzy liste torow na podstawie danych ze stringa
            CTrack track = new CTrack(s);
            tracks.add(track);
        }
    }
    @Override
    public void drawMe(Graphics2D G2D) { // rysowanie w panelu wszystkich torow
        for(CTrack t:tracks){
            t.drawMe(G2D);
        }
    }
}
