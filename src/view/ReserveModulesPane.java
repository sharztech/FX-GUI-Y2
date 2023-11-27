package view;

import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Module;
import view.SelectModulesPane.ButtonHandler;

public class ReserveModulesPane extends GridPane {

	private Button addBtn, removeBtn, confirmBtn;
	private ListView<Module> unselectedBlock3_4ListView, reservedListView;
	private SelectModulesPane smp;

	public ReserveModulesPane() {

		// styling
		this.setVgap(15);
		this.setHgap(10);
		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(20));

		// Set Hgrow and Vgrow for columns and rows
		ColumnConstraints column0 = new ColumnConstraints();
		column0.setHalignment(HPos.RIGHT);
		column0.setHgrow(Priority.ALWAYS);

		ColumnConstraints column1 = new ColumnConstraints();
		column1.setHalignment(HPos.LEFT);
		column1.setHgrow(Priority.ALWAYS);

		// Creation of labels

		Label lblUnselectedBlock3_4 = new Label("Unselected Block 3/4 modules ");
		Label lblReservedModules = new Label("Reserved Block 3/4 modules ");
		Label lblReserves = new Label("Reserve an optional module ");

		// Creation of Listviews
		unselectedBlock3_4ListView = new ListView<>();
		reservedListView = new ListView<>();

		// initialise the buttons
		addBtn = new Button("Add");
		removeBtn = new Button("Remove");
		confirmBtn = new Button("Confirm");

		// Increase their size
		addBtn.setPrefSize(100, 50);
		removeBtn.setPrefSize(100, 50);
		confirmBtn.setPrefSize(100, 50);

		// --- Create a VBox for the labels and ListViews on the left
		VBox leftBox = new VBox(10); 
		leftBox.getChildren().addAll(lblUnselectedBlock3_4, unselectedBlock3_4ListView);
		leftBox.setAlignment(Pos.CENTER_LEFT);

		// --- Create a VBox for the labels and ListViews on the right
		VBox rightBox = new VBox(10); 
		rightBox.getChildren().addAll(lblReservedModules, reservedListView);
		rightBox.setAlignment(Pos.CENTER_LEFT);

		HBox buttonsBox = new HBox(10);
		buttonsBox.getChildren().addAll(lblReserves, addBtn, removeBtn, confirmBtn);
		buttonsBox.setAlignment(Pos.BOTTOM_CENTER);

		// Create a new HBox for the entire layout
		HBox mainBox = new HBox(20); // Spacing between left, center, and right boxes
		mainBox.getChildren().addAll(leftBox, rightBox);
		mainBox.setAlignment(Pos.CENTER);

		// Create a new VBox for the entire layout
		VBox rootBox = new VBox(20); // Spacing between mainBox and buttonsAndCreditsBox

		// Sets the height of the rootBox
		rootBox.setPrefHeight(600);

		// I then increased the spacing between the components

		VBox.setMargin(mainBox, new Insets(10, 0, 10, 0));

		rootBox.getChildren().addAll(mainBox, buttonsBox);
		getChildren().add(rootBox);

		// Bind the size properties for responsive layout
		leftBox.prefWidthProperty().bind(widthProperty().multiply(0.8));
		leftBox.prefHeightProperty().bind(heightProperty());
		rightBox.prefWidthProperty().bind(widthProperty().multiply(0.8));
		rightBox.prefHeightProperty().bind(heightProperty());
		// ----- Setting event handlers for the buttons -------//

		// Setting the buttons up
		addBtn.setOnAction(event -> AddButtonHandler());
		removeBtn.setOnAction(event -> RemoveButtonHandler());
		confirmBtn.setOnAction(event -> ConfirmButtonHandler());
	}

	public void setUnselectedModules(ObservableList<Module> unselectedBlock3_4Modules) {

		unselectedBlock3_4ListView.setItems(unselectedBlock3_4Modules);

	}

	public void setSelectedModulesPane(SelectModulesPane smp) {
		this.smp = smp;
	}

	
	public void setConfirmButtonHandler(ConfirmButtonHandler handler) {
		confirmBtn.setOnAction(event -> handler.handle(this));
	}

	public interface ConfirmButtonHandler {
		void handle(ReserveModulesPane reserveModulesPane);
	}

	public void ConfirmButtonHandler() {
		// Implement your logic to capture details of the reserved module
		Module reservedModule = reservedListView.getSelectionModel().getSelectedItem();

		if (reservedModule != null) {
			
		}
	}

	public void setAddButtonHandler(ButtonHandler handler) {
		addBtn.setOnAction(handler);
	}
	

	public void AddButtonHandler() {
		Module selectedModule = unselectedBlock3_4ListView.getSelectionModel().getSelectedItem();
		if (selectedModule != null) {

			// Add the selected module to the selectedBlock3_4ListView
			reservedListView.getItems().add(selectedModule);

			// Remove the selected module from the unselectedBlock3_4ListView
			unselectedBlock3_4ListView.getItems().remove(selectedModule);

		}
	}

	public void RemoveButtonHandler() {
		Module selectedModule = unselectedBlock3_4ListView.getSelectionModel().getSelectedItem();
		if (selectedModule != null) {
			// Add the selected module to the reservedListView
			reservedListView.getItems().remove(selectedModule);

			// Remove the selected module to the unselectedBlock3_4ListView
			unselectedBlock3_4ListView.getItems().add(selectedModule);

		}
	}

	public ObservableList<Module> getUnselectedBlock3_4Modules() {
		// Creates a new observable list to store this new instance of remainding
		// modules
		ObservableList<Module> remainingModules = FXCollections.observableArrayList();

		// Adds all of the modules from the unselectedBlock3_4ListView
		remainingModules.addAll(unselectedBlock3_4ListView.getItems());

		// Removes the selected modules from the new instance

		ObservableList<Module> selectedModules = reservedListView.getItems();

		remainingModules.removeAll(selectedModules);

		return remainingModules;

	}

}