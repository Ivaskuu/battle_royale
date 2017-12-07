package battleroyale.ClientServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

import com.google.gson.Gson;

import battleroyale.GiocatoreSlim;
import battleroyale.Partita;

public class Server extends Thread
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
			
			System.out.println("\nInfo sul nuovo gg: " + new Gson().toJson(gg2));
			partita = new Partita(new GiocatoreSlim[] {gg1, gg2});
			
			System.out.println(new Gson().toJson(partita));
			output.println(new Gson().toJson(partita));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		start();
	}
	
	@Override
	public void run()
	{
		try
		{
			// Ascoltare il client
			System.out.println("\nSto ascoltando il client\n");
			
			while(true)
			{
				// Aspettare input
				String msgIn = input.readLine();
				System.out.println("Il client ha detto: \"" + msgIn + "\"");
				
				// Rispondere
				String msgOut = "Ho ricevuto: " + msgIn;
				System.out.println("Ho risposto: \"" + msgOut + "\"");				
				output.println(msgOut);
			}
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
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