package pe.com.foxsoft.ballartelyweb.jpa.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Component;

import pe.com.foxsoft.ballartelyweb.jpa.data.ShippingDetail;
import pe.com.foxsoft.ballartelyweb.jpa.data.ShippingHead;
import pe.com.foxsoft.ballartelyweb.jpa.util.JPAUtil;
import pe.com.foxsoft.ballartelyweb.spring.exception.BallartelyException;
import pe.com.foxsoft.ballartelyweb.spring.util.Constantes;

@Component
public class ShippingJPA {
	
	public String insertShippingDataBase(EntityManager em, ShippingHead shippinghead, List<ShippingDetail> lstShippingDetails) throws BallartelyException {
		try {
			em.getTransaction().begin();
			
			JPAUtil.persistEntity(em, shippinghead);
			
			for(ShippingDetail detail: lstShippingDetails) {
				JPAUtil.persistEntity(em, detail);
			}
			
			return Constantes.MESSAGE_PERSIST_SUCCESS;
		} catch (Exception e) {
			throw new BallartelyException(BallartelyException.GENERAL_ERROR, e.getMessage());
		}
	}

}
