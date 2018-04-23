package pe.com.foxsoft.reportsweb.jpa.util;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import pe.com.foxsoft.reportsweb.jpa.domain.ParametroJPA;
import pe.com.foxsoft.reportsweb.spring.exception.ReportsException;

public class JPAUtil {
	
	public static <T> T executeQuerySingle(EntityManager em, CriteriaBuilder cb, Class<T> clasz, List<ParametroJPA> parameters) throws ReportsException {
		TypedQuery<T> tQuery = null;
		try {
			CriteriaQuery<T> query = cb.createQuery(clasz);
			Root<T> root = query.from(clasz);
			List<Predicate> predicates = new ArrayList<Predicate>();
			for(ParametroJPA param: parameters) {
				ParameterExpression<?> pExp = cb.parameter(Class.forName(param.getParameterType()), param.getParameterName());
				Predicate predicate = cb.equal(root.get(param.getParameterName()), pExp);
				predicates.add(predicate);
			}
			
			query.select(root).where(predicates.toArray(new Predicate[predicates.size()]));
			
			tQuery = em.createQuery(query);
			for(int i=0; i<parameters.size(); i++) {
				ParametroJPA param = parameters.get(i);
				tQuery.setParameter(param.getParameterName(), param.getParameterValue());
				
			}
			return tQuery.getSingleResult();
		}catch (NoResultException nre) {
			throw new ReportsException(ReportsException.NO_RESULT_ERROR, nre.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			throw new ReportsException(ReportsException.GENERAL_ERROR, e.getMessage());
		}
	}
	
	public static <T> List<T> executeQueryList(EntityManager em, CriteriaBuilder cb, Class<T> clasz, List<ParametroJPA> parameters) throws ClassNotFoundException {
		CriteriaQuery<T> query = cb.createQuery(clasz);
		Root<T> root = query.from(clasz);
		List<Predicate> predicates = new ArrayList<Predicate>();
		for(ParametroJPA param: parameters) {
			ParameterExpression<?> pExp = cb.parameter(Class.forName(param.getParameterType()), param.getParameterName());
			Predicate predicate = cb.equal(root.get(param.getParameterName()), pExp);
			predicates.add(predicate);
		}
		
		query.select(root).where(predicates.toArray(new Predicate[predicates.size()]));
		
		TypedQuery<T> tQuery = em.createQuery(query);
		for(int i=0; i<parameters.size(); i++) {
			ParametroJPA param = parameters.get(i);
			tQuery.setParameter(param.getParameterName(), param.getParameterValue());
			
		}
		
		return tQuery.getResultList();
	}
}
