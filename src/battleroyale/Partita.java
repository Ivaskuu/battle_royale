package battleroyale;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import battleroyale.Carta.TipoCarta;

public class Partita
{
	public static final int MAX_GIOCATORI = 2;
	public static final int MAX_CARTE_MANO = 10;
	public static final int MAX_CARTE_CAMPO = 1;
	public static final int MAX_MANA = 10;
	
	public final int NUM_GG;
	public final int PRIMO_GG; // Soluzione temporanea (pigra) per manaMax TODO
	public final Giocatore[] giocatori;
	
	// Il mana dei 2 o piu giocatori
	public int manaMax;
	public int manaAtt;
	
	// Le carte piazzate dai giocatori nel campo di battaglia
	public ArrayList<Carta>[] carteNelCampo; // Array di ArrayList
	
	// Le carte che il giocatore ha pescato ma non aggiunto nel campo di battaglia
	public ArrayList<Carta>[] mano; // Array di ArrayList
	
	// Il deck del giocatore
	public ArrayList<Carta>[] deck; // Array di ArrayList
	
	// Le carte piazzate dai giocatori nel campo di battaglia
	// public ArrayList<Carta> carteDistrutte; // Serve davvero ??
	
	// Quale giocatore sta giocando adesso
	public int turno = 0;
	
	public Partita(Giocatore[] giocatori)
	{
		NUM_GG = giocatori.length;
		this.giocatori = giocatori;
		
		carteNelCampo = new ArrayList[NUM_GG];
		mano = new ArrayList[NUM_GG];
		deck = new ArrayList[NUM_GG];

		PRIMO_GG = new Random().nextInt(NUM_GG);
		turno = PRIMO_GG;
		
		manaMax = 1;
		manaAtt = manaMax;

		System.out.println("\nE' cominciata una nuova partita.");
		System.out.println("Gioca per primo " + giocatori[turno].nomeGiocatore + ", cioè il giocatore numero " + turno + "\n");
		
		// Inizializza i giocatori
		for(int i = 0; i < NUM_GG; i++)
		{
			carteNelCampo[i] = new ArrayList<Carta>();
			mano[i] = new ArrayList<Carta>();
			deck[i] = new ArrayList<Carta>();
			
			copiaEMescolaDeck(i);
			
			pescaCarta(i, 3);
		}
		
		riepilogoPartita();
	}
	
	public void cambiaTurno()
	{
		turno++;
		if(turno == MAX_GIOCATORI) turno = 0;

		controllaEffettiCarte();
		
		if(turno == PRIMO_GG && manaMax < MAX_MANA) manaMax++;
		manaAtt = manaMax;
		
		notificaClient();
		System.out.println("\n-- Turno cambiato --");
		System.out.println("Tocca giocare a " + giocatori[turno].nomeGiocatore);
		
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
			System.out.println(giocatori[turno].nomeGiocatore + " ha raggiunto il numero max di carte sul campo (" + MAX_CARTE_CAMPO + ")");
			return;
		}

		Carta carta = mano[turno].get(posMazzo);
		
		if(carta.costoMana > manaAtt) // Se il giocatore non ha abbastanza mana
		{
			System.out.println(giocatori[turno].nomeGiocatore + " non ha abbastanza mana per aggiungere " + carta.nome + " (costa " + carta.costoMana + " e hai " + manaAtt + ")");
		}
		else
		{
			manaAtt -= carta.costoMana;
			carteNelCampo[turno].add(carta);
			mano[turno].remove(posMazzo);
			
			notificaClient();
			
			System.out.println(giocatori[turno].nomeGiocatore + " (" + turno + "), ha aggiunto una nuova carta sul campo, cioè "
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
			Carta avv = carteNelCampo[avversario].get(idCartaAvv);
			
			System.out.println(giocatori[turno].nomeGiocatore + " (" + att.nome + ") sta attcando " + giocatori[avversario].nomeGiocatore + " (" + avv.nome + ")");
			
			// Rimuovi la vita della carta dell'avversario
			avv.salute -= att.attacco;
			if(avv.salute > 0) System.out.println("\nLa carta dell'avversario ha perso " + att.attacco + " HP (rimasti " + avv.salute + " HP)\n");
			else
			{
				carteNelCampo[avversario].remove(idCartaAvv);
				System.out.println("\nLa carta dell'avversario è stata distrutta\n");
			}

			// Rimuovi la vita della carta dell'attaccante
			att.salute -= avv.attacco;
			if(att.salute > 0) System.out.println("\nLa carta dell'attaccante ha perso " + avv.attacco + " HP (rimasti " + att.salute + " HP)\n");
			else
			{
				carteNelCampo[turno].remove(idCartaAtt);
				System.out.println("\nLa carta dell'attaccante è stata distrutta\n");
			}
			
			notificaClient();
		}
		else System.out.println("Le carte non esistono sul campo da battaglia. Attacco annullato.");
		
		riepilogoPartita();
	}
	
	public void pescaCarta(int giocatore, int nCarte)
	{
		for (int i = 0; i < nCarte; i++)
		{
			if(deck[giocatore].size() > 0)
			{
				if(mano[giocatore].size() < MAX_CARTE_MANO)
				{
					Carta cartaPescata = deck[giocatore].get(0);
					deck[giocatore].remove(0);
					mano[giocatore].add(cartaPescata);
					
					System.out.println("Il giocatore " + giocatore + " (" + giocatori[giocatore].nomeGiocatore + ") "
							+ "ha pescato " + cartaPescata.nome + " (HP: " + cartaPescata.salute + " / ATT: " + cartaPescata.attacco + ").");
				}
				else
				{
					System.out.println(giocatori[turno].nomeGiocatore + " ha raggiunto il numero max di carte nella mano (" + MAX_CARTE_MANO + ")");
					return;
				}
			}
			else
			{
				System.out.println("Il giocatore " + giocatore + " (" + giocatori[giocatore].nomeGiocatore + ") "
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
					System.out.println("Il giocatore " + i + " (" + giocatori[i].nomeGiocatore + "), non ha nessuna carta nel campo da battaglia");
				}
				else
				{
					System.out.println("\nGiocatore " + i + " (" + giocatori[i].nomeGiocatore + ")");
					
					for (int j = 0; j < carteNelCampo[i].size(); j++)
					{
						System.out.println("Carta " + j + "\n"
								+ "Nome: " + carteNelCampo[i].get(j).nome + "\n"
								+ "Salute: " + carteNelCampo[i].get(j).salute + "\n"
								+ "Attacco: " + carteNelCampo[i].get(j).attacco + "\n");
					}
				}
			}
		}
		else // Fai vedere le carte in campo di un determinato giocatore
		{
			System.out.println("\nGiocatore " + giocatore + " (" + giocatori[giocatore].nomeGiocatore + ")");
			
			for (int j = 0; j < carteNelCampo[giocatore].size(); j++)
			{
				System.out.println("Carta " + j + "\n"
						+ "Nome: " + carteNelCampo[giocatore].get(j).nome + "\n"
						+ "Salute: " + carteNelCampo[giocatore].get(j).salute + "\n"
						+ "Attacco: " + carteNelCampo[giocatore].get(j).attacco + "\n");
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
						+ "Salute: " + mano[giocatore].get(j).salute + "\n"
						+ "Attacco: " + mano[giocatore].get(j).attacco + "\n"
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
			riepilogo += "Giocatore " + i + ", " + giocatori[i].nomeGiocatore + ":\n";
			riepilogo += "Ha " + manaAtt + " mana\n";
			
			riepilogo += "Ha " + carteNelCampo[i].size() + " carte nel campo da battaglia:\n";
			for (int j = 0; j < carteNelCampo[i].size(); j++)
			{
				riepilogo += "Carta " + j + "\n"
						+ "Nome: " + carteNelCampo[i].get(j).nome + "\n"
						+ "Salute: " + carteNelCampo[i].get(j).salute + "\n"
						+ "Attacco: " + carteNelCampo[i].get(j).attacco + "\n";
			}
			
			riepilogo += "\n";
		}
		
		System.out.println(riepilogo);
	}
	
	public void copiaEMescolaDeck(int giocatore)
	{
		for (int j = 0; j < giocatori[giocatore].deck.length; j++)
		{
			deck[giocatore].add(giocatori[giocatore].deck[j]);
		}
		
		Collections.shuffle(deck[giocatore]);
	}
	
	public void notificaClient()
	{
		// TODO: Notifica i client
	}
}