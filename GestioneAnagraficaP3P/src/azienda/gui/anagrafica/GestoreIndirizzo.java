package azienda.gui.anagrafica;

import java.awt.*;
import java.sql.SQLException;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;

import azienda.anagrafica.Indirizzo;
import azienda.anagrafica.database.IndirizzoDB;
import azienda.eccezioni.CheckException;
import azienda.gui.FinestraPrincipale;
import azienda.gui.PopupMessage;
import azienda.gui.filtri.IntFilter;
import azienda.gui.filtri.SizeFilter;
import azienda.utility.*;
import java.util.List;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.awt.event.ActionEvent;

/**
 * @author      Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version     1.0
 */
@SuppressWarnings("serial")
public class GestoreIndirizzo extends JFrame {
	private JTextField textIndirizzo;
	private JTextField textCivico;
    private JComboBox<String> comboRegione;
	private JComboBox<String> comboProvincia;
	private JComboBox<String> comboComune;
	private Indirizzo indirizzoDipendente;
	private JButton btnConferma;
	private JButton btnAnnulla;
	
	/**
	 * Create the frame.
	 * @wbp.parser.constructor
	 */
	public GestoreIndirizzo(FinestraPrincipale finestraPrincipale, DettagliDipendente pannelloDipendente, boolean tipo) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				finestraPrincipale.setEnabled(true);
				dispose();
			}
		});
		creaGrafica();
		
		btnConferma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(getIndirizzo()){						
					if(tipo==true){
						pannelloDipendente.setResidenza(indirizzoDipendente);
					}
					else {
						pannelloDipendente.setDomicilio(indirizzoDipendente);
					}
					
					finestraPrincipale.setEnabled(true);
					dispose();
				}
			}
		});	
		
		btnAnnulla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				finestraPrincipale.setEnabled(true);
				dispose();
			}
		});
	}
	
	/**
	 * Crea la grafica.                        
	 *
	 */
	private final void creaGrafica(){
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(GestoreIndirizzo.class.getResource(Risorse.USR)));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 327, 227);
		JPanel contentPane = new JPanel();
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		comboRegione = new JComboBox<String>();
		comboRegione.setBackground(Color.WHITE);
		creaListaRegioni();
		comboRegione.setBounds(10, 27, 217, 20);
		contentPane.add(comboRegione);
		
		comboProvincia = new JComboBox<String>();
		comboProvincia.setBackground(Color.WHITE);
		creaListaProvince();
		comboProvincia.setBounds(237, 27, 60, 20);
		contentPane.add(comboProvincia);
		
		JLabel lblRegione = new JLabel(Dati.REGIONE);
		lblRegione.setBounds(10, 11, 78, 14);
		contentPane.add(lblRegione);
		
		JLabel lblProvincia = new JLabel(Dati.PROVINCIA);
		lblProvincia.setBounds(238, 11, 107, 14);
		contentPane.add(lblProvincia);
		
		comboComune = new JComboBox<String>();
		comboComune.setBackground(Color.WHITE);
		creaListaComuni();
		comboComune.setBounds(10, 75, 287, 20);
		contentPane.add(comboComune);
		
		JLabel lblIndirizzo = new JLabel(Dati.INDIRIZZO);
		lblIndirizzo.setBounds(10, 106, 78, 14);
		contentPane.add(lblIndirizzo);
		
		JLabel lblComune = new JLabel(Dati.COMUNE);
		lblComune.setBounds(10, 58, 78, 14);
		contentPane.add(lblComune);
		
		int colonne = 10;
		textIndirizzo = new JTextField();
		textIndirizzo.setBounds(10, 125, 217, 20);
		contentPane.add(textIndirizzo);
		textIndirizzo.setColumns(colonne);	
		AbstractDocument document = (AbstractDocument) textIndirizzo.getDocument();
		document.setDocumentFilter(new SizeFilter(Indirizzo.INDIRIZZO_MAX_LENGHT));
		
		JLabel lblNCivico = new JLabel(Dati.N_CIVICO);
		lblNCivico.setBounds(237, 106, 79, 14);
		contentPane.add(lblNCivico);
		
		textCivico = new JTextField();
		textCivico.setBounds(237, 125, 60, 20);
		contentPane.add(textCivico);
		textCivico.setColumns(colonne);
		AbstractDocument document1 = (AbstractDocument) textCivico.getDocument();
		document1.setDocumentFilter(new IntFilter());
		
		btnConferma = new JButton(Pulsanti.CONFERMA);
		btnConferma.setBounds(198, 156, 99, 23);
		contentPane.add(btnConferma);
		
		btnAnnulla = new JButton(Pulsanti.ANNULLA);
		btnAnnulla.setBounds(105, 156, 89, 23);
		contentPane.add(btnAnnulla);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int w = getSize().width;
        int h = getSize().height;
        int x = (dim.width-w)/2;
        int y = (dim.height-h)/2;
        setLocation(x, y);
        
		comboRegione.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				creaListaProvince();		
				creaListaComuni();
			}
		});
		
		comboProvincia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				creaListaComuni();
			}
		});     

	}
	
	/**
	 * Crea la lista delle regioni.                         
	 */
	private final void creaListaRegioni(){
		DefaultComboBoxModel<String> listaRegioni = new DefaultComboBoxModel<String>();
		List<String> regioni = null;
		try {
			regioni = IndirizzoDB.leggiRegioni();
			int size = regioni.size();
			for(int i=0; i<size; i++){
				listaRegioni.addElement(regioni.get(i));
			}
			
		} catch (SQLException e) {
			PopupMessage.newErrorMessage(Messaggi.DB_CONNECTION_ERROR);
		} catch (IOException e) {
			PopupMessage.newErrorMessage(Messaggi.FILE_CONNECTION_ERROR);
		}
		comboRegione.setModel(listaRegioni);

	}
	
	/**
	 * Crea la lista delle province.                         
	 */
	private final void creaListaProvince(){
		String regione = comboRegione.getSelectedItem().toString();
		DefaultComboBoxModel<String> listaProvince = new DefaultComboBoxModel<String>();
		List<String> province = null;
		try {
			province = IndirizzoDB.leggiProvince(regione);
			int size = province.size();
			for(int i=0; i<size; i++){
				listaProvince.addElement(province.get(i));
			}
			
		} catch (SQLException e1) {
			PopupMessage.newErrorMessage(Messaggi.DB_CONNECTION_ERROR);
		} catch (IOException e) {
			PopupMessage.newErrorMessage(Messaggi.FILE_CONNECTION_ERROR);
		}
		comboProvincia.setModel(listaProvince);
	}
	
	/**
	 * Crea la lista dei comuni.                         
	 */
	private final void creaListaComuni(){
		String regione = comboRegione.getSelectedItem().toString();
		String provincia = comboProvincia.getSelectedItem().toString();
		DefaultComboBoxModel<String> listaComuni = new DefaultComboBoxModel<String>();
		List<String> comuni = null;
		try {
			comuni = IndirizzoDB.leggiComuni(regione, provincia);
			int size = comuni.size();
			for(int i=0; i<size; i++){
				listaComuni.addElement(comuni.get(i));
			}
			
		} catch (SQLException e1) {
			PopupMessage.newErrorMessage(Messaggi.DB_CONNECTION_ERROR);
		} catch (IOException e) {
			PopupMessage.newErrorMessage(Messaggi.FILE_CONNECTION_ERROR);
		}
		comboComune.setModel(listaComuni);
	}

	/**
	 * Legge l'indirizzo
	 * 
	 * @return corretto
	 */
	private final boolean getIndirizzo(){
		boolean corretto = true;
		String indirizzo = textIndirizzo.getText();
		int numeroCivico;
			
		try{
			Utility.isNotEmpty(indirizzo);
			Utility.isNotEmpty(textCivico.getText());
			
			numeroCivico = Integer.parseInt(textCivico.getText());
			String comune = comboComune.getSelectedItem().toString();
			String provincia = comboProvincia.getSelectedItem().toString();
			String regione = comboRegione.getSelectedItem().toString();
		
			indirizzoDipendente = new Indirizzo(indirizzo, numeroCivico, comune, provincia, regione);
		}catch(CheckException e1){
			PopupMessage.newErrorMessage(Messaggi.SPECIFIC_ERROR+e1.getMessage());
			corretto =  false;
		}catch (SQLException e2) {
			PopupMessage.newErrorMessage(Messaggi.DB_CONNECTION_ERROR);
			corretto = false;
		} catch (IOException e) {
			PopupMessage.newErrorMessage(Messaggi.FILE_CONNECTION_ERROR);
			corretto = false;
		}
		return corretto;
	}
	
}
