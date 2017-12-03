package battleroyale;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Ciao");
        
        Giocatore gg1 = new Giocatore("Adrian", 50, null, null);
        Giocatore gg2 = new Giocatore("Scino", 50, null, null);
        
        Partita partita = new Partita(new Giocatore[] {gg1, gg2});

        
    }
}