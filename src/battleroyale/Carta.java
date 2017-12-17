package battleroyale;

import java.awt.image.BufferedImage;

import battleroyale.ClassiPartita.Effetto;

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
    public Effetto[] effetti;
    
    public String descrizione;
	public BufferedImage immagine;

    // Attributi necessari durante la partita
    public int saluteAtt;
    public int attaccoAtt;
    public int giocatePerTurnoMax; // Quante volte la carta e stata giocata durante questo turno (di solito 1)
    public int giocatePerTurnoAtt; // Quante volte la carta pu� attaccare (di solito 1)
    
    // Il tipo di carta
    public enum TipoCarta
	{
	    PERSONAGGIO, MAGIA, ARMA
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
    	PROVOCAZIONE,    //L'avversario deve per forza attaccare i personaggi con PROVOCAZIONE prima di attaccare l'avversario    MAIN
    	RUBAVITA,        //Quando il personaggio attacca ti cura per i danni inflitti                                             MAIN
    	FURTIVO,         //Non può essere scelto come bersaglio finchè non attacca                                                MAIN
    	CARICA,          //Può attaccare nello stesso turno in cui è stata messa                                                  MAIN
    	FURIA,           //Può attaccare 2 volte in un turno                                                                      MAIN
    	IMMUNE,			 //Non può essere scelto come bersaglio di carte magie o effetti                                          MAIN
    	SCUDO,           //Ignora il primo danno che riceve                                                                       MAIN
    	RANTOLO,         //Indica un effetto che si attiva quando il personaggio viene distrutto                                  MAIN
    	GRIDO, 			 //Indica un effetto che si attiva quando il personaggio viene messo nel campo di battaglia               MAIN
    	EFFETTO,		 //Indica un effetto che si attiva in determinate situazioni  -- Es. quando peschi una carta o quando un personaggio muore   CLASSE
    	VELENO           //Distrugge immediatamente il bersaglio che attacca                                                      MAIN
    }
    
    //
    // COSTRUTTORI
    //
   
    // Carta magia
    public Carta(BufferedImage img, String n, int costo_m, RaritaCarta rar, Effetto[] eff, String descrizione)
    {
    	immagine = img;
        costoMana = costo_m;
        nome = n;
        tipoCarta = TipoCarta.MAGIA;
        effetti = eff;
        this.descrizione = descrizione;
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
    public Carta(BufferedImage img, String n, int costoM, RaritaCarta rar, ClasseCarta cls, int atk, int sal, String descrizione) // No effetto
    {
    	immagine = img;
        costoMana = costoM;
        nome = n;
        classeCarta = cls;
        saluteMax = sal;
        attaccoMax = atk;
        tipoCarta = TipoCarta.PERSONAGGIO;
        raritaCarta = rar;
        this.effetti = null;
    	this.descrizione = descrizione;
    }
    
    public Carta(BufferedImage img, String n, int costoM, RaritaCarta rar, ClasseCarta cls, int atk, int sal, Effetto effetto, String descrizione) // 1 effetto
    {
    	immagine = img;
        costoMana = costoM;
        nome = n;
        classeCarta = cls;
        saluteMax = sal;
        attaccoMax = atk;
        tipoCarta = TipoCarta.PERSONAGGIO;
        raritaCarta = rar;
        this.effetti = new Effetto[] { effetto };
    	this.descrizione = descrizione;
    }
    
    public Carta(BufferedImage img, String n, int costoM, RaritaCarta rar, ClasseCarta cls, int atk, int sal, Effetto[] effetti, String descrizione) // Piu effetti
    {
    	immagine = img;
        costoMana = costoM;
        nome = n;
        classeCarta = cls;
        saluteMax = sal;
        attaccoMax = atk;
        tipoCarta = TipoCarta.PERSONAGGIO;
        raritaCarta = rar;
        this.effetti = effetti;
    	this.descrizione = descrizione;
    }
    
    // Carta personaggio durante la partita
    public static Carta cartaToCartaPartita(Carta carta)
    {
    	carta.saluteAtt = carta.saluteMax;
    	carta.attaccoAtt = carta.attaccoMax;
    	carta.giocatePerTurnoMax = 1;
    	carta.giocatePerTurnoAtt = 0;
    	
    	return carta;
    }
}