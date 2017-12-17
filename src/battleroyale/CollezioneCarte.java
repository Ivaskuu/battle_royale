package battleroyale;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import com.sun.javafx.sg.prism.EffectFilter;

import battleroyale.Carta.AbilitaCarta;
import battleroyale.Carta.ClasseCarta;
import battleroyale.Carta.RaritaCarta;
import battleroyale.EffettoSpeciale.TriggerEffetto;
import battleroyale.ClassiPartita.Effetto;
import battleroyale.ClassiPartita.Effetto.TipoEffetto;

public class CollezioneCarte
{
	// Attributi personaggi
	// Immagine, nome, costo_mana,  raritï¿½, classe, AbilitÃ [] ,attacco ,attaccomax, salute ,salutemax, effetto/descrizione
	
	// public Carta(BufferedImage img, String nome, int costo_m, RaritaCarta rar, ClasseCarta cls, int atk, int sal, Effetto effetto, String descrizione)
	
	public static Carta[] collezioneCarte = 
	{
		 //------------------------------------------- [ Carte personaggi della nostra classe ] ---------------------------------------------------------------------
		 new Carta(null, "Podz", 6, RaritaCarta.EPICA, ClasseCarta.AESTHETICS, 7, 7, new Effetto(TipoEffetto.Provocazione), "E così ciccione che gli altri personnaggi si nascondono dietro di lui"),
		 new Carta(null, "Michel", 3, RaritaCarta.RARA, ClasseCarta.SECCHIONE, 2, 3, " "), // new AbilitaCarta[] {AbilitaCarta.IMMUNE}
		 new Carta(null, "Bertot", 4, RaritaCarta.RARA, ClasseCarta.DROGATO, 3, 4, new Effetto(TipoEffetto.Provocazione), " "),
		 new Carta(null, "Casati", 4, RaritaCarta.COMUNE, null, 4, 5, " "),
		 new Carta(null, "Casiraghi", 4, RaritaCarta.COMUNE, ClasseCarta.DROGATO, 4, 4, " "),
		 new Carta(null, "Fumagalli", 4, RaritaCarta.RARA, null, 3, 4, " "), // TODO: new AbilitaCarta[] {AbilitaCarta.RUBAVITA}
		 new Carta(null, "Steve", 2, RaritaCarta.COMUNE, null, 2, 2, new Effetto(TipoEffetto.ScudoDivino), " "),
		 new Carta(null, "Adrian", 4, RaritaCarta.LEGGENDARIA, ClasseCarta.SVILUPPATORE, 3, 4, new Effetto(TipoEffetto.GridoDiBattaglia, new Effetto(TipoEffetto.Evocazione, new Object[] { 0, false, getCarta("Simone"), 1 })), "Quando giochi questa carta evoca sul terreno 'Simone' dal tuo mazzo"),
		 new Carta(null, "Fabio", 5, RaritaCarta.EPICA, ClasseCarta.COMUNISTA, 4, 4, new Effetto(TipoEffetto.FuriaDelVento), "Quando giochi questa carta silenzia TUTTI i personaggi sul terreno"), // TODO: AbilitaCarta.GRIDO
		 new Carta(null, "Mande", 3, RaritaCarta.RARA, ClasseCarta.SECCHIONE, 1, 1, new Effetto[] { new Effetto(TipoEffetto.ScudoDivino), new Effetto(TipoEffetto.Miglioramento, new Object[] { 1, 0, "Michel", true, 1, true, 1 }) }, "Se e' presente 'Michel' nel tuo campo di battaglia aumenta le sue statistiche +1/+1"), // TODO: new AbilitaCarta(AbilitaCarta.FURTIVO)
		 new Carta(null, "Mustafa", 4, RaritaCarta.RARA, null, 3, 4, "Quando giochi questa carta prendi il controllo di un nemico casuale"), // new AbilitaCarta[] {AbilitaCarta.GRIDO}
		 new Carta(null, "Peg", 3, RaritaCarta.COMUNE, ClasseCarta.AESTHETICS, 3, 3, new Effetto(TipoEffetto.Provocazione), " "),
		 new Carta(null, "Penatti", 5, RaritaCarta.RARA, null, 4, 2, " "), // new AbilitaCarta[] {AbilitaCarta.RUBAVITA}
		 new Carta(null, "Puente", 3, RaritaCarta.EPICA, null, 2, 1, new Effetto(TipoEffetto.Altro), "Ogni volta che peschi una carta, ne aggiunge una copia nel tuo mazzo"), // TODO: Non puo essere attaccata da tipi
		 new Carta(null, "Stephen", 6, RaritaCarta.RARA, ClasseCarta.AESTHETICS, 5, 4, new Effetto(TipoEffetto.Carica), " "),
		 new Carta(null, "Riccardi", 7, RaritaCarta.EPICA, ClasseCarta.SECCHIONE, 4, 6, new Effetto(TipoEffetto.Miglioramento, new Object[] { 1, -1, -1, true, -1, true, -1 }), "Quando giochi questa carta diminuisce le statistiche di TUTTI i personaggi di -1/-1"), // TODO: new AbilitaCarta(AbilitaCarta.IMMUNE)
		 new Carta(null, "Rossi", 5, RaritaCarta.RARA, ClasseCarta.AESTHETICS, 4, 3, new Effetto(TipoEffetto.Carica), " "),
		 // TODO: new Carta(null, "Yuri pizzaiolo", 3, RaritaCarta.EPICA, null, new AbilitaCarta[] {AbilitaCarta.VELENO}, 1, 3, null, " "), 
		 new Carta(null, "Simone", 3, RaritaCarta.LEGGENDARIA, ClasseCarta.SVILUPPATORE, 2, 2, new Effetto[] { new Effetto(TipoEffetto.GridoDiBattaglia, new Effetto(TipoEffetto.Evocazione, new Object[] { 0, false, getCarta("Adrian"), 1 })), new Effetto(TipoEffetto.Carica), new Effetto(TipoEffetto.FuriaDelVento) }, "Quando giochi questa carta evoca sul terreno 'Adrian' dal tuo mazzo"),
		 
		
		 
		 
		 //-------------------------------------------- [ Carte personaggi aggiuntivi ] -----------------------------------------------
		 new Carta(null, "Naji il pescatore", 6, RaritaCarta.EPICA, ClasseCarta.DROGATO, 6, 5, new Effetto(TipoEffetto.Probabilita, new Object[] { 50, new Effetto(TipoEffetto.Pesca) }), "All'inizio di ogni tuo turno hai il 50% di possibilita' di pescare una carta aggiuntiva."), // TODO new AbilitaCarta[] {AbilitaCarta.EFFETTO},
		 new Carta(null, "Nick", 8, RaritaCarta.EPICA, ClasseCarta.HACKERINO, 6, 8, new Effetto[] { new Effetto(TipoEffetto.Provocazione), new Effetto(TipoEffetto.Carica) }, " "),
		 new Carta(null, "Pizio", 3, RaritaCarta.RARA, ClasseCarta.HACKERINO, 3, 3, "Solito easter egg"), // new AbilitaCarta[] {AbilitaCarta.FURTIVO},
		 new Carta(null, "Luciano", 5, RaritaCarta.RARA, ClasseCarta.PERSONALESC, 4, 4, new Effetto(TipoEffetto.Attacca, new Object[] { 1, -1, ClasseCarta.HACKERINO, 100 }), "Quando giochi questa carta distrugge TUTTI i personaggi che appartengono alla classe 'HACKERINO'"),
		 new Carta(null, "Angelina", 2, RaritaCarta.LEGGENDARIA, ClasseCarta.PERSONALESC, 0, 2, "Non fa un cazzo"),
		 new Carta(null, "Prof. Mariani", 3, RaritaCarta.RARA, ClasseCarta.PERSONALESC, 3, 3, new Effetto(TipoEffetto.GridoDiBattaglia, new Effetto(TipoEffetto.Attacca, new Object[] { 1, -1, "Nick", 100 })), "Quando giochi questa carta distrugge il personaggio 'Nick' se presente nel campo di battaglia"),
		 new Carta(null, "Meani", 4, RaritaCarta.RARA, ClasseCarta.COMUNISTA, 2, 3, new Effetto(TipoEffetto.GridoDiBattaglia, new Effetto(TipoEffetto.Miglioramento, new Object[] { 1, -1, -1, false, 2, false, 2 })), "Imposta l'attacco e la salute di TUTTI i personaggi a 2/2"),
		 new Carta(null, "Prof. Giuimp", 5, RaritaCarta.RARA, ClasseCarta.PERSONALESC, 4, 4, new Effetto(TipoEffetto.GridoDiBattaglia, new Effetto(TipoEffetto.Evocazione, new String[] { "Studente generico", "Studente generico" })), "Quando giochi questa carta evoca 2 studenti generici 1/1"),
		 new Carta(null, "Rappresentante scolastico", 7, RaritaCarta.LEGGENDARIA, ClasseCarta.STUDENTE, 5, 5, new Effetto(TipoEffetto.Miglioramento, new Object[] { 1, -1, ClasseCarta.STUDENTE, true, 2, true, 2 }), "Quando giochi questa carta fornisce +2/+2 a TUTTTI i personaggi di tipo 'STUDENTE'"),
		 new Carta(null, "Tecnico fallito", 1, RaritaCarta.COMUNE, ClasseCarta.PERSONALESC, 1, 2, new Effetto(TipoEffetto.Probabilita, new Object[] { 50, new Effetto(TipoEffetto.ScartaCarta, new Object[] { 0, new int[] { -1 } }) }), "Quando giochi questa carta hai il 50% di possibilita di scartare una carta casuale dalla tua mano"),
		 new Carta(null, "Mister X", 3, RaritaCarta.EPICA, ClasseCarta.HACKERINO, 3, 2, " "), // TODO: new AbilitaCarta[] {AbilitaCarta.FURTIVO}
		 new Carta(null, "Vice Preside", 6, RaritaCarta.EPICA, ClasseCarta.PERSONALESC, 4, 4, new Effetto(TipoEffetto.Miglioramento, new Object[] { 1, -1, ClasseCarta.PERSONALESC, true, 1, true, 1 }), "Quando giochi questa carta fornisce +1/+1 a TUTTI i personaggi di tipo 'PERSONALE SCOLASTICO'"),
		 new Carta(null, "Prof. Catalano", 4, RaritaCarta.RARA, ClasseCarta.PERSONALESC, 4, 4, new Effetto(TipoEffetto.Provocazione), " "),
		 new Carta(null, "Prof. Fea", 5, RaritaCarta.RARA, ClasseCarta.PERSONALESC, 3, 3, new Effetto(TipoEffetto.GridoDiBattaglia, new Effetto(TipoEffetto.ScartaCarta, new Object[] { 1, new int[] { -1 } })), "Quando giochi questa carta fai tornare un personaggio in mano all'avversario"),
		 new Carta(null, "Prof. Pelli", 3, RaritaCarta.RARA, ClasseCarta.PERSONALESC, 3, 3, " "),
		 
		 
	
		 
		
		 new Carta(null, "Preside", 8, RaritaCarta.LEGGENDARIA, ClasseCarta.PERSONALESC, 6, 6, new Effetto[] { new Effetto(TipoEffetto.ScudoDivino), new Effetto(TipoEffetto.Miglioramento, new Object[] { 1, -1, ClasseCarta.PERSONALESC, true, 2, true, 2 }) }, "Quando giochi questa carta fornisce +2/+2 a TUTTI i personaggi di tipo 'PERSONALE SCOLASTICO'"),
		 
		 // Carte personaggi generici (Non sbloccabili) -- Secondo me, anche si
		 new Carta(null, "Studente generico", 1, RaritaCarta.COMUNE, ClasseCarta.STUDENTE, 1, 1, " "),
		 new Carta(null, "Personale Ata", 1, RaritaCarta.COMUNE, ClasseCarta.PERSONALESC, 1, 1, " "),
		
		 
		 //---------------------------------------------------Carte magia-------------------------------------------------------------
		 //Attributi Magia
		 //Id,immagine, nome, costo ,raritï¿½, effetto
		 //  GLI ID DELLE CARTE MAGIE VANNO DA 100 IN POI
		 new Carta(null, "Moneta", 0, RaritaCarta.COMUNE, null, "Ti da 1 punto mana solo per questo turno"),
		 new Carta(null, "Potere agli sviluppatori!", 10, RaritaCarta.LEGGENDARIA, null, "Evoca sul campo di battaglia 'Simone' e 'Adrian' e gli fornisce +5/+5"),
		 new Carta(null, "Trasformazione", 5, RaritaCarta.RARA, null, "Trasforma un personaggio nemico in un personale ATA 1/1"),
		 new Carta(null, "Alla ricerca della F*ga!", 8, RaritaCarta.EPICA, null, "Se controlli 'Podz','Pegs','Rossi' e 'Stephen' sul campo di battaglia aumenta loro i parametri +3/+3 e gli fornisce PROVOCAZIONE"),
         new Carta(null, "Uguaglianza", 4, RaritaCarta.RARA, null, "Scegli un bersaglio sul terreno, TUTTI i personaggi sul campo di battaglia copiano il suo attacco e la sua difesa"),	
         new Carta(null, "Croccantelle", 2, RaritaCarta.COMUNE, null, "Aumenta di +1/+1 un bersaglio che NON appartiene alla classe 'PERSONALE SCOLASTICO"),
         new Carta(null, "Fumo nei bagni", 3, RaritaCarta.RARA, null, "Fornisce furtivitÃ  a tutti i tuoi personaggi, se appartengono alla classe 'DROGATO' gli fornisce +1/+1"),
         new Carta(null, "Interrogazione", 4, RaritaCarta.RARA, null, "Fai tornare i personaggi nemici che NON appartengono alla classe 'PERSONALE SCOLASTICO' nel mazzo dell'avversario"),
	 };
	
	
	public static Carta getCarta(String nome)
	{
		for(int i=0;i<collezioneCarte.length;i++)
		{
			if(collezioneCarte[i].nome.equals(nome)==true)
			{
				return collezioneCarte[i];
			}
		}
		
		System.out.println("Errore, carta not found");
		return null;
	}
		
	// Restituisce una carta a caso
	public static Carta getRandomCarta()
	{
		return collezioneCarte[new Random().nextInt(collezioneCarte.length)];
	}
	
	public static int[] cartaArrayToPosArray(Carta[] carte)
	{
		int[] arrayCarte = new int[carte.length];
		
		for (int i = 0; i < carte.length; i++)
		{
			for (int j = 0; j < collezioneCarte.length; j++)
			{
				if(carte[i].nome.equals(collezioneCarte[j].nome))
				{
					arrayCarte[i] = j;
					break;
				}
			}
		}
		
		return arrayCarte;
	}
	
	/*public static Carta[] posArrayToCartaArray(int[] carte)
	{
		Carta[] arrayCarte = new Carta[carte.length];
		
		for (int i = 0; i < carte.length; i++)
		{
			arrayCarte[i] = collezioneCarte[i];
		}
		
		return arrayCarte;
	}*/
}