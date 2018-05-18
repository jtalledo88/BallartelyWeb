package pe.com.foxsoft.ballartelyweb.jpa.dao;

import java.util.Date;
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
			List<ShippingHead> lstShippingExisting = getShippingsExistingDataBase(em);
			for(int i=0; i<lstShippingExisting.size(); i++) {
				ShippingHead head = lstShippingExisting.get(i);
				head.setShippingModificationDate(new Date());
				head.setShippingStatus(Constantes.STATUS_PRODUCT_COLD + (i+1));
				JPAUtil.persistEntity(em, head);
			}
			
			JPAUtil.persistEntity(em, shippinghead);
			int shippingId = getShippingIdDataBase(em, shippinghead.getPaymentDocumentNumber());
			
			for(ShippingDetail detail: lstShippingDetails) {
				if(detail.getShippingQuantityBenefit() == 0) {
					continue;
				}
				detail.setShippingDetailId(0);
				shippinghead.setShippingId(shippingId);
				detail.setShippingHead(shippinghead);
				JPAUtil.persistEntity(em, detail);
				ProductStock productStock = JPAUtil.findEntity(em, ProductStock.class, detail.getProductLabel().getProductLabelId());
				productStock.setProductStockCant(productStock.getProductStockCant() + detail.getShippingQuantityBenefit());
				productStock.setProductStockModificationDate(new Date());
				JPAUtil.mergeEntity(em, productStock);
			}
			
			JPAUtil.persistEntity(em, movement);
			return Constantes.MESSAGE_PERSIST_SUCCESS;
		} catch (Exception e) {
			throw new BallartelyException(BallartelyException.GENERAL_ERROR, e.getMessage());
		}
	}

	public int getShippingIdDataBase(EntityManager em, String paymentDocumentNumber) throws BallartelyException{
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
	
	public List<ShippingHead> getShippingsExistingDataBase(EntityManager em) throws BallartelyException{
		try {
			TypedQuery<ShippingHead> queryShippingId = em.createQuery(
					"select s from ShippingHead s order by s.shippingCreationDate desc", ShippingHead.class);
				
			return queryShippingId.getResultList();
		} catch (NoResultException nre) {
			throw new BallartelyException(BallartelyException.NO_RESULT_ERROR, nre.getMessage());
		} catch (Exception e) {
			throw new BallartelyException(BallartelyException.GENERAL_ERROR, e.getMessage());
		}
	}

}
