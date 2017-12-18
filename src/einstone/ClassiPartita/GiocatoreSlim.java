package einstone.ClassiPartita;

import einstone.CollezioneCarte;
import einstone.Giocatore;

// Versione meno pesante della classe Giocatore
public class GiocatoreSlim
{
	public static final int SALUTE_MAX = 30;
	
	public final String nome;
	public final int[] deck;
	public int salute;
	
	public GiocatoreSlim(String nome, int[] deck)
	{
		this.nome = nome;
		this.deck = deck;
		this.salute = SALUTE_MAX;
	}
	
	public static GiocatoreSlim fromGiocatore(Giocatore gg)
	{
		return new GiocatoreSlim(gg.nomeGiocatore, CollezioneCarte.cartaArrayToPosArray(gg.deck));
	}
}