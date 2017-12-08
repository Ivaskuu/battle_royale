package battleroyale.Partita;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.google.gson.Gson;

import battleroyale.Carta;
import battleroyale.CollezioneCarte;
import battleroyale.Partita.AggiornamentoPartita.AzionePartita;

public class Partita
{
	public static final int MAX_GIOCATORI = 2;
	public static final int MAX_CARTE_MANO = 10;
	public static final int MAX_CARTE_CAMPO = 5;
	public static final int MAX_MANA = 10;
	
	public static MenuPartita menuPartita;
	
	public final int NUM_GG;
	public final int THIS_GG;

	public final Combattente combattente;
	public final String nomeAltroGG;
	
	public ArrayList<Carta>[] campo;
	public int turno;
	
	private PrintWriter altroGG;
	

	//
    // COSTRUTTORI
    //
	
	// Chiamata dal server
	// gg1=questo giocatore (server), gg2=l'altro giocatore (client)
	public Partita(GiocatoreSlim gg1, GiocatoreSlim gg2, PrintWriter altroGG)
	{
		this.altroGG = altroGG;
		this.NUM_GG = 2;
		
		this.turno = 0;
		this.THIS_GG = new Random().nextInt(2); // Se 0 inizio per primo
		
		campo = new ArrayList[NUM_GG];
		campo[0] = new ArrayList<Carta>();
		campo[1] = new ArrayList<Carta>();
		
		combattente = new Combattente(gg1.nome, 1, getShuffledDeck(gg1.deck));
		nomeAltroGG = gg2.nome;
		
		initClient();
		
		System.out.println("\nE' cominciata una nuova partita.");
		if(THIS_GG == 0) System.out.println("Giochi tu per primo\n");
		else System.out.println("Inizia l'altro giocatore\n");
		
		pescaCarta(); pescaCarta(); pescaCarta(); if(THIS_GG == 1) pescaCarta();
		riepilogoPartita();
		
		menuPartita = new MenuPartita(this);
	}
	
	public void initClient()
	{
		altroGG.println(contrario(THIS_GG));
	}
	
	// Chiamata dal client dopo la rispostadel server
	// gg1=questo giocatore (client), gg2=l'altro giocatore (server)
	public Partita(GiocatoreSlim gg1, GiocatoreSlim gg2, int posThisGG, PrintWriter altroGG)
	{		
		this.altroGG = altroGG;
		
		this.NUM_GG = 2;
		this.THIS_GG = posThisGG;

		this.turno = 0;
		
		campo = new ArrayList[NUM_GG];
		campo[0] = new ArrayList<Carta>();
		campo[1] = new ArrayList<Carta>();
		
		combattente = new Combattente(gg1.nome, 1, getShuffledDeck(gg1.deck));
		nomeAltroGG = gg2.nome;
		
		System.out.println("\nE' cominciata una nuova partita.");
		if(THIS_GG == 0) System.out.println("Giochi tu per primo\n");
		else System.out.println("Inizia l'altro giocatore\n");
		
		pescaCarta(); pescaCarta(); pescaCarta(); if(THIS_GG == 1) pescaCarta();
		riepilogoPartita();
		
		menuPartita = new MenuPartita(this);
	}
	

	//
    // FUNZIONI AZIONI PARTITA
    //
	
	public void toccaMe()
	{
		turno = THIS_GG;

		//controllaEffettiCarte(); TODO
		ripristinaGiocateCarte();
		
		if(combattente.manaMax < MAX_MANA) combattente.manaMax++;
		combattente.manaAtt = combattente.manaMax;
		
		System.out.println("\n-- Turno cambiato --");
		System.out.println("Tocca giocare a te");
		
		pescaCarta();
		riepilogoPartita();
	}
	
	public void toccaTe()
	{
		turno = contrario(turno);
		aggiornaAltroGiocatore(new AggiornamentoPartita(AzionePartita.CambiaTurno));
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
		if(campo[THIS_GG].size() >= MAX_CARTE_CAMPO) // Se ci sono troppe carte sul campo
		{
			System.out.println("Hai raggiunto il numero max di carte sul campo (" + MAX_CARTE_CAMPO + ")");
			return;
		}

		Carta carta = combattente.mano.get(posMazzo);
		if(carta.costoMana > combattente.manaAtt) // Se il giocatore non ha abbastanza mana
		{
			System.out.println("Non hai abbastanza mana per aggiungere " + carta.nome + " (costa " + carta.costoMana + " ma hai " + combattente.manaAtt + ")");
		}
		else
		{
			combattente.manaAtt -= carta.costoMana;
			campo[THIS_GG].add(carta);
			combattente.mano.remove(posMazzo);

			aggiornaAltroGiocatore(new AggiornamentoPartita(AzionePartita.AggiungiCartaSulCampo, carta));
			
			System.out.println(carta.nome + " � stato/a aggiunto/a sul campo");			
			mostraCampoBattaglia(-1);
		}
	}
	
	public void haAggiuntoCartaSulCampo(Carta carta)
	{
		campo[contrario(THIS_GG)].add(carta);
		System.out.println("L'avversario ha aggiunto " + carta.nome + " (" + carta.attaccoAtt + "/" + carta.saluteAtt + ") sul campo");
	}
	
	// Quando il giocatore attacca una carta dell'avversario
	public void attacca(int idCartaAtt, int idCartaAvv)
	{
		if(campo[THIS_GG].get(idCartaAtt) != null && campo[contrario(THIS_GG)].get(idCartaAvv) != null) // Se le carte esistono veramente
		{
			Carta att = campo[THIS_GG].get(idCartaAtt);
			
			if(att.giocatePerTurnoAtt > 0)
			{
				Carta avv = campo[contrario(THIS_GG)].get(idCartaAvv);
				
				campo[THIS_GG].get(idCartaAtt).giocatePerTurnoAtt--;
				System.out.println("Il tuo " + att.nome + " (" + att.attaccoAtt + "/" + att.saluteAtt + ") sta attcando " + avv.nome + " (" + avv.attaccoAtt + "/" + avv.saluteAtt + ")");
				
				// Rimuovi la vita della carta dell'avversario
				avv.saluteAtt -= att.attaccoAtt;
				if(avv.saluteAtt > 0) System.out.println("\nLa carta dell'avversario ha perso " + att.attaccoAtt + " HP (rimasti " + avv.saluteAtt + " HP)\n");
				else
				{
					campo[contrario(THIS_GG)].remove(idCartaAvv);
					System.out.println("\nLa carta dell'avversario � stata distrutta\n");
				}

				// Rimuovi la vita della carta dell'attaccante
				att.saluteAtt -= avv.attaccoAtt;
				if(att.saluteAtt > 0) System.out.println("\nLa tua carta ha perso " + avv.attaccoAtt + " HP (rimasti " + att.saluteAtt + " HP)\n");
				else
				{
					campo[THIS_GG].remove(idCartaAtt);
					System.out.println("\nLa tua carta � stata distrutta\n");
				}
				
				aggiornaAltroGiocatore(new AggiornamentoPartita(AzionePartita.Attacco, new int[] {idCartaAtt, idCartaAvv}));
			}
			else
			{
				System.out.println("Hai gia usato questa carta durante questo turno");
			}
		}
		else System.out.println("Le carte non esistono sul campo da battaglia.");
		
		mostraCampoBattaglia(-1);
	}
	
	// Quando sta giocando l'altro e ci attacca
	public void farsiAttacare(int posCartaSua, int posCartaMia)
	{
		Carta cartaMia = campo[THIS_GG].get(posCartaMia);
		Carta cartaSua = campo[contrario(THIS_GG)].get(posCartaSua);
		
		cartaSua.giocatePerTurnoAtt--;
		System.out.println("Il tuo " + cartaMia.nome + " (" + cartaMia.attaccoAtt + "/" + cartaMia.saluteAtt + ") si sta facendo attcare da " + cartaSua.nome + " (" + cartaSua.attaccoAtt + "/" + cartaSua.saluteAtt + ")");
		
		// Rimuovi la vita della carta dell'avversario cioe io
		cartaMia.saluteAtt -= cartaSua.attaccoAtt;
		if(cartaMia.saluteAtt > 0) System.out.println("\nLa tua carta ha perso " + cartaSua.attaccoAtt + " HP (rimasti " + cartaMia.saluteAtt + " HP)\n");
		else
		{
			campo[THIS_GG].remove(posCartaMia);
			System.out.println("\nLa tua carta � stata distrutta\n");
		}

		// Rimuovi la vita della carta dell'attaccante cioe lui
		cartaSua.saluteAtt -= cartaMia.attaccoAtt;
		if(cartaSua.saluteAtt > 0) System.out.println("\nLa sua carta ha perso " + cartaMia.attaccoAtt + " HP (rimasti " + cartaSua.saluteAtt + " HP)\n");
		else
		{
			campo[contrario(THIS_GG)].remove(posCartaSua);
			System.out.println("\nLa sua carta � stata distrutta\n");
		}
	}
	
	public void pescaCarta()
	{
		if(combattente.deck.size() > 0) // Se ci sono ancora carte nel deck
		{
			if(combattente.mano.size() < MAX_CARTE_MANO) // Se c'� ancora posto per una carta nella mano
			{
				Carta cartaPescata = Carta.cartaToCartaPartita(combattente.deck.get(0)); // Prendi la prima carta del deck
				combattente.deck.remove(0); // Rimuovila daldeck
				combattente.mano.add(cartaPescata); // Aggiungila nella mano
				
				System.out.println("Hai pescato " + cartaPescata.nome + " (ATT: " + cartaPescata.attaccoAtt + " / HP: " + cartaPescata.saluteAtt + ").");
				aggiornaAltroGiocatore(new AggiornamentoPartita(AzionePartita.Pesca)); // No param. perche l'altro gg non deve sapere le tue carte
			}
			else
			{
				System.out.println("Hia raggiunto il numero max di carte nella mano (" + MAX_CARTE_MANO + ")");
				return;
			}
		}
		else
		{
			System.out.println("Il tuo deck e vuoto");
		}
		
		System.out.println("Sono rimaste " + combattente.deck.size() + " carte nel tuo deck.\n");
	}
	
	
	//
    // FUNZIONI INFORMAZIONI
    //
	
	public void mostraCampoBattaglia(int giocatore)
	{
		if(giocatore == -1) // Fai vedere le carte nel campo di entrambi i giocatori
		{
			System.out.println("\n-- Lista carte nel campo di battaglia --\n");
			
			for (int i = 0; i < NUM_GG; i++)
			{
				if(campo[i].size() == 0) // Se non ha nessuna carta, fai vedere un bel messaggio
				{
					if(i == THIS_GG) System.out.println("Non hai nessuna carta nel campo");
					else System.out.println("L'avversario non ha nessuna carta sul campo");
				}
				else
				{
					if(i == THIS_GG) System.out.println("\nLe mie carte sul campo");
					else System.out.println("\nLe carte dell'avversario sul campo");
					
					for (int j = 0; j < campo[i].size(); j++)
					{
						System.out.println("Carta " + j + "\n"
								+ "Nome: " + campo[i].get(j).nome + "\n"
								+ "Salute: " + campo[i].get(j).saluteAtt + "\n"
								+ "Attacco: " + campo[i].get(j).attaccoAtt + "\n"
								+ "Effetto: " + "\n");
					}
				}
			}
		}
		else // Fai vedere le carte in campo di un determinato giocatore
		{
			for (int j = 0; j < campo[giocatore].size(); j++)
			{
				System.out.println("Carta " + j + "\n"
						+ "Nome: " + campo[giocatore].get(j).nome + "\n"
						+ "Salute: " + campo[giocatore].get(j).saluteAtt + "\n"
						+ "Attacco: " + campo[giocatore].get(j).attaccoAtt + "\n"
						+ "Effetto: " + "\n");
				
				if(campo[THIS_GG].get(j).giocatePerTurnoAtt > 0) System.out.println("Carta giocabile\n");
				else System.out.println("Carta gi� giocata questo turno\n");
			}
		}
		
		System.out.println("\n");
	}
	
	public void mostraMano()
	{
		if(combattente.mano.size() == 0)
		{
			System.out.println("Non hai nessuna carta nella mano");
		}
		else
		{
			for (int j = 0; j < combattente.mano.size(); j++)
			{
				System.out.println("Carta " + j + "\n"
						+ "Nome: " + combattente.mano.get(j).nome + "\n"
						+ "Salute: " + combattente.mano.get(j).saluteAtt + "\n"
						+ "Attacco: " + combattente.mano.get(j).attaccoAtt + "\n"
						+ "Costo mana: " + combattente.mano.get(j).costoMana + "\n");
			}
		}
	}
	
	public void riepilogoPartita()
	{
		System.out.println("\n-- Riepilogo partita --\n");
		System.out.println("Giocatore " + combattente.nome + "\n"
				+ "Hai " + combattente.manaAtt + " di mana\n"
					+ "Hai " + combattente.deck.size() + " carte nel deck\n"
						+ "Hai " + combattente.mano.size() + " carte nella mano\n"
							+ "Hai " + campo[THIS_GG].size() + " carte sul campo\n");
	}
	

	//
    // FUNZIONI UTILITA
    //
	
	public ArrayList<Carta> getShuffledDeck(int[] deck)
	{
		ArrayList<Carta> randomDeck = new ArrayList<Carta>();
		for (int i = 0; i < deck.length; i++)
		{
			randomDeck.add(CollezioneCarte.collezioneCarte[deck[i]]);
		}
		
		Collections.shuffle(randomDeck);
		return randomDeck;
	}
	
	public void ripristinaGiocateCarte()
	{
		for (int i = 0; i < campo[THIS_GG].size(); i++)
		{
			campo[THIS_GG].get(i).giocatePerTurnoAtt = campo[THIS_GG].get(i).giocatePerTurnoMax;
		}
	}
	
	public int contrario(int num)
	{
		if(num != 0 && num != 1) return -1; 
		return num == 1 ? 0 : 1;
	}
	

	//
    // FUNZIONI MULTIPLAYER
    //
	
	public void aggiornaAltroGiocatore(AggiornamentoPartita agg)
	{
		altroGG.println(new Gson().toJson(agg));
	}
	
	public void riceviAggiornamento(AggiornamentoPartita agg)
	{
		if(agg.azione == AzionePartita.Pesca)
		{
			System.out.println("L'altro giocatore ha pescato una carta");
		}
	}
}