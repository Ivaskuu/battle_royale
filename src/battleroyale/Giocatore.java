package battleroyale;

import java.util.ArrayList;

/* La persona che sta giocando */

public class Giocatore
{
	public String nomeGiocatore;
	public int gold;
	public ArrayList<Carta> mazzo;
	public Carta[] carteSbloccate;
	
	public Giocatore(String nomeGiocatore, int gold, ArrayList<Carta> mazzo, Carta[] carteSbloccate)
	{
		this.nomeGiocatore = nomeGiocatore;
		this.gold = gold;
		this.mazzo = mazzo;
		this.carteSbloccate = carteSbloccate;
	}
}