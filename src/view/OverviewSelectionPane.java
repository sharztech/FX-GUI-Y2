package view;

import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Module;

public class OverviewSelectionPane extends GridPane{
	
	private Button saveOverView;
	private TextArea studentProfile, selectModules, reservedModules;
	

	public OverviewSelectionPane() {

	// styling
	this.setVgap(15);
	this.setHgap(10);
	this.setAlignment(Pos.CENTER);
	this.setPadding(new Insets(20));

	// Set Hgrow and Vgrow for columns and rows
	ColumnConstraints column0 = new ColumnConstraints();
	column0.setHalignment(HPos.CENTER);
	column0.setHgrow(Priority.ALWAYS);

	
	

	// Creation of Listviews
	studentProfile = new TextArea();
	selectModules = new TextArea();
	reservedModules = new TextArea();
	
	
	// Setting the TextArea Text
	 studentProfile.setText("Student profile will appear here...");
	 selectModules.setText("Selected modules will appear here...");
	 reservedModules.setText("Reserved modules will appear here...");
	 
	// Enable scrolling for the studentProfile TextArea
     ScrollPane studentProfileScrollPane = new ScrollPane(studentProfile);
     studentProfileScrollPane.setFitToWidth(true);
     studentProfileScrollPane.setFitToHeight(true);

     // initialise the buttons
     saveOverView = new Button("Save Overview");
     // Increase their size
     saveOverView.setPrefSize(100, 10);

     // --- Creation a VBox for the labels and TextArea on the top
     VBox upperBox = new VBox(10);
     upperBox.getChildren().add(studentProfileScrollPane); // Use the ScrollPane here
     upperBox.setAlignment(Pos.TOP_CENTER);

     // --- Creation a VBox for the labels and TextArea on the middle
     HBox middleBox = new HBox(10);
     middleBox.getChildren().addAll(selectModules, reservedModules);
     middleBox.setAlignment(Pos.CENTER);

     VBox buttonsBox = new VBox(10);
     buttonsBox.getChildren().addAll(saveOverView);
     buttonsBox.setAlignment(Pos.CENTER);

     // VBox for the entire layout
     VBox rootBox = new VBox(20); // Spacing between mainBox and buttonsAndCreditsBox

     // Bind the size properties for responsive layout
     rootBox.prefWidthProperty().bind(widthProperty().multiply(0.8));
     rootBox.prefHeightProperty().bind(heightProperty());

     rootBox.getChildren().addAll(upperBox, middleBox, buttonsBox);
     getChildren().add(rootBox);
 }

	public void setOverviewModules(ObservableList<Module> observableArrayList) {
		// TODO Auto-generated method stub
		
	}
}


