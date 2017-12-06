package battleroyale.ClientServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class HandleClient extends Thread
{
	private Socket clientSocket;
	
	private Scanner input;
	private PrintWriter output;
	
	public HandleClient(Socket socket) throws IOException
	{
		clientSocket = socket;

		// Inizializzare i canali di i/o per communicare
		input = new Scanner(clientSocket.getInputStream());
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
				// Aspettare input
				String msgIn = input.nextLine();
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
}