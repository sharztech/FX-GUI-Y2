package view;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Block;
import model.Course;
import model.Module;

public class SelectModulesPane extends GridPane {

	private Button addBtn, removeBtn, resetBtn, submitBtn;
	private TextField creditsTextField;
	private ListView<Module> selectedBlock1ListView, selectedBlock2ListView, unselectedBlock3_4ListView,
			selectedBlock3_4ListView;

	public SelectModulesPane() {

		// styling
		this.setVgap(15);
		this.setHgap(10);
		this.setAlignment(Pos.CENTER);

		// Set padding
		this.setPadding(new Insets(20));

		// create labels
		Label lblSelectedBlock1 = new Label("Selected Block 1 modules ");
		Label lblSelectedBlock2 = new Label("Selected Block 2 modules ");
		Label lblUnselectedBlock3_4 = new Label("Unselected Block 3/4 modules ");
		Label lblSelectedBlock3_4 = new Label("Selected Block 3/4 modules ");
		Label lblSelection = new Label("Block 3/4");
		Label lblCredits = new Label("Current credits: ");

		// Sets the credits label
		creditsTextField = new TextField("0");
		creditsTextField.setPrefWidth(40);
		creditsTextField.setPrefColumnCount(10);

		// Create LiewViews

		selectedBlock1ListView = new ListView<>();
		selectedBlock2ListView = new ListView<>();
		unselectedBlock3_4ListView = new ListView<>();
		selectedBlock3_4ListView = new ListView<>();

		// initialise the buttons
		addBtn = new Button("Add");
		removeBtn = new Button("Remove");
		resetBtn = new Button("Reset");
		submitBtn = new Button("Submit");

		// --- Create a VBox for the labels and ListViews on the left
		VBox leftBox = new VBox(10); // Spacing between components
		leftBox.getChildren().addAll(lblSelectedBlock1, selectedBlock1ListView, lblSelectedBlock2,
				selectedBlock2ListView);
		leftBox.setAlignment(Pos.CENTER_LEFT);

		// --- Create an HBox for the buttons in the centre
		HBox buttonsBox = new HBox(10); // Spacing between buttons
		buttonsBox.getChildren().addAll(lblSelection, addBtn, removeBtn);
		buttonsBox.setAlignment(Pos.CENTER);

		// --- Create another VBox for the labels and ListViews on the right upper
		// section
		VBox rightUpperBox = new VBox(10); // Spacing between components
		rightUpperBox.getChildren().addAll(lblUnselectedBlock3_4, unselectedBlock3_4ListView

		);
		rightUpperBox.setAlignment(Pos.TOP_LEFT);

		// --- Create another VBox for the labels and ListViews in the middle right
		// section
		VBox rightMiddleBox = new VBox(10); // Spacing between components
		rightMiddleBox.getChildren().addAll(

				buttonsBox // Move buttonsBox here
		);
		rightMiddleBox.setAlignment(Pos.CENTER_RIGHT);

		// --- Create another VBox for the labels and ListViews in the lower right
		// section

		VBox rightLowerBox = new VBox(10); // Spacing between components
		rightLowerBox.getChildren().addAll(lblSelectedBlock3_4, selectedBlock3_4ListView

		);
		rightLowerBox.setAlignment(Pos.BOTTOM_LEFT);

		// --- Create another VBox to structure right column --- /
		VBox rightBox = new VBox(10); // Spacing between components
		rightBox.getChildren().addAll(

				rightUpperBox, rightMiddleBox, rightLowerBox);
		rightBox.setAlignment(Pos.CENTER);

		// Create a new VBox for the credits labels
		HBox creditsBox = new HBox(10);
		creditsBox.getChildren().addAll(lblCredits, creditsTextField);
		creditsBox.setAlignment(Pos.CENTER);

		// Create a new HBox for the buttons (resetBtn and submitBtn) underneath
		// creditsBox

		HBox bottomButtonsBox = new HBox(40);
		bottomButtonsBox.getChildren().addAll(resetBtn, submitBtn);
		bottomButtonsBox.setAlignment(Pos.BOTTOM_CENTER);

		// Create a new HBox for the entire layout
		HBox mainBox = new HBox(20); // Spacing between left, centre, and right boxes
		mainBox.getChildren().addAll(leftBox, rightBox);
		mainBox.setAlignment(Pos.CENTER);

		// Create a new VBox for the entire layout
		VBox rootBox = new VBox(20); // Spacing between mainBox and buttonsAndCreditsBox
		rootBox.getChildren().addAll(mainBox, creditsBox, bottomButtonsBox);
		rootBox.setAlignment(Pos.CENTER);

		getChildren().add(rootBox); // Add the root VBox to the GridPane

		// Set up button actions (you need to implement the logic)
		addBtn.setOnAction(event -> AddButtonHandler());
		removeBtn.setOnAction(event -> RemoveButtonHandler());
		resetBtn.setOnAction(event -> ResetButtonHandler());
	//	submitBtn.setOnAction(event -> SubmitButtonHandler());

		// Bind the size properties for responsive layout
		leftBox.prefWidthProperty().bind(widthProperty().multiply(0.5));
		rightBox.prefWidthProperty().bind(widthProperty().multiply(0.5));

		updateCreditsLabel(calculateAccumulatedCredits());
	}

	

	public void updateCreditsLabel(int totalCredits) {
		setCredits(Integer.toString(totalCredits));
	}

	public void AddButtonHandler() {
		Module selectedModule = unselectedBlock3_4ListView.getSelectionModel().getSelectedItem();
		if (selectedModule != null) {

			int currentCredits = calculateAccumulatedCredits();
			if (currentCredits + selectedModule.getModuleCredits() > 120) {
				showErrorDialog("Credit limit has been reached",
						" Maximum modules picked, please reconsider you choices or move onto the next stage of the process");
				return;
			}
			// Add the selected module to the selectedBlock3_4ListView
			selectedBlock3_4ListView.getItems().add(selectedModule);

			// Remove the selected module from the unselectedBlock3_4ListView
			unselectedBlock3_4ListView.getItems().remove(selectedModule);

			updateCreditsLabel(calculateAccumulatedCredits());

		}
	}

	public void RemoveButtonHandler() {
		Module selectedModule = selectedBlock3_4ListView.getSelectionModel().getSelectedItem();
		if (selectedModule != null && !selectedModule.getModuleCode().equals("CTEC3451")) {
			// Remove the selected module from the selectedBlock3_4ListView
			selectedBlock3_4ListView.getItems().remove(selectedModule);

			// Add the selected module to the unselectedBlock3_4ListView
			unselectedBlock3_4ListView.getItems().add(selectedModule);

			// Update the credits label
			updateCreditsLabel(calculateAccumulatedCredits());
		}
	}

	public void ResetButtonHandler() {
		// Clear all list views and reset credits label
		selectedBlock1ListView.getItems().clear();
		selectedBlock2ListView.getItems().clear();
		unselectedBlock3_4ListView.getItems().clear();
		selectedBlock3_4ListView.getItems().clear();
		updateCreditsLabel(0);
	}

	// methods to update the content of this pane
	public void setSelectedBlock1Modules(ObservableList<Module> modules) {
		selectedBlock1ListView.setItems(modules);
	}

	public void setUnselectedBlock3_4Modules(ObservableList<Module> modules) {
		unselectedBlock3_4ListView.setItems(modules);
	}

	public void setSelectedBlock2Modules(ObservableList<Module> modules) {
		selectedBlock2ListView.setItems(modules);
	}

	public void setSelectedBlock3_4Modules(ObservableList<Module> modules) {
		selectedBlock3_4ListView.setItems(modules);
	}

	public void setCredits(String credits) {
		creditsTextField.setText(String.valueOf(credits));
	}

	public void setAddButtonHandler(ButtonHandler handler) {
		addBtn.setOnAction(handler);
	}

	public void setRemoveButtonHandler(ButtonHandler handler) {
		removeBtn.setOnAction(handler);
	}

	public void setResetButtonHandler(ButtonHandler handler) {
		resetBtn.setOnAction(handler);
	}

	public ObservableList<Module> getSelectedBlock1Modules() {
		return selectedBlock1ListView.getItems();
	}

	public ObservableList<Module> getSelectedBlock2Modules() {
		return selectedBlock2ListView.getItems();
	}

	public ObservableList<Module> getSelectedBlock3_4Modules() {
		return selectedBlock3_4ListView.getItems();
	}

	public ObservableList<Module> getUnselectedBlock3_4Modules() {
		return unselectedBlock3_4ListView.getItems();
	}

	public void clearSelectedModule() {
		selectedBlock3_4ListView.getItems().clear();
		
	}
	public void clearSelectedModules() {
		selectedBlock3_4ListView.getItems().clear();
		selectedBlock2ListView.getItems().clear();
		selectedBlock1ListView.getItems().clear();
	}
	


	private int calculateAccumulatedCredits() {
		int credits = 0;

		// Calculate credits for Block 1 compulsory modules
		credits += getModuleCredits(selectedBlock1ListView.getItems(), Block.BLOCK_1);

		// Calculate credits for Block 2 compulsory modules
		credits += getModuleCredits(selectedBlock2ListView.getItems(), Block.BLOCK_2);

		// Calculate credits for Block 3/4 compulsory modules
		credits += getModuleCredits(selectedBlock3_4ListView.getItems(), Block.BLOCK_3_4);

		System.out.println("Total Credits: " + credits);
		return credits;
	}

	public int getAccumulatedCredits() {
		return calculateAccumulatedCredits();
	}

	// Utility method to calculate credits for compulsory modules in a block
	private int getModuleCredits(ObservableList<Module> selectedModules, Block block) {
		int credits = 0;
		int compulsoryModulesCount = 0;

		for (Module module : selectedModules) {
			// Check if the module is in the specified block and is compulsory
			if (module.getRunPlan() == block || module.isMandatory()) {
				credits += module.getModuleCredits();
				compulsoryModulesCount++;
			}
		}

		// Ensure that there are exactly 3 compulsory modules for the block
		if (compulsoryModulesCount != 1) {
			// Handle error or log a message
			System.err.println("Error: Invalid number of compulsory modules for block " + block);
		}

		return credits;
	}

	// ----- Populating listviews with the required data ------//

	public void setModulesForCourse(Course course) {
		ObservableList<Module> selectedBlock1Modules = FXCollections.observableArrayList();
		ObservableList<Module> selectedBlock2Modules = FXCollections.observableArrayList();
		ObservableList<Module> selectedBlock3_4Modules = FXCollections.observableArrayList();
		ObservableList<Module> unselectedBlock3_4Modules = FXCollections.observableArrayList();

		for (Module module : course.getAllModulesOnCourse()) {
			if (module.getRunPlan() == Block.BLOCK_1) {
				selectedBlock1Modules.add(module);
			} else if (module.getRunPlan() == Block.BLOCK_2) {
				selectedBlock2Modules.add(module);
			} else if (module.getRunPlan() == Block.BLOCK_3_4) {

				if (!module.getModuleCode().equals("CTEC3451")) {
					unselectedBlock3_4Modules.add(module);
				}

				if (module.getModuleCode().equals("CTEC3451")) {
					selectedBlock3_4Modules.add(module);
				}
			} else {
				selectedBlock3_4Modules.add(module);
			}
		}

		selectedBlock1ListView.setItems(selectedBlock1Modules);
		selectedBlock2ListView.setItems(selectedBlock2Modules);
		selectedBlock3_4ListView.setItems(selectedBlock3_4Modules);
		unselectedBlock3_4ListView.setItems(unselectedBlock3_4Modules);
	}



	public interface ButtonHandler extends javafx.event.EventHandler<javafx.event.ActionEvent> {

	}

	public void setSubmitButtonHandler(ButtonHandler handler) {

		submitBtn.setOnAction(handler);
		
	}

	private void showErrorDialog(String title, String content) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}

}
