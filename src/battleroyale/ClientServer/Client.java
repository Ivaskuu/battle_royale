package battleroyale.ClientServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.google.gson.Gson;

import battleroyale.Giocatore;
import battleroyale.GiocatoreSlim;

public class Client
{
	private static Socket socket;
	private static BufferedReader input;
	private static PrintWriter output;

	private static BufferedReader tast;
	
	public Client(String ip, Giocatore gg)
    {
		try
		{
			System.out.println("ip : " + ip);
			
			socket = new Socket(ip, Server.PORTA);
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream(), true);

			System.out.println("Connessione eseguita, inviamogli le info sul giocatore");
			output.println(new Gson().toJson(GiocatoreSlim.fromGiocatore(gg)));
			
			System.out.println("Adesso mandami le info sulla partita, tipo chi comincia per primo, server");
			System.out.println("Ho ricevuto le info sulla partita: " + input.readLine());
			
			tast = new BufferedReader(new InputStreamReader(System.in));
			while(true) tast.readLine();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}