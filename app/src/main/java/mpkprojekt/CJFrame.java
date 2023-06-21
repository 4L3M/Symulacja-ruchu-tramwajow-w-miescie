package mpkprojekt;
import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
public class CJFrame extends JFrame {
    CGuiPanel guiPanel ; // obiekt panelu
    public CJFrame(ArrayList <CAbstractTram> abstractTrams,CClock clock) {
        guiPanel = new CGuiPanel(abstractTrams,clock); //tworzenie obiektu panelu
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true); // ustawienie parametru widocznosci kontenera na prawda
        this.add(guiPanel); //dodanie panelu do kontenera
        pack();
        setSize(new Dimension(1900, 1000));//ustawienie rozmiaru kontenera
    }
}