package azienda.anagrafica.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import azienda.anagrafica.Strumentazione;
import azienda.sicurezza.JdbcConnector;
/**
 * @author      Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version     1.0
 */
public class StrumentazioneDB extends AnagraficaDB{
	
	private StrumentazioneDB(){};
	
	/**
	 * Restituisce la lista di tutte le strumentazioni presenti nella base di dati.                         
	 *
	 * @return ArrayList contenente la lista dele strmentazioni.
	 * @throws SQLException
	 * @throws IOException
	 * @see ricercaSpazi()
	 */
	public static List<Strumentazione> leggiStrumentazioni() throws SQLException, IOException{
		return ricercaStrumentazioni("", "", "", "", "");
	}
	
	/**
	 * Restituisce la lista delle strumentazioni che corrispondono ai criteri di ricerca
	 * specificati.                        
	 *
	 * @param ricercaId Identificativo da cercare.
	 * @param ricercaNome Nome da cercare.
	 * @param ricercaModello Modello da cercare.
	 * @param ricercaMarca Marca da cercare.
	 * @param ricercaTipologia Tipologia da cercare.
	 * @return ArrayList contenente la lista degli spazi.
	 * @throws SQLException
	 * @throws IOException
	 */
	public static List<Strumentazione> ricercaStrumentazioni(String ricercaId, String ricercaNome, 
			String ricercaModello, String ricercaMarca, String ricercaTipologia) throws SQLException, IOException{
		List<Strumentazione> strumentazioni = new ArrayList<Strumentazione>();
		Connection connessione = JdbcConnector.getConnection();
		Statement istruzione = connessione.createStatement();
		ResultSet risultato = istruzione.executeQuery("SELECT * FROM Strumentazioni WHERE "
				+ "ID_Strumentazione LIKE '"+ricercaId+"%' AND "
				+ "Nome LIKE '"+ricercaNome+"%' AND Modello LIKE '"+ricercaModello+"%' AND "
				+ "Marca LIKE '"+ricercaMarca+"%' AND Tipologia LIKE '"+ricercaTipologia+"%';");
			
		while (risultato.next()) {
			int nuovoIdStrumentazione = risultato.getInt("ID_Strumentazione");
			String nuovoNome = risultato.getString("Nome");
			String nuovoModello = risultato.getString("Modello");
			String nuovaMarca = risultato.getString("Marca");
			String nuovaTipologia = risultato.getString("Tipologia");
			String nuovaDataAcquisto = risultato.getString("DataAcquisto");
			String nuovoCodiceFiscale = risultato.getString("CodiceFiscale");
			String id = risultato.getString("ID_Spazio");
			int nuovoIdSpazio;
			if(id==null){
				nuovoIdSpazio = -1;
			}
			else {
				nuovoIdSpazio = risultato.getInt("ID_Spazio");
			}
			Strumentazione strumentazione = new Strumentazione(nuovoNome, nuovoModello, nuovaMarca, nuovaTipologia, nuovaDataAcquisto, nuovoCodiceFiscale, nuovoIdSpazio);
			strumentazione.setIdStrumentazione(nuovoIdStrumentazione);
			strumentazioni.add(strumentazione);
		}

		return strumentazioni;
	}
	
	/**
	 * Restituisce un vettore di stringhe conenente come primo elemento il dipendente a cui
	 * la strumentazione è assegnata e come secondo elemento lo spazio a cui la strumentazione
	 * è assegnata.                          
	 *
	 * @param strumentazione
	 * @return Vettore di stringhe.
	 * @throws SQLException
	 * @throws IOException
	 */
	public static String[] getUsoStrumentazione(Strumentazione strumentazione) throws SQLException, IOException {
		String[] usoStrumentazione = new String[2];
		Connection connessione = JdbcConnector.getConnection();
		Statement istruzione = connessione.createStatement();
		ResultSet risultato = null;
		
		if(strumentazione.getCodiceFiscale()==null){
			usoStrumentazione[0] = "Nessuno";
		}
		else {
			risultato = istruzione.executeQuery("SELECT Nome, Cognome FROM Dipendenti "
					+ "WHERE CodiceFiscale = '"+strumentazione.getCodiceFiscale()+"';");
			
			while(risultato.next()){
				String nomeDip = risultato.getString("Nome");
				String cognomeDip = risultato.getString("Cognome");
				usoStrumentazione[0] = "CF: " + strumentazione.getCodiceFiscale() + " - " + cognomeDip + " " + nomeDip;
			}
		}
		
		
		if(strumentazione.getIdSpazio()<0){
			usoStrumentazione[1] = "Nessuno";	
		}
		else {
			risultato = istruzione.executeQuery("SELECT Nome FROM Spazi "
				+ "WHERE ID_Spazio = '"+strumentazione.getIdSpazio()+"';");
			while(risultato.next()){
				String nomeSpazio = risultato.getString("nome");
				usoStrumentazione[1] = "ID: " + strumentazione.getIdSpazio() + " - " + nomeSpazio;
			}
		}
		
		connessione.close();
		return usoStrumentazione;
	}
	
}
