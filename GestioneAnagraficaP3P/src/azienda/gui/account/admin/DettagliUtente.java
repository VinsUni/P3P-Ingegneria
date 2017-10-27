package azienda.gui.account.admin;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import azienda.sicurezza.GestoreUtenti;
import azienda.sicurezza.Utente;
import azienda.utility.Dati;
import azienda.utility.Messaggi;
import azienda.utility.Pulsanti;
import azienda.utility.Risorse;
import azienda.utility.Testi;

/**
 * @author      Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version     1.0
 */
@SuppressWarnings("serial")
public class DettagliUtente extends JFrame implements WindowListener{

	private JTextField textUsername;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JButton btnConferma;
	private GestoreUtenti gestoreUtenti;
	private GestioneAccount finestra;
	private String usernameGestore;
	
	/**
	 * Costruttore del pannello di creazione dell'utente.
	 * 
	 * @param gestoreUtenti Oggetto della classe GestoreUtenti
	 * @param finestra Oggetto della classe GestioneAccount
	 */
	public DettagliUtente(GestoreUtenti gestoreUtenti, GestioneAccount finestra) {

		this.gestoreUtenti = gestoreUtenti;
		this.finestra = finestra;
			
		creaGrafica();
		setTitle(Testi.CREA_USR);

		btnConferma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confermaCreazione();
			}
		});	
	}
	
	/**
	 * Costruttore del pannello di modifica dell'utente.
	 * 
	 * @param gestoreUtenti Oggetto della classe GestoreUtenti
	 * @param finestra Oggetto della classe GestioneAccount
	 * @param username Username dell'utente
	 */
	public DettagliUtente(GestoreUtenti gestoreUtenti, GestioneAccount finestra, String username) {
		this(gestoreUtenti, finestra);
		this.usernameGestore = username;
		
		setTitle(Testi.MODIFICA_USR);
		textUsername.setText(username);
		if(username.equals(gestoreUtenti.getUsernameGestore())){
			textUsername.setEditable(false);
		}
		btnConferma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confermaModifica();		
			}
		});
	}

	/**
	 * Crea l'utente.                        
	 */
	private final void confermaCreazione(){
		String username = textUsername.getText();
		String password = String.valueOf(passwordField.getPassword());
		
		if(username.equals("") || username.length()>Utente.MAX_USER_LENGTH){
			JOptionPane.showMessageDialog(null, Messaggi.USR_LENGHT_ERROR, Messaggi.ERROR, JOptionPane.ERROR_MESSAGE);
		} else if (password.length()<Utente.MIN_PASS_LENGTH || password.length()>Utente.MAX_PASS_LENGTH){
			JOptionPane.showMessageDialog(null, Messaggi.PW_LENGHT_ERROR, Messaggi.ERROR, JOptionPane.ERROR_MESSAGE);
		} else {
			if(password.equals(String.valueOf(passwordField_1.getPassword()))){
				try {
					gestoreUtenti.creaUtente(username, password);
					JOptionPane.showMessageDialog(null, Messaggi.INSERT_NOTICE, Messaggi.NOTICE, JOptionPane.INFORMATION_MESSAGE);
					finestra.creaLista(gestoreUtenti);
					finestra.setEnabled(true);
					dispose();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, Messaggi.INSERT_ERROR, Messaggi.ERROR, JOptionPane.ERROR_MESSAGE);
				} catch (IOException e2) {
					JOptionPane.showMessageDialog(null, Messaggi.FILE_CONNECTION_ERROR, Messaggi.ERROR, JOptionPane.ERROR_MESSAGE);		
				}
			}
			else {
				JOptionPane.showMessageDialog(null, Messaggi.DIFFERENT_PW_ERROR, Messaggi.ERROR, JOptionPane.ERROR_MESSAGE);
			}		
		}
	}
	
	/**
	 * Modifica l'utente.                   
	 */
	private final void confermaModifica(){
		String nuovoUsername = textUsername.getText();
		String password = String.valueOf(passwordField.getPassword());
		
		if(usernameGestore.equals("") || usernameGestore.length()>Utente.MAX_USER_LENGTH){
			JOptionPane.showMessageDialog(null, Messaggi.USR_LENGHT_ERROR, Messaggi.ERROR, JOptionPane.ERROR_MESSAGE);
		} else if (password.length()<Utente.MIN_PASS_LENGTH || password.length()>Utente.MAX_PASS_LENGTH){
			JOptionPane.showMessageDialog(null, Messaggi.PW_LENGHT_ERROR, Messaggi.ERROR, JOptionPane.ERROR_MESSAGE);
		} else {
			if(password.equals(String.valueOf(passwordField_1.getPassword()))){
				try {
					gestoreUtenti.modificaUtente(usernameGestore, nuovoUsername, password);
					JOptionPane.showMessageDialog(null, Messaggi.UPDATE_NOTICE, Messaggi.NOTICE, JOptionPane.INFORMATION_MESSAGE);
					finestra.creaLista(gestoreUtenti);
					finestra.setEnabled(true);
					dispose();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, Messaggi.UPDATE_ERROR, Messaggi.ERROR, JOptionPane.ERROR_MESSAGE);
				} catch (IOException e2) {
					JOptionPane.showMessageDialog(null, Messaggi.FILE_CONNECTION_ERROR, Messaggi.ERROR, JOptionPane.ERROR_MESSAGE);		
				}
			}
			else {
				JOptionPane.showMessageDialog(null, Messaggi.DIFFERENT_PW_ERROR, Messaggi.ERROR, JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	/**
	 * Crea la grafica
	 */
	private final void creaGrafica(){
		setIconImage(Toolkit.getDefaultToolkit().getImage(DettagliUtente.class.getResource(Risorse.SETTINGS)));
		addWindowListener(this);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 338, 167);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsername = new JLabel(Dati.USR);
		lblUsername.setBounds(10, 11, 82, 14);
		contentPane.add(lblUsername);
		
		textUsername = new JTextField();
		textUsername.setBounds(150, 5, 172, 20);
		contentPane.add(textUsername);
		textUsername.setColumns(10);
		
		JLabel lblPassword = new JLabel(Dati.PW);
		lblPassword.setBounds(10, 36, 153, 14);
		contentPane.add(lblPassword);
		
		JLabel lblConfermaPassword = new JLabel(Testi.CONFERMA_PW);
		lblConfermaPassword.setBounds(10, 61, 125, 14);
		contentPane.add(lblConfermaPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(150, 30, 172, 20);
		contentPane.add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(150, 55, 172, 20);
		contentPane.add(passwordField_1);
		
		btnConferma = new JButton(Pulsanti.CONFERMA);
		btnConferma.setBounds(221, 104, 101, 23);
		contentPane.add(btnConferma);
		
		JButton btnAnnulla = new JButton(Pulsanti.ANNULLA);
		btnAnnulla.setBounds(122, 104, 89, 23);
		contentPane.add(btnAnnulla);
		
		JLabel lblNewLabel = new JLabel(Testi.LUNGHEZZA_PW);
		lblNewLabel.setBounds(10, 86, 312, 14);
		contentPane.add(lblNewLabel);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int w = getSize().width;
        int h = getSize().height;
        int x = (dim.width-w)/2;
        int y = (dim.height-h)/2;
        setLocation(x, y);
        
		btnAnnulla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				finestra.creaLista(gestoreUtenti);
				finestra.setEnabled(true);
				dispose();
			}
		});
	}

	/**
	 * Non usato
	 */
	@Override
	public void windowActivated(WindowEvent arg0) {		
	}
	/**
	 * Non usato
	 */
	@Override
	public void windowClosed(WindowEvent arg0) {		
	}
	
	/**
	 * Chiusura finestra.
	 */
	@Override
	public void windowClosing(WindowEvent arg0) {
		finestra.creaLista(gestoreUtenti);
		finestra.setEnabled(true);
		dispose();
	}

	/**
	 * Non usato
	 */
	@Override
	public void windowDeactivated(WindowEvent arg0) {
		
	}
	
	/**
	 * Non usato
	 */
	@Override
	public void windowDeiconified(WindowEvent arg0) {
		
	}

	/**
	 * Non usato
	 */
	@Override
	public void windowIconified(WindowEvent arg0) {
		
	}
	
	/**
	 * Non usato
	 */
	@Override
	public void windowOpened(WindowEvent arg0) {
		
	}
	
}
