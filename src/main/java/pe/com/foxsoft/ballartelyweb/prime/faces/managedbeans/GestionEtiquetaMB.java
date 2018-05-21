package pe.com.foxsoft.ballartelyweb.prime.faces.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.FacesException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.DualListModel;

import pe.com.foxsoft.ballartelyweb.jpa.data.ProductLabel;
import pe.com.foxsoft.ballartelyweb.jpa.data.ShippingDetail;
import pe.com.foxsoft.ballartelyweb.jpa.data.ShippingDetailLabel;
import pe.com.foxsoft.ballartelyweb.jpa.data.ShippingHead;
import pe.com.foxsoft.ballartelyweb.spring.exception.BallartelyException;
import pe.com.foxsoft.ballartelyweb.spring.service.CompraService;
import pe.com.foxsoft.ballartelyweb.spring.service.EtiquetaProductoService;
import pe.com.foxsoft.ballartelyweb.spring.util.Constantes;
import pe.com.foxsoft.ballartelyweb.spring.util.Utilitarios;

@ManagedBean
@SessionScoped
public class GestionEtiquetaMB {
	
	protected final Logger logger = Logger.getLogger(getClass());
	
	@ManagedProperty("#{compraService}")
	private CompraService compraService;
	
	@ManagedProperty("#{etiquetaProductoService}")
	private EtiquetaProductoService etiquetaProductoService;
	
	
	private List<ShippingHead> lstComprasHeadMain;
	private List<ShippingDetail> lstComprasDetailMain;
	private List<ShippingDetailLabel> lstEtiquetasMain;
	private DualListModel<ProductLabel> lstEtiquetasSeleccionMain;
	
	private ShippingHead objCompraSeleccionada;
	private ShippingDetail objCompraDetalleSeleccionada;

	public GestionEtiquetaMB() {
		lstComprasHeadMain = new ArrayList<>();
		lstComprasDetailMain = new ArrayList<>();
		lstEtiquetasMain = new ArrayList<>();
		lstEtiquetasSeleccionMain = new DualListModel<>();
	}
	
	public void cargaTablaDetalle() {
		String sMensaje = null;
		try {
			if(objCompraSeleccionada != null) {
				this.lstComprasDetailMain = this.compraService.getListaComprasDetalle(objCompraSeleccionada.getShippingId()); 
			}
		} catch (BallartelyException e) {
			sMensaje = "Error en cargaTablaDetalle";
			this.logger.error(e.getMessage());
			throw new FacesException(sMensaje, e);
		}
	}
	
	public void cargaTablaEtiquetas() {
		String sMensaje = null;
		try {
			if(objCompraDetalleSeleccionada != null) {
				this.lstEtiquetasMain = this.compraService.getListaComprasDetalleLabel(objCompraDetalleSeleccionada.getShippingDetailId());
			}
		} catch (BallartelyException e) {
			sMensaje = "Error en cargaTablaEtiquetas";
			this.logger.error(e.getMessage());
			throw new FacesException(sMensaje, e);
		}
	}
	
	public void openGestionarEtiquetas() {
		String sMensaje = null;
		try {
			List<ProductLabel> lstProductLabelSource = new ArrayList<>();
			List<ProductLabel> lstProductLabelTarget = new ArrayList<>();
			for(ShippingDetailLabel detailLabel: lstEtiquetasMain) {
				if(!Constantes.DETAIL_LABEL_TYPE_ORIGIN.equals(detailLabel.getShippingDetailLabelType())) {
					lstProductLabelTarget.add(detailLabel.getProductLabel());
				}
			}
			lstProductLabelSource = this.etiquetaProductoService.getListaEtiquetaProductos();
			for(ShippingDetailLabel sdl: lstEtiquetasMain) {
				for(int i=0; i<lstProductLabelSource.size(); i++) {
					ProductLabel ps = lstProductLabelSource.get(i);
					if(ps.getProductLabelId() == sdl.getProductLabel().getProductLabelId()) {
						lstProductLabelSource.remove(ps);
						break;
					}
				}
			}
			lstEtiquetasSeleccionMain = new DualListModel<>(lstProductLabelSource, lstProductLabelTarget);
			
		} catch (BallartelyException e) {
			sMensaje = "Error en openGestionarEtiquetas";
			this.logger.error(e.getMessage());
			throw new FacesException(sMensaje, e);
		}
	}
	
	public void grabarEtiquetasDetalle() {
		String sMensaje = null;
		try {
			sMensaje = this.compraService.grabarCompraDetalleLabel(lstEtiquetasMain, lstEtiquetasSeleccionMain.getTarget(), 
					objCompraDetalleSeleccionada);
			Utilitarios.mensaje("", sMensaje);
			setLstEtiquetasMain(new ArrayList<>());
			cargaTablaEtiquetas();
		} catch (BallartelyException e) {
			sMensaje = "Error en obtenerComprasHead";
			this.logger.error(e.getMessage());
			throw new FacesException(sMensaje, e);
		}
	}

	private void obtenerComprasHead() {
		String sMensaje = null;
		try {
			this.lstComprasHeadMain = this.compraService.getListaComprasCabecera();
		} catch (BallartelyException e) {
			sMensaje = "Error en obtenerComprasHead";
			this.logger.error(e.getMessage());
			throw new FacesException(sMensaje, e);
		}
		
	}

	public List<ShippingHead> getLstComprasHeadMain() {
		obtenerComprasHead();
		return lstComprasHeadMain;
	}

	public void setLstComprasHeadMain(List<ShippingHead> lstComprasHeadMain) {
		this.lstComprasHeadMain = lstComprasHeadMain;
	}

	public ShippingHead getObjCompraSeleccionada() {
		return objCompraSeleccionada;
	}

	public void setObjCompraSeleccionada(ShippingHead objCompraSeleccionada) {
		this.objCompraSeleccionada = objCompraSeleccionada;
	}
	
	public ShippingDetail getObjCompraDetalleSeleccionada() {
		return objCompraDetalleSeleccionada;
	}

	public void setObjCompraDetalleSeleccionada(ShippingDetail objCompraDetalleSeleccionada) {
		this.objCompraDetalleSeleccionada = objCompraDetalleSeleccionada;
	}

	public List<ShippingDetail> getLstComprasDetailMain() {
		return lstComprasDetailMain;
	}

	public void setLstComprasDetailMain(List<ShippingDetail> lstComprasDetailMain) {
		this.lstComprasDetailMain = lstComprasDetailMain;
	}

	public List<ShippingDetailLabel> getLstEtiquetasMain() {
		return lstEtiquetasMain;
	}

	public void setLstEtiquetasMain(List<ShippingDetailLabel> lstEtiquetasMain) {
		this.lstEtiquetasMain = lstEtiquetasMain;
	}

	public DualListModel<ProductLabel> getLstEtiquetasSeleccionMain() {
		return lstEtiquetasSeleccionMain;
	}

	public void setLstEtiquetasSeleccionMain(DualListModel<ProductLabel> lstEtiquetasSeleccionMain) {
		this.lstEtiquetasSeleccionMain = lstEtiquetasSeleccionMain;
	}

	public CompraService getCompraService() {
		return compraService;
	}

	public void setCompraService(CompraService compraService) {
		this.compraService = compraService;
	}

	public EtiquetaProductoService getEtiquetaProductoService() {
		return etiquetaProductoService;
	}

	public void setEtiquetaProductoService(EtiquetaProductoService etiquetaProductoService) {
		this.etiquetaProductoService = etiquetaProductoService;
	}	
	
}
