package pe.com.foxsoft.ballartelyweb.spring.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pe.com.foxsoft.ballartelyweb.jpa.dao.EtiquetaProductoJPA;
import pe.com.foxsoft.ballartelyweb.jpa.data.ProductLabel;
import pe.com.foxsoft.ballartelyweb.jpa.util.JPAUtil;
import pe.com.foxsoft.ballartelyweb.spring.exception.BallartelyException;

@Component
public class EtiquetaProductoService {
	
	
	private EntityManager em;
	
	@Autowired
	private EtiquetaProductoJPA etiquetaProductoJPA;
	
	public EntityManager getEm() {
		return em;
	}
	
	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	public EtiquetaProductoJPA getEtiquetaProductoJPA() {
		return etiquetaProductoJPA;
	}

	public void setEtiquetaProductoJPA(EtiquetaProductoJPA etiquetaProductoJPA) {
		this.etiquetaProductoJPA = etiquetaProductoJPA;
	}

	@Transactional(readOnly=true, noRollbackFor=BallartelyException.class)
	public List<ProductLabel> buscarEtiquetaProductos(ProductLabel productLabel) throws BallartelyException {		
		return etiquetaProductoJPA.getProductLabelsDataBase(em, productLabel);
	}

	@Transactional(readOnly=false, rollbackFor=Throwable.class)
	public String agregarEtiquetaProducto(ProductLabel productLabel) throws BallartelyException {
		return etiquetaProductoJPA.persistProductLabelDataBase(em, productLabel);
	}

	@Transactional(readOnly=true, noRollbackFor=BallartelyException.class)
	public ProductLabel obtenerEtiquetaProducto(int itemProductLabel) throws BallartelyException {
		return JPAUtil.findEntity(em, ProductLabel.class, itemProductLabel);
	}

	@Transactional(readOnly=false, rollbackFor=Throwable.class)
	public String editarEtiquetaProducto(ProductLabel productLabel) throws BallartelyException {
		return JPAUtil.mergeEntity(em, productLabel);
	}

	@Transactional(readOnly=false, rollbackFor=Throwable.class)
	public String eliminarEtiquetaProducto(ProductLabel productLabel) throws BallartelyException {
		return JPAUtil.removeEntity(em, productLabel);
	}

	@Transactional(readOnly=true, noRollbackFor=BallartelyException.class)
	public List<ProductLabel> getListaEtiquetaProductos() throws BallartelyException {
		return JPAUtil.executeQueryList(em, ProductLabel.class, JPAUtil.NAMED_QUERY_ALL_PRODUCT_LABEL);
	}

}
