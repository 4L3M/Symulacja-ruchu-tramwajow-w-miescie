package mpkprojekt;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
public class CGuiPanel extends JPanel {
    private int width = 1900;                                                   // szerokosc panelu
    private int heigth = 1000;                                                  // wysokosc panelu
    private char [][] array = new char[10000][10000];                           // tablica znakow do przchowywania informacji o tle mapy do wyswietlania
    private int maxc = 0;                                                       // max kolumny
    private int maxr = 0;                                                       // max rzedy
    public ArrayList <Objects> objects = null;                                  // lista objektow do rysowania
    CClock clock;                                                               // zegar
    ArrayList <CAbstractTram> abstractTrams = null;                             // lista tramwajow
    public CGuiPanel( ArrayList<CAbstractTram> abstractTrams,CClock clock){     // konstruktor
        this.setPreferredSize(new Dimension(width,heigth));
        this.abstractTrams = abstractTrams;
        this.clock = clock;
        readMap();
    }
   private void readMap(){                                                      // wczytanie tla mapy
        File file = new File("Map");                                   // Otwieranie pliku o podanej nazwie
        Scanner scanner = null;                                                 // Tworzy obiekt typu Scanner
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.println("Niewlasciwy plik");
        }
        List<String> list = new ArrayList<>();                                  // Utworzenie listy
        while (scanner.hasNextLine()){                                          // Tak dlugo dodawaj do listy, dopóki w pliku są kolejne linie
            list.add(scanner.nextLine());                                       // Wczytanie do listy poszczególnych wierszy
        }
        maxr = list.size();                                                     // wyznaczenie ilosci rzedow
        for(String s: list){
            if(maxc < s.length()) maxc = s.length();                            // wyznaczenie ilosci kolumn
        }
        for(int i = 0; i<maxr;i++){
            for (int j = 0; j<maxc;j++){
                array[i][j] = list.get(i).charAt(j);                            // wczytanie do tablicy przechowujacej znaki uzywane do rysowania tla mapy
            }
        }
    }
    public void paint(Graphics G){                                              // rysuje na panelu
        Graphics2D G2D = (Graphics2D) G;                                        // rzutowanie na typ G2D
        for(int i = 0; i<maxr;i++){                                             // rysowanie tla mapy
            for (int j = 0; j<maxc;j++){
               if(array[i][j]=='B'){
                    G2D.setColor(Color.orange.darker().darker());}
                if(array[i][j]==' '){
                    G2D.setColor(Color.GREEN.brighter());}
                if(array[i][j]=='W'){
                    G2D.setColor(Color.cyan.darker());}
                if (array[i][j]=='T'){
                    G2D.setColor(Color.BLUE);}
                G2D.fillRect(10+j*10,10+i*10,10,10);            // powierzchnia jednego obiektu na ekranie
                                                                                  // to 10 pikseli^2, dlatego pomnozone razy 10
            }
        }

        for(int i = 0 ; i < objects.size();i++  ){
            objects.get(i).drawMe(G2D);
        }

        for(int i = 0 ; i < abstractTrams.size();i++  ){
            abstractTrams.get(i).drawMe(G2D);
        }
        G2D.setColor(Color.BLACK);                                                // rysowanie ramki zegara
        G2D.fillRect(1150,50,100,50);
        G2D.setColor(Color.white);                                                // rysowanie tla zegara
        G2D.fillRect(1155,55,90,40);
        G2D.setColor(Color.black);
        G2D.setFont(new Font("Arial",Font.BOLD,16));                   // ustawienie czcionki zegara
        G2D.drawString(clock.getTime(),1170,80);                            // wypisanie godziny zegara
    }
    }