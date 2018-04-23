package pe.com.foxsoft.reportsweb.spring.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pe.com.foxsoft.reportsweb.jpa.data.Usuario;
import pe.com.foxsoft.reportsweb.jpa.util.JPAUtil;
import pe.com.foxsoft.reportsweb.spring.domain.Parameter;
import pe.com.foxsoft.reportsweb.spring.exception.ReportsException;

@Component
public class ProductoService {
	
	
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

	@Transactional(readOnly=true)
	public Usuario getUser(Usuario user) throws ReportsException {
		List<ParametroJPA> parametersUser = new ArrayList<ParametroJPA>();
		ParametroJPA pUser = new ParametroJPA(Usuario.USER_NAME, ParametroJPA.CLASS_STRING, user.getUserName());
		parametersUser.add(pUser);
		pUser = new ParametroJPA(Usuario.USER_PASSWORD, ParametroJPA.CLASS_STRING, user.getUserPassword());
		parametersUser.add(pUser);
		
		return JPAUtil.executeQuerySingle(em, cb, Usuario.class, parametersUser);
	}

}
