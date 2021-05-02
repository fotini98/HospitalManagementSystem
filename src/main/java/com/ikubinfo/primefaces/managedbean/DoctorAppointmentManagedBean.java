package com.ikubinfo.primefaces.managedbean;


import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.component.panel.Panel;
import org.primefaces.component.selectonemenu.SelectOneMenu;

import com.ikubinfo.primefaces.model.Appointment;
import com.ikubinfo.primefaces.model.Employee;
import com.ikubinfo.primefaces.model.Medicine;
import com.ikubinfo.primefaces.model.Patient;
import com.ikubinfo.primefaces.model.Prescription;
import com.ikubinfo.primefaces.service.AppointmentService;
import com.ikubinfo.primefaces.service.PrescriptionService;
import com.ikubinfo.primefaces.util.Messages;

@ManagedBean
@ViewScoped
public class DoctorAppointmentManagedBean {
	
	private Employee doctor;
	private Appointment appointment;
	private List<Appointment> appointments;
	private List<Patient> patients;
	private Patient patient;//??
	private Prescription prescription;
	
	private List<Medicine> medicines;
	
	@ManagedProperty(value = "#{appointmentService}")
	private AppointmentService service;
	
	@ManagedProperty(value = "#{loginBean}")
	private LoginBean loginBean;
	
	@ManagedProperty(value = "#{prescriptionService}")
	private PrescriptionService prescriptionService;
	
	@ManagedProperty(value="#{messages}")
	private Messages message;
	
	
	@PostConstruct
	public void init() {
		System.err.println("doctor appoint managed bean post construct");
		System.out.println(loginBean.getEmployee().getFullName());
		doctor= loginBean.getEmployee();
		appointment=new Appointment();
		appointments=service.getAllDoctorAppointment(doctor.getEmployeeId());
		patients=service.getPatients();
		medicines = prescriptionService.getAllMedicines();
		patient=new Patient();
	}
	
	public void create() {
		appointment.setPatient(patient);
		appointment.setDoctorId(doctor.getEmployeeId());
		if(service.create(appointment)) {
			appointments=service.getAllDoctorAppointment(doctor.getEmployeeId());
			message.showInfoMessage("Appointment Added Successfully!");
		}else {
			message.showFatalMessage("Something went wrong!!");
		}
	}
	
	public void update() {
		appointment.setPatient(patient);
		appointment.setDoctorId(doctor.getEmployeeId());
		if(service.updateAppointment(appointment)) {
			appointments=service.getAllDoctorAppointment(doctor.getEmployeeId());
			message.showInfoMessage("Appointment updated Successfully!");
		}else {
			message.showFatalMessage("Something went wrong!!");
		}
	}
	
	public void delete() {
		if(service.delete(appointment)) {
			appointments=service.getAllDoctorAppointment(doctor.getEmployeeId());
			message.showInfoMessage("Appointment deleted Successfully!");
		}else {
			message.showFatalMessage("Something went wrong!!");
		}
	}
	
	public void markAsCompleted() {
		System.out.println(" mark as complted invoked");
		
		if(service.markAsCompleted(appointment)) {
			message.showInfoMessage("Appointment Status changed to Completed Successfully!");
		}else {
			message.showFatalMessage("Something went wrong!!");
		}
	}
	
	public void addMedicinePanel(ActionEvent event) {
		UIComponent component = FacesContext.getCurrentInstance().getViewRoot().findComponent("myPanelGrid");
		System.out.println(component);
		if (component != null) {
			Panel p = new Panel();
			p.setClosable(true);
			p.setHeader("");
			p.setVisible(true);
			
	
			OutputLabel medicine = new OutputLabel();
			medicine.setValue("Medicine  ");
		//	medicine.setFor("medicine");

			OutputLabel dose = new OutputLabel();
			dose.setValue("Dose  ");
			InputText doseTxt=new InputText();
		

			OutputLabel duration = new OutputLabel();
			duration.setValue("Duration  ");
			InputText durationTxt=new InputText();
			SelectOneMenu selectMedicine = new SelectOneMenu();

			UISelectItems selectOptions = new UISelectItems();
			selectOptions.setValue(medicines);
			
			selectMedicine.getChildren().add(selectOptions);
			//selectMedicine.setId("medicine");
			selectMedicine.setRequired(false);

			component.getChildren().add(p);
			p.getChildren().add(medicine);
			p.getChildren().add(selectMedicine);
			p.getChildren().add(dose);
			p.getChildren().add(doseTxt);
			p.getChildren().add(duration);
			p.getChildren().add(durationTxt);
component.processUpdates(null);
		
			System.err.println(medicines);

		}
		
		
	}


	public Employee getDoctor() {
		return doctor;
	}


	public void setDoctor(Employee doctor) {
		this.doctor = doctor;
	}


	public Appointment getAppointment() {
		return appointment;
	}


	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}


	public List<Appointment> getAppointments() {
		return appointments;
	}


	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}


	public AppointmentService getService() {
		return service;
	}


	public void setService(AppointmentService service) {
		this.service = service;
	}

	public List<Patient> getPatients() {
		return patients;
	}

	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}

	public Messages getMessage() {
		return message;
	}

	public void setMessage(Messages message) {
		this.message = message;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Prescription getPrescription() {
		return prescription;
	}

	public void setPrescription(Prescription prescription) {
		this.prescription = prescription;
	}

	public PrescriptionService getPrescriptionService() {
		return prescriptionService;
	}

	public void setPrescriptionService(PrescriptionService prescriptionService) {
		this.prescriptionService = prescriptionService;
	}

	public List<Medicine> getMedicines() {
		return medicines;
	}

	public void setMedicines(List<Medicine> medicines) {
		this.medicines = medicines;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}
	
	
	

}
