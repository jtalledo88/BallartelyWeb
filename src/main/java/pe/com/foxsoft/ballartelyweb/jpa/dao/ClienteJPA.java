package pe.com.foxsoft.ballartelyweb.jpa.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import pe.com.foxsoft.ballartelyweb.jpa.data.Client;
import pe.com.foxsoft.ballartelyweb.spring.exception.BallartelyException;

@Component
public class ClienteJPA {
	
	public List<Client> getClientsDataBase(EntityManager em, Client client) throws BallartelyException {
		try {
			TypedQuery<Client> queryProductLabel = em.createQuery(
					"select c from Client c join fetch c.clientStatus cs join fetch c.clientType ct join fetch c.documentType dt "
					+ "where c.clientNames = :clientNames or c.documentNumber =:documentNumber or cs.paramId = :clientStatus or "
					+ "or ct.paramId =:clientType or dt.paramId =:documentType", Client.class);
			queryProductLabel.setParameter("clientNames", client.getClientNames());
			queryProductLabel.setParameter("documentNumber", client.getDocumentNumber());
			queryProductLabel.setParameter("clientStatus", client.getClientStatus().getParamId());
			queryProductLabel.setParameter("clientType", client.getClientType().getParamId());
			queryProductLabel.setParameter("documentType", client.getDocumentType().getParamId());
			
			return queryProductLabel.getResultList();
		} catch (NoResultException nre) {
			throw new BallartelyException(BallartelyException.NO_RESULT_ERROR, nre.getMessage());
		} catch (Exception e) {
			throw new BallartelyException(BallartelyException.GENERAL_ERROR, e.getMessage());
		}
	}

}
