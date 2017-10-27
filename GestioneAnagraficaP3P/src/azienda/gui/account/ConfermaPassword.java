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
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author      Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version     1.0
 */
@SuppressWarnings("serial")
public class ConfermaPassword extends JFrame{

	private JPasswordField passwordField;
	private Utente utente;
	private FinestraPrincipale finestra;
	
	/**
	 * Create the frame.
	 */
	public ConfermaPassword(Utente utente, FinestraPrincipale finestra) {
		this.finestra = finestra;
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				finestra.setEnabled(true);
				dispose();
			}
		});
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(ConfermaPassword.class.getResource(Risorse.SETTINGS)));
		this.utente = utente;
		
		setTitle(Testi.CONFERMA_PW);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 271, 133);
		JPanel contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLabel = new JLabel(Testi.VECCHIA_PW);
		lblLabel.setBounds(10, 11, 245, 14);
		contentPane.add(lblLabel);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(10, 36, 245, 20);
		contentPane.add(passwordField);
		
		JButton btnConferma = new JButton(Pulsanti.CONFERMA);
		btnConferma.setBounds(82, 67, 97, 23);
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
		
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10){
					conferma();
				}
			}
		});
		
		
	}

	/**
	 * Evento del pulsante conferma.                       
	 */
	private final void conferma(){
		String password = String.valueOf(passwordField.getPassword());
		if(utente.verificaPassword(password)){
			ModificaPassword modificaPassword = new ModificaPassword(utente, password, finestra);
			modificaPassword.setVisible(true);
			dispose();
		}else{
			JOptionPane.showMessageDialog(null, Messaggi.WRONG_PW, Messaggi.ERROR, JOptionPane.ERROR_MESSAGE);
		}
	}

}
