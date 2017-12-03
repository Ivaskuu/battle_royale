package battleroyale;

import java.util.ArrayList;
import java.util.Random;

import battleroyale.Carta.TipoCarta;

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
		// Se r == true turno=0 else turno=1
		this.turno = new Random().nextInt(MAX_GIOCATORI);
		
		// Inizializza i giocatori
		for(int i = 0; i < MAX_GIOCATORI; i++)
		{
			if(i == turno) manaGiocatori[i] = 1;
			else manaGiocatori[i] = 0;
			
			carteNelCampo[i] = new ArrayList<Carta>();
		}
		
		System.out.println("E' cominciata una nuova partita.");
		System.out.println("Gioca per primo il giocatore " + turno);
	}
	
	public void cambiaTurno()
	{
		turno++;
		if(turno == MAX_GIOCATORI) turno = 0;

		controllaEffettiCarte();
		manaGiocatori[turno]++;
		
		notificaClient();
	}
	
	public void controllaEffettiCarte()
	{
		for (int i = 0; i < carteNelCampo.length; i++)
		{
			for (int j = 0; j < carteNelCampo[i].size(); j++)
			{
				/*if(carteNelCampo[i].get(j).)
				{
					System.out.println("");
				}*/
				// TODO: Eseguire i effetti della carta
			}
		}
	}
	
	// Aggiungi una carta nel campo di battaglia
	public void aggiungiCarta(int id)
	{
		// Controlla che la carta non ci sia gi� nel campo da battaglia
		for (int i = 0; i < carteNelCampo[turno].size(); i++)
		{
			if(carteNelCampo[turno].get(i).Id == id) break;
		}
		
		carteNelCampo[turno].add(CollezioneCarte.collezione_carte[id]);
		notificaClient();
	}
	
	// Quando il giocatore attacca una carta dell'avversario
	public void attacca(int idCartaAtt, int idCartaAvv)
	{
		int avversario = turno + 1;
		if(avversario == MAX_GIOCATORI) avversario = 0;
		
		if(carteNelCampo[turno].get(idCartaAtt) != null && carteNelCampo[avversario].get(idCartaAvv) != null) // Se le carte esistono veramente
		{
			if(carteNelCampo[turno].get(idCartaAtt).costo_mana <= manaGiocatori[turno]) // Se il giocatore ha abbastanza mana
			{
				// Rimuovi la vita della carta dell'avversario
				carteNelCampo[avversario].get(idCartaAvv).salute -= carteNelCampo[turno].get(idCartaAtt).attacco;
				
				// Rimuovi la carta dell'avversario se la sua salute � <= 0
				if(carteNelCampo[avversario].get(idCartaAvv).salute <= 0) carteNelCampo[avversario].remove(idCartaAvv);
				
				// Rimuovi il mana del giocatore
				manaGiocatori[turno] -= carteNelCampo[turno].get(idCartaAtt).costo_mana;
				
				notificaClient();
			}
		}
	}
	
	public void notificaClient()
	{
		// TODO: Notifica i client
	}
}