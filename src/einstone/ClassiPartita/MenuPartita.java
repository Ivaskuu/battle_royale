package einstone.ClassiPartita;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.google.gson.Gson;

import einstone.ClassiPartita.AggiornamentoPartita.AzionePartita;

public class MenuPartita
{
	private Partita partita;
	private BufferedReader input;
	
	public MenuPartita(Partita partita, int thisGG, BufferedReader input)
	{
		this.partita = partita;
		this.input = input;
		
		if(thisGG == 0) turnoMio();
		else aspettaCambioTurno(true);
	}
	
	public void turnoMio()
	{     
		BufferedReader tast = new BufferedReader(new InputStreamReader(System.in));
		
        do
        {
        	try
        	{
        		System.out.print("-- Menu --\n"
        				+ "[1] Riepilogo\n"
        				+ "[2] Mostra mano\n"
        				+ "[3] Mostra campo battaglia\n"
        				+ "[4] Attacca\n"
        				+ "[5] Aggiungi carta sul campo\n"
        				+ "[6] Esprimiti\n"
        				+ "[7] Finire il turno\n"
        				+ "\nScelta: ");
        		
        		String sceltaMenu = tast.readLine(); // Evitare l'indexoutofbounds
        		switch(sceltaMenu != null && sceltaMenu.length() > 0 ? sceltaMenu.charAt(0) : '-')
        		{
        			case '1': // Riepilogo partita
        				partita.riepilogoPartita();
        				break;
        			case '2': // Mostra mano
        				partita.mostraManoTabella();
        				break;
        			case '3': // Mostra campo battaglia
        				partita.mostraCampoTabellaTuttiGg();
        				break;
        			case '4': // Attaccare
        				if(partita.campo[partita.THIS_GG].size() > 0) // Può attaccare solo se ha gia una carta sul campo
        				{
        					// Controlla che ci sia almeno una carta che possa attaccare (giocateAtt > 0) 
							boolean puoAttaccare = false;
							for (int i = 0; i < partita.campo[partita.THIS_GG].size(); i++)
							{
								if(partita.campo[partita.THIS_GG].get(i).giocatePerTurnoAtt > 0) puoAttaccare = true;
							}
							if(!puoAttaccare)
							{
								System.out.println("Nessuna delle tue carte puo' attaccare perche' sono esauste\n");
								break;
							}
							
							int posAtt = -2; // Posizione della carta o eroe del gg attuale
							int posAvv = -2; // Posizione della carta o eroe del gg avversario
							
							do
							{
		    					partita.mostraCampoGg(partita.THIS_GG);
								System.out.print("Scegli la carta con cui attaccare (oppure -1 per annullare attacco)"
		    	        				+ "\nScelta: ");
		    					String scelta = tast.readLine();
		    					
		    					if(scelta.charAt(0) == '-') break;
		    					
		    					posAtt = Integer.parseInt(scelta) - 1;
		    					if(posAtt >= partita.campo[partita.THIS_GG].size()) System.out.println("Per favore inserire una posizione valida");
		    					else if(posAtt >= 0 && partita.campo[partita.THIS_GG].get(posAtt).giocatePerTurnoAtt <= 0) System.out.println("Hai gia' usato questa carta durante questo turno\n");
		    					else if(partita.campo[partita.THIS_GG].get(posAtt).attaccoAtt == 0) System.out.println("Non puoi attaccare con una carta che ha 0 di attacco");
							}
							while
							(
								posAtt >= partita.campo[partita.THIS_GG].size()
								|| (posAtt >= 0 && partita.campo[partita.THIS_GG].get(posAtt).giocatePerTurnoAtt <= 0)
								|| partita.campo[partita.THIS_GG].get(posAtt).attaccoAtt == 0
							);
							
							if(posAtt == -2) break; // Vuol dire che vuole annullare l'attacco
							
							do
							{
								partita.mostraCampoGg(partita.contrario(partita.THIS_GG));
								System.out.print("Scegli cosa attaccare (oppure -1 per annullare attacco)"
		    	        				+ "\nScelta: ");
								
		    					String scelta = tast.readLine();
		    					if(scelta.charAt(0) == '-') break;
		    					
		    					posAvv = Integer.parseInt(scelta) - 1;
		    					if(posAvv >= partita.campo[partita.contrario(partita.THIS_GG)].size()) System.out.println("Per favore inserire una posizione valida");
							}
							while(posAvv >= partita.campo[partita.contrario(partita.THIS_GG)].size());

							if(posAvv == -2) break; // Vuol dire che vuole annullare l'attacco
							
							partita.attacca(posAtt, posAvv, false);
		        		}
		        		else
		        		{
		        			System.out.println("Devi avere almeno una carta sul campo per poter attaccare.");
		        		}
        				break;
        			case '5': // Aggiungi carta sul campo
        				if(partita.combattente.mano.size() > 0)
        				{
        					partita.mostraManoTabella();
        					System.out.print("Scegli la carta da evocare (oppure -1 per annullare)\n"
        	        				+ "Scelta: ");
        					
        					String scelta = tast.readLine();
        					
        					if(scelta.charAt(0) == '-') break;
        					else
        					{
        						int posCarta = Integer.parseInt(scelta);
        						partita.aggiungiCartaSulCampo(posCarta);
        					}
        				}
        				else
        				{
        					System.out.println("Non hai nessuna carta nella mano\n");
        				}
        				break;
        			case '6':
        				int msg = -1;
    					String[] reactions = new String[]
    					{
    						"Salve!", "Buona fortuna!",
    						"Bella giocata!", "Bella partita !",
    						"Wow !", "Oops...", "Minaccia",
    						"TUO PADRE FROCCIO"
    					};
    					
    					do
        				{
    						System.out.println("\n > Seleziona il messaggio da inviare\n");
        					for (int i = 0; i < reactions.length; i++)
        					{
        						System.out.println("[" + i + "] " + reactions[i]);
							}
        					System.out.print("\nScelta (oppure -1 per ritornare al menu) : ");
        					
        					String scelta = tast.readLine();
        					if(scelta.charAt(0) == '-') break;
        					else msg = Integer.parseInt(scelta);
        				}
        				while(msg < 0);
        				
    					if(msg == -1) break;
        				partita.aggiornaAltroGiocatore(new AggiornamentoPartita(AzionePartita.Messaggio, reactions[msg]));
        				System.out.println("Hai detto all'altro giocatore: \"" + reactions[msg] + "\".\n");
        				break;
        			case '7':
        				aspettaCambioTurno(false);
        				break;
        			default:
        				System.out.println("\nCoes ?\n");
        				break;
        		}
        	}
        	catch(Exception e)
        	{
    			e.printStackTrace();
        	}
        }
        while(true);
	}
	
	public void aspettaCambioTurno(boolean primaVolta)
	{
		try
		{
			if(!primaVolta) partita.toccaTe();
			System.out.println("Sta giocando l'altro giocatore...");
			
			while(true)
			{
				String msgIn = input.readLine();
				AggiornamentoPartita agg = new Gson().fromJson(msgIn, AggiornamentoPartita.class);
				partita.riceviAggiornamento(agg);
				
				if(agg.azione == AzionePartita.CambiaTurno) break;
			}
			
			System.in.read(new byte[System.in.available()]);
			if(primaVolta) turnoMio();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}