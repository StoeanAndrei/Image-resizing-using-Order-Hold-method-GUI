package application;

import java.io.File;

import backend.Resize;
import backend.Scalable;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Cimage extends Application {
	//@Override
	
	public String pathImage;
	private String pathImageR;
	
	public void start(Stage primaryStage) {
		try {
	        Label label1 = new Label(" Image resizing\n using Order Hold method");
	        Image image = new Image(getClass().getResourceAsStream("/img/logo.jpg"));
	        label1.setGraphic(new ImageView(image));
	        label1.setTextFill(Color.web("#0076a3"));
	        label1.setFont(new Font("Arial", 26));
	        label1.setLayoutX(140);
			label1.setLayoutY(65);
			
			Button btn1 = new Button("Add Image");
	        btn1.setFont(new Font("Arial", 16));
	        btn1.setPadding(new Insets(30, 40, 30, 40));
	        btn1.setLayoutX(800);
	        btn1.setLayoutY(50);
	        
	        Label label2 = new Label("Choose a extension: ");
	        label2.setFont(new Font("Arial", 14));
	        label2.setLayoutX(820);
			label2.setLayoutY(170);
	        
	        final ToggleGroup gr = new ToggleGroup();

	        RadioButton rb1 = new RadioButton("BMP");
	        rb1.setToggleGroup(gr);
	        //rb1.setSelected(true);
	        rb1.setLayoutX(860);
	        rb1.setLayoutY(200);

	        RadioButton rb2 = new RadioButton("PNG");
	        rb2.setToggleGroup(gr);
	        rb2.setLayoutX(860);
	        rb2.setLayoutY(250);
	         
	        RadioButton rb3 = new RadioButton("JPG");
	        rb3.setToggleGroup(gr);
	        rb3.setLayoutX(860);
	        rb3.setLayoutY(300);
	        
	        rb1.setUserData("*.bmp");
	        rb2.setUserData("*.png");
	        rb3.setUserData("*.jpg");
	        
	        FileChooser fileChooser = new FileChooser();
	        fileChooser.setTitle("Open Resource File");
	        fileChooser.setInitialDirectory(new File("C:\\"));

	        gr.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle toggle, Toggle new_toggle) -> {
			    if (new_toggle == null) {
			        fileChooser.getExtensionFilters().addAll(
			            new FileChooser.ExtensionFilter("File", (String) gr.getSelectedToggle().getUserData())
			       	);
			    } else {
			        fileChooser.getExtensionFilters().addAll(
			            new FileChooser.ExtensionFilter("File", (String) gr.getSelectedToggle().getUserData())
			        );
			    }
			});
	        
	        Pane root = new Pane(); 
	        
	        Slider slider = new Slider();
	        slider.setMin(0.1);
	        slider.setMax(10);
	        slider.setValue(1);
	        slider.setShowTickLabels(true);
	        slider.setShowTickMarks(true);
	        slider.setMajorTickUnit(1);
	        slider.setMinorTickCount(1);
	        slider.setBlockIncrement(1);
	        slider.setLayoutX(400);
	        slider.setLayoutY(600);  
			
			Button btn2 = new Button("Resize Image");
	        btn2.setFont(new Font("Arial", 16));
	        btn2.setPadding(new Insets(30, 40, 30, 40));
	        btn2.setLayoutX(850);
	        btn2.setLayoutY(580);
	        btn2.setDisable(true);
	        
	        btn1.setOnAction(e -> {
	            File selectedFile = fileChooser.showOpenDialog(primaryStage);
	            pathImage = selectedFile.toURI().toString();
	            
	            //System.out.println(pathImage);
	        	
	            if (selectedFile != null) {
	            	
	            	Image img = new Image(pathImage);
	    	        
	    	        ImageView imageView = new ImageView();
	    	        imageView.setImage(img);
	    	        imageView.setPreserveRatio(true);
	    	        imageView.setFitWidth(600);
	    	        imageView.setFitHeight(500);
	                imageView.setSmooth(true);
	                imageView.setCache(true);
	                
	                imageView.scaleXProperty().bind(slider.valueProperty());
	                imageView.scaleYProperty().bind(slider.valueProperty());
	    	        
	    	        ScrollPane scrollPane = new ScrollPane();
	    	        scrollPane.setContent(imageView);
	    	        scrollPane.setHvalue(0.1);
	    	        scrollPane.setVvalue(0.1);
	    	        scrollPane.setLayoutX(250);
	    	        scrollPane.setLayoutY(200);
	    	        scrollPane.setPrefSize(500, 350);
	    	        
	    	        root.getChildren().addAll(imageView, scrollPane);
	    	        
	    	        btn1.setDisable(true);
	    	        btn2.setDisable(false);
	    	        rb1.setDisable(true);
	    	        rb2.setDisable(true);
	    	        rb3.setDisable(true);
	            }
	        });
	        
	        final ToggleGroup group = new ToggleGroup();

	        ToggleButton tb1 = new ToggleButton("White");
	        tb1.setToggleGroup(group);
	        tb1.setSelected(true);
	        tb1.setPadding(new Insets(10, 20, 10, 20));
	        tb1.setLayoutX(100);
	        tb1.setLayoutY(200);

	        ToggleButton tb2 = new ToggleButton("Lightgray");
	        tb2.setToggleGroup(group);
	        tb2.setPadding(new Insets(10, 20, 10, 20));
	        tb2.setLayoutX(100);
	        tb2.setLayoutY(250);

	        ToggleButton tb3 = new ToggleButton("Gray");
	        tb3.setToggleGroup(group);
	        tb3.setPadding(new Insets(10, 20, 10, 20));
	        tb3.setLayoutX(100);
	        tb3.setLayoutY(300);
	        
	        tb1.setUserData("-fx-background-color: white");
	        tb2.setUserData("-fx-background-color: lightgray");
	        tb3.setUserData("-fx-background-color: gray");

			Scene scene = new Scene(root, 1100, 700);
	        root.setStyle("-fx-background-color: white");
			
			group.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle toggle, Toggle new_toggle) -> {
			    if (new_toggle == null) {
			        root.setStyle("-fx-background-color: white");

			    } else {
			        root.setStyle("" + group.getSelectedToggle().getUserData());
			    }
			});
			
			Label label3 = new Label("Insert a factor for resizing: ");
	        label3.setFont(new Font("Arial", 14));
	        label3.setLayoutX(820);
			label3.setLayoutY(430);
			
			ComboBox<String> comboBox = new ComboBox<String>();
	        comboBox.setLayoutX(860);
			comboBox.setLayoutY(465);
			comboBox.setTooltip(new Tooltip("FACTOR"));
			
	        comboBox.getItems().addAll(
	            "300",
	            "700",
	            "1800",
	            "3200",
	            "4000",
	            "5000" 
	        );   
			
			btn2.setOnAction(e -> {
				String selectedValue = comboBox.getSelectionModel().getSelectedItem();
				int intValue = Integer.parseInt(selectedValue);
				
				this.pathImageR = pathImage.substring(6);
				
                Scalable resize = new Resize(pathImageR);
                resize.invoke(intValue, Scalable.Preserve.WIDTH);
                
                Processing pr = new Processing(pathImageR);
                pr.start(new Stage());
                
            	primaryStage.close();
			});
		    
	        root.getChildren().addAll(label1, btn1, tb1, tb2, tb3, rb1, rb2, rb3, label2, btn2, slider, label3, comboBox); 
			
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
