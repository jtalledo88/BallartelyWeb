package pe.com.foxsoft.ballartelyweb.spring.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pe.com.foxsoft.ballartelyweb.jpa.data.Usuario;
import pe.com.foxsoft.ballartelyweb.jpa.domain.ParametroJPA;
import pe.com.foxsoft.ballartelyweb.jpa.util.JPAUtil;
import pe.com.foxsoft.ballartelyweb.spring.exception.BallartelyException;

@Component
public class LoginService {
	
	
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
	public Usuario getUser(Usuario user) throws BallartelyException {
		List<ParametroJPA> parametrosUsuario = new ArrayList<ParametroJPA>();
		ParametroJPA pUsuario = new ParametroJPA(Usuario.USER_NAME, ParametroJPA.CLASS_STRING, 
				user.getUsuario(), ParametroJPA.COMPARE_TYPE_EQUALS);
		parametrosUsuario.add(pUsuario);
		pUsuario = new ParametroJPA(Usuario.USER_PASSWORD, ParametroJPA.CLASS_STRING, 
				user.getContrasenia(), ParametroJPA.COMPARE_TYPE_EQUALS);
		parametrosUsuario.add(pUsuario);
		
		return JPAUtil.executeQuerySingle(em, cb, Usuario.class, parametrosUsuario);
	}

}
