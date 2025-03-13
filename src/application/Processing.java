package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Processing extends Application {
	
	public String pathImg;
	
	Processing(String pathImageR) {
		super();
		pathImg = pathImageR;
		//System.out.println(pathImageR);
	}
    
	@Override
	public void start(Stage primaryStage) {
		try {
			Label label = new Label(" Image resizing\n using Order Hold method");
	        Image image = new Image(getClass().getResourceAsStream("/img/logo.jpg"));
	        label.setGraphic(new ImageView(image));
	        label.setTextFill(Color.web("#0076a3"));
	        label.setFont(new Font("Arial", 26));
	        label.setLayoutX(20);
			label.setLayoutY(20);
				
			ProgressBar pb = new ProgressBar(100);
		    pb.setLayoutX(70);
			pb.setLayoutY(135);
			pb.setPrefWidth(200);
			pb.setPrefHeight(30);
			pb.setStyle("-fx-control-inner-background: palegreen;");

			ProgressIndicator pi = new ProgressIndicator(100);
		    pi.setLayoutX(200);
			pi.setLayoutY(130);
			pi.setPrefWidth(200);
			pi.setPrefHeight(50);
			pi.setStyle("-fx-control-inner-background: palegreen;");

			Separator separator = new Separator();
			separator.setLayoutX(190);
			separator.setLayoutY(100);
			separator.setMaxWidth(100);
			separator.setMaxSize(100, 100);
			separator.setHalignment(HPos.CENTER);
			
			Button btn = new Button("DONE");
	        btn.setLayoutX(170);
	        btn.setLayoutY(200);
	        btn.setPadding(new Insets(10, 20, 10, 20));
            
            double duration = 3;
            double step = 0.1;
            double sleepTime = duration / (1 / step);

            new Thread(() -> {
                double progress = 0;
                while (progress < 1) {
                    try {
                        Thread.sleep((long) (sleepTime * 1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progress += step;
                    final double finalProgress = progress;
                    Platform.runLater(() -> {
                        pb.setProgress(finalProgress);
                        pi.setProgress(finalProgress);
                    });
                }
            }).start();
			
	        btn.setOnAction(event -> {
	            Information inf = new Information(pathImg);
	            inf.start(new Stage());

            	primaryStage.close();
	        });
			
	        VBox layout = new VBox(10);
	        layout.getChildren().addAll(label, separator, pb, pi, btn);
	        layout.setAlignment(Pos.CENTER); 
			layout.setStyle("-fx-background-color: white");
	        
			Scene scene = new Scene(layout, 400, 250);
			
	        primaryStage.setTitle("JavaFX - IGU"); 
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            Image icon = new Image(getClass().getResourceAsStream("/img/logo.png"));
            primaryStage.getIcons().add(icon); 
            primaryStage.setResizable(true);      
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

	        
	        