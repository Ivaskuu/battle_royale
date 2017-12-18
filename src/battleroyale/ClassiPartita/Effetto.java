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
		Furtivita,
		Veleno,
		Rubavita,
		Immune,
		
		// Con payload
		Cura,
		Attacca,
		Evocazione,
		Miglioramento,
		GridoDiBattaglia,
		Pesca,
		Rabbia,
		RantoloDiMorte,
		InizioTurno,
		FineTurno,
		ScartaCarta,
		
		// Altro
		Probabilita,
		Altro,
	}
}