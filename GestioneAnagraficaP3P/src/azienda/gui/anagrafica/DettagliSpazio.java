package azienda.gui.anagrafica;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import azienda.anagrafica.Spazio;
import azienda.anagrafica.database.SpazioDB;
import azienda.eccezioni.CheckException;
import azienda.gui.*;
import azienda.gui.filtri.DoubleFilter;
import azienda.gui.filtri.IntFilter;
import azienda.gui.filtri.SizeFilter;
import azienda.gui.ricerca.RicercaSpazio;
import azienda.utility.*;

/**
 * @author      Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version     1.0
 */
@SuppressWarnings("serial")
public class DettagliSpazio extends DettagliAnagrafica {
	private JTextField textNome;
	private JTextField textUbicazione;
	private JTextField textNroFinestre;
	private JTextField textNroPorte;
	private JTextField textSuperficie;

	/**
	 * Costruttore del pannello di creazione dello spazio.
	 * 
	 * @param finestraPrincipale Finestra della schermata principale.
	 */
	public DettagliSpazio(FinestraPrincipale finestraPrincipale) {
		creaGrafica(finestraPrincipale);
		lblTitolo.setText(Pulsanti.NUOVO_SPAZIO);
		
		btnConferma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				try{
					Spazio spazio = verificaDati();
					SpazioDB.crea(spazio);
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
	 * Costruttore del pannello di modifica dello spazio.
	 * 
	 * @param finestraPrincipale Finestra della schermata principale.
	 * @param spazio Spazio da modificare.
	 */
	public DettagliSpazio(FinestraPrincipale finestraPrincipale, Spazio spazio) {
		creaGrafica(finestraPrincipale);
     	lblTitolo.setText(Pulsanti.MODIFICA_SPAZIO);
     	visualizzaDati(spazio);
		
		btnConferma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				try{
					int idSpazio = spazio.getIdSpazio();
					Spazio nuovoSpazio = verificaDati();
					SpazioDB.modifica(nuovoSpazio, idSpazio);
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
				finestraPrincipale.setPannelloPrincipale(new RicercaSpazio(finestraPrincipale));
			}
		});
	}
	
	/**
	 * Crea la grafica.                      
	 *
	 * @param finestraPrincipale.
	 */
	protected final void creaGrafica(FinestraPrincipale finestraPrincipale) {
		super.creaGrafica(finestraPrincipale);
		
		JLabel lblNome = new JLabel(Dati.NOME);
		lblNome.setBounds(70, 23, 46, 14);
		panelCorpo.add(lblNome);
		
		JLabel lblUbicazione = new JLabel(Dati.UBICAZIONE);
		lblUbicazione.setBounds(70, 47, 78, 14);
		panelCorpo.add(lblUbicazione);
		
		JLabel lblNumeroFinestre = new JLabel(Dati.NRO_FINESTRE);
		lblNumeroFinestre.setBounds(70, 72, 101, 14);
		panelCorpo.add(lblNumeroFinestre);
		
		JLabel lblNumeroPorte = new JLabel(Dati.NRO_PORTE);
		lblNumeroPorte.setBounds(70, 97, 101, 14);
		panelCorpo.add(lblNumeroPorte);
		
		JLabel lblSuperficie = new JLabel(Dati.SUPERFICIE);
		lblSuperficie.setBounds(70, 122, 67, 14);
		panelCorpo.add(lblSuperficie);
		
		textNome = new JTextField();
		textNome.setBounds(184, 20, 166, 20);
		panelCorpo.add(textNome);
		textNome.setColumns(20);
		AbstractDocument documentNome = (AbstractDocument) textNome.getDocument();
		documentNome.setDocumentFilter(new SizeFilter(Spazio.MAX_LENGHT));

		textNroPorte = new JTextField();
		textNroPorte.setBounds(184, 94, 166, 20);
		panelCorpo.add(textNroPorte);
		textNroPorte.setColumns(30);
		AbstractDocument documentPorte = (AbstractDocument) textNroPorte.getDocument();
		documentPorte.setDocumentFilter(new IntFilter());
		
		textSuperficie = new JTextField();
		textSuperficie.setBounds(184, 119, 166, 20);
		panelCorpo.add(textSuperficie);
		textSuperficie.setColumns(30);	
		AbstractDocument documentSuperficie = (AbstractDocument) textSuperficie.getDocument();
		documentSuperficie.setDocumentFilter(new DoubleFilter());
		
		textUbicazione = new JTextField();
		textUbicazione.setBounds(184, 44, 166, 20);
		panelCorpo.add(textUbicazione);
		textUbicazione.setColumns(10);
		AbstractDocument documentUbicazione = (AbstractDocument) textUbicazione.getDocument();
		documentUbicazione.setDocumentFilter(new SizeFilter(Spazio.MAX_LENGHT));
		
		textNroFinestre = new JTextField();
		textNroFinestre.setBounds(184, 69, 166, 20);
		panelCorpo.add(textNroFinestre);
		textNroFinestre.setColumns(10);
		AbstractDocument documentFinestre = (AbstractDocument) textNroFinestre.getDocument();
		documentFinestre.setDocumentFilter(new IntFilter());
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 11, 571, 2);
		panelCorpo.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 150, 533, 2);
		panelCorpo.add(separator_1);
		
		add(panelPulsanti, BorderLayout.SOUTH);
		add(panelTitolo, BorderLayout.NORTH);
		add(panelCorpo, BorderLayout.CENTER);
	}
	
	/**
	 * Verifica i dati inseriti.                         
	 *
	 * @return spazio.
	 * @throws CheckException
	 * @throws SQLException
	 * @throws IOException
	 */
	private final Spazio verificaDati() throws CheckException, SQLException, IOException {
		String nome = textNome.getText();
		String ubicazione = textUbicazione.getText();
		int nroFinestre;
		int nroPorte;
		double superficie;
		
		Utility.isNotEmpty(nome);
		Utility.isNotEmpty(ubicazione);
		Utility.isNotEmpty(textNroFinestre.getText());
		Utility.isNotEmpty(textNroPorte.getText());
		Utility.isNotEmpty(textSuperficie.getText());
		
		nroFinestre = Integer.parseInt(textNroFinestre.getText());
		nroPorte = Integer.parseInt(textNroPorte.getText());
		superficie = Float.parseFloat(textSuperficie.getText());
		Spazio spazio = new Spazio(nome, ubicazione, nroFinestre, nroPorte, superficie);
		return spazio;
	}
	
	/**
	 * Visualizza i dati inseriti.                         
	 *
	 * @param spazio.
	 */
	private final void visualizzaDati(Spazio spazio){
		textNome.setText(spazio.getNome());	
		textUbicazione.setText(spazio.getUbicazione());	
		textNroPorte.setText(String.valueOf(spazio.getNroPorte()));
		textNroFinestre.setText(String.valueOf(spazio.getNroFinestre()));
		textSuperficie.setText(String.valueOf(spazio.getSuperficie()));
	}	
}

