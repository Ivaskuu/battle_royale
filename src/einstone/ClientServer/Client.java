package einstone.ClientServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.google.gson.Gson;

import einstone.Giocatore;
import einstone.ClassiPartita.AggiornamentoPartita;
import einstone.ClassiPartita.Combattente;
import einstone.ClassiPartita.GiocatoreSlim;
import einstone.ClassiPartita.Partita;
import einstone.ClassiPartita.AggiornamentoPartita.AzionePartita;

public class Client extends Thread
{
	private Socket socket;
	private BufferedReader input;
	private PrintWriter output;

	public Partita partita;
	
	public Client(String ip, GiocatoreSlim gg1)
    {
		try
		{
			socket = new Socket(ip, Server.PORTA);
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream(), true);
			
			output.println(new Gson().toJson(gg1));
			
			GiocatoreSlim gg2 = new Gson().fromJson(input.readLine(), GiocatoreSlim.class);
			int THIS_GG = Integer.parseInt(input.readLine());
			
			partita = new Partita(gg1, gg2, THIS_GG, input, output);

			start();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}