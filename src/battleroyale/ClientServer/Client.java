package battleroyale.ClientServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.google.gson.Gson;

import battleroyale.Partita.AggiornamentoPartita;
import battleroyale.Partita.GiocatoreSlim;
import battleroyale.Partita.Partita;
import battleroyale.Partita.AggiornamentoPartita.AzionePartita;
import battleroyale.Giocatore;

public class Client
{
	private Socket socket;
	private BufferedReader input;
	private PrintWriter output;

	private BufferedReader tast;
	private int turno;

	public Partita partita;
	
	public Client(String ip, GiocatoreSlim gg2, Partita partita)
    {
		try
		{
			socket = new Socket(ip, Server.PORTA);
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream(), true);
			tast = new BufferedReader(new InputStreamReader(System.in));
			
			// 3 way handshake
			GiocatoreSlim avversario = new Gson().fromJson(input.readLine(), GiocatoreSlim.class);
			output.println(new Gson().toJson(gg2));
			turno = Integer.parseInt(input.readLine());

			do
			{
				while(true)
				{
					AggiornamentoPartita agg = new Gson().fromJson(input.readLine(), AggiornamentoPartita.class);
					
					System.out.println("\nAggiornamento partita:\n" + agg.azione);
					if(agg.parametri != null) for (int i = 0; i < agg.parametri.length; i++) System.out.println(agg.parametri[i]);
					else System.out.println("Non ha nessun parametro");
				}
				
				while(true);
				{
					AggiornamentoPartita agg = new Gson().fromJson(input.readLine(), AggiornamentoPartita.class);
					if(agg.azione == AzionePartita.CambiaTurno) break;
				}
			}
			while(true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}