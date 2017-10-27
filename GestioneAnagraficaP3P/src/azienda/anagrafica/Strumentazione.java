package azienda.anagrafica;

import azienda.utility.Utility;

/**
 * @author      Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version     1.0
 */
public class Strumentazione implements Anagrafica{
	private int idStrumentazione;
	private String nome;
	private String modello;
	private String marca;
	private String tipologia;
	private String dataAcquisto;
	private String codiceFiscale;
	private int idSpazio;
	
	/**
	 * Costruttore della strumentazione.                      
	 *
	 * @param nome Nome della strumentazione.
	 * @param modello Modello della strumentazione. 
	 * @param marca Marca della strumentazione.
	 * @param tipologia Tipologia della strumetazione.
	 * @param dataAcquisto Data di acquisto della strumentazione.
	 * @param codiceFiscale Codice fiscale del dipendente a cui la strumentazione è assegnata.
	 * @param idSpazio Identificativo dello spazio a cui la strumentazione è assegnata.
	 */
	public Strumentazione(String nome, String modello, String marca, String tipologia, 
			String dataAcquisto, String codiceFiscale, int idSpazio) {
		super();
		this.nome = nome;
		this.modello = modello;
		this.marca = marca;
		this.tipologia = tipologia;
		this.dataAcquisto = dataAcquisto;
		this.codiceFiscale = codiceFiscale;
		this.idSpazio = idSpazio;
	}

	/**
	 * Restituisce il nome.                         
	 *
	 * @return nome.
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Restituisce il modello.                         
	 *
	 * @return modello.
	 */
	public String getModello() {
		return modello;
	}

	/**
	 * Restituisce la marca.                         
	 *
	 * @return marca.
	 */
	public String getMarca() {
		return marca;
	}

	/**
	 * Restituisce la tipologia.                         
	 *
	 * @return tipologia.
	 */
	public String getTipologia() {
		return tipologia;
	}

	/**
	 * Restituisce la data di acquisto.                         
	 *
	 * @return dataAcquisto.
	 */
	public String getDataAcquisto() {
		return dataAcquisto;
	}

	/**
	 * Restituisce il numero identificativo della strumentazione.                         
	 *
	 * @return idStrumentazione.
	 */
	public int getIdStrumentazione() {
		return idStrumentazione;
	}

	/**
	 * Assegna il numero identificativo della strumentazione.                       
	 *
	 */
	public void setIdStrumentazione(int idStrumentazione){
		this.idStrumentazione = idStrumentazione;
	}
	
	/**
	 * Restituisce il codice fiscale a cui la strumentazione è assegnata.                         
	 *
	 * @return codiceFiscale.
	 */
	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	/**
	 * Restituisce l'identificativo dello spazio a cui la strumentazione è assegnata.                         
	 *
	 * @return idSpazio.
	 */
	public int getIdSpazio() {
		return idSpazio;
	}

	/**
	 * Restituisce la stringa composta da numero identificativo e nome.                         
	 *
	 * @return Stringa identificativa della strumentazione.
	 */
	public String getIdentificativoStrumentazione(){
		String stringa = "ID: " + idStrumentazione + " - " + nome;
		return stringa;
	}
	
	/**
	 * Restituisce la stringa formattata contenente i campi della strumentazione.                           
	 *
	 * @return Stringa descrittiva dell'indirizzo.
	 */
	@Override
	public String toString() {
		String stringa = "     • ID: "+idStrumentazione + " - " + nome + "\n" 
				+ "          - Modello: " + modello + "\n"
				+ "          - Marca: " + marca + "\n" 
				+ "          - Tipologia: " + tipologia + "\n" 
				+ "          - Data di acquisto: " + Utility.sqlToStringFormat(dataAcquisto);
		return stringa;
	}

	/**
	 * Restituisce la query necessaria a creare la strumentazione nella base di dati.                           
	 *
	 * @return Stringa contenente la query per la creazione della strumentazione
	 * @see crea()
	 */
	@Override
	public String getQueryCreazione() {
		String query;
		if(codiceFiscale==null && idSpazio<0){
			query = "INSERT INTO Strumentazioni (Nome, Modello, Marca, Tipologia, DataAcquisto) "
					+ "VALUES ('"+nome+"', '"+modello+"', '"+marca+"', '"+tipologia+"', '"+dataAcquisto+"');";			
		} else if(codiceFiscale==null){
			query = "INSERT INTO Strumentazioni (Nome, Modello, Marca, Tipologia, DataAcquisto, ID_Spazio) "
					+ "VALUES ('"+nome+"', '"+modello+"', '"+marca+"', '"+tipologia+"', '"+dataAcquisto+"', '"+idSpazio+"');";		
		} else if(idSpazio<0){
			query = "INSERT INTO Strumentazioni (Nome, Modello, Marca, Tipologia, DataAcquisto, CodiceFiscale) "
					+ "VALUES ('"+nome+"', '"+modello+"', '"+marca+"', '"+tipologia+"', '"+dataAcquisto+"', '"+codiceFiscale+"');";
		} else {
			query = "INSERT INTO Strumentazioni (Nome, Modello, Marca, Tipologia, DataAcquisto, CodiceFiscale, ID_Spazio) "
					+ "VALUES ('"+nome+"', '"+modello+"', '"+marca+"', '"+tipologia+"', '"+dataAcquisto+"', '"+codiceFiscale+"', '"+idSpazio+"');";
		}
		return query;
	}

	/**
	 * Restituisce la query necessaria a modificare la strumentazione nella base di dati.                           
	 *
	 * @param  vecchioId Numero identificativo della strumentazione.
	 * @return Stringa contenente la query per la modifica della strumentazione.
	 * @see modifica(int vecchioId)
	 */
	@Override
	public String getQueryModifica(String vecchioId) {
		String query = "UPDATE Strumentazioni "
				+"SET "
				+"Nome = '"+nome+"',"
				+"Modello = '"+modello+"',"
				+"Marca = '"+marca+"',"
				+"Tipologia = '"+tipologia+"',"
				+"DataAcquisto = '"+dataAcquisto+"', "
				+"CodiceFiscale = '"+codiceFiscale+"', "
				+"ID_Spazio = '"+idSpazio+"' "
				+"WHERE ID_Strumentazione = '"+vecchioId+"';";
		return query;
	}

	/**
	 * Restituisce la query necessaria a eliminare la strumentazione dalla base di dati.                           
	 *
	 * @return Stringa contenente la query per l'eliminazione della strumentazione.
	 * @see elimina()
	 */
	@Override
	public String getQueryElimina() {
		String query = "DELETE FROM Strumentazioni WHERE ID_Strumentazione = '" + idStrumentazione +"';";
		return query;
	}
	

}
