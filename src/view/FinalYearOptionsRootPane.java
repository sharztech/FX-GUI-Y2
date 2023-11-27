package view;

import javafx.animation.FadeTransition;
import javafx.scene.control.Tab;

import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;


public class FinalYearOptionsRootPane extends BorderPane {

	private CreateStudentProfilePane cspp;
	private FinalYearOptionsMenuBar mstmb;
	private TabPane tp;
	private SelectModulesPane smp;
	private ReserveModulesPane rmp;
	private OverviewSelectionPane osp;
	
	public FinalYearOptionsRootPane() {
		//create tab pane and disable tabs from being closed
		tp = new TabPane();
		tp.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		
		//create panes
		cspp = new CreateStudentProfilePane();
		 smp = new SelectModulesPane();
		 rmp = new ReserveModulesPane();
		 osp = new OverviewSelectionPane();
		
		//create tabs with panes added
		Tab t1 = new Tab("Create Profile", cspp);
		Tab t2 = new Tab("Select Modules", smp);
		Tab t3 = new Tab("Reserve Modules", rmp);
		Tab t4 = new Tab("Overview Selection", osp);
		

		//add tabs to tab pane
		tp.getTabs().addAll(t1, t2, t3, t4);
		
		//create menu bar
		mstmb = new FinalYearOptionsMenuBar();
		
		//add menu bar and tab pane to this root pane
		this.setTop(mstmb);
		this.setCenter(tp);
		
		
		//Attaching a listener so that it knows to change the credit code
		
	}
		
		// EDIT BELOW!
		// Experimentation of tab transitions
		
	//	FadeTransition fadetransition = new FadeTransition();
	//	fadetransition.setFromValue(0.0);
		//fadetransition.setToValue(0.1);
	
	
	/*Event Handler for the tab transition
		tp.getSelectionModel().selectedItemProperty().addListener(observable, oldTab, newTab)-> { 
			if(newTab != null) {
				fadetransition.playFromStaart();
			}
			
		};
	}*/

	//methods allowing sub-containers to be accessed by the controller.
	public CreateStudentProfilePane getCreateStudentProfilePane() {
		return cspp;
	}
	
	public FinalYearOptionsMenuBar getModuleSelectionToolMenuBar() {
		return mstmb;
	}
	
	public SelectModulesPane getSelectModulesPane() {
		return smp;
	}
	
	public ReserveModulesPane getReserveModulesPane() {
		return rmp;
	}
	
	public OverviewSelectionPane getOverviewSelectionPane() {
		return osp;
	}
	
	//method to allow the controller to change tabs
	public void changeTab(int index) {
		tp.getSelectionModel().select(index);
	}
	
}
