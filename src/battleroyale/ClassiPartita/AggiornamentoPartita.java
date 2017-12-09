package battleroyale.ClassiPartita;

public class AggiornamentoPartita
{
	public AzionePartita azione;
	public String[] payload;
	
	public AggiornamentoPartita(AzionePartita azione)
	{
		this.azione = azione;
	}
	
	public AggiornamentoPartita(AzionePartita azione, String payload)
	{
		this.azione = azione;
		this.payload = new String[] {payload};
	}
	
	public AggiornamentoPartita(AzionePartita azione, String[] payload)
	{
		this.azione = azione;
		this.payload = payload;
	}
	
	public enum AzionePartita
	{
		AggiungiCartaSulCampo, // La pos della carta nella classe CollezioneCarte
		Attacco, // La pos nel carteSulCampo[] dell'attaccante e dell'avversario
		Pesca, // Le pos delle carta nella classe CollezioneCarte
		CambiaTurno, // Nessun parametro
		GameOver, // Il giocatore ha perso la partita
		GameWin // Il giocatore ha vinto la partita
	};
}