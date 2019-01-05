package pl.app.common;

import javafx.application.Application;
import pl.app.client.ClientMain;
import pl.app.server.ServerMain;

/** Wspólna klasa dla klienta i serwera pozwalająca stworzyć instancję klasy ChooseWindow i za jej pomocą wybrać
 * tryb klient bądź serwer
 * @author Marcin*/

public class Main
{
    public static void main(String[] args) throws Exception
    {
        if (args.length < 1)
        {
            ChooseWindow chooseWindow = new ChooseWindow();

            String mode = chooseWindow.getMode();
            if (mode == "Client")
            {
                runClient();
            }
            else if (mode == "Server")
            {
                runServer(null);
            }

            else
            {
                System.exit(0);
                System.out.println("Nie wybrano żadnego trybu");
            }
        }

        else
        {
            if (args[0].contains("server"))
            {
                runServer(null);
            }

            else
            {
                runClient();
            }
        }
    }
    /** Metoda uruchamiająca tryb serwera */
    private static void runServer(String[] args)
    {
        Application.launch(ServerMain.class, args);
        System.exit(0);
    }

    /** Metoda uruchamiająca tryb klienta */
    private static void runClient() throws Exception
    {
        ClientMain clientMain = new ClientMain(null, 0);
        System.exit(0);
    }
}