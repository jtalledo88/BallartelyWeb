package pe.com.foxsoft.ballartelyweb.spring.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pe.com.foxsoft.ballartelyweb.jpa.data.ParametroGeneral;
import pe.com.foxsoft.ballartelyweb.jpa.domain.ParametroJPA;
import pe.com.foxsoft.ballartelyweb.jpa.util.JPAUtil;
import pe.com.foxsoft.ballartelyweb.spring.exception.BallartelyException;

@Component
public class ParametroGeneralService {
	
	
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
	public List<ParametroGeneral> obtenerListaParametros(String tblCode) throws BallartelyException {
		List<ParametroJPA> parametros = new ArrayList<ParametroJPA>();
		ParametroJPA pParam = new ParametroJPA(ParametroGeneral.PARAM_TYPE, ParametroJPA.CLASS_STRING, 
				tblCode, ParametroJPA.COMPARE_TYPE_EQUALS);
		parametros.add(pParam);
		
		return JPAUtil.executeQueryList(em, cb, ParametroGeneral.class, parametros);
	}

	@Transactional(readOnly=true, noRollbackFor=BallartelyException.class)
	public List<ParametroGeneral> buscarParametrosGenerales(ParametroGeneral objParametroGeneral) throws BallartelyException {
		List<ParametroJPA> parametros = new ArrayList<ParametroJPA>();
		ParametroJPA pParam = new ParametroJPA(ParametroGeneral.PARAM_CODE, ParametroJPA.CLASS_STRING, 
				objParametroGeneral.getCodParametro(), ParametroJPA.COMPARE_TYPE_EQUALS);
		parametros.add(pParam);
		pParam = new ParametroJPA(ParametroGeneral.PARAM_DESCRIPTION, ParametroJPA.CLASS_STRING, 
				objParametroGeneral.getCodParametro(), ParametroJPA.COMPARE_TYPE_LIKE);
		parametros.add(pParam);
		pParam = new ParametroJPA(ParametroGeneral.PARAM_STATUS, ParametroJPA.CLASS_STRING, 
				objParametroGeneral.getCodParametro(), ParametroJPA.COMPARE_TYPE_EQUALS);
		parametros.add(pParam);
		return JPAUtil.executeQueryList(em, cb, ParametroGeneral.class, parametros);
	}

	@Transactional(readOnly=false, rollbackFor=Throwable.class)
	public String agregarParametroGeneral(ParametroGeneral objParametroGeneral) throws BallartelyException {
		return JPAUtil.persistEntity(em, objParametroGeneral);
	}

	@Transactional(readOnly=true, noRollbackFor=BallartelyException.class)
	public ParametroGeneral obtenerParametroGeneral(long itemParametroGeneral) throws BallartelyException {
		return JPAUtil.findEntity(em, ParametroGeneral.class, itemParametroGeneral);
	}

	@Transactional(readOnly=false, rollbackFor=Throwable.class)
	public String editarParametroGeneral(ParametroGeneral objParametroGeneral) throws BallartelyException {
		return JPAUtil.mergeEntity(em, objParametroGeneral);
	}

	@Transactional(readOnly=false, rollbackFor=Throwable.class)
	public String eliminarParametroGeneral(ParametroGeneral objParametroGeneral) throws BallartelyException {
		return JPAUtil.removeEntity(em, objParametroGeneral);
	}

	@Transactional(readOnly=true, noRollbackFor=BallartelyException.class)
	public List<ParametroGeneral> getListaParametrosGenerales() throws BallartelyException {
		List<ParametroJPA> parametros = new ArrayList<ParametroJPA>();
		
		return JPAUtil.executeQueryList(em, cb, ParametroGeneral.class, parametros);
	}

}
