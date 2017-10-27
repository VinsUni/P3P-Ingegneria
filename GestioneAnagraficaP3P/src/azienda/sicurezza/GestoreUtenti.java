package azienda.sicurezza;

import java.io.IOException;
import java.sql.*;
import java.util.TreeMap;

import org.apache.commons.codec.digest.DigestUtils;

import azienda.eccezioni.AdminException;

/**
 * @author      Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version     1.0
 */
public class GestoreUtenti {
	private Utente utente;
	
	/**
	 * Costruttore usato internamente dalla classe.
	 */
	private GestoreUtenti(Utente utente){
		this.utente = utente;
	}
	
	/**
	 * Crea un oggetto della classe GestoreUtente dopo aver verificato che l'utente ricevuto
	 * come parametro è un amministratore.                          
	 *
	 * @param utenteAdmin Utente con diritti da amministratore.
	 * @return Oggetto di classe GestoreUtenti.
	 * @throws AdminException
	 */
	public static GestoreUtenti nuovoGestoreUtente(Utente utenteAdmin) throws AdminException{
		if(!utenteAdmin.isAdmin()){
			throw new AdminException();
		}
		return new GestoreUtenti(utenteAdmin);
	}
	
	/**
	 * Restituisce lo username dell'utente.                           
	 *
	 * @return username.
	 */
	public String getUsernameGestore(){
		return utente.getUsername();
	}
	
	/**
	 * Legge la lista degli utenti presenti nella base di dati. Restituisce una TreeMap
	 * con campi di tipo String e Boolean. Il primo campo corrisponde allo username dell'utente.
	 * Il secondo campo corrisponde al grado.                        
	 *
	 * @return TreeMap<String, Boolean> contenente username e gradi.
	 * @throws SQLException
	 * @throws IOException
	 */
	public TreeMap<String, Boolean> leggiUtenti() throws SQLException, IOException{
		
		TreeMap<String, Boolean> utenti = new TreeMap<String, Boolean>();
		Connection connessione = null;

		connessione = JdbcConnector.getConnection();

		Statement istruzione = connessione.createStatement();
		ResultSet risultato = istruzione.executeQuery("SELECT Username, Amministratore FROM Utenti;");
			
		while (risultato.next()) {
			String username = risultato.getString("Username");
			Boolean amministratore = risultato.getBoolean("Amministratore");
			utenti.put(username, amministratore);
		}
		connessione.close();
		return utenti;
	}
	
	/**
	 * Crea un'utente non amministratore nella base di dati.                        
	 *
	 * @param username Username del nuovo utente.
	 * @param password Password del nuovo utente.
	 * @throws SQLException
	 * @throws IOException
	 */
	public void creaUtente(String username, String password) throws SQLException, IOException{
		Connection connessione = JdbcConnector.getConnection();
		password = DigestUtils.md5Hex( password );
			
		String query = "INSERT INTO Utenti(Username, Pass, Amministratore) VALUES"
				+ " ('"+username+"', '"+password+"', false);";
		Statement istruzione = connessione.createStatement();
		
		istruzione.executeUpdate(query);
		connessione.close();
	}
	
	/**
	 * Elimina l'utente non amministratore il cui username corrisponde al parametro inserito
	 * dalla base di dati.                        
	 *
	 * @param username Username dell'utente da eliminare.
	 * @return booleano che indica se l'utente è stato eliminato oppure no.
	 * @throws SQLException
	 * @throws IOException
	 */
	public boolean eliminaUtente(String username) throws SQLException, IOException{
		Connection connessione = JdbcConnector.getConnection();
		boolean admin = false;
		boolean result = true;
		String query = "SELECT Amministratore FROM Utenti WHERE Username = '" + username +"';";
					
		Statement istruzione = connessione.createStatement();
		ResultSet risultato = istruzione.executeQuery(query);
		
		while (risultato.next()) {
			admin = risultato.getBoolean("Amministratore");
		}
		
		if(admin==true){
			result = false;
		} else {
			query = "DELETE FROM Utenti WHERE Username = '" + username +"';";
			istruzione.executeUpdate(query);
		}
		connessione.close();
		return result;
	}
	
	/**
	 * Modifica l'utente il cui username corrisponde al parametro vecchioUsername inserito.                   
	 *
	 * @param vecchioUsername Vecchio username dell'utente da modificare.
	 * @param nuovoUsername Nuovo username dell'utente.
	 * @param nuovaPassword Nuova password dell'utente.
	 * @return Utente modificato.
	 * @throws SQLException
	 * @throws IOException
	 */
	public Utente modificaUtente(String vecchioUsername, String nuovoUsername, String nuovaPassword) 
			throws SQLException, IOException{		
		Connection connessione = JdbcConnector.getConnection();
		
		String query = "UPDATE Utenti SET Pass = '"+DigestUtils.md5Hex( nuovaPassword )+"', Username = '"+nuovoUsername+"' WHERE "
				+ "Username = '"+vecchioUsername+"';";
		Statement istruzione = connessione.createStatement();
		istruzione.executeUpdate(query);
		
		if(utente.getUsername().equals(vecchioUsername)){
			utente.modificaPasswordAdmin(nuovaPassword);
		}
		connessione.close();
		return utente;
	}
}
