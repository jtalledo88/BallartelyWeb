package pe.com.foxsoft.ballartelyweb.jpa.data;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the shipping_detail database table.
 * 
 */
@Entity
@Table(name="shipping_detail")
@NamedQuery(name="ShippingDetail.findAll", query="SELECT s FROM ShippingDetail s")
public class ShippingDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="shipping_detail_id")
	private int shippingDetailId;

	@Column(name="shipping_amout")
	private BigDecimal shippingAmout;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="shipping_creation_date")
	private Date shippingCreationDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="shipping_modification_date")
	private Date shippingModificationDate;

	@Column(name="shipping_quantity_benefit")
	private int shippingQuantityBenefit;

	@Column(name="shipping_unit_price")
	private BigDecimal shippingUnitPrice;

	//bi-directional many-to-one association to ProductLabel
	@ManyToOne
	@JoinColumn(name="product_label_id")
	private ProductLabel productLabel;

	//bi-directional many-to-one association to ShippingHead
	@ManyToOne
	@JoinColumn(name="shipping_head_id")
	private ShippingHead shippingHead;

	public ShippingDetail() {
	}

	public int getShippingDetailId() {
		return this.shippingDetailId;
	}

	public void setShippingDetailId(int shippingDetailId) {
		this.shippingDetailId = shippingDetailId;
	}

	public BigDecimal getShippingAmout() {
		return this.shippingAmout;
	}

	public void setShippingAmout(BigDecimal shippingAmout) {
		this.shippingAmout = shippingAmout;
	}

	public Date getShippingCreationDate() {
		return this.shippingCreationDate;
	}

	public void setShippingCreationDate(Date shippingCreationDate) {
		this.shippingCreationDate = shippingCreationDate;
	}

	public Date getShippingModificationDate() {
		return this.shippingModificationDate;
	}

	public void setShippingModificationDate(Date shippingModificationDate) {
		this.shippingModificationDate = shippingModificationDate;
	}

	public int getShippingQuantityBenefit() {
		return this.shippingQuantityBenefit;
	}

	public void setShippingQuantityBenefit(int shippingQuantityBenefit) {
		this.shippingQuantityBenefit = shippingQuantityBenefit;
	}

	public BigDecimal getShippingUnitPrice() {
		return this.shippingUnitPrice;
	}

	public void setShippingUnitPrice(BigDecimal shippingUnitPrice) {
		this.shippingUnitPrice = shippingUnitPrice;
	}

	public ProductLabel getProductLabel() {
		return this.productLabel;
	}

	public void setProductLabel(ProductLabel productLabel) {
		this.productLabel = productLabel;
	}

	public ShippingHead getShippingHead() {
		return this.shippingHead;
	}

	public void setShippingHead(ShippingHead shippingHead) {
		this.shippingHead = shippingHead;
	}

}