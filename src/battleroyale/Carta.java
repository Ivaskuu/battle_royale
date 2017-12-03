package battleroyale;

import java.awt.image.BufferedImage;

public class Carta 
{
   //Instanzio gli attributi generali
	public int Id;
	public BufferedImage immagine;
    public int costoMana;
    public String nome;
    public String effetto;
    public char rarita;
    public int salute;
    public int saluteMax;
    public int attacco;
    public int attaccoMax;
    public TipoCarta tipoCarta;
    public RaritaCarta raritaCarta;
    public ClasseCarta classeCarta;
    public AbilitaCarta[] abilitaCarta;
    
    //Classe che determina il tipo di carta
    public enum TipoCarta
	{
	    PERSONAGGIO, MAGIA
	}
    
    //Classe che determina la rari� della carta
    public enum RaritaCarta
    {
    	COMUNE, RARA, EPICA, LEGGENDARIA
    }
    
    //Classer che determina la classe della carta
    public enum ClasseCarta 
    {
    	SVILUPPATORE,
    	AESTHETICS, COMUNISTA, HACKERINO, SECCHIONE, DROGATO, PERSONALESC
    }
    
	//Classe che identifica eventuali abilità del personaggio
    public enum AbilitaCarta
    {
    	PROVOCAZIONE, RUBAVITA, FURTIVO, CARICA, FURIA, IMMUNE, SCUDO, RANTOLO, GRIDO, EFFETTO
    }
    
   
    //Costruttore per carta magia
    public Carta(int id, BufferedImage img,String n, int costo_m, RaritaCarta rar, String eff)
    {
    	Id=id;
    	immagine=img;
        costoMana = costo_m;
        nome = n;
        tipoCarta=TipoCarta.MAGIA;
        raritaCarta = rar;
        effetto=eff;
    }
    
    //Costruttore per carte personaggio
    public Carta(int id, BufferedImage img, String n, int costo_m, RaritaCarta rar, ClasseCarta cls, AbilitaCarta[] abilita, int atk,int atkmax, int sal, int salmax, String eff)
    {
    	Id=id;
    	immagine=img;
        costoMana = costo_m;
        nome = n;
        classeCarta=cls;
        salute=sal;
        saluteMax=salmax;
        attacco = atk;
        attaccoMax = atkmax;
        tipoCarta=TipoCarta.PERSONAGGIO;    
        abilitaCarta=abilita;   //Un giocatore può avere uno o più abilità
        raritaCarta=rar;
        effetto=eff;
    }
    
 
    

}
