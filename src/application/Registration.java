package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.event.ActionEvent;  
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Registration extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
	        Label label1 = new Label(" Image resizing\n using Order Hold method");
	        Image image = new Image(getClass().getResourceAsStream("/img/logo.jpg"));
	        label1.setGraphic(new ImageView(image));
	        label1.setTextFill(Color.web("#0076a3"));
	        label1.setFont(new Font("Arial", 20));
	        label1.setLayoutX(80);
			label1.setLayoutY(40);
			

			CheckBox cb = new CheckBox();
			cb.setText("Remember account?");
	        cb.setFont(new Font("Arial", 12));
	        cb.setLayoutX(40);
			cb.setLayoutY(110);
			cb.setSelected(true);
			
			Label label2 = new Label("Username");
	        label2.setFont(new Font("Arial", 14));
	        label2.setLayoutX(30);
			label2.setLayoutY(140);
			
			TextField tf = new TextField();
			tf.setPromptText("Your username");
	        tf.setLayoutX(110);
			tf.setLayoutY(135);
			
			Label label3 = new Label("Password");
	        label3.setFont(new Font("Arial", 14));
	        label3.setLayoutX(30);
			label3.setLayoutY(180);

			PasswordField pf = new PasswordField();
			pf.setPromptText("Your password");
	        pf.setLayoutX(110);
			pf.setLayoutY(175);
			pf.setTooltip(new Tooltip("Minimum 8 characters"));
			
			final Label message = new Label("");
			message.setLayoutX(40);
			message.setLayoutY(210);
			
			Button btn1 = new Button("Registration");
	        btn1.setLayoutX(135);
	        btn1.setLayoutY(230);
	        btn1.setPadding(new Insets(10, 40, 10, 40));
	        btn1.setOnAction(new EventHandler<ActionEvent>() {  
	            @Override  
	            public void handle(ActionEvent arg0) {  
	            	if(tf.getText().equals("username") || tf.getText().equals("") || pf.getText().equals("")) {
		            	message.setText("Your data is incorrect!");
		            	message.setTextFill(Color.rgb(210, 39, 30)); 
	            	} else if(!tf.getText().equals("username")) {
	                    message.setText("Your username has been registered!");
	                    message.setTextFill(Color.rgb(21, 117, 84));
	                    btn1.setDisable(true);
	            	}
	            }  
	        });  
	        
	        Button btn2 = new Button("Authentification");
	        btn2.setLayoutX(150);
	        btn2.setLayoutY(285);
	        btn2.setPadding(new Insets(10, 20, 10, 20));
	        btn2.setOnAction(new EventHandler<ActionEvent>() {  
	            @Override  
	            public void handle(ActionEvent arg0) {   
	            	if(cb.isSelected()) {
	            		if(!tf.getText().equals("") && !pf.getText().equals("")) {       
		            		String str = "Username: " + tf.getText() + "\nPassword: " + pf.getText();
		            		Alert alert = new Alert(AlertType.INFORMATION, str, ButtonType.OK);
		            		alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		            		alert.show();
	            		}
	            	}
		            Authentification aut = new Authentification();
		            aut.start(new Stage());
	            	primaryStage.close();
	            }  
	        }); 
	        
	        Button btn3 = new Button("EXIT");
	        btn3.setLayoutX(180);
	        btn3.setLayoutY(340);
	        btn3.setPadding(new Insets(10, 20, 10, 20));
	        
	        btn3.setOnAction(event -> Platform.exit());
	        
	        Pane root = new Pane();  
	        root.getChildren().addAll(label1, label2, tf, label3, pf, btn1, btn2, btn3, message, cb);  
	        
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
