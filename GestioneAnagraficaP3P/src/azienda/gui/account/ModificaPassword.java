package azienda.gui.account;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import azienda.gui.FinestraPrincipale;
import azienda.sicurezza.Utente;
import azienda.utility.Messaggi;
import azienda.utility.Pulsanti;
import azienda.utility.Risorse;
import azienda.utility.Testi;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.Color;

/**
 * @author      Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version     1.0
 */
@SuppressWarnings("serial")
public class ModificaPassword extends JFrame implements KeyListener{

	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private Utente utente;
	private String password;
	private FinestraPrincipale finestra;

	/**
	 * Costruttore della finestra di modifica della password.
	 * 
	 * @parma utente Utente che desidera modificare la password.
	 * @param password Password attuale dell'utete.
	 * @param finestra Finestra pricipale.
	 */
	public ModificaPassword(Utente utente, String password, FinestraPrincipale finestra) {
		this.finestra = finestra;
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				finestra.setEnabled(true);
				dispose();
			}
		});
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(ModificaPassword.class.getResource(Risorse.SETTINGS)));
		this.utente = utente;
		this.password = password;
		
		setTitle(Pulsanti.MODIFICA_PW);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 321, 184);
		JPanel contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblInserisciLaNuova = new JLabel(Testi.INSERISCI_PW);
		lblInserisciLaNuova.setBounds(10, 11, 315, 14);
		contentPane.add(lblInserisciLaNuova);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(10, 36, 295, 20);
		contentPane.add(passwordField);
		
		JLabel lblConfermaPassword = new JLabel(Testi.CONFERMA_PW);
		lblConfermaPassword.setBounds(10, 67, 245, 14);
		contentPane.add(lblConfermaPassword);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(10, 92, 295, 20);
		contentPane.add(passwordField_1);
		
		JButton btnConferma = new JButton(Pulsanti.CONFERMA);
		btnConferma.setBounds(110, 123, 97, 23);
		contentPane.add(btnConferma);
		
		setResizable(false);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int w = getSize().width;
        int h = getSize().height;
        int x = (dim.width-w)/2;
        int y = (dim.height-h)/2;
        setLocation(x, y);
        
		btnConferma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				conferma();
			}
		});
		
		passwordField.addKeyListener(this);
		passwordField_1.addKeyListener(this);
	}
	
	/**
	 * Evento del pulsante conferma.                        
	 */
	private final void conferma(){
		String nuovaPassword = String.valueOf(passwordField.getPassword());
		if(nuovaPassword.length()<Utente.MIN_PASS_LENGTH || nuovaPassword.length()>Utente.MAX_PASS_LENGTH){
			JOptionPane.showMessageDialog(null, Messaggi.PW_LENGHT_ERROR, Messaggi.ERROR, JOptionPane.ERROR_MESSAGE);
		} else {
			if(nuovaPassword.equals(String.valueOf(passwordField_1.getPassword()))){			
				try {
					utente.modificaPassword(password, nuovaPassword);
					JOptionPane.showMessageDialog(null, Messaggi.PW_MODIFIED, Messaggi.NOTICE, JOptionPane.INFORMATION_MESSAGE);
					finestra.setEnabled(true);
					dispose();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, Messaggi.PW_MOD_ERROR, Messaggi.ERROR, JOptionPane.ERROR_MESSAGE);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, Messaggi.FILE_CONNECTION_ERROR, Messaggi.ERROR, JOptionPane.ERROR_MESSAGE);		
				}			
			}
			else {
				JOptionPane.showMessageDialog(null, Messaggi.DIFFERENT_PW_ERROR, Messaggi.ERROR, JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Evento della pressione del tasto invio.
	 * 
	 * @parma arg0 Evento del pulsante.
	 */
	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyCode()==10){
			conferma();
		}
	}

	/**
	 * Non usato.
	 */
	@Override
	public void keyReleased(KeyEvent arg0) {
		
	}

	/**
	 * Non usato.
	 */
	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

}
