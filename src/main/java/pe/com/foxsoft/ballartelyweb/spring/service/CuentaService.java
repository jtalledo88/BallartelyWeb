package pe.com.foxsoft.ballartelyweb.spring.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pe.com.foxsoft.ballartelyweb.jpa.dao.CuentaJPA;
import pe.com.foxsoft.ballartelyweb.jpa.data.Account;
import pe.com.foxsoft.ballartelyweb.jpa.util.JPAUtil;
import pe.com.foxsoft.ballartelyweb.spring.exception.BallartelyException;

@Component
public class CuentaService {
	
	
	private EntityManager em;
	
	@Autowired
	private CuentaJPA cuentaJPA;
	
	public EntityManager getEm() {
		return em;
	}
	
	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	public CuentaJPA getCuentaJPA() {
		return cuentaJPA;
	}

	public void setCuentaJPA(CuentaJPA cuentaJPA) {
		this.cuentaJPA = cuentaJPA;
	}

	@Transactional(readOnly=false, rollbackFor=Throwable.class)
	public String agregarCuenta(Account account) throws BallartelyException {
		return JPAUtil.persistEntity(em, account);
	}

	@Transactional(readOnly=true, noRollbackFor=BallartelyException.class)
	public Account obtenerCuenta(int itemAccount) throws BallartelyException {
		return JPAUtil.findEntity(em, Account.class, itemAccount);
	}
	
	@Transactional(readOnly=true, noRollbackFor=BallartelyException.class)
	public Account obtenerCuentaPrincipal() throws BallartelyException {
		return cuentaJPA.getAccountPrincipalDataBase(em);
	}
	
	@Transactional(readOnly=true, noRollbackFor=BallartelyException.class)
	public List<Account> obtenerCuentas(Account account) throws BallartelyException {
		return cuentaJPA.getAccountsByOwnerDataBase(em, account);
	}

	@Transactional(readOnly=false, rollbackFor=Throwable.class)
	public String editarCuenta(Account account) throws BallartelyException {
		return JPAUtil.mergeEntity(em, account);
	}

	@Transactional(readOnly=false, rollbackFor=Throwable.class)
	public String eliminarCuenta(Account account) throws BallartelyException {
		return JPAUtil.removeEntity(em, account);
	}

	@Transactional(readOnly=true, noRollbackFor=BallartelyException.class)
	public List<Account> getListaCuentas() throws BallartelyException {
		return JPAUtil.executeQueryList(em, Account.class, JPAUtil.NAMED_QUERY_ALL_ACCOUNT);
	}

}
