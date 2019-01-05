
package pl.app.server;

import java.io.IOException;
import java.net.ServerSocket;

/** Klasa zajmująca się stroną techniczną serwera dla sieciowej gry ,,Kółko i
 * Krzyżyk", tj. stworzeniem serwera i przydzieleniem odpowiedniego portu.
 * Implementuje interfejs runnable.
 * @see java.lang.Runnable
 * @author Marcin*/

public class ServerService implements Runnable
{
	private static ServerService instance = null;
	private int port;
	
	/** gniazdo nasłuchu po stronie serwera */
	private ServerSocket serverSocketListener;

	/** getter */
	public ServerSocket getServerSocketListener()
	{
		return serverSocketListener;
	}

	/** getter */
	public int getPort()
	{
		return port;
	}

	/** setter */
	public void setPort(int port)
	{
		this.port = port;
	}
	
	/**Metoda uruchamiająca działanie serwera poprzez wywołanie metody call()
	 * @see pl.app.server.ServerService#call() */
	public void run()
	{
		try
		{
			call();
		} 
		
		catch (Exception e)
		{

		}
	}
	
	/** Metoda korzystająca ze wzorca projektowego Singleton - umożliwia stworzenie tylko
	 * jednej instancji klasy ServerService.
	 * @return obiekt klasy ServerService
	 */
	public static ServerService getInstance()
	{
		if (instance == null)
		{
			instance = new ServerService();
		}
		return instance;
	}
	
	/** Metoda inicjalizuje port nasłuchiwania serwera
	 * @throws IOException*/
	public void startListener() throws IOException 
	{
		serverSocketListener = new ServerSocket(port);
	}
	
	/** Metoda kończąca nasłuchiwanie
	 * @throws IOException */
	public void closeListener() throws IOException
	{
		serverSocketListener.close();
	}

    /** Metoda parująca klientów aplikacji
	 * @throws IOException*/
	public void call() throws IOException
	{
		String msg = "Serwer wystartował";

			while (true)
			{
				Game game = new Game();

				/** @see https://docs.oracle.com/javase/7/docs/api/java/net/ServerSocket.html#accept() */
				Game.Player playerX = game.new Player(serverSocketListener.accept(), 'X');
				Game.Player playerO = game.new Player(serverSocketListener.accept(), 'O');

				/** @see pl.app.server.Game.Player#setOpponent(Game.Player)*/
				playerX.setOpponent(playerO);
				playerO.setOpponent(playerX);
				game.currentPlayer = playerX;
				playerX.start();
				playerO.start();
			}
	}
}

