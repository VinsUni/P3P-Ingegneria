package azienda.sicurezza;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author      Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version     1.0
 */
class JdbcConnectionConf {
	private String url;
	private String username;
	private String password;
	
	/**
	 * Legge le informazioni necessarie per connetteri alla base di dati dal file "db.dat".
	 * 
	 * @throws IOException
	 */
	JdbcConnectionConf() throws IOException{
		BufferedReader in = null;
		try{	
			in = new BufferedReader(new FileReader("db.dat"));
			url = in.readLine();
			username = in.readLine();
			password = in.readLine();
		}finally{
			if(in!=null) in.close();
		}
	}

	/**
	 * Restituisce l'URL della base di dati.
	 * 
	 * @return URL della base di dati.
	 */
	String getUrl() {
		return url;
	}

	/**
	 * Restituisce lo username necessario per connettersi alla base di dati.
	 * 
	 * @return username.
	 */
	String getUsername() {
		return username;
	}

	/**
	 * Restituisce la password necessaria per connettersi alla base di dati.
	 * 
	 * @return password.
	 */
	String getPassword() {
		return password;
	}
}
