package azienda.gui.anagrafica;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.filechooser.FileNameExtensionFilter;

import azienda.Scheda;
import azienda.gui.FinestraPrincipale;
import azienda.gui.PopupMessage;
import azienda.utility.Messaggi;
import azienda.utility.Pulsanti;
import azienda.utility.Testi;
import azienda.utility.Utility;

/**
 * @author      Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version     1.0
 */
@SuppressWarnings("serial")
public class GestoreScheda  extends JPanel {
	private JCheckBox chckbxDipedenti;
	private JCheckBox chckbxSpazi;
	private JCheckBox chckbxStrumentazioni;
	private JCheckBox chckbxOccupazione;
	private JCheckBox chckbxUso;
	private boolean[] selected;
	
	/**
	 * Costruttore del pannello di creazione della scheda.
	 * 
	 * @param finestraPrincipale Finestra della schermata principale.
	 */
	public GestoreScheda(FinestraPrincipale FinestraPrincipale){
		setLayout(new BorderLayout(0, 0));
				
		selected = new boolean[5];
		
		JPanel panelPulsanti = new JPanel();
		
		JButton btnAnnulla = new JButton(Pulsanti.ANNULLA);
		panelPulsanti.add(btnAnnulla);
		
		JButton btnConferma = new JButton(Pulsanti.CONFERMA);
		panelPulsanti.add(btnConferma);
		
		JPanel panelTitolo = new JPanel();
		
		JLabel lblCreaScheda = new JLabel(Pulsanti.CREA_SCHEDA);
		int fontSize = 16;
		lblCreaScheda.setFont(new Font(Testi.FONT, Font.BOLD, fontSize));
		panelTitolo.add(lblCreaScheda);
		
		JPanel panelCorpo = new JPanel();
		
		panelCorpo.setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 11, 510, 1);
		panelCorpo.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 388, 510, 1);
		panelCorpo.add(separator_1);
		
		chckbxDipedenti = new JCheckBox(Testi.ANA_DIPENDENTE);
		chckbxDipedenti.setBounds(112, 50, 147, 23);
		panelCorpo.add(chckbxDipedenti);
		
		chckbxSpazi = new JCheckBox(Testi.ANA_SPAZIO);
		chckbxSpazi.setBounds(112, 76, 128, 23);
		panelCorpo.add(chckbxSpazi);
		
		chckbxStrumentazioni = new JCheckBox(Testi.ANA_STRUMENTAZIONE);
		chckbxStrumentazioni.setBounds(112, 102, 179, 23);
		panelCorpo.add(chckbxStrumentazioni);
		
		JLabel lblIntestazione = new JLabel(Testi.SELEZIONA_INFO);
		lblIntestazione.setBounds(76, 23, 356, 14);
		panelCorpo.add(lblIntestazione);
		
		chckbxOccupazione = new JCheckBox(Testi.OCCUPAZIONE);
		chckbxOccupazione.setBounds(112, 128, 179, 23);
		panelCorpo.add(chckbxOccupazione);
		
		chckbxUso = new JCheckBox(Testi.USO_STRUMENTAZIONE);
		chckbxUso.setBounds(112, 154, 215, 23);
		panelCorpo.add(chckbxUso);
		
		JButton btnSelezionaTutto = new JButton(Pulsanti.SELEZIONA_TUTTO);

		btnSelezionaTutto.setBounds(112, 184, 128, 23);
		panelCorpo.add(btnSelezionaTutto);
		
		JButton btnDeselezionaTutto = new JButton(Pulsanti.DESELEZIONA_TUTTO);

		btnDeselezionaTutto.setBounds(250, 184, 147, 23);
		panelCorpo.add(btnDeselezionaTutto);
		
		add(panelPulsanti, BorderLayout.SOUTH);
		add(panelTitolo, BorderLayout.NORTH);
		add(panelCorpo, BorderLayout.CENTER);
		
		btnSelezionaTutto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chckbxDipedenti.setSelected(true);
				chckbxSpazi.setSelected(true);
				chckbxStrumentazioni.setSelected(true);
				chckbxOccupazione.setSelected(true);
				chckbxUso.setSelected(true);
			}
		});
		
		btnDeselezionaTutto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chckbxDipedenti.setSelected(false);
				chckbxSpazi.setSelected(false);
				chckbxStrumentazioni.setSelected(false);
				chckbxOccupazione.setSelected(false);
				chckbxUso.setSelected(false);
			}
		});
		
		btnAnnulla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FinestraPrincipale.resetPannelloPrincipale();
			}
		});
		
		btnConferma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				selected[0] = chckbxDipedenti.isSelected();
				selected[1] = chckbxSpazi.isSelected();
				selected[2] = chckbxStrumentazioni.isSelected();
				selected[3] = chckbxOccupazione.isSelected();
				selected[4] = chckbxUso.isSelected();
				File selectedFile = null;
				if(isSelected()){
					Scheda scheda;
					JFileChooser fileChooser = getFileChooser();	
					if(fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
						selectedFile = fileChooser.getSelectedFile();
						String absolutePath = selectedFile.getAbsolutePath();
						FileNameExtensionFilter fileNameExtensionFilter = (FileNameExtensionFilter) fileChooser.getFileFilter();
						String selectedExtension = fileNameExtensionFilter.getExtensions()[0];

						if (absolutePath.contains(Testi.PUNTO)) {
							selectedFile = new File(absolutePath.substring(0, absolutePath.indexOf(Testi.PUNTO) + 1) + selectedExtension);
						} else {
							selectedFile = new File(absolutePath + Testi.PUNTO + selectedExtension);
						}
						
						try {
							scheda = new Scheda();
							scheda.creaScheda(selected, selectedFile);
							
							PopupMessage.newNoticeMessage(Messaggi.CREATED_FORM_NOTICE);
							FinestraPrincipale.resetPannelloPrincipale();
						} catch (SQLException dbException) {
							PopupMessage.newErrorMessage(Messaggi.DB_CONNECTION_ERROR);
						} catch (Exception genericException) {
							PopupMessage.newErrorMessage(Messaggi.CREATION_FORM_ERROR);
						} 
					}		

				}else{
					PopupMessage.newErrorMessage(Messaggi.UNSELECTED_FIELDS);
				}
			}
		});
	}
	
	/**
	 * Restituisce il selettore del percorso
	 * @return JFileChooser
	 */
	private final JFileChooser getFileChooser(){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.removeChoosableFileFilter(fileChooser.getFileFilter());
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(Testi.SELEZIONA_FORMATO, Testi.FORMATO));
	    FileNameExtensionFilter extensionFile = (FileNameExtensionFilter) fileChooser.getFileFilter();
	    fileChooser.setSelectedFile(new File(Testi.NOME_SCHEDA+Utility.getDateHyphenFormat()+Testi.PUNTO + extensionFile.getExtensions()[0]));
		
	    fileChooser.addPropertyChangeListener(JFileChooser.FILE_FILTER_CHANGED_PROPERTY, new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent pcEvt) {
				JFileChooser ch = (JFileChooser) pcEvt.getSource();

				FileNameExtensionFilter fileNameExtensionFilter = (FileNameExtensionFilter) pcEvt.getNewValue();
				ch.setSelectedFile(new File(Testi.NOME_SCHEDA+Utility.getDateHyphenFormat()+Testi.PUNTO + fileNameExtensionFilter.getExtensions()[0]));
			}
		});
	    return fileChooser;
	}
	
	/**
	 * Verifica se almeno un campo è selezionato
	 * @return isSelected
	 */
	private final boolean isSelected(){
		boolean isSelected = false;
		for(int i=0; i<selected.length; i++){
			if(selected[i]){
				isSelected = true;
			}
		}
		return isSelected;
	}
}
