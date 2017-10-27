package azienda.gui.ricerca;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.List;

import azienda.anagrafica.Dipendente;
import azienda.anagrafica.database.DipendenteDB;
import azienda.gui.FinestraPrincipale;
import azienda.gui.PopupMessage;
import azienda.gui.anagrafica.DettagliDipendente;
import azienda.utility.Dati;
import azienda.utility.Messaggi;
import azienda.utility.Pulsanti;
import azienda.utility.Testi;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

/**
 * @author Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version 1.0
 */
@SuppressWarnings("serial")
public class RicercaDipendente extends JPanel implements KeyListener {

	private JTextField textNome;
	private JTextField textCognome;
	private JTextField textCF;
	private JList<String> list;
	private List<Dipendente> dipendenti;
	private JScrollPane panelLista;
	private JTextField textMansione;

	/**
	 * Costruttore del pannello della ricerca dipendenti.
	 * 
	 * @param finestraPrincipale
	 *            Finestra della schermata principale.
	 */
	public RicercaDipendente(FinestraPrincipale finestraPrincipale) {

		setLayout(new BorderLayout(0, 0));

		JPanel panelTitolo = new JPanel();
		add(panelTitolo, BorderLayout.NORTH);

		int fontSize = 13;
		JLabel lblRicercaDipendente = new JLabel(Pulsanti.RICERCA_DIPENDENTE);
		lblRicercaDipendente.setFont(new Font(Testi.FONT, Font.BOLD, fontSize));
		panelTitolo.add(lblRicercaDipendente);

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
		gbl_panelCampi.columnWidths = new int[] { 143, 0, 0 };
		gbl_panelCampi.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_panelCampi.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_panelCampi.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		panelCampi.setLayout(gbl_panelCampi);

		JLabel lblNome = new JLabel(Dati.NOME);
		lblNome.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblNome = new GridBagConstraints();
		gbc_lblNome.insets = new Insets(0, 0, 5, 5);
		gbc_lblNome.anchor = GridBagConstraints.WEST;
		gbc_lblNome.gridx = 0;
		gbc_lblNome.gridy = 0;
		panelCampi.add(lblNome, gbc_lblNome);

		int colonne = 10;
		textNome = new JTextField();
		GridBagConstraints gbc_textNome = new GridBagConstraints();
		gbc_textNome.insets = new Insets(0, 0, 5, 0);
		gbc_textNome.fill = GridBagConstraints.HORIZONTAL;
		gbc_textNome.gridx = 1;
		gbc_textNome.gridy = 0;
		panelCampi.add(textNome, gbc_textNome);
		textNome.setColumns(colonne);

		JLabel lblCognome = new JLabel(Dati.COGNOME);
		lblCognome.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblCognome = new GridBagConstraints();
		gbc_lblCognome.anchor = GridBagConstraints.WEST;
		gbc_lblCognome.insets = new Insets(0, 0, 5, 5);
		gbc_lblCognome.gridx = 0;
		gbc_lblCognome.gridy = 1;
		panelCampi.add(lblCognome, gbc_lblCognome);

		textCognome = new JTextField();
		GridBagConstraints gbc_textCognome = new GridBagConstraints();
		gbc_textCognome.insets = new Insets(0, 0, 5, 0);
		gbc_textCognome.fill = GridBagConstraints.HORIZONTAL;
		gbc_textCognome.gridx = 1;
		gbc_textCognome.gridy = 1;
		panelCampi.add(textCognome, gbc_textCognome);
		textCognome.setColumns(colonne);

		JLabel lblCodiceFiscale = new JLabel(Dati.CF);
		lblCodiceFiscale.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblCodiceFiscale = new GridBagConstraints();
		gbc_lblCodiceFiscale.anchor = GridBagConstraints.WEST;
		gbc_lblCodiceFiscale.insets = new Insets(0, 0, 5, 5);
		gbc_lblCodiceFiscale.gridx = 0;
		gbc_lblCodiceFiscale.gridy = 2;
		panelCampi.add(lblCodiceFiscale, gbc_lblCodiceFiscale);

		textCF = new JTextField();
		GridBagConstraints gbc_textCF = new GridBagConstraints();
		gbc_textCF.insets = new Insets(0, 0, 5, 0);
		gbc_textCF.fill = GridBagConstraints.HORIZONTAL;
		gbc_textCF.gridx = 1;
		gbc_textCF.gridy = 2;
		panelCampi.add(textCF, gbc_textCF);
		textCF.setColumns(colonne);

		JLabel lblMansione = new JLabel(Dati.MANSIONE);
		GridBagConstraints gbc_lblMansione = new GridBagConstraints();
		gbc_lblMansione.anchor = GridBagConstraints.WEST;
		gbc_lblMansione.insets = new Insets(0, 0, 5, 5);
		gbc_lblMansione.gridx = 0;
		gbc_lblMansione.gridy = 3;
		panelCampi.add(lblMansione, gbc_lblMansione);

		textMansione = new JTextField();
		GridBagConstraints gbc_textMansione = new GridBagConstraints();
		gbc_textMansione.insets = new Insets(0, 0, 5, 0);
		gbc_textMansione.fill = GridBagConstraints.HORIZONTAL;
		gbc_textMansione.gridx = 1;
		gbc_textMansione.gridy = 3;
		panelCampi.add(textMansione, gbc_textMansione);
		textMansione.setColumns(colonne);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.EAST;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.VERTICAL;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 4;
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
				textNome.setText("");
				textCognome.setText("");
				textCF.setText("");
				textMansione.setText("");
				creaLista();
			}
		});

		// MODIFICA
		btnVisualizza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Dipendente dipendente = dipendenti.get(list.getSelectedIndex());
				DettagliDipendente pannello = new DettagliDipendente(finestraPrincipale, dipendente);
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
						DipendenteDB.elimina(dipendenti.get(list.getSelectedIndex()));
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

		textNome.addKeyListener(this);
		textCognome.addKeyListener(this);
		textCF.addKeyListener(this);
		textMansione.addKeyListener(this);

		panelLista = new JScrollPane();
		panelLista.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panelLista.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panelCorpo.add(panelLista, BorderLayout.CENTER);

		creaLista();
	}

	/**
	 * Crea la lista dei dipendenti
	 */
	private final void creaLista() {
		DefaultListModel<String> listModel = new DefaultListModel<String>();

		try {
			dipendenti = DipendenteDB.ricercaDipendenti(textNome.getText(), textCognome.getText(), textCF.getText(),
					textMansione.getText());
			int size = dipendenti.size();
			for (int i = 0; i < size; i++) {
				listModel.addElement(dipendenti.get(i).getIdentificativoDipendente());
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
