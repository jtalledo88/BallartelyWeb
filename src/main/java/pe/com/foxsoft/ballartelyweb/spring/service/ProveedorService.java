package pe.com.foxsoft.ballartelyweb.spring.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pe.com.foxsoft.ballartelyweb.jpa.dao.ProveedorJPA;
import pe.com.foxsoft.ballartelyweb.jpa.data.Provider;
import pe.com.foxsoft.ballartelyweb.jpa.util.JPAUtil;
import pe.com.foxsoft.ballartelyweb.spring.exception.BallartelyException;

@Component
public class ProveedorService {
	
	
	private EntityManager em;
	
	@Autowired
	private ProveedorJPA proveedorJPA;
	
	public EntityManager getEm() {
		return em;
	}
	
	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	public ProveedorJPA getProveedorJPA() {
		return proveedorJPA;
	}

	public void setProveedorJPA(ProveedorJPA proveedorJPA) {
		this.proveedorJPA = proveedorJPA;
	}

	@Transactional(readOnly=true, noRollbackFor=BallartelyException.class)
	public List<Provider> buscarProveedores(Provider provider) throws BallartelyException {		
		return proveedorJPA.getProvidersDataBase(em, provider);
	}

	@Transactional(readOnly=false, rollbackFor=Throwable.class)
	public String agregarProveedor(Provider provider) throws BallartelyException {
		return JPAUtil.persistEntity(em, provider);
	}
	
	@Transactional(readOnly=true, noRollbackFor=BallartelyException.class)
	public Provider obtenerProveedor(int itemProvider) throws BallartelyException {
		return JPAUtil.findEntity(em, Provider.class, itemProvider);
	}

	@Transactional(readOnly=false, rollbackFor=Throwable.class)
	public String editarProveedor(Provider provider) throws BallartelyException {
		return JPAUtil.mergeEntity(em, provider);
	}

	@Transactional(readOnly=false, rollbackFor=Throwable.class)
	public String eliminarProveedor(Provider provider) throws BallartelyException {
		return JPAUtil.removeEntity(em, provider);
	}

	@Transactional(readOnly=true, noRollbackFor=BallartelyException.class)
	public List<Provider> getListaProveedores() throws BallartelyException {
		return JPAUtil.executeQueryList(em, Provider.class, JPAUtil.NAMED_QUERY_ALL_PROVIDER);
	}

}
