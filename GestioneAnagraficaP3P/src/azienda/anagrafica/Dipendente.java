package azienda.anagrafica;


import azienda.utility.Utility;

/**
 * @author      Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version     1.0
 */
public class Dipendente extends Persona {
	private String mansione;
	private int idSpazio;
	
	/**
	 * Costruttore del dipendente.                      
	 *
	 * @param codiceFiscale Codice fiscale del dipendente.
	 * @param nome Nome del dipendente.
	 * @param cognome Cognome del dipendente. 
	 * @param sesso Sesso del dipendente.
	 * @param dataNascita Data di nascita del dipendente.
	 * @param mansione Mansione del dipendente.
	 * @param indirizzoMail Indirizzo E-Mail del dipendente.
	 */
	public Dipendente(String codiceFiscale, String nome, String cognome, char sesso, String dataNascita,
			String indirizzoMail, String mansione) {
		super(codiceFiscale, nome, cognome, sesso, dataNascita, indirizzoMail);

		this.mansione = mansione;
	}

	/**
	 * Restituisce la mansione.                           
	 *
	 * @return mansione.
	 */
	public String getMansione() {
		return mansione;
	}

	/**
	 * Restituisce l'id dello spazio a cui il dipendente è assegnato.                           
	 *
	 * @return idSpazio.
	 */
	public int getIdSpazio() {
		return idSpazio;
	}
	
	public void setIdSpazio(int idSpazio) {
		this.idSpazio = idSpazio;
	}

	/**
	 * Restituisce la stringa composta da codice fiscale, cognome e nome del dipendente.                         
	 *
	 * @return Stringa identificativa del dipendente.
	 */
	public String getIdentificativoDipendente() {
		String stringa = "CF: " + codiceFiscale + " - " + cognome + " " + nome;
		return stringa;
	}

	/**
	 * Restituisce la stringa formattata contenente i campi del dipendente.                           
	 *
	 * @return Stringa descrittiva del dipendente.
	 */
	@Override
	public String toString() {
		String stringa = "     • " + cognome + " " + nome + "\n" + "          - Data di nascita: "
				+ Utility.sqlToStringFormat(dataNascita) + "\n" + "          - Sesso: " + sesso + "\n"
				+ "          - Codice fiscale: " + codiceFiscale + "\n" + "          - Residenza: "
				+ residenza.toString() + "\n" + "          - Domicilio: " + domicilio.toString() + "\n"
				+ "          - Mansione: " + mansione + "\n" + "          - Numero di cellulare: " + telefono.getNumeroCellulare()
				+ "\n" + "          - Numero di casa: " + telefono.getNumeroCasa() + "\n" + "          - E-Mail: " + indirizzoMail;
	
		return stringa;
	}

	/**
	 * Restituisce la query necessaria a creare il dipendente nella base di dati.                           
	 *
	 * @return Stringa contenente la query per la creazione del dipendente
	 * @see crea()
	 */
	@Override
	public String getQueryCreazione() {
		String query;
		
		if (idSpazio < 0) {
			query = "INSERT INTO Dipendenti VALUES " + " ('" 
					+ codiceFiscale + "', '" + nome + "', '" 
					+ cognome + "', '" + sesso + "', '" + dataNascita 
					+ "'" + ", '" + indirizzoMail + "', '" 
					+ mansione + "', null, null, null, null);";
		} else {
			query = "INSERT INTO Dipendenti VALUES " + " ('" 
					+ codiceFiscale + "', '" + nome + "', '" + cognome
					+ "', '" + sesso + "', '" + dataNascita + "'" 
					+ ", '" + indirizzoMail + "',  '"
					+ mansione + "', null, null, null, '" + idSpazio + "');";		
		}
				
		return query;
	}

	/**
	 * Restituisce la query necessaria a modificare il dipendente nella base di dati.                           
	 *
	 * @param  vecchioId Codice fiscale identificativo del dipendente.
	 * @return Stringa contenente la query per la modifica del dipendente.
	 * @see modifica(String vecchioId)
	 */
	@Override
	public String getQueryModifica(String vecchioId) {
		String query = "UPDATE Dipendenti " + "SET CodiceFiscale = '" + codiceFiscale + "'," + "Nome = '" + nome + "',"
				+ "Cognome = '" + cognome + "'," + "Sesso = '" + sesso + "'," + "DataNascita = '" + dataNascita + "',"
				+ "IndirizzoMail = '" + indirizzoMail + "'," + "Mansione = '" + mansione + "'," 
				+ " ID_Spazio = '" + idSpazio + "' "
				+ "WHERE CodiceFiscale = '" + vecchioId + "';";
		return query;
	}

	/**
	 * Restituisce la query necessaria a eliminare il dipendente dalla base di dati.                           
	 *
	 * @param  vecchioId Stringa identificativa del dipendente.    
	 * @return Stringa contenente la query per l'eliminazione del dipendente.
	 * @throws IOException 
	 * @throws SQLException 
	 * @see elimina()
	 */
	@Override
	public String getQueryElimina()  {
		String query = "DELETE FROM Dipendenti WHERE CodiceFiscale = '" + codiceFiscale +"';";
		return query;
	}
}
