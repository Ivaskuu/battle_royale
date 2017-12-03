package battleroyale;

import battleroyale.Carta.AbilitaCarta;
import battleroyale.Carta.ClasseCarta;
import battleroyale.Carta.RaritaCarta;

public class CollezioneCarte {
	
	//Attributi Magia
	//Id, nome, costo ,rarit�, effetto
	
	//Attributi personaggi
	//id,Immagine, nome, costo_mana,  rarit�, classe, Abilità[] ,attacco , salute ,salutemax, 
	
	public static Carta[] collezione_carte = 
		{
		  //--------------------------------------------Carte personaggi base---------------------------------------------------------------------
		 new Carta(0,null,"Podz",6,RaritaCarta.EPICA,ClasseCarta.AESTHETICS,new AbilitaCarta[] {AbilitaCarta.PROVOCAZIONE} ,7,7,7," "),
		 new Carta(1,null,"Michel", 3, RaritaCarta.RARA, ClasseCarta.SECCHIONE,new AbilitaCarta[] {AbilitaCarta.IMMUNE} ,2,3,3," "),
		 new Carta(2,null,"Bertot", 4, RaritaCarta.RARA, ClasseCarta.DROGATO,new AbilitaCarta[] {AbilitaCarta.PROVOCAZIONE},  3,4,4," "),
		 new Carta(3,null,"Casati", 4, RaritaCarta.COMUNE, null, null, 4,5,5,null),
		 
		  
				
				
				
		};
	
	
	

}
