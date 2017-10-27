package azienda.gui;
import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import azienda.utility.Risorse;

/**
 * @author      Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version     1.0
 */
@SuppressWarnings("serial")
class Home extends JPanel {


	/**
	 * Costruttore del pannello principale.
	 */
	private Home() {
		setLayout(new BorderLayout(0, 0));
		JLabel lblLogo = new JLabel("");
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogo.setIcon(new ImageIcon(FinestraPrincipale.class.getResource(Risorse.LOGO)));
		add(lblLogo, BorderLayout.CENTER);
	}
	
	/**
	 * Restituisce un pannello principale.
	 * 
	 * @return home Pannello principale.
	 */
	public static Home getHome(){
		return new Home();
	}

}
