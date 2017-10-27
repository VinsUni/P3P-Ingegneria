package azienda.anagrafica;


/**
 * @author      Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version     1.0
 */
public class Spazio implements Anagrafica{
	private int idSpazio;
	private String nome;
	private String ubicazione;
	private int nroFinestre;
	private int nroPorte;
	private double superficie;

	
	/**
	 * Costruttore dello spazio.                      
	 *
	 * @param nome Nome dello spazio.
	 * @param ubicazione Ubicazione lo spazio. 
	 * @param nroFinestre Numero di finestre dello spazio.
	 * @param nroPorte Numero di porte dello spazio.
	 * @param superficie Superficie dello spazio.
	 */
	public Spazio(String nome, String ubicazione, int nroFinestre, int nroPorte, double superficie) {
		super();
		this.nome = nome;
		this.ubicazione = ubicazione;
		this.nroFinestre = nroFinestre;
		this.nroPorte = nroPorte;
		this.superficie = superficie;
	}

	/**
	 * Restituisce il nome.                         
	 *
	 * @return nome.
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Restituisce l'ubicazione.                         
	 *
	 * @return ubicazione.
	 */
	public String getUbicazione() {
		return ubicazione;
	}

	/**
	 * Restituisce il numero di finestre.                           
	 *
	 * @return nroFinestre.
	 */
	public int getNroFinestre() {
		return nroFinestre;
	}

	/**
	 * Restituisce il numero di porte.                           
	 *
	 * @return nroPorte.
	 */
	public int getNroPorte() {
		return nroPorte;
	}

	/**
	 * Restituisce la superficie.                           
	 *
	 * @return superficie.
	 */
	public double getSuperficie() {
		return superficie;
	}
	
	/**
	 * Restituisce l'identificativo dello spazio.                          
	 *
	 * @return idSpazio.
	 */
	public int getIdSpazio() {
		return idSpazio;
	}
	
	/**
	 * Assegna il numero identificativo dello spazio.                         
	 *
	 * @param idSpazio Numero identificativo.
	 */
	public void setIdSpazio(int idSpazio){
		this.idSpazio = idSpazio;
	}
	
	/**
	 * Restituisce la stringa composta da numero identificativo e nome.                         
	 *
	 * @return Stringa identificativa dello spazio.
	 */
	public String getIdentificativoSpazio(){
		String stringa = "ID: " + idSpazio + " - " + nome;
		return stringa;
	}
	
	/**
	 * Restituisce la stringa formattata contenente i campi dello spazio.                           
	 *
	 * @return Stringa descrittiva dello spazio.
	 */
	@Override
	public String toString() {
		String stringa = "     • ID: "+idSpazio + " - " + nome + "\n" 
				+ "          - Ubicazione: " + ubicazione + "\n"
				+ "          - Numero di porte: " + nroPorte + "\n" 
				+ "          - Numero di finestre: " + nroFinestre + "\n" 
				+ "          - Superficie: " + superficie;
		return stringa;
	}

	/**
	 * Restituisce la query necessaria a creare lo spazio nella base di dati.                           
	 *
	 * @return Stringa contenente la query per la creazione dello spazio
	 * @see crea()
	 */
	@Override
	public String getQueryCreazione() {
		String query = "INSERT INTO Spazi (Nome, Ubicazione, NroPorte, NroFinestre, Superficie) VALUES "
				+ " ('"+nome+"', '"+ubicazione+"', '"+nroPorte+"', '"+nroFinestre+"', '"+superficie+"');";
	
		return query;
	}

	/**
	 * Restituisce la query necessaria a modificare lo spazio nella base di dati.                           
	 *
	 * @param  vecchioId Numero identificativo dello spazio.
	 * @return Stringa contenente la query per la modifica dello spazio.
	 * @see modifica(int vecchioId)
	 */
	@Override
	public String getQueryModifica(String vecchioId) {
		String query = "UPDATE Spazi "
				+"SET "
				+"Nome = '"+nome+"',"
				+"Ubicazione = '"+ubicazione+"',"
				+"NroPorte = '"+nroPorte+"',"
				+"NroFinestre = '"+nroFinestre+"',"
				+"Superficie = '"+superficie+"' "
				+"WHERE ID_Spazio = '"+vecchioId+"';";
		return query;
	}

	/**
	 * Restituisce la query necessaria a eliminare lo spazio dalla base di dati.                           
	 *
	 * @return Stringa contenente la query per l'eliminazione dello spazio.
	 * @see elimina()
	 */
	@Override
	public String getQueryElimina() {
		String query = "DELETE FROM Spazi WHERE ID_Spazio = '" + idSpazio +"';";
		return query;
	}

}
