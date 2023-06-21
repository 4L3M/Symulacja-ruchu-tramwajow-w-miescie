package mpkprojekt;

public abstract class CStop {
    CPosition pos;
    CPosition controlPos; // pozycja niewidoczna przy wyswietlaniu. Na niej zatrzymuje sie tramwaj
    CTram stoppedTram = null;
    public CStop(String position, String controlPosition) {
        // konstruktor
        pos = new CPosition(position);
        controlPos = new CPosition(controlPosition);
    }
}