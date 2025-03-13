package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.event.ActionEvent;  
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Authentification extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
	        Label label1 = new Label(" Image resizing\n using Order Hold method");
	        Image image = new Image(getClass().getResourceAsStream("/img/logo.jpg"));
	        label1.setGraphic(new ImageView(image));
	        label1.setTextFill(Color.web("#0076a3"));
	        label1.setFont(new Font("Arial", 20));
	        label1.setLayoutX(80);
			label1.setLayoutY(50);
			
			Label label2 = new Label("Username");
	        label2.setFont(new Font("Arial", 14));
	        label2.setLayoutX(30);
			label2.setLayoutY(150);
			
			TextField tf = new TextField();
			tf.setPromptText("Your username");
	        tf.setLayoutX(110);
			tf.setLayoutY(145);
			tf.setTooltip(new Tooltip("username"));
			
			Label label3 = new Label("Password");
	        label3.setFont(new Font("Arial", 14));
	        label3.setLayoutX(30);
			label3.setLayoutY(190);

			PasswordField pf = new PasswordField();
			pf.setPromptText("Your password");
	        pf.setLayoutX(110);
			pf.setLayoutY(185);
			pf.setTooltip(new Tooltip("12345"));
			
			final Label message = new Label("");
			message.setLayoutX(40);
			message.setLayoutY(220);
			
			Button btn1 = new Button("LOGIN");
	        btn1.setLayoutX(150);
	        btn1.setLayoutY(250);
	        btn1.setPadding(new Insets(10, 40, 10, 40));
	        btn1.setOnAction(new EventHandler<ActionEvent>() {  
	            @Override  
	            public void handle(ActionEvent arg0) {  
	            	if(!tf.getText().equals("username") || !pf.getText().equals("12345")) {
	            		message.setText("Your username or password are incorrect!");
	                    message.setTextFill(Color.rgb(210, 39, 30)); 
	            	} else {
	                    //message.setText("Your password has been confirmed");
	                    //message.setTextFill(Color.rgb(21, 117, 84));
	            		
	                    primaryStage.close(); 
	                    
		            	Cimage cimg = new Cimage();
		                cimg.start(new Stage());
	            	}
	            }  
	        });  
	        
	        Button btn2 = new Button("Registration");
	        btn2.setLayoutX(145);
	        btn2.setLayoutY(310);
	        btn2.setPadding(new Insets(10, 30, 10, 30));
	        btn2.setOnAction(new EventHandler<ActionEvent>() {  
	            @Override  
	            public void handle(ActionEvent arg0) {  
	            	Registration reg = new Registration();
	                reg.start(new Stage());
	                primaryStage.close(); 
	            }  
	        }); 
	        
	        Pane root = new Pane();  
	        root.getChildren().addAll(label1, label2, tf, label3, pf, btn1, btn2, message);  
			
			Scene scene = new Scene(root, 400, 400);
	        root.setStyle("-fx-background-color: white");
	        primaryStage.setTitle("JavaFX - IGU"); 
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            Image icon = new Image(getClass().getResourceAsStream("/img/logo.png"));
            primaryStage.getIcons().add(icon);
            primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
