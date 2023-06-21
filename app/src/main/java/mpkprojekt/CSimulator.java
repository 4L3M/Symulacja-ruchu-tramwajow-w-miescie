package mpkprojekt;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class CSimulator { // glowna klasa obslugujaca symulacje
    CJFrame jFrame = null;// kontener wizualizacji
    ArrayList <CTram> trams = new ArrayList<>(); // lista tramwajow
    ArrayList<CAbstractTram> abstractTrams = new ArrayList<>(); // lista wszystkich tramwajow
    ArrayList<Objects> obj = new ArrayList<>();// lista obiektow do rysowania
    ArrayList<CRepairTram> repairTrams = new ArrayList<>(); // lista tramwajow naprawczych

    CClock clock = new CClock(0,0,0); // inicjacja zegara na wartosci 0:0:0
    CStatistics statistics = new CStatistics();// inicjacja klasy statystyk
    CMap map = new CMap(obj,clock,abstractTrams,repairTrams,trams); // tworzenie mapy

    public void Sim (){ // konstruktor
        jFrame = new CJFrame(abstractTrams, clock); //
        jFrame.guiPanel.objects = obj;
        Timer timer = new Timer(1,new CTimer(this)); // utworxenie zmiennej typu Timer, ktora odpowiada za wywolanie korku symulacji co okreslony czas
        // (ActionListener)
        timer.start(); // uruchomienie ActionListenera
        map.createMap();
  }
    public void checkIfWorks(){
        // sprawdza kazdy tramwaj, czy dziala
        ArrayList<CTram> toremove = new ArrayList<>(); // lista tramwajow do usuniecia
        for(CTram t: trams){ // iteruje po wszystkich tramwajach
            if(t.isWorking == 0){ // sprawdza czy dziala
                t.timeWaiting--; // dekrementuje czas oczekiwania tramwaju na tramwaj naprawczy
                if(t.timeWaiting !=0) continue; // jezeli czas oczekiwania != 0 to pomin tramwaj
                CRepairTram repairTram = new CRepairTram(t.length+1,t.line, t.map_pos+1,abstractTrams,map.trafficLights);
                // tworzy tramwaj naprawczy, o dlugosci zepsutego tramwaju +1 i ustawia go na pozycji zepsutego tramwaju +1
                abstractTrams.add(repairTram);
                repairTrams.add(repairTram);
                toremove.add(t); // dodaj zepsuty tramwaj do listy usuwania
            }
        }
        for(CTram t:toremove){ // iteruje po tramwajach do usuniecia
            t.saveToStats(); // zapisz statystyki zepsutego tramwaju
            trams.remove(t);
            abstractTrams.remove(t);

        }
        toremove.clear(); // wyczysc liste trawmajow do usuniecia
    }
    public void destroy (){
        // funkcja losowo zatrzymuje tramwaj - psucie tramwaju
        for(CTram t: trams){ // iteruje po tramwajach
            if(t.isWorking==0)continue; // jezeli tramwaj jest ju zepsutu to go pomija
            //if(t.map_pos + 2 >= t.line.tracks.size()) continue; // jezeli tramwaj jest blisko konca linii, zostaje pominiety
            Random rand = new Random();
            int i = rand.nextInt(5000); // ustawia prawdopodobienstwo zepsucia tramwaju
            if(i == 7){
                t.isWorking = 0; // unieruchamia tramwaj
                t.line.failure++;// inkrementuje liczbe awarii na linii w statystykach
                t.timeWaiting = t.map_pos; // ustawia czas oczekiwania na trmawaj naprawczy w zaleznosci od odleglosci od petli
            }
        }
    }
}

class CTimer implements ActionListener { // klasa obsluguje krok symuacji
    CSimulator simtimer = null;
    CTimer(CSimulator s){
        this.simtimer = s;
    } // konstruktor
    @Override
    public void actionPerformed(ActionEvent e) { // wykonuje krok symulacji
//        System.out.println(simtimer.clock.getTime());
//        System.out.println(currentTime);
        for(int i = simtimer.abstractTrams.size()-1 ;i>=0;i-- ){
            CAbstractTram t = simtimer.abstractTrams.get(i);
            t.move(); // wykonuje ruch kazdego tramwaju
        }
        for(CPlatform s: simtimer.map.platforms){
            s.changetime(simtimer.clock.currentTime);// dekrmentuje czas odpowiedzialny za zatrzymywanie tramwaju na poszczegolnym przystanku
        }
        for(CTrafficLights s: simtimer.map.trafficLights){
            s.changeTime(simtimer.clock.currentTime);// odpowiada za zmiane swiatel sygnalizacji
        }
        simtimer.checkIfWorks(); // wywolanie funkcji ktora sprawdza czy tramwaje dzialaj
        for (CLoop l: simtimer.map.loops){ // iterowanie po wszystkich petlach
            l.checkifcango(null); // wywolanie funkcji wypuszczajacej tramawje
        }
        simtimer.destroy(); // wywoalnie funkcji psujacej tramwaje
        if(simtimer.clock.currentTime%1000 == 0){ // zapisywanie statystyk
           String lineF = "";
           String lineD = "";
            for(CLine l: simtimer.map.lines){
                lineF += l.lineNumber+":"+l.failure+";";
                lineD += l.lineNumber+":"+simtimer.statistics.averageDelay(l.delay, l.countTrams)+";";
            }
            simtimer.statistics.failureStatistics.add(lineF);
            simtimer.statistics.delayStat.add(lineD);
            try {
                simtimer.statistics.saveAll();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        simtimer.clock.tikTak(); // przesuniecie czasu symulacji
        simtimer.jFrame.guiPanel.repaint(); // odswiezenie panelu wizualizacyjnego
    }
}
