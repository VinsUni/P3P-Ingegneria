package azienda.anagrafica.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import azienda.anagrafica.Anagrafica;
import azienda.sicurezza.JdbcConnector;

/**
 * @author      Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version     1.0
 */
public abstract class AnagraficaDB {	
	
	/**
	 * Crea l'anagrafica all'interno della base di dati.                        
	 *          
	 * @param anagrafica         
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void crea(Anagrafica anagrafica) throws SQLException, IOException{
		Connection connessione = JdbcConnector.getConnection();	
		String query = anagrafica.getQueryCreazione();	
		Statement istruzione = connessione.createStatement();		
		istruzione.executeUpdate(query);	
		connessione.close();
	}
	
	/**
	 * Modifica l'anagrafica nella base di dati il cui identificativo corrisponde
	 * all'ID specificato.                        
	 *
	 * @param anagrafica  
	 * @param  vecchioId Numero identificativo dell'anagrafica.          
	 * @throws SQLException
	 * @throws IOException
	 * @see modifica(String vecchioId)
	 */
	public static void modifica(Anagrafica anagrafica, int vecchioId) throws SQLException, IOException{
		modifica(anagrafica, String.valueOf(vecchioId));
	}
	
	/**
	 * Modifica l'anagrafica nella base di dati il cui identificativo corrisponde
	 * all'ID specificato.                        
	 * 
	 * @param anagrafica  
	 * @param  vecchioId Stringa identificativa dell'anagrafica.          
	 * @throws SQLException
	 * @throws IOException
	 * @see getQueryModifica(String vecchioId)
	 */
	public static void modifica(Anagrafica anagrafica, String vecchioId) throws SQLException, IOException{
		Connection connessione = JdbcConnector.getConnection();		
		String query = anagrafica.getQueryModifica(vecchioId);
		Statement istruzione = connessione.createStatement();	
		istruzione.executeUpdate(query);
		connessione.close();
	}
	
	/**
	 * Elimina l'anagrafica dalla base di dati.                      
	 * @param anagrafica  
	 * @throws SQLException
	 * @throws IOException
	 * @see getQueryElimina()
	 */
	public static void elimina(Anagrafica anagrafica) throws SQLException, IOException{
		Connection connessione = JdbcConnector.getConnection();
		Statement istruzione = connessione.createStatement();
		istruzione.executeUpdate(anagrafica.getQueryElimina());
		connessione.close();
	}
	
}
