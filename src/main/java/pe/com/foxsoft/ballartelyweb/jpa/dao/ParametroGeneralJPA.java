package pe.com.foxsoft.ballartelyweb.jpa.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import pe.com.foxsoft.ballartelyweb.jpa.data.GeneralParameter;
import pe.com.foxsoft.ballartelyweb.spring.exception.BallartelyException;

@Component
public class ParametroGeneralJPA {
	
	
	public List<GeneralParameter> getGeneralParametersDataBase(EntityManager em, String paramType) throws BallartelyException {
		try {
			TypedQuery<GeneralParameter> queryProductLabel = em.createQuery(
					"select g from GeneralParameter g where g.paramType = :paramType and g.paramStatus = :paramStatus", GeneralParameter.class);
			queryProductLabel.setParameter("paramType", paramType);
			queryProductLabel.setParameter("paramStatus", "1");
			
			return queryProductLabel.getResultList();
		} catch (NoResultException nre) {
			throw new BallartelyException(BallartelyException.NO_RESULT_ERROR, nre.getMessage());
		} catch (Exception e) {
			throw new BallartelyException(BallartelyException.GENERAL_ERROR, e.getMessage());
		}
	}
	
	public GeneralParameter getGeneralParameterDataBase(EntityManager em, String paramCode) throws BallartelyException {
		try {
			TypedQuery<GeneralParameter> queryGeneralParameter = em.createQuery(
					"select g from GeneralParameter g where g.paramCode = :paramCode and g.paramStatus = :paramStatus", GeneralParameter.class);
			queryGeneralParameter.setParameter("paramCode", paramCode);
			queryGeneralParameter.setParameter("paramStatus", "1");
			
			return queryGeneralParameter.getSingleResult();
		} catch (NoResultException nre) {
			throw new BallartelyException(BallartelyException.NO_RESULT_ERROR, nre.getMessage());
		} catch (Exception e) {
			throw new BallartelyException(BallartelyException.GENERAL_ERROR, e.getMessage());
		}
	}
	
	public List<GeneralParameter> getGeneralParametersDataBase(EntityManager em, GeneralParameter generalParameter) throws BallartelyException {
		try {
			TypedQuery<GeneralParameter> queryProductLabel = em.createQuery(
					"select g from GeneralParameter g where g.paramCode = :paramCode "
					+ "or g.paramDescription like %:paramDescription% or "
					+ "g.paramStatus =: paramStatus or g.paramType = :paramType", GeneralParameter.class);
			queryProductLabel.setParameter("paramCode", generalParameter.getParamCode());
			queryProductLabel.setParameter("paramDescription", generalParameter.getParamDescription());
			queryProductLabel.setParameter("paramStatus", generalParameter.getParamStatus());
			queryProductLabel.setParameter("paramType", generalParameter.getParamType());
			
			return queryProductLabel.getResultList();
		} catch (NoResultException nre) {
			throw new BallartelyException(BallartelyException.NO_RESULT_ERROR, nre.getMessage());
		} catch (Exception e) {
			throw new BallartelyException(BallartelyException.GENERAL_ERROR, e.getMessage());
		}
	}

}
