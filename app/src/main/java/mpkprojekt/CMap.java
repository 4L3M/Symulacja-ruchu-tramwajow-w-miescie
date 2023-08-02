package mpkprojekt;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CMap {
    ArrayList<CTrafficLights> trafficLights = new ArrayList<>();         // lista swiatel
    ArrayList<CPlatform> platforms = new ArrayList<>();                  // lista przystankow
    ArrayList<CLoop> loops = new ArrayList<>();                          // lista petli
    ArrayList <CLine> lines = new ArrayList<>();                         // lista linii
    ArrayList <Objects> obj;                                             // lista obiekow do rysowania
    CClock clock;                                                        // zegra
    ArrayList <CAbstractTram> abstractTrams;                             // lista tramwajow wszytskich

    public CMap (ArrayList<Objects> obj, CClock clock, ArrayList <CAbstractTram> abstractTrams){
        this.obj = obj;
        this.clock = clock;
        this.abstractTrams = abstractTrams;
    }
    public void createLines(){                                           // wczytuje dane odnosnie lin i laduje nimi liste linii
        File file = new File("Lines");
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.println("Nieprawidlowy plik z liniami");
            throw new RuntimeException(e);
        }
        while (scanner.hasNextLine()){
            String string = scanner.nextLine();
            String [] splittedString = string.split("-");
            CLine line = new CLine(Integer.valueOf(splittedString[0]),splittedString[1]);
            lines.add(line);
            obj.add(line);
        }
    }
    public void createStop(){                                            // wczytuje dane dotyczace przytsankow i laduje nimi liste przystankow
        ArrayList<String> stopsFromFile = new ArrayList<>();
        File file = new File("stop.txt");
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.println("Brak pliku z przystankami");
            throw new RuntimeException(e);
        }
        while (scanner.hasNextLine()){
            stopsFromFile.add(scanner.nextLine());
        }

        for(String s: stopsFromFile){
            String [] singleStop = s.split("-");
            CPlatform stop = new CPlatform(singleStop[0],singleStop[1]);
            obj.add(stop);
            platforms.add(stop);
        }
    }
    public void createLights(){                                          // wczytuje dane dotyczace swaitel i laduje nimi liste swiatel
        ArrayList<String> lightFromFile = new ArrayList<>();
        File file = new File("trafficLights");
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.println("Brak pliku ze swiatlami");
            throw new RuntimeException(e);
        }
        while (scanner.hasNextLine()){
            lightFromFile.add(scanner.nextLine());
        }
        for(String s: lightFromFile){
            String [] singleLight = s.split(";");
            CTrafficLights trafficLight = new CTrafficLights(singleLight[0],singleLight[1],Integer.valueOf(singleLight[2]));
            obj.add(trafficLight);
            trafficLights.add(trafficLight);
        }
    }
    public void createLoops (){                                          // wczytuje dane dotyczace petli i laduje nimi liste petli
        Map<Integer,ArrayList<String>> allSchedules = new HashMap<>();
        Scanner scanner = null;
        File file = new File("Schedule");
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.println("Brak podanego pliku!");
            throw new RuntimeException(e);
        }
        while (scanner.hasNextLine()){
            String string = scanner.nextLine();
            String [] splittedString = string.split("-");
            if(!allSchedules.containsKey(Integer.valueOf(splittedString[0]))) {
                allSchedules.put(Integer.valueOf(splittedString[0]), new ArrayList<>());
            }

            if(allSchedules.containsKey(Integer.valueOf(splittedString[0]))){
                allSchedules.get(Integer.valueOf(splittedString[0])).add(splittedString[1]);
            }
        }
        for(Integer i: allSchedules.keySet()){
            CLoop loop = null;
            CLine line = null;
            for(CLine l: lines){
                if(l.lineNumber == i){
                    line = l; break; }
            }
            loop = new CLoop("(0,0)", "(0,0)", line, allSchedules.get(i));
            loops.add(loop);
        }
    }
    public void createMap(){
        createLines();
        createStop();
        createLights();
        createLoops();
    }
}