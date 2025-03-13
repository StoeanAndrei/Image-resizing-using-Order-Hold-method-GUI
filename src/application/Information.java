package application;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Information extends Application {
 
    final ScrollBar sc = new ScrollBar();
    final Image[] images = new Image[4];
    final ImageView[] pics = new ImageView[4];
    final VBox vb = new VBox();
    DropShadow shadow = new DropShadow();

	public String pathImg;
	
    Information(String pathImageR) {
		super();
		pathImg = pathImageR;
		//System.out.println(pathImageR);
	}
 
    @Override
    public void start(Stage stage) {
		
        Group layout = new Group();
        
		Scene scene = new Scene(layout, 800, 800);
 
        shadow.setColor(Color.GREY);
        shadow.setOffsetX(2);
        shadow.setOffsetY(2);

        vb.setLayoutY(100);
        vb.setLayoutX(100);
        vb.setSpacing(10);
 
        sc.setLayoutX(scene.getWidth()-sc.getWidth());
        sc.setMin(1);
        sc.setMax(2000);
        sc.setOrientation(Orientation.VERTICAL);
        sc.setPrefHeight(800);
        
        int lastSeparatorIndex = pathImg.lastIndexOf("/");
    	String directory = new String();
    	
        if (lastSeparatorIndex != -1) {
        	directory = pathImg.substring(0, lastSeparatorIndex);
        	
            directory = directory.replace("/", "\\");
        }
        
        //System.out.println(directory);
        
        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                Label label1 = new Label(" Image resizing\n using Order Hold method");
                Image imagel = new Image(getClass().getResourceAsStream("/img/logo.jpg"));
                label1.setGraphic(new ImageView(imagel));
                label1.setTextFill(Color.web("#0076a3"));
                label1.setFont(new Font("Arial", 26));
                //label1.setLayoutX(160);
        		//label1.setLayoutY(30);

	            vb.getChildren().add(label1);
            }
            
            final Image image = images[i] =
                new Image("file:/" + directory + "/resize_" + (i+1) + ".bmp");
            final ImageView pic = pics[i] =
                new ImageView(images[i]);
            
            pic.setPreserveRatio(true);
            pic.setFitWidth(600);
            pic.setFitHeight(500);
            pic.setSmooth(true);
            pic.setCache(true);
            pic.setEffect(shadow);
            
            vb.getChildren().add(pics[i]);
            
            if (i == 3) {
                Label label1 = new Label(" Image resizing\n using Order Hold method");
                Image imagel = new Image(getClass().getResourceAsStream("/img/logo.jpg"));
                label1.setGraphic(new ImageView(imagel));
                label1.setTextFill(Color.web("#0076a3"));
                label1.setFont(new Font("Arial", 26));
                label1.setLayoutX(160);
        		label1.setLayoutY(30);
        		
	            Button btn = new Button("About Image");
		        btn.setTextFill(Color.web("#0076a3"));
		        btn.setFont(new Font("Arial", 12));
	            btn.setPadding(new Insets(15, 30, 15, 30));
	    		
	            btn.setOnAction(event -> {
	               AboutPic about = new AboutPic(pathImg);
	               about.start(new Stage());
	
	            	stage.close();
	            	
	            	final ImageView picc = new ImageView(image);

	                vb.getChildren().add(picc);
	            });
	            
	            vb.getChildren().add(btn);
            }
        }
 
        sc.valueProperty().addListener((ObservableValue<? extends Number> ov, 
            Number old_val, Number new_val) -> {
                vb.setLayoutY(-new_val.doubleValue());
        });  

        layout.getChildren().addAll(vb, sc);
        //layout.setAlignment(Pos.CENTER); 
		layout.setStyle("-fx-background-color: white");
		
        stage.setTitle("JavaFX - IGU"); 
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        Image icon = new Image(getClass().getResourceAsStream("/img/logo.png"));
        stage.getIcons().add(icon); 
        stage.setResizable(false);      
        stage.setScene(scene);
        stage.centerOnScreen();
		
		stage.show();
    }
 
    public static void main(String[] args) {
        launch(args);
    }
}