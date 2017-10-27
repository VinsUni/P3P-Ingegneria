package azienda.anagrafica.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import azienda.anagrafica.Spazio;
import azienda.sicurezza.JdbcConnector;
/**
 * @author      Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version     1.0
 */
public class SpazioDB extends AnagraficaDB{
	
	private SpazioDB(){};
	
	/**
	 * Restituisce la lista di tutti gli spazi presenti nella base di dati.                         
	 *
	 * @return ArrayList contenente la lista degli spazi.
	 * @throws SQLException
	 * @throws IOException
	 * @see ricercaSpazi()
	 */
	public static List<Spazio> leggiSpazi() throws SQLException, IOException{
		return ricercaSpazi("", "", "");
	}
	
	/**
	 * Restituisce la lista degli spazi che corrispondono ai criteri di ricerca
	 * specificati.                        
	 *
	 * @param ricercaId Identificativo da cercare.
	 * @param ricercaNome Nome da cercare.
	 * @param ricercabicazione Ubicazione da cercare.
	 * @return ArrayList contenente la lista degli spazi.
	 * @throws SQLException
	 * @throws IOException
	 */
	public static List<Spazio> ricercaSpazi(String ricercaId, String ricercaNome, String ricercabicazione) throws SQLException, IOException{
		List<Spazio> spazi = new ArrayList<Spazio>();
		Connection connessione = JdbcConnector.getConnection();

		Statement istruzione = connessione.createStatement();
		ResultSet risultato = istruzione.executeQuery("SELECT * FROM Spazi WHERE "
				+ "ID_Spazio LIKE '"+ricercaId+"%' AND Nome LIKE '"+ricercaNome+"%' "
				+ "AND Ubicazione LIKE '"+ricercabicazione+"%' ;");	
		
		while (risultato.next()) {
			int nuovoIdSpazio = risultato.getInt("ID_Spazio");
			String nuovoNome = risultato.getString("Nome");
			String nuovoUbicazione = risultato.getString("Ubicazione");
			int nuovoNroFinestre = risultato.getInt("NroFinestre");
			int nuovoNroPorte = risultato.getInt("NroPorte");
			double nuovaSuperficie = risultato.getDouble("Superficie");
			Spazio spazio = new Spazio(nuovoNome, nuovoUbicazione, nuovoNroFinestre, nuovoNroPorte, nuovaSuperficie);
			spazio.setIdSpazio(nuovoIdSpazio);
			spazi.add(spazio);
		}

		return spazi;
	}
	
}
