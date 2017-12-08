package battleroyale;

public class EffettoCarta {
	
	public TriggerEffetto triggerEffetto;
	public TipoEffetto tipoEffetto;

	public EffettoCarta(TriggerEffetto triggerEffetto, TipoEffetto tipoEffetto) 
	{
		this.triggerEffetto = triggerEffetto;
		this.tipoEffetto = tipoEffetto;
	}


	public static enum TriggerEffetto
	{
		OnInizioTurno, 
		OnFineTurno, 
		OnPesca,
		OnDistruggi,
		OnSubisciDanno,
		OnEvoca,
						//TODO
	}
	
	public static enum TipoEffetto
	{
		EvocaCarta,
		DistruggiCarta, 
		SilenziaCarta,
		Converti,
		Pesca,
		
	}
	
	

}

	

	