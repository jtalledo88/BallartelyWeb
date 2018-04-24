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

import org.apache.log4j.Logger;

import pe.com.foxsoft.ballartelyweb.jpa.data.ParametroGeneral;
import pe.com.foxsoft.ballartelyweb.spring.domain.TipoParametro;
import pe.com.foxsoft.ballartelyweb.spring.exception.BallartelyException;
import pe.com.foxsoft.ballartelyweb.spring.service.ParametroGeneralService;
import pe.com.foxsoft.ballartelyweb.spring.util.Propiedades;
import pe.com.foxsoft.ballartelyweb.spring.util.Utilitarios;

@ManagedBean
@SessionScoped
public class ParametroGeneralMB {

	protected final Logger logger = Logger.getLogger(getClass());
	
	@ManagedProperty("#{parametroGeneralService}")
	private ParametroGeneralService parametroGeneralService;
	
	@ManagedProperty("#{propiedades}")
	private Propiedades propiedades;

	private ParametroGeneral objParametroGeneralMain;

	private List<ParametroGeneral> lstParametrosGeneralesMain;
	private List<TipoParametro> lstTiposParametros;
	private List<String> lstDescParametroGeneralBUS;
	private List<String> lstCodParametroGeneralBUS;
	private boolean validaListaBuscar = true;
	private int canRegTablaPrincipal;
	private boolean flagConfirmEliParamGen = false;

	public ParametroGeneralMB() {
		this.objParametroGeneralMain = new ParametroGeneral();
		this.lstParametrosGeneralesMain = new ArrayList<ParametroGeneral>();
		this.lstCodParametroGeneralBUS = new ArrayList<String>();
		this.lstDescParametroGeneralBUS = new ArrayList<String>();
		this.lstTiposParametros = new ArrayList<TipoParametro>();
	}

	public void buscarParametrosGenerales() {
		try {
			this.validaListaBuscar = false;
			this.lstParametrosGeneralesMain = this.parametroGeneralService.buscarParametrosGenerales(this.objParametroGeneralMain);
			this.canRegTablaPrincipal = this.lstParametrosGeneralesMain.size();
		} catch (BallartelyException e) {
			String sMensaje = "Error en buscarParametrosGenerales";
			this.logger.error(e.getMessage());
			throw new FacesException(sMensaje, e);
		}
	}

	public void agregarParametroGeneral() {
		String sMensaje = "";
		
		ParametroGeneral objParametroGeneral = new ParametroGeneral();
		try {
			if ("".equals(this.objParametroGeneralMain.getDescParametro())) {
				Utilitarios.mensajeError("Campos Obligatorios", "Debe llenar la descripción del parametro.");
				return;
			} 
			if ("".equals(this.objParametroGeneralMain.getCodParametro())) {
				Utilitarios.mensajeError("Campos Obligatorios", "Debe llenar el código del parametro.");
				return;
			} 
			if ("".equals(this.objParametroGeneralMain.getEstadoParametro())) {
				Utilitarios.mensajeError("Campos Obligatorios", "Debe seleccionar un estado.");
				return;
			} 
			
			objParametroGeneral.setTipoParametro(this.objParametroGeneralMain.getTipoParametro());
			objParametroGeneral.setCodParametro(this.objParametroGeneralMain.getCodParametro().toUpperCase());
			objParametroGeneral.setDescParametro(this.objParametroGeneralMain.getDescParametro());
			objParametroGeneral.setValorParametro(this.getObjParametroGeneralMain().getValorParametro());
			objParametroGeneral.setEstadoParametro(this.objParametroGeneralMain.getEstadoParametro());
			objParametroGeneral.setFecCreacion(new Date());
			
			sMensaje = this.parametroGeneralService.agregarParametroGeneral(objParametroGeneral);
			Utilitarios.mensaje("", sMensaje);
			setLstParametrosGeneralesMain(new ArrayList<ParametroGeneral>());
			this.canRegTablaPrincipal = getListaPrincipalParametroGeneral();
		
		} catch (BallartelyException e) {
			sMensaje = "Error en agregarParametroGeneral";
			this.logger.error(e.getMessage());
			throw new FacesException(sMensaje, e);
		}
	}

	public void openAgregarParametroGeneral() {
		this.objParametroGeneralMain = new ParametroGeneral();
		this.objParametroGeneralMain.setCodParametro("");
		this.objParametroGeneralMain.setDescParametro("");
		this.objParametroGeneralMain.setEstadoParametro("");
	}

	public void openEditarParametroGeneral() {
		setObjParametroGeneralMain(new ParametroGeneral());

		Map<String, String> paramMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

		long itemParametroGeneral = Long.parseLong((String) paramMap.get("itemParametroGeneral"));
		try {
			this.objParametroGeneralMain = this.parametroGeneralService.obtenerParametroGeneral(itemParametroGeneral);
		} catch (BallartelyException e) {
			String sMensaje = "Error en openEditarParametroGeneral";
			this.logger.error(e.getMessage());
			throw new FacesException(sMensaje, e);
		}
	}

	public void editarParametroGeneral() {
		String sMensaje = "";
		
		try {
			if ("".equals(this.objParametroGeneralMain.getDescParametro())) {
				Utilitarios.mensajeError("Campos Obligatorios", "Debe llenar la descripción del parametro.");
				return;
			} 
			if ("".equals(this.objParametroGeneralMain.getCodParametro())) {
				Utilitarios.mensajeError("Campos Obligatorios", "Debe llenar el código del parametro.");
				return;
			} 
			if ("".equals(this.objParametroGeneralMain.getEstadoParametro())) {
				Utilitarios.mensajeError("Campos Obligatorios", "Debe seleccionar un estado.");
				return;
			}
			
			this.objParametroGeneralMain.setFecModificacion(new Date());
			sMensaje = this.parametroGeneralService.editarParametroGeneral(this.objParametroGeneralMain);
			Utilitarios.mensaje("", sMensaje);
			this.canRegTablaPrincipal = getListaPrincipalParametroGeneral();
			
		} catch (BallartelyException e) {
			sMensaje = "Error en editarParametroGeneral";
			this.logger.error(e.getMessage());
			throw new FacesException(sMensaje, e);
		}
	}

	public void visibleConfirmElimParametroGeneral() {
		this.flagConfirmEliParamGen = true;
	}

	public void eliminarParametroGeneral() {
		String sMensaje = "";
		try {
			sMensaje = this.parametroGeneralService.eliminarParametroGeneral(this.objParametroGeneralMain);
			setLstParametrosGeneralesMain(new ArrayList<ParametroGeneral>());
			this.canRegTablaPrincipal = getListaPrincipalParametroGeneral();
			Utilitarios.mensaje("", sMensaje);
		} catch (BallartelyException e) {
			sMensaje = "Error en eliminarParametroGeneral";
			this.logger.error(e.getMessage());
			throw new FacesException(sMensaje, e);
		}
		this.flagConfirmEliParamGen = false;
	}

	public int getListaPrincipalParametroGeneral() {
		int can = 0;
		try {
			this.lstParametrosGeneralesMain = this.parametroGeneralService.getListaParametrosGenerales();
			can = this.lstParametrosGeneralesMain.size();
			for (ParametroGeneral p : this.lstParametrosGeneralesMain) {
				if (p.getCodParametro() != null) {
					this.lstCodParametroGeneralBUS.add(p.getCodParametro());
				}
				if (p.getDescParametro() != null) {
					this.lstDescParametroGeneralBUS.add(p.getDescParametro());
				}
			}
		} catch (BallartelyException e) {
			String sMensaje = "Error en eliminarParametroGeneral";
			this.logger.error(e.getMessage(), e);
			throw new FacesException(sMensaje, e);
		}
		return can;
	}

	public void obtenerTiposParametros() {
		try {
			this.lstTiposParametros = Utilitarios.obtenerListaTipoParametros(propiedades.getTiposParametro());
		} catch (BallartelyException e) {
			String sMensaje = "Error en obtenerTiposParametros";
			this.logger.error(e.getMessage(), e);
			throw new FacesException(sMensaje, e);
		}
	}

	public List<String> completeDesc(String query) {
		List<String> results = new ArrayList<String>();
		for (String s : this.lstDescParametroGeneralBUS) {
			if (Utilitarios.compararCadenas(s, query.trim())) {
				results.add(s);
			}
		}
		return results;
	}

	public List<String> completeCod(String query) {
		List<String> results = new ArrayList<String>();
		for (String s : this.lstCodParametroGeneralBUS) {
			if (Utilitarios.compararCadenas(s, query.trim())) {
				results.add(s);
			}
		}
		return results;
	}

	public ParametroGeneral getObjParametroGeneralMain() {
		return this.objParametroGeneralMain;
	}

	public void setObjParametroGeneralMain(ParametroGeneral objParametroGeneralMain) {
		this.objParametroGeneralMain = objParametroGeneralMain;
	}

	public List<ParametroGeneral> getLstParametrosGeneralesMain() {
		if ((this.lstParametrosGeneralesMain.isEmpty()) && (this.validaListaBuscar)) {
			this.canRegTablaPrincipal = getListaPrincipalParametroGeneral();
		}
		return this.lstParametrosGeneralesMain;
	}

	public void setLstParametrosGeneralesMain(List<ParametroGeneral> lstParametrosGeneralesMain) {
		this.lstParametrosGeneralesMain = lstParametrosGeneralesMain;
	}

	public int getCanRegTablaPrincipal() {
		return this.canRegTablaPrincipal;
	}

	public void setCanRegTablaPrincipal(int canRegTablaPrincipal) {
		this.canRegTablaPrincipal = canRegTablaPrincipal;
	}

	public List<TipoParametro> getLstTiposParametros() {
		if (this.lstTiposParametros.isEmpty()) {
			obtenerTiposParametros();
		}
		return this.lstTiposParametros;
	}

	public void setLstTiposParametros(List<TipoParametro> lstTiposParametros) {
		this.lstTiposParametros = lstTiposParametros;
	}
	
	public boolean isFlagConfirmEliParamGen() {
		return this.flagConfirmEliParamGen;
	}

	public void setFlagConfirmEliParamGen(boolean flagConfirmEliParamGen) {
		this.flagConfirmEliParamGen = flagConfirmEliParamGen;
	}

	public List<String> getLstDescParametroGeneralBUS() {
		return this.lstDescParametroGeneralBUS;
	}

	public void setLstDescParametroGeneralBUS(List<String> lstDescParametroGeneralBUS) {
		this.lstDescParametroGeneralBUS = lstDescParametroGeneralBUS;
	}

	public List<String> getLstCodParametroGeneralBUS() {
		return this.lstCodParametroGeneralBUS;
	}

	public void setLstCodParametroGeneralBUS(List<String> lstCodParametroGeneralBUS) {
		this.lstCodParametroGeneralBUS = lstCodParametroGeneralBUS;
	}

	public boolean isValidaListaBuscar() {
		return this.validaListaBuscar;
	}

	public void setValidaListaBuscar(boolean validaListaBuscar) {
		this.validaListaBuscar = validaListaBuscar;
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
