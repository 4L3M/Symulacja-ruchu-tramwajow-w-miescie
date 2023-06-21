package mpkprojekt;

public class CPosition {
    int x; // wspolrzedna x
    int y; // wspolrzedna y

    public CPosition(String xy){
        // konstruktor
        // inicjuje na podstawie lancucha znakow
        xy = xy.replace("(","");
        xy = xy.replace(")","");
        String [] s = xy.split(",");
        this.x = Integer.parseInt(s[0]);
        this.y = Integer.parseInt(s[1]);
    }
}
