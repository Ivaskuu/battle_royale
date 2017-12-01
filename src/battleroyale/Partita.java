package battleroyale;

import java.util.ArrayList;
import java.util.Random;

public class Partita
{
	public static final int MAX_GIOCATORI = 2;
	
	// Il mana dei 2 o piu giocatori
	public int[] manaGiocatori = new int[MAX_GIOCATORI];
	
	// Le carte piazzate dai giocatori nel campo di battaglia
	public ArrayList<Carta>[] carteNelCampo = new ArrayList[MAX_GIOCATORI];
	
	// Quale giocatore sta giocando adesso
	public int turno = 0;
	
	public Partita()
	{
		// Inizializza i giocatori
		for(int i = 0; i < MAX_GIOCATORI; i++)
		{
			manaGiocatori[i] = 1;
			carteNelCampo[i] = new ArrayList<Carta>();
		}

		// Se r == true turno=0 else turno=1
		this.turno = new Random().nextBoolean() ? 0 : 1;
	}
	
	public void cambiaTurno()
	{
		turno++;
		if(turno == MAX_GIOCATORI) turno = 0;
		
		// TODO: Notifica il client
	}
	
	// Aggiungi una carta nel campo di battaglia
	public void aggiungiCarta(int id)
	{
		// Controlla che la carta non ci sia già nel campo da battaglia
		for (int i = 0; i < carteNelCampo[turno].size(); i++)
		{
			if(carteNelCampo[turno].get(i).Id == id) break;
		}
		
		carteNelCampo[turno].add(CollezioneCarte.collezione_carte[id]); // TODO: prendere la carta dall'array di carte con [id]

		// TODO: Notifica i client
	}
}