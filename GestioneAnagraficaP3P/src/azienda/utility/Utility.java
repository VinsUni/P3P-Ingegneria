package azienda.utility;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

import azienda.eccezioni.CheckException;

/**
 * @author      Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version     1.0
 */
public class Utility {
	/**
	 * Non usato
	 */
	private Utility(){};
	
	private static final String[] MESI = {"Gennario", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio",
			"Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"
	};
	
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	/**
	 * Verifica se la stringa inserita è un email
	 *                     
	 * @param email stringa da verificare;
	 * @throws CheckException
	 */
	public static void isEmail(String email) throws CheckException{
		if(!(email.length()<=40 && email.matches(EMAIL_PATTERN))){
			throw new CheckException(Messaggi.NOT_VALID_EMAIL);
		}
	}
	
	/**
	 * Restituisce la data attuale nel formato GG MESE AAAA.
	 *                     
	 * @return Stringa contenente la data attuale.
	 */
	public static String getDateTextFormat(){
    	
    	GregorianCalendar gc = new GregorianCalendar();
    	int anno = gc.get(Calendar.YEAR);
    	int mese = gc.get(Calendar.MONTH);
    	int giorno = gc.get(Calendar.DATE);
    	
    	return giorno + " " + MESI[mese] + " " + anno;
	}
	
	/**
	 * Restituisce la data attuale nel formato GG-MM-AAAA.
	 *                     
	 * @return Stringa contenente la data attuale.
	 */
	public static String getDateHyphenFormat(){
    	
    	GregorianCalendar gc = new GregorianCalendar();
    	int anno = gc.get(Calendar.YEAR);
    	int mese = gc.get(Calendar.MONTH) + 1;
    	int giorno = gc.get(Calendar.DATE);
    	
    	return giorno + Testi.TRATTINO + mese + Testi.TRATTINO + anno;
	}
	
	/**
	 * Converte la data dal formato AAAAA-MM-GG al formato GG/MM/AAAA.
	 *                     
	 * @param data Stringa contenente la data nel formato AAAAA-MM-GG.
	 * @return Stringa contenente la data nel formato GG/MM/AAAA.
	 */
	public static String sqlToStringFormat(String data){
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(Date.valueOf(data));
		int anno = gc.get(Calendar.YEAR);
		int mese = gc.get(Calendar.MONTH) + 1;
		int giorno = gc.get(Calendar.DATE);
		
		return giorno + Testi.SLASH + mese + Testi.SLASH + anno;
	}
	
	/**
	 * Converte la data da un oggetto di classe Calendar al formato AAAA-MM-GG.
	 *                     
	 * @param calendar Oggetto di classe Calendar.
	 * @return Stringa contenente la data nel formato AAAA-MM-GG.
	 */
	public static String dateToSql(Calendar calendar){
    	String data = "";
		if(calendar!=null) {
			calendar = new GregorianCalendar();
    		int anno = calendar.get(Calendar.YEAR);
    		int mese = calendar.get(Calendar.MONTH) + 1;
    		int giorno = calendar.get(Calendar.DATE);
    		data = anno+Testi.TRATTINO+mese+Testi.TRATTINO+giorno;
		}
    	
    	return data;
	}
	
	/**
	 * Converte la data dal formato AAAAA-MM-GG ad un oggetto di classe Date.
	 *                     
	 * @param data Stringa contenente la data nel formato AAAAA-MM-GG.
	 * @return Oggetto di classe Date.
	 */
	public static java.util.Date sqlToDate(String data){
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(Date.valueOf(data));
		return calendar.getTime();
	}
	
	/**
	 * Verifica se una stringa è vuota.
	 *                     
	 * @param stringa Stringa da controllare.
	 * @throws CheckException
	 */
	public static void isNotEmpty(String stringa) throws CheckException{
		if(stringa.length()==0){
			throw new CheckException();
		}
	}
}
