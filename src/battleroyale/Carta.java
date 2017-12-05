package battleroyale;

import java.awt.image.BufferedImage;

public class Carta 
{
	//
    // ATTRIBUTI
    //
	
	// Attributi generali
    public String nome;
    public int saluteMax;
    public int attaccoMax;
    public int costoMana;

    public TipoCarta tipoCarta;
    public RaritaCarta raritaCarta;
    public ClasseCarta classeCarta;
    public AbilitaCarta[] abilitaCarta;
    
	public BufferedImage immagine;
    public String effetto;
    
    // Attributi necessari durante la partita
    public int saluteAtt;
    public int attaccoAtt;
    public int giocatePerTurnoMax; // Quante volte la carta e stata giocata durante questo turno (di solito 1)
    public int giocatePerTurnoAtt; // Quante volte la carta e stata giocata durante questo turno (di solito 1)
    
    // Il tipo di carta
    public enum TipoCarta
	{
	    PERSONAGGIO, MAGIA
	}
    
    // La rarita della carta
    public enum RaritaCarta
    {
    	COMUNE, RARA, EPICA, LEGGENDARIA
    }
    
    // La classe della carta
    public enum ClasseCarta 
    {
    	SVILUPPATORE,
    	STUDENTE,AESTHETICS, COMUNISTA, HACKERINO, SECCHIONE, DROGATO, PERSONALESC
    }
    
	// Eventuali abilita del personaggio
    public enum AbilitaCarta
    {
    	PROVOCAZIONE, RUBAVITA, FURTIVO, CARICA, FURIA, IMMUNE, SCUDO, RANTOLO, GRIDO, EFFETTO, VELENO
    }
    
    //
    // COSTRUTTORI
    //
   
    // Carta magia
    public Carta(BufferedImage img,String n, int costo_m, RaritaCarta rar, String eff)
    {
    	immagine = img;
        costoMana = costo_m;
        nome = n;
        tipoCarta = TipoCarta.MAGIA;
        raritaCarta = rar;
        effetto = eff;
    }

    /*// Carta magia durante la partita
    public static Carta cartaToCartaPartita(Carta carta, int giocatePerTurno)
    {
    	carta.saluteAtt = carta.saluteMax;
    	carta.attaccoAtt = carta.attaccoMax;
    	carta.giocatePerTurnoMax = giocatePerTurno;
    	carta.giocatePerTurnoAtt = giocatePerTurno;
    	
    	return carta;
    }*/
    
    // Carta personaggio
    public Carta(BufferedImage img, String n, int costo_m, RaritaCarta rar, ClasseCarta cls, AbilitaCarta[] abilita, int atk, int sal, String eff)
    {
    	immagine = img;
        costoMana = costo_m;
        nome = n;
        classeCarta = cls;
        saluteMax = sal;
        attaccoMax = atk;
        abilitaCarta = abilita; //Un giocatore puo avere una o piu abilita
        tipoCarta = TipoCarta.PERSONAGGIO;
        raritaCarta = rar;
        effetto = eff;
    }
    
    // Carta personaggio durante la partita
    public static Carta cartaToCartaPartita(Carta carta)
    {
    	carta.saluteAtt = carta.saluteMax;
    	carta.attaccoAtt = carta.attaccoMax;
    	carta.giocatePerTurnoMax = 1; // TODO: Se effetto allora incrementalo
    	carta.giocatePerTurnoAtt = carta.giocatePerTurnoMax;
    	
    	return carta;
    }
}