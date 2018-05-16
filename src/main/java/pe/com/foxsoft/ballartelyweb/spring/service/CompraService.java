package pe.com.foxsoft.ballartelyweb.spring.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pe.com.foxsoft.ballartelyweb.jpa.dao.ShippingJPA;
import pe.com.foxsoft.ballartelyweb.jpa.data.ShippingDetail;
import pe.com.foxsoft.ballartelyweb.jpa.data.ShippingHead;
import pe.com.foxsoft.ballartelyweb.spring.exception.BallartelyException;

@Component
public class CompraService {
	
	
	private EntityManager em;
	
	@Autowired
	private ShippingJPA shippingJPA;
	
	public EntityManager getEm() {
		return em;
	}
	
	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	public ShippingJPA getShippingJPA() {
		return shippingJPA;
	}

	public void setShippingJPA(ShippingJPA shippingJPA) {
		this.shippingJPA = shippingJPA;
	}

	@Transactional(readOnly=false, rollbackFor=BallartelyException.class)
	public String insertarCompra(ShippingHead shippinghead, List<ShippingDetail> lstShippingDetails) throws BallartelyException {
		return shippingJPA.insertShippingDataBase(em, shippinghead, lstShippingDetails);
	}

}
