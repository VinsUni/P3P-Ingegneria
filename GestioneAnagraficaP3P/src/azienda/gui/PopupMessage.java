package azienda.gui;

import javax.swing.JOptionPane;
import azienda.utility.Messaggi;

/**
 * @author      Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version     1.0
 */
public class PopupMessage {
	/**
	 * Non usato
	 */
	private PopupMessage(){};
	
	/**
	 * Popup messaggio di errore.                   
	 *          
	 * @param message Messaggio di errore.         
	 */
	public static void newErrorMessage(String message){
		JOptionPane.showMessageDialog(null, message, Messaggi.ERROR, JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Popup messaggio di avviso.                   
	 * 
	 * @param message Messaggio di avviso.
	 */
	public static void newNoticeMessage(String message){
		JOptionPane.showMessageDialog(null, message, Messaggi.NOTICE, JOptionPane.INFORMATION_MESSAGE);		
	}
}
