package pe.com.foxsoft.reportsweb.jpa.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name="general_parameter")
public class ParametroGeneral {
	public static final String PARAM_ID = "item";
	public static final String PARAM_TYPE = "tipoParametro";
	public static final String PARAM_CODE = "codParametro";
	public static final String PARAM_DESC = "descParametro";
	public static final String PARAM_VALUE = "valorParametro";
	public static final String PARAM_CREATION_DATE = "fecCreacion";
	public static final String PARAM_MODIFICATION_DATE = "fecModificacion";
	public static final String PARAM_STATUS = "estadoParametro";
	
	@Id
	@Column(name="PARAM_ID")
	private long item;
	@Column(name="PARAM_TYPE")
	private String tipoParametro;
	@Column(name="PARAM_CODE")
	private String codParametro;
	@Column(name="PARAM_DESC")
	private String descParametro;
	@Column(name="PARAM_VALUE")
	private String valorParametro;
	@Column(name="PARAM_CREATION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecCreacion;
	@Column(name="PARAM_MODIFICATION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecModificacion;
	@Column(name="PARAM_STATUS")
	private String estadoParametro;
	
	public long getItem() {
		return item;
	}
	public void setItem(long item) {
		this.item = item;
	}
	public String getTipoParametro() {
		return tipoParametro;
	}
	public void setTipoParametro(String tipoParametro) {
		this.tipoParametro = tipoParametro;
	}
	public String getCodParametro() {
		return codParametro;
	}
	public void setCodParametro(String codParametro) {
		this.codParametro = codParametro;
	}
	public String getDescParametro() {
		return descParametro;
	}
	public void setDescParametro(String descParametro) {
		this.descParametro = descParametro;
	}
	public String getValorParametro() {
		return valorParametro;
	}
	public void setValorParametro(String valorParametro) {
		this.valorParametro = valorParametro;
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
	public String getEstadoParametro() {
		return estadoParametro;
	}
	public void setEstadoParametro(String estadoParametro) {
		this.estadoParametro = estadoParametro;
	}
	
	
}
