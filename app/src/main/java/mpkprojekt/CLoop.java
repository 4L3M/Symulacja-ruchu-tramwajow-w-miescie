package mpkprojekt;
import java.util.ArrayList;
import java.util.Random;
public class CLoop extends CPlatform{
    CLine line = null;                                                                                                  // linia
    ArrayList <String> schedule = null;                                                                                 // rozklad jazdy
    public CLoop(String position, String controlPosition,  CLine line, ArrayList <String> schedule) {                   // konstruktor
        super(position, controlPosition);
        this.line = line;
        this.schedule = schedule;
    }
   boolean startTram(ArrayList <CAbstractTram> abstractTrams,  ArrayList<CTrafficLights> trafficLights, CClock clock, ArrayList <CPlatform> platforms){
                                                                                                                        // tworzy tramwaje i wypuszcza je zgodnie z rozkladem jazdy
        if(schedule.size() <= 0) return false;                                                                          // jezeli lista jest pusta, to zakoncz
        if(clock.isTimeEqual(schedule.get(0))){                                                                         // pobierz pierwszy element z rozkladu jazdy i sprawdz czy sie zgadza z aktualnym czasem
            schedule.remove(0);                                                                                   // usun pobrany element z rozkladu
            Random random = new Random();
            CTram tram = new CTram (random.nextInt(2) +2, line, abstractTrams, trafficLights, platforms);  // stworz nowy tramwaj
            abstractTrams.add(tram);
        }
        return true;
    }
}