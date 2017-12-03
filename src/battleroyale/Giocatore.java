package battleroyale;

import java.util.ArrayList;

/* La persona che sta giocando */

public class Giocatore
{
	public String nomeGiocatore;
	public int gold;
	public Carta[] carteSbloccate;
	public Carta[] deck;
	
	public Giocatore(String nomeGiocatore, int gold, Carta[] carteSbloccate, Carta[] deck)
	{
		this.nomeGiocatore = nomeGiocatore;
		this.gold = gold;
		this.carteSbloccate = carteSbloccate;
		this.deck = deck;
	}
}