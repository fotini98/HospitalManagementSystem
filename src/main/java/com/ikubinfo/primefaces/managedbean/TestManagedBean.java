package com.ikubinfo.primefaces.managedbean;

import java.io.Serializable;
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

import com.ikubinfo.primefaces.model.Medicine;
import com.ikubinfo.primefaces.service.PrescriptionService;

@ManagedBean
@ViewScoped
public class TestManagedBean implements Serializable {
	private static final long serialVersionUID = 3800933422824282320L;

	private List<Medicine> medicines;

	@ManagedProperty(value = "#{prescriptionService}")
	private PrescriptionService prescriptionService;

	@PostConstruct
	public void init() {
		medicines = prescriptionService.getAllMedicines();
	}

	public List<Medicine> getMedicines() {
		return medicines;
	}

	public void setMedicines(List<Medicine> medicines) {
		this.medicines = medicines;
	}

	public PrescriptionService getPrescriptionService() {
		return prescriptionService;
	}

	public void setPrescriptionService(PrescriptionService prescriptionService) {
		this.prescriptionService = prescriptionService;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void addPanel(ActionEvent event) {
		UIComponent component = FacesContext.getCurrentInstance().getViewRoot().findComponent("myPanelGrid");
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

		
			System.err.println(medicines);

		}
		
		
	}

}
