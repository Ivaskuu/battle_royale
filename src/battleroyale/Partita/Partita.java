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
	public final int PRIMO_GG;

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
		
		// Inizializza i giocatori (combattenti)
		for(int i = 0; i < NUM_GG; i++)
		{
			// Dai un mana al primo giocatore, e 0 agli altri
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
		
		if(turno == PRIMO_GG && combattenti[turno].manaMax < MAX_MANA) combattenti[turno].manaMax++;
		combattenti[turno].manaAtt = combattenti[turno].manaMax;
		
		//notificaClient();
		System.out.println("\n-- Turno cambiato --");
		System.out.println("Tocca giocare a " + combattenti[turno].nome);
		
		pescaCarta(turno, 1);
		
		riepilogoPartita();
	}
	
	public void controllaEffettiCarte()
	{
		/*for (int i = 0; i < carteNelCampo.length; i++)
		{
			for (int j = 0; j < carteNelCampo[i].size(); j++)
			{
				// TODO: Eseguire i effetti della carta
			}
		}*/
	}
	
	// Aggiungi una carta nel campo di battaglia dalla mano
	public void aggiungiCartaSulCampo(int posMazzo)
	{
		if(combattenti[turno].campo.size() >= MAX_CARTE_CAMPO) // Se ci sono troppe carte sul campo
		{
			System.out.println("Hai raggiunto il numero max di carte sul campo (" + MAX_CARTE_CAMPO + ")");
			return;
		}

		Carta carta = combattenti[turno].mano.get(posMazzo);
		
		if(carta.costoMana > combattenti[turno].manaAtt) // Se il giocatore non ha abbastanza mana
		{
			System.out.println(combattenti[turno].nome + " non ha abbastanza mana per aggiungere " + carta.nome + " (costa " + carta.costoMana + " e hai " + combattenti[turno].manaAtt + ")");
		}
		else
		{
			combattenti[turno].manaAtt -= carta.costoMana;
			combattenti[turno].campo.add(carta);
			combattenti[turno].mano.remove(posMazzo);
			
			//notificaClient();
			
			System.out.println(carta.nome + " è stato/a aggiunto/a sul campo");			
			mostraCampoBattaglia(-1);
		}
	}
	
	// Quando il giocatore attacca una carta dell'avversario
	public void attacca(int idCartaAtt, int idCartaAvv)
	{
		int avversario = turno + 1 == NUM_GG ? 0 : turno + 1;
		
		if(combattenti[turno].campo.get(idCartaAtt) != null && combattenti[avversario].campo.get(idCartaAvv) != null) // Se le carte esistono veramente
		{
			Carta att = combattenti[turno].campo.get(idCartaAtt);
			
			if(att.giocatePerTurnoAtt > 0)
			{
				Carta avv =combattenti[avversario].campo.get(idCartaAvv);
				
				combattenti[turno].campo.get(idCartaAtt).giocatePerTurnoAtt--;
				System.out.println("Il tuo " + att.nome + " (" + att.saluteAtt + "/" + att.attaccoAtt + ") sta attcando " + avv.nome + " (" + avv.saluteAtt + "/" + avv.attaccoAtt + ")");
				
				// Rimuovi la vita della carta dell'avversario
				avv.saluteAtt -= att.attaccoAtt;
				if(avv.saluteAtt > 0) System.out.println("\nLa carta dell'avversario ha perso " + att.attaccoAtt + " HP (rimasti " + avv.saluteAtt + " HP)\n");
				else
				{
					combattenti[avversario].campo.remove(idCartaAvv);
					System.out.println("\nLa carta dell'avversario è stata distrutta\n");
				}

				// Rimuovi la vita della carta dell'attaccante
				att.saluteAtt -= avv.attaccoAtt;
				if(att.saluteAtt > 0) System.out.println("\nLa tua carta ha perso " + avv.attaccoAtt + " HP (rimasti " + att.saluteAtt + " HP)\n");
				else
				{
					combattenti[turno].campo.remove(idCartaAtt);
					System.out.println("\nLa tua carta è stata distrutta\n");
				}
				
				//notificaClient();
			}
			else
			{
				System.out.println("Hai gia usato questa carta durante questo turno");
			}
		}
		else System.out.println("Le carte non esistono sul campo da battaglia.");
		
		mostraCampoBattaglia(-1);
	}
	
	public void pescaCarta(int giocatore, int nCarte)
	{
		for (int i = 0; i < nCarte; i++) // Per ogni carta da pescare
		{
			if(combattenti[giocatore].deck.size() > 0) // Se ci sono ancora carte nel deck
			{
				if(combattenti[giocatore].mano.size() < MAX_CARTE_MANO) // Se c'è ancora posto per una carta nella mano
				{
					Carta cartaPescata = Carta.cartaToCartaPartita(combattenti[giocatore].deck.get(0)); // Prendi la prima carta del deck
					combattenti[giocatore].deck.remove(0); // Rimuovila daldeck
					combattenti[giocatore].mano.add(cartaPescata); // Aggiungila nella mano
					
					System.out.println("Hai pescato " + cartaPescata.nome + " (ATT: " + cartaPescata.attaccoAtt + " / HP: " + cartaPescata.saluteAtt + ").");
					aggiornaAltroGiocatore(new AggiornamentoPartita(AzionePartita.Pesca)); // No param. perche tanto si pesca sempre la prima carta
				}
				else
				{
					System.out.println("Hia raggiunto il numero max di carte nella mano (" + MAX_CARTE_MANO + ")");
					return;
				}
			}
			else
			{
				System.out.println("Non hai più carte nel deck.");
			}
		}
		
		System.out.println("Sono rimaste " + combattenti[giocatore].deck.size() + " carte nel tuo deck.\n");
	}
	
	public void mostraCampoBattaglia(int giocatore)
	{
		if(giocatore == -1) // Fai vedere le carte nel campo di tutti i giocatori
		{
			System.out.println("\n-- Lista carte nel campo di battaglia --\n");
			
			for (int i = 0; i < NUM_GG; i++)
			{
				if(combattenti[i].campo.size() == 0) // Se non ha nessuna carta, fai vedere un bel messaggio
				{
					System.out.println("Il giocatore " + i + " (" + combattenti[i].nome + "), non ha nessuna carta nel campo da battaglia");
				}
				else
				{
					System.out.println("\nGiocatore " + i + " (" + combattenti[i].nome + ")");
					
					for (int j = 0; j < combattenti[i].campo.size(); j++)
					{
						System.out.println("Carta " + j + "\n"
								+ "Nome: " + combattenti[i].campo.get(j).nome + "\n"
								+ "Salute: " + combattenti[i].campo.get(j).saluteAtt + "\n"
								+ "Attacco: " + combattenti[i].campo.get(j).attaccoAtt + "\n"
								+ "Effetto: " + "\n");
					}
				}
			}
		}
		else // Fai vedere le carte in campo di un determinato giocatore
		{
			for (int j = 0; j < combattenti[giocatore].campo.size(); j++)
			{
				System.out.println("Carta " + j + "\n"
						+ "Nome: " + combattenti[giocatore].campo.get(j).nome + "\n"
						+ "Salute: " + combattenti[giocatore].campo.get(j).saluteAtt + "\n"
						+ "Attacco: " + combattenti[giocatore].campo.get(j).attaccoAtt + "\n"
						+ "Effetto: " + "\n");
				
				if(combattenti[turno].campo.get(j).giocatePerTurnoAtt > 0) System.out.println("Carta giocabile\n");
				else System.out.println("Carta già giocata questo turno\n");
			}
		}
		
		System.out.println("\n");
	}
	
	public void mostraMano(int giocatore)
	{
		if(combattenti[giocatore].mano.size() == 0)
		{
			System.out.println("Non hai nessuna carta nella mano");
		}
		else
		{
			for (int j = 0; j < combattenti[giocatore].mano.size(); j++)
			{
				System.out.println("Carta " + j + "\n"
						+ "Nome: " + combattenti[giocatore].mano.get(j).nome + "\n"
						+ "Salute: " + combattenti[giocatore].mano.get(j).saluteAtt + "\n"
						+ "Attacco: " + combattenti[giocatore].mano.get(j).attaccoAtt + "\n"
						+ "Costo mana: " + combattenti[giocatore].mano.get(j).costoMana + "\n");
			}
		}
	}
	
	public void riepilogoPartita()
	{
		String riepilogo = "\n-- Riepilogo partita --\n";
		
		for (int i = 0; i < NUM_GG; i++)
		{
			riepilogo += "Giocatore " + i + ", " + combattenti[i].nome + ":\n";
			riepilogo += "Ha " + combattenti[i].manaAtt + " mana\n";
			
			riepilogo += "Ha " + combattenti[i].campo.size() + " carte nel campo da battaglia:\n";
			for (int j = 0; j < combattenti[i].campo.size(); j++)
			{
				riepilogo += "Carta " + j + "\n"
						+ "Nome: " + combattenti[i].campo.get(j).nome + "\n"
						+ "Salute: " + combattenti[i].campo.get(j).saluteAtt + "\n"
						+ "Attacco: " + combattenti[i].campo.get(j).attaccoAtt + "\n";
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
		for (int i = 0; i < combattenti[giocatore].campo.size(); i++)
		{
			combattenti[giocatore].campo.get(i).giocatePerTurnoAtt = combattenti[giocatore].campo.get(i).giocatePerTurnoMax;
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