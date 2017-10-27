package azienda.gui.anagrafica;

import java.awt.BorderLayout;
import java.awt.Color;
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
import azienda.eccezioni.CheckException;
import azienda.gui.*;
import azienda.gui.filtri.LongFilter;
import azienda.gui.filtri.SizeFilter;
import azienda.gui.ricerca.RicercaDipendente;
import azienda.utility.*;

/**
 * @author      Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version     1.0
 */
@SuppressWarnings("serial")
public class DettagliDipendente extends DettagliAnagrafica {
	private List<Spazio> spazi;
	private JComboBox<String> comboSpazi;
	private Indirizzo residenza;
	private Indirizzo domicilio;
	private DettagliDipendente pannelloDipendente;
	private JLabel lblIndirizzo1;
	private JLabel lblComune1;
	private JLabel lblIndirizzo2;
	private JLabel lblComune2;
	private JTextField textNome;
	private JTextField textCognome;
	private JTextField textMansione;
	private JTextField textMail;
	private JTextField textCellulare;
	private JTextField textCasa;
	private JTextField textCodFiscale;
	private JComboBox<String> comboSesso;
	private JDateChooser dateChooser;
	private JButton btnAggiungi;

	/**
	 * Costruttore del pannello di creazione del dipendente.
	 * 
	 * @param finestraPrincipale Finestra della schermata principale.
	 */
	public DettagliDipendente(FinestraPrincipale finestraPrincipale) {
		pannelloDipendente = this;

		creaGrafica(finestraPrincipale);
		lblTitolo.setText(Pulsanti.NUOVO_DIPENDENTE);
		// CONFERMA
		btnConferma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Dipendente dipendente = verificaDati();
					DipendenteDB.crea(dipendente);					
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

		// ANNULLA
		btnAnnulla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				finestraPrincipale.resetPannelloPrincipale();
			}
		});
	}

	/**
	 * Costruttore del pannello di modifica del dipendente.
	 * 
	 * @param finestraPrincipale Finestra della schermata principale.
	 * @param dipendente Dipendente da modificare.
	 */
	public DettagliDipendente(FinestraPrincipale finestraPrincipale, Dipendente dipendente) { 
		pannelloDipendente = this;

		creaGrafica(finestraPrincipale);
		
     	lblTitolo.setText(Pulsanti.MODIFICA_DIPENDENTE);
     	visualizzaDati(dipendente);
     	
		// CONFERMA
		btnConferma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String vecchioCF = dipendente.getCodiceFiscale();
					int vecchioIdResidenza = dipendente.getResidenza().getIdIndirizzo();
					int vecchioIdDomicilio = dipendente.getDomicilio().getIdIndirizzo();
					int vecchioIdTelefono = dipendente.getTelefono().getIdTelefono();
					Dipendente nuovoDipendente = verificaDati();
					DipendenteDB.modifica(nuovoDipendente, vecchioCF, vecchioIdResidenza, vecchioIdDomicilio, vecchioIdTelefono);
					PopupMessage.newNoticeMessage(Messaggi.UPDATE_NOTICE);	
					finestraPrincipale.resetPannelloPrincipale();
				} catch (CheckException e1) {
					PopupMessage.newErrorMessage(Messaggi.SPECIFIC_ERROR+e1.getMessage());					
				} catch (SQLException e2) {
					PopupMessage.newErrorMessage(Messaggi.UPDATE_ERROR);					
				} catch (IOException e3) {
					PopupMessage.newErrorMessage(Messaggi.FILE_CONNECTION_ERROR);					
				}
			}
		});
		
		// ANNULLA
		btnAnnulla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				finestraPrincipale.setPannelloPrincipale(new RicercaDipendente(finestraPrincipale));
			}
		});
		
	}  

	/**
	 * Imposta la residenza.
	 * @param indirizzo Indirizzo della residenza.
	 */
	public final void setResidenza(Indirizzo indirizzo) {
		lblIndirizzo1.setText(indirizzo.getIndirizzoCompleto());
		lblComune1.setText(indirizzo.getComuneCompleto());
		residenza = indirizzo;
		lblIndirizzo1.setForeground(Color.DARK_GRAY);
	}

	/**
	 * Imposta il domicilio.
	 * @param indirizzo Indirizzo del domicilio.
	 */
	public final void setDomicilio(Indirizzo indirizzo) {
		lblIndirizzo2.setText(indirizzo.getIndirizzoCompleto());
		lblComune2.setText(indirizzo.getComuneCompleto());
		domicilio = indirizzo;
	}

	/**
	 * Crea la grafica.                       
	 *
	 * @param finestraPrincipale
	 */
	protected final void creaGrafica(FinestraPrincipale finestraPrincipale) {
		super.creaGrafica(finestraPrincipale);

		JLabel lblNome = new JLabel(Dati.NOME);
		lblNome.setBounds(51, 18, 46, 14);
		panelCorpo.add(lblNome);

		JLabel lblCognome = new JLabel(Dati.COGNOME);
		lblCognome.setBounds(51, 43, 67, 14);
		panelCorpo.add(lblCognome);

		JLabel lblSesso = new JLabel(Dati.SESSO);
		lblSesso.setBounds(287, 68, 46, 14);
		panelCorpo.add(lblSesso);

		JLabel lblDataDiNascita = new JLabel(Dati.DATA_NASCITA);
		lblDataDiNascita.setBounds(51, 68, 101, 14);
		panelCorpo.add(lblDataDiNascita);

		JLabel lblDomicilio = new JLabel(Dati.DOMICILIO);
		lblDomicilio.setBounds(51, 168, 67, 14);
		panelCorpo.add(lblDomicilio);

		JLabel lblMansione = new JLabel(Dati.MANSIONE);
		lblMansione.setBounds(51, 218, 67, 14);
		panelCorpo.add(lblMansione);

		textNome = new JTextField();
		textNome.setBounds(163, 18, 208, 20);
		panelCorpo.add(textNome);
		textNome.setColumns(20);
		AbstractDocument documentNome = (AbstractDocument) textNome.getDocument();
		documentNome.setDocumentFilter(new SizeFilter(Dipendente.MAX_LENGHT));

		textCognome = new JTextField();
		textCognome.setBounds(163, 43, 208, 20);
		panelCorpo.add(textCognome);
		textCognome.setColumns(20);
		AbstractDocument documentCognome = (AbstractDocument) textCognome.getDocument();
		documentCognome.setDocumentFilter(new SizeFilter(Dipendente.MAX_LENGHT));

		comboSesso = new JComboBox<String>();
		comboSesso.setModel(new DefaultComboBoxModel<String>(new String[] { Testi.MASCHIO, Testi.FEMMINA }));
		comboSesso.setBounds(331, 68, 40, 20);
		panelCorpo.add(comboSesso);

		dateChooser = new JDateChooser();
		dateChooser.setBounds(163, 68, 114, 20);
		panelCorpo.add(dateChooser);

		textMansione = new JTextField();
		textMansione.setBounds(163, 218, 208, 20);
		panelCorpo.add(textMansione);
		textMansione.setColumns(30);
		AbstractDocument documentMansione = (AbstractDocument) textMansione.getDocument();
		documentMansione.setDocumentFilter(new SizeFilter(Dipendente.MAX_LENGHT));

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 11, 510, 1);
		panelCorpo.add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 388, 510, 1);
		panelCorpo.add(separator_1);

		JLabel lblResidenza = new JLabel(Dati.RESIDENZA);
		lblResidenza.setBounds(51, 117, 101, 14);
		panelCorpo.add(lblResidenza);

		JLabel lblIndirizzoEmail = new JLabel(Dati.MAIL);
		lblIndirizzoEmail.setBounds(51, 243, 101, 14);
		panelCorpo.add(lblIndirizzoEmail);

		textMail = new JTextField();
		textMail.setBounds(163, 243, 208, 20);
		panelCorpo.add(textMail);
		textMail.setColumns(10);
		AbstractDocument documentMail = (AbstractDocument) textMail.getDocument();
		documentMail.setDocumentFilter(new SizeFilter(Dipendente.MAX_LENGHT));

		JLabel lblNumeroCellulare = new JLabel(Dati.CELLULARE);
		lblNumeroCellulare.setBounds(51, 268, 101, 14);
		panelCorpo.add(lblNumeroCellulare);

		textCellulare = new JTextField();
		textCellulare.setBounds(163, 268, 208, 20);
		panelCorpo.add(textCellulare);
		textCellulare.setColumns(10);
		AbstractDocument documentCellulare = (AbstractDocument) textCellulare.getDocument();
		documentCellulare.setDocumentFilter(new LongFilter());

		JLabel lblNumeroCasa = new JLabel(Dati.CASA);
		lblNumeroCasa.setBounds(51, 293, 101, 14);
		panelCorpo.add(lblNumeroCasa);

		textCasa = new JTextField();
		textCasa.setBounds(163, 293, 208, 20);
		panelCorpo.add(textCasa);
		textCasa.setColumns(10);
		AbstractDocument documentCasa = (AbstractDocument) textCasa.getDocument();
		documentCasa.setDocumentFilter(new LongFilter());

		JLabel lblCodiceFiscale = new JLabel(Dati.CF);
		lblCodiceFiscale.setBounds(51, 93, 87, 14);
		panelCorpo.add(lblCodiceFiscale);

		textCodFiscale = new JTextField();
		textCodFiscale.setBounds(163, 93, 208, 20);
		panelCorpo.add(textCodFiscale);
		textCodFiscale.setColumns(10);
		AbstractDocument documentCF = (AbstractDocument) textCodFiscale.getDocument();
		documentCF.setDocumentFilter(new SizeFilter(Dipendente.CF_MAX_LENGHT));

		add(panelPulsanti, BorderLayout.SOUTH);
		add(panelCorpo, BorderLayout.CENTER);

		JLabel lblAssegnaAdUno = new JLabel(Testi.ASSEGNA_SPAZIO);
		lblAssegnaAdUno.setBounds(51, 318, 222, 14);
		panelCorpo.add(lblAssegnaAdUno);

		DefaultComboBoxModel<String> listaSpazi = new DefaultComboBoxModel<String>();

		try {
			spazi = SpazioDB.leggiSpazi();
			listaSpazi.addElement(Pulsanti.NESSUNO);
			int size = spazi.size();
			for (int i = 0; i < size; i++) {
				listaSpazi.addElement(spazi.get(i).getIdentificativoSpazio());
			}

		} catch (SQLException e) {
			PopupMessage.newErrorMessage(Messaggi.DB_CONNECTION_ERROR);					
		} catch (IOException e) {
			PopupMessage.newErrorMessage(Messaggi.FILE_CONNECTION_ERROR);					
		}

		comboSpazi = new JComboBox<String>();
		comboSpazi.setModel(listaSpazi);
		comboSpazi.setBounds(51, 339, 320, 20);
		panelCorpo.add(comboSpazi);

		lblIndirizzo1 = new JLabel(Messaggi.AVVISO_INDIRIZZO);
		lblIndirizzo1.setForeground(Color.RED);
		lblIndirizzo1.setBounds(163, 117, 170, 14);
		panelCorpo.add(lblIndirizzo1);

		lblComune1 = new JLabel("");
		lblComune1.setBounds(163, 142, 170, 14);
		panelCorpo.add(lblComune1);

		lblIndirizzo2 = new JLabel(Messaggi.STESSO_INDIRIZZO);
		lblIndirizzo2.setBounds(163, 168, 185, 14);
		panelCorpo.add(lblIndirizzo2);

		lblComune2 = new JLabel("");
		lblComune2.setBounds(163, 193, 170, 14);
		panelCorpo.add(lblComune2);

		btnAggiungi = new JButton(Pulsanti.AGGIUNGI);
		btnAggiungi.setBounds(375, 113, 89, 23);
		panelCorpo.add(btnAggiungi);

		JButton btnModifica = new JButton(Pulsanti.MODIFICA);
		btnModifica.setBounds(375, 164, 89, 23);
		panelCorpo.add(btnModifica);
		add(panelTitolo, BorderLayout.NORTH);


		// AGGIUNGI
		btnAggiungi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 GestoreIndirizzo finestra = 
						  new GestoreIndirizzo(finestraPrincipale, pannelloDipendente, true); 
					 finestra.setVisible(true);
					 finestraPrincipale.setEnabled(false);
			}
		});

		// MODIFICA
		btnModifica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 GestoreIndirizzo finestra = 
						 new GestoreIndirizzo(finestraPrincipale, pannelloDipendente, false); 
				 finestra.setVisible(true);
				 finestraPrincipale.setEnabled(false);	
			}
		});	
		
	}

	/**
	 * Verifica i dati inseriti.                         
	 *
	 * @return dipendente.
	 * @throws CheckException
	 * @throws SQLException
	 * @throws IOException
	 */
	private final Dipendente verificaDati() throws CheckException, SQLException, IOException {
		Dipendente dipendente = null;
		String codiceFiscale = textCodFiscale.getText();
		String nome = textNome.getText();
		String cognome = textCognome.getText();
		char sesso = comboSesso.getSelectedItem().toString().charAt(0);
		String dataNascita = Utility.dateToSql(dateChooser.getCalendar());
		String mansione = textMansione.getText();
		String numeroCellulare = textCellulare.getText();
		String numeroCasa = textCasa.getText();
		String indirizzoMail = textMail.getText();

		int idSpazio = -1;
		if (comboSpazi.getSelectedIndex() > 0) {
			idSpazio = spazi.get(comboSpazi.getSelectedIndex() - 1).getIdSpazio();
		}

		Utility.isNotEmpty(nome);
		Utility.isNotEmpty(cognome);
		Utility.isNotEmpty(dataNascita);
		Utility.isNotEmpty(codiceFiscale);
		Utility.isNotEmpty(mansione);
		Utility.isEmail(indirizzoMail);
		if (residenza == null){
			throw new CheckException();
		}		
		if (numeroCellulare.equals("") && numeroCasa.equals("")){
			throw new CheckException(Messaggi.UNSPECIFIED_PHONE);
		}
		if (domicilio == null){
			domicilio = residenza.copiaIndirizzo();
		}
		dipendente = new Dipendente(codiceFiscale, nome, cognome, sesso, dataNascita, indirizzoMail, mansione);		
		dipendente.setTelefono(new Telefono(numeroCellulare, numeroCasa));
		dipendente.setIdSpazio(idSpazio);
		dipendente.setResidenza(residenza);
		dipendente.setDomicilio(domicilio);
		
		return dipendente;
	}
	
	/**
	 * Verifica i dati del dipendente.                        
	 *
	 * @param dipendente.
	 */
	private final void visualizzaDati(Dipendente dipendente){
		textNome.setText(dipendente.getNome());	
		textCognome.setText(dipendente.getCognome());	
		textCodFiscale.setText(dipendente.getCodiceFiscale());
		textCasa.setText(dipendente.getTelefono().getNumeroCasa());	
		textCellulare.setText(dipendente.getTelefono().getNumeroCellulare());
		textMail.setText(dipendente.getIndirizzoMail());
		textMansione.setText(dipendente.getMansione());
		dateChooser.setDate(Utility.sqlToDate(dipendente.getDataNascita()));
		setResidenza(dipendente.getResidenza());
		setDomicilio(dipendente.getDomicilio());
		int selected = 0;
		int size = spazi.size();
		for(int i=0; i<size; i++){
			if(spazi.get(i).getIdSpazio()==dipendente.getIdSpazio()){
				selected = i+1;
			}
		}		
		comboSpazi.setSelectedIndex(selected);	
		btnAggiungi.setText(Pulsanti.MODIFICA);
	}
	
}
