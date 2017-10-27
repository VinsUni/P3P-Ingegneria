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

import azienda.anagrafica.Strumentazione;
import azienda.anagrafica.database.StrumentazioneDB;
import azienda.gui.FinestraPrincipale;
import azienda.gui.PopupMessage;
import azienda.gui.anagrafica.DettagliStrumentario;
import azienda.utility.Dati;
import azienda.utility.Messaggi;
import azienda.utility.Pulsanti;
import azienda.utility.Testi;

/**
 * @author Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version 1.0
 */
@SuppressWarnings("serial")
public class RicercaStrumentario extends JPanel implements KeyListener {

	private JTextField textNome;
	private JTextField textModello;
	private JTextField textMarca;
	private JList<String> list;
	private List<Strumentazione> strumentazioni;
	private JScrollPane panelLista;
	private JTextField textTipologia;
	private JTextField textID;

	/**
	 * Costruttore del pannello della ricerca strumentazioni.
	 * 
	 * @param finestraPrincipale
	 *            Finestra della schermata principale.
	 */
	public RicercaStrumentario(FinestraPrincipale finestraPrincipale) {
		setLayout(new BorderLayout(0, 0));

		JPanel panelTitolo = new JPanel();
		add(panelTitolo, BorderLayout.NORTH);

		JLabel lblRicercaStrumentazione = new JLabel(Pulsanti.RICERCA_STRUMENTAZIONE);
		int fontSize = 13;
		lblRicercaStrumentazione.setFont(new Font(Testi.FONT, Font.BOLD, fontSize));
		panelTitolo.add(lblRicercaStrumentazione);

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
		gbl_panelCampi.columnWidths = new int[] { 134, 0, 0 };
		gbl_panelCampi.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panelCampi.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_panelCampi.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		panelCampi.setLayout(gbl_panelCampi);

		JLabel lblIdStrumentazione = new JLabel(Dati.ID_STRUMENTAZIONE);
		GridBagConstraints gbc_lblIdStrumentazione = new GridBagConstraints();
		gbc_lblIdStrumentazione.anchor = GridBagConstraints.WEST;
		gbc_lblIdStrumentazione.insets = new Insets(0, 0, 5, 5);
		gbc_lblIdStrumentazione.gridx = 0;
		gbc_lblIdStrumentazione.gridy = 0;
		panelCampi.add(lblIdStrumentazione, gbc_lblIdStrumentazione);

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

		JLabel lblModello = new JLabel(Dati.MODELLO);
		lblModello.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblModello = new GridBagConstraints();
		gbc_lblModello.anchor = GridBagConstraints.WEST;
		gbc_lblModello.insets = new Insets(0, 0, 5, 5);
		gbc_lblModello.gridx = 0;
		gbc_lblModello.gridy = 2;
		panelCampi.add(lblModello, gbc_lblModello);

		textModello = new JTextField();
		GridBagConstraints gbc_textModello = new GridBagConstraints();
		gbc_textModello.insets = new Insets(0, 0, 5, 0);
		gbc_textModello.fill = GridBagConstraints.HORIZONTAL;
		gbc_textModello.gridx = 1;
		gbc_textModello.gridy = 2;
		panelCampi.add(textModello, gbc_textModello);
		textModello.setColumns(colonne);

		JLabel lblMarca = new JLabel(Dati.MARCA);
		lblMarca.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblMarca = new GridBagConstraints();
		gbc_lblMarca.anchor = GridBagConstraints.WEST;
		gbc_lblMarca.insets = new Insets(0, 0, 5, 5);
		gbc_lblMarca.gridx = 0;
		gbc_lblMarca.gridy = 3;
		panelCampi.add(lblMarca, gbc_lblMarca);

		textMarca = new JTextField();
		GridBagConstraints gbc_textMarca = new GridBagConstraints();
		gbc_textMarca.insets = new Insets(0, 0, 5, 0);
		gbc_textMarca.fill = GridBagConstraints.HORIZONTAL;
		gbc_textMarca.gridx = 1;
		gbc_textMarca.gridy = 3;
		panelCampi.add(textMarca, gbc_textMarca);
		textMarca.setColumns(colonne);

		JLabel lblTipologia = new JLabel(Dati.TIPOLOGIA);
		GridBagConstraints gbc_lblTipologia = new GridBagConstraints();
		gbc_lblTipologia.anchor = GridBagConstraints.WEST;
		gbc_lblTipologia.insets = new Insets(0, 0, 5, 5);
		gbc_lblTipologia.gridx = 0;
		gbc_lblTipologia.gridy = 4;
		panelCampi.add(lblTipologia, gbc_lblTipologia);

		textTipologia = new JTextField();
		GridBagConstraints gbc_textTipologia = new GridBagConstraints();
		gbc_textTipologia.insets = new Insets(0, 0, 5, 0);
		gbc_textTipologia.fill = GridBagConstraints.HORIZONTAL;
		gbc_textTipologia.gridx = 1;
		gbc_textTipologia.gridy = 4;
		panelCampi.add(textTipologia, gbc_textTipologia);
		textTipologia.setColumns(colonne);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.EAST;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.VERTICAL;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 5;
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

		// RESET
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textID.setText("");
				textNome.setText("");
				textModello.setText("");
				textMarca.setText("");
				textTipologia.setText("");
				creaLista();
			}
		});

		// MODIFICA
		btnVisualizza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Strumentazione strumentazione = strumentazioni.get(list.getSelectedIndex());
				DettagliStrumentario pannello = new DettagliStrumentario(finestraPrincipale, strumentazione);
				finestraPrincipale.setPannelloPrincipale(pannello);
			}
		});

		// ELIMINA
		btnElimina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showConfirmDialog(null, Messaggi.DELETE_WARNING, Messaggi.NOTICE,
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (n == JOptionPane.YES_OPTION) {
					try {
						StrumentazioneDB.elimina(strumentazioni.get(list.getSelectedIndex()));
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
		textMarca.addKeyListener(this);
		textTipologia.addKeyListener(this);

		panelLista = new JScrollPane();
		panelLista.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panelLista.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panelCorpo.add(panelLista, BorderLayout.CENTER);

		creaLista();
	}

	/**
	 * Crea la lista delle strumentazioni
	 */
	private final void creaLista() {
		DefaultListModel<String> listModel = new DefaultListModel<String>();

		try {
			strumentazioni = StrumentazioneDB.ricercaStrumentazioni(textID.getText(), textNome.getText(),
					textModello.getText(), textMarca.getText(), textTipologia.getText());
			int size = strumentazioni.size();
			for (int i = 0; i < size; i++) {
				listModel.addElement(strumentazioni.get(i).getIdentificativoStrumentazione());
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
	 * @param arg0
	 *            Evento.
	 */
	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == 10) {
			creaLista();
		}
	}

	/**
	 * Non usato
	 */
	@Override
	public void keyReleased(KeyEvent e) {
	}

	/**
	 * Non usato
	 */
	@Override
	public void keyTyped(KeyEvent e) {
	}

}
