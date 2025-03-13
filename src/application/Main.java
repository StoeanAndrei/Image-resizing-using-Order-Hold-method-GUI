package application;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.event.ActionEvent;  
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
	        Label label1 = new Label(" Image resizing\n using Order Hold method");
	        Image image = new Image(getClass().getResourceAsStream("/img/logo.jpg"));
	        label1.setGraphic(new ImageView(image));
	        label1.setTextFill(Color.web("#0076a3"));
	        label1.setFont(new Font("Arial", 20));
	        label1.setLayoutX(80);
			label1.setLayoutY(130);
			
			Separator separator = new Separator();
			separator.setLayoutX(205);
			separator.setLayoutY(210);
			separator.setMaxWidth(100);
			separator.setMaxSize(100, 100);
			separator.setHalignment(HPos.CENTER);
			
			Button btn1 = new Button("START");  
			btn1.setLayoutX(175);
			btn1.setLayoutY(250);  
	        btn1.setPadding(new Insets(10, 20, 10, 20));
	        btn1.setOnAction(new EventHandler<ActionEvent>() {  
	              
	            @Override  
	            public void handle(ActionEvent arg0) {  
	                Authentification aut = new Authentification();
	                aut.start(new Stage());
	            	primaryStage.close();
	            }  
	        });  
			
	        Button btn2 = new Button("EXIT");
	        btn2.setLayoutX(180);
	        btn2.setLayoutY(300);
	        btn2.setPadding(new Insets(10, 20, 10, 20));
	        
	        btn2.setOnAction(event -> Platform.exit());
			
			ChoiceBox<String> cb = new ChoiceBox<>();
			cb.setItems(FXCollections.observableArrayList(
			    "English", 
			    "Romanian")
			);
			cb.setLayoutX(240);
			cb.setLayoutY(20);
			cb.setPadding(new Insets(0, 0, 0, 0));
			cb.setTooltip(new Tooltip("LANGUAGE"));
			cb.setValue("English");
			
			Hyperlink link = new Hyperlink();
			
			link.setText("Order Hold method");
			link.setLayoutX(10);
			link.setLayoutY(370);
			
			link.setOnAction((ActionEvent e) -> {
			    String url = "https://www.educative.io/answers/how-to-zoom-an-image-using-a-zero-order-hold";

		        HostServices hostServices = getHostServices();
		        hostServices.showDocument(url);
			});
	        
	        Pane root = new Pane();
            root.getChildren().addAll(label1, btn1, btn2, cb, link, separator); 
	       
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
