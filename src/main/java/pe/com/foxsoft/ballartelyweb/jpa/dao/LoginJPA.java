package pe.com.foxsoft.ballartelyweb.jpa.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import pe.com.foxsoft.ballartelyweb.jpa.data.User;
import pe.com.foxsoft.ballartelyweb.spring.exception.BallartelyException;

@Component
public class LoginJPA {
	
	public User getUserDataBase(EntityManager em, User user) throws BallartelyException {
		try {
			TypedQuery<User> queryUser = em.createQuery(
					"select u from User u where u.userName = :userName and u.userPassword = :userPassword", User.class);
			queryUser.setParameter("userName", user.getUserName());
			queryUser.setParameter("userPassword", user.getUserPassword());
			
			return queryUser.getSingleResult();
		} catch (NoResultException nre) {
			throw new BallartelyException(BallartelyException.NO_RESULT_ERROR, nre.getMessage());
		} catch (Exception e) {
			throw new BallartelyException(BallartelyException.GENERAL_ERROR, e.getMessage());
		}
	}

}
