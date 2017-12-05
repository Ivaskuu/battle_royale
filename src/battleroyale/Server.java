package battleroyale;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread
{
	private final int PORTA = 59168;
	
	private ServerSocket serverSocket;
	private Socket clientSocket;
	
	private BufferedReader input;
	private PrintStream output;
	
	public Server()
	{
		try
		{
			serverSocket = new ServerSocket(PORTA);
			start();
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
	// La faccio su un'altro thread per non bloccare il programma
	@Override
	public void run()
	{
		try
		{
			// Parte collegamento
			clientSocket = serverSocket.accept(); // Aspetta che il client si connetti
			System.out.println("Il client " + clientSocket.getInetAddress().toString() + " si e collegato.");
			
			// Inizializzare i canali di i/o per communicare
			input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			output = new PrintStream(clientSocket.getOutputStream());
			
			// Parte ascolto
			while(true)
			{
				//System.out.println("Sto ascoltando il client");
				if(input.ready()) // Il client ci ha trasmesso un messaggio
				{
					String msg = input.readLine();
					System.out.println("Il client ha detto: " + msg);
				}
			}
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
}