package azienda.gui;
import java.awt.*;
import javax.swing.border.EmptyBorder;

import azienda.gui.account.ConfermaPassword;
import azienda.gui.account.admin.GestioneAccount;
import azienda.gui.anagrafica.DettagliDipendente;
import azienda.gui.anagrafica.DettagliSpazio;
import azienda.gui.anagrafica.DettagliStrumentario;
import azienda.gui.anagrafica.GestoreScheda;
import azienda.gui.ricerca.*;
import azienda.sicurezza.Utente;
import azienda.utility.Messaggi;
import azienda.utility.Pulsanti;
import azienda.utility.Risorse;
import azienda.utility.Testi;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * @author      Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version     1.0
 */
@SuppressWarnings("serial")
public class FinestraPrincipale extends JFrame {

	private JSplitPane splitPane;

	/**
	 * Costruttore della finestra principale.
	 * 
	 * @param utente Utente autenticato dal sistema.
	 */
	public FinestraPrincipale(Utente utente) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int n = JOptionPane.showConfirmDialog(null, Messaggi.EXIT, "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				
				if(n==JOptionPane.YES_OPTION){
					dispose();
				}			
			}
		});
		
		FinestraPrincipale finestra = this;
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(FinestraPrincipale.class.getResource(Risorse.BUSINESS)));

		setResizable(false);

		setTitle(Testi.GESTIONE_ANAGRAFICA);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		setBounds(100, 100, 676, 508);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));

		splitPane = new JSplitPane();
		splitPane.setEnabled(false);
		splitPane.setContinuousLayout(true);
		contentPane.add(splitPane);

		JPanel panel = new JPanel();
		splitPane.setLeftComponent(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setEnabled(false);
		splitPane_1.setContinuousLayout(true);
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		panel.add(splitPane_1);

		JPanel panel_2 = new JPanel();
		splitPane_1.setLeftComponent(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));

		JLabel lblImage = new JLabel("");

		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblImage.setIcon(new ImageIcon(FinestraPrincipale.class.getResource(Risorse.USR_PROFILE)));
		panel_2.add(lblImage, BorderLayout.NORTH);

		JLabel lblUsername = new JLabel(utente.getUsername());
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(lblUsername, BorderLayout.SOUTH);

		JPanel panel_3 = new JPanel();

		splitPane_1.setRightComponent(panel_3);
		panel_3.setLayout(new GridLayout(0, 1, 0, 0));

		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setEnabled(false);
		splitPane_2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		panel_3.add(splitPane_2);

		JPanel panel_1 = new JPanel();
		splitPane_2.setLeftComponent(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));

		JButton btnCreaScheda = new JButton(Pulsanti.CREA_SCHEDA);
		panel_1.add(btnCreaScheda);

		// CREA SCHEDA
		btnCreaScheda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GestoreScheda pannello = new GestoreScheda(finestra);
				setPannelloPrincipale(pannello);
			}
		});

		JButton btnAggiungiAnagrafica = new JButton(Pulsanti.AGGIUNGI_ANAGRAFICA);
		panel_1.add(btnAggiungiAnagrafica);

		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(btnAggiungiAnagrafica, popupMenu);

		JMenuItem mntmNuovoDipendente = new JMenuItem(Pulsanti.NUOVO_DIPENDENTE);
		mntmNuovoDipendente.setIcon(new ImageIcon(FinestraPrincipale.class.getResource(Risorse.EMPLOYEE)));

		popupMenu.add(mntmNuovoDipendente);

		JMenuItem mntmNuovaStrumentazione = new JMenuItem(Pulsanti.NUOVA_STRUMENTAZIONE);
		mntmNuovaStrumentazione
				.setIcon(new ImageIcon(FinestraPrincipale.class.getResource(Risorse.INSTRUMENT)));

		popupMenu.add(mntmNuovaStrumentazione);

		JMenuItem mntmNuovoSpazio = new JMenuItem(Pulsanti.NUOVO_SPAZIO);
		mntmNuovoSpazio.setIcon(new ImageIcon(FinestraPrincipale.class.getResource(Risorse.SPACE)));

		popupMenu.add(mntmNuovoSpazio);

		// AGGIUNGI ANAGRAFICA
		btnAggiungiAnagrafica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int x = btnAggiungiAnagrafica.getWidth() / 2;
				int y = btnAggiungiAnagrafica.getHeight() / 2;
				popupMenu.show(btnAggiungiAnagrafica, x, y);
			}
		});

		// NUOVO DIPENDENTE
		mntmNuovoDipendente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setPannelloPrincipale(new DettagliDipendente(finestra));	
			}
		});

		// NUOVA STRUMENTAZIONE
		mntmNuovaStrumentazione.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setPannelloPrincipale(new DettagliStrumentario(finestra));	
			}
		});

		// NUOVO SPAZIO
		mntmNuovoSpazio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setPannelloPrincipale(new DettagliSpazio(finestra));	
			}
		});

		JButton btnRicercaAnagrafica = new JButton(Pulsanti.RICERCA_ANAGRAFICA);
		panel_1.add(btnRicercaAnagrafica);

		JPopupMenu popupMenu_1 = new JPopupMenu();
		addPopup(btnRicercaAnagrafica, popupMenu_1);

		JMenuItem mntmRicercaDipendente = new JMenuItem(Pulsanti.RICERCA_DIPENDENTE);
		mntmRicercaDipendente.setIcon(new ImageIcon(FinestraPrincipale.class.getResource(Risorse.EMPLOYEE)));
		popupMenu_1.add(mntmRicercaDipendente);

		JMenuItem mntmRicercaStrumentazione = new JMenuItem(Pulsanti.RICERCA_STRUMENTAZIONE);
		mntmRicercaStrumentazione
				.setIcon(new ImageIcon(FinestraPrincipale.class.getResource(Risorse.INSTRUMENT)));
		popupMenu_1.add(mntmRicercaStrumentazione);

		JMenuItem mntmRicercaSpazio = new JMenuItem(Pulsanti.RICERCA_SPAZIO);
		mntmRicercaSpazio.setIcon(new ImageIcon(FinestraPrincipale.class.getResource(Risorse.SPACE)));
		popupMenu_1.add(mntmRicercaSpazio);

		// RICERCA ANAGRAFICA
		btnRicercaAnagrafica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int x = btnRicercaAnagrafica.getWidth() / 2;
				int y = btnRicercaAnagrafica.getHeight() / 2;
				popupMenu_1.show(btnRicercaAnagrafica, x, y);
			}
		});

		// RICERCA DIPENDENTE
		mntmRicercaDipendente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setPannelloPrincipale(new RicercaDipendente(finestra));	
			}
		});

		// RICERCA STRUMENTAZIONE
		mntmRicercaStrumentazione.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setPannelloPrincipale(new RicercaStrumentario(finestra));	
			}
		});

		// RICERCA SPAZIO
		mntmRicercaSpazio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setPannelloPrincipale(new RicercaSpazio(finestra));	
			}
		});

		JButton btnModifcaPassword = new JButton(Pulsanti.MODIFICA_PW);
		panel_1.add(btnModifcaPassword);

		// MODIFICA PASSWORD
		btnModifcaPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConfermaPassword finestraPassword = new ConfermaPassword(utente, finestra);
				finestraPassword.setVisible(true);
				setEnabled(false);
			}
		});


		JButton btnGestione = new JButton(Pulsanti.GESTIONE_ACCOUNT);
		if (utente.isAdmin()) {
			panel_1.add(btnGestione);
		}
		
		JButton btnEsci = new JButton(Pulsanti.LOGOUT);
		panel_1.add(btnEsci);

		// ESCI
		btnEsci.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showConfirmDialog(null, Messaggi.EXIT, "",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (n == JOptionPane.YES_OPTION) {
					Login finestraLogin = new Login();
					finestraLogin.setVisible(true);
					finestra.dispose();
				}
			}
		});

		JPanel panel_4 = new JPanel();
		splitPane_2.setRightComponent(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));

		lblImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				resetPannelloPrincipale();
			}
		});


		// GESTIONE ACCOUNT
		btnGestione.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GestioneAccount finestraGestioneAccount = new GestioneAccount(utente, finestra);
				finestraGestioneAccount.setVisible(true);
				setEnabled(false);
			}
		});

		resetPannelloPrincipale();

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = getSize().width;
		int h = getSize().height;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;
		setLocation(x, y);
	}

	/**
	 * Modifica il pannello principale.
	 * 
	 * @param panel Nuovo pannello da sostituire al pannello principale.
	 */
	public final void setPannelloPrincipale(JPanel panel){
		splitPane.setRightComponent(panel);	
	}
	/**
	 * Resetta il pannello pricipale.
	 */
	public final void resetPannelloPrincipale(){
		splitPane.setRightComponent(Home.getHome());	
	}
	
	/**
	 * Non usato
	 */
	private static void addPopup(Component component, final JPopupMenu popup) {
	}
}
