package pe.com.foxsoft.ballartelyweb.spring.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pe.com.foxsoft.ballartelyweb.jpa.dao.ParametroGeneralJPA;
import pe.com.foxsoft.ballartelyweb.jpa.data.GeneralParameter;
import pe.com.foxsoft.ballartelyweb.jpa.util.JPAUtil;
import pe.com.foxsoft.ballartelyweb.spring.exception.BallartelyException;

@Component
public class ParametroGeneralService {
	
	
	private EntityManager em;
	
	@Autowired
	private ParametroGeneralJPA parametroGeneralJPA;
	
	public EntityManager getEm() {
		return em;
	}
	
	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	public ParametroGeneralJPA getParametroGeneralJPA() {
		return parametroGeneralJPA;
	}

	public void setParametroGeneralJPA(ParametroGeneralJPA parametroGeneralJPA) {
		this.parametroGeneralJPA = parametroGeneralJPA;
	}

	@Transactional(readOnly=true, noRollbackFor=BallartelyException.class)
	public List<GeneralParameter> obtenerListaParametros(String tblCode) throws BallartelyException {
		return parametroGeneralJPA.getGeneralParametersDataBase(em, tblCode);
	}

	@Transactional(readOnly=true, noRollbackFor=BallartelyException.class)
	public List<GeneralParameter> buscarParametrosGenerales(GeneralParameter generalParameter) throws BallartelyException {
		return parametroGeneralJPA.getGeneralParametersDataBase(em, generalParameter);
	}

	@Transactional(readOnly=false, rollbackFor=Throwable.class)
	public String agregarParametroGeneral(GeneralParameter generalParameter) throws BallartelyException {
		return JPAUtil.persistEntity(em, generalParameter);
	}

	@Transactional(readOnly=true, noRollbackFor=BallartelyException.class)
	public GeneralParameter obtenerParametroGeneral(Integer itemGeneralParameter) throws BallartelyException {
		return JPAUtil.findEntity(em, GeneralParameter.class, itemGeneralParameter);
	}

	@Transactional(readOnly=false, rollbackFor=Throwable.class)
	public String editarParametroGeneral(GeneralParameter generalParameter) throws BallartelyException {
		return JPAUtil.mergeEntity(em, generalParameter);
	}

	@Transactional(readOnly=false, rollbackFor=Throwable.class)
	public String eliminarParametroGeneral(GeneralParameter generalParameter) throws BallartelyException {
		return JPAUtil.removeEntity(em, generalParameter);
	}

	@Transactional(readOnly=true, noRollbackFor=BallartelyException.class)
	public List<GeneralParameter> getListaParametrosGenerales() throws BallartelyException {
		return JPAUtil.executeQueryList(em, GeneralParameter.class, JPAUtil.NAMED_QUERY_ALL_GENERAL_PARAMETER);
	}

}
