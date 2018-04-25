package pe.com.foxsoft.ballartelyweb.prime.faces.managedbeans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import pe.com.foxsoft.ballartelyweb.jpa.data.GeneralParameter;
import pe.com.foxsoft.ballartelyweb.jpa.data.ProductLabel;
import pe.com.foxsoft.ballartelyweb.spring.exception.BallartelyException;
import pe.com.foxsoft.ballartelyweb.spring.service.EtiquetaProductoService;
import pe.com.foxsoft.ballartelyweb.spring.service.ParametroGeneralService;
import pe.com.foxsoft.ballartelyweb.spring.util.Propiedades;
import pe.com.foxsoft.ballartelyweb.spring.util.Utilitarios;

@ManagedBean
@SessionScoped
public class EtiquetaProductoMB {

	protected final Logger logger = Logger.getLogger(getClass());

	@ManagedProperty("#{etiquetaProductoService}")
	private EtiquetaProductoService etiquetaProductoService;

	@ManagedProperty("#{parametroGeneralService}")
	private ParametroGeneralService parametroGeneralService;
	
	@ManagedProperty("#{propiedades}")
	private Propiedades propiedades;

	private ProductLabel objEtiquetaProductoMain;
	private ProductLabel objEtiquetaProductoSearch;

	private List<ProductLabel> lstEtiquetaProductosMain;
	private List<SelectItem> lstEstadosGenerales;
	private List<String> lstDescEtiquetaProductoBUS;
	private List<String> lstCodEtiquetaProductoBUS;
	private boolean validaListaBuscar = true;
	private int canRegTablaPrincipal;
	private boolean flagConfirmEliEtiqProd = false;

	public EtiquetaProductoMB() {
		this.objEtiquetaProductoMain = new ProductLabel();
		this.objEtiquetaProductoSearch = new ProductLabel();
		this.lstEtiquetaProductosMain = new ArrayList<ProductLabel>();
		this.lstEstadosGenerales = new ArrayList<SelectItem>();
		this.lstCodEtiquetaProductoBUS = new ArrayList<String>();
		this.lstDescEtiquetaProductoBUS = new ArrayList<String>();
	}

	public void buscarEtiquetaProductos() {
		try {
			this.validaListaBuscar = false;
			this.lstEtiquetaProductosMain = this.etiquetaProductoService.buscarEtiquetaProductos(this.objEtiquetaProductoSearch);
			this.canRegTablaPrincipal = this.lstEtiquetaProductosMain.size();
		} catch (BallartelyException e) {
			String sMensaje = "Error en buscarEtiquetaProductos";
			this.logger.error(e.getMessage());
			throw new FacesException(sMensaje, e);
		}
	}

	public void agregarEtiquetaProducto() {
		String sMensaje = "";
		
		ProductLabel objEtiquetaProducto = new ProductLabel();
		try {
			if ("".equals(this.objEtiquetaProductoMain.getProductLabelDescription())) {
				Utilitarios.mensajeError("Campos Obligatorios", "Debe llenar la descripción de la etiqueta.");
				return;
			} 
			if ("".equals(this.objEtiquetaProductoMain.getProductLabelCode())) {
				Utilitarios.mensajeError("Campos Obligatorios", "Debe llenar el código de la etiqueta.");
				return;
			} 
			if (this.objEtiquetaProductoMain.getGeneralParameter() == null) {
				Utilitarios.mensajeError("Campos Obligatorios", "Debe seleccionar un estado.");
				return;
			} 
			
			objEtiquetaProducto.setProductLabelCode(this.objEtiquetaProductoMain.getProductLabelCode().toUpperCase());
			objEtiquetaProducto.setProductLabelDescription(this.objEtiquetaProductoMain.getProductLabelDescription());
			objEtiquetaProducto.setGeneralParameter(this.objEtiquetaProductoMain.getGeneralParameter());
			objEtiquetaProducto.setProductLabelCreationDate(new Date());
			
			sMensaje = this.etiquetaProductoService.agregarEtiquetaProducto(objEtiquetaProducto);
			Utilitarios.mensaje("", sMensaje);
			setLstEtiquetaProductosMain(new ArrayList<ProductLabel>());
			this.canRegTablaPrincipal = getListaPrincipalEtiquetaProductos();
		
		} catch (BallartelyException e) {
			sMensaje = "Error en agregarEtiquetaProducto";
			this.logger.error(e.getMessage());
			throw new FacesException(sMensaje, e);
		}
	}

	public void openAgregarEtiquetaProducto() {
		this.objEtiquetaProductoMain = new ProductLabel();
		this.objEtiquetaProductoMain.setProductLabelCode("");
		this.objEtiquetaProductoMain.setProductLabelDescription("");
		this.objEtiquetaProductoMain.setGeneralParameter(new GeneralParameter());
	}

	public void openEditarEtiquetaProducto() {
		setObjEtiquetaProductoMain(new ProductLabel());

		Map<String, String> paramMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

		int itemEtiquetaProducto = Integer.parseInt((String) paramMap.get("itemEtiquetaProducto"));
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
			if ("".equals(this.objEtiquetaProductoMain.getProductLabelDescription())) {
				Utilitarios.mensajeError("Campos Obligatorios", "Debe llenar la descripción de la etiqueta.");
				return;
			} 
			if ("".equals(this.objEtiquetaProductoMain.getProductLabelCode())) {
				Utilitarios.mensajeError("Campos Obligatorios", "Debe llenar el código de la etiqueta.");
				return;
			} 
			if (this.objEtiquetaProductoMain.getGeneralParameter() == null) {
				Utilitarios.mensajeError("Campos Obligatorios", "Debe seleccionar un estado.");
				return;
			} 
			
			this.objEtiquetaProductoMain.setProductLabelModificationDate(new Date());
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
			setLstEtiquetaProductosMain(new ArrayList<ProductLabel>());
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
			for (ProductLabel p : this.lstEtiquetaProductosMain) {
				if (p.getProductLabelCode() != null) {
					this.lstCodEtiquetaProductoBUS.add(p.getProductLabelCode());
				}
				if (p.getProductLabelDescription() != null) {
					this.lstDescEtiquetaProductoBUS.add(p.getProductLabelDescription());
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
			this.lstEstadosGenerales = new ArrayList<>();
			this.lstEstadosGenerales.add(new SelectItem(new GeneralParameter(), "-- Seleccione --"));
			List<GeneralParameter> lstGeneralParameters = this.parametroGeneralService.obtenerListaParametros(propiedades.getComboEstados());
			for(GeneralParameter g: lstGeneralParameters) {
				this.lstEstadosGenerales.add(new SelectItem(g, g.getParamValue()));
			} 
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

	public ProductLabel getObjEtiquetaProductoMain() {
		return this.objEtiquetaProductoMain;
	}

	public void setObjEtiquetaProductoMain(ProductLabel objEtiquetaProductoMain) {
		this.objEtiquetaProductoMain = objEtiquetaProductoMain;
	}
	
	public ProductLabel getObjEtiquetaProductoSearch() {
		return objEtiquetaProductoSearch;
	}

	public void setObjEtiquetaProductoSearch(ProductLabel objEtiquetaProductoSearch) {
		this.objEtiquetaProductoSearch = objEtiquetaProductoSearch;
	}

	public List<ProductLabel> getLstEtiquetaProductosMain() {
		if ((this.lstEtiquetaProductosMain.isEmpty()) && (this.validaListaBuscar)) {
			this.canRegTablaPrincipal = getListaPrincipalEtiquetaProductos();
		}
		return this.lstEtiquetaProductosMain;
	}

	public void setLstEtiquetaProductosMain(List<ProductLabel> lstEtiquetaProductosMain) {
		this.lstEtiquetaProductosMain = lstEtiquetaProductosMain;
	}

	public int getCanRegTablaPrincipal() {
		return this.canRegTablaPrincipal;
	}

	public void setCanRegTablaPrincipal(int canRegTablaPrincipal) {
		this.canRegTablaPrincipal = canRegTablaPrincipal;
	}

	public List<SelectItem> getLstEstadosGenerales() {
		obtenerEstadosEtiquetaProductos();
		return this.lstEstadosGenerales;
	}

	public void setLstEstadosGenerales(List<SelectItem> lstEstadosGenerales) {
		this.lstEstadosGenerales = lstEstadosGenerales;
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

	public EtiquetaProductoService getEtiquetaProductoService() {
		return etiquetaProductoService;
	}

	public void setEtiquetaProductoService(EtiquetaProductoService etiquetaProductoService) {
		this.etiquetaProductoService = etiquetaProductoService;
	}

	public ParametroGeneralService getParametroGeneralService() {
		return parametroGeneralService;
	}

	public void setParametroGeneralService(ParametroGeneralService parametroGeneralService) {
		this.parametroGeneralService = parametroGeneralService;
	}
	
	public Propiedades getPropiedades() {
		return propiedades;
	}

	public void setPropiedades(Propiedades propiedades) {
		this.propiedades = propiedades;
	}
	
}
