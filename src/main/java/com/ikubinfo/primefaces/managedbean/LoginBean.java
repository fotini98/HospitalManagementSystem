package com.ikubinfo.primefaces.managedbean;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.ikubinfo.primefaces.model.Employee;
import com.ikubinfo.primefaces.service.LoginService;
import com.ikubinfo.primefaces.util.Messages;
 
@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {
 
    private static final long serialVersionUID = 1L;
    private String password;
    private String email;
    private String id;
    private Employee employee;
    
    @ManagedProperty(value = "#{loginService}")
	private LoginService loginService;
	
	@ManagedProperty(value = "#{messages}")
	private Messages messages;
	
	public String login() {
        employee = loginService.logInEmployee(email, password);
        if (employee!=null) {

      
            HttpSession session =  (HttpSession)
    				FacesContext.
    				getCurrentInstance().
    				getExternalContext().
    				getSession(false);
            session.setAttribute("id", employee.getEmployeeId()); 
           if(employee.getRoleId()==1) {
        	   return "adminDashboard.xhtml?faces-redirect=true";
           }else if(employee.getRoleId()==2) {
        	   return "doctorDashboard.xhtml?faces-redirect=true";
           }else if(employee.getRoleId()==3) {
        	   return "receptionistDashboard.xhtml?faces-redirect=true";
           }else if(employee.getRoleId()==4) {
        	   return "patientDashboard.xhtml?faces-redirect=true";
           }else if(employee.getRoleId()==5) {
        	   return "nurseDashboard.xhtml?faces-redirect=true";
           }
        } else {
        	messages.showFatalMessage("Invalid Login! Please Try Again!");
 
        }
		return "login";
    }
 
    public String logout() {
    	System.out.println("invoked");
      HttpSession session =  (HttpSession)
				FacesContext.
				getCurrentInstance().
				getExternalContext().
				getSession(false);
      session.invalidate();
      return "login?faces-redirect=true";
   }
    
    public void loginValidation() throws IOException {
    	if(employee!=null) {
    		if(employee.getRoleId()==1) {
         	 //  return "adminDashboard.xhtml?faces-redirect=true";
    			 FacesContext.getCurrentInstance().getExternalContext().redirect("adminDashboard.xhtml");
            }else if(employee.getRoleId()==2) {
            	FacesContext.getCurrentInstance().getExternalContext().redirect("doctorDashboard.xhtml");
         	 //  return "doctorDashboard.xhtml?faces-redirect=true";
            }else if(employee.getRoleId()==3) {
         	//   return "receptionistDashboard.xhtml?faces-redirect=true";
            }else if(employee.getRoleId()==4) {
         	//   return "patientDashboard.xhtml?faces-redirect=true";
            }else if(employee.getRoleId()==5) {
         	//   return "nurseDashboard.xhtml?faces-redirect=true";
            }
    	} 
    		// return "login?faces-redirect=true";
    	
    }
    
    public void doctorValidation(){
    	if(employee.getRoleId()==2) {
    		
    	}
    }
    

 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
    
 
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public Messages getMessages() {
		return messages;
	}

	public void setMessages(Messages messages) {
		this.messages = messages;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}


}