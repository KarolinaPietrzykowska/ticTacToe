package pl.app.tests;

import org.junit.Test;
import pl.app.server.ServerService;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotEquals;

/** Klasa testująca korzystająca z platformy JUnit. Służy do testowania klasy serwera ServerService.
 *  @author Karolina */
public class ServerServiceTest
{
    private ServerService serverService = new ServerService();
    private int port = 8910;

    /** Metoda testująca przypisanie wartości portu do danego obiektu klasy ServerService */
    @Test
    public void setPortTest()
    {
        //given
        int expectedPort = 8910;

        //when
        serverService.setPort(port);

        //then
        assertEquals(serverService.getPort(),expectedPort);
    }

    /** Metoda testująca wzorzec projektowy Singleton - możliwość jednoczesnego utworzenia tylko jednej instancji
      * klasy ServerService */
    @Test
    public void getInstanceTest()
    {
        ServerService instance = null;
        assertNotEquals(instance, ServerService.getInstance());
    }

    /** Metoda sprawdzająca poprawność rozpoczęcia nasłuchiwania serwera
     * @throws IOException*/
    @Test
    public void startListenerTest() throws IOException
    {
        //given
        serverService.setPort(port);

        //when
        serverService.startListener();

        //then
        assertTrue(serverService.getServerSocketListener().isBound());
    }

    /** Metoda sprawdzająca poprawność zakończenia nasłuchiwania serwera
     * @throws IOException*/
    @Test
    public void closeListenerTest() throws IOException
    {
        //given
        serverService.setPort(port);

        //when
        serverService.startListener();
        serverService.closeListener();

        //then
        assertTrue(serverService.getServerSocketListener().isClosed());
    }
}