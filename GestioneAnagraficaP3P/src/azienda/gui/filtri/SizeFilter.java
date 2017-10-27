package azienda.gui.filtri;

import javax.swing.text.*;
import java.awt.Toolkit;

/**
 * @author      Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version     1.0
 */
public class SizeFilter extends DocumentFilter {
    private int maxCharacters;

	/**
	 * Costruttore del filtro.
	 * 
	 * @param maxChars Numero massimo di caratteri che è possibile inserire in una casella di testo.
	 */
    public SizeFilter(int maxChars) {
        maxCharacters = maxChars;
    }

	/**
	 * insert
	 * 
	 * @throws BadLocationException
	 */
    public void insertString(FilterBypass fb, int offs,
                             String str, AttributeSet a)
        throws BadLocationException {

        if ((fb.getDocument().getLength() + str.length()) <= maxCharacters){
            super.insertString(fb, offs, str, a);
        }
        else {
            Toolkit.getDefaultToolkit().beep();
        }
    }
    
	/**
	 * replace
	 * 
	 * @throws BadLocationException
	 */
    public void replace(FilterBypass fb, int offs,
                        int length, 
                        String str, AttributeSet a)
        throws BadLocationException {

        if ((fb.getDocument().getLength() + str.length()
             - length) <= maxCharacters){
            super.replace(fb, offs, length, str, a);
        }
        else {
            Toolkit.getDefaultToolkit().beep();
        }
    }

}