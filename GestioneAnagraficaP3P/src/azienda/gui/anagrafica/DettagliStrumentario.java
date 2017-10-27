package azienda.gui.anagrafica;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import com.toedter.calendar.JDateChooser;
import azienda.anagrafica.*;
import azienda.anagrafica.database.DipendenteDB;
import azienda.anagrafica.database.SpazioDB;
import azienda.anagrafica.database.StrumentazioneDB;
import azienda.eccezioni.CheckException;
import azienda.gui.*;
import azienda.gui.filtri.SizeFilter;
import azienda.gui.ricerca.RicercaStrumentario;
import azienda.utility.*;

/**
 * @author      Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version     1.0
 */

@SuppressWarnings("serial")
public class DettagliStrumentario extends DettagliAnagrafica {
    private List<Spazio> spazi;
    private List<Dipendente> dipendenti;
	private JComboBox<String> comboSpazi;
	private JComboBox<String> comboDipendenti;
	private JTextField textNome;
	private JTextField textModello;
	private JTextField textMarca;
	private JTextField textTipologia;
	private JDateChooser dateChooser;
	
	/**
	 * Costruttore del pannello di creazione della strumentazione.
	 * 
	 * @param finestraPrincipale Finestra della schermata principale.
	 */
	public DettagliStrumentario(FinestraPrincipale finestraPrincipale) {
	
		creaGrafica(finestraPrincipale);
		lblTitolo.setText(Pulsanti.NUOVA_STRUMENTAZIONE);
		
		btnConferma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try{			
					Strumentazione strumentazione = verificaDati();					
					StrumentazioneDB.crea(strumentazione);
					PopupMessage.newNoticeMessage(Messaggi.INSERT_NOTICE);
					finestraPrincipale.resetPannelloPrincipale();
				} catch (CheckException e1) {
					PopupMessage.newErrorMessage(Messaggi.SPECIFIC_ERROR + e1.getMessage());
				} catch (SQLException e2) {
					PopupMessage.newErrorMessage(Messaggi.INSERT_ERROR);					
				} catch (IOException e3) {
					PopupMessage.newErrorMessage(Messaggi.FILE_CONNECTION_ERROR);
				}
			}
		});			
		
		btnAnnulla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				finestraPrincipale.resetPannelloPrincipale();
			}
		});
	}
	
	/**
	 * Costruttore del pannello di modifica della strumentazione.
	 * 
	 * @param finestraPrincipale Finestra della schermata principale.
	 * @param strumentazione Strumentazione da modificare.
	 */
	public DettagliStrumentario(FinestraPrincipale finestraPrincipale, Strumentazione strumentazione) {
		creaGrafica(finestraPrincipale);
		lblTitolo.setText(Pulsanti.MODIFICA_STRUMENTAZIONE);
		visualizzaDati(strumentazione);
		
		btnConferma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{			
					int idStrumentazione = strumentazione.getIdStrumentazione();
					Strumentazione nuovaStrumentazione = verificaDati();					
					StrumentazioneDB.modifica(nuovaStrumentazione, idStrumentazione);
					PopupMessage.newNoticeMessage(Messaggi.UPDATE_NOTICE);
				} catch (CheckException e1) {
					PopupMessage.newErrorMessage(Messaggi.SPECIFIC_ERROR+e1.getMessage());
				} catch (SQLException e2) {
					PopupMessage.newErrorMessage(Messaggi.UPDATE_ERROR);
				} catch (IOException e3) {
					PopupMessage.newErrorMessage(Messaggi.FILE_CONNECTION_ERROR);
				}
			}
		});			
		
		btnAnnulla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				finestraPrincipale.setPannelloPrincipale(new RicercaStrumentario(finestraPrincipale));
			}
		});
	}
	
	/**
	 * Crea la grafica.                         
	 *
	 * @param finestraPrincipale.
	 */
	protected final void creaGrafica(FinestraPrincipale finestraPrincipale){
		super.creaGrafica(finestraPrincipale);
		
		JLabel lblNome = new JLabel(Dati.NOME);
		lblNome.setBounds(44, 23, 46, 14);
		panelCorpo.add(lblNome);
		
		JLabel lbModello = new JLabel(Dati.MODELLO);
		lbModello.setBounds(44, 48, 67, 14);
		panelCorpo.add(lbModello);
		
		JLabel lblMarca = new JLabel(Dati.MARCA);
		lblMarca.setBounds(44, 73, 46, 14);
		panelCorpo.add(lblMarca);
		
		JLabel lblTipologia = new JLabel(Dati.TIPOLOGIA);
		lblTipologia.setBounds(44, 98, 101, 14);
		panelCorpo.add(lblTipologia);
		
		textNome = new JTextField();
		textNome.setBounds(158, 20, 224, 20);
		panelCorpo.add(textNome);
		textNome.setColumns(20);
		AbstractDocument documentNome = (AbstractDocument) textNome.getDocument();
		documentNome.setDocumentFilter(new SizeFilter(Strumentazione.MAX_LENGHT));
		
		textModello = new JTextField();
		textModello.setBounds(158, 45, 224, 20);
		panelCorpo.add(textModello);
		textModello.setColumns(20);
		AbstractDocument documentModello = (AbstractDocument) textModello.getDocument();
		documentModello.setDocumentFilter(new SizeFilter(Strumentazione.MAX_LENGHT));
		
		textTipologia = new JTextField();
		textTipologia.setBounds(158, 95, 224, 20);
		panelCorpo.add(textTipologia);
		textTipologia.setColumns(10);
		AbstractDocument documentTipologia = (AbstractDocument) textTipologia.getDocument();
		documentTipologia.setDocumentFilter(new SizeFilter(Strumentazione.MAX_LENGHT));
		
		textMarca = new JTextField();
		textMarca.setBounds(158, 70, 224, 20);
		panelCorpo.add(textMarca);
		textMarca.setColumns(10);
		AbstractDocument documentMarca = (AbstractDocument) textMarca.getDocument();
		documentMarca.setDocumentFilter(new SizeFilter(Strumentazione.MAX_LENGHT));
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 11, 516, 1);
		panelCorpo.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 245, 537, 1);
		panelCorpo.add(separator_1);
		
		JLabel lblDataDiAcquisto = new JLabel(Dati.DATA_ACQUISTO);
		lblDataDiAcquisto.setBounds(44, 123, 101, 14);
		panelCorpo.add(lblDataDiAcquisto);
		
		dateChooser = new JDateChooser();
		dateChooser.setBounds(158, 123, 224, 20);
		panelCorpo.add(dateChooser);
		
		add(panelPulsanti, BorderLayout.SOUTH);
		add(panelCorpo, BorderLayout.CENTER);
		
		JLabel lblAssegnaAdUn = new JLabel(Testi.ASSEGNA_DIPENDENTE);
		lblAssegnaAdUn.setBounds(44, 148, 210, 14);
		panelCorpo.add(lblAssegnaAdUn);
		
		DefaultComboBoxModel<String> listaDipendenti = new DefaultComboBoxModel<String>();
		DefaultComboBoxModel<String> listaSpazi = new DefaultComboBoxModel<String>();
		try {
			dipendenti = DipendenteDB.leggiDipendenti();
			listaDipendenti.addElement(Pulsanti.NESSUNO);
			int size = dipendenti.size();
			for(int i=0; i<size; i++){
				listaDipendenti.addElement(dipendenti.get(i).getIdentificativoDipendente());
			}	
			
			spazi = SpazioDB.leggiSpazi();
			listaSpazi.addElement(Pulsanti.NESSUNO);
			size = spazi.size();
			for(int i=0; i<size; i++){
				listaSpazi.addElement(spazi.get(i).getIdentificativoSpazio());
			}
		} catch (SQLException e) {
			PopupMessage.newErrorMessage(Messaggi.DB_CONNECTION_ERROR);
		} catch (IOException e) {
			PopupMessage.newErrorMessage(Messaggi.FILE_CONNECTION_ERROR);
		}
		
		comboDipendenti = new JComboBox<String>();
		comboDipendenti.setModel(listaDipendenti);
		comboDipendenti.setBounds(44, 165, 338, 20);
		panelCorpo.add(comboDipendenti);
		
		JLabel lblAssegnaAdUno = new JLabel(Testi.ASSEGNA_SPAZIO);
		lblAssegnaAdUno.setBounds(44, 190, 273, 14);
		panelCorpo.add(lblAssegnaAdUno);	
		
		comboSpazi = new JComboBox<String>();
		comboSpazi.setModel(listaSpazi);
		comboSpazi.setBounds(44, 209, 338, 20);
		panelCorpo.add(comboSpazi);
		add(panelTitolo, BorderLayout.NORTH);
		
	}
	
	/**
	 * Verifica i dati inseriti.                         
	 *
	 * @return strumentazione.
	 * @throws CheckException
	 * @throws SQLException
	 * @throws IOException
	 */
	private final Strumentazione verificaDati() throws CheckException, SQLException, IOException {
		String nome = textNome.getText();
		String modello = textModello.getText();
		String tipologia = textTipologia.getText();
		String marca = textMarca.getText();
		String dataAcquisto = Utility.dateToSql(dateChooser.getCalendar());
		String codiceFiscale = null;
		int idSpazio = -1;
		
		if(comboDipendenti.getSelectedIndex()>0){
			codiceFiscale = dipendenti.get(comboDipendenti.getSelectedIndex()-1).getCodiceFiscale();
		}	
		
		if(comboSpazi.getSelectedIndex()>0){
			idSpazio = spazi.get(comboSpazi.getSelectedIndex()-1).getIdSpazio();
		}	
		
		Utility.isNotEmpty(nome);
		Utility.isNotEmpty(modello);
		Utility.isNotEmpty(modello);
		Utility.isNotEmpty(tipologia);
		Utility.isNotEmpty(dataAcquisto);
		Strumentazione strumentazione = new Strumentazione(nome, modello, marca, tipologia, dataAcquisto, codiceFiscale, idSpazio);

		return strumentazione;
	}
	
	/**
	 * Visualizza i dati inseriti.                         
	 *
	 * @param strumentazione.
	 */
	private final void visualizzaDati(Strumentazione strumentazione){
		textNome.setText(strumentazione.getNome());	
		textMarca.setText(strumentazione.getMarca());	
		textModello.setText(strumentazione.getModello());
		textTipologia.setText(strumentazione.getTipologia());	
		dateChooser.setDate(Utility.sqlToDate(strumentazione.getDataAcquisto()));
		int selected = 0;
		int size = dipendenti.size();
		for(int i=0; i<size; i++){
			if(dipendenti.get(i).getCodiceFiscale().equals(strumentazione.getCodiceFiscale())){
				selected = i+1;
			}
		}
		comboDipendenti.setSelectedIndex(selected);
		
		selected =0;
		size = spazi.size();
		for(int i=0; i<size; i++){
			if(spazi.get(i).getIdSpazio()==strumentazione.getIdSpazio()){
				selected = i+1;
			}
		}
		comboSpazi.setSelectedIndex(selected);
	}
	
}
