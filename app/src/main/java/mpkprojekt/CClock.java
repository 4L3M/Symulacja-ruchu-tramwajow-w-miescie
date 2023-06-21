package mpkprojekt;
public class CClock {
    int second = 0; // sekundy
    int hour = 0; // godziny
    int minute = 0; // minuty
    int currentTime = 0; // aktualny czas - aktualna ilosc tykniec zegara
    public CClock(int hour, int minute, int second) {
        //inicjacja konstruktora
        this.second = second;
        this.hour = hour;
        this.minute = minute;
    }
    public String getTime (){
        return hour + ":" + minute + ":" + second;
    } // zwraca aktualna godzine sformatowana w lancuch znakow w formacie h:m:s
    public void tikTak (){
        // wykonuje tykniecie zegara (przyjeto ze zegar podczas jednego "tykniecia" przesuwa sie o 10s)
        second += 10;
        if(second >= 60){ // jezeli liczba sekund > 60 wykonaj operacje modulo 60
            second %= 60; // wykonaj operacje modulo 60
            minute++; // zwieksz minute o 1
            if(minute >= 60){ // jezeli liczba minut > 60 wykonaj operacje modulo 60
                minute %= 60; // wykonaj operacje modulo 60
                hour++; // zwieksz godzine o 1
                if(hour > 23){ // jezeli godzina > 23 wyzeruj
                    hour = 0;
                }
            }
        }
        currentTime++;
    }
   public boolean isTimeEqual(String departureTime){
        // sprawdza czy podana godzina jest rowna z aktualna
      int hC, mC, sC;
      String [] C = departureTime.split(":"); // splitownaie lancucha znakow, zeby przygotowac go do parsowania
      hC = Integer.parseInt(C[0]); // parsowanie
      mC = Integer.parseInt(C[1]);
      sC = Integer.parseInt(C[2]);

      if(hC > hour) return false;
      if(mC > minute) return false;
      if(sC > second) return false;

      return true;
   }
}