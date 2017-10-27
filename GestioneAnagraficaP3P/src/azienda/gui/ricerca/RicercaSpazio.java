package azienda.gui.ricerca;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import azienda.anagrafica.Spazio;
import azienda.anagrafica.database.SpazioDB;
import azienda.gui.FinestraPrincipale;
import azienda.gui.PopupMessage;
import azienda.gui.anagrafica.DettagliSpazio;
import azienda.utility.Dati;
import azienda.utility.Messaggi;
import azienda.utility.Pulsanti;
import azienda.utility.Testi;

/**
 * @author      Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version     1.0
 */
@SuppressWarnings("serial")
public class RicercaSpazio extends JPanel implements KeyListener{

	private JTextField textNome;
	private JTextField textModello;
	private JList<String> list;
    private List<Spazio> spazi;
    private JScrollPane panelLista;
    private JTextField textID;

	/**
	 * Costruttore del pannello della ricerca spazi.
	 * 
	 * @param finestraPrincipale Finestra della schermata principale.
	 */
	public RicercaSpazio(FinestraPrincipale finestraPrincipale) {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panelTitolo = new JPanel();
		add(panelTitolo, BorderLayout.NORTH);
		
		JLabel lblRicercaSpazio = new JLabel(Pulsanti.RICERCA_SPAZIO);
		int fontSize = 13;
		lblRicercaSpazio.setFont(new Font(Testi.FONT, Font.BOLD, fontSize));
		panelTitolo.add(lblRicercaSpazio);
		
		JPanel panelPulsanti = new JPanel();
		add(panelPulsanti, BorderLayout.SOUTH);
		
		JButton btnVisualizza = new JButton(Pulsanti.VISUALIZZA);
		panelPulsanti.add(btnVisualizza);
		
		JButton btnElimina = new JButton(Pulsanti.ELIMINA);
		panelPulsanti.add(btnElimina);
		
		JButton btnAnnulla = new JButton(Pulsanti.ANNULLA);
		panelPulsanti.add(btnAnnulla);
		
		JPanel panelCorpo = new JPanel();
		add(panelCorpo, BorderLayout.CENTER);
		panelCorpo.setLayout(new BorderLayout(0, 0));
		
		JPanel panelCampi = new JPanel();
		panelCorpo.add(panelCampi, BorderLayout.NORTH);
		GridBagLayout gbl_panelCampi = new GridBagLayout();
		gbl_panelCampi.columnWidths = new int[]{117, 0, 0};
		gbl_panelCampi.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panelCampi.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panelCampi.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panelCampi.setLayout(gbl_panelCampi);
		
		JLabel lblIdSpazio = new JLabel(Dati.ID_SPAZIO);
		GridBagConstraints gbc_lblIdSpazio = new GridBagConstraints();
		gbc_lblIdSpazio.anchor = GridBagConstraints.WEST;
		gbc_lblIdSpazio.insets = new Insets(0, 0, 5, 5);
		gbc_lblIdSpazio.gridx = 0;
		gbc_lblIdSpazio.gridy = 0;
		panelCampi.add(lblIdSpazio, gbc_lblIdSpazio);
		
		int colonne = 10;
		textID = new JTextField();
		GridBagConstraints gbc_textID = new GridBagConstraints();
		gbc_textID.insets = new Insets(0, 0, 5, 0);
		gbc_textID.fill = GridBagConstraints.HORIZONTAL;
		gbc_textID.gridx = 1;
		gbc_textID.gridy = 0;
		panelCampi.add(textID, gbc_textID);
		textID.setColumns(colonne);
		
		JLabel lblNome = new JLabel(Dati.NOME);
		lblNome.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblNome = new GridBagConstraints();
		gbc_lblNome.insets = new Insets(0, 0, 5, 5);
		gbc_lblNome.anchor = GridBagConstraints.WEST;
		gbc_lblNome.gridx = 0;
		gbc_lblNome.gridy = 1;
		panelCampi.add(lblNome, gbc_lblNome);
		
		textNome = new JTextField();
		GridBagConstraints gbc_textNome = new GridBagConstraints();
		gbc_textNome.insets = new Insets(0, 0, 5, 0);
		gbc_textNome.fill = GridBagConstraints.HORIZONTAL;
		gbc_textNome.gridx = 1;
		gbc_textNome.gridy = 1;
		panelCampi.add(textNome, gbc_textNome);
		textNome.setColumns(colonne);
		
		JLabel lblUbicazione = new JLabel(Dati.UBICAZIONE);
		lblUbicazione.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblUbicazione = new GridBagConstraints();
		gbc_lblUbicazione.anchor = GridBagConstraints.WEST;
		gbc_lblUbicazione.insets = new Insets(0, 0, 5, 5);
		gbc_lblUbicazione.gridx = 0;
		gbc_lblUbicazione.gridy = 2;
		panelCampi.add(lblUbicazione, gbc_lblUbicazione);
		
		textModello = new JTextField();
		GridBagConstraints gbc_textModello = new GridBagConstraints();
		gbc_textModello.insets = new Insets(0, 0, 5, 0);
		gbc_textModello.fill = GridBagConstraints.HORIZONTAL;
		gbc_textModello.gridx = 1;
		gbc_textModello.gridy = 2;
		panelCampi.add(textModello, gbc_textModello);
		textModello.setColumns(colonne);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.EAST;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.VERTICAL;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 3;
		panelCampi.add(panel, gbc_panel);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JButton btnReset = new JButton(Pulsanti.RESET);
		panel.add(btnReset);
		
		JButton btnCerca = new JButton(Pulsanti.CERCA);
		panel.add(btnCerca);
		
		// CERCA
		btnCerca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				creaLista();
			}
		});
		
		// REST
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textNome.setText("");
				textModello.setText("");
				textID.setText("");
				creaLista();
			}
		});
		
		// MODIFICA
		btnVisualizza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Spazio spazio = spazi.get(list.getSelectedIndex());
				DettagliSpazio pannello = new DettagliSpazio(finestraPrincipale, spazio);
				finestraPrincipale.setPannelloPrincipale(pannello);	
			}
		});
		
		// ELIMINA
		btnElimina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showConfirmDialog(null, Messaggi.DELETE_WARNING, Messaggi.NOTICE, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				
				if(n==JOptionPane.YES_OPTION){
					try {						
						SpazioDB.elimina(spazi.get(list.getSelectedIndex()));
						PopupMessage.newNoticeMessage(Messaggi.DELETE_NOTICE);
						creaLista();
					} catch (SQLException e1) {
						PopupMessage.newErrorMessage(Messaggi.DELETE_ERROR);
					} catch (IOException e2) {
						PopupMessage.newErrorMessage(Messaggi.FILE_CONNECTION_ERROR);
					}
				}
			}
		});
		
		// ANNULLA
		btnAnnulla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				finestraPrincipale.resetPannelloPrincipale();
			}
		});
		
		textID.addKeyListener(this);
		textNome.addKeyListener(this);
		textModello.addKeyListener(this);
		
		
		panelLista = new JScrollPane();
		panelLista.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panelLista.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panelCorpo.add(panelLista, BorderLayout.CENTER);
		
		creaLista();
	}

	/**
	 * Crea la lista degli spazi
	 */
	private final void creaLista(){
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		
		try {
			spazi = SpazioDB.ricercaSpazi(textID.getText(), textNome.getText(), textModello.getText());
			int size = spazi.size();
			for(int i=0; i<size; i++){
				listModel.addElement(spazi.get(i).getIdentificativoSpazio());
			}
			
		} catch (SQLException e) {
			PopupMessage.newErrorMessage(Messaggi.DB_CONNECTION_ERROR);
		} catch (IOException e) {
			PopupMessage.newErrorMessage(Messaggi.FILE_CONNECTION_ERROR);
		}
		
		list = new JList<String>(listModel);
		list.setFont(new Font(Testi.FONT, Font.PLAIN, 14));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		panelLista.setViewportView(list);
		
		panelLista.revalidate();
	}

	/**
	 * Evento della pressione del tasto invio.
	 * 
	 * @param arg0 Evento.
	 */
	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyCode()==10){
			creaLista();
		}
	}

	/**
	 * Non usato
	 */
	@Override
	public void keyReleased(KeyEvent e) {	}

	/**
	 * Non usato
	 */
	@Override
	public void keyTyped(KeyEvent e) {	}
	
}
