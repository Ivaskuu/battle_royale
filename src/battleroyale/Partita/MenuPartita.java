package battleroyale.Partita;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MenuPartita
{
	public MenuPartita(Partita partita)
	{
        BufferedReader tast = new BufferedReader(new InputStreamReader(System.in));
        
        do
        {
        	try
        	{
        		System.out.print("-- Menu --\n"
        				+ "[2] Riepilogo\n"
        				+ "[1] Cambia turno\n"
        				+ "[5] Mostra mano\n"
        				+ "[3] Attacca\n"
        				+ "[6] Mostra campo battaglia\n"
        				+ "[4] Aggiungi carta nel campo da battaglia\n"
        				+ "\nScelta: ");
        		
        		switch(tast.readLine().charAt(0))
        		{
        			case '1':
        				partita.cambiaTurno();
        				break;
        			case '2':
        				partita.riepilogoPartita();
        				break;
        			case '3':
						int avversario = partita.turno + 1 == partita.NUM_GG ? 0 : partita.turno + 1;
        				if(partita.carteNelCampo[partita.turno].size() > 0 && partita.carteNelCampo[avversario].size() > 0)
        				{
        					partita.mostraCampoBattaglia(partita.turno);
        					
        					do
        					{
        						System.out.print("Scegli la tua carta (-1 per annullare attacco)"
            	        				+ "\nScelta: ");
            					scelta = tast.readLine().charAt(0);
            					
            					if(scelta == '-')
            					{
            						scelta = '3'; // Per ritornare al menu di prima
            						break;
            					}
            					
            					if(partita.carteNelCampo[partita.turno].get(Integer.parseInt("" + scelta)).giocatePerTurnoAtt <= 0)
            					{
            						System.out.println("Hai gia' usato questa carta durante questo turno\n");
            					}
        					}
        					while(partita.carteNelCampo[partita.turno].get(Integer.parseInt("" + scelta)).giocatePerTurnoAtt <= 0);
        					
        					int cartaAtt = Integer.parseInt("" + scelta);
        					partita.mostraCampoBattaglia(avversario);
        					
        					System.out.print("Scegli la carta da attaccare (-1 per annullare attacco)"
        	        				+ "\nScelta: ");
        					scelta = tast.readLine().charAt(0);
        					
        					if(scelta == '-')
        					{
        						scelta = '3'; // Per ritornare al menu di prima
        						break;
        					}
        					else
        					{
        						int cartaAvv = Integer.parseInt("" + scelta);
        						partita.attacca(cartaAtt, cartaAvv);
        					}
        				}
        				else
        				{
        					System.out.println("Ogni giocatore deve avere almeno una carta nel campo di battaglia\n");
        				}
        				break;
        			case '4':
        				if(partita.mano[partita.turno].size() > 0)
        				{
        					partita.mostraMano(partita.turno);
        					
        					System.out.print("Hai " + partita.manaAtt + " mana.\n"
        							+ "Scegli la carta da aggiungere nel campo di battaglia\n(-1 per annullare attacco)\n\n"
        	        				+ "Scelta: ");
        					scelta = tast.readLine().charAt(0);
        					
        					if(scelta == '-')
        					{
        						scelta = '3'; // Per ritornare al menu di prima
        						break;
        					}
        					else
        					{
        						int posCarta = Integer.parseInt("" + scelta);
        						partita.aggiungiCartaSulCampo(posCarta);
        					}
        				}
        				else
        				{
        					System.out.println("Non hai nessuna carta nella mano\n");
        				}
        				break;
        			case '5':
        				partita.mostraMano(partita.turno);
        				break;
        			case '6':
        				partita.mostraCampoBattaglia(-1);
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
}