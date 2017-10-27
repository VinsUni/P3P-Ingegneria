package azienda.utility;

/**
 * @author      Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version     1.0
 */
public class Messaggi {
	// TITOLI
	/**
	 * Titolo di avviso.                  
	 */
	public static final String NOTICE = "Avviso!";
	/**
	 * Titolo di errore.                 
	 */
	public static final String ERROR = "Errore!";

	// MESSAGGI CONNESSIONE AL DATABASE 
	/**
	 * Errore di connessione al database.                   
	 */
	public static final String DB_CONNECTION_ERROR = "Si è verificato un errore durante la connessione al database!";
	/**
	 * Errore di connessione al file contenente le informazioni sul database.                
	 */
	public static final String FILE_CONNECTION_ERROR = "Impossibile trovare le informazioni necessarie per la connessione al database!";

	// MESSAGGI NEL LOGIN
	/**
	 * Errore durante il login.                  
	 */
	public static final String LOGIN_ERROR = "Username o password errati!";
	/**
	 * Errore di password errata.                 
	 */
	public static final String WRONG_PW = "Password errata!";
	
	// MESSAGGI NELLA GESTIONE DELL'UTENTE
	/**
	 * Errore nella modifica della password.                   
	 */
	public static final String PW_MOD_ERROR = "Errore nella modifica della password!";
	/**
	 * Errore nella modifica della password.                   
	 */
	public static final String DIFFERENT_PW_ERROR = "Le password sono diverse!";
	/**
	 * Conferma di modifica della massword.                    
	 */
	public static final String PW_MODIFIED = "Password modificata";
	/**
	 * Errore nella lunghezza della password.                  
	 */
	public static final String PW_LENGHT_ERROR = "La password deve essere lunga almeno 6 caratteri e massimo 20 caratteri!";
	/**
	 * Errore nell'inserimento dello username.                    
	 */
	public static final String USR_LENGHT_ERROR = "Username inserito non valido!\nLo username deve essere lungo massimo 20 caratteri!";
	/**
	 * Errore nell'eliminazione dell'utente.                 
	 */
	public static final String DELETE_USR_WARNING = "Sei sicuro di voler eliminare questo account?";
	/**
	 * Tentativo di eliminazione dell'utente amministratore.                   
	 */
	public static final String DELETE_ADMIN_WARNING = "Non puoi eliminare l'amministratore!";
	/**
	 * L'account che si sta utilizzando non è amministratore.                
	 */
	public static final String NOT_ADMIN = "L'account utente che stai utilizzando non è amministratore!";
	
	// MESSAGGI NELL'ELIMINAZIONE
	/**
	 * Avviso che l'eliminazione è avvenuta con successo.                
	 */
	public static final String DELETE_NOTICE = "L'eliminazione è avvenuta con successo!";
	/**
	 * Errore nell'eliminazione.                    
	 */
	public static final String DELETE_ERROR = "Si è verificato un errore durante l'eliminazione!";
	/**
	 * Conferma di eliminazione.                   
	 */
	public static final String DELETE_WARNING = "Sei sicuro di voler eliminare questo elemento?";
	
	// MESSAGGI NELL'INSERIMENTO
	/**
	 * Avviso che la creazione è avvenuta con successo.                
	 */
	public static final String INSERT_NOTICE = "La creazione è avvenuto con successo!";
	/**
	 * Errore nella creazione.                    
	 */
	public static final String INSERT_ERROR = "Si è verificato un errore durante la creazione!";
	/**
	 * Avviso che non è stato specificato nessun numero di telefono.                   
	 */
	public static final String UNSPECIFIED_PHONE = "Devi inserire almeno un numero di telefono!";	
	/**
	 * Avviso che non sono stati compilati tutti i campi.                  
	 */
	public static final String EMPTY_FIELD = "Devi compilare tutti i campi!";	
	/**
	 * Avviso che non sono stati compilati tutti i campi.                  
	 */
	public static final String NOT_VALID_EMAIL = "E-Mail inserita non valida!";		
	/**
	 * Avvisa che l'utente deve inserire almeno un indirizzo.
	 */
	public static final String AVVISO_INDIRIZZO = "Devi inserire un indirizzo.";
	/**
	 * Avvisa che se non si inserisce un domicilio, verrà assegnato lo stesso indirizzo della residenza.
	 */
	public static final String STESSO_INDIRIZZO = "Stesso indirizzo della residenza.";

	// MESSAGGI NELLA MODIFICA
	/**
	 * Avviso che la modifica è avvenuta con successo.              
	 */
	public static final String UPDATE_NOTICE = "La modifica è avvenuta con successo!";
	/**
	 * Errore nella modifica.                   
	 */
	public static final String UPDATE_ERROR = "Si è verificato un errore durante la modifica!";
	
	// MESSAGGI NELLA CREAZIONE DELLA SCHEDA
	/**
	 * Errore nella creazione della scheda.                
	 */
	public static final String CREATION_FORM_ERROR = "Si è verificato un errore nella creazione della scheda!";
	/**
	 * Avviso di avvenuta creazione della scheda.            
	 */
	public static final String CREATED_FORM_NOTICE = "La scheda è stata creata con successo nel percorso specificato.";
	/**
	 * Errore nell'apertura della scheda.                
	 */
	public static final String OPEN_FORM_ERROR = "Si è verificato un errore nell'apertura della scheda!";
	/**
	 * Avviso che non è stato selezionato nessun campo.                  
	 */
	public static final String UNSELECTED_FIELDS = "Devi selezionare almeno un campo!";
	
	// ALTRO
	/**
	 * Messaggio di uscita dal programma.                   
	 */
	public static final String EXIT = "Sei sicuro di voler uscire dal programma?";
	
	/**
	 * Errore specifico.                    
	 */
	public static final String SPECIFIC_ERROR = "Si è verificato un errore: ";

	/**
	 * Non usato
	 */
	private Messaggi(){};
}
