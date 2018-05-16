package pe.com.foxsoft.ballartelyweb.jpa.data;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the shipping_detail database table.
 * 
 */
@Embeddable
public class ShippingDetailPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="shipping_detail_id")
	private int shippingDetailId;

	@Column(name="shipping_head_id", insertable=false, updatable=false)
	private int shippingHeadId;

	public ShippingDetailPK() {
	}
	public int getShippingDetailId() {
		return this.shippingDetailId;
	}
	public void setShippingDetailId(int shippingDetailId) {
		this.shippingDetailId = shippingDetailId;
	}
	public int getShippingHeadId() {
		return this.shippingHeadId;
	}
	public void setShippingHeadId(int shippingHeadId) {
		this.shippingHeadId = shippingHeadId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ShippingDetailPK)) {
			return false;
		}
		ShippingDetailPK castOther = (ShippingDetailPK)other;
		return 
			(this.shippingDetailId == castOther.shippingDetailId)
			&& (this.shippingHeadId == castOther.shippingHeadId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.shippingDetailId;
		hash = hash * prime + this.shippingHeadId;
		
		return hash;
	}
}