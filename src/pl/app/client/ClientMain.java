package pl.app.client;

/** Klasa odpowiedzialna za uruchomienie aplikacji klienckiej podłączonej do serwera
 *  @author Marcin */
public class ClientMain
{
    private static String serverAddress;
    private static int port;

    public ClientMain(String serverAddress, int port) throws Exception
    {
        this.serverAddress = serverAddress;
        this.port = port;
        run(serverAddress, port);
    }

    /** Metoda uruchamia aplikację klienta za pomoca stworzenia nowej instancji klasy klient */
    public void run(String serverAddress, int port) throws Exception
    {
        if (serverAddress == null | port < 1)
        {
            SetUp setUp = new SetUp();
            serverAddress = setUp.getServerAddress();
            port = setUp.getPort();
        }

        if (serverAddress == null | port < 1)
        {
            System.exit(0);
        }

        while (true)
        {
            Client client = new Client(serverAddress, port);

            client.play();
            if (!client.wantsToPlayAgain())
            {
                break;
            }
        }
    }
}