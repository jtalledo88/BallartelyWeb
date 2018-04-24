package pe.com.foxsoft.ballartelyweb.jpa.util;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import pe.com.foxsoft.ballartelyweb.jpa.domain.ParametroJPA;
import pe.com.foxsoft.ballartelyweb.spring.exception.BallartelyException;
import pe.com.foxsoft.ballartelyweb.spring.util.Constantes;

public class JPAUtil {
	
	@SuppressWarnings("unchecked")
	public static <T> T executeQuerySingle(EntityManager em, CriteriaBuilder cb, Class<T> clasz, List<ParametroJPA> parameters) throws BallartelyException {
		try {
			CriteriaQuery<T> query = cb.createQuery(clasz);
			Root<T> root = query.from(clasz);
			List<Predicate> predicates = new ArrayList<Predicate>();
			for(ParametroJPA param: parameters) {
				ParameterExpression<?> pExp = cb.parameter(Class.forName(param.getParameterType()), param.getParameterName());
				Predicate predicate = null;
				if(ParametroJPA.COMPARE_TYPE_EQUALS.equals(param.getParameterCompareType())) {
					predicate = cb.equal(root.get(param.getParameterName()), pExp);
				}else if(ParametroJPA.COMPARE_TYPE_LIKE.equals(param.getParameterCompareType())) {
					predicate = cb.like(root.<String>get(param.getParameterName()), (Expression<String>) pExp);
				}
				
				predicates.add(predicate);
			}
			
			query.select(root).where(predicates.toArray(new Predicate[predicates.size()]));
			
			TypedQuery<T> tQuery = em.createQuery(query);
			for(int i=0; i<parameters.size(); i++) {
				ParametroJPA param = parameters.get(i);
				tQuery.setParameter(param.getParameterName(), param.getParameterValue());
				
			}
			return tQuery.getSingleResult();
		}catch (NoResultException nre) {
			throw new BallartelyException(BallartelyException.NO_RESULT_ERROR, nre.getMessage());
		}catch (Exception e) {
			throw new BallartelyException(BallartelyException.GENERAL_ERROR, e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> executeQueryList(EntityManager em, CriteriaBuilder cb, Class<T> clasz, List<ParametroJPA> parameters) throws BallartelyException {
		try {
			CriteriaQuery<T> query = cb.createQuery(clasz);
			Root<T> root = query.from(clasz);
			List<Predicate> predicates = new ArrayList<Predicate>();
			for(ParametroJPA param: parameters) {
				ParameterExpression<?> pExp = cb.parameter(Class.forName(param.getParameterType()), param.getParameterName());
				Predicate predicate = null;
				if(ParametroJPA.COMPARE_TYPE_EQUALS.equals(param.getParameterCompareType())) {
					predicate = cb.equal(root.get(param.getParameterName()), pExp);
				}else if(ParametroJPA.COMPARE_TYPE_LIKE.equals(param.getParameterCompareType())) {
					predicate = cb.like(root.<String>get(param.getParameterName()), (Expression<String>) pExp);
				}
				
				predicates.add(predicate);
			}
			
			query.select(root).where(predicates.toArray(new Predicate[predicates.size()]));
			
			TypedQuery<T> tQuery = em.createQuery(query);
			for(int i=0; i<parameters.size(); i++) {
				ParametroJPA param = parameters.get(i);
				tQuery.setParameter(param.getParameterName(), param.getParameterValue());
				
			}
			
			return tQuery.getResultList();
		} catch (NoResultException nre) {
			throw new BallartelyException(BallartelyException.NO_RESULT_ERROR, nre.getMessage());
		} catch (Exception e) {
			throw new BallartelyException(BallartelyException.GENERAL_ERROR, e.getMessage());
		}
	}

	public static String persistEntity(EntityManager em, Object objEntity) throws BallartelyException {
		try {
			em.persist(objEntity);
			return Constantes.MESSAGE_PERSIST_SUCCESS;
		} catch (Exception e) {
			throw new BallartelyException(BallartelyException.GENERAL_ERROR, e.getMessage());
		}
	}

	public static <T> T findEntity(EntityManager em, Class<T> clasz, Object idEntity) throws BallartelyException {
		try {
			return em.find(clasz, idEntity);
		} catch (NoResultException nre) {
			throw new BallartelyException(BallartelyException.NO_RESULT_ERROR, nre.getMessage());
		} catch (Exception e) {
			throw new BallartelyException(BallartelyException.GENERAL_ERROR, e.getMessage());
		}
	}

	public static String mergeEntity(EntityManager em, Object objEntity) throws BallartelyException {
		try {
			em.merge(objEntity);
			return Constantes.MESSAGE_MERGE_SUCCESS;
		} catch (Exception e) {
			throw new BallartelyException(BallartelyException.GENERAL_ERROR, e.getMessage());
		}
	}

	public static String removeEntity(EntityManager em, Object objEntity) throws BallartelyException {
		try {
			Object objRemove = em.merge(objEntity);
			em.remove(objRemove);
			return Constantes.MESSAGE_REMOVE_SUCCESS;
		} catch (Exception e) {
			throw new BallartelyException(BallartelyException.GENERAL_ERROR, e.getMessage());
		}
	}
}
