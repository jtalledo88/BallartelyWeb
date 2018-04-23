package pe.com.foxsoft.reportsweb.prime.faces.managedbeans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import pe.com.foxsoft.reportsweb.jpa.data.Usuario;
import pe.com.foxsoft.reportsweb.spring.exception.BallartelyException;
import pe.com.foxsoft.reportsweb.spring.service.LoginService;

@ManagedBean
@SessionScoped
public class LoginMB {

	@ManagedProperty("#{loginService}")
	private LoginService loginService;

	private Usuario user = new Usuario();

	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	public String logIn() {
		try {
			user = loginService.getUser(user);
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("The User "+this.user.getUserCompleteNames()+" Exists"));
		} catch (BallartelyException e) {
			if(e.getType() == BallartelyException.NO_RESULT_ERROR) {
				FacesContext.getCurrentInstance().addMessage(null, 
						new FacesMessage("The User Doesn't Exists"));
			}
			return "";
		}
		
		return "welcome";
	}
}
