package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import model.Block;
import model.Course;
import model.Module;
import model.Name;
import model.StudentProfile;
import view.FinalYearOptionsRootPane;
import view.OverviewSelectionPane;
import view.ReserveModulesPane;
import view.SelectModulesPane;
import view.SelectModulesPane.ButtonHandler;
import view.CreateStudentProfilePane;
import view.FinalYearOptionsMenuBar;


public class FinalYearOptionsController  {

	// fields to be used throughout class
	private FinalYearOptionsRootPane view;
	private StudentProfile model;
	private CreateStudentProfilePane cspp;
	private FinalYearOptionsMenuBar mstmb;
	private SelectModulesPane smp;
	private ReserveModulesPane rmp;
	private OverviewSelectionPane osp;

	public FinalYearOptionsController(FinalYearOptionsRootPane view, StudentProfile model) {

		// initialise view and model fields
		this.view = view;
		this.model = model;

		// initialise view subcontainer fields
		cspp = view.getCreateStudentProfilePane();
		mstmb = view.getModuleSelectionToolMenuBar();
		smp = view.getSelectModulesPane();
		rmp = view.getReserveModulesPane();
		osp = view.getOverviewSelectionPane();

		// add courses to combobox in create student profile pane using the
		// buildModulesAndCourses helper method below
		cspp.addCourseDataToComboBox(buildModulesAndCourses());

		// attach event handlers to view using private helper method
		this.attachEventHandlers();

	}

	// helper method - used to attach event handlers
	private void attachEventHandlers() {
		// attach an event handler to the create student profile pane
		cspp.addCreateStudentProfileHandler(new CreateStudentProfileHandler());

		// attach an event handler to the menu bar that closes the application
		mstmb.addExitHandler(e -> System.exit(0));

		// Attach event handlers for the new Select Modules tab
		smp.setAddButtonHandler(new AddButtonHandler());
		smp.setRemoveButtonHandler(new RemoveButtonHandler());
		smp.setResetButtonHandler(new ResetButtonHandler());
		smp.setSubmitButtonHandler(new SubmitButtonHandler());

		// Attaching event handlers for the Reserve Modules tab
		//rmp.setAddButtonHandler(new AddButtonHandler());
	//	rmp.setRemoveButtonHandler(new RemoveButtonHandler());
	//	rmp.setConfirmButtonHandler(new ConfirmButtonHandler());

	}

	// event handler (currently empty), which can be used for creating a profile
	private class CreateStudentProfileHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {

			// ----- Retrieves student data -----///
			String studentPnumber = cspp.getStudentPnumber();
			String studentFirstName = cspp.getStudentName().getFirstName();
			String studentSurname = cspp.getStudentName().getFamilyName();
			String studentEmail = cspp.getStudentEmail();
			Course selectedCourse = cspp.getSelectedCourse();

			// -- Check what this is for ---//
			model.setStudentPnumber(studentPnumber);
			model.setStudentName(new Name(studentFirstName, studentSurname));
			model.setStudentEmail(studentEmail);
			model.setStudentCourse(selectedCourse);

			// ----- Ensuring fields are not empty -----//

			if (studentPnumber.isEmpty() || studentFirstName.isEmpty() || studentSurname.isEmpty()
					|| studentEmail.isEmpty() || selectedCourse == null) {
				showErrorDialog("ERROR", "Please fill in all required fields!");
				return;
			}

			// ----- Creating Student data object --------//
			StudentProfile studentProfile = new StudentProfile();
			studentProfile.setStudentPnumber(studentPnumber);
			studentProfile.getStudentName().setFirstName(studentFirstName);
			studentProfile.getStudentName().setFamilyName(studentEmail);
			studentProfile.setStudentEmail(studentEmail);
			// Add submission date//
			studentProfile.setStudentCourse(selectedCourse);

			// Populate the Select Modules Pane with the initial data
			smp.setModulesForCourse(selectedCourse);

			view.getSelectModulesPane().setModulesForCourse(selectedCourse);

			// ------ Updating the credits after the modules have been added to each
			// ListView -----//
			smp.updateCreditsLabel(smp.getAccumulatedCredits());
			// ------- Change to the 'Select Modules Tab' -----//

			view.changeTab(1);
		}
		// showErrorDialog helper method

		private void showErrorDialog(String title, String content) {
			Alert alert = new Alert(Alert.AlertType.ERROR);

			alert.setTitle(title);
			alert.setHeaderText(null);
			alert.setContentText(content);
			alert.showAndWait();
		}
	}

	// Method to handle the submission of module selection

	private class SubmitButtonHandler implements ButtonHandler {
		public void handle(ActionEvent event) {
			// Get the selected modules from each block
			ObservableList<Module> selectedBlock1Modules = smp.getSelectedBlock1Modules();
			ObservableList<Module> selectedBlock2Modules = smp.getSelectedBlock2Modules();
			ObservableList<Module> selectedBlock3_4Modules = smp.getSelectedBlock3_4Modules();

			// Get the unselected modules from the unselectedBlock3_4Modules
			ObservableList<Module> remainingUnselectedBlock3_4Modules = rmp.getUnselectedBlock3_4Modules();

			// Combine all selected modules into one list
			ObservableList<Module> allSelectedModules = FXCollections.observableArrayList();
			allSelectedModules.addAll(selectedBlock1Modules);
			allSelectedModules.addAll(selectedBlock2Modules);
			allSelectedModules.addAll(selectedBlock3_4Modules);

			rmp.setUnselectedModules(remainingUnselectedBlock3_4Modules);
			// Populate the selected modules list view in the overview selection tab
			osp.setOverviewModules(FXCollections.observableArrayList(allSelectedModules));

			// Additional logic if needed
			// ...

			// Show a confirmation message or perform other actions if required
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Submission Successful");
			alert.setHeaderText(null);
			alert.setContentText("Modules submitted successfully!");
			alert.showAndWait();

			if (smp.getAccumulatedCredits() == 120) {
				// module.forEach
			}
			int totalCredits = smp.getAccumulatedCredits();
			if (totalCredits == 120) {
				view.changeTab(2);
			}
		}
	}

	/*
	 * public void SaveStudentProfileHandler() throws Exception, IOException {
	 * 
	 * StudentProfile studentProfile = m
	 * 
	 * 
	 * try { StudentProfile studentProfile = new StudentProfile(); model =
	 * studentProfile;
	 * 
	 * ObjectOutputStream out = new ObjectOutputStream(new
	 * FileOutputStream("student.dat")); out.writeObject(studentProfile);
	 * out.close();
	 * 
	 * }catch (IOException e) { e.printStackTrace(); showErrorDialog("I/O Error",
	 * "Error saving student profile" ); } catch (NullPointerException e) {
	 * logger.error("Student profile is null") //for(StudentProfile studentProfile )
	 * }
	 * 
	 * }
	 */

	private class AddButtonHandler implements ButtonHandler {
		@Override
		public void handle(ActionEvent event) {
			smp.AddButtonHandler();
		}
	}

	private class RemoveButtonHandler implements ButtonHandler {
		@Override
		public void handle(ActionEvent event) {
			smp.RemoveButtonHandler();
		}
	}

	private class ResetButtonHandler implements ButtonHandler {
		@Override
		public void handle(ActionEvent event) {
			smp.ResetButtonHandler();
		}
	}
	

	

	// helper method - builds modules and course data and returns courses within an
	// array
	private Course[] buildModulesAndCourses() {
		Module ctec3701 = new Module("CTEC3701", "Software Development: Methods & Standards", 30, true, Block.BLOCK_1);

		Module ctec3702 = new Module("CTEC3702", "Big Data and Machine Learning", 30, true, Block.BLOCK_2);
		Module ctec3703 = new Module("CTEC3703", "Mobile App Development and Big Data", 30, true, Block.BLOCK_2);

		Module ctec3451 = new Module("CTEC3451", "Development Project", 30, true, Block.BLOCK_3_4);

		Module ctec3704 = new Module("CTEC3704", "Functional Programming", 30, false, Block.BLOCK_3_4);
		Module ctec3705 = new Module("CTEC3705", "Advanced Web Development", 30, false, Block.BLOCK_3_4);

		Module imat3711 = new Module("IMAT3711", "Privacy and Data Protection", 30, false, Block.BLOCK_3_4);
		Module imat3722 = new Module("IMAT3722", "Fuzzy Logic and Inference Systems", 30, false, Block.BLOCK_3_4);

		Module ctec3706 = new Module("CTEC3706", "Embedded Systems and IoT", 30, false, Block.BLOCK_3_4);

		Course compSci = new Course("Computer Science");
		compSci.addModule(ctec3701);
		compSci.addModule(ctec3702);
		compSci.addModule(ctec3451);
		compSci.addModule(ctec3704);
		compSci.addModule(ctec3705);
		compSci.addModule(imat3711);
		compSci.addModule(imat3722);

		Course softEng = new Course("Software Engineering");
		softEng.addModule(ctec3701);
		softEng.addModule(ctec3703);
		softEng.addModule(ctec3451);
		softEng.addModule(ctec3704);
		softEng.addModule(ctec3705);
		softEng.addModule(ctec3706);

		Course[] courses = new Course[2];
		courses[0] = compSci;
		courses[1] = softEng;

		return courses;
	}

}
