package azienda.gui.account.admin;

import java.awt.Dimension;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import azienda.eccezioni.AdminException;
import azienda.gui.FinestraPrincipale;
import azienda.sicurezza.GestoreUtenti;
import azienda.sicurezza.Utente;
import azienda.utility.Messaggi;
import azienda.utility.Pulsanti;
import azienda.utility.Risorse;
import azienda.utility.Testi;

import java.awt.Toolkit;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

/**
 * @author Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version 1.0
 */
@SuppressWarnings("serial")
public class GestioneAccount extends JFrame {
	private JPanel contentPane;
	private JList<String> listaAccount;
	private JScrollPane panelLista;
	private TreeMap<String, Boolean> utenti = null;

	/**
	 * Costruttore del pannello di gestione degli account.
	 * 
	 * @parma utente Utente con permessi da amministratore.
	 * @param finestraPrincipale
	 *            Finestra della schermata principale.
	 */
	public GestioneAccount(Utente utente, FinestraPrincipale finestraPrincipale) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				finestraPrincipale.setEnabled(true);
				dispose();
			}
		});

		try {
			GestoreUtenti gestoreUtenti = GestoreUtenti.nuovoGestoreUtente(utente);

			GestioneAccount finestra = this;
			setResizable(false);
			setIconImage(Toolkit.getDefaultToolkit().getImage(GestioneAccount.class.getResource(Risorse.SETTINGS)));
			setTitle(Pulsanti.GESTIONE_ACCOUNT);
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			setBounds(100, 100, 422, 262);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(new BorderLayout(0, 0));

			JPanel panelTitolo = new JPanel();
			contentPane.add(panelTitolo, BorderLayout.NORTH);
			panelTitolo.setLayout(new BorderLayout(0, 0));

			JLabel lblTitolo = new JLabel(Testi.LISTA_ACCOUNT);
			lblTitolo.setHorizontalAlignment(SwingConstants.LEFT);
			panelTitolo.add(lblTitolo);

			JPanel panelPulsanti = new JPanel();
			contentPane.add(panelPulsanti, BorderLayout.SOUTH);

			JButton btnNuovo = new JButton(Pulsanti.NUOVO);
			btnNuovo.setHorizontalAlignment(SwingConstants.RIGHT);
			panelPulsanti.add(btnNuovo);

			JButton btnModifica = new JButton(Pulsanti.MODIFICA);
			btnModifica.setHorizontalAlignment(SwingConstants.RIGHT);
			panelPulsanti.add(btnModifica);

			JButton btnElimina = new JButton(Pulsanti.ELIMINA);
			btnElimina.setHorizontalAlignment(SwingConstants.RIGHT);
			panelPulsanti.add(btnElimina);

			panelLista = new JScrollPane();

			creaLista(gestoreUtenti);

			// NUOVO
			btnNuovo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					DettagliUtente finestraNuovoUtente = new DettagliUtente(gestoreUtenti, finestra);
					finestraNuovoUtente.setVisible(true);
					setEnabled(false);
				}
			});

			// MODIFICA
			btnModifica.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DettagliUtente finestraModificaUtente = new DettagliUtente(gestoreUtenti, finestra,
							listaAccount.getSelectedValue());
					finestraModificaUtente.setVisible(true);
					setEnabled(false);
				}
			});

			// ELIMINA
			btnElimina.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int n = JOptionPane.showConfirmDialog(null, Messaggi.DELETE_USR_WARNING, Messaggi.NOTICE,
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (n == JOptionPane.YES_OPTION) {
						try {
							if (gestoreUtenti.eliminaUtente(listaAccount.getSelectedValue())) {
								JOptionPane.showMessageDialog(null, Messaggi.DELETE_NOTICE, Messaggi.NOTICE,
										JOptionPane.INFORMATION_MESSAGE);
								creaLista(gestoreUtenti);
							} else {
								JOptionPane.showMessageDialog(null, Messaggi.DELETE_ADMIN_WARNING, Messaggi.ERROR,
										JOptionPane.ERROR_MESSAGE);
							}
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(null, Messaggi.DELETE_ERROR, Messaggi.ERROR,
									JOptionPane.ERROR_MESSAGE);
						} catch (IOException e2) {
							JOptionPane.showMessageDialog(null, Messaggi.FILE_CONNECTION_ERROR, Messaggi.ERROR,
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			});

			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			int w = getSize().width;
			int h = getSize().height;
			int x = (dim.width - w) / 2;
			int y = (dim.height - h) / 2;
			setLocation(x, y);

		} catch (AdminException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), Messaggi.ERROR, JOptionPane.ERROR_MESSAGE);
			dispose();
		}

	}

	/**
	 * Metodo che crea la lista degli account utente nel pannello di gestione
	 * degli account.
	 * 
	 * @parma gestureUtenti Oggetto della classe GestoreUtenti.
	 */
	public final void creaLista(GestoreUtenti gestoreUtenti) {
		panelLista.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panelLista.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		DefaultListModel<String> listModel = new DefaultListModel<String>();

		try {
			utenti = gestoreUtenti.leggiUtenti();

			for (Map.Entry<String, Boolean> entry : utenti.entrySet()) {
				listModel.addElement(entry.getKey());
			}

			listaAccount = new JList<String>(listModel);
			int fontSize = 14;
			listaAccount.setFont(new Font(Testi.FONT, Font.PLAIN, fontSize));
			listaAccount.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			listaAccount.setSelectedIndex(0);
			panelLista.setViewportView(listaAccount);

			listaAccount.setCellRenderer(new DefaultListCellRenderer() {
				private static final long serialVersionUID = 1L;

				@Override
				public Component getListCellRendererComponent(JList<?> list, Object value, int index,
						boolean isSelected, boolean cellHasFocus) {
					Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

					boolean admin = utenti.get(value.toString());
					if (admin) {
						setBackground(Color.RED);
					}
					return c;
				}

			});

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Messaggi.DB_CONNECTION_ERROR, Messaggi.ERROR,
					JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, Messaggi.FILE_CONNECTION_ERROR, Messaggi.ERROR,
					JOptionPane.ERROR_MESSAGE);
		}

		contentPane.add(panelLista, BorderLayout.CENTER);

		panelLista.revalidate();
	}
}
