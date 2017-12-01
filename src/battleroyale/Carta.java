package battleroyale;

public abstract class Carta 
{
   //Instanzio gli attributi generali
	public int Id;
    public int costo_mana;
    public String nome;
    public char rarita;
   // public int livello;   TODO
    public String classe;
    public int salute;
    public int attacco;
    public int difesa;
    public TipoCarta tipoCarta;
    public RaritaCarta raritaCarta;
    
    //Classe che determina il tipo di carta
    public enum TipoCarta
	{
	    PERSONAGGIO, MAGIA
	}
    
    //Classe che determina la rarià della carta
    public enum RaritaCarta
    {
    	COMUNE, RARA, EPICA, LEGGENDARIA
    }
    
    //Costruttore per carta magia
    public Carta(int id, String n, int costo_m, RaritaCarta rar)
    {
    	Id=id;
        costo_mana = costo_m;
        nome = n;
        tipoCarta=TipoCarta.MAGIA;
        raritaCarta = rar;
    }
    
    //Costruttore per carte personaggio
    public Carta(int id, String n, int costo_m, RaritaCarta rar,  String cls, int sal, int atk, int def)
    {
    	Id=id;
        costo_mana = costo_m;
        nome = n;
        classe=cls;
        salute=sal;
        attacco = atk;
        difesa=def;
        tipoCarta=TipoCarta.PERSONAGGIO;
        raritaCarta=rar;
    }
    
 
    

}
