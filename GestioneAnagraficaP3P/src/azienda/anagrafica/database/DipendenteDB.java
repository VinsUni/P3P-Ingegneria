package azienda.anagrafica.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import azienda.anagrafica.Dipendente;
import azienda.anagrafica.Indirizzo;
import azienda.anagrafica.Telefono;
import azienda.sicurezza.JdbcConnector;

/**
 * @author      Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version     1.0
 */
public class DipendenteDB extends AnagraficaDB{
	private DipendenteDB(){};
	
	/**
	 * Crea il dipendente all'interno della base di dati.                        
	 *      
	 * @param dipendente 
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void crea(Dipendente dipendente) throws SQLException, IOException {
		Connection connessione = JdbcConnector.getConnection();
		String query = dipendente.getQueryCreazione();
		Statement istruzione = connessione.createStatement();
		istruzione.executeUpdate(query);
				
		IndirizzoDB.crea(dipendente.getResidenza());
		IndirizzoDB.crea(dipendente.getDomicilio());
		TelefonoDB.crea(dipendente.getTelefono());

		query = "UPDATE Dipendenti " + "SET " + "Residenza = '" + 
				dipendente.getResidenza().getIdIndirizzo() + "', " + "Domicilio = '"
				+ dipendente.getDomicilio().getIdIndirizzo() + "', " + 
				"ID_Telefono = '"+ dipendente.getTelefono().getIdTelefono() + "' " + 
				"WHERE CodiceFiscale = '" + dipendente.getCodiceFiscale() + "';";

		istruzione.executeUpdate(query);
		connessione.close();
	}
	
	
	/**
	 * Modifica il dipendente nella base di dati il cui identificativo corrisponde
	 * al codice fiscale specificato                        
	 *
	 * @param dipendente 
	 * @param  vecchioId Stringa identificativa dell'anagrafica.   
	 * @param  nuovaResidenza Indirizzo della nuova residenza.  
	 * @param  nuovoDomicilio Indirizzo del nuovo domicilio          
	 * @throws SQLException
	 * @throws IOException
	 * @see getQueryModifica(String vecchioId)
	 */
	public static void modifica(Dipendente dipendente, String vecchioCF, int vecchioIdResidenza, 
			int vecchioIdDomicilio, int vecchioIdTelefono) throws SQLException, IOException {
		Connection connessione = JdbcConnector.getConnection();

		String query = dipendente.getQueryModifica(vecchioCF);
		Statement istruzione = connessione.createStatement();

		istruzione.executeUpdate(query);
		IndirizzoDB.modifica(dipendente.getResidenza(), vecchioIdResidenza);
		IndirizzoDB.modifica(dipendente.getDomicilio(), vecchioIdDomicilio);
		TelefonoDB.modifica(dipendente.getTelefono(), vecchioIdTelefono);

		query = "UPDATE Strumentazioni " + "SET CodiceFiscale = '" + dipendente.getCodiceFiscale() + "' " + "WHERE CodiceFiscale = '"
				+ vecchioCF + "';";

		istruzione.executeUpdate(query);
		connessione.close();
	}
	
	/**
	 * Elimina il dipendente dalla base di dati.                      
	 *
	 * @param dipendente 
	 * @throws SQLException
	 * @throws IOException
	 * @see getQueryElimina()
	 */
	public static void elimina(Dipendente dipendente) throws SQLException, IOException{
		Connection connessione = JdbcConnector.getConnection();
		Statement istruzione = connessione.createStatement();
		istruzione.executeUpdate(dipendente.getQueryElimina());
		connessione.close();
		IndirizzoDB.elimina(dipendente.getResidenza());
		IndirizzoDB.elimina(dipendente.getDomicilio());
		TelefonoDB.elimina(dipendente.getTelefono());
	}
	
	/**
	 * Restituisce la lista di tutti i dipendenti presenti nella base di dati.                         
	 *
	 * @return ArrayList contenente la lista dei dipendenti.
	 * @throws SQLException
	 * @throws IOException
	 * @see ricercaDipendenti()
	 */
	public static List<Dipendente> leggiDipendenti() throws SQLException, IOException{
		return ricercaDipendenti("","","","");
	}
	
	/**
	 * Restituisce la lista dei dipendenti che corrispondono ai criteri di ricerca
	 * specificati.                        
	 *
	 * @param ricercaNome Nome da cercare.
	 * @param ricercaCognome Cognome da cercare.
	 * @param ricercaCodiceFiscale Codice fiscale da cercare.
	 * @param ricercaMansione Mansione da cercare.
	 * @return ArrayList contenente la lista dei dipendenti.
	 * @throws SQLException
	 * @throws IOException
	 */
	public static List<Dipendente> ricercaDipendenti(String ricercaNome, String ricercaCognome, 
			String ricercaCodiceFiscale, String ricercaMansione) throws SQLException, IOException{
		List<Dipendente> dipendenti = new ArrayList<Dipendente>();
		Connection connessione = JdbcConnector.getConnection();

		Indirizzo nuovaResidenza = null;
		Indirizzo nuovoDomicilio = null;
		Telefono nuovoTelefono = null;
		
		Statement istruzione = connessione.createStatement();
		ResultSet risultato = istruzione.executeQuery("SELECT * FROM Dipendenti WHERE Nome LIKE '"+ricercaNome+"%'"
				+ "AND Cognome LIKE '"+ricercaCognome+"%' AND CodiceFiscale LIKE '"+ricercaCodiceFiscale+"%'"
				+ "AND Mansione LIKE '"+ricercaMansione+"%' ;");
			
		while (risultato.next()) {
			String nuovoCodiceFiscale = risultato.getString("CodiceFiscale");
			String nuovoNome = risultato.getString("Nome");
			String nuovoCognome = risultato.getString("Cognome");
			char nuovoSesso = risultato.getString("Sesso").charAt(0);
			String nuovaDataNascita = risultato.getString("DataNascita");
			int nuovoIdResidenza = risultato.getInt("Residenza");
			int nuovoIdDomicilio = risultato.getInt("Domicilio");
			int nuovoIdTelefono = risultato.getInt("ID_Telefono");
			String nuovaMansione = risultato.getString("Mansione");
			String nuovoIndirizzoMail = risultato.getString("IndirizzoMail");
			int nuovoIdSpazio = risultato.getInt("ID_Spazio");
			
			nuovaResidenza = IndirizzoDB.leggiIndirizzo(nuovoIdResidenza);
			nuovoDomicilio = IndirizzoDB.leggiIndirizzo(nuovoIdDomicilio);
			nuovoTelefono = TelefonoDB.leggiTelefono(nuovoIdTelefono);
			
			Dipendente dipendente = new Dipendente(nuovoCodiceFiscale, nuovoNome, nuovoCognome, 
					nuovoSesso, nuovaDataNascita, nuovoIndirizzoMail, nuovaMansione);
			dipendente.setTelefono(nuovoTelefono);
			dipendente.setIdSpazio(nuovoIdSpazio);
			dipendente.setResidenza(nuovaResidenza);
			dipendente.setDomicilio(nuovoDomicilio);
			dipendenti.add(dipendente);
		}
		return dipendenti;
	}
}
