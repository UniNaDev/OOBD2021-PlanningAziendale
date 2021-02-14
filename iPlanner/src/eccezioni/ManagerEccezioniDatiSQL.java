package eccezioni;

public interface ManagerEccezioniDatiSQL {
	public final String VIOLAZIONE_NOT_NULL = "23502";
	public final String VIOLAZIONE_PKEY_UNIQUE = "23505";
	public final String VIOLAZIONE_VINCOLI_TABELLA = "23514";
	public final String VIOLAZIONE_LUNGHEZZA_STRINGA = "22001";
	public final String VIOLAZIONE_DATA_INESISTENTE = "22008";
	
	public final String VIOLAZIONE_ACCAVALLAMENTO_MEETING = "P0001";
	public final String VIOLAZIONE_CAPIENZA_SALA = "P0002";
	public final String VIOLAZIONE_ONNIPRESENZA_DIPENDENTE = "P0003";
	public final String VIOLAZIONE_PROJECT_MANAGER_UNICO = "P0004";
	
	public void mostraErrore();
}
