package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;


public class StudentProfile implements Serializable {

	private String studentPnumber;
	private Name studentName;
	private String studentEmail;
	private LocalDate studentDate;
	private Course studentCourse;
	private Set<Module> selectedModules;
	private Set<Module> reservedModules;
	
	public StudentProfile() {
		studentPnumber = "";
		studentName = new Name();
		studentEmail = "";
		studentDate = null;
		studentCourse = null;
		selectedModules = new TreeSet<Module>();
		reservedModules = new TreeSet<Module>();
	}
	
	public String getStudentPnumber() {
		return studentPnumber;
	}
	
	public void setStudentPnumber(String studentPnumber) {
		this.studentPnumber = studentPnumber;
	}
	
	public Name getStudentName() {
		return studentName;
	}
	
	public void setStudentName(Name studentName) {
		this.studentName = studentName;
	}
	
	public String getStudentEmail() {
		return studentEmail;
	}
	
	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}
	
	public LocalDate getSubmissionDate() {
		return studentDate;
	}
	
	public void setSubmissionDate(LocalDate studentDate) {
		this.studentDate = studentDate;
	}
	
	public Course getStudentCourse() {
		return studentCourse;
	}
	
	public void setStudentCourse(Course studentCourse) {
		this.studentCourse = studentCourse;
	}
	
	public boolean addSelectedModule(Module m) {
		return selectedModules.add(m);
	}
	
	public Set<Module> getAllSelectedModules() {
		return selectedModules;
	}
	
	public void clearSelectedModules() {
		selectedModules.clear();
	}
	
	public boolean addReservedModule(Module m) {
		return reservedModules.add(m);
	}
	
	public Set<Module> getAllReservedModules() {
		return reservedModules;
	}
	
	public void clearReservedModules() {
		reservedModules.clear();
	}
	// Calculate the total credits based on selected modules
    public int calculateTotalCredits() {
        int totalCredits = 0;

        // Assuming you have a method to get credits from the selected modules
        totalCredits += calculateCreditsFromSet(selectedModules);
        totalCredits += calculateCreditsFromSet(reservedModules);

        // Ensure the total credits do not surpass 120
        return Math.min(totalCredits, 120);
    }

    // Calculate credits from a set of modules
    private int calculateCreditsFromSet(Set<Module> modules) {
        int credits = 0;
        for (Module module : modules) {
            credits += module.getModuleCredits();
        }
        return credits;
    }
	
	@Override
	public String toString() {
		return "StudentProfile:[Pnumber=" + studentPnumber + ", studentName="
				+ studentName + ", studentEmail=" + studentEmail + ", studentDate="
				+ studentDate + ", studentCourse=" + studentCourse.actualToString() 
				+ ", selectedModules=" + selectedModules
				+ ", reservedModules=" + reservedModules + "]";
	}
	
}
