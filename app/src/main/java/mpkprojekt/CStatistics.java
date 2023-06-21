package mpkprojekt;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CStatistics {
    ArrayList<String> failureStatistics = new ArrayList<>(); // lista statystyk awarii tramwajow
    ArrayList<String> delayStat = new ArrayList<>(); // lista statystyk opoznien tramwajow
    public void saveToFile(String fileName, ArrayList <String> Stat) throws IOException {
        // zapisanie zawartosci listy do pliku
        FileWriter fileWriter = new FileWriter(fileName, false);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        for(String s: Stat){
            printWriter.println(s);
        }
        printWriter.close();
    }
    public void saveAll () throws IOException {
        // zapisanie statystyk do pliku
        saveToFile("Failures.txt",failureStatistics);
        saveToFile("Delay.txt",delayStat);
    }
    public float averageDelay (float allD, int howManyTrams){
        // wyliczenie sredniego spoznienia
        return allD/(1.0f*howManyTrams);
    }
}
