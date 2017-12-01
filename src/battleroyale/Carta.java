package battleroyale;

public class Carta 
{
   //Instanzio gli attributi generali
	public int Id;
    public int costo_mana;
    public String nome;
    public String effetto;
    public char rarita;
   // public int livello;   TODO
    public int salute;
    public int attacco;
    public TipoCarta tipoCarta;
    public RaritaCarta raritaCarta;
    public ClasseCarta classeCarta;
    public AbilitaCarta abilitaCarta;
    
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
    	AESTHETICS, COMUNISTA, SVILUPPATORE, SECCHIONE, DROGATO
    }
    
    //Classe che identifica eventuali effetti del personaggio
    public enum AbilitaCarta
    {
    	PROVOCAZIONE, RUBAVITA, FURTIVO, CARICA, FURIA, IMMUNE 
    }
    
    //Costruttore per carta magia
    public Carta(int id, String n, int costo_m, RaritaCarta rar, String eff)
    {
    	Id=id;
        costo_mana = costo_m;
        nome = n;
        tipoCarta=TipoCarta.MAGIA;
        raritaCarta = rar;
        effetto=eff;
    }
    
    //Costruttore per carte personaggio
    public Carta(int id, String n, int costo_m, RaritaCarta rar, ClasseCarta cls, AbilitaCarta abilita, int sal, int atk, String eff)
    {
    	Id=id;
        costo_mana = costo_m;
        nome = n;
        classeCarta=cls;
        salute=sal;
        attacco = atk;
        tipoCarta=TipoCarta.PERSONAGGIO;
        abilitaCarta=abilita;
        raritaCarta=rar;
        effetto=eff;
    }
    
 
    

}
