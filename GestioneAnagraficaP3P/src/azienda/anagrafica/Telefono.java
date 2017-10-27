package azienda.anagrafica;


/**
 * @author      Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version     1.0
 */
public class Telefono implements Anagrafica{
	private int idTelefono;
	private String numeroCellulare;
	private String numeroCasa;
	
	/**
	 * Costruttore del dipendente.                      
	 *
	 * @param numeroCellulare Numero di cellulare.
	 * @param numeroCasa Numero di casa.
	 */
	public Telefono(String numeroCellulare, String numeroCasa) {
		this.numeroCellulare = numeroCellulare;
		this.numeroCasa = numeroCasa;
	}

	/**
	 * Restituisce il numero identificativo del telefono.                         
	 *
	 * @return idTelefono.
	 */
	public int getIdTelefono() {
		return idTelefono;
	}
	/**
	 * Imposta il numero identificativo del telefono.                     
	 *
	 * @return codiceFiscale.
	 */
	public void setIdTelefono(int idTelefono) {
		this.idTelefono = idTelefono;
	}
	/**
	 * Restituisce il numero di cellulare.                    
	 *
	 * @return numeroCellulare.
	 */
	public String getNumeroCellulare() {
		return numeroCellulare;
	}
	/**
	 * Imposta il numero di cellulare.                     
	 *
	 */
	public void setNumeroCellulare(String numeroCellulare) {
		this.numeroCellulare = numeroCellulare;
	}
	/**
	 * Restituisce il numero di casa.                         
	 *
	 * @return numeroCasa.
	 */
	public String getNumeroCasa() {
		return numeroCasa;
	}
	/**
	 * Imposta il numero di casa.                           
	 *
	 */
	public void setNumeroCasa(String numeroCasa) {
		this.numeroCasa = numeroCasa;
	}

	@Override
	public String getQueryCreazione() {
		String query = "INSERT INTO Telefoni (ID_Telefono, NumeroCellulare, NumeroCasa) VALUES "
				+ " ('"+idTelefono+"', '"+numeroCellulare+"', '"+numeroCasa+"');";
		return query;
	}

	@Override
	public String getQueryModifica(String vecchioId) {
		String query = "UPDATE Telefoni "
				+"SET "
				+"NumeroCellulare = '"+numeroCellulare+"', "
				+"NumeroCasa = '"+numeroCasa+"' "
				+"WHERE ID_Telefono = '"+vecchioId+"';";
		return query;
	}

	@Override
	public String getQueryElimina() {
		String query = "DELETE FROM Telefoni WHERE ID_Telefono = '" + getIdTelefono() +"';";
		return query;
	}
	
}
