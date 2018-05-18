package pe.com.foxsoft.ballartelyweb.jpa.data;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the shipping_detail_label database table.
 * 
 */
@Entity
@Table(name="shipping_detail_label")
@NamedQuery(name="ShippingDetailLabel.findAll", query="SELECT s FROM ShippingDetailLabel s")
public class ShippingDetailLabel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="shipping_detail_label_id")
	private int shippingDetailLabelId;

	@Column(name="shipping_detail_id")
	private int shippingDetailId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="shipping_detail_label_creation_date")
	private Date shippingDetailLabelCreationDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="shipping_detail_label_modification_date")
	private Date shippingDetailLabelModificationDate;

	@Column(name="shipping_detail_label_type")
	private String shippingDetailLabelType;

	//bi-directional many-to-one association to ProductLabel
	@ManyToOne
	@JoinColumn(name="product_label_id")
	private ProductLabel productLabel;

	public ShippingDetailLabel() {
	}

	public int getShippingDetailLabelId() {
		return this.shippingDetailLabelId;
	}

	public void setShippingDetailLabelId(int shippingDetailLabelId) {
		this.shippingDetailLabelId = shippingDetailLabelId;
	}

	public int getShippingDetailId() {
		return this.shippingDetailId;
	}

	public void setShippingDetailId(int shippingDetailId) {
		this.shippingDetailId = shippingDetailId;
	}

	public Date getShippingDetailLabelCreationDate() {
		return this.shippingDetailLabelCreationDate;
	}

	public void setShippingDetailLabelCreationDate(Date shippingDetailLabelCreationDate) {
		this.shippingDetailLabelCreationDate = shippingDetailLabelCreationDate;
	}

	public Date getShippingDetailLabelModificationDate() {
		return this.shippingDetailLabelModificationDate;
	}

	public void setShippingDetailLabelModificationDate(Date shippingDetailLabelModificationDate) {
		this.shippingDetailLabelModificationDate = shippingDetailLabelModificationDate;
	}

	public String getShippingDetailLabelType() {
		return this.shippingDetailLabelType;
	}

	public void setShippingDetailLabelType(String shippingDetailLabelType) {
		this.shippingDetailLabelType = shippingDetailLabelType;
	}

	public ProductLabel getProductLabel() {
		return this.productLabel;
	}

	public void setProductLabel(ProductLabel productLabel) {
		this.productLabel = productLabel;
	}

}