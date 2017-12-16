package battleroyale.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;

public class Menu {

	JFrame f = new JFrame("Einstone alpha test");
	JPanel p = new JPanel();

	
	//Titolo del gioco su Jpanel
	JLabel title = new JLabel("Einstone");
	
	//Label del menu
	JLabel lblmenu= new JLabel("Benvenuto in Einstone, seleziona un'opzione per cominciare");
	
	//Pulsanti sul menu
	JButton btnCreateGame = new JButton("Crea una partita");
	JButton btnJoinGame = new JButton("Unisciti ad una partita");
	JButton btnManageGame = new JButton("Gestisci il tuo deck");
	JButton btnBuyPacks = new JButton("Compra una bustina");
	JButton btnExit = new JButton("Esci dal gioco");
	

    public Menu()
	{
		initialize();
		
		
	}

	
	private void initialize()
	{
		p.setBackground(Color.ORANGE);
		f.getContentPane().add(p);
		p.setLayout(null);
		
		//--------------------------------Pulsanti del menu---------------------------------
		
		btnCreateGame.setBounds(208, 106, 168, 45);
		p.add(btnCreateGame);
		
		btnJoinGame.setBounds(208, 162, 168, 45);
		p.add(btnJoinGame);
		
	
		btnManageGame.setBounds(208, 218, 168, 45);
		p.add(btnManageGame);
		
	
		btnBuyPacks.setBounds(208, 274, 168, 45);
		p.add(btnBuyPacks);
		
	
		btnExit.setBounds(208, 330, 168, 45);
		p.add(btnExit);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		
		
		//-----------------------------------------------------------------------------------
		
		//Titolo nel JPanel
		title.setForeground(Color.DARK_GRAY);
		title.setFont(new Font("BlizQuadrata Web", Font.PLAIN, 62));
		title.setBounds(159, 11, 267, 69);
		p.add(title);
		
		
		//Attributi del frame
		f.setLocation(350, 200);
		f.setVisible(true);
		f.setBounds(100, 100, 600, 450);
		f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		f.addWindowListener(new Gestione_finestre());
	}
	
	
	
	
	
}
