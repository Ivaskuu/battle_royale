package battleroyale;

import battleroyale.Carta.AbilitaCarta;
import battleroyale.Carta.ClasseCarta;
import battleroyale.Carta.RaritaCarta;

public class CollezioneCarte {
	
	
	
	//Attributi personaggi
	//id,Immagine, nome, costo_mana,  rarit�, classe, Abilità[] ,attacco ,attaccomax, salute ,salutemax, effetto/descrizione
	
	public static Carta[] collezione_carte = 
		{
		 //--------------------------------------------Carte personaggi base---------------------------------------------------------------------
		 new Carta(0,null,"Podz",6,RaritaCarta.EPICA,ClasseCarta.AESTHETICS,new AbilitaCarta[] {AbilitaCarta.PROVOCAZIONE},7,7," "),
		 new Carta(1,null,"Michel", 3, RaritaCarta.RARA, ClasseCarta.SECCHIONE,new AbilitaCarta[] {AbilitaCarta.IMMUNE} ,2,3," "),
		 new Carta(2,null,"Bertot", 4, RaritaCarta.RARA, ClasseCarta.DROGATO,new AbilitaCarta[] {AbilitaCarta.PROVOCAZIONE},3,4," "),
		 new Carta(3,null,"Casati", 4, RaritaCarta.COMUNE, null, null,4,5," "),
		 new Carta(4,null,"Casiraghi", 4, RaritaCarta.COMUNE, ClasseCarta.DROGATO, null,4,4, " "),
		 new Carta(5,null,"Fumagalli", 4, RaritaCarta.RARA, null, new AbilitaCarta[] {AbilitaCarta.RUBAVITA},3,4," "),
		 new Carta(6,null,"Steve", 2, RaritaCarta.COMUNE, null, new AbilitaCarta[] {AbilitaCarta.SCUDO},2,2," "),
		 new Carta(7,null,"Adrian",4,RaritaCarta.LEGGENDARIA,ClasseCarta.SVILUPPATORE, new AbilitaCarta[] {AbilitaCarta.GRIDO},3,4,"Quando giochi questa carta evoca sul terreno 'Simone' dal tuo mazzo"),
		 new Carta(8,null,"Fabio",5,RaritaCarta.EPICA,ClasseCarta.COMUNISTA, new AbilitaCarta[] {AbilitaCarta.FURIA,AbilitaCarta.GRIDO},4,4,"Quando giochi questa carta silenzia TUTTI i personaggi sul terreno"),
		 new Carta(9,null,"Mande",3,RaritaCarta.RARA,ClasseCarta.SECCHIONE,new AbilitaCarta[] {AbilitaCarta.FURTIVO, AbilitaCarta.SCUDO, AbilitaCarta.GRIDO},1,1,"Se è presente Michel nel tuo campo di battaglia aumenta le sue statistiche +1/+1"),
		 new Carta(10,null,"Mustafa",4,RaritaCarta.RARA,null,new AbilitaCarta[] {AbilitaCarta.GRIDO},3,4,"Quando giochi questa carta prendi il controllo di un nemico casuale"),
		 new Carta(11,null,"Peg",3,RaritaCarta.COMUNE, ClasseCarta.AESTHETICS, new AbilitaCarta[] {AbilitaCarta.PROVOCAZIONE},3,3," "),
		 new Carta(12,null,"Penatti", 5, RaritaCarta.RARA, null, new AbilitaCarta[] {AbilitaCarta.RUBAVITA},4,2," "),
		 new Carta(12,null,"Puente",3,RaritaCarta.EPICA,null, new AbilitaCarta[] {AbilitaCarta.EFFETTO},2,1,"Ogni volta che peschi una carta, ne aggiunge una copia nel tuo mazzo"),
		 new Carta(13,null,"Stephen",6,RaritaCarta.RARA,ClasseCarta.AESTHETICS,new AbilitaCarta[] {AbilitaCarta.CARICA},5,4," "),
		 new Carta(13,null,"Riccardi",7,RaritaCarta.EPICA,ClasseCarta.SECCHIONE,new AbilitaCarta[] {AbilitaCarta.GRIDO, AbilitaCarta.IMMUNE},4,6,"Quando giochi questa carta diminuisce le statistiche di TUTTI personaggi di -1/-1"),
		 new Carta(14,null,"Rossi",5,RaritaCarta.RARA,ClasseCarta.AESTHETICS,new AbilitaCarta[] {AbilitaCarta.CARICA},4,3," "),
		 new Carta(15,null,"Yuri pizzaiolo",3,RaritaCarta.EPICA,null,new AbilitaCarta[] {AbilitaCarta.VELENO},3,1," "), 
		 new Carta(16,null,"Simone",3,RaritaCarta.LEGGENDARIA,ClasseCarta.SVILUPPATORE,new AbilitaCarta[] {AbilitaCarta.GRIDO,AbilitaCarta.CARICA,AbilitaCarta.FURIA},2,2,"Quando giochi questa carta evoca sul terreno 'Adrian' dal tuo mazzo"),
		 
		
		 
		 
		 //---------------------------------------------Carte personaggi aggiuntivi------------------------------------------------
		 // GLI ID DELLE CART DEI PERSONAGGI VANNO DA 30 A 100
		 new Carta(30,null,"Naji il pescatore",6,RaritaCarta.EPICA, ClasseCarta.DROGATO, new AbilitaCarta[] {AbilitaCarta.EFFETTO},6,5,"All'inizio di ogni tuo turno hai il 50% di possibilità di pescare una carta aggiuntiva. "), 
		 new Carta(31,null,"Nick", 8, RaritaCarta.EPICA, ClasseCarta.HACKERINO, new AbilitaCarta[] {AbilitaCarta.PROVOCAZIONE, AbilitaCarta.CARICA},6,8," "),
		 new Carta(32,null,"Pizio",3, RaritaCarta.RARA, ClasseCarta.HACKERINO, new AbilitaCarta[] {AbilitaCarta.FURTIVO},3,3," "),
		 new Carta(33,null,"Luciano",5,RaritaCarta.RARA,ClasseCarta.PERSONALESC,new AbilitaCarta[] {AbilitaCarta.GRIDO},4,4,"Quando giochi questa carta distrugge TUTTI i personaggi che appartengono alla classe 'HACKERINO'"),
		 new Carta(34,null,"Concettina",2,RaritaCarta.LEGGENDARIA,ClasseCarta.PERSONALESC, null,0,2," "),
		 new Carta(35,null,"Prof. Mariani",3,RaritaCarta.RARA,ClasseCarta.PERSONALESC,new AbilitaCarta[] {AbilitaCarta.GRIDO},3,3,"Quando giochi questa carta distrugge il personaggio 'Nick'"),
		 new Carta(36,null,"Meani",4, RaritaCarta.RARA, ClasseCarta.COMUNISTA, new AbilitaCarta[] {AbilitaCarta.GRIDO},2,3,"Imposta l'attacco e la salute di TUTTI i personaggi a 2/2"),
		 new Carta(37,null,"Prof. Giuimp",5, RaritaCarta.RARA, ClasseCarta.PERSONALESC, new AbilitaCarta[] {AbilitaCarta.GRIDO},4,4,"Quando giochi questa carta evoca 2 studenti generici 1/1"),
		 new Carta(38,null,"Rappresentante scolastico",7,RaritaCarta.LEGGENDARIA, ClasseCarta.STUDENTE, new AbilitaCarta[] {AbilitaCarta.GRIDO},5,5,"Quando giochi questa carta fornisce +2/+2 a TUTTTI i personaggi di tipo 'STUDENTE'"),
		 new Carta(39,null,"Tecnico fallito",1,RaritaCarta.COMUNE,ClasseCarta.PERSONALESC, new AbilitaCarta[] {AbilitaCarta.GRIDO},1,2,"Quando giochi questa carta hai il 50 % di possibilità di scartare una carta casuale dalla tua mano"),
		 new Carta(40,null,"Mister X",3,RaritaCarta.EPICA,ClasseCarta.HACKERINO,new AbilitaCarta[] {AbilitaCarta.FURTIVO},3,2," "),
		 new Carta(41,null,"Vice Preside",6,RaritaCarta.EPICA,ClasseCarta.PERSONALESC,new AbilitaCarta[] {AbilitaCarta.GRIDO},4,4,"Quando giochi questa carta fornisce +1/+1 a TUTTI i personaggi di tipo 'PERSONALE SCOLASTICO'"),

		 
		 
		 
	
		 
		
		 new Carta(40,null,"Preside",8,RaritaCarta.LEGGENDARIA,ClasseCarta.PERSONALESC, new AbilitaCarta[] {AbilitaCarta.GRIDO, AbilitaCarta.SCUDO},6,6,"Quando giochi questa carta fornisce +2/+2 a TUTTI i personaggi di tipo 'PERSONALE SCOLASTICO'"),
		 
		 //Carte personaggi generici (Non sbloccabili)
		 new Carta(98,null,"Studente generico",1,RaritaCarta.COMUNE,ClasseCarta.STUDENTE,null,1,1," "),
		 new Carta(99,null,"Personale Ata",1, RaritaCarta.COMUNE,ClasseCarta.PERSONALESC,null,1,1," "),
		
		 
		 //---------------------------------------------------Carte magia-------------------------------------------------------------
		 //Attributi Magia
		 //Id,immagine, nome, costo ,rarit�, effetto
		 //  GLI ID DELLE CARTE MAGIE VANNO DA 100 IN POI
		 new Carta(100,null,"Moneta", 0, RaritaCarta.COMUNE, "Ti da 1 punto mana solo per questo turno"),
		 new Carta(101,null,"Potere agli sviluppatori!",10,RaritaCarta.LEGGENDARIA,"Evoca sul campo di battaglia 'Simone' e 'Adrian' e gli fornisce +5/+5"),
		 new Carta(102,null,"Trasformazione",5,RaritaCarta.RARA,"Trasforma un personaggio nemico in un personale ATA 1/1"),
		 new Carta(103,null,"Alla ricerca della F*ga!",8,RaritaCarta.EPICA,"Se controlli 'Podz','Pegs','Rossi' e 'Stephen' sul campo di battaglia aumenta loro i parametri +3/+3 e gli fornisce PROVOCAZIONE"),
         new Carta(104,null,"Uguaglianza",4,RaritaCarta.RARA,"Scegli un bersaglio sul terreno, TUTTI i personaggi sul campo di battaglia copiano il suo attacco e la sua difesa"),	
         new Carta(105,null,"Croccantelle",2,RaritaCarta.COMUNE,"Aumenta di +1/+1 un bersaglio che NON appartiene alla classe 'PERSONALE SCOLASTICO"),
			
		 
		 };
		
	

}
