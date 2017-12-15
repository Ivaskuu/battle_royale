package battleroyale;

import java.util.Random;

import battleroyale.ClassiPartita.Effetto;

public class EffettoSpeciale {
	
	public TriggerEffetto triggerEffetto;
	public Effetto effetto;
	public Object[] payload;

	
	public EffettoSpeciale(TriggerEffetto triggerEffetto, Effetto effetto) 
	{
		this.triggerEffetto = triggerEffetto;
		this.effetto= effetto;
	}
	
	public EffettoSpeciale(TriggerEffetto triggerEffetto, Effetto effetto, Object payload) 
	{
		this.triggerEffetto = triggerEffetto;
		this.effetto= effetto;
		this.payload=new Object[]{payload}; 
	}
	
	public EffettoSpeciale(TriggerEffetto triggerEffetto, Effetto effetto, Object[] payload) 
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
}
	