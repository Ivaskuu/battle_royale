package battleroyale.ClassiPartita;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.google.gson.Gson;

import battleroyale.Carta;
import battleroyale.ClassiPartita.Effetti;
import battleroyale.Carta.AbilitaCarta;
import battleroyale.CollezioneCarte;
import battleroyale.EffettoSpeciale.TriggerEffetto;
import battleroyale.ClassiPartita.AggiornamentoPartita.AzionePartita;

public class Partita
{
	public static final int MAX_GIOCATORI = 2;
	public static final int MAX_CARTE_MANO = 10;
	public static final int MAX_CARTE_CAMPO = 5;
	public static final int MAX_MANA = 10;
	
	public static MenuPartita menuPartita;
	
	public final int NUM_GG;
	public final int THIS_GG;
	
	public final GiocatoreSlim ggIo;
	public final GiocatoreSlim ggAvversario;

	public final Combattente combattente;
	
	public ArrayList<Carta>[] campo;
	public int turno;

	private PrintWriter output;
	

	//
    // COSTRUTTORI
    //
	
	// Chiamata dal server
	// gg1=questo giocatore (server), gg2=l'altro giocatore (client)
	public Partita(GiocatoreSlim gg1, GiocatoreSlim gg2, BufferedReader input, PrintWriter output)
	{
		this.output = output;
		
		this.NUM_GG = 2;
		this.ggIo = gg1;
		this.ggAvversario = gg2;
		
		this.turno = 0;
		this.THIS_GG = new Random().nextInt(2); // Se 0 inizio per primo
		
		campo = new ArrayList[NUM_GG];
		campo[0] = new ArrayList<Carta>();
		campo[1] = new ArrayList<Carta>();
		
		combattente = new Combattente(gg1.nome, 1, getShuffledDeck(gg1.deck));
		
		initClient(gg1);
		
		System.out.println("\nE' cominciata una nuova partita.");
		if(THIS_GG == 0)
		{
			System.out.println("Giochi tu per primo\n");
		}
		else
		{
			System.out.println("Inizia l'altro giocatore\n");
		}
		
		pescaCarta(); pescaCarta(); pescaCarta(); if(THIS_GG == 1) pescaCarta();
		riepilogoPartita();
		
		menuPartita = new MenuPartita(this, THIS_GG, input);
	}
	
	public void initClient(GiocatoreSlim gg1)
	{
		output.println(new Gson().toJson(gg1));
		output.println(contrario(THIS_GG));
	}
	
	// Chiamata dal client dopo la rispostadel server
	// gg1=questo giocatore (client), gg2=l'altro giocatore (server)
	public Partita(GiocatoreSlim gg1, GiocatoreSlim gg2, int posThisGG, BufferedReader input, PrintWriter output)
	{
		this.output = output;
		
		this.NUM_GG = 2;
		this.THIS_GG = posThisGG;
		
		this.ggIo = gg1;
		this.ggAvversario = gg2;

		this.turno = 0;
		
		campo = new ArrayList[NUM_GG];
		campo[0] = new ArrayList<Carta>();
		campo[1] = new ArrayList<Carta>();
		
		combattente = new Combattente(gg1.nome, 1, getShuffledDeck(gg1.deck));
		
		System.out.println("\nE' cominciata una nuova partita.");
		if(THIS_GG == 0)
		{
			System.out.println("Giochi tu per primo\n");
		}
		else
		{
			System.out.println("Inizia l'altro giocatore\n");
		}
		
		pescaCarta(); pescaCarta(); pescaCarta(); if(THIS_GG == 1) pescaCarta();
		riepilogoPartita();
		
		menuPartita = new MenuPartita(this, THIS_GG, input);
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
		//carta.giocatePerTurnoAtt = 0;
		if(carta.costoMana > combattente.manaAtt) // Se il giocatore non ha abbastanza mana
		{
			System.out.println("Non hai abbastanza mana per aggiungere " + carta.nome + " (costa " + carta.costoMana + " ma hai " + combattente.manaAtt + ")");
		}
		else  //Aggiungi la carta sul campo
		{
			combattente.manaAtt -= carta.costoMana;
			campo[THIS_GG].add(carta);
			combattente.mano.remove(posMazzo);

			aggiornaAltroGiocatore(new AggiornamentoPartita(AzionePartita.AggiungiCartaSulCampo, new Gson().toJson(carta)));
			
			System.out.println(carta.nome + " e' stato/a aggiunto/a sul campo");
			
			//Controllo se ha carica o furia o un effetto con il grido di battaglia
			for(int i=0;i<carta.abilitaCarta.length;i++)
			{
				if(carta.abilitaCarta[i]==AbilitaCarta.CARICA)
				{
					carta.giocatePerTurnoAtt=1;
					System.out.println("Questa carta ha carica, può attaccare in questo turno!");
				}
				else if(carta.abilitaCarta[i]==AbilitaCarta.FURIA)
				{
					carta.giocatePerTurnoMax=2;
					System.out.println("Questa carta ha furia, può attaccare 2 volte!");
				}
				else if(carta.abilitaCarta[i]==AbilitaCarta.GRIDO)
				{
					//Attivo l'effetto della carta con grido di battaglia 
					//Effetti.eseguiEffetto(this, carta.effetto, payload);
					
				}
			}
			
			mostraCampoGg(THIS_GG);
		}
	}
	
	public void haAggiuntoCartaSulCampo(Carta carta)
	{
		campo[contrario(THIS_GG)].add(carta);
		System.out.println("L'avversario ha aggiunto " + carta.nome + " (" + carta.attaccoAtt + "/" + carta.saluteAtt + ") sul campo");
	}
	
	// Quando il giocatore attacca una carta dell'avversario
	public void attacca(int posCartaAtt, int posCartaAvv)
	{
		if(posCartaAvv == -1) // Il giocatore vuole attacare l'erore dell'avversario
		{
			Carta att = campo[THIS_GG].get(posCartaAtt);
			
			if(att.giocatePerTurnoAtt > 0)
			{
				att.giocatePerTurnoAtt--;
				ggAvversario.salute -= att.attaccoAtt;
				
				System.out.println("\nL'eroe dell'avversario ha perso " + att.attaccoAtt + " HP (rimasti " + ggAvversario.salute + " HP)");
				aggiornaAltroGiocatore(new AggiornamentoPartita(AzionePartita.Attacco, new String[] {new Gson().toJson(posCartaAtt), new Gson().toJson(posCartaAvv)}));
				
				if(ggAvversario.salute <= 0)
				{
					System.out.println("\nL'eroe dell'avversario e' stato distrutto.\nHai vinto la partita!\n");
					aggiornaAltroGiocatore(new AggiornamentoPartita(AzionePartita.GameWin));
				}
			}
			else
			{
				System.out.println("Hai gia usato questa carta durante questo turno");
			}
		}
		else if(campo[THIS_GG].get(posCartaAtt) != null && campo[contrario(THIS_GG)].get(posCartaAvv) != null) // Se le carte esistono veramente
		{
			Carta att = campo[THIS_GG].get(posCartaAtt);
			
			if(att.giocatePerTurnoAtt > 0)
			{
				Carta avv = campo[contrario(THIS_GG)].get(posCartaAvv);
				
				campo[THIS_GG].get(posCartaAtt).giocatePerTurnoAtt--;
				System.out.println("Il tuo " + att.nome + " (" + att.attaccoAtt + "/" + att.saluteAtt + ") sta attcando " + avv.nome + " (" + avv.attaccoAtt + "/" + avv.saluteAtt + ")");
				
				// Rimuovi la vita della carta dell'avversario
				avv.saluteAtt -= att.attaccoAtt;
				if(avv.saluteAtt > 0) System.out.println("\nLa carta dell'avversario ha perso " + att.attaccoAtt + " HP (rimasti " + avv.saluteAtt + " HP)\n");
				else
				{
					campo[contrario(THIS_GG)].remove(posCartaAvv);
					System.out.println("\nLa carta dell'avversario e' stata distrutta\n");
				}

				// Rimuovi la vita della carta dell'attaccante
				att.saluteAtt -= avv.attaccoAtt;
				if(att.saluteAtt > 0) System.out.println("\nLa tua carta ha perso " + avv.attaccoAtt + " HP (rimasti " + att.saluteAtt + " HP)\n");
				else
				{
					campo[THIS_GG].remove(posCartaAtt);
					System.out.println("\nLa tua carta e' stata distrutta\n");
				}
				
				aggiornaAltroGiocatore(new AggiornamentoPartita(AzionePartita.Attacco, new String[] {new Gson().toJson(posCartaAtt), new Gson().toJson(posCartaAvv)}));
			}
			else
			{
				System.out.println("Hai gia usato questa carta durante questo turno");
			}
		}
		else System.out.println("Le carte non esistono sul campo da battaglia.");
		
		mostraCampoTabellaTuttiGg();
	}
	
	// Quando sta giocando l'altro e ci attacca
	public void farsiAttacare(int posCartaSua, int posCartaMia)
	{
		Carta cartaSua = campo[contrario(THIS_GG)].get(posCartaSua);
		
		if(posCartaMia == -1) // Sta attacando l'eroe
		{
			cartaSua.giocatePerTurnoAtt--;
			System.out.println("Il tuo eroe si sta facendo attaccare da " + cartaSua.nome + " (" + cartaSua.attaccoAtt + "/" + cartaSua.saluteAtt + ")");
			
			ggIo.salute -= cartaSua.attaccoAtt;
			System.out.println("Il tuo eroe ha perso " + cartaSua.attaccoAtt + " HP (rimasti " + ggIo.salute + ")");
			
			if(ggIo.salute <= 0) System.out.println("Il tuo eroe si e' fatto distruggere, e hai perso la partita.");
		}
		else
		{
			Carta cartaMia = campo[THIS_GG].get(posCartaMia);
			
			cartaSua.giocatePerTurnoAtt--;
			System.out.println("La tua carta " + cartaMia.nome + " (" + cartaMia.attaccoAtt + "/" + cartaMia.saluteAtt + ") si sta facendo attcare da " + cartaSua.nome + " (" + cartaSua.attaccoAtt + "/" + cartaSua.saluteAtt + ")");
			
			// Rimuovi la vita della carta dell'avversario cioe io
			cartaMia.saluteAtt -= cartaSua.attaccoAtt;
			if(cartaMia.saluteAtt > 0) System.out.println("La tua carta ha perso " + cartaSua.attaccoAtt + " HP (rimasti " + cartaMia.saluteAtt + " HP)\n");
			else
			{
				campo[THIS_GG].remove(posCartaMia);
				System.out.println("La tua carta e' stata distrutta\n");
			}
	
			// Rimuovi la vita della carta dell'attaccante cioe lui
			cartaSua.saluteAtt -= cartaMia.attaccoAtt;
			if(cartaSua.saluteAtt > 0) System.out.println("\nLa sua carta ha perso " + cartaMia.attaccoAtt + " HP (rimasti " + cartaSua.saluteAtt + " HP)\n");
			else
			{
				campo[contrario(THIS_GG)].remove(posCartaSua);
				System.out.println("\nLa sua carta e' stata distrutta\n");
			}
		}
	}
	
	public void pescaCarta()
	{
		if(combattente.deck.size() > 0) // Se ci sono ancora carte nel deck
		{
			if(combattente.mano.size() < MAX_CARTE_MANO) // Se c'e' ancora posto per una carta nella mano
			{
				Carta cartaPescata = Carta.cartaToCartaPartita(combattente.deck.get(0)); // Prendi la prima carta del deck
				combattente.deck.remove(0); // Rimuovila daldeck
				combattente.mano.add(cartaPescata); // Aggiungila nella mano
				
				System.out.println("Hai pescato " + cartaPescata.nome + " (ATT: " + cartaPescata.attaccoAtt + " / HP: " + cartaPescata.saluteAtt + ").");
				aggiornaAltroGiocatore(new AggiornamentoPartita(AzionePartita.Pesca)); // No param. perche l'altro gg non deve sapere le tue carte
				
				//Controllo se ci sono delle carte con il trigger pesca carta TODO
				
				
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
	
	public void riepilogoPartita()
	{
		System.out.println("--------------- Riepilogo partita ---------------");
		System.out.println
		(
			"Giocatore " + combattente.nome + "\n"
			+ "Il tuo eroe ha " + ggIo.salute + " di salute\n"
			+ "L'eroe dell'avversario ha " + ggAvversario.salute + " di salute\n"
			+ "Hai " + combattente.manaAtt + " di mana\n"
			+ "Hai " + combattente.deck.size() + " carte nel deck\n"
			+ "Hai " + combattente.mano.size() + " carte nella mano\n"
			+ "Hai " + campo[THIS_GG].size() + " carte sul campo"
		);
		System.out.println("------------------------------------------------\n");
	}
	
	public void mostraCampoTabellaTuttiGg()
	{
		for(int j = 0; j < 2; j++) // Per tutti e 2 giocatori
		{
			if(j == THIS_GG)
			{
				System.out.println(" > Le tue carte nel campo");
				System.out.println("|---------------------------------------|");
				System.out.println("| Nome | Attacco | Salute | Stato carta |");
				System.out.println("|---------------------------------------|");
				System.out.println("| Eroe " + ggIo.nome + " | 0 | " + ggIo.salute + " |");
				
				for (int i = 0; i < campo[j].size(); i++)
				{
					System.out.print("| " + campo[j].get(i).nome + " | " + campo[j].get(i).attaccoAtt + " | " + campo[j].get(i).saluteAtt + " | ");
					System.out.println(campo[j].get(i).giocatePerTurnoAtt == 0 ? "E' esausto |" : "Puo attaccare |");
				}
				System.out.println("|---------------------------------------|\n");
			}
			else
			{
				System.out.println(" > Le carte dell'avversario nel campo");
				System.out.println("|-------------------------|");
				System.out.println("| Nome | Attacco | Salute |");
				System.out.println("|-------------------------|");
				System.out.println("| Eroe " + ggAvversario.nome + " | 0 | " + ggAvversario.salute + " |");
				
				for (int i = 0; i < campo[j].size(); i++)
				{
					System.out.println("| " + campo[j].get(i).nome + " | " + campo[j].get(i).attaccoAtt + " | " + campo[j].get(i).saluteAtt + " |");
				}
				System.out.println("|-------------------------|\n");
			}
		}
	}
	
	public void mostraCampoGg(int giocatore)
	{
		if(giocatore == THIS_GG)
		{
			System.out.println(" > Le tue carte nel campo");
			System.out.println("|-------------------------------------------|");
			System.out.println("| N | Nome | Attacco | Salute | Stato carta |");
			System.out.println("|-------------------------------------------|");
			for (int i = 0; i < campo[giocatore].size(); i++)
			{
				System.out.print("| " + (i+1) + " | " + campo[giocatore].get(i).nome + " | " + campo[giocatore].get(i).attaccoAtt + " | " + campo[giocatore].get(i).saluteAtt + " | ");
				System.out.println(campo[giocatore].get(i).giocatePerTurnoAtt == 0 ? "E' esausto |" : "Puo attaccare |");
			}
			System.out.println("|-------------------------------------------|\n");
		}
		else
		{
			System.out.println(" > Le carte dell'avversario nel campo");
			System.out.println("|-----------------------------|");
			System.out.println("| N | Nome | Attacco | Salute |");
			System.out.println("|-----------------------------|");
			System.out.println("| 0 | Eroe " + ggAvversario.nome + "| 0 |   " + ggAvversario.salute + " |");
			for (int i = 0; i < campo[giocatore].size(); i++)
			{
				System.out.println("| " + (i+1) + " | " + campo[giocatore].get(i).nome + " | " + campo[giocatore].get(i).attaccoAtt + " | " + campo[giocatore].get(i).saluteAtt + " |");
			}
			System.out.println("|-----------------------------|\n");
		}
	}
	
	public void mostraManoTabella()
	{
		if(combattente.mano.size() == 0)
		{
			System.out.println("|----------------------------------|");
			System.out.println("| Non hai nessuna carta nella mano |");
			System.out.println("|----------------------------------|\n");
		}
		else
		{
			System.out.println("\n > Hai " + combattente.manaAtt + " mana\n");
			System.out.println("|------------------------------------------|");
			System.out.println("| N | Nome | Attacco | Salute | Costo mana |");
			System.out.println("|------------------------------------------|");
			for (int i = 0; i < combattente.mano.size(); i++)
			{
				System.out.println("| " + i + " | " + combattente.mano.get(i).nome + " | " + combattente.mano.get(i).attaccoAtt + " | " + combattente.mano.get(i).saluteAtt + " | " + combattente.mano.get(i).costoMana + " |");
			}
			System.out.println("|------------------------------------------|\n");
		}
	}
	

	//
    // FUNZIONI MULTIPLAYER
    //
	
	public void aggiornaAltroGiocatore(AggiornamentoPartita agg)
	{
		output.println(new Gson().toJson(agg));
	}
	
	public void riceviAggiornamento(AggiornamentoPartita agg)
	{
		switch(agg.azione)
		{
			case AggiungiCartaSulCampo:
				haAggiuntoCartaSulCampo(new Gson().fromJson(agg.payload[0], Carta.class));
				break;
			case Attacco:
				farsiAttacare(new Gson().fromJson(agg.payload[0], int.class), new Gson().fromJson(agg.payload[1], int.class));
				break;
			case CambiaTurno:
				toccaMe();
				break;
			case GameOver:
				System.out.println("gAmE oVeR");
				break;
			case GameWin:
				System.out.println("gAmE oVeR");
				break;
			case Pesca:
				System.out.println("L'altro giocatore ha pescato una carta");
				break;
			case Messaggio:
				System.out.println("\nL'altro giocatore ha detto: \"" + agg.payload[0] + "\".\n");
				break;
			default:
				break;
		}
	}
}