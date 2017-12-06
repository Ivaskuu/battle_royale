package battleroyale.ClientServer;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client
{
	private static Socket socket;
	private static Scanner input;
	private static PrintWriter output;

	private static Scanner tast;
	
	public static void main(String[] args)
    {
		try
		{
			socket = new Socket("127.0.0.1", Server.PORTA);
			input = new Scanner(socket.getInputStream());
			output = new PrintWriter(socket.getOutputStream(), true);

			System.out.println("Connessione eseguita");
			
			tast = new Scanner(System.in);
			
			while(true)
			{
				System.out.print("\nScrivi qualcosa da inviare al server: ");
				output.println(tast.nextLine());
				
				System.out.println("Il server ha risposto: \"" + input.nextLine() + "\"");
			}
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
}