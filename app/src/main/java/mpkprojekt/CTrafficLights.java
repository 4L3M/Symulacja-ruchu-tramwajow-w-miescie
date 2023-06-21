package mpkprojekt;
import java.awt.*;

public class CTrafficLights extends CStop implements Objects{
    boolean colour = false; // kolor swiatla, true - zielony, false - czerwony
    int offSetTime = 0; // zmienna sluzaca do ustawiania momentu swiecenia odpowiednich kolorow swiatel
    public CTrafficLights(String position, String controlPosition, int offSetTime) {
        // konstruktor
        super(position, controlPosition);
        this.offSetTime = offSetTime;
    }
    public void drawMe (Graphics2D G2D){
        // wyrysowanie odpowiedniego koloru swiatla jako symbol na mapie
        if(colour){
            G2D.setColor(Color.green.darker());
            G2D.fillRect(pos.x * 10,pos.y * 10,10,10);
        } else {
            G2D.setColor(Color.red);
            G2D.fillRect(pos.x * 10,pos.y * 10,10,10);
        }
    }
    boolean checkIfCanGo(CPosition position){
        // zatrzymuje i uruchamia tramwaj
        if((position.x==controlPos.x)&&(position.y==controlPos.y)){
            if (colour) return true;
            return false;
        }
        return true;
    }
    public void changeTime(int currentTime) {
        // ustawia kolory swiatel w zaleznosci od czasu
        if(((currentTime+offSetTime*4)%(60*4))<(15*4)){colour=true;}else{colour=false;}
    }
}
