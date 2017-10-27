package azienda.sicurezza;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author      Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version     1.0
 */
public class JdbcConnector {
	
	/**
	 * Non usato
	 */
	private JdbcConnector() {}
	
	/**
	 * Metodo che ritorna una connessione al DBMS                         
	 *
	 * @return Oggetto di classe Connection.
	 * @throws SQLException, IOException
	 */
	public static Connection getConnection() throws SQLException, IOException{		
		JdbcConnectionConf conf = new JdbcConnectionConf();
		new com.mysql.jdbc.Driver();
		return DriverManager.getConnection(conf.getUrl(), conf.getUsername(), conf.getPassword());	
	}
	
}
