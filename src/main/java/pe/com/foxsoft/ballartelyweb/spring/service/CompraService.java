package pe.com.foxsoft.ballartelyweb.spring.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pe.com.foxsoft.ballartelyweb.jpa.dao.CompraJPA;
import pe.com.foxsoft.ballartelyweb.jpa.data.Movement;
import pe.com.foxsoft.ballartelyweb.jpa.data.ProductLabel;
import pe.com.foxsoft.ballartelyweb.jpa.data.ShippingDetail;
import pe.com.foxsoft.ballartelyweb.jpa.data.ShippingDetailLabel;
import pe.com.foxsoft.ballartelyweb.jpa.data.ShippingHead;
import pe.com.foxsoft.ballartelyweb.jpa.util.JPAUtil;
import pe.com.foxsoft.ballartelyweb.spring.exception.BallartelyException;

@Component
public class CompraService {
	
	
	private EntityManager em;
	
	@Autowired
	private CompraJPA compraJPA;
	
	public EntityManager getEm() {
		return em;
	}
	
	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	public CompraJPA getCompraJPA() {
		return compraJPA;
	}

	public void setCompraJPA(CompraJPA compraJPA) {
		this.compraJPA = compraJPA;
	}

	@Transactional(readOnly=false, rollbackFor=BallartelyException.class)
	public String insertarCompra(ShippingHead shippinghead, List<ShippingDetail> lstShippingDetails, Movement movement) throws BallartelyException {
		return compraJPA.insertShippingDataBase(em, shippinghead, lstShippingDetails, movement);
	}

	@Transactional(readOnly=true, noRollbackFor=BallartelyException.class)
	public List<ShippingHead> getListaComprasCabecera() throws BallartelyException {
		return compraJPA.getShippingsExistingDataBase(em);
	}
	
	@Transactional(readOnly=true, noRollbackFor=BallartelyException.class)
	public List<ShippingDetail> getListaComprasDetalle(int ShippingHeadId) throws BallartelyException {
		return compraJPA.getShippingsDetailsDataBase(em, ShippingHeadId);
	}
	
	@Transactional(readOnly=true, noRollbackFor=BallartelyException.class)
	public List<ShippingDetailLabel> getListaComprasDetalleLabel(int ShippingDetailId) throws BallartelyException {
		return compraJPA.getShippingsDetailsLabelDataBase(em, ShippingDetailId);
	}
	
	@Transactional(readOnly=false, rollbackFor=Throwable.class)
	public String eliminarCompraDetalleLabel(ShippingDetailLabel shippingDetailLabel) throws BallartelyException {
		return JPAUtil.removeEntity(em, shippingDetailLabel);
	}

	@Transactional(readOnly=false, rollbackFor=BallartelyException.class)
	public String grabarCompraDetalleLabel(List<ShippingDetailLabel> lstEtiquetasMain, 
			List<ProductLabel> target, ShippingDetail shippingDetail) throws BallartelyException{
		return compraJPA.grabarCompraDetalleLabel(em, lstEtiquetasMain, target, shippingDetail);
	}
}
