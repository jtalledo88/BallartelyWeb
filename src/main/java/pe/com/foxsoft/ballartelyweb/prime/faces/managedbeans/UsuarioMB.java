package pe.com.foxsoft.ballartelyweb.prime.faces.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.FacesException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import pe.com.foxsoft.ballartelyweb.jpa.data.Account;
import pe.com.foxsoft.ballartelyweb.jpa.data.Client;
import pe.com.foxsoft.ballartelyweb.jpa.data.User;
import pe.com.foxsoft.ballartelyweb.spring.exception.BallartelyException;
import pe.com.foxsoft.ballartelyweb.spring.service.CuentaService;
import pe.com.foxsoft.ballartelyweb.spring.service.UsuarioService;
import pe.com.foxsoft.ballartelyweb.spring.util.Constantes;
import pe.com.foxsoft.ballartelyweb.spring.util.EncriptacionUtil;
import pe.com.foxsoft.ballartelyweb.spring.util.Utilitarios;

@ManagedBean
@SessionScoped
public class UsuarioMB {

	protected final Logger logger = Logger.getLogger(getClass());
	
	@ManagedProperty("#{usuarioService}")
	private UsuarioService usuarioService;
	
	@ManagedProperty("#{cuentaService}")
	private CuentaService cuentaService;

	private User usuario = new User();
	private List<Account> lstCuentaPrincipal;
	private double saldoCuentaTotal = 10.05;
	
	public UsuarioMB() {
		if(Utilitarios.getObjectInSession(Constantes.SESSION_USUARIO_ATTR) != null) {
			usuario = (User)Utilitarios.getObjectInSession(Constantes.SESSION_USUARIO_ATTR);
		}
		lstCuentaPrincipal = new ArrayList<>();
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
			usuario.setUserPassword(EncriptacionUtil.encriptar(Constantes.ENCRIPTATION_KEY, usuario.getUserPassword()));
			usuario = usuarioService.getUser(usuario);
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
	
	public void editarUsuario() {
		String sMensaje = null;
		if("".equals(usuario.getUserCompleteNames())) {
			Utilitarios.mensajeError("Campos Obligatorios", "Debe llenar los nombres del usuario.");
			return;
		}
		
		try {
			usuarioService.editarUsuario(usuario);
		} catch (BallartelyException e) {
			sMensaje = "Error en editarUsuario";
			this.logger.error(e.getMessage());
			throw new FacesException(sMensaje, e);
		}
	}
	
	public void openVerCuenta() {
		String sMensaje = null;
		try {
			Account account = new Account();
			account.setAccountType(Constantes.ACCOUNT_TYPE_P);
			account.setClient(new Client());
			account.setAccountStatus(Constantes.STATUS_ACTIVE);
			lstCuentaPrincipal = cuentaService.obtenerCuentas(account);
		} catch (BallartelyException e) {
			sMensaje = "Error en editarUsuario";
			this.logger.error(e.getMessage());
			throw new FacesException(sMensaje, e);
		}
	}
	
	public void openVerMovimientos() {
		
	}
	
	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public CuentaService getCuentaService() {
		return cuentaService;
	}

	public void setCuentaService(CuentaService cuentaService) {
		this.cuentaService = cuentaService;
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}
	
	public List<Account> getLstCuentaPrincipal() {
		return lstCuentaPrincipal;
	}

	public void setLstCuentaPrincipal(List<Account> lstCuentaPrincipal) {
		this.lstCuentaPrincipal = lstCuentaPrincipal;
	}

	public double getSaldoCuentaTotal() {
		return saldoCuentaTotal;
	}

	public void setSaldoCuentaTotal(double saldoCuentaTotal) {
		this.saldoCuentaTotal = saldoCuentaTotal;
	}
	
}
