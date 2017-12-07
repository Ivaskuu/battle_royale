package battleroyale.ClientServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.google.gson.Gson;

import battleroyale.Partita.AggiornamentoPartita;
import battleroyale.Partita.GiocatoreSlim;
import battleroyale.Partita.AggiornamentoPartita.AzionePartita;
import battleroyale.Giocatore;

public class Client
{
	private Socket socket;
	private BufferedReader input;
	private PrintWriter output;

	private BufferedReader tast;
	private int turno;
	
	public Client(String ip, Giocatore gg/*, Partita*/)
    {
		try
		{
			socket = new Socket(ip, Server.PORTA);
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream(), true);
			tast = new BufferedReader(new InputStreamReader(System.in));

			System.out.println("Connessione eseguita, inviamogli le info su di noi");
			output.println(new Gson().toJson(GiocatoreSlim.fromGiocatore(gg)));
			
			System.out.println("Adesso dimmi chi comincia per primo, server");
			System.out.println("Comincia per primo: " + input.readLine());

			do
			{
				while(true)
				{
					AggiornamentoPartita agg = new Gson().fromJson(input.readLine(), AggiornamentoPartita.class);
					
					System.out.println("\nAggiornamento partita:\n" + agg.azione);
					if(agg.parametri != null) for (int i = 0; i < agg.parametri.length; i++) System.out.println(agg.parametri[i]);
					else System.out.println("Non ha nessun parametro");
				}
				
				/*do
				{
					AggiornamentoPartita agg = new Gson().fromJson(input.readLine(), AggiornamentoPartita.class);
					if(agg.azione == AzionePartita.CambiaTurno) break;
				}
				while(true);*/
			}
			while(true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}