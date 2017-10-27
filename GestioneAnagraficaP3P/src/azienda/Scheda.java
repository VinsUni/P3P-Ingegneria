package azienda;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import azienda.anagrafica.Dipendente;
import azienda.anagrafica.Spazio;
import azienda.anagrafica.Strumentazione;
import azienda.anagrafica.database.DipendenteDB;
import azienda.anagrafica.database.SpazioDB;
import azienda.anagrafica.database.StrumentazioneDB;
import azienda.utility.Testi;
import azienda.utility.Utility;

/**
 * @author      Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version     1.0
 */
public class Scheda {
	private List<?>[] scheda;
	private List<Dipendente> dipendenti;
	private List<Spazio> spazi;
	private List<Strumentazione> strumentazioni;	
	private final String[] ELENCO_TITOLI = {
			"Elenco dipendenti: ",
			"Elenco strumentazioni: ",
			"Elenco spazi: ",
			"Occupazione degli spazi da parte dei dipendenti: ",
			"Uso della strumentazione: "
	};
	private final int PARAGRAPH_SIZE = 30;
	private Font fontTitolo;
	private Font fontTesto;
		
	/**
	 * Costruttore della classe.  
	 * 
	 * @throws SQLException
	 * @throws IOException             
	 */
	public Scheda() throws SQLException, IOException{
		scheda = new ArrayList[3];
		dipendenti = DipendenteDB.leggiDipendenti();
		scheda[0] = dipendenti;
		strumentazioni = StrumentazioneDB.leggiStrumentazioni();
		scheda[1] = strumentazioni;
		spazi = SpazioDB.leggiSpazi();
		scheda[2] = spazi;
		int textSize = 12;
		fontTitolo = new Font(Font.FontFamily.HELVETICA  , textSize, Font.BOLD);
		textSize = 11;
		fontTesto = new Font(Font.FontFamily.HELVETICA  , textSize, Font.NORMAL);
	}
	
	/**
	 * Crea la scheda dinamicamente in formato PDF in base ai campi selezionati e la salva nel percorso 
	 * specificato.
	 *                     
	 * @param campiSelezionati Vettore booleano contenente i campi selezionati.
	 * @param fileSelezionato Percorso in cui salvare il PDF.
	 * @throws Exception
	 */
	public void creaScheda(boolean[] campiSelezionati, File fileSelezionato) throws Exception{
		FileOutputStream file = null;
		Document document = null;
		try {
			file = new FileOutputStream(fileSelezionato);
	    	document = creaDocumento(campiSelezionati, file);    
	    } finally {	
		   	if(document!=null){
		   		document.close();
		   	}
		   	if(file!=null){
		   		file.close();
		   	}
	    }
	}
	
	/**
	 * Crea il documento
	 *                     
	 * @param campiSelezionati Vettore booleano contenente i campi selezionati.
	 * @param file Percorso in cui salvare il PDF.
	 * @throws Exception
	 */
	private Document creaDocumento(boolean[] campiSelezionati, FileOutputStream file) throws Exception{
		Document document = new Document();
		PdfWriter.getInstance(document, file);
    	document.open();
    	document.add(new Paragraph(Utility.getDateTextFormat(), fontTitolo));
    	int paragraphTitle = 25;
    	document.add(new Paragraph(paragraphTitle, Testi.TITOLO_INIZIO, fontTitolo));
    	int textSize = 11;
    	Paragraph paragrafoTesto = new Paragraph(Testi.TESTO, new Font(Font.FontFamily.HELVETICA  , textSize, Font.ITALIC));
    	int spacing = 10;
    	paragrafoTesto.setSpacingBefore(spacing);
    	document.add(paragrafoTesto);  	
    	
    	int[] size = new int[3];
    	size[0] = dipendenti.size();
    	size[1] = strumentazioni.size();
    	size[2] = spazi.size();
    	  	
    	
    	creaListaAnagrafiche(document, campiSelezionati, size);  
    	if(campiSelezionati[3]){
    		creaListaAssegnazioneStrumentazioni(document, campiSelezionati, size);
    	}
    	if(campiSelezionati[4]){
    		creaListaAssegnazioneDipendentiSpazi(document, campiSelezionati, size);
    	}
    		
    	document.add(new Paragraph(PARAGRAPH_SIZE, Testi.TITOLO_FINE, fontTitolo));
    	document.add(paragrafoTesto);
    	
    	return document;
	}
	
	/**
	 * Crea la lista delle anagrafiche
	 *                 
	 * @param document Documento in cui creare la lista
	 * @param campiSelezionati Vettore booleano contenente i campi selezionati.
	 * @param size Dimensioni delle liste
	 * @throws Exception
	 */
	private void creaListaAnagrafiche(Document document, boolean[] campiSelezionati, int size[]) throws DocumentException{
		for(int i=0; i<3; i++){
    		if(campiSelezionati[i]){
    			document.add(new Paragraph(PARAGRAPH_SIZE, ELENCO_TITOLI[i], fontTitolo));
	    		for(int j=0; j<size[i]; j++){
	    			document.add(new Paragraph(scheda[i].get(j).toString(), fontTesto));
	    		}
    		}		
    	}	
	}
	
	/**
	 * Crea la lista delle assegnazioni dei dipendenti agli spazi
	 *                 
	 * @param document Documento in cui creare la lista
	 * @param campiSelezionati Vettore booleano contenente i campi selezionati.
	 * @param size Dimensioni delle liste
	 * @throws Exception
	 */
	private void creaListaAssegnazioneDipendentiSpazi(Document document, boolean[] campiSelezionati, int size[]) throws DocumentException{  	
		int k = 3;
		document.add(new Paragraph(PARAGRAPH_SIZE, ELENCO_TITOLI[k], fontTitolo));
		for (int i = 0; i < size[2]; i++) {
			document.add(new Paragraph("     • Spazio " + spazi.get(i).getIdentificativoSpazio(), fontTesto));
			for (int j = 0; j < size[0]; j++) {
				if (spazi.get(i).getIdSpazio() == dipendenti.get(j).getIdSpazio()) {
					document.add(new Paragraph("          - " + dipendenti.get(j).getIdentificativoDipendente(), fontTesto));
				}
			}
		}  	
	}
	
	/**
	 * Crea la lista delle assegnazioni delle strumentazioni
	 *                 
	 * @param document Documento in cui creare la lista
	 * @param campiSelezionati Vettore booleano contenente i campi selezionati.
	 * @param size Dimensioni delle liste
	 * @throws Exception
	 */
	private void creaListaAssegnazioneStrumentazioni(Document document, boolean[] campiSelezionati, int size[]) throws DocumentException, SQLException, IOException{
    	int k = 4;
		document.add(new Paragraph(PARAGRAPH_SIZE, ELENCO_TITOLI[k], fontTitolo));
    	for(int i=0; i<size[1]; i++){
    		document.add(new Paragraph("     • Strumentazione "+strumentazioni.get(i).getIdentificativoStrumentazione(), fontTesto));
    		document.add(new Paragraph("          - Usato da: "+StrumentazioneDB.getUsoStrumentazione(strumentazioni.get(i))[0], fontTesto));
    		document.add(new Paragraph("          - Destinato a: "+StrumentazioneDB.getUsoStrumentazione(strumentazioni.get(i))[1], fontTesto));
			
    	}  	
	}
	
	
}
