package azienda.gui;

import java.awt.*;
import javax.swing.*;

import azienda.sicurezza.Utente;
import azienda.utility.Dati;
import azienda.utility.Messaggi;
import azienda.utility.Risorse;
import azienda.utility.Testi;

import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author      Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version     1.0
 */
@SuppressWarnings("serial")
public class Login extends JFrame implements ActionListener, KeyListener{

	private JTextField userText;
	private JPasswordField passwordText;

	/**
	 * Costruttore della finestra di login.
	 */
	public Login(){
		int xSize = 300;
		int ySize = 150;
		int textSize = 20;
		setIconImage(Toolkit.getDefaultToolkit().getImage(FinestraPrincipale.class.getResource(Risorse.USR)));
		setSize(xSize, ySize);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(Testi.LOGIN);
		setResizable(false);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int w = getSize().width;
        int h = getSize().height;
        int x = (dim.width-w)/2;
        int y = (dim.height-h)/2;
        setLocation(x, y);
        
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.WHITE);
		
		JLabel userLabel = new JLabel(Dati.USR);
		userLabel.setBounds(10, 10, 80, 25);
		panel.add(userLabel);

		userText = new JTextField(textSize);
		userText.setBounds(100, 10, 160, 25);
		panel.add(userText);

		JLabel passwordLabel = new JLabel(Dati.PW);
		passwordLabel.setBounds(10, 40, 80, 25);
		panel.add(passwordLabel);

		passwordText = new JPasswordField(textSize);
		passwordText.setBounds(100, 40, 160, 25);
		panel.add(passwordText);

		JButton loginButton = new JButton(Testi.ACCEDI);
		loginButton.setBounds(110, 80, 80, 25);
		loginButton.addActionListener(this);
		panel.add(loginButton);
		
		userText.addKeyListener(this);
		passwordText.addKeyListener(this);
		
		add(panel);
	}

	/**
	 * Effettua il login nel sistema
	 */
	private final void effettuaLogin(){
		try {
			Utente utente = Utente.verificaUtente(userText.getText(), String.valueOf(passwordText.getPassword()));
			if(utente!=null){
				this.dispose();
				FinestraPrincipale finestra = new FinestraPrincipale(utente);
				finestra.setVisible(true);
			}else{
				PopupMessage.newErrorMessage(Messaggi.LOGIN_ERROR);
			}	
		
		} catch (SQLException e) {
			PopupMessage.newErrorMessage(Messaggi.DB_CONNECTION_ERROR);
		} catch (IOException e) {
			PopupMessage.newErrorMessage(Messaggi.FILE_CONNECTION_ERROR);
		}
		
	}
	
	/**
	 * Evento del pulsante accedi.
	 * @param e Evento.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals(Testi.ACCEDI)){
			effettuaLogin();
		}	
	}

	/**
	 * Evento della pressione del tasto invio.
	 * 
	 * @param arg0 Evento.
	 */
	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyCode()==10){
			effettuaLogin();
		}
	}

	/**
	 * Non usato
	 */
	@Override
	public void keyReleased(KeyEvent arg0) {}

	/**
	 * Non usato
	 */
	@Override
	public void keyTyped(KeyEvent arg0) {}
	
	/**
	 * Main
	 */
	public static void main(String[] args) {
		Login finestra = new Login();
		finestra.setVisible(true);
	}
}
