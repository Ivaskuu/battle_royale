package battleroyale;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Ciao");
        
        Giocatore gg1 = new Giocatore("Adrian", 50, null, null);
        Giocatore gg2 = new Giocatore("Scino", 50, null, null);
        
        Partita partita = new Partita(new Giocatore[] {gg1, gg2});

    	partita.aggiungiCarta(0);
    	partita.aggiungiCarta(1);
    	partita.aggiungiCarta(3);
    	
    	partita.cambiaTurno();

    	partita.aggiungiCarta(0);
    	partita.aggiungiCarta(1);
    	partita.aggiungiCarta(3);
    	
    	partita.cambiaTurno();

    	partita.aggiungiCarta(0);
    	partita.aggiungiCarta(1);
    	partita.aggiungiCarta(3);
    	
    	partita.cambiaTurno();

    	partita.aggiungiCarta(0);
    	partita.aggiungiCarta(1);
    	partita.aggiungiCarta(3);
    	
    	partita.cambiaTurno();

    	partita.aggiungiCarta(0);
    	partita.aggiungiCarta(1);
    	partita.aggiungiCarta(3);
    	
    	partita.cambiaTurno();

    	partita.aggiungiCarta(0);
    	partita.aggiungiCarta(1);
    	partita.aggiungiCarta(3);
    	
    	partita.cambiaTurno();

    	partita.aggiungiCarta(0);
    	partita.aggiungiCarta(1);
    	partita.aggiungiCarta(3);
    	
    	partita.cambiaTurno();

    	partita.aggiungiCarta(0);
    	partita.aggiungiCarta(1);
    	partita.aggiungiCarta(3);
    	
    	partita.cambiaTurno();

    	partita.aggiungiCarta(0);
    	partita.aggiungiCarta(1);
    	partita.aggiungiCarta(3);
    	
    	partita.cambiaTurno();
    	
    	partita.attacca(0, 0);
    	
    	partita.cambiaTurno();
    	
    	partita.attacca(0, 0);
    	
    	partita.cambiaTurno();
    	
    }
}