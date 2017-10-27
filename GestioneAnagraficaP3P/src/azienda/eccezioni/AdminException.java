package azienda.eccezioni;

import azienda.utility.Messaggi;

/**
 * @author      Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version     1.0
 */
@SuppressWarnings("serial")
public class AdminException extends Exception{

	/**
	 * Eccezione che segnala se l'utente non è admin.
	 * 
	 * @return void
	 */
	public AdminException(){
		super(Messaggi.NOT_ADMIN);
	}	
}
