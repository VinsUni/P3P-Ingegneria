package azienda.anagrafica.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import azienda.anagrafica.Telefono;
import azienda.sicurezza.JdbcConnector;
/**
 * @author      Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version     1.0
 */
public class TelefonoDB extends AnagraficaDB{
	private TelefonoDB(){};
	/**
	 * Crea i numeri di telefono all'interno della base di dati.                        
	 *          
	 * @param telefono         
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void crea(Telefono telefono) throws SQLException, IOException{
		int i = 0;	
		Connection connessione = JdbcConnector.getConnection();
		Statement istruzione = connessione.createStatement();

		ResultSet risultato = istruzione.executeQuery("SELECT ID_Telefono FROM Telefoni");
		while(risultato.next()) {
			i = risultato.getInt("ID_Telefono");	
		}
		i++;		
		telefono.setIdTelefono(i);
		istruzione.executeUpdate(telefono.getQueryCreazione());
		connessione.close();
	}
	
	/**
	 * Legge uno specifico numero di telefono dalla base di dati che corrisponde all'identificativo
	 * specificato.                      
	 *
	 * @param ricercaIdTelefono Identificativo da cercare.
	 * @return Telefono.
	 * @throws SQLException
	 * @throws IOException
	 */
	public static Telefono leggiTelefono(int ricercaIdTelefono) throws SQLException, IOException{
		Connection connessione = JdbcConnector.getConnection();
		Telefono nuovoTelefono = null;

		Statement istruzione = connessione.createStatement();
		ResultSet risultato = istruzione.executeQuery("SELECT * FROM Telefoni " 						
				+"WHERE ID_Telefono = "+ricercaIdTelefono+" ;");
		while (risultato.next()){
			String nuovoNumeroCellulare = risultato.getString("NumeroCellulare");
			String nuovoNumeroCasa = risultato.getString("NumeroCasa");
			nuovoTelefono = new Telefono(nuovoNumeroCellulare, nuovoNumeroCasa);
			nuovoTelefono.setIdTelefono(ricercaIdTelefono);
		}	
		connessione.close();
		return nuovoTelefono;
	}
}
