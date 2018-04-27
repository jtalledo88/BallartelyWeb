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

import pe.com.foxsoft.ballartelyweb.jpa.data.Client;
import pe.com.foxsoft.ballartelyweb.jpa.data.GeneralParameter;
import pe.com.foxsoft.ballartelyweb.spring.exception.BallartelyException;
import pe.com.foxsoft.ballartelyweb.spring.service.ClienteService;
import pe.com.foxsoft.ballartelyweb.spring.service.ParametroGeneralService;
import pe.com.foxsoft.ballartelyweb.spring.util.Propiedades;
import pe.com.foxsoft.ballartelyweb.spring.util.Utilitarios;

@ManagedBean
@SessionScoped
public class ClienteMB {

	protected final Logger logger = Logger.getLogger(getClass());

	@ManagedProperty("#{clienteService}")
	private ClienteService clienteService;

	@ManagedProperty("#{parametroGeneralService}")
	private ParametroGeneralService parametroGeneralService;
	
	@ManagedProperty("#{propiedades}")
	private Propiedades propiedades;

	private Client objClienteMain;
	private Client objClienteSearch;

	private List<Client> lstClientesMain;
	private List<SelectItem> lstEstadosGenerales;
	private List<SelectItem> lstTiposDocGenerales;
	private List<SelectItem> lstTiposCliGenerales;
	private List<String> lstNumDocClienteBUS;
	private List<String> lstNomClienteBUS;
	private boolean validaListaBuscar = true;
	private int canRegTablaPrincipal;
	private boolean flagConfirmEliClient = false;

	public ClienteMB() {
		this.objClienteMain = new Client();
		this.objClienteSearch = new Client();
		this.lstClientesMain = new ArrayList<Client>();
		this.lstEstadosGenerales = new ArrayList<SelectItem>();
		this.lstTiposDocGenerales = new ArrayList<>();
		this.lstTiposCliGenerales = new ArrayList<>();
		this.lstNumDocClienteBUS = new ArrayList<String>();
		this.lstNomClienteBUS = new ArrayList<String>();
	}

	public void buscarClientes() {
		try {
			this.validaListaBuscar = false;
			this.lstClientesMain = this.clienteService.buscarClientes(this.objClienteSearch);
			this.canRegTablaPrincipal = this.lstClientesMain.size();
		} catch (BallartelyException e) {
			String sMensaje = "Error en buscarClientes";
			this.logger.error(e.getMessage());
			throw new FacesException(sMensaje, e);
		}
	}

	public void agregarCliente() {
		String sMensaje = "";
		
		Client objCliente = new Client();
		try {
			if (this.objClienteMain.getDocumentType() == null) {
				Utilitarios.mensajeError("Campos Obligatorios", "Debe seleccionar un tipo de documento.");
				return;
			} 
			if ("".equals(this.objClienteMain.getDocumentNumber())) {
				Utilitarios.mensajeError("Campos Obligatorios", "Debe llenar el número de documento del cliente.");
				return;
			}
			if ("".equals(this.objClienteMain.getClientNames())) {
				Utilitarios.mensajeError("Campos Obligatorios", "Debe llenar el nombre o razón social del cliente.");
				return;
			}
			if ("".equals(this.objClienteMain.getClientAddress())) {
				Utilitarios.mensajeError("Campos Obligatorios", "Debe llenar la dirección del cliente.");
				return;
			}
			if ("".equals(this.objClienteMain.getClientPhoneNumber())) {
				Utilitarios.mensajeError("Campos Obligatorios", "Debe llenar el número de teléfono del cliente.");
				return;
			}
			if (this.objClienteMain.getClientStatus() == null) {
				Utilitarios.mensajeError("Campos Obligatorios", "Debe seleccionar un estado.");
				return;
			}
			if (this.objClienteMain.getClientType() == null) {
				Utilitarios.mensajeError("Campos Obligatorios", "Debe seleccionar un tipo de cliente.");
				return;
			} 
			
			
			objCliente.setDocumentType(this.objClienteMain.getDocumentType());
			objCliente.setDocumentNumber(this.objClienteMain.getDocumentNumber());
			objCliente.setClientAddress(this.objClienteMain.getClientAddress());
			objCliente.setClientPhoneNumber(this.objClienteMain.getClientPhoneNumber());
			objCliente.setClientStatus(this.objClienteMain.getClientStatus());
			objCliente.setClientType(this.objClienteMain.getClientType());
			objCliente.setClientCreationDate(new Date());
			
			sMensaje = this.clienteService.agregarCliente(objCliente);
			Utilitarios.mensaje("", sMensaje);
			setLstClientesMain(new ArrayList<Client>());
			this.canRegTablaPrincipal = getListaPrincipalClientes();
		
		} catch (BallartelyException e) {
			sMensaje = "Error en agregarCliente";
			this.logger.error(e.getMessage());
			throw new FacesException(sMensaje, e);
		}
	}

	public void openAgregarCliente() {
		this.objClienteMain = new Client();
		this.objClienteMain.setDocumentType(new GeneralParameter());
		this.objClienteMain.setDocumentNumber("");
		this.objClienteMain.setClientNames("");
		this.objClienteMain.setClientAddress("");
		this.objClienteMain.setClientPhoneNumber("");
		this.objClienteMain.setClientType(new GeneralParameter());
		this.objClienteMain.setClientStatus(new GeneralParameter());
	}

	public void openEditarCliente() {
		setObjClienteMain(new Client());

		Map<String, String> paramMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

		int itemCliente = Integer.parseInt((String) paramMap.get("itemCliente"));
		try {
			this.objClienteMain = this.clienteService.obtenerCliente(itemCliente);
		} catch (BallartelyException e) {
			String sMensaje = "Error en openEditarCliente";
			this.logger.error(e.getMessage());
			throw new FacesException(sMensaje, e);
		}
	}

	public void editarCliente() {
		String sMensaje = "";
		
		try {
			if (this.objClienteMain.getDocumentType() == null) {
				Utilitarios.mensajeError("Campos Obligatorios", "Debe seleccionar un tipo de documento.");
				return;
			} 
			if ("".equals(this.objClienteMain.getDocumentNumber())) {
				Utilitarios.mensajeError("Campos Obligatorios", "Debe llenar el número de documento del cliente.");
				return;
			}
			if ("".equals(this.objClienteMain.getClientNames())) {
				Utilitarios.mensajeError("Campos Obligatorios", "Debe llenar el nombre o razón social del cliente.");
				return;
			}
			if ("".equals(this.objClienteMain.getClientAddress())) {
				Utilitarios.mensajeError("Campos Obligatorios", "Debe llenar la dirección del cliente.");
				return;
			}
			if ("".equals(this.objClienteMain.getClientPhoneNumber())) {
				Utilitarios.mensajeError("Campos Obligatorios", "Debe llenar el número de teléfono del cliente.");
				return;
			}
			if (this.objClienteMain.getClientStatus() == null) {
				Utilitarios.mensajeError("Campos Obligatorios", "Debe seleccionar un estado.");
				return;
			}
			if (this.objClienteMain.getClientType() == null) {
				Utilitarios.mensajeError("Campos Obligatorios", "Debe seleccionar un tipo de cliente.");
				return;
			}
			
			this.objClienteMain.setClientModificationDate(new Date());
			sMensaje = this.clienteService.editarCliente(this.objClienteMain);
			Utilitarios.mensaje("", sMensaje);
			this.canRegTablaPrincipal = getListaPrincipalClientes();
			
		} catch (BallartelyException e) {
			sMensaje = "Error en editarCliente";
			this.logger.error(e.getMessage());
			throw new FacesException(sMensaje, e);
		}
	}

	public void visibleConfirmElimCliente() {
		this.flagConfirmEliClient = true;
	}

	public void eliminarCliente() {
		String sMensaje = "";
		try {
			sMensaje = this.clienteService.eliminarCliente(this.objClienteMain);
			setLstClientesMain(new ArrayList<Client>());
			this.canRegTablaPrincipal = getListaPrincipalClientes();
			Utilitarios.mensaje("", sMensaje);
		} catch (BallartelyException e) {
			sMensaje = "Error en eliminarCliente";
			this.logger.error(e.getMessage());
			throw new FacesException(sMensaje, e);
		}
		this.flagConfirmEliClient = false;
	}

	public int getListaPrincipalClientes() {
		int can = 0;
		try {
			this.lstClientesMain = this.clienteService.getListaClientes();
			can = this.lstClientesMain.size();
			for (Client c : this.lstClientesMain) {
				if (c.getClientNames() != null) {
					this.lstNomClienteBUS.add(c.getClientNames());
				}
				if (c.getDocumentNumber() != null) {
					this.lstNumDocClienteBUS.add(c.getDocumentNumber());
				}
			}
		} catch (BallartelyException e) {
			String sMensaje = "Error en getListaPrincipalClientes";
			this.logger.error(e.getMessage(), e);
			throw new FacesException(sMensaje, e);
		}
		return can;
	}

	public void obtenerEstadosClientes() {
		try {
			this.lstEstadosGenerales = new ArrayList<>();
			this.lstEstadosGenerales.add(new SelectItem(new GeneralParameter(), "-- Seleccione --"));
			List<GeneralParameter> lstGeneralParameters = this.parametroGeneralService.obtenerListaParametros(propiedades.getComboEstados());
			for(GeneralParameter g: lstGeneralParameters) {
				this.lstEstadosGenerales.add(new SelectItem(g, g.getParamValue()));
			} 
		} catch (BallartelyException e) {
			String sMensaje = "Error en obtenerEstadosClientes";
			this.logger.error(e.getMessage(), e);
			throw new FacesException(sMensaje, e);
		}
	}
	
	public void obtenerTiposDocumentoClientes() {
		try {
			this.lstTiposDocGenerales = new ArrayList<>();
			this.lstTiposDocGenerales.add(new SelectItem(new GeneralParameter(), "-- Seleccione --"));
			List<GeneralParameter> lstGeneralParameters = this.parametroGeneralService.obtenerListaParametros(propiedades.getComboTiposDocumento());
			for(GeneralParameter g: lstGeneralParameters) {
				this.lstTiposDocGenerales.add(new SelectItem(g, g.getParamValue()));
			} 
		} catch (BallartelyException e) {
			String sMensaje = "Error en obtenerTiposDocumentoClientes";
			this.logger.error(e.getMessage(), e);
			throw new FacesException(sMensaje, e);
		}
	}
	
	public void obtenerTiposClientes() {
		try {
			this.lstTiposCliGenerales = new ArrayList<>();
			this.lstTiposCliGenerales.add(new SelectItem(new GeneralParameter(), "-- Seleccione --"));
			List<GeneralParameter> lstGeneralParameters = this.parametroGeneralService.obtenerListaParametros(propiedades.getComboTiposCliente());
			for(GeneralParameter g: lstGeneralParameters) {
				this.lstTiposCliGenerales.add(new SelectItem(g, g.getParamValue()));
			} 
		} catch (BallartelyException e) {
			String sMensaje = "Error en obtenerTiposClientes";
			this.logger.error(e.getMessage(), e);
			throw new FacesException(sMensaje, e);
		}
	}

	public List<String> completeNom(String query) {
		List<String> results = new ArrayList<String>();
		for (String s : this.lstNomClienteBUS) {
			if (Utilitarios.compararCadenas(s, query.trim())) {
				results.add(s);
			}
		}
		return results;
	}

	public List<String> completeNumDoc(String query) {
		List<String> results = new ArrayList<String>();
		for (String s : this.lstNumDocClienteBUS) {
			if (Utilitarios.compararCadenas(s, query.trim())) {
				results.add(s);
			}
		}
		return results;
	}

	public Client getObjClienteMain() {
		return this.objClienteMain;
	}

	public void setObjClienteMain(Client objClienteMain) {
		this.objClienteMain = objClienteMain;
	}
	
	public Client getObjClienteSearch() {
		return objClienteSearch;
	}

	public void setObjClienteSearch(Client objClienteSearch) {
		this.objClienteSearch = objClienteSearch;
	}

	public List<Client> getLstClientesMain() {
		if ((this.lstClientesMain.isEmpty()) && (this.validaListaBuscar)) {
			this.canRegTablaPrincipal = getListaPrincipalClientes();
		}
		return this.lstClientesMain;
	}

	public void setLstClientesMain(List<Client> lstClientesMain) {
		this.lstClientesMain = lstClientesMain;
	}

	public int getCanRegTablaPrincipal() {
		return this.canRegTablaPrincipal;
	}

	public void setCanRegTablaPrincipal(int canRegTablaPrincipal) {
		this.canRegTablaPrincipal = canRegTablaPrincipal;
	}

	public List<SelectItem> getLstEstadosGenerales() {
		obtenerEstadosClientes();
		return this.lstEstadosGenerales;
	}

	public void setLstEstadosGenerales(List<SelectItem> lstEstadosGenerales) {
		this.lstEstadosGenerales = lstEstadosGenerales;
	}
	
	public List<SelectItem> getLstTiposDocGenerales() {
		obtenerTiposDocumentoClientes();
		return lstTiposDocGenerales;
	}

	public void setLstTiposDocGenerales(List<SelectItem> lstTiposDocGenerales) {
		this.lstTiposDocGenerales = lstTiposDocGenerales;
	}

	public List<SelectItem> getLstTiposCliGenerales() {
		obtenerTiposClientes();
		return lstTiposCliGenerales;
	}

	public void setLstTiposCliGenerales(List<SelectItem> lstTiposCliGenerales) {
		this.lstTiposCliGenerales = lstTiposCliGenerales;
	}
	
	public boolean isFlagConfirmEliClient() {
		return this.flagConfirmEliClient;
	}

	public void setFlagConfirmEliClient(boolean flagConfirmEliClient) {
		this.flagConfirmEliClient = flagConfirmEliClient;
	}

	public List<String> getLstNumDocClienteBUS() {
		return lstNumDocClienteBUS;
	}

	public void setLstNumDocClienteBUS(List<String> lstNumDocClienteBUS) {
		this.lstNumDocClienteBUS = lstNumDocClienteBUS;
	}

	public List<String> getLstNomClienteBUS() {
		return lstNomClienteBUS;
	}

	public void setLstNomClienteBUS(List<String> lstNomClienteBUS) {
		this.lstNomClienteBUS = lstNomClienteBUS;
	}

	public boolean isValidaListaBuscar() {
		return this.validaListaBuscar;
	}

	public void setValidaListaBuscar(boolean validaListaBuscar) {
		this.validaListaBuscar = validaListaBuscar;
	}

	public ClienteService getClienteService() {
		return clienteService;
	}

	public void setClienteService(ClienteService clienteService) {
		this.clienteService = clienteService;
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
