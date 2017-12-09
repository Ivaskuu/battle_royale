package battleroyale.ClassiPartita;

import battleroyale.CollezioneCarte;
import battleroyale.Giocatore;

// Versione meno pesante della classe Giocatore
public class GiocatoreSlim
{
	public String nome;
	public int[] deck;
	
	public GiocatoreSlim(String nome, int[] deck)
	{
		this.nome = nome;
		this.deck = deck;
	}
	
	public static GiocatoreSlim fromGiocatore(Giocatore gg)
	{
		return new GiocatoreSlim(gg.nomeGiocatore, CollezioneCarte.cartaArrayToPosArray(gg.deck));
	}
}