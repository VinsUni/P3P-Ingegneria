package azienda.eccezioni;

import azienda.utility.Messaggi;

/**
 * @author      Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version     1.0
 */

@SuppressWarnings("serial")
public class CheckException extends Exception{

	/**
	 * Eccezione che segnala se un campo è vuoto.
	 * 
	 * @return void
	 */
	public CheckException(){
		super(Messaggi.EMPTY_FIELD);
	}
	
	/**
	 * Eccezione che segnala se un campo inserito non è valido.
	 * 
	 * @param e Messaggio
	 * @return void
	 */
	public CheckException(String e){
		super(e);
	}	
}
