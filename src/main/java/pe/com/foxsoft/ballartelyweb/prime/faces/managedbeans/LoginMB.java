package pe.com.foxsoft.ballartelyweb.prime.faces.managedbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import pe.com.foxsoft.ballartelyweb.jpa.data.User;
import pe.com.foxsoft.ballartelyweb.spring.exception.BallartelyException;
import pe.com.foxsoft.ballartelyweb.spring.service.LoginService;
import pe.com.foxsoft.ballartelyweb.spring.util.Constantes;
import pe.com.foxsoft.ballartelyweb.spring.util.Utilitarios;

@ManagedBean
@SessionScoped
public class LoginMB {

	@ManagedProperty("#{loginService}")
	private LoginService loginService;

	private User usuario = new User();

	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public String logIn() {
		try {
			if("".equals(usuario.getUserName())) {
				Utilitarios.mensajeError("Campos Obligatorios", "Debe llenar el usuario.");
				return "";
			}
			if("".equals(usuario.getUserPassword())) {
				Utilitarios.mensajeError("Campos Obligatorios", "Debe llenar la contraseña.");
				return "";
			}
			
			usuario.setUserName(usuario.getUserName().toUpperCase());
			usuario = loginService.getUser(usuario);
			Utilitarios.putObjectInSession(Constantes.SESSION_USUARIO_ATTR, usuario);
		} catch (BallartelyException e) {
			if(e.getType() == BallartelyException.NO_RESULT_ERROR) {
				Utilitarios.mensajeError("", "Usuario y/o contraseña incorrectos.");
			}else {
				Utilitarios.mensajeError("", "Ocurrió un error: " + e.getMessage());
			}
			return "";
		}
		
		return "bienvenido";
	}
	
	public void logOut() {
		Utilitarios.removeObjectInSession(Constantes.SESSION_USUARIO_ATTR);
		Utilitarios.redirectPage("/index.jsp");
	}
}
