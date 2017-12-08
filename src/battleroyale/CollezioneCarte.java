package battleroyale;

import java.util.Random;

import battleroyale.Carta.AbilitaCarta;
import battleroyale.Carta.ClasseCarta;
import battleroyale.Carta.RaritaCarta;

public class CollezioneCarte
{
	// Attributi personaggi
	// Immagine, nome, costo_mana,  rarit�, classe, Abilità[] ,attacco ,attaccomax, salute ,salutemax, effetto/descrizione
	
	public static Carta[] collezioneCarte = 
	{
		 //--------------------------------------------Carte personaggi base---------------------------------------------------------------------
		 new Carta(null,"Podz",6,RaritaCarta.EPICA,ClasseCarta.AESTHETICS,new AbilitaCarta[] {AbilitaCarta.PROVOCAZIONE},7,7,null," "),
		 new Carta(null,"Michel", 3, RaritaCarta.RARA, ClasseCarta.SECCHIONE,new AbilitaCarta[] {AbilitaCarta.IMMUNE},2,3,null," "),
		 new Carta(null,"Bertot", 4, RaritaCarta.RARA, ClasseCarta.DROGATO,new AbilitaCarta[] {AbilitaCarta.PROVOCAZIONE},3,4,null," "),
		 new Carta(null,"Casati", 4, RaritaCarta.COMUNE, null, null,4,5,null," "),
		 new Carta(null,"Casiraghi", 4, RaritaCarta.COMUNE, ClasseCarta.DROGATO, null,4,4,null, " "),
		 new Carta(null,"Fumagalli", 4, RaritaCarta.RARA, null, new AbilitaCarta[] {AbilitaCarta.RUBAVITA},3,4,null," "),
		 new Carta(null,"Steve", 2, RaritaCarta.COMUNE, null, new AbilitaCarta[] {AbilitaCarta.SCUDO},2,2,null," "),
		 new Carta(null,"Adrian",4,RaritaCarta.LEGGENDARIA,ClasseCarta.SVILUPPATORE, new AbilitaCarta[] {AbilitaCarta.GRIDO},3,4,null,"Quando giochi questa carta evoca sul terreno 'Simone' dal tuo mazzo"),
		 new Carta(null,"Fabio",5,RaritaCarta.EPICA,ClasseCarta.COMUNISTA, new AbilitaCarta[] {AbilitaCarta.FURIA,AbilitaCarta.GRIDO},4,4,null,"Quando giochi questa carta silenzia TUTTI i personaggi sul terreno"),
		 new Carta(null,"Mande",3,RaritaCarta.RARA,ClasseCarta.SECCHIONE,new AbilitaCarta[] {AbilitaCarta.FURTIVO, AbilitaCarta.SCUDO, AbilitaCarta.GRIDO},1,1,null,"Se è presente Michel nel tuo campo di battaglia aumenta le sue statistiche +1/+1"),
		 new Carta(null,"Mustafa",4,RaritaCarta.RARA,null,new AbilitaCarta[] {AbilitaCarta.GRIDO},3,4,null,"Quando giochi questa carta prendi il controllo di un nemico casuale"),
		 new Carta(null,"Peg",3,RaritaCarta.COMUNE, ClasseCarta.AESTHETICS, new AbilitaCarta[] {AbilitaCarta.PROVOCAZIONE},3,3,null," "),
		 new Carta(null,"Penatti", 5, RaritaCarta.RARA, null, new AbilitaCarta[] {AbilitaCarta.RUBAVITA},4,2,null," "),
		 new Carta(null,"Puente",3,RaritaCarta.EPICA,null, new AbilitaCarta[] {AbilitaCarta.EFFETTO},2,1,null,"Ogni volta che peschi una carta, ne aggiunge una copia nel tuo mazzo"),
		 new Carta(null,"Stephen",6,RaritaCarta.RARA,ClasseCarta.AESTHETICS,new AbilitaCarta[] {AbilitaCarta.CARICA},5,4,null," "),
		 new Carta(null,"Riccardi",7,RaritaCarta.EPICA,ClasseCarta.SECCHIONE,new AbilitaCarta[] {AbilitaCarta.GRIDO, AbilitaCarta.IMMUNE},4,6,null,"Quando giochi questa carta diminuisce le statistiche di TUTTI personaggi di -1/-1"),
		 new Carta(null,"Rossi",5,RaritaCarta.RARA,ClasseCarta.AESTHETICS,new AbilitaCarta[] {AbilitaCarta.CARICA},4,3,null," "),
		 new Carta(null,"Yuri pizzaiolo",3,RaritaCarta.EPICA,null,new AbilitaCarta[] {AbilitaCarta.VELENO},1,3,null," "), 
		 new Carta(null,"Simone",3,RaritaCarta.LEGGENDARIA,ClasseCarta.SVILUPPATORE,new AbilitaCarta[] {AbilitaCarta.GRIDO,AbilitaCarta.CARICA,AbilitaCarta.FURIA},2,2,null,"Quando giochi questa carta evoca sul terreno 'Adrian' dal tuo mazzo"),
		 
		
		 
		 
		 //---------------------------------------------Carte personaggi aggiuntivi------------------------------------------------
		 // GLI ID DELLE CART DEI PERSONAGGI VANNO DA 30 A 100
		 new Carta(null,"Naji il pescatore",6,RaritaCarta.EPICA, ClasseCarta.DROGATO, new AbilitaCarta[] {AbilitaCarta.EFFETTO},6,5,null,"All'inizio di ogni tuo turno hai il 50% di possibilità di pescare una carta aggiuntiva. "), 
		 new Carta(null,"Nick", 8, RaritaCarta.EPICA, ClasseCarta.HACKERINO, new AbilitaCarta[] {AbilitaCarta.PROVOCAZIONE, AbilitaCarta.CARICA},6,8,null," "),
		 new Carta(null,"Pizio",3, RaritaCarta.RARA, ClasseCarta.HACKERINO, new AbilitaCarta[] {AbilitaCarta.FURTIVO},3,3,null," "),
		 new Carta(null,"Luciano",5,RaritaCarta.RARA,ClasseCarta.PERSONALESC,new AbilitaCarta[] {AbilitaCarta.GRIDO},4,4,null,"Quando giochi questa carta distrugge TUTTI i personaggi che appartengono alla classe 'HACKERINO'"),
		 new Carta(null,"Concettina",2,RaritaCarta.LEGGENDARIA,ClasseCarta.PERSONALESC, null,0,2,null," "),
		 new Carta(null,"Prof. Mariani",3,RaritaCarta.RARA,ClasseCarta.PERSONALESC,new AbilitaCarta[] {AbilitaCarta.GRIDO},3,3,null,"Quando giochi questa carta distrugge il personaggio 'Nick'"),
		 new Carta(null,"Meani",4, RaritaCarta.RARA, ClasseCarta.COMUNISTA, new AbilitaCarta[] {AbilitaCarta.GRIDO},2,3,null,"Imposta l'attacco e la salute di TUTTI i personaggi a 2/2"),
		 new Carta(null,"Prof. Giuimp",5, RaritaCarta.RARA, ClasseCarta.PERSONALESC, new AbilitaCarta[] {AbilitaCarta.GRIDO},4,4,null,"Quando giochi questa carta evoca 2 studenti generici 1/1"),
		 new Carta(null,"Rappresentante scolastico",7,RaritaCarta.LEGGENDARIA, ClasseCarta.STUDENTE, new AbilitaCarta[] {AbilitaCarta.GRIDO},5,5,null,"Quando giochi questa carta fornisce +2/+2 a TUTTTI i personaggi di tipo 'STUDENTE'"),
		 new Carta(null,"Tecnico fallito",1,RaritaCarta.COMUNE,ClasseCarta.PERSONALESC, new AbilitaCarta[] {AbilitaCarta.GRIDO},1,2,null,"Quando giochi questa carta hai il 50 % di possibilità di scartare una carta casuale dalla tua mano"),
		 new Carta(null,"Mister X",3,RaritaCarta.EPICA,ClasseCarta.HACKERINO,new AbilitaCarta[] {AbilitaCarta.FURTIVO},3,2,null," "),
		 new Carta(null,"Vice Preside",6,RaritaCarta.EPICA,ClasseCarta.PERSONALESC,new AbilitaCarta[] {AbilitaCarta.GRIDO},4,4,null,"Quando giochi questa carta fornisce +1/+1 a TUTTI i personaggi di tipo 'PERSONALE SCOLASTICO'"),
		 new Carta(null,"Prof. Catalano",4,RaritaCarta.RARA,ClasseCarta.PERSONALESC,new AbilitaCarta[] {AbilitaCarta.PROVOCAZIONE},4,4,null," "),
		 new Carta(null,"Prof. Fea",5,RaritaCarta.RARA,ClasseCarta.PERSONALESC, new AbilitaCarta[] {AbilitaCarta.GRIDO},3,3,null,"Quando giochi questa carta fai tornare un personaggio in mano all'avversario"),
		 new Carta(null,"Prof. Pelli",3,RaritaCarta.RARA,ClasseCarta.PERSONALESC, null, 3,3,null," "),
		 
		 
	
		 
		
		 new Carta(null,"Preside",8,RaritaCarta.LEGGENDARIA,ClasseCarta.PERSONALESC, new AbilitaCarta[] {AbilitaCarta.GRIDO, AbilitaCarta.SCUDO},6,6,null,"Quando giochi questa carta fornisce +2/+2 a TUTTI i personaggi di tipo 'PERSONALE SCOLASTICO'"),
		 
		 //Carte personaggi generici (Non sbloccabili)
		 new Carta(null,"Studente generico",1,RaritaCarta.COMUNE,ClasseCarta.STUDENTE,null,1,1,null," "),
		 new Carta(null,"Personale Ata",1, RaritaCarta.COMUNE,ClasseCarta.PERSONALESC,null,1,1,null," "),
		
		 
		 //---------------------------------------------------Carte magia-------------------------------------------------------------
		 //Attributi Magia
		 //Id,immagine, nome, costo ,rarit�, effetto
		 //  GLI ID DELLE CARTE MAGIE VANNO DA 100 IN POI
		 new Carta(null,"Moneta", 0, RaritaCarta.COMUNE,null, "Ti da 1 punto mana solo per questo turno"),
		 new Carta(null,"Potere agli sviluppatori!",10,RaritaCarta.LEGGENDARIA,null,"Evoca sul campo di battaglia 'Simone' e 'Adrian' e gli fornisce +5/+5"),
		 new Carta(null,"Trasformazione",5,RaritaCarta.RARA,null,"Trasforma un personaggio nemico in un personale ATA 1/1"),
		 new Carta(null,"Alla ricerca della F*ga!",8,RaritaCarta.EPICA,null,"Se controlli 'Podz','Pegs','Rossi' e 'Stephen' sul campo di battaglia aumenta loro i parametri +3/+3 e gli fornisce PROVOCAZIONE"),
         new Carta(null,"Uguaglianza",4,RaritaCarta.RARA,null,"Scegli un bersaglio sul terreno, TUTTI i personaggi sul campo di battaglia copiano il suo attacco e la sua difesa"),	
         new Carta(null,"Croccantelle",2,RaritaCarta.COMUNE,null,"Aumenta di +1/+1 un bersaglio che NON appartiene alla classe 'PERSONALE SCOLASTICO"),
         new Carta(null,"Fumo nei bagni",3,RaritaCarta.RARA,null,"Fornisce furtività a tutti i tuoi personaggi, se appartengono alla classe 'DROGATO' gli fornisce +1/+1"),
         new Carta(null,"Interrogazione",4,RaritaCarta.RARA,null,"Fai tornare i personaggi nemici che NON appartengono alla classe 'PERSONALE SCOLASTICO' nel mazzo dell'avversario"),
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
}