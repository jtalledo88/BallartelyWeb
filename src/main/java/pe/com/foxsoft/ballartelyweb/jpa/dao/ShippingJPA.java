package pe.com.foxsoft.ballartelyweb.jpa.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import pe.com.foxsoft.ballartelyweb.jpa.data.Movement;
import pe.com.foxsoft.ballartelyweb.jpa.data.ProductStock;
import pe.com.foxsoft.ballartelyweb.jpa.data.ShippingDetail;
import pe.com.foxsoft.ballartelyweb.jpa.data.ShippingHead;
import pe.com.foxsoft.ballartelyweb.jpa.util.JPAUtil;
import pe.com.foxsoft.ballartelyweb.spring.exception.BallartelyException;
import pe.com.foxsoft.ballartelyweb.spring.util.Constantes;

@Component
public class ShippingJPA {
	
	public String insertShippingDataBase(EntityManager em, ShippingHead shippinghead, List<ShippingDetail> lstShippingDetails, Movement movement) throws BallartelyException {
		try {
			em.getTransaction().begin();
			
			JPAUtil.persistEntity(em, shippinghead);
			int shippingId = getShippingIdDataBase(em, shippinghead.getPaymentDocumentNumber());
			
			for(ShippingDetail detail: lstShippingDetails) {
				detail.getId().setShippingHeadId(shippingId);
				JPAUtil.persistEntity(em, detail);
				ProductStock productStock = JPAUtil.findEntity(em, ProductStock.class, detail.getProductLabel().getProductLabelId());
				productStock.setProductStockCant(productStock.getProductStockCant() + detail.getShippingQuantity());
				JPAUtil.mergeEntity(em, productStock);
			}
			
			JPAUtil.persistEntity(em, movement);
			em.getTransaction().commit();
			return Constantes.MESSAGE_PERSIST_SUCCESS;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new BallartelyException(BallartelyException.GENERAL_ERROR, e.getMessage());
		}
	}

	private int getShippingIdDataBase(EntityManager em, String paymentDocumentNumber) throws BallartelyException{
		try {
			TypedQuery<Integer> queryShippingId = em.createQuery(
					"select s.shippingId from ShippingHead s where s.paymentDocumentNumber = :paymentDocumentNumber", Integer.class);
			queryShippingId.setParameter("paymentDocumentNumber", paymentDocumentNumber);
				
			return queryShippingId.getSingleResult().intValue();
		} catch (NoResultException nre) {
			throw new BallartelyException(BallartelyException.NO_RESULT_ERROR, nre.getMessage());
		} catch (Exception e) {
			throw new BallartelyException(BallartelyException.GENERAL_ERROR, e.getMessage());
		}
	}

}
