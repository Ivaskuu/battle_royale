package battleroyale.ClassiPartita;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gson.Gson;

import battleroyale.ClassiPartita.AggiornamentoPartita.AzionePartita;

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
        				+ "[6] Finire il turno\n"
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
							}
							while
							(
								posAtt >= partita.campo[partita.THIS_GG].size()
								|| posAtt >= 0 && partita.campo[partita.THIS_GG].get(posAtt).giocatePerTurnoAtt <= 0
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
							
							partita.attacca(posAtt, posAvv);
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
        				aspettaCambioTurno(false);
        				break;
        			default:
        				System.out.println("\nCoes ?\n");
        				break;
        		}
        	}
        	catch(Exception e)
        	{
        		System.out.println(e);
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
			
			if(primaVolta) turnoMio();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}