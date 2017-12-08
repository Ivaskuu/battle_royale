package battleroyale;

import java.util.Random;

public class EffettoCarta {
	
	public TriggerEffetto triggerEffetto;
	public Effetto effetto;
	public Object[] payload;


	public EffettoCarta(TriggerEffetto triggerEffetto, Effetto effetto) 
	{
		this.triggerEffetto = triggerEffetto;
		this.effetto= effetto;

	}
	
	public EffettoCarta(TriggerEffetto triggerEffetto, Effetto effetto, Object payload) 
	{
		this.triggerEffetto = triggerEffetto;
		this.effetto= effetto;
		this.payload=new Object[]{payload}; 

	}
	
	public EffettoCarta(TriggerEffetto triggerEffetto, Effetto effetto, Object[] payload) 
	{
		this.triggerEffetto = triggerEffetto;
		this.effetto= effetto;
		this.payload = payload;

	}
	
	

	public static enum TriggerEffetto
	{
		OnInizioTurno, 
		OnFineTurno, 
		OnPesca,
		OnDistruggi,
		OnSubisciDanno,
		OnEvoca,
						//TODO Aggiungere altri trigger
	}
	
	public static enum Effetto
	{
		PescaCarta, 
		CopiaCarta,
		DistruggiCarta, 
		DaiVita,
						//TODO Aggiungere altri Tipoeffetto
	}
	
	

}

	

	