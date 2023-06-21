package mpkprojekt;

import java.awt.*;
import java.util.ArrayList;

public class CRepairTram extends CAbstractTram implements Objects {
    ArrayList<CTrafficLights> trafficLights; // lista swiatel
    public CRepairTram( int length, CLine line, int map_pos, ArrayList<CAbstractTram> abstractTrams, ArrayList<CTrafficLights> trafficLights) {
        // konstruktor
        super(length, map_pos, line, abstractTrams);
        this.trafficLights=trafficLights;
    }
    @Override
    public void drawMe(Graphics2D G2D) {
        // rysuje tramwaj i jego wszystkie pozycje na panelu
        G2D.setColor(Color.black);
        CPosition p = listToDraw().get(0);
        G2D.fillRect(p.x * 10,p.y * 10,10,10);
        G2D.setColor(Color.BLUE);
        for(int i = 1; i < listToDraw().size(); i++){
            p= listToDraw().get(i);
            G2D.fillRect(p.x * 10,p.y * 10,10,10);
        }
    }
    @Override
    public boolean checkIfCanGo(){
        // sprawdza, czy moze jechac
        boolean b = true;
        for(CTrafficLights lights: trafficLights){ // iteruje po kazdym sygnalizatorze
            if(!lights.checkIfCanGo(headPosition())) b = false; // odpytuje kazdy sygnalizator, czy moze jechac
        }
        if(b) return true;
        return false;
    }
}