package battleroyale;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class HandleClient extends Thread
{
	private Socket clientSocket;
	
	private BufferedReader input;
	private PrintWriter output;
	
	public HandleClient(Socket socket) throws IOException
	{
		clientSocket = socket;

		// Inizializzare i canali di i/o per communicare
		input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		output = new PrintWriter(clientSocket.getOutputStream(), true);
		
		System.out.println("Il client " + clientSocket.getInetAddress().toString() + " si 'e collegato.");
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
				String msg = input.readLine();
				System.out.println("Il client ha detto: " + msg);
				
				output.println("Ho ricevuto il tuo messaggio");
			}
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
}