package azienda.sicurezza;
import java.io.IOException;
import java.sql.*;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author      Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version     1.0
 */
public class Utente {
	private String username;
	private String password;
	private boolean grado;
	/**
	 * Lunghezza massima dello username.                     
	 */
	public static final int MAX_USER_LENGTH = 20;
	/**
	 * Lunghezza massima della password.                     
	 */
	public static final int MAX_PASS_LENGTH = 20;
	/**
	 * Lunghezza minima della password.                     
	 */
	public static final int MIN_PASS_LENGTH = 6;
	
	/**
	 * Costruttore usato internamente nella classe
	 * 
	 * @param username
	 * @param password
	 * @param amministratore
	 */
	private Utente(String username, String password, boolean amministratore){
		this.username = username;
		this.password = password;
		this.grado = amministratore;
	}
	
	/**
	 * Restituisce lo username.                           
	 *
	 * @return username.
	 */
	public String getUsername(){
		return username;
	}
	
	/**
	 * Verifica se l'utente è amministratore.                           
	 *
	 * @return grado.
	 */
	public boolean isAdmin(){
		return grado;
	}

	/**
	 * Verifica se la password inserita corrisponde alla password attuale dell'utente.                           
	 *
	 * @param inputPassword Password da verificare.
	 * @return validità della password.
	 */
	public boolean verificaPassword(String inputPassword){
		boolean verifica = false;
		inputPassword = DigestUtils.md5Hex( inputPassword );
		if(password.equals(inputPassword)){
			verifica = true;
		}
		return verifica;
	}
	
	/**
	 * Modifica la password attuale con una nuova password.                           
	 *
	 * @param vecchiaPassword Vecchia password dell'utente.
	 * @param nuovaPassword Nuova password.
	 * @throws SQLException
	 * @throws IOException
	 */
	public void modificaPassword(String vecchiaPassword, String nuovaPassword) throws SQLException, IOException{
		vecchiaPassword = DigestUtils.md5Hex( vecchiaPassword );
		
		if(password.equals(vecchiaPassword)){
			modificaPasswordAdmin(nuovaPassword);
		}
	}
	
	/**
	 * Modifica la password attuale con una nuova password.                           
	 *
	 * @param nuovaPassword Nuova password.
	 * @throws SQLException
	 * @throws IOException
	 */
	protected void modificaPasswordAdmin(String nuovaPassword) throws SQLException, IOException{
		Connection connessione = JdbcConnector.getConnection();
		nuovaPassword = DigestUtils.md5Hex( nuovaPassword );
		String query = "UPDATE Utenti SET Pass = '"+nuovaPassword+"' WHERE "
				+ "Username = '"+username+"';";
		Statement istruzione = connessione.createStatement();
		istruzione.executeUpdate(query);
		this.password = nuovaPassword;
		connessione.close();
	}
		
	/**
	 * Verifica se lo username e la password inseriti appartengono ad un utente registrato
	 * nel sistema. Se i dati inseriti sono validi, restituisce un oggetto di classe Utente
	 * necessario per effettuare il login.                          
	 *
	 * @param usernameAttuale Username inserito dall'utente.
	 * @param usernameAttuale Username inserito dall'utente.
	 * @return Utente.
	 * @throws SQLException
	 * @throws IOException
	 */
	public static Utente verificaUtente(String usernameAttuale, String passwordAttuale) throws SQLException, IOException{
		Connection connessione = JdbcConnector.getConnection();
		passwordAttuale = DigestUtils.md5Hex( passwordAttuale );
		
		String query = "SELECT * FROM Utenti;";
		Statement istruzione = connessione.createStatement();
		ResultSet risultato = istruzione.executeQuery(query);
		
		boolean trovato = false;
		Utente utente = null;
		String usernameDB = "";
		String passwordDB = "";
		boolean gradoDB = false;
		
		while (trovato == false) {
			risultato.next();
			usernameDB = risultato.getString("Username");
			passwordDB = risultato.getString("Pass");
			gradoDB = risultato.getBoolean("Amministratore");
			if(usernameDB.equals(usernameAttuale)){
				if(passwordDB.equals(passwordAttuale)){
					trovato = true;
				}
			}	
		}
		connessione.close();
		if(trovato==true){
			 utente = new Utente(usernameAttuale, passwordAttuale, gradoDB);
		}
		return utente;
	}
}
