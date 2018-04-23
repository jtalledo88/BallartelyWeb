package pe.com.foxsoft.reportsweb.jpa.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name="product_label")
public class EtiquetaProducto {
	public static final String PRODUCT_LABEL_ID = "item";
	public static final String PRODUCT_LABEL_CODE = "codEtiqueta";
	public static final String PRODUCT_LABEL_DESCRIPTION = "descEtiqueta";
	public static final String PRODUCT_LABEL_CREATION_DATE = "fecCreacion";
	public static final String PRODUCT_LABEL_MODIFICATION_DATE = "fecModificacion";
	public static final String PRODUCT_LABEL_STATUS = "estadoEtiqueta";
	
	@Id
	@Column(name="PRODUCT_LABEL_ID")
	private long item;
	@Column(name="PRODUCT_LABEL_CODE")
	private String codEtiqueta;
	@Column(name="PRODUCT_LABEL_DESCRIPTION")
	private String descEtiqueta;
	@Column(name="PRODUCT_LABEL_CREATION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecCreacion;
	@Column(name="PRODUCT_LABEL_MODIFICATION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecModificacion;
	@Column(name="PRODUCT_LABEL_STATUS")
	private String estadoEtiqueta;
	
	public long getItem() {
		return item;
	}
	public void setItem(long item) {
		this.item = item;
	}
	public String getCodEtiqueta() {
		return codEtiqueta;
	}
	public void setCodEtiqueta(String codEtiqueta) {
		this.codEtiqueta = codEtiqueta;
	}
	public String getDescEtiqueta() {
		return descEtiqueta;
	}
	public void setDescEtiqueta(String descEtiqueta) {
		this.descEtiqueta = descEtiqueta;
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
	public String getEstadoEtiqueta() {
		return estadoEtiqueta;
	}
	public void setEstadoEtiqueta(String estadoEtiqueta) {
		this.estadoEtiqueta = estadoEtiqueta;
	}
	
}
