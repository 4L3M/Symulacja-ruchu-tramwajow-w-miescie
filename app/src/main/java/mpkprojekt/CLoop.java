package mpkprojekt;
import java.util.ArrayList;
import java.util.Random;
public class CLoop extends CPlatform{
    ArrayList<CTrafficLights> trafficLights; // lista swiatel
    CLine line = null; // linia
    CClock clock = null; // zegar
    ArrayList <CPlatform> platforms = null; // lista przystankow
    ArrayList <CRepairTram> repairTrams = null; // lista tramwajow naprawczych
    ArrayList<CTram> trams = null; // lista tramwajow
    ArrayList <String> schedule = null; // rozklad jazdy
    ArrayList <CAbstractTram> abstractTrams; // lista wwzystkich tramwajow
    public CLoop(String position, String controlPosition,  CLine line,ArrayList<CAbstractTram> abstractTrams,
                 ArrayList<CTrafficLights> trafficLights, CClock clock, ArrayList<CPlatform> platforms, ArrayList <CRepairTram> repairTrams,
                 ArrayList <String> schedule, ArrayList<CTram> trams) {
        // konstruktor
        super(position, controlPosition);
        this.line = line;
        this.trafficLights = trafficLights;
        this.clock = clock;
        this.platforms = platforms;
        this.repairTrams = repairTrams;
        this.schedule = schedule;
        this.trams = trams;
        this.abstractTrams = abstractTrams;
    }
    boolean checkifcango(CTram tram){
        // tworzy tramwaje i wypuszcza je zgodnie z rozkladem jazdy
        if(schedule.size()<=0) return false; // jezeli lista jest pusta, to zakoncz
        if(clock.isTimeEqual(schedule.get(0))){ // pobierz pierwszy element z rozkladu jazdy i sprawdz czy sie zgadza z aktualnym czasem
            schedule.remove(0); // usun pobrany element z rozkladu
            Random random = new Random();
             tram = new CTram (random.nextInt(2)+2,line,abstractTrams,trafficLights,platforms,repairTrams); // stworz nowy tramwaj
            abstractTrams.add(tram);
            trams.add(tram);
        }
        return true;
    }
}