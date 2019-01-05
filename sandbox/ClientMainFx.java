

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.stage.Stage;

public class ClientMainFx extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		ClientStage clientStage  	= new ClientStage();
		
		
		Platform.runLater(new Runnable() {
		      @Override
		      public void run() {
		    	  try {
					clientStage.InitializeSocket();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	  try {
					clientStage.InitializeControls();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		      }
		  });
		
		
	
					
		
					
					AnimationTimer t = new AnimationTimer() {

						@Override
						public void handle(long now) {
							
							
							try {
								
								clientStage.play();
							} catch (Exception e) {
								
								e.printStackTrace();
							}
						}
						
						
					};
					t.start();
					//t.wait();
					
					;
	}
						

						
					
		
		
	
	 
	
	
	public static void main(String[] args) {
		launch(args);
	}
}