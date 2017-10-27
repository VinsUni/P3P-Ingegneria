package azienda.anagrafica;

/**
 * @author      Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version     1.0
 */
public abstract class Persona implements Anagrafica{

	protected String codiceFiscale;
	protected String nome;
	protected String cognome;
	protected char sesso;
	protected String dataNascita;
	protected String indirizzoMail;
	protected Indirizzo residenza;
	protected Indirizzo domicilio;
	protected Telefono telefono;	
	/**
	 * Lunghezza massima del codice fiscale.                      
	 */
	public static final int CF_MAX_LENGHT = 20;
	
	/**
	 * Costruttore del dipendente.                      
	 *
	 * @param codiceFiscale Codice fiscale del dipendente.
	 * @param nome Nome del dipendente.
	 * @param cognome Cognome del dipendente. 
	 * @param sesso Sesso del dipendente.
	 * @param dataNascita Data di nascita del dipendente.
	 * @param indirizzoMail Indirizzo E-Mail del dipendente.
	 */
	public Persona(String codiceFiscale, String nome, String cognome, char sesso, String dataNascita,
			String indirizzoMail) {
		super();
		this.codiceFiscale = codiceFiscale;
		this.nome = nome;
		this.cognome = cognome;
		this.sesso = sesso;
		this.dataNascita = dataNascita;
		this.indirizzoMail = indirizzoMail;
	}

	/**
	 * Restituisce il codice fiscale.                           
	 *
	 * @return codiceFiscale.
	 */
	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	/**
	 * Restituisce il nome.                           
	 *
	 * @return Nome.
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Restituisce il cognome.                           
	 *
	 * @return cognome.
	 */
	public String getCognome() {
		return cognome;
	}

	/**
	 * Restituisce il sesso.                          
	 *
	 * @return Sesso.
	 */
	public char getSesso() {
		return sesso;
	}

	/**
	 * Restituisce la data di nascita.                           
	 *
	 * @return dataNascita.
	 */
	public String getDataNascita() {
		return dataNascita;
	}

	/**
	 * Restituisce l'indirizzo E-Mail                        
	 *
	 * @return indirizzoMail.
	 */
	public String getIndirizzoMail() {
		return indirizzoMail;
	}

	/**
	 * Restituisce la residenza.                       
	 *
	 * @return residenza.
	 */
	public Indirizzo getResidenza() {
		return residenza;
	}

	/**
	 * Restituisce il domicilio.                     
	 *
	 * @return domicilio.
	 */
	public Indirizzo getDomicilio() {
		return domicilio;
	}

	/**
	 * Imposta la residenza.                  
	 *
	 */
	public void setResidenza(Indirizzo residenza) {
		this.residenza = residenza;
	}

	/**
	 * Imposta il domicilio.              
	 *
	 */
	public void setDomicilio(Indirizzo domicilio) {
		this.domicilio = domicilio;
	}

	/**
	 * Restituisce i numeri di telefono.               
	 *
	 * @return telefono.
	 */
	public Telefono getTelefono() {
		return telefono;
	}

	/**
	 * Imposta il numero di telefono.            
	 *
	 */
	public void setTelefono(Telefono telefono) {
		this.telefono = telefono;
	}
}
