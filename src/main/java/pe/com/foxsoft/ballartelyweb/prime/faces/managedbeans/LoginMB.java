package pe.com.foxsoft.ballartelyweb.prime.faces.managedbeans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import pe.com.foxsoft.ballartelyweb.jpa.data.Usuario;
import pe.com.foxsoft.ballartelyweb.spring.exception.BallartelyException;
import pe.com.foxsoft.ballartelyweb.spring.service.LoginService;
import pe.com.foxsoft.ballartelyweb.spring.util.Utilitarios;

@ManagedBean
@SessionScoped
public class LoginMB {

	@ManagedProperty("#{loginService}")
	private LoginService loginService;

	private Usuario usuario = new Usuario();

	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String logIn() {
		try {
			if("".equals(usuario.getUsuario())) {
				Utilitarios.mensajeError("Campos Obligatorios", "Debe llenar el usuario.");
				return "";
			}
			if("".equals(usuario.getContrasenia())) {
				Utilitarios.mensajeError("Campos Obligatorios", "Debe llenar la contraseña.");
				return "";
			}
			
			usuario.setUsuario(usuario.getUsuario().toUpperCase());
			usuario = loginService.getUser(usuario);
		} catch (BallartelyException e) {
			if(e.getType() == BallartelyException.NO_RESULT_ERROR) {
				FacesContext.getCurrentInstance().addMessage(null, 
						new FacesMessage("Usuario y/o contraseña incorrectos."));
			}
			return "";
		}
		
		return "bienvenido";
	}
}
