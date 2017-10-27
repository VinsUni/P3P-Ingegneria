package azienda.anagrafica;
import java.io.IOException;
import java.sql.*;
import azienda.sicurezza.JdbcConnector;

/**
 * @author      Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version     1.0
 */
public class Indirizzo implements Anagrafica{
	private int idIndirizzo;
	private String indirizzo;
	private int numeroCivico;
	private int idComune;
	private String comune;
	private String provincia;
	private String regione;
	/**
	 * Lunghezza massima dell'indirizzo.                   
	 */
	public static final int INDIRIZZO_MAX_LENGHT = 30;

	/**
	 * Costruttore pubblico dell'indirizzo.                      
	 *
	 * @param indirizzo Indirizzo dell'abitazione.
	 * @param numeroCivico Numero civico dell'abitazione
	 * @param comune Comune dell'abitazione.
	 * @param provincia Provincia dell'abitazione.
	 * @param regione Regione dell'abitazione.
	 * @throws SQLException
	 * @throws IOException
	 */
	public Indirizzo(String indirizzo, int numeroCivico, String comune, String provincia, 
			String regione) throws SQLException, IOException {
		this(0, indirizzo, numeroCivico, 0, comune, provincia, regione);
		this.idComune = getIdComuneDB();	
	}

	/**
	 * Costruttore privato dell'indirizzo per la lettura dalla base di dati.                      
	 *
	 * @param idIndirizzo ID dell'indirizzo nella base di dati.
	 * @param indirizzo Indirizzo dell'abitazione.
	 * @param numeroCivico Numero civico dell'abitazione
	 * @param comune Comune dell'abitazione.
	 * @param provincia Provincia dell'abitazione.
	 * @param regione Regione dell'abitazione.
	 */
	public Indirizzo(int idIndirizzo, String indirizzo, int numeroCivico, int idComune, String comune, String provincia,
			String regione) {
		this.idIndirizzo = idIndirizzo;
		this.indirizzo = indirizzo;
		this.numeroCivico = numeroCivico;
		this.idComune = idComune;
		this.comune = comune;
		this.provincia = provincia;
		this.regione = regione;
	}

	/**
	 * Restituisce il numero identificativo.                         
	 *
	 * @return idIndirizzo.
	 */
	public int getIdIndirizzo() {
		return idIndirizzo;
	}
	
	/**
	 * Assegna il numero identificativo.                         
	 *
	 */
	public void setIdIndirizzo(int idIndirizzo) {
		this.idIndirizzo = idIndirizzo;
	}
	
	/**
	 * Restituisce il numero civico.                         
	 *
	 * @return numeroCivico.
	 */
	public int getNumeroCivico() {
		return numeroCivico;
	}

	/**
	 * Restituisce la provincia.                         
	 *
	 * @return provincia.
	 */
	public String getProvincia() {
		return provincia;
	}

	/**
	 * Restituisce la regione.                         
	 *
	 * @return regione.
	 */
	public String getRegione() {
		return regione;
	}
	
	/**
	 * Restituisce il comune.                         
	 *
	 * @return comune.
	 */
	public String getComune() {
		return comune;
	}

	/**
	 * Restituisce l'indirizzo.                         
	 *
	 * @return indirizzo.
	 */
	public String getIndirizzo(){
		return indirizzo;
	}

	/**
	 * Restituisce il numero identificativo del comune.                         
	 *
	 * @return idComune.
	 */
	public int getIdComune() {
		return idComune;
	}
	
	/**
	 * Restituisce la stringa composta da indirizzo e numero civico.                         
	 *
	 * @return Stringa identificativa della strumentazione.
	 */
	public String getIndirizzoCompleto(){
		return indirizzo + ", " + numeroCivico;
	}
	
	/**
	 * Restituisce la stringa composta da comune, provincia e regione.                         
	 *
	 * @return Stringa identificativa della strumentazione.
	 */
	public String getComuneCompleto(){
		return comune + "("+provincia+"), " + regione;
	}
	
	/**
	 * Restituisce la stringa formattata contenente i campi dell'indirizzo.                           
	 *
	 * @return Stringa descrittiva dell'indirizzo.
	 */
	@Override
	public String toString() {
		return indirizzo + " " + numeroCivico + ", "+comune + "("+provincia+"), " + regione;
	}
	
	/**
	 * Legge l'ID del comune dalla base di dati                         
	 *
	 * @return Numero identificativo del comune.
	 * @throws SQLException
	 * @throws IOException
	 */
	private int getIdComuneDB() throws SQLException, IOException{
		int idComuneDB = 0;
		Connection connessione = JdbcConnector.getConnection();

		Statement istruzione = connessione.createStatement();
		ResultSet risultato = istruzione.executeQuery("SELECT ID_Comune FROM Comuni "
				+ "WHERE Comune = '"+comune+"' AND Regione = '"+regione+"' "
				+ "AND Provincia = '"+provincia+"';");
			
		while (risultato.next()) {
			idComuneDB = risultato.getInt("ID_Comune");
		}
		connessione.close();
		return idComuneDB;
	}
	
	/**
	 * Copia l'indirizzo attuale in un nuovo oggetto Indirizzo.                   
	 *
	 * @return Indirizzo
	 * @throws SQLException
	 * @throws IOException
	 */
	public Indirizzo copiaIndirizzo() throws SQLException, IOException{
		return new Indirizzo(indirizzo, numeroCivico, comune, provincia, regione);
	}

	@Override
	public String getQueryCreazione() {
		String query = "INSERT INTO Indirizzi (ID_Indirizzo, Indirizzo, NumeroCivico, ID_Comune) VALUES "
				+ " ('"+idIndirizzo+"', '"+indirizzo+"', '"+numeroCivico+"', '"+idComune+"');";
		
		return query;
	}

	@Override
	public String getQueryModifica(String vecchioId) {
		String query = "UPDATE Indirizzi "
				+"SET "
				+"Indirizzo = '"+indirizzo+"', "
				+"NumeroCivico = '"+numeroCivico+"', "
				+"ID_Comune = '"+idComune+"' "
				+"WHERE ID_Indirizzo = '"+vecchioId+"';";
		return query;
	}

	@Override
	public String getQueryElimina() {
		String query = "DELETE FROM Indirizzi WHERE ID_Indirizzo = '" + getIdIndirizzo() +"';";
		return query;
	}
	
}
