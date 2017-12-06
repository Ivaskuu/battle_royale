package battleroyale.ClientServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server extends Thread
{
	private static final int PORTA = 59168;
	
	private static ServerSocket serverSocket;
	private static Socket clientSocket;
	
	public static void main(String[] args)
    {
		try
		{
			serverSocket = new ServerSocket(PORTA);
			System.out.println("Il server 'e pronto e sta aspettando una connessione su " + serverSocket.getLocalSocketAddress());
			
			HandleClient client = new HandleClient(serverSocket.accept());
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
}