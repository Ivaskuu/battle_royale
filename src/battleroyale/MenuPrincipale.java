package battleroyale;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class MenuPrincipale
{
	public static void main(String[] args)
	{
		String[] scelteMenu =
		{
			"Crea partita",
			"Unisciti ad una partita",
			"Gestire il deck",
			"Compra un chest",
			"Esci dal gioco"
		};
		
		Scanner tast = new Scanner(System.in);
        int scelta;
        
        do
        {
        	try
        	{
        		System.out.println("-- Menu --");
        		for (int i = 0; i < scelteMenu.length; i++)
        		{
        			System.out.println("[" + i + "] " + scelteMenu[i]);
				}
        		System.out.print("\nScelta: ");
        		
        		scelta = tast.nextInt();
        		
        		switch(scelta)
        		{
        			case 1:
        				// Creare un nuovo Server e printare l'indirizzo IP globale
        				break;
        			case 2:
        				// Chiedere l'indirizzo IP del server e creare una classe Client(ip)
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