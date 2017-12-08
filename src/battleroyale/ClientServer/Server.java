package battleroyale.ClientServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

import com.google.gson.Gson;

import battleroyale.Partita.AggiornamentoPartita;
import battleroyale.Partita.AggiornamentoPartita.AzionePartita;
import battleroyale.Partita.GiocatoreSlim;
import battleroyale.Partita.Partita;

public class Server
{
	public static final int PORTA = 59168;
	
	private static ServerSocket serverSocket;	
	private Socket clientSocket;
	
	private BufferedReader input;
	private PrintWriter output;
	
	public Partita partita;
	
	public Server(GiocatoreSlim gg1)
    {
		try
		{
			serverSocket = new ServerSocket(PORTA);
			System.out.println("In attesa di un avversario all'indirizzo: " + getPublicIp());
			
			clientSocket = serverSocket.accept();
			
			// Inizializzare i canali di i/o per communicare
			input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			output = new PrintWriter(clientSocket.getOutputStream(), true);
			
			System.out.println("Il client " + clientSocket.getInetAddress().toString() + " si 'e collegato.");
			
			GiocatoreSlim gg2 = new Gson().fromJson(input.readLine(), GiocatoreSlim.class);
			partita = new Partita(gg1, gg2, input, output);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public String getPublicIp()
	{
		try
		{
			URL whatismyip;
			whatismyip = new URL("http://checkip.amazonaws.com");
			
			BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
			return in.readLine();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}