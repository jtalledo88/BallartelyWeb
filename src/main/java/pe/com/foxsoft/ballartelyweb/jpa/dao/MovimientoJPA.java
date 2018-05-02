package pe.com.foxsoft.ballartelyweb.jpa.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import pe.com.foxsoft.ballartelyweb.jpa.data.Account;
import pe.com.foxsoft.ballartelyweb.spring.exception.BallartelyException;
import pe.com.foxsoft.ballartelyweb.spring.util.Constantes;

@Component
public class MovimientoJPA {
	
	public List<Account> getAccountsByOwnerDataBase(EntityManager em, Account account) throws BallartelyException {
		try {
			TypedQuery<Account> queryUser = em.createQuery(
					"select m from M a join fetch a.client c where (a.accountType = '" + Constantes.ACCOUNT_TYPE_C + 
					"' and c.clientId = :clientId) or (a.accountType = '" + Constantes.ACCOUNT_TYPE_P +"' and c.clientId is null) "
							+ "and a.accountStatus = :accountStatus", Account.class);
			queryUser.setParameter("clientId", account.getClient().getClientId());
			queryUser.setParameter("accountStatus", account.getAccountStatus());
			
			return queryUser.getResultList();
		} catch (NoResultException nre) {
			throw new BallartelyException(BallartelyException.NO_RESULT_ERROR, nre.getMessage());
		} catch (Exception e) {
			throw new BallartelyException(BallartelyException.GENERAL_ERROR, e.getMessage());
		}
	}

}
