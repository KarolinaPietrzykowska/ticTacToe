package pl.app.server;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.BindException;
import java.net.InetAddress;

/** Klasa odpowiedzialna za wygląd aplikacji w technologii javaFX, obsługę zdarzenia oraz start Serwera
 *  @author Marcin*/
public class ServerStage extends Stage
{
	private BorderPane bpane;
	private Button btnStart;
	private TextField txtPort;
	private Label lblPort;
	private Label lblStatus;
	private HBox hbox;
	private ImageView image;
	private Image icon;

	public BorderPane getBpane()
	{
		return bpane;
	}

	public Button getBtnStart()
	{
		return btnStart;
	}

	public TextField getTxtPort()
	{
		return txtPort;
	}

	public Label getLblPort()
	{
		return lblPort;
	}

	public Label getLblStatus()
	{
		return lblStatus;
	}

	public HBox getHbox()
	{
		return hbox;
	}

	public ImageView getImage()
	{
		return image;
	}

	/** Konstruktor wywołuje metodę initializeControls() */
	public ServerStage()
	{
		initializeControls();
	}

	/** Metoda ta inicjalizuje elementy potrzebne do stworzenia wygladu aplikacji */
	private void initializeControls()
	{
		bpane = new BorderPane();
		hbox = new HBox();
		txtPort = new TextField("8911");
		Font font = new Font("Verdana",20);
		txtPort.setFont(font);
		lblPort = new Label("Port");
		lblPort.setFont(font);
		lblPort.setTextFill(Color.RED);
		btnStart = new Button("Start");
		btnStart.setFont(font);
		lblStatus = new Label("Serwer jest wyłączony");
		lblStatus.setFont(font);
		lblStatus.setTextFill(Color.RED);
		btnStart.setMaxHeight(40);
		btnStart.setMaxWidth(100);
		txtPort.setMaxHeight(40);
		txtPort.setMaxWidth(70);
		txtPort.setPadding(new Insets(10, 10, 10, 10));

		image = new ImageView(getClass().getResource("/resources/atariServerBackground.jpg").toExternalForm());
		icon = new Image(getClass().getResource("/resources/mainIcon.png").toExternalForm());

        image.fitWidthProperty().bind(widthProperty());

		hbox.getChildren().addAll(lblPort, txtPort, btnStart);
		hbox.setSpacing(10);
		bpane.setPadding(new Insets(10, 10, 10, 10));
		bpane.setPrefHeight(500);
		bpane.setPrefWidth(720);
		bpane.getChildren().add(image);

		bpane.setCenter(hbox);
		bpane.setBottom(lblStatus);
		bpane.setAlignment(lblStatus, Pos.BOTTOM_CENTER);
		hbox.setAlignment(Pos.CENTER);

		Scene scene = new Scene(bpane);

		this.getIcons().add(icon);
		this.initStyle(StageStyle.DECORATED);
		this.setResizable(false);
		this.setScene(scene);

		/** Dodanie obsługi zdarzenia przycisku btnStart*/
		btnStart.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				String btnString = btnStart.getText();

				if (btnString.equals("Start"))
				{
					try
					{
                        /** Zmienna przechowuje wartość portu podaną przez użytkownika w polu edycyjnym txtPort */
                        int port = Integer.parseInt(txtPort.getText());

						ServerService serverService = new ServerService().getInstance();
						serverService.setPort(port);
						serverService.startListener();
						Thread t = new Thread(serverService);
						t.start();

						/** zmnienna myHost przechowuje adres IP na ktorym działa serwer
						 * @see https://docs.oracle.com/javase/7/docs/api/java/net/InetAddress.html*/
						InetAddress myHost = InetAddress.getLocalHost();
						btnStart.setText("Stop");
						lblStatus.setText("Serwer pracuje na " + myHost.getHostName() + ": " + port);
						lblStatus.setTextFill(Color.GREEN);
						lblPort.setTextFill(Color.GREEN);
						txtPort.setDisable(true);
					}

					catch (BindException e)
					{
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText("Podany port jest już zajęty. Podaj inny.");
						alert.showAndWait();
					}

                    catch (NumberFormatException e)
                    {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setContentText("Port musi być z zakresu 2000-9000");
                        alert.showAndWait();
                    }

                    catch (IllegalArgumentException e)
                    {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setContentText("Port musi być z zakresu 2000-9000");
                        alert.showAndWait();
                    }

                    catch (Exception e)
					{
						e.printStackTrace();
					}
                }

				else
				{
					try
					{
						System.out.println("Zamykam serwer");
						ServerService.getInstance().closeListener();
						btnStart.setText("Start");
						lblStatus.setText("Serwer jest wyłączony");
						lblStatus.setTextFill(Color.RED);
						lblPort.setTextFill(Color.RED);
						txtPort.setDisable(false);
					}
					catch (Throwable e)
					{
						e.printStackTrace();
					}
				}
			}
		});
	}
}
