package pl.app.server;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Klasa będąca główną klasą Serwera w aplikacji wykorzystującej architekturę klient-serwer.
 * Jej zadaniem jest stworzenie okna aplikacji napisanej w technologii JavaFX.
 * @author Marcin
 */
public class ServerMain extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        ServerStage serverStage = new ServerStage();
        serverStage.setTitle("Serwer aplikacji");
        serverStage.setResizable(false);
        serverStage.showAndWait();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
