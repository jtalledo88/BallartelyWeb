package pe.com.foxsoft.reportsweb.prime.faces.managedbeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import pe.com.foxsoft.reportsweb.jpa.data.EtiquetaProducto;
import pe.com.foxsoft.reportsweb.jpa.data.ParametroGeneral;
import pe.com.foxsoft.reportsweb.spring.exception.BallartelyException;
import pe.com.foxsoft.reportsweb.spring.service.EtiquetaProductoService;
import pe.com.foxsoft.reportsweb.spring.service.ParametroService;
import pe.com.foxsoft.reportsweb.spring.util.Constantes;
import pe.com.foxsoft.reportsweb.spring.util.Utilitarios;

@ManagedBean
@SessionScoped
public class EtiquetaProductoMB {

	protected final Logger logger = Logger.getLogger(getClass());

	@ManagedProperty("#{etiquetaProductoService}")
	private EtiquetaProductoService etiquetaProductoService;

	@ManagedProperty("#{parametroService}")
	private ParametroService parametroService;

	private EtiquetaProducto objEtiquetaProductoMain;
	private EtiquetaProducto objEtiquetaProducto;

	private List<EtiquetaProducto> lstEtiquetaProductosMain;
	private List<ParametroGeneral> lstEstadosGenerales;
	private List<String> lstDescEtiquetaProductoBUS;
	private List<String> lstCodEtiquetaProductoBUS;
	private boolean validaListaBuscar = true;
	private int canRegTablaPrincipal;
	private boolean flagConfirmEliEtiqProd = false;

	public EtiquetaProductoMB() {
		this.objEtiquetaProductoMain = new EtiquetaProducto();
		this.lstEtiquetaProductosMain = new ArrayList<EtiquetaProducto>();
		this.lstEstadosGenerales = new ArrayList<ParametroGeneral>();
		this.lstCodEtiquetaProductoBUS = new ArrayList<String>();
		this.lstDescEtiquetaProductoBUS = new ArrayList<String>();
		this.objEtiquetaProducto = new EtiquetaProducto();
	}

	public void buscarEtiquetaProductos() {
		try {
			this.validaListaBuscar = false;
			this.lstEtiquetaProductosMain = this.etiquetaProductoService.buscarEtiquetaProductos(this.objEtiquetaProductoMain);
			this.canRegTablaPrincipal = this.lstEtiquetaProductosMain.size();
		} catch (BallartelyException e) {
			String sMensaje = "Error en buscarEtiquetaProductos";
			this.logger.error(e.getMessage());
			throw new FacesException(sMensaje, e);
		}
	}

	public void agregarEtiquetaProducto() {
		String sMensaje = "";
		
		EtiquetaProducto objEtiquetaProducto = new EtiquetaProducto();
		try {
			if ("".equals(this.objEtiquetaProductoMain.getDescEtiqueta())) {
				Utilitarios.mensajeError("Campos Obligatorios", "Debe llenar la descripción de la etiqueta.");
				return;
			} 
			if ("".equals(this.objEtiquetaProductoMain.getCodEtiqueta())) {
				Utilitarios.mensajeError("Campos Obligatorios", "Debe llenar el código de la etiqueta.");
				return;
			} 
			if ("".equals(this.objEtiquetaProductoMain.getEstadoEtiqueta())) {
				Utilitarios.mensajeError("Campos Obligatorios", "Debe seleccionar un estado.");
				return;
			} 
			
			objEtiquetaProducto.setCodEtiqueta(this.objEtiquetaProductoMain.getCodEtiqueta().toUpperCase());
			objEtiquetaProducto.setDescEtiqueta(this.objEtiquetaProductoMain.getDescEtiqueta());
			
			sMensaje = this.etiquetaProductoService.agregarEtiquetaProducto(objEtiquetaProducto);
			Utilitarios.mensaje("", sMensaje);
			setLstEtiquetaProductosMain(new ArrayList<EtiquetaProducto>());
			this.canRegTablaPrincipal = getListaPrincipalEtiquetaProductos();
		
		} catch (BallartelyException e) {
			sMensaje = "Error en agregarEtiquetaProducto";
			this.logger.error(e.getMessage());
			throw new FacesException(sMensaje, e);
		}
	}

	public void openAgregarEtiquetaProducto() {
		this.objEtiquetaProductoMain = new EtiquetaProducto();
		this.objEtiquetaProductoMain.setCodEtiqueta("");
		this.objEtiquetaProductoMain.setDescEtiqueta("");
		this.objEtiquetaProductoMain.setEstadoEtiqueta("");
	}

	public void openEditarEtiquetaProducto() {
		setObjEtiquetaProductoMain(new EtiquetaProducto());

		Map<String, String> paramMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

		long itemEtiquetaProducto = Long.parseLong((String) paramMap.get("itemEtiquetaProducto"));
		try {
			this.objEtiquetaProductoMain = this.etiquetaProductoService.obtenerEtiquetaProducto(itemEtiquetaProducto);
		} catch (BallartelyException e) {
			String sMensaje = "Error en openEditarEtiquetaProducto";
			this.logger.error(e.getMessage());
			throw new FacesException(sMensaje, e);
		}
	}

	public void editarEtiquetaProducto() {
		String sMensaje = "";
		
		try {
			if ("".equals(this.objEtiquetaProductoMain.getDescEtiqueta())) {
				Utilitarios.mensajeError("Campos Obligatorios", "Debe llenar la descripción de la etiqueta.");
				return;
			} 
			if ("".equals(this.objEtiquetaProductoMain.getCodEtiqueta())) {
				Utilitarios.mensajeError("Campos Obligatorios", "Debe llenar el código de la etiqueta.");
				return;
			} 
			if ("".equals(this.objEtiquetaProductoMain.getEstadoEtiqueta())) {
				Utilitarios.mensajeError("Campos Obligatorios", "Debe seleccionar un estado.");
				return;
			} 
			sMensaje = this.etiquetaProductoService.editarEtiquetaProducto(this.objEtiquetaProductoMain);
			Utilitarios.mensaje("", sMensaje);
			this.canRegTablaPrincipal = getListaPrincipalEtiquetaProductos();
			
		} catch (BallartelyException e) {
			sMensaje = "Error en editarEtiquetaProducto";
			this.logger.error(e.getMessage());
			throw new FacesException(sMensaje, e);
		}
	}

	public void visibleConfirmElimEtiquetaProducto() {
		this.flagConfirmEliEtiqProd = true;
	}

	public void eliminarEtiquetaProducto() {
		String sMensaje = "";
		try {
			sMensaje = this.etiquetaProductoService.eliminarEtiquetaProducto(this.objEtiquetaProductoMain);
			setLstEtiquetaProductosMain(new ArrayList<EtiquetaProducto>());
			this.canRegTablaPrincipal = getListaPrincipalEtiquetaProductos();
			Utilitarios.mensaje("", sMensaje);
		} catch (BallartelyException e) {
			sMensaje = "Error en eliminarEtiquetaProducto";
			this.logger.error(e.getMessage());
			throw new FacesException(sMensaje, e);
		}
		this.flagConfirmEliEtiqProd = false;
	}

	public int getListaPrincipalEtiquetaProductos() {
		int can = 0;
		try {
			this.lstEtiquetaProductosMain = this.etiquetaProductoService.getListaEtiquetaProductos();
			can = this.lstEtiquetaProductosMain.size();
			for (EtiquetaProducto p : this.lstEtiquetaProductosMain) {
				if (p.getCodEtiqueta() != null) {
					this.lstCodEtiquetaProductoBUS.add(p.getCodEtiqueta());
				}
				if (p.getDescEtiqueta() != null) {
					this.lstDescEtiquetaProductoBUS.add(p.getDescEtiqueta());
				}
			}
		} catch (BallartelyException e) {
			String sMensaje = "Error en getListaPrincipalEtiquetaProductos";
			this.logger.error(e.getMessage(), e);
			throw new FacesException(sMensaje, e);
		}
		return can;
	}

	public void obtenerEstadosEtiquetaProductos() {
		try {
			this.lstEstadosGenerales = this.parametroService.obtenerListaParametros(Constantes.TBL_STATUS_LIST);
		} catch (BallartelyException e) {
			String sMensaje = "Error en obtenerEstadosEtiquetaProductos";
			this.logger.error(e.getMessage(), e);
			throw new FacesException(sMensaje, e);
		}
	}

	public List<String> completeDesc(String query) {
		List<String> results = new ArrayList<String>();
		for (String s : this.lstDescEtiquetaProductoBUS) {
			if (Utilitarios.compararCadenas(s, query.trim())) {
				results.add(s);
			}
		}
		return results;
	}

	public List<String> completeCod(String query) {
		List<String> results = new ArrayList<String>();
		for (String s : this.lstCodEtiquetaProductoBUS) {
			if (Utilitarios.compararCadenas(s, query.trim())) {
				results.add(s);
			}
		}
		return results;
	}

	public EtiquetaProducto getObjEtiquetaProductoMain() {
		return this.objEtiquetaProductoMain;
	}

	public void setObjEtiquetaProductoMain(EtiquetaProducto objEtiquetaProductoMain) {
		this.objEtiquetaProductoMain = objEtiquetaProductoMain;
	}

	public List<EtiquetaProducto> getLstEtiquetaProductosMain() {
		if ((this.lstEtiquetaProductosMain.isEmpty()) && (this.validaListaBuscar)) {
			this.canRegTablaPrincipal = getListaPrincipalEtiquetaProductos();
		}
		return this.lstEtiquetaProductosMain;
	}

	public void setLstEtiquetaProductosMain(List<EtiquetaProducto> lstEtiquetaProductosMain) {
		this.lstEtiquetaProductosMain = lstEtiquetaProductosMain;
	}

	public int getCanRegTablaPrincipal() {
		return this.canRegTablaPrincipal;
	}

	public void setCanRegTablaPrincipal(int canRegTablaPrincipal) {
		this.canRegTablaPrincipal = canRegTablaPrincipal;
	}

	public List<ParametroGeneral> getLstEstadosGenerales() {
		if (this.lstEstadosGenerales.isEmpty()) {
			obtenerEstadosEtiquetaProductos();
		}
		return this.lstEstadosGenerales;
	}

	public void setLstEstadosGenerales(List<ParametroGeneral> lstEstadosGenerales) {
		this.lstEstadosGenerales = lstEstadosGenerales;
	}

	public EtiquetaProducto getObjEtiquetaProducto() {
		return this.objEtiquetaProducto;
	}

	public void setObjEtiquetaProducto(EtiquetaProducto objEtiquetaProducto) {
		this.objEtiquetaProducto = objEtiquetaProducto;
	}

	public boolean isFlagConfirmEliEtiqProd() {
		return this.flagConfirmEliEtiqProd;
	}

	public void setFlagConfirmEliEtiqProd(boolean flagConfirmEliEtiqProd) {
		this.flagConfirmEliEtiqProd = flagConfirmEliEtiqProd;
	}

	public List<String> getLstDescEtiquetaProductoBUS() {
		return this.lstDescEtiquetaProductoBUS;
	}

	public void setLstDescEtiquetaProductoBUS(List<String> lstDescEtiquetaProductoBUS) {
		this.lstDescEtiquetaProductoBUS = lstDescEtiquetaProductoBUS;
	}

	public List<String> getLstCodEtiquetaProductoBUS() {
		return this.lstCodEtiquetaProductoBUS;
	}

	public void setLstCodEtiquetaProductoBUS(List<String> lstCodEtiquetaProductoBUS) {
		this.lstCodEtiquetaProductoBUS = lstCodEtiquetaProductoBUS;
	}

	public boolean isValidaListaBuscar() {
		return this.validaListaBuscar;
	}

	public void setValidaListaBuscar(boolean validaListaBuscar) {
		this.validaListaBuscar = validaListaBuscar;
	}
}
