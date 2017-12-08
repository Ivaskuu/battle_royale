package battleroyale.Partita;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MenuPartita extends Thread
{
	private Partita partita;
	
	public MenuPartita(Partita partita, int thisGG)
	{
		this.partita = partita;
		
		if(thisGG == 0) turnoMio();
		else start();
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
        		
        		switch(tast.readLine().charAt(0))
        		{
        			case '1': // Riepilogo partita
        				partita.riepilogoPartita();
        				break;
        			case '2': // Mostra mano
        				partita.mostraMano();
        				break;
        			case '3': // Mostra campo battaglia
        				partita.mostraCampoBattaglia(-1);
        				break;
        			case '4': // Attaccare
						int avversario = partita.contrario(partita.THIS_GG);
        				if(partita.campo[partita.THIS_GG].size() > 0 && partita.campo[avversario].size() > 0)
        				{
        					int cartaMia = -1;
        					int cartaSua = -1;
        					partita.mostraCampoBattaglia(partita.THIS_GG);
        					
        					do
        					{
        						System.out.print("Scegli la tua carta (-1 per annullare attacco)"
            	        				+ "\nScelta: ");
            					String scelta = tast.readLine();
            					
            					if(scelta.charAt(0) == '-') break;
            					
            					cartaMia = Integer.parseInt(scelta);
            					if(partita.campo[partita.THIS_GG].size()-1 < cartaMia) System.out.println("Carta inesistente");
            					else if(partita.campo[partita.THIS_GG].get(cartaMia).giocatePerTurnoAtt <= 0) System.out.println("Hai gia' usato questa carta durante questo turno\n");
        					}
        					while(partita.campo[partita.THIS_GG].get(cartaMia).giocatePerTurnoAtt <= 0);
        					
        					do
        					{
        						partita.mostraCampoBattaglia(partita.contrario(partita.THIS_GG));
        						System.out.print("Scegli la sua carta da attaccare (-1 per annullare attacco)"
            	        				+ "\nScelta: ");
        						
            					String scelta = tast.readLine();
            					if(scelta.charAt(0) == '-') break;
            					
            					cartaSua = Integer.parseInt(scelta);
            					if(partita.campo[partita.contrario(partita.THIS_GG)].size()-1 < cartaSua) System.out.println("Carta inesistente");
        					}
        					while(partita.campo[partita.contrario(partita.THIS_GG)].get(cartaMia).giocatePerTurnoAtt <= 0);
        					
        					partita.attacca(cartaMia, cartaSua);
        				}
        				else
        				{
        					System.out.println("Ogni giocatore deve avere almeno una carta nel campo di battaglia\n");
        				}
        				break;
        			case '5': // Aggiungi carta sul campo
        				if(partita.combattente.mano.size() > 0)
        				{
        					partita.mostraMano();
        					System.out.print("Hai " + partita.combattente.manaAtt + " di mana.\n"
        							+ "Scegli la carta da mettere nel campo di battaglia (-1 per annullare)\n"
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
        				partita.toccaTe();
        				start();
        				return;
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
	
	@Override
	public void run()
	{
		BufferedReader tast = new BufferedReader(new InputStreamReader(System.in));
		
		try
		{
			while(partita.turno == partita.contrario(partita.THIS_GG))
			{
				System.out.println("Sta giocando l'avversario ...");
				tast.readLine();
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}