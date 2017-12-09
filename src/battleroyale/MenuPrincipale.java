package battleroyale;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

import com.google.gson.Gson;

import battleroyale.ClassiPartita.GiocatoreSlim;
import battleroyale.ClientServer.Client;
import battleroyale.ClientServer.Server;

public class MenuPrincipale
{
	private static Server server;
	private static Client client;
	
	private static boolean partitaIniziata = false;
	
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
		BufferedReader tast2 = new BufferedReader(new InputStreamReader(System.in));
		
        int scelta;
        
        System.out.print("Benvenuto, corragioso avventuriero. Come ti chiami ?\nNome: ");
        Giocatore gg = new Giocatore(tast.nextLine(), 50, null, CollezioneCarte.collezioneCarte);
        
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
        			case 0:
        				server = new Server(GiocatoreSlim.fromGiocatore(gg));
        				partitaIniziata = true;
        				break;
        			case 1:
        				System.out.print("Per favore inserisca l'indirizzo IP: ");
        				client = new Client(tast2.readLine(), GiocatoreSlim.fromGiocatore(gg));
        				partitaIniziata = true;
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
        while(!partitaIniziata);
	}
}