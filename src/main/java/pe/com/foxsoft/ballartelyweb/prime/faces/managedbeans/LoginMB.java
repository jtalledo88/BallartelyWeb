package pe.com.foxsoft.ballartelyweb.prime.faces.managedbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

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
			Utilitarios.putObjectInSession("usuario", usuario);
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
	
	public String logOut() {
		Utilitarios.removeObjectInSession("usuario");
		
		return "login";
	}
}
