package battleroyale.Partita;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import battleroyale.Carta;
import battleroyale.CollezioneCarte;
import battleroyale.ClientServer.Client;
import battleroyale.ClientServer.Server;
import battleroyale.Partita.AggiornamentoPartita.AzionePartita;
import battleroyale.Carta.TipoCarta;

public class Partita
{
	public static final int MAX_GIOCATORI = 2;
	public static final int MAX_CARTE_MANO = 10;
	public static final int MAX_CARTE_CAMPO = 5;
	public static final int MAX_MANA = 10;
	
	public final int NUM_GG;
	public final int PRIMO_GG; // Soluzione temporanea (pigra) per manaMax TODO

	public final Combattente[] combattenti;
	public int turno;
	
	private Client client;
	private Server server;
	
	public Partita(GiocatoreSlim[] giocatori, Client client)
	{
		//this.client = client;
		this.NUM_GG = giocatori.length;
		
		this.PRIMO_GG = new Random().nextInt(2);
		this.turno = PRIMO_GG;
		
		System.out.println("\nE' cominciata una nuova partita.");
		System.out.println("Gioca per primo " + giocatori[turno].nome + ", cioè il giocatore numero " + turno + "\n");
		
		// Inizializza i giocatori
		for(int i = 0; i < NUM_GG; i++)
		{
			combattenti[i] = new Combattente(giocatori[i].nome, i == PRIMO_GG ? 1 : 0, getRandomDeck(giocatori[i].deck));
			pescaCarta(i, 3);
		}
		
		riepilogoPartita();

		initPartita();
		initClient(client, PRIMO_GG, deck[1]);
	}
	
	public void initClient()
	{
		
	}
	
	public Partita(GiocatoreSlim[] giocatori, int primoGG, Server server, ArrayList<Carta>[] deck)
	{
		this.client = client;
		this.PRIMO_GG = primoGG;
		this.turno = PRIMO_GG;

		this.giocatori = giocatori;
		this.NUM_GG = giocatori.length;
		
		initPartita();
	}
	
	private void initPartita()
	{
		
	}
	
	public void cambiaTurno()
	{
		turno++;
		if(turno == MAX_GIOCATORI) turno = 0;

		//controllaEffettiCarte(); TODO
		ripristinaGiocateCarte(turno);
		
		if(turno == PRIMO_GG && manaMax < MAX_MANA) manaMax++;
		manaAtt = manaMax;
		
		notificaClient();
		System.out.println("\n-- Turno cambiato --");
		System.out.println("Tocca giocare a " + giocatori[turno].nome);
		
		pescaCarta(turno, 1);
		
		riepilogoPartita();
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
	
	// Aggiungi una carta nel campo di battaglia dalla mano
	public void aggiungiCartaSulCampo(int posMazzo)
	{
		if(carteNelCampo[turno].size() >= MAX_CARTE_CAMPO) // Se ci sono troppe carte sul campo
		{
			System.out.println(giocatori[turno].nome + " ha raggiunto il numero max di carte sul campo (" + MAX_CARTE_CAMPO + ")");
			return;
		}

		Carta carta = mano[turno].get(posMazzo);
		
		if(carta.costoMana > manaAtt) // Se il giocatore non ha abbastanza mana
		{
			System.out.println(giocatori[turno].nome + " non ha abbastanza mana per aggiungere " + carta.nome + " (costa " + carta.costoMana + " e hai " + manaAtt + ")");
		}
		else
		{
			manaAtt -= carta.costoMana;
			carteNelCampo[turno].add(carta);
			mano[turno].remove(posMazzo);
			
			notificaClient();
			
			System.out.println(giocatori[turno].nome + " (" + turno + "), ha aggiunto una nuova carta sul campo, cioè "
					+ carta.nome);
			
			mostraCampoBattaglia(-1);
		}
	}
	
	// Quando il giocatore attacca una carta dell'avversario
	public void attacca(int idCartaAtt, int idCartaAvv)
	{
		int avversario = turno + 1;
		if(avversario == NUM_GG) avversario = 0;
		
		System.out.println(turno + " " + avversario);
		
		if(carteNelCampo[turno].get(idCartaAtt) != null && carteNelCampo[avversario].get(idCartaAvv) != null) // Se le carte esistono veramente
		{
			Carta att = carteNelCampo[turno].get(idCartaAtt);
			
			if(att.giocatePerTurnoAtt > 0)
			{
				Carta avv = carteNelCampo[avversario].get(idCartaAvv);
				
				carteNelCampo[turno].get(idCartaAtt).giocatePerTurnoAtt--;
				System.out.println(giocatori[turno].nome + " (" + att.nome + ") sta attcando " + giocatori[avversario].nome + " (" + avv.nome + ")");
				
				// Rimuovi la vita della carta dell'avversario
				avv.saluteAtt -= att.attaccoAtt;
				if(avv.saluteAtt > 0) System.out.println("\nLa carta dell'avversario ha perso " + att.attaccoAtt + " HP (rimasti " + avv.saluteAtt + " HP)\n");
				else
				{
					carteNelCampo[avversario].remove(idCartaAvv);
					System.out.println("\nLa carta dell'avversario è stata distrutta\n");
				}

				// Rimuovi la vita della carta dell'attaccante
				att.saluteAtt -= avv.attaccoAtt;
				if(att.saluteAtt > 0) System.out.println("\nLa carta dell'attaccante ha perso " + avv.attaccoAtt + " HP (rimasti " + att.saluteAtt + " HP)\n");
				else
				{
					carteNelCampo[turno].remove(idCartaAtt);
					System.out.println("\nLa carta dell'attaccante è stata distrutta\n");
				}
				
				notificaClient();
			}
			else
			{
				System.out.println("Hai gia usato questa carta durante questo turno");
			}
		}
		else System.out.println("Le carte non esistono sul campo da battaglia. Attacco annullato.");
		
		riepilogoPartita();
	}
	
	public void pescaCarta(int giocatore, int nCarte)
	{
		for (int i = 0; i < nCarte; i++)
		{
			if(combattenti[giocatore].deck.size() > 0)
			{
				if(combattenti[giocatore].mano.size() < MAX_CARTE_MANO)
				{
					Carta cartaPescata = Carta.cartaToCartaPartita(combattenti[giocatore].deck.get(0));
					combattenti[giocatore].deck.remove(0);
					combattenti[giocatore].mano.add(cartaPescata);
					
					System.out.println("Il giocatore " + giocatore + " (" + giocatori[giocatore].nome + ") "
							+ "ha pescato " + cartaPescata.nome + " (ATT: " + cartaPescata.attaccoAtt + " / HP: " + cartaPescata.saluteAtt + ").");
					aggiornaAltroGiocatore(new AggiornamentoPartita(AzionePartita.Pesca)); // No param. perche tanto si pesca sempre la prima carta
				}
				else
				{
					System.out.println(giocatori[turno].nome + " ha raggiunto il numero max di carte nella mano (" + MAX_CARTE_MANO + ")");
					return;
				}
			}
			else
			{
				System.out.println("Il giocatore " + giocatore + " (" + giocatori[giocatore].nome + ") "
						+ "non ha più carte nel deck.");
			}
		}
		
		System.out.println("Ci sono rimaste " + deck[giocatore].size() + " carte nel suo deck.\n");
	}
	
	public void mostraCampoBattaglia(int giocatore)
	{
		if(giocatore == -1) // Fai vedere le carte nel campo di tutti i giocatori
		{
			System.out.println("\n-- Lista carte nel campo di battaglia --\n");
			
			for (int i = 0; i < NUM_GG; i++)
			{
				if(carteNelCampo[i].size() == 0) // Se non ha nessuna carta, fai vedere un bel messaggio
				{
					System.out.println("Il giocatore " + i + " (" + giocatori[i].nome + "), non ha nessuna carta nel campo da battaglia");
				}
				else
				{
					System.out.println("\nGiocatore " + i + " (" + giocatori[i].nome + ")");
					
					for (int j = 0; j < carteNelCampo[i].size(); j++)
					{
						System.out.println("Carta " + j + "\n"
								+ "Nome: " + carteNelCampo[i].get(j).nome + "\n"
								+ "Salute: " + carteNelCampo[i].get(j).saluteAtt + "\n"
								+ "Attacco: " + carteNelCampo[i].get(j).attaccoAtt + "\n");
						
						if(giocatore == turno)
						{
							if(carteNelCampo[turno].get(j).giocatePerTurnoAtt > 0) System.out.println("Carta giocabile\n");
							else System.out.println("Carta già giocata questo turno\n");
						}
					}
				}
			}
		}
		else // Fai vedere le carte in campo di un determinato giocatore
		{
			System.out.println("\nGiocatore " + giocatore + " (" + giocatori[giocatore].nome + ")");
			
			for (int j = 0; j < carteNelCampo[giocatore].size(); j++)
			{
				System.out.println("Carta " + j + "\n"
						+ "Nome: " + carteNelCampo[giocatore].get(j).nome + "\n"
						+ "Salute: " + carteNelCampo[giocatore].get(j).saluteAtt + "\n"
						+ "Attacco: " + carteNelCampo[giocatore].get(j).attaccoAtt + "\n");
			}
		}
		
		System.out.println("\n");
	}
	
	public void mostraMano(int giocatore)
	{
		if(mano[giocatore].size() == 0)
		{
			System.out.println("Non hai nessuna carta nella mano");
		}
		else
		{
			for (int j = 0; j < mano[giocatore].size(); j++)
			{
				System.out.println("Carta " + j + "\n"
						+ "Nome: " + mano[giocatore].get(j).nome + "\n"
						+ "Salute: " + mano[giocatore].get(j).saluteAtt + "\n"
						+ "Attacco: " + mano[giocatore].get(j).attaccoAtt + "\n"
						+ "Costo mana: " + mano[giocatore].get(j).costoMana + "\n");
			}
		}
	}
	
	public void riepilogoPartita()
	{
		String riepilogo = "\n-- Riepilogo partita --\n";
		
		//riepilogo += "Ci sono " + NUM_GG + " giocatori\n";
		
		for (int i = 0; i < NUM_GG; i++)
		{
			riepilogo += "Giocatore " + i + ", " + giocatori[i].nome + ":\n";
			riepilogo += "Ha " + manaAtt + " mana\n";
			
			riepilogo += "Ha " + carteNelCampo[i].size() + " carte nel campo da battaglia:\n";
			for (int j = 0; j < carteNelCampo[i].size(); j++)
			{
				riepilogo += "Carta " + j + "\n"
						+ "Nome: " + carteNelCampo[i].get(j).nome + "\n"
						+ "Salute: " + carteNelCampo[i].get(j).saluteAtt + "\n"
						+ "Attacco: " + carteNelCampo[i].get(j).attaccoAtt + "\n";
			}
			
			riepilogo += "\n";
		}
		
		System.out.println(riepilogo);
	}
	
	public ArrayList<Carta> getRandomDeck(int[] deck)
	{
		ArrayList<Carta> randomDeck = new ArrayList<Carta>();
		for (int i = 0; i < deck.length; i++)
		{
			randomDeck.add(CollezioneCarte.collezioneCarte[deck[i]]);
		}
		
		Collections.shuffle(randomDeck);
		return randomDeck;
	}
	
	public void ripristinaGiocateCarte(int giocatore)
	{
		for (int i = 0; i < carteNelCampo[giocatore].size(); i++)
		{
			carteNelCampo[giocatore].get(i).giocatePerTurnoAtt = carteNelCampo[giocatore].get(i).giocatePerTurnoMax;
		}
	}
	
	public void aggiornaAltroGiocatore(AggiornamentoPartita agg)
	{
		if(server != null) // Siamo il Server
		{
			
		}
		else // Siamo il Client
		{
			
		}
	}
}