package battleroyale.ClassiPartita;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import battleroyale.Carta;
import battleroyale.CollezioneCarte;
import battleroyale.ClassiPartita.Effetto.TipoEffetto;

public class Effetti 
{
	public static void eseguiEffetto(Partita p, Effetto e, Object[] payload)
	{
		switch(e.tipoEffetto)
		{
			case Attacca:
				//TODO aggiornare l'altro giocatore
				
			break;
			case Evocazione:
				// Trova la carta specifica nel mazzo e la evoca
				int gg = (int)e.payload[0]; // Per quale giocatore evocare le carte
				
				if(((boolean)e.payload[1]) == true) // Se la carta da evocare deve essere presente nel deck del giocatore
				{
					/*ArrayList<Carta> deckGg = p.combattente.deck;
					for (int i = 0; i < deckGg.size(); i++)
					{
						if(deckGg.get(i).nome == payload[0].toString())
						{
							//Controlla se il campo è pieno
							if(p.campo[p.THIS_GG].size() < p.MAX_CARTE_CAMPO)
							{
								Carta carta = Carta.cartaToCartaPartita(deckGg.get(i));
								deckGg.remove(i);
								p.campo[p.THIS_GG].add(carta);
								System.out.println("Hai aggiunto " + carta.nome + " sul tuo campo!");
							}
							else System.out.println("Il tuo campo è pieno! La carta non è stata aggiunta nel tuo campo");
								
						}
					}*/
				}
				else
				{
					
				}
					
				break;
			case Cura:
				int cosa = (int)payload[0];
				int dove = (int)payload[1];
				int[] qualeCarte = (int[])payload[0];
				int diQuanto = (int)payload[0];
				
				if(cosa == 0) // Cura l'eroe
				{
					
				}
				else // Cura l'eroe
				{
					
				}
				break;
			default:
				System.out.println("Errore, effetto not found");
				break;
		}
	}
	
	private static String chiediAlGg(String msg)
	{
		BufferedReader tast = new BufferedReader(new InputStreamReader(System.in));
		System.out.println(msg);
		
		String scelta = "";
		
		do
		{
			try
			{
				System.out.print("\nScelta: ");
				scelta = tast.readLine();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		while(scelta == null || scelta.length() == 0);
		
		return scelta;
	}
}