package pe.com.foxsoft.ballartelyweb.spring.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pe.com.foxsoft.ballartelyweb.jpa.dao.LoginJPA;
import pe.com.foxsoft.ballartelyweb.jpa.data.User;
import pe.com.foxsoft.ballartelyweb.spring.exception.BallartelyException;

@Component
public class LoginService {
	
	
	private EntityManager em;
	
	@Autowired
	private LoginJPA loginJPA;
	
	public EntityManager getEm() {
		return em;
	}
	
	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	public LoginJPA getLoginJPA() {
		return loginJPA;
	}
	
	public void setLoginJPA(LoginJPA loginJPA) {
		this.loginJPA = loginJPA;
	}

	@Transactional(readOnly=true)
	public User getUser(User user) throws BallartelyException {
		return loginJPA.getUserDataBase(em, user);
	}

}
