package battleroyale;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends Thread
{
	private Socket socket;
	
	private BufferedReader input;
	private PrintStream output;
	
	public Client()
	{
		try
		{
			socket = new Socket("127.0.0.1", 59168);
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintStream(socket.getOutputStream());
			
			start();
			
			output.print("Ciao server, piacere");
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
	public void inviaMsgAlServer(String msg)
	{
		output.print(msg);
	}
	
	@Override
	public void run()
	{
		try
		{
			while(true)
			{
				//System.out.println("Sto ascoltando il server");
				if(input.ready())
				{
					String msg = input.readLine();
					System.out.println("Il server ha detto: " + msg);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
}