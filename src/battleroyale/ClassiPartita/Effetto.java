package battleroyale.ClassiPartita;

public class Effetto
{
	public TipoEffetto tipoEffetto;
	public Object[] payload;
	
	public Effetto(TipoEffetto tipoEffetto)
	{
		this.tipoEffetto = tipoEffetto;
		this.payload = payload;
	}
	
	public Effetto(TipoEffetto tipoEffetto, Object payload)
	{
		this.tipoEffetto = tipoEffetto;
		this.payload = new Object[] { payload };
	}
	
	public Effetto(TipoEffetto tipoEffetto, Object[] payload)
	{
		this.tipoEffetto = tipoEffetto;
		this.payload = payload;
	}
	
	public static enum TipoEffetto
	{
		// Senza payload
		Provocazione,
		Carica,
		FuriaDelVento,
		ScudoDivino,
		
		// Con payload
		Cura,
		Attacca,
		Evocazione,
		Miglioramento,
		GridoDiBattaglia,
		Rabbia,
		RantoloDiMorte,
		InizioTurno,
		FineTurno,
		
		// Altro
		Altro,
	}
}