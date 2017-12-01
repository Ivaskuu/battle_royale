package battleroyale;

public class Personaggio extends Carta
{
    public int livello;
    public String classe;
    public int salute;
    public int attacco;
    public int difesa;
  
    
    public Personaggio(int costo_m, String n,char rar, int liv, String cls, int hp, int atk, int def) 
    {
        super(costo_m, n, rar);
        nome=n;
        livello=liv;
        classe=cls;
        salute=hp;
        attacco=atk;
        difesa=def;
    }
    
    
    
    
    
}
