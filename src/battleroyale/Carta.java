package battleroyale;

public abstract class Carta 
{
   //Instanzio gli attributi che possono avere sia le carte magie che carte personaggio
   // Costo mana- nome- descrizione/effetto
    public int costo_mana;
    public String nome;
    
    public Carta(int costo_m, String n)
    {
        costo_mana= costo_m;
        nome =n;
    }
    
    

}
