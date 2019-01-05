
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Optional;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;



import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.app.server.ServerService;
import javafx.scene.Scene;

public class ClientStage extends Stage {
	
	
	private BorderPane bpane;
	private GridPane gridPane;
	private Label msgLabel;
	private Square[] board = new Square[9];
	private Square currSquare;
	private static int PORT = 8911; // do zmiany na wybieralne
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private HBox hbox1;
	private HBox hbox2;
	private HBox hbox3;
	private VBox vbox;
	private VBox vboxMain;
	private static Player player;
	private static Player oponent;
	private String response;
	private String serverAddress ="localhost";


	
	

	public ClientStage() throws Exception {
		

		
		

	}
	
	
	
	
	public void InitializeSocket() throws Exception {
		
		socket = new Socket(serverAddress, PORT);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);	
	}

	public void InitializeControls() throws Exception {

		gridPane = new GridPane();
		msgLabel = new Label("Czekaj na komunikaty");

		hbox1 = new HBox();
		hbox2 = new HBox();
		hbox3 = new HBox();

		

		// Layout GUI
		msgLabel.setBackground(Background.EMPTY);

		for (int i = 0; i < board.length; i++) {
			final int j = i;
			board[i] = new Square();

			board[i].setOnAction((event) -> {

				currSquare = board[j];
				out.println("MOVE " + j);
				System.out.println("Clicked"+j);
			});

			System.out.println("Dodaje button nr " + i);

			if (i <= 2)
				hbox1.getChildren().add(board[i]);
			if (i >= 3 && i <= 5)
				hbox2.getChildren().add(board[i]);
			if (i >= 6)
				hbox3.getChildren().add(board[i]);

		}

		vbox = new VBox(hbox1, hbox2, hbox3);
		vboxMain = new VBox(vbox, msgLabel);

		Scene scene = new Scene(vboxMain);
		this.initStyle(StageStyle.DECORATED);
		this.setScene(scene);
		this.show();

	}

	static class Square extends Button {

		Image icoPlayer = new Image(getClass().getResourceAsStream("ps4.png"));
		Image icoOponent = new Image(getClass().getResourceAsStream("xbox.png"));
		ImageView icoview;

		public void setIco(Player player) {

			if (player.getPlayerName() == "X") {

				icoview = new ImageView(icoPlayer);
			} else {

				icoview = new ImageView(icoOponent);

			}

			icoview.setFitHeight(100);
			icoview.setFitWidth(100);
			icoview.setPreserveRatio(true);

		}

		// ImageView icoview = new ImageView(icoPlayer);

		public Square() {

			this.setBorder(getBorder());

			this.setPrefSize(300, 300);

			this.setStyle("-fx-border-color: black");

			this.setBackground(Background.EMPTY);

		}

		public void setMove(Player player) {
			this.setText(player.getPlayerName());

			// this.setGraphic(icoview);

		}
	}

	private class Player {

		Player(String playerName) {

			this.playerName = playerName;

		}

		private String playerName;

		public String getPlayerName() {
			return playerName;
		}

		public void setPlayerName(String playerName) {
			this.playerName = playerName;
		}

	}
	
	
	
		public void connect() throws Exception{
			
			
			
			msgLabel.setText("Gracz: ");
			System.out.println("Jestem w connect()");

			try {
				
				
					
				
				response = in.readLine();
				System.out.println("Response :" + response);
				if (response.startsWith("WELCOME")) {
					char mark = response.charAt(8);
					// icon = new ImageIcon(mark == 'X' ? "xbox.gif" : "ps4.gif");
					if (mark == 'X') {
				
						player = new Player("X");
						oponent = new Player("O");
					} else {

						player = new Player("0");
						oponent = new Player("X");
					}

					setTitle("Kó³ko i Krzy¿yk - Gracz: " + mark);
					msgLabel.setText("Gracz: "+mark);
					vboxMain.requestLayout();
					
			
					
				
				
				}
				out.println("QUIT");
			} finally {
				System.out.println(socket.getLocalAddress());
			}
		}
			
		
	
	

		public  void play() throws Exception {
			boolean check=true;
			System.out.println("Jestem w play");
				
				try {
					
					
			while( check) {
				check=false;
				
					
					if ((response=in.readLine()) !=null) {
						
						
						System.out.println("Response :" + response);
				
					
					
					
					if (response.startsWith("VALID_MOVE")) {
						msgLabel.setText("Valid move, please wait");
						currSquare.setIco(player);
						// currSquare.repaint();
					} else if (response.startsWith("OPPONENT_MOVED")) {
						int loc = Integer.parseInt(response.substring(15));
						board[loc].setIco(oponent);
						// board[loc].repaint();
						msgLabel.setText("Opponent moved, your turn");
					} else if (response.startsWith("VICTORY")) {
						msgLabel.setText("You win");
						break;
					} else if (response.startsWith("DEFEAT")) {
						msgLabel.setText("You lose");
						break;
					} else if (response.startsWith("TIE")) {
						msgLabel.setText("You tied");
						break;
					} else if (response.startsWith("MESSAGE")) {
						msgLabel.setText(response.substring(8));
						
					}
					
			}
			}}
			catch (Exception e) {
				e.printStackTrace();
				
				
				
			}
		}

		boolean wantsToPlayAgain() {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Koniec rozgrywki");
			alert.setHeaderText(msgLabel.getText());
			alert.setContentText("Chcesz grac dalej?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				return true;
			} else {
				return false;
			}

		}


	
		
		

	}


