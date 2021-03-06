package azienda.gui.filtri;

/**
 * @author      Pignataro Davide, Pucariello Giovanni, Plantone Vincenzo
 * @version     1.0
 */
public class DoubleFilter extends TypeFilter {

	/**
	 * Test
	 */
	@Override
	protected boolean test(String text) {
		boolean test = false;
		try {
			Double.parseDouble(text);
			test = true;
		} catch (NumberFormatException e) {
			test = false;
		}
		return test;
	}
}
