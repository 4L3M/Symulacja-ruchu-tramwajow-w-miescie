package mpkprojekt;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class CTram extends CAbstractTram implements Objects{
    int isWorking; // czy dziala? 0 - nie dziala; 1 - dziala
    int timeWaiting =0; // czas oczekiwania na tramwaj zastepczy w przypadku zepsucia
    ArrayList<CTrafficLights> trafficLights; // lista swiatel
    ArrayList<CPlatform> platforms; // lista przystankow
    //ArrayList<CRepairTram> repairTrams; // lista zepsutych tramwajow
    CClock tramClock = new CClock(0,0,0);
    int randColour;
    public CTram( int length, CLine line, ArrayList<CAbstractTram> abstractTrams, ArrayList<CTrafficLights> trafficLights,
                  ArrayList<CPlatform> platforms,ArrayList<CRepairTram> repairTrams) {
        //konstruktor
        super(length,0, line, abstractTrams);
        this.trafficLights=trafficLights;
        isWorking = 1;
        this.platforms=platforms;
       // this.repairTrams = repairTrams;
        Random rand = new Random();
        this.randColour = rand.nextInt(2);
    }

    @Override
    public void drawMe(Graphics2D G2D) {
        // rysuje tramwaj na panelu
        G2D.setColor(Color.cyan);
        if(randColour == 0) G2D.setColor(Color.BLUE); // losowo ustawia kolor na jasny lub ciemny niebieski
        for(CPosition p: listToDraw()){ // rysuje wszystkie pozycje tramwaju
            if(isWorking==0) G2D.setColor(Color.red.darker()); // jezeli zepsuty to kolor - czerwony
            G2D.fillRect(p.x * 10,p.y * 10,10,10);
        }
        G2D.setColor(Color.black); // ustawia kolor numeru linii
        if(randColour == 0){ G2D.setColor(Color.white);} // wypisuje numer linii
        G2D.drawString(String.valueOf(line.lineNumber),line.tracks.get(map_pos).pos.x*10 , line.tracks.get(map_pos).pos.y*10+10);
    }
    @Override
    public boolean checkIfCanGo(){
        // sprawdza czy tramwaj moze sie przemieszczac
        boolean b = true;
        if (isWorking == 0) { // jezeli jest zepsuty - zwraca falsz
            return false;
        }
        for(CPlatform platform: platforms){ // odpytuje wszystkie przystanki, czy moze sie przemiescic
            if(!platform.checkifcango(this)) b = false;
        }
        for(CTrafficLights lights: trafficLights){ // odpytuje wszytskie sygnalizatory czy moze sie przemiescic
            if(!lights.checkIfCanGo(headPosition())) b = false;
        }
        if(b) return true;
        return false;
    }
    @Override
    public boolean checkCollision(){ // sprawdza wszystkie kolizje z innymi tramwajami
        //if(isWorking == 0) return true;
        for(CAbstractTram t: abstractTrams){
            if(t == this) continue;
            for(CPosition p: t.listToCheckColision()){
                CPosition myPosition = line.tracks.get(map_pos).pos;
                if(myPosition.x == p.x && myPosition.y == p.y){
                    tramClock.tikTak(); // jezeli wystepuje kolizja - zwieksz opoznienie o okreslony czas
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    public void saveToStats() {
        // zapisz spoznienie do linii
        line.delay += tramClock.minute + tramClock.hour * 60 + tramClock.second / 60;
        line.countTrams++;
    }
}