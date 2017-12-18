package battleroyale.ClassiPartita;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import com.google.gson.Gson;

import battleroyale.Carta;
import battleroyale.ClassiPartita.Effetti;
import battleroyale.Carta.AbilitaCarta;
import battleroyale.CollezioneCarte;
import battleroyale.EffettoSpeciale.TriggerEffetto;
import battleroyale.ClassiPartita.AggiornamentoPartita.AzionePartita;
import battleroyale.ClassiPartita.Effetto.TipoEffetto;

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
		if(carta.costoMana > combattente.manaAtt) // Se il giocatore non ha abbastanza mana
		{
			System.out.println("Non hai abbastanza mana per aggiungere " + carta.nome + " (costa " + carta.costoMana + " ma hai " + combattente.manaAtt + ")");
		}
		else  // Aggiungi la carta sul campo
		{
			combattente.manaAtt -= carta.costoMana;
			campo[THIS_GG].add(carta);
			combattente.mano.remove(posMazzo);

			aggiornaAltroGiocatore(new AggiornamentoPartita(AzionePartita.AggiungiCartaSulCampo, new Gson().toJson(carta)));
			
			System.out.println(carta.nome + " e' stato/a aggiunto/a sul campo");			
			mostraCampoGg(THIS_GG);
		}
	}
	
	public void haAggiuntoCartaSulCampo(Carta carta)
	{
		campo[contrario(THIS_GG)].add(carta);
		System.out.println("L'avversario ha aggiunto " + carta.nome + " (" + carta.attaccoAtt + "/" + carta.saluteAtt + ") sul campo");
	}
	
	// Quando il giocatore attacca una carta dell'avversario
	public void attacca(int posCartaAtt, int posCartaAvv, boolean skipProvocazione) // skipProvocazione serve nel caso di una carta magia
	{
		ArrayList<Integer> carteConProvocazione = new ArrayList<Integer>();
		for(int i = 0; i < campo[contrario(THIS_GG)].size(); i++) // Per tutti i 2 gg
		{
			if(campo[contrario(THIS_GG)].get(i).getEffetto(TipoEffetto.Provocazione) != null)
			{
				carteConProvocazione.add(i);
			}
		}
		
		if(posCartaAvv == -1) // Il giocatore vuole attacare l'eroe dell'avversario
		{
			if(carteConProvocazione.size() == 0 || skipProvocazione == true)
			{
				Carta cartaMia = campo[THIS_GG].get(posCartaAtt);
				
				if(cartaMia.giocatePerTurnoAtt > 0)
				{					
					if(cartaMia.getEffetto(TipoEffetto.Furtivita) != null)
					{
						cartaMia.effetti[cartaMia.getEffetto(TipoEffetto.Furtivita)] = null;
						System.out.println("La tua carta ha perso l'effetto 'Furtivita''");
					}
					
					cartaMia.giocatePerTurnoAtt--;
					ggAvversario.salute -= cartaMia.attaccoAtt;
					
					System.out.println("\nL'eroe dell'avversario ha perso " + cartaMia.attaccoAtt + " HP (rimasti " + ggAvversario.salute + " HP)");
					aggiornaAltroGiocatore(new AggiornamentoPartita(AzionePartita.Attacco, new String[] {new Gson().toJson(posCartaAtt), new Gson().toJson(posCartaAvv)}));
					
					if(ggAvversario.salute <= 0)
					{
						System.out.println("\nL'eroe dell'avversario e' stato distrutto.\nHai vinto la partita!\n");
						aggiornaAltroGiocatore(new AggiornamentoPartita(AzionePartita.GameWin));
					}
					
					if(cartaMia.getEffetto(TipoEffetto.Rubavita) != null)
					{
						System.out.println("La tua carta ha l'effetto 'Ruba vita' quindi il tuo erore riceve +" + cartaMia.attaccoAtt + " HP");
						ggIo.salute = Math.min(30, ggIo.salute + cartaMia.attaccoAtt);
						
						System.out.println("Adesso il tuo eroe ha " + ggIo.salute + " HP");
					}
					
					mostraCampoTabellaTuttiGg();
				}
				else
				{
					System.out.println("Hai gia usato questa carta durante questo turno");
				}
			}
			else System.out.println("Devi prima attaccare i personnaggi con l'effetto PROVOCAZIONE");
		}
		else if(campo[THIS_GG].get(posCartaAtt) != null && campo[contrario(THIS_GG)].get(posCartaAvv) != null) // Se le carte esistono veramente
		{
			boolean staAttaccandoProvocazione = false;
			for(int i = 0; i < carteConProvocazione.size(); i++)
			{
				if(posCartaAvv == carteConProvocazione.get(i)) staAttaccandoProvocazione = true;
			}
			
			if(carteConProvocazione.size() == 0 || staAttaccandoProvocazione == true)
			{
				Carta cartaMia = campo[THIS_GG].get(posCartaAtt);
				
				if(cartaMia.giocatePerTurnoAtt > 0)
				{
					Carta cartaAvv = campo[contrario(THIS_GG)].get(posCartaAvv);
					
					if(cartaAvv.getEffetto(TipoEffetto.Furtivita) != null) // Controllo dell'effetto Furtivita
					{
						System.out.println("Non puoi attaccare questa carta perche' ha l'effetto 'Furtivita''");
						return;
					}
					else
					{
						campo[THIS_GG].get(posCartaAtt).giocatePerTurnoAtt--;
						System.out.println("Il tuo " + cartaMia.nome + " (" + cartaMia.attaccoAtt + "/" + cartaMia.saluteAtt + ") sta attcando " + cartaAvv.nome + " (" + cartaAvv.attaccoAtt + "/" + cartaAvv.saluteAtt + ")");
						
						if(cartaMia.getEffetto(TipoEffetto.Furtivita) != null)
						{
							cartaMia.effetti[cartaMia.getEffetto(TipoEffetto.Furtivita)] = null;
							System.out.println("La tua carta ha perso l'effetto 'Furtivita''");
						}
						
						// Rimuovi la vita della carta dell'avversario
						if(cartaAvv.getEffetto(TipoEffetto.ScudoDivino) != null)
						{
							cartaAvv.effetti[cartaAvv.getEffetto(TipoEffetto.ScudoDivino)] = null;
							System.out.println("La carta dell'avversario non ha subito danni ma ha perso l'effetto 'Scudo Divino'");
						}
						else
						{
							if(cartaMia.getEffetto(TipoEffetto.Veleno) != null)
							{
								campo[contrario(THIS_GG)].remove(posCartaAvv);
								System.out.println("\nLa tua carta ha l'effetto 'Veleno' e quindi ha distrutto la carta dell'avversario");
							}
							else
							{
								cartaAvv.saluteAtt -= cartaMia.attaccoAtt;
								if(cartaAvv.saluteAtt > 0) System.out.println("\nLa carta dell'avversario ha perso " + cartaMia.attaccoAtt + " HP (rimasti " + cartaAvv.saluteAtt + " HP)\n");
								else
								{
									campo[contrario(THIS_GG)].remove(posCartaAvv);
									System.out.println("\nLa carta dell'avversario e' stata distrutta\n");
								}
							}
						}
		
						// Rimuovi la vita della carta dell'attaccante (cioe io, cartaMia)
						if(cartaMia.getEffetto(TipoEffetto.ScudoDivino) != null && cartaAvv.attaccoAtt > 0)
						{
							cartaMia.effetti[cartaMia.getEffetto(TipoEffetto.ScudoDivino)] = null;
							System.out.println("La tua carta non ha subito danni ma ha perso l'effetto 'Scudo Divino'");
						}
						else
						{
							if(cartaAvv.getEffetto(TipoEffetto.Veleno) != null)
							{
								campo[THIS_GG].remove(posCartaAtt);
								System.out.println("\nLa carta dell'avversario ha l'effetto 'Veleno' e quindi ha distrutto la tua carta\n");
							}
							else
							{
								cartaMia.saluteAtt -= cartaAvv.attaccoAtt;
								if(cartaMia.saluteAtt > 0) System.out.println("\nLa tua carta ha perso " + cartaAvv.attaccoAtt + " HP (rimasti " + cartaMia.saluteAtt + " HP)\n");
								else
								{
									campo[THIS_GG].remove(posCartaAtt);
									System.out.println("\nLa tua carta e' stata distrutta\n");
								}
							}
						}
						
						if(cartaMia.getEffetto(TipoEffetto.Rubavita) != null)
						{
							System.out.println("La tua carta ha l'effetto 'Ruba vita' quindi il tuo erore riceve +" + cartaMia.attaccoAtt + " HP");
							ggIo.salute = Math.min(30, ggIo.salute + cartaMia.attaccoAtt);
							
							System.out.println("Adesso il tuo eroe ha " + ggIo.salute + " HP");
						}

						//mostraCampoTabellaTuttiGg();
						aggiornaAltroGiocatore(new AggiornamentoPartita(AzionePartita.Attacco, new String[] {new Gson().toJson(posCartaAtt), new Gson().toJson(posCartaAvv)}));
					}
				}
				else System.out.println("Hai gia usato questa carta durante questo turno");
			}
			else System.out.println("Devi prima attaccare i personnaggi con l'effetto PROVOCAZIONE");
		}
		else System.out.println("Le carte non esistono sul campo da battaglia.");
	}
	
	// Quando sta giocando l'altro e ci attacca
	public void farsiAttacare(int posCartaSua, int posCartaMia)
	{
		Carta cartaSua = campo[contrario(THIS_GG)].get(posCartaSua);
		
		if(posCartaMia == -1) // Sta attacando l'eroe
		{
			cartaSua.giocatePerTurnoAtt--;
			System.out.println("Il tuo eroe si sta facendo attaccare da " + cartaSua.nome + " (" + cartaSua.attaccoAtt + "/" + cartaSua.saluteAtt + ")");
			
			if(cartaSua.getEffetto(TipoEffetto.Furtivita) != null)
			{
				cartaSua.effetti[cartaSua.getEffetto(TipoEffetto.Furtivita)] = null;
				System.out.println("La sua carta ha perso l'effetto 'Furtivita''");
			}
			
			ggIo.salute -= cartaSua.attaccoAtt;
			System.out.println("Il tuo eroe ha perso " + cartaSua.attaccoAtt + " HP (rimasti " + ggIo.salute + ")");
			
			if(cartaSua.getEffetto(TipoEffetto.Rubavita) != null)
			{
				System.out.println("La sua carta ha l'effetto 'Ruba vita' quindi il suo erore riceve +" + cartaSua.attaccoAtt + " HP");
				ggAvversario.salute = Math.min(30, ggAvversario.salute + cartaSua.attaccoAtt);
				
				System.out.println("Adesso il suo eroe ha " + ggAvversario.salute + " HP");
			}
			
			if(ggIo.salute <= 0) System.out.println("Il tuo eroe si e' fatto distruggere, e hai perso la partita.");
		}
		else
		{
			Carta cartaMia = campo[THIS_GG].get(posCartaMia);
			
			cartaSua.giocatePerTurnoAtt--;
			System.out.println("La tua carta " + cartaMia.nome + " (" + cartaMia.attaccoAtt + "/" + cartaMia.saluteAtt + ") si sta facendo attcare da " + cartaSua.nome + " (" + cartaSua.attaccoAtt + "/" + cartaSua.saluteAtt + ")");

			if(cartaSua.getEffetto(TipoEffetto.Furtivita) != null)
			{
				cartaSua.effetti[cartaSua.getEffetto(TipoEffetto.Furtivita)] = null;
				System.out.println("La carta dell'avversario ha perso l'effetto 'Furtivita''");
			}
			
			// Rimuovi la vita della carta mia (cioe io)
			if(cartaMia.getEffetto(TipoEffetto.ScudoDivino) != null)
			{
				cartaMia.effetti[cartaMia.getEffetto(TipoEffetto.ScudoDivino)] = null;
				System.out.println("La tua carta non ha subito danni ma ha perso l'effetto 'Scudo Divino'");
			}
			else
			{
				if(cartaSua.getEffetto(TipoEffetto.Veleno) != null)
				{
					campo[THIS_GG].remove(posCartaMia);
					System.out.println("\nLa sua carta ha l'effetto 'Veleno' quindi ha distrutto la tua carta\n");
				}
				else
				{
					cartaMia.saluteAtt -= cartaSua.attaccoAtt;
					if(cartaMia.saluteAtt > 0) System.out.println("La tua carta ha perso " + cartaSua.attaccoAtt + " HP (rimasti " + cartaMia.saluteAtt + " HP)\n");
					else
					{
						campo[THIS_GG].remove(posCartaMia);
						System.out.println("La tua carta e' stata distrutta\n");
					}
				}
			}
		
			// Rimuovi la vita della carta dell'attaccante cioe lui
			if(cartaSua.getEffetto(TipoEffetto.ScudoDivino) != null)
			{
				cartaSua.effetti[cartaSua.getEffetto(TipoEffetto.ScudoDivino)] = null;
				System.out.println("La sua carta non ha subito danni ma ha perso l'effetto 'Scudo Divino'");
			}
			else
			{
				if(cartaMia.getEffetto(TipoEffetto.Veleno) != null)
				{
					campo[contrario(THIS_GG)].remove(posCartaSua);
					System.out.println("\nLa tua carta ha l'effetto 'Veleno' e quindi ha distrutto la carta dell'avversario");
				}
				else
				{
					cartaSua.saluteAtt -= cartaMia.attaccoAtt;
					if(cartaSua.saluteAtt > 0) System.out.println("\nLa sua carta ha perso " + cartaMia.attaccoAtt + " HP (rimasti " + cartaSua.saluteAtt + " HP)\n");
					else
					{
						campo[contrario(THIS_GG)].remove(posCartaSua);
						System.out.println("\nLa sua carta e' stata distrutta\n");
					}
				}
			}
			
			if(cartaSua.getEffetto(TipoEffetto.Rubavita) != null)
			{
				System.out.println("La sua carta ha l'effetto 'Ruba vita' quindi il suo erore riceve +" + cartaSua.attaccoAtt + " HP");
				ggAvversario.salute = Math.min(30, ggAvversario.salute + cartaSua.attaccoAtt);
				
				System.out.println("Adesso il suo eroe ha " + ggAvversario + " HP");
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
				System.out.println("|------------------------------------------------------|");
				System.out.println("| Nome | Attacco | Salute | Stato carta | Effetti |");
				System.out.println("|------------------------------------------------------|");
				System.out.println("| Eroe " + ggIo.nome + " | 0 | " + ggIo.salute + " |");
				
				for (int i = 0; i < campo[j].size(); i++)
				{
					Carta carta = campo[j].get(i);
					System.out.print("| " + carta.nome + " | " + carta.attaccoAtt + " | " + carta.saluteAtt + " | ");
					System.out.print(carta.giocatePerTurnoAtt == 0 ? "E' esausto | " : "Puo attaccare | ");
					
					carta.printEffetti();
					System.out.println(" |");
				}
				System.out.println("|------------------------------------------------------|\n");
			}
			else
			{
				System.out.println(" > Le carte dell'avversario nel campo");
				System.out.println("|----------------------------------------|");
				System.out.println("| Nome | Attacco | Salute | Effetti |");
				System.out.println("|----------------------------------------|");
				System.out.println("| Eroe " + ggAvversario.nome + " | 0 | " + ggAvversario.salute + " |");
				
				for (int i = 0; i < campo[j].size(); i++)
				{
					Carta carta = campo[j].get(i);
					System.out.print("| " + carta.nome + " | " + carta.attaccoAtt + " | " + carta.saluteAtt + " | ");
					
					carta.printEffetti();
					System.out.println(" |");
				}
				System.out.println("|----------------------------------------|\n");
			}
		}
	}
	
	public void mostraCampoGg(int giocatore)
	{
		if(giocatore == THIS_GG)
		{
			System.out.println(" > Le tue carte nel campo");
			System.out.println("|-----------------------------------------------------|");
			System.out.println("| N | Nome | Attacco | Salute | Stato carta | Effetti |");
			System.out.println("|-----------------------------------------------------|");
			
			for (int i = 0; i < campo[giocatore].size(); i++)
			{
				Carta carta = campo[giocatore].get(i);
				System.out.print("| " + (i+1) + " | " + carta.nome + " | " + carta.attaccoAtt + " | " + carta.saluteAtt + " | ");
				System.out.print(carta.giocatePerTurnoAtt == 0 ? "E' esausto | " : "Puo attaccare | ");
				
				carta.printEffetti();
				System.out.println(" |");
			}
			System.out.println("|----------------------------------------------------|\n");
		}
		else
		{
			System.out.println(" > Le carte dell'avversario nel campo");
			System.out.println("|---------------------------------------|");
			System.out.println("| N | Nome | Attacco | Salute | Effetti |");
			System.out.println("|---------------------------------------|");
			System.out.println("| 0 | Eroe " + ggAvversario.nome + "| 0 |   " + ggAvversario.salute + " |");
			
			for (int i = 0; i < campo[giocatore].size(); i++)
			{
				Carta carta = campo[giocatore].get(i);
				System.out.print("| " + (i+1) + " | " + carta.nome + " | " + carta.attaccoAtt + " | " + carta.saluteAtt + " | ");
				
				carta.printEffetti();
				System.out.println(" |");
			}
			System.out.println("|---------------------------------------|\n");
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
			System.out.println("|----------------------------------------------------|");
			System.out.println("| N | Nome | Attacco | Salute | Costo mana | Effetti |"); // TODO: Tipo carta (pers, magia...)
			System.out.println("|----------------------------------------------------|");
			for (int i = 0; i < combattente.mano.size(); i++)
			{
				Carta carta = combattente.mano.get(i);
				System.out.print("| " + i + " | " + carta.nome + " | " + carta.attaccoAtt + " | " + carta.saluteAtt + " | " + carta.costoMana + " | ");
				
				carta.printEffetti();
				System.out.println(" |");
			}
			System.out.println("|----------------------------------------------------|\n");
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