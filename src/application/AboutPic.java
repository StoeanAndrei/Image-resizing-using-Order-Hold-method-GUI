package application;

import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AboutPic extends Application {

    final ScrollBar sc = new ScrollBar();
    
    List<Tones> tones = Arrays.<Tones>asList();

    final TreeItem<Tones> root = 
        new TreeItem<>(new Tones("Tones", ""));
    
	public String pathImg;
	
	private	final Node rootIcon = 
        new ImageView(new Image(getClass().getResourceAsStream("/img/tones.png"), 40, 40, false, false));
    private final Image depIcon = 
        new Image(getClass().getResourceAsStream("/img/colors.png"), 40, 40, false, false);

    
    
    List<Tones> tonesTW = Arrays.<Tones>asList();
    TreeItem<String> rootNode;
    
    private final TableView<Pixel> table = new TableView<>();
    
    int blackPixelCount = 0;
    int whitePixelCount = 0;
    int shadowPixelCount = 0;
    int midtonePixelCount = 0;
    int highlightPixelCount = 0;
    
    int totalRed = 0;
    int totalGreen = 0;
    int totalBlue = 0;
	
    AboutPic(String pathImageR) {
		super();
		pathImg = pathImageR;
		//System.out.println(pathImageR);
	}
 
    @Override
    public void start(Stage stage) {
		
        Group layout = new Group();
        
    	VBox vb = new VBox(10); 
        
		Scene scene = new Scene(layout, 840, 800);
		
        vb.setLayoutY(100);
        vb.setLayoutX(100);
        vb.setSpacing(10);
		
		sc.setLayoutX(scene.getWidth()-sc.getWidth());
		sc.setLayoutY(0);
        sc.setMin(1);
        sc.setMax(1300);
        sc.setPrefHeight(800);
        sc.setPrefWidth(10);
        sc.setOrientation(Orientation.VERTICAL);

        Label label = new Label(" Image resizing\n using Order Hold method");
        Image logo = new Image(getClass().getResourceAsStream("/img/logo.jpg"));
        label.setGraphic(new ImageView(logo));
        label.setTextFill(Color.web("#0076a3"));
        label.setFont(new Font("Arial", 26));
        //label.setLayoutX(140);
		//label.setLayoutY(65);
        
        Separator separator1 = new Separator();
		separator1.setMaxWidth(300);
		separator1.setHalignment(HPos.CENTER);

    	DropShadow shadow = new DropShadow();
        shadow.setColor(Color.LIGHTGREY);
        shadow.setOffsetX(2);
        shadow.setOffsetY(2);
        
        int lastSeparatorIndex = pathImg.lastIndexOf("/");
    	String directory = new String();
    	final String dir;
    	
        if (lastSeparatorIndex != -1) {
        	directory = pathImg.substring(0, lastSeparatorIndex);
        	
            directory = directory.replace("/", "\\");
        }
        dir = directory;
        
        final Image image = new Image("file:/" + directory + "/resize_4" + ".bmp");
        final ImageView pic = new ImageView(image);
        
        pic.setPreserveRatio(true);
        pic.setFitWidth(600);
        pic.setFitHeight(500);
        pic.setSmooth(true);
        pic.setCache(true);
        pic.setEffect(shadow); 
        
        Separator separator2 = new Separator();
		separator2.setMaxWidth(300);
		separator2.setHalignment(HPos.CENTER);
        
        ListView<String> list = new ListView<>();
        list.setPrefWidth(50);
        list.setPrefHeight(50);
        
        ObservableList<String> items = FXCollections.observableArrayList (
        		"/resize_1", "/resize_2", "/resize_3", "/resize_4");
        list.setItems(items);
        
        list.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> ov, String old_val, 
                    String new_val) -> {
    	            Image imag = new Image("file:/" + dir + new_val.toString() + ".bmp");
                	pic.setImage(imag);
            });
		
		Button btn = new Button("Tones and Colors");
        btn.setFont(new Font("Arial", 16));
        btn.setPadding(new Insets(20, 30, 20, 30));
        //btn.setDisable(true);
        
        btn.setOnAction(e -> {
        	btn.setDisable(true);
        	
	        int imageWidth = (int) image.getWidth();
	        int imageHeight = (int) image.getHeight();
	        
	        // Calculate the area of black pixels
	        for (int y = 0; y < imageHeight; y++) {
	            for (int x = 0; x < imageWidth; x++) {
	                int pixel = image.getPixelReader().getArgb(x, y);
	                if (pixel == 0xFF000000) {
	                    blackPixelCount++;
	                }
	            }
	        }
	        
	        // Calculate white grade
	        for (int y = 0; y < imageHeight; y++) {
	            for (int x = 0; x < imageWidth; x++) {
	                int pixel = image.getPixelReader().getArgb(x, y);
	                if (pixel > 0xFFFFFF90) {
	                    whitePixelCount++;
	                }
	            }
	        }        
	        
	        // Calculate pixel counts for shadows, midtones, and highlights
	        for (int y = 0; y < imageHeight; y++) {
	            for (int x = 0; x < imageWidth; x++) {
	                int pixel = image.getPixelReader().getArgb(x, y);
	
	                // Thresholds for shadows, midtones, and highlights
	                if (pixel < 0xFF333333) { // Shadow threshold
	                    shadowPixelCount++;
	                } else if (pixel > 0xFFCCCCCC) { // Highlight threshold
	                    highlightPixelCount++;
	                } else {
	                    midtonePixelCount++;
	                }
	            }
	        }
	        
	        // Measure the RGB color values
	        PixelReader pixelReader = image.getPixelReader();

	        int r, g, b;
	        int rgb;
	        
	        for (int x = 0; x < image.getWidth(); x++) {
	            for (int y = 0; y < image.getHeight(); y++) {
	            	rgb = pixelReader.getArgb(x, y);
	                r = (rgb >> 16) & 0xFF;
	                g = (rgb >> 8) & 0xFF;
	                b = rgb & 0xFF;
	                
	                if (x < 1000 && y < 1000) {
		    			int n = Math.abs(rgb);
		    			/*int[] hexNum = new int[100];
		    	        int i = 0;
		    	        while (n != 0) {
		    	            hexNum[i] = n % 16;
		    	            n = n / 16;
		    	            i++;
		    	        }
		    	        for (int j = i - 1; j >= 0; j--) {
		    	            if (hexNum[j] > 9)
		    	                System.out.print((char) (55 + hexNum[j]));
		    	            else
		    	                System.out.print(hexNum[j]);
		    	        }*/
		                
		                table.getItems().add(new Pixel(String.valueOf(x), String.valueOf(y), String.valueOf(n)));
	                }

	                totalRed += (int) (r * 255);
	                totalGreen += (int) (g * 255);
	                totalBlue += (int) (b * 255);
	            }
	        }
	        
	        table.setEditable(true);
	        
	        TableColumn<Pixel, String> lineCol = new TableColumn<>("Line");
	        lineCol.setMinWidth(100);
	        lineCol.setCellValueFactory(
	                new PropertyValueFactory<>("line"));
	 
	        TableColumn<Pixel, String> columnCol = new TableColumn<>("Column");
	        columnCol.setMinWidth(100);
	        columnCol.setCellValueFactory(
	                new PropertyValueFactory<>("column"));
	 
	        TableColumn<Pixel, String> valueCol = new TableColumn<>("Color code");
	        valueCol.setMinWidth(200);
	        valueCol.setCellValueFactory(
	                new PropertyValueFactory<>("value"));
	        
	        table.getColumns().add(lineCol);
	        table.getColumns().add(columnCol);
	        table.getColumns().add(valueCol);
    
	        totalRed /= 255 * 1000;
            totalGreen /= 255 * 1000;
            totalBlue /= 255 * 1000;
            
            totalRed = Math.abs(totalRed);
            totalGreen = Math.abs(totalGreen);
            totalBlue = Math.abs(totalBlue);

	        /*System.out.println("Total Red: " + totalRed);
	        System.out.println("Total Green: " + totalGreen);
	        System.out.println("Total Blue: " + totalBlue);
	
	        System.out.println("Shadow pixel count: " + shadowPixelCount);
	        System.out.println("Midtone pixel count: " + midtonePixelCount);
	        System.out.println("Highlight pixel count: " + highlightPixelCount);
	        System.out.println("White pixel count: " + whitePixelCount);
	        System.out.println("Black pixel count: " + blackPixelCount);*/
	        
	        tones = Arrays.<Tones>asList(
	        		new Tones("Shadow", String.valueOf(shadowPixelCount)),
	        		new Tones("Midtone", String.valueOf(midtonePixelCount)),
	        		new Tones("Highlight", String.valueOf(highlightPixelCount)),
	        		new Tones("White", String.valueOf(whitePixelCount)),
	        		new Tones("Black", String.valueOf(blackPixelCount)));
	        
	        root.setExpanded(true);

	        tones.stream().forEach((tones) -> {
	            root.getChildren().add(new TreeItem<>(tones));
	        });

	        TreeTableColumn<Tones, String> tColumn = 
	            new TreeTableColumn<>("Tones");
	        tColumn.setPrefWidth(150);
	        tColumn.setCellValueFactory(
	            (TreeTableColumn.CellDataFeatures<Tones, String> param) -> 
	            new ReadOnlyStringWrapper(param.getValue().getValue().getT())
	        );

	        TreeTableColumn<Tones, String> vColumn = 
	            new TreeTableColumn<>("Values");
	        vColumn.setPrefWidth(190);
	        vColumn.setCellValueFactory(
	            (TreeTableColumn.CellDataFeatures<Tones, String> param) -> 
	            new ReadOnlyStringWrapper(param.getValue().getValue().getV())
	        );
	        
	        tColumn.setCellValueFactory(
	            (TreeTableColumn.CellDataFeatures<Tones, String> param) -> 
	            new ReadOnlyStringWrapper(param.getValue().getValue().getT())
	        );

	        vColumn.setCellValueFactory(
	            (TreeTableColumn.CellDataFeatures<Tones, String> param) -> 
	            new ReadOnlyStringWrapper(param.getValue().getValue().getV())
	        );

	        TreeTableView<Tones> treeTableView = new TreeTableView<>(root);
	        treeTableView.getColumns().add(tColumn);
	        treeTableView.getColumns().add(vColumn);
	        

	        rootNode = new TreeItem<>("Tones and Colors", rootIcon);
	        TreeView<String> treeView = new TreeView<>(rootNode);
	        
	        tonesTW = Arrays.<Tones>asList(
	                new Tones("Shadow", "Tones"),
	                new Tones("Midtone", "Tones"),           
	                new Tones("Highlight", "Tones"),
	                new Tones("White", "Tones"),
	                new Tones("Black", "Tones"),
	                new Tones("Red", "Colors"),
	                new Tones("Green", "Colors"),
	                new Tones("Blue", "Colors"));
	    	
	        rootNode.setExpanded(true);
	        for (Tones tone : tonesTW) {
	            TreeItem<String> empLeaf = new TreeItem<>(tone.getT());
	            boolean found = false;
	            for (TreeItem<String> depNode : rootNode.getChildren()) {
	                if (depNode.getValue().contentEquals(tone.getV())){
	                    depNode.getChildren().add(empLeaf);
	                    found = true;
	                    break;
	                }
	            }
	            if (!found) {
	                TreeItem<String> depNode = new TreeItem<>(
	                    tone.getV(), 
	                    new ImageView(depIcon)
	                );
	                rootNode.getChildren().add(depNode);
	                depNode.getChildren().add(empLeaf);
	            }
	        }
	        
	        Label label1 = new Label(); 
	        
	        Separator separator3 = new Separator();
			separator3.setMaxWidth(150);
			separator3.setHalignment(HPos.CENTER);
	        
	        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue ) ->{
	            String selectedItem = newValue.getValue();
	            
	            switch(selectedItem) {
		            case "Shadow": 
	            		//System.out.println(selectedItem + " " + String.valueOf(shadowPixelCount));
	                    label1.setText(selectedItem + " " + String.valueOf(shadowPixelCount));
		            	break;
		            case "Midtone": 
	            		//System.out.println(selectedItem + " " + String.valueOf(midtonePixelCount));
	                    label1.setText(selectedItem + " " + String.valueOf(midtonePixelCount));
		            	break;
		            case "Highlight": 
	            		//System.out.println(selectedItem + " " + String.valueOf(highlightPixelCount));
	                    label1.setText(selectedItem + " " + String.valueOf(highlightPixelCount));
		            	break;
		            case "White": 
	            		//System.out.println(selectedItem + " " + String.valueOf(whitePixelCount));
	                    label1.setText(selectedItem + " " + String.valueOf(whitePixelCount));
		            	break;
		            case "Black": 
	            		//System.out.println(selectedItem + " " + String.valueOf(blackPixelCount));
	                    label1.setText(selectedItem + " " + String.valueOf(blackPixelCount));
		            	break;
		           case "Red": 
	            		//System.out.println(selectedItem + " " + String.valueOf(totalRed));
	                    label1.setText(selectedItem + " " + String.valueOf(totalRed));
		            	break;
		            case "Green": 
	            		//System.out.println(selectedItem + " " + String.valueOf(totalGreen));
	                    label1.setText(selectedItem + " " + String.valueOf(totalGreen));
		            	break;
		            case "Blue": 
	            		//System.out.println(selectedItem + " " + String.valueOf(totalBlue));
	                    label1.setText(selectedItem + " " + String.valueOf(totalBlue));
		            	break;
	            }
	        });
	        
	        Separator separator4 = new Separator();
			separator4.setMaxWidth(150);
			separator4.setHalignment(HPos.CENTER);
			
			Button btn2 = new Button("EXIT");
	        btn2.setPadding(new Insets(10, 20, 10, 20));
	        
	        btn2.setOnAction(event -> Platform.exit());
	        
	        vb.getChildren().addAll(treeTableView, treeView, separator3, label1, separator4, table, btn2);
        });  
		
		sc.valueProperty().addListener((ObservableValue<? extends Number> ov, 
	            Number old_val, Number new_val) -> {
	                vb.setLayoutY(-new_val.doubleValue());
	        });  
        
        vb.getChildren().addAll(label, separator1, pic, separator2, list, btn);
        vb.setAlignment(Pos.CENTER);  
		vb.setStyle("-fx-background-color: white");
		
		layout.getChildren().addAll(vb, sc); 
		
        stage.setTitle("JavaFX - IGU"); 
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        Image icon = new Image(getClass().getResourceAsStream("/img/logo.png"));
        stage.getIcons().add(icon); 
        stage.setResizable(false);      
        stage.setScene(scene);
        stage.centerOnScreen();
		stage.show();
    }
    
    public class Tones {
    	 
        private SimpleStringProperty t;
        private SimpleStringProperty v;
        public SimpleStringProperty nameProperty() {
            if (t == null) {
                t = new SimpleStringProperty(this, "tone");
            }
            return t;
        }
        public SimpleStringProperty vProperty() {
            if (v == null) {
                v = new SimpleStringProperty(this, "value");
            }
            return v;
        }
        private Tones(String t, String v) {
            this.t = new SimpleStringProperty(t);
            this.v = new SimpleStringProperty(v);
        }
        public String getT() {
            return t.get();
        }
        public void setT(String fName) {
            t.set(fName);
        }
        public String getV() {
            return v.get();
        }
        public void setV(String fName) {
            v.set(fName);
        }
    }
    

    public static class Pixel {
 
        private final SimpleStringProperty line;
        private final SimpleStringProperty column;
        private final SimpleStringProperty value;
 
        private Pixel(String fName, String lName, String email) {
            this.line = new SimpleStringProperty(fName);
            this.column = new SimpleStringProperty(lName);
            this.value = new SimpleStringProperty(email);
        }
 
        public String getLine() {
            return line.get();
        }
 
        public void setLine(String fName) {
        	line.set(fName);
        }
 
        public String getColumn() {
            return column.get();
        }
 
        public void setColumn(String fName) {
        	column.set(fName);
        }
 
        public String getValue() {
            return value.get();
        }
 
        public void setValue(String fName) {
            value.set(fName);
        }
    }
 
    public static void main(String[] args) {
        launch(args);
    }
}