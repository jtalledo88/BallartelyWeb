package pe.com.foxsoft.ballartelyweb.prime.faces.managedbeans;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.FacesException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;

import pe.com.foxsoft.ballartelyweb.jpa.data.GeneralParameter;
import pe.com.foxsoft.ballartelyweb.jpa.data.ProductLabel;
import pe.com.foxsoft.ballartelyweb.jpa.data.Provider;
import pe.com.foxsoft.ballartelyweb.jpa.data.ShippingDetail;
import pe.com.foxsoft.ballartelyweb.jpa.data.ShippingDetailPK;
import pe.com.foxsoft.ballartelyweb.jpa.data.ShippingHead;
import pe.com.foxsoft.ballartelyweb.spring.exception.BallartelyException;
import pe.com.foxsoft.ballartelyweb.spring.service.CompraService;
import pe.com.foxsoft.ballartelyweb.spring.service.CuentaService;
import pe.com.foxsoft.ballartelyweb.spring.service.EtiquetaProductoService;
import pe.com.foxsoft.ballartelyweb.spring.service.ParametroGeneralService;
import pe.com.foxsoft.ballartelyweb.spring.service.ProveedorService;
import pe.com.foxsoft.ballartelyweb.spring.util.Propiedades;
import pe.com.foxsoft.ballartelyweb.spring.util.Utilitarios;

@ManagedBean
@SessionScoped
public class CompraMB {

	protected final Logger logger = Logger.getLogger(getClass());

	@ManagedProperty("#{compraService}")
	private CompraService compraService;
	
	@ManagedProperty("#{cuentaService}")
	private CuentaService cuentaService;
	
	@ManagedProperty("#{proveedorService}")
	private ProveedorService proveedorService;
	
	@ManagedProperty("#{etiquetaProductoService}")
	private EtiquetaProductoService etiquetaProductoService;

	@ManagedProperty("#{parametroGeneralService}")
	private ParametroGeneralService parametroGeneralService;
	
	@ManagedProperty("#{propiedades}")
	private Propiedades propiedades;

	private ShippingHead objShippingHeadMain;
	private InputStream isComprobantePago;

	private List<ShippingDetail> lstItemsCompraMain;
	private List<ProductLabel> lstEtiquetasProducto;
	private List<Provider> lstProveedores;
	
	
	private BigDecimal totalCompraBruto;
	private BigDecimal igvGeneral;
	private BigDecimal totalCompraNeto;
	
	private boolean validaListaBuscar = true;
	private boolean flagConfirmEliClient = false;

	public CompraMB() {
		this.objShippingHeadMain = new ShippingHead();
		this.lstItemsCompraMain = new ArrayList<>();
		this.totalCompraBruto = new BigDecimal(0);
		this.totalCompraNeto = new BigDecimal(0);
	}

	public void agregarItemCompra() {
		ShippingDetail detail = new ShippingDetail();
		ShippingDetailPK pk = new ShippingDetailPK();
		pk.setShippingDetailId(lstItemsCompraMain.size() + 1);
		detail.setId(pk);
		detail.setShippingAmout(new BigDecimal(0));
		detail.setShippingQuantity(0);
		detail.setShippingUnitPrice(new BigDecimal(0));
		lstItemsCompraMain.add(detail);
	}

	public void registrarCompra() {
		String sMensaje = "";
		
		try {
			
		
		} catch (Exception e) {
			sMensaje = "Error en agregarCliente";
			this.logger.error(e.getMessage());
			throw new FacesException(sMensaje, e);
		}
	}

	public void editarItem(RowEditEvent event) {
		ShippingDetail detNew = (ShippingDetail)event.getObject();
		totalCompraBruto = new BigDecimal(0);
		totalCompraNeto = new BigDecimal(0);
		int totalCantidadCompra = 0;
		for(int i=0; i<lstItemsCompraMain.size(); i++) {
			ShippingDetail detOld = lstItemsCompraMain.get(i);
			if(detNew.getId().getShippingDetailId() == detOld.getId().getShippingDetailId()) {
				detOld.setProductLabel(detNew.getProductLabel());
				detOld.setShippingAmout(detNew.getShippingUnitPrice().multiply(new BigDecimal(detNew.getShippingQuantity())));
				detOld.setShippingQuantity(detNew.getShippingQuantity());
				detOld.setShippingUnitPrice(detNew.getShippingUnitPrice());
			}
			totalCompraBruto = totalCompraBruto.add(detOld.getShippingAmout());
			totalCantidadCompra += detOld.getShippingQuantity();
		}
		
		totalCompraNeto = totalCompraBruto.add(totalCompraBruto.multiply(igvGeneral));
		
		objShippingHeadMain.setShippingTotalQuantity(totalCantidadCompra);
		objShippingHeadMain.setShippingTotalAmount(totalCompraNeto);
	}
	
	public void eliminarItem(RowEditEvent event) {
		ShippingDetail detRemove = (ShippingDetail)event.getObject();
		lstItemsCompraMain.remove(detRemove);
		totalCompraBruto = new BigDecimal(0);
		totalCompraNeto = new BigDecimal(0);
		int totalCantidadCompra = 0;
		for(int i=0; i<lstItemsCompraMain.size(); i++) {
			ShippingDetail detOld = lstItemsCompraMain.get(i);
			totalCompraBruto = totalCompraBruto.add(detOld.getShippingAmout());
			totalCantidadCompra += detOld.getShippingQuantity();
		}
		
		totalCompraNeto = totalCompraBruto.add(totalCompraBruto.multiply(igvGeneral));
		
		objShippingHeadMain.setShippingTotalQuantity(totalCantidadCompra);
		objShippingHeadMain.setShippingTotalAmount(totalCompraNeto);
    }

	public List<Provider> completeProvider(String query) {
        List<Provider> lstProveedoresFiltro = new ArrayList<Provider>();
        obtenerProveedores();
        for (int i = 0; i < lstProveedores.size(); i++) {
        	Provider provider = lstProveedores.get(i);
            if(provider.getProviderSocialReason().toLowerCase().indexOf(query.toLowerCase()) != -1) {
            	lstProveedoresFiltro.add(provider);
            }
        }
         
        return lstProveedoresFiltro;
    }
	
	public void subirComprobante(FileUploadEvent event) {
		String sMensaje = null;
        try {
        	objShippingHeadMain.setShippingPaymentFile(event.getFile().getFileName());
			this.isComprobantePago = event.getFile().getInputstream();
			Utilitarios.mensaje("", "Archivo " + event.getFile().getFileName() + " subido con exito.");
		} catch (IOException e) {
			sMensaje = "Error en subirComprobante";
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
			
		} catch (Exception e) {
			sMensaje = "Error en eliminarCliente";
			this.logger.error(e.getMessage());
			throw new FacesException(sMensaje, e);
		}
		this.flagConfirmEliClient = false;
	}
	

	private void obtenerEtiquetasProducto() {
		try {
			this.lstEtiquetasProducto = this.etiquetaProductoService.getListaEtiquetaProductos();
		} catch (BallartelyException e) {
			String sMensaje = "Error en obtenerEtiquetasProducto";
			this.logger.error(e.getMessage(), e);
			throw new FacesException(sMensaje, e);
		}
	}
	
	private void obtenerProveedores() {
		try {
			this.lstProveedores = this.proveedorService.getListaProveedores();
		} catch (BallartelyException e) {
			String sMensaje = "Error en obtenerProveedores";
			this.logger.error(e.getMessage(), e);
			throw new FacesException(sMensaje, e);
		}
	}

	private void obtenerIgvGeneral() {
		try {
			GeneralParameter generalParameter = this.parametroGeneralService.obtenerParametroGeneral(propiedades.getUniqueCodeIGV());
			this.igvGeneral = new BigDecimal(generalParameter.getParamValue());
		} catch (BallartelyException e) {
			String sMensaje = "Error en obtenerIgvGeneral";
			this.logger.error(e.getMessage(), e);
			throw new FacesException(sMensaje, e);
		}
		
	}
	
	public CompraService getCompraService() {
		return compraService;
	}

	public void setCompraService(CompraService compraService) {
		this.compraService = compraService;
	}

	public CuentaService getCuentaService() {
		return cuentaService;
	}

	public void setCuentaService(CuentaService cuentaService) {
		this.cuentaService = cuentaService;
	}

	public ProveedorService getProveedorService() {
		return proveedorService;
	}

	public void setProveedorService(ProveedorService proveedorService) {
		this.proveedorService = proveedorService;
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

	public ShippingHead getObjShippingHeadMain() {
		return objShippingHeadMain;
	}

	public void setObjShippingHeadMain(ShippingHead objShippingHeadMain) {
		this.objShippingHeadMain = objShippingHeadMain;
	}

	public List<ShippingDetail> getLstItemsCompraMain() {
		return lstItemsCompraMain;
	}

	public void setLstItemsCompraMain(List<ShippingDetail> lstItemsCompraMain) {
		this.lstItemsCompraMain = lstItemsCompraMain;
	}

	public List<ProductLabel> getLstEtiquetasProducto() {
		obtenerEtiquetasProducto();
		return lstEtiquetasProducto;
	}

	public void setLstEtiquetasProducto(List<ProductLabel> lstEtiquetasProducto) {
		this.lstEtiquetasProducto = lstEtiquetasProducto;
	}

	public List<Provider> getLstProveedores() {
		return lstProveedores;
	}

	public void setLstProveedores(List<Provider> lstProveedores) {
		this.lstProveedores = lstProveedores;
	}

	public boolean isValidaListaBuscar() {
		return validaListaBuscar;
	}

	public void setValidaListaBuscar(boolean validaListaBuscar) {
		this.validaListaBuscar = validaListaBuscar;
	}

	public BigDecimal getTotalCompraBruto() {
		return totalCompraBruto;
	}

	public void setTotalCompraBruto(BigDecimal totalCompraBruto) {
		this.totalCompraBruto = totalCompraBruto;
	}

	public BigDecimal getIgvGeneral() {
		obtenerIgvGeneral();
		return igvGeneral;
	}

	public void setIgvGeneral(BigDecimal igvGeneral) {
		this.igvGeneral = igvGeneral;
	}

	public BigDecimal getTotalCompraNeto() {
		return totalCompraNeto;
	}

	public void setTotalCompraNeto(BigDecimal totalCompraNeto) {
		this.totalCompraNeto = totalCompraNeto;
	}

	public boolean isFlagConfirmEliClient() {
		return flagConfirmEliClient;
	}

	public void setFlagConfirmEliClient(boolean flagConfirmEliClient) {
		this.flagConfirmEliClient = flagConfirmEliClient;
	}

	public InputStream getIsComprobantePago() {
		return isComprobantePago;
	}

	public void setIsComprobantePago(InputStream isComprobantePago) {
		this.isComprobantePago = isComprobantePago;
	}
	
}
