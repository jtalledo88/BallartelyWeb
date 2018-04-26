package pe.com.foxsoft.ballartelyweb.spring.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pe.com.foxsoft.ballartelyweb.jpa.dao.ClienteJPA;
import pe.com.foxsoft.ballartelyweb.jpa.data.Client;
import pe.com.foxsoft.ballartelyweb.jpa.util.JPAUtil;
import pe.com.foxsoft.ballartelyweb.spring.exception.BallartelyException;

@Component
public class ClienteService {
	
	
	private EntityManager em;
	
	@Autowired
	private ClienteJPA clienteJPA;
	
	public EntityManager getEm() {
		return em;
	}
	
	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	public ClienteJPA getClienteJPA() {
		return clienteJPA;
	}

	public void setClienteJPA(ClienteJPA clienteJPA) {
		this.clienteJPA = clienteJPA;
	}

	@Transactional(readOnly=true, noRollbackFor=BallartelyException.class)
	public List<Client> buscarClientes(Client client) throws BallartelyException {		
		return clienteJPA.getClientsDataBase(em, client);
	}

	@Transactional(readOnly=false, rollbackFor=Throwable.class)
	public String agregarCliente(Client client) throws BallartelyException {
		return JPAUtil.persistEntity(em, client);
	}

	@Transactional(readOnly=true, noRollbackFor=BallartelyException.class)
	public Client obtenerCliente(int itemClient) throws BallartelyException {
		return JPAUtil.findEntity(em, Client.class, itemClient);
	}

	@Transactional(readOnly=false, rollbackFor=Throwable.class)
	public String editarCliente(Client client) throws BallartelyException {
		return JPAUtil.mergeEntity(em, client);
	}

	@Transactional(readOnly=false, rollbackFor=Throwable.class)
	public String eliminarCliente(Client client) throws BallartelyException {
		return JPAUtil.removeEntity(em, client);
	}

	@Transactional(readOnly=true, noRollbackFor=BallartelyException.class)
	public List<Client> getListaClientes() throws BallartelyException {
		return JPAUtil.executeQueryList(em, Client.class, JPAUtil.NAMED_QUERY_ALL_CLIENT);
	}

}
