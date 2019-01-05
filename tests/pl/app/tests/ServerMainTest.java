package pl.app.tests;

import org.junit.Test;
import pl.app.server.ServerMain;

import static org.junit.Assert.assertTrue;

/** Klasa testująca korzystająca z platformy JUnit. Służy do testowania klasy serwera ServerMain.
 *  @author Karolina */
public class ServerMainTest  
{
	private ServerMain serverMain;

	/** Metoda testująca tworzenie instancji klasy ServerMain */
	@Test
	public void isServerMainCreated()
	{
		serverMain = new ServerMain();
		assertTrue(!serverMain.equals(null));
	}
}
