package pe.com.foxsoft.ballartelyweb.spring.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pe.com.foxsoft.ballartelyweb.jpa.dao.UsuarioJPA;
import pe.com.foxsoft.ballartelyweb.jpa.data.User;
import pe.com.foxsoft.ballartelyweb.jpa.util.JPAUtil;
import pe.com.foxsoft.ballartelyweb.spring.exception.BallartelyException;

@Component
public class UsuarioService {
	
	
	private EntityManager em;
	
	@Autowired
	private UsuarioJPA usuarioJPA;
	
	public EntityManager getEm() {
		return em;
	}
	
	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	public UsuarioJPA getUsuarioJPA() {
		return usuarioJPA;
	}
	
	public void setUsuarioJPA(UsuarioJPA UsuarioJPA) {
		this.usuarioJPA = UsuarioJPA;
	}

	@Transactional(readOnly=true)
	public User getUser(User user) throws BallartelyException {
		return usuarioJPA.getUserDataBase(em, user);
	}
	
	@Transactional(readOnly=false, rollbackFor=Throwable.class)
	public String editarUsuario(User user) throws BallartelyException {
		return JPAUtil.mergeEntity(em, user);
	}

}
