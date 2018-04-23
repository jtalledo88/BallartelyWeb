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
public class ParametroService {
	
	
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
		ParametroJPA pParam = new ParametroJPA(ParametroGeneral.PARAM_CODE, ParametroJPA.CLASS_STRING, 
				tblCode, ParametroJPA.COMPARE_TYPE_EQUALS);
		parametros.add(pParam);
		
		return JPAUtil.executeQueryList(em, cb, ParametroGeneral.class, parametros);
	}

}
