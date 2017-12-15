package battleroyale.ClassiPartita;
import java.util.ArrayList;

import battleroyale.Carta;
import battleroyale.CollezioneCarte;

public class Effetti 
{
	public static void eseguiEffetto(Partita p, Effetto e, Object[] payload)
	{
		switch(e)
		{
			case CopiaCarta:
				
			break;
			case PescaCarta:
				
			break;
			case DaiVita:
			
			break;
			case DistruggiCarta:
				//TODO aggiornare l'altro giocatore
				
			break;
			case EvocaDalDeck :
			//Trova la carta specifica nel mazzo e la evoca
			ArrayList<Carta> deckGg = p.combattente.deck;
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
			}
				
			break;
			default:
			System.out.println("Errore, effetto not found");
			break;
		}
	}
}