package pe.com.foxsoft.reportsweb.jpa.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name="user")
public class Usuario {
	public static final String USER_ID = "idUsuario";
	public static final String USER_NAME = "usuario";
	public static final String USER_PASSWORD = "contrasenia";
	public static final String USER_COMPLETE_NAMES = "nombresUsuario";
	public static final String USER_CREATION_DATE = "fecCreacion";
	public static final String USER_MODIFICATION_DATE = "fecModificacion";
	
	@Id
	@Column(name="USER_ID")
	private long idUsuario;
	@Column(name="USER_NAME")
	private String usuario;
	@Column(name="USER_PASSWORD")
	private String contrasenia;
	@Column(name="USER_COMPLETE_NAMES")
	private String nombresUsuario;
	@Column(name="USER_CREATION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecCreacion;
	@Column(name="USER_MODIFICATION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecModificacion;
	
	public long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getContrasenia() {
		return contrasenia;
	}
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	public String getNombresUsuario() {
		return nombresUsuario;
	}
	public void setNombresUsuario(String nombresUsuario) {
		this.nombresUsuario = nombresUsuario;
	}
	public Date getFecCreacion() {
		return fecCreacion;
	}
	public void setFecCreacion(Date fecCreacion) {
		this.fecCreacion = fecCreacion;
	}
	public Date getFecModificacion() {
		return fecModificacion;
	}
	public void setFecModificacion(Date fecModificacion) {
		this.fecModificacion = fecModificacion;
	}
	
}
