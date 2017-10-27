package azienda.gui.anagrafica;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import azienda.gui.FinestraPrincipale;
import azienda.utility.Pulsanti;
import azienda.utility.Testi;

/**
 * @author      Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version     1.0
 */
@SuppressWarnings("serial")
public abstract class DettagliAnagrafica extends JPanel{
	protected JLabel lblTitolo;
	protected JButton btnAnnulla;
	protected JButton btnConferma;
	protected JTextField textNome;
	protected JPanel panelPulsanti;
	protected JPanel panelTitolo;
	protected JPanel panelCorpo;
	
	/**
	 * Crea la grafica.                       
	 *
	 * @param finestraPrincipale
	 */
	protected void creaGrafica(FinestraPrincipale finestraPrincipale){
		setLayout(new BorderLayout(0, 0));
		
		panelPulsanti = new JPanel();
		
		btnAnnulla = new JButton(Pulsanti.ANNULLA);
		panelPulsanti.add(btnAnnulla);

		btnConferma = new JButton(Pulsanti.CONFERMA);
		panelPulsanti.add(btnConferma);
		
		panelTitolo = new JPanel();
		
		lblTitolo = new JLabel();
		lblTitolo.setFont(new Font(Testi.FONT, Font.BOLD, 14));
		panelTitolo.add(lblTitolo);
		
		panelCorpo = new JPanel();
		panelCorpo.setLayout(null);
	}
}
