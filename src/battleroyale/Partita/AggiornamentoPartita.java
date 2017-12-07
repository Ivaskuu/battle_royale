package battleroyale.Partita;

public class AggiornamentoPartita
{
	public AzionePartita azione;
	public Object[] parametri;
	
	public AggiornamentoPartita(AzionePartita azione)
	{
		this.azione = azione;
	}
	
	public AggiornamentoPartita(AzionePartita azione, Object parametro)
	{
		this.azione = azione;
		this.parametri = new Object[] {parametro};
	}
	
	public AggiornamentoPartita(AzionePartita azione, Object[] parametri)
	{
		this.azione = azione;
		this.parametri = parametri;
	}
	
	public enum AzionePartita
	{
		AggiungiCartaSulCampo, // La pos della carta nella classe CollezioneCarte
		Attacco, // La pos nel carteSulCampo[] dell'attaccante e dell'avversario
		Pesca, // La pos della carta nella classe CollezioneCarte
		CambiaTurno // Nessun parametro
	};
}