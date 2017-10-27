package azienda.anagrafica.database;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import azienda.anagrafica.Indirizzo;
import azienda.sicurezza.JdbcConnector;

/**
 * @author      Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version     1.0
 */
public class IndirizzoDB extends AnagraficaDB{
	
	/**
	 * Non usato
	 */
	private IndirizzoDB(){	}
	
	/**
	 * Crea l'indirizzo all'interno della base di dati.                        
	 * @param indirizzo         
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void crea(Indirizzo indirizzo) throws SQLException, IOException{
		int i = 0;	
		Connection connessione = JdbcConnector.getConnection();
		Statement istruzione = connessione.createStatement();

		ResultSet risultato = istruzione.executeQuery("SELECT ID_Indirizzo FROM Indirizzi");
		while(risultato.next()) {
			i = risultato.getInt("ID_Indirizzo");	
		}
		i++;	
		indirizzo.setIdIndirizzo(i);
	
		istruzione.executeUpdate(indirizzo.getQueryCreazione());
		
		connessione.close();
	}
	
	/**
	 * Restituisce una lista.                 
	 *
	 * @param query Query
	 * @param campo Campo da cercare.
	 * @return ArrayList contenente la lista delle regioni.
	 * @throws SQLException, IOException.
	 */
	private static List<String> leggiLista(String query, String campo)  throws SQLException, IOException{
		List<String> lista = new ArrayList<String>();
		Connection connessione = JdbcConnector.getConnection();
		Statement istruzione = connessione.createStatement();
		ResultSet risultato = istruzione.executeQuery(query);
		while (risultato.next()) {
			String elemento = risultato.getString(campo);
			lista.add(elemento);
		}
		connessione.close();
		return lista;	
	}
	
	/**
	 * Restituisce la lista di tutte le regioni presenti nella base di dati.                         
	 *
	 * @return ArrayList contenente la lista delle regioni.
	 * @throws SQLException, IOException.
	 */
	public static List<String> leggiRegioni() throws SQLException, IOException{
		String query = "SELECT DISTINCT Regione FROM Comuni ORDER BY Regione;";
		String campo = "Regione";
		return leggiLista(query, campo);	
	}
	
	/**
	 * Restituisce la lista di tutte le province di una specifica regione presenti 
	 * nella base di dati.                         
	 *
	 * @param regione Regione di appartenenza delle province.
	 * @return ArrayList contenente la lista delle province.
	 * @throws SQLException
	 * @throws IOException
	 */
	public static List<String> leggiProvince(String regione) throws SQLException, IOException{
		String query = "SELECT DISTINCT Provincia FROM Comuni "
				+ "WHERE Regione = '"+regione+"' ORDER BY Provincia;";
		String campo = "Provincia";
		return leggiLista(query, campo);	
	}
	
	/**
	 * Restituisce la lista di tutti i comuni di una specifica provincia presenti 
	 * nella base di dati.                         
	 *
	 * @param regione Regione di appartenenza dei comuni.
	 * @param provincia Provincia di appartenenza dei comuni.
	 * @return ArrayList contenente la lista dei comuni.
	 * @throws SQLException
	 * @throws IOException
	 */
	public static List<String> leggiComuni(String regione, String provincia) throws SQLException, IOException{
		String query = "SELECT DISTINCT Comune FROM Comuni "
				+ "WHERE Regione = '"+regione+"' AND Provincia = '"+provincia+"' ORDER BY Comune;";
		String campo = "Comune";
		return leggiLista(query, campo);	
	}
	
	/**
	 * Legge uno specifico indirizzo dalla base di dati che corrisponde all'identificativo
	 * specificato.                      
	 *
	 * @param ricercaIdIndirizzo Identificativo da cercare.
	 * @return Indirizzo.
	 * @throws SQLException
	 * @throws IOException
	 */
	public static Indirizzo leggiIndirizzo(int ricercaIdIndirizzo) throws SQLException, IOException{
		Connection connessione = JdbcConnector.getConnection();
		Indirizzo nuovoIndirizzo = null;

		Statement istruzione = connessione.createStatement();
		ResultSet risultato = istruzione.executeQuery("SELECT * FROM Indirizzi INNER JOIN Comuni ON Indirizzi.ID_Comune = Comuni.ID_Comune " 						
				+"WHERE ID_Indirizzo = "+ricercaIdIndirizzo+" ;");
		while (risultato.next()){
			String indirizzoDB = risultato.getString("Indirizzo");
			int numeroCivicoDB = risultato.getInt("NumeroCivico");
			String comuneDB = risultato.getString("Comune");
			String regioneDB = risultato.getString("Regione");
			String provinciaDB = risultato.getString("Provincia");
			int idComuneDB = risultato.getInt("ID_Comune");
			nuovoIndirizzo = new Indirizzo(ricercaIdIndirizzo, indirizzoDB, numeroCivicoDB, idComuneDB, comuneDB, provinciaDB, regioneDB);
		}	
		connessione.close();
		return nuovoIndirizzo;
	}
}
