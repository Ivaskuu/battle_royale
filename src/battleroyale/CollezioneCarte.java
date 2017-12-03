package battleroyale;

import battleroyale.Carta.AbilitaCarta;
import battleroyale.Carta.ClasseCarta;
import battleroyale.Carta.RaritaCarta;

public class CollezioneCarte {
	
	
	
	//Attributi personaggi
	//id,Immagine, nome, costo_mana,  rarit�, classe, Abilità[] ,attacco , salute ,salutemax, effetto/descrizione
	
	public static Carta[] collezione_carte = 
		{
		  //--------------------------------------------Carte personaggi base---------------------------------------------------------------------
		 new Carta(0,null,"Podz",6,RaritaCarta.EPICA,ClasseCarta.AESTHETICS,new AbilitaCarta[] {AbilitaCarta.PROVOCAZIONE} ,7,7,7," "),
		 new Carta(1,null,"Michel", 3, RaritaCarta.RARA, ClasseCarta.SECCHIONE,new AbilitaCarta[] {AbilitaCarta.IMMUNE} ,2,3,3," "),
		 new Carta(2,null,"Bertot", 4, RaritaCarta.RARA, ClasseCarta.DROGATO,new AbilitaCarta[] {AbilitaCarta.PROVOCAZIONE},  3,4,4," "),
		 new Carta(3,null,"Casati", 4, RaritaCarta.COMUNE, null, null, 4,5,5," "),
		 new Carta(4,null,"Casiraghi", 4, RaritaCarta.COMUNE, ClasseCarta.DROGATO, null, 4,4,4, " "),
		 new Carta(5,null,"Fumagalli", 4, RaritaCarta.RARA, null, new AbilitaCarta[] {AbilitaCarta.RUBAVITA},3,4,4," "),
		 
		 
		 
		 
		 
		  
				
		 
		 
		 
		 
		 
		 
		 
		 //---------------------------------------------Carte personaggi aggiuntivi------------------------------------------------
		 // GLI ID DELLE CART DEI PERSONAGGI VANNO DA 30 A 100
		 new Carta(30,null,"Naji il pescatore",6,RaritaCarta.EPICA, ClasseCarta.DROGATO, null, 6,5,5,"All'inizio di ogni tuo turno hai il 50% di possibilità di pescare una carta aggiuntiva. "), 
		 new Carta(31,null,"Nick", 8, RaritaCarta.EPICA, null, new AbilitaCarta[] {AbilitaCarta.PROVOCAZIONE, AbilitaCarta.CARICA},6,8,8," "),
		 new Carta(32,null,"Pizio",3, RaritaCarta.RARA, null, new AbilitaCarta[] {AbilitaCarta.FURTIVO}, 3,3,3," "),
		 
		 
		 
		 
	
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 //---------------------------------------------------Carte magia-------------------------------------------------------------
		//Attributi Magia
		//Id, nome, costo ,rarit�, effetto
		 //  GLI ID DELLE CARTE MAGIE VANNO DA 100 IN POI
		 new Carta(100,null,"Moneta", 0, null, "Ti da 1 punto mana solo per questo turno"),
	
				
		};
	
	
	

}
