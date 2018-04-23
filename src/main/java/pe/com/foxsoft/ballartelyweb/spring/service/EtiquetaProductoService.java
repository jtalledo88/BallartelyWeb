package pe.com.foxsoft.ballartelyweb.spring.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pe.com.foxsoft.ballartelyweb.jpa.data.EtiquetaProducto;
import pe.com.foxsoft.ballartelyweb.jpa.domain.ParametroJPA;
import pe.com.foxsoft.ballartelyweb.jpa.util.JPAUtil;
import pe.com.foxsoft.ballartelyweb.spring.exception.BallartelyException;

@Component
public class EtiquetaProductoService {
	
	
	private EntityManager em;
	
	private CriteriaBuilder cb;
	
	public EntityManager getEm() {
		return em;
	}
	
	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
		this.cb = this.em.getCriteriaBuilder();
	}


	@Transactional(readOnly=true, noRollbackFor=BallartelyException.class)
	public List<EtiquetaProducto> buscarEtiquetaProductos(EtiquetaProducto objEtiquetaProducto) throws BallartelyException {
		List<ParametroJPA> parametrosEtiqProd = new ArrayList<ParametroJPA>();
		ParametroJPA pEtiqProd = new ParametroJPA(EtiquetaProducto.PRODUCT_LABEL_CODE, ParametroJPA.CLASS_STRING, 
				objEtiquetaProducto.getCodEtiqueta(), ParametroJPA.COMPARE_TYPE_EQUALS);
		parametrosEtiqProd.add(pEtiqProd);
		pEtiqProd = new ParametroJPA(EtiquetaProducto.PRODUCT_LABEL_DESCRIPTION, ParametroJPA.CLASS_STRING, 
				objEtiquetaProducto.getDescEtiqueta(), ParametroJPA.COMPARE_TYPE_LIKE);
		parametrosEtiqProd.add(pEtiqProd);
		pEtiqProd = new ParametroJPA(EtiquetaProducto.PRODUCT_LABEL_STATUS, ParametroJPA.CLASS_STRING, 
				objEtiquetaProducto.getEstadoEtiqueta(), ParametroJPA.COMPARE_TYPE_EQUALS);
		parametrosEtiqProd.add(pEtiqProd);
		
		return JPAUtil.executeQueryList(em, cb, EtiquetaProducto.class, parametrosEtiqProd);
	}

	@Transactional(readOnly=false, rollbackFor=Throwable.class)
	public String agregarEtiquetaProducto(EtiquetaProducto objEtiquetaProducto) throws BallartelyException {
		return JPAUtil.persistEntity(em, objEtiquetaProducto);
	}

	@Transactional(readOnly=true, noRollbackFor=BallartelyException.class)
	public EtiquetaProducto obtenerEtiquetaProducto(long itemEtiquetaProducto) throws BallartelyException {
		return JPAUtil.findEntity(em, EtiquetaProducto.class, itemEtiquetaProducto);
	}

	@Transactional(readOnly=false, rollbackFor=Throwable.class)
	public String editarEtiquetaProducto(EtiquetaProducto objEtiquetaProducto) throws BallartelyException {
		return JPAUtil.mergeEntity(em, objEtiquetaProducto);
	}

	@Transactional(readOnly=false, rollbackFor=Throwable.class)
	public String eliminarEtiquetaProducto(EtiquetaProducto objEtiquetaProducto) throws BallartelyException {
		return JPAUtil.removeEntity(em, objEtiquetaProducto);
	}

	@Transactional(readOnly=true, noRollbackFor=BallartelyException.class)
	public List<EtiquetaProducto> getListaEtiquetaProductos() throws BallartelyException {
		List<ParametroJPA> parametrosEtiqProd = new ArrayList<ParametroJPA>();
		
		return JPAUtil.executeQueryList(em, cb, EtiquetaProducto.class, parametrosEtiqProd);
	}

}
