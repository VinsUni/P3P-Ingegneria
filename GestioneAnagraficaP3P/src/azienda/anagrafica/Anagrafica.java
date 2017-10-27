package azienda.anagrafica;

/**
 * @author      Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version     1.0
 */
public interface Anagrafica {
	/**
	 * Lunghezza massima dei campi.                     
	 */
	public static final int MAX_LENGHT = 30;
	
	/**
	 * Restituisce la query necessaria a creare l'anagrafica nella base di dati.                           
	 *
	 * @return Stringa contenente la query per la creazione dell'anagrafica.
	 * @see crea()
	 */
	public String getQueryCreazione();
	
	/**
	 * Restituisce la query necessaria a modificare l'anagrafica nella base di dati.                           
	 *
	 * @param  vecchioId Stringa identificativa dell'anagrafica.     
	 * @return Stringa contenente la query per la modifica dell'anagrafica.
	 * @see modifica(String vecchioId)
	 */
	public String getQueryModifica(String vecchioId);	
	
	/**
	 * Restituisce la query necessaria a eliminare l'anagrafica dalla base di dati.                           
	 *
	 * @param  vecchioId Stringa identificativa dell'anagrafica.     
	 * @return Stringa contenente la query per l'eliminazione dell'anagrafica.
	 * @see elimina()
	 */
	public String getQueryElimina();
	
	/**
	 * Restituisce la stringa formattata contenente i campi dell'anagrafica.                           
	 *
	 * @return Stringa descrittiva dell'anagrafica.
	 */
	public String toString();
}
