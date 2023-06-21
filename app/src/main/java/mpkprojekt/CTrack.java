package mpkprojekt;

import java.awt.*;

public class CTrack implements Objects{
CPosition pos;
    public CTrack(String xy) {
    // konstruktor
        this.pos = new CPosition(xy);
    }
    @Override
    public void drawMe(Graphics2D G2D) {
        // wyrysowanie torow na mapie
        G2D.setColor(Color.white);
        G2D.fillRect(pos.x * 10, pos.y * 10,10,10);
    }
}
