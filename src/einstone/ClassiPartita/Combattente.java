package einstone.ClassiPartita;

import java.util.ArrayList;

import einstone.Carta;

public class Combattente//SenzaPatente <= fa rima (iury)
{
	public final String nome;
	
	public int manaMax;
	public int manaAtt;
	
	public ArrayList<Carta> deck;
	public ArrayList<Carta> mano;
	
	public Combattente(String nome, int manaMax, ArrayList<Carta> deck)
	{
		this.nome = nome;
		this.manaMax = manaMax;
		this.manaAtt = this.manaMax;
		
		this.deck = deck;
		this.mano = new ArrayList<Carta>();
	}
}