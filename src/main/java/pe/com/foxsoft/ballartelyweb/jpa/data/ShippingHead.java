package pe.com.foxsoft.ballartelyweb.jpa.data;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the shipping_head database table.
 * 
 */
@Entity
@Table(name="shipping_head")
@NamedQuery(name="ShippingHead.findAll", query="SELECT s FROM ShippingHead s")
public class ShippingHead implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="shipping_id")
	private int shippingId;

	@Column(name="payment_document_number")
	private String paymentDocumentNumber;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="shipping_creation_date")
	private Date shippingCreationDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="shipping_modification_date")
	private Date shippingModificationDate;

	@Column(name="shipping_payment_file")
	private String shippingPaymentFile;

	@Column(name="shipping_status")
	private String shippingStatus;

	@Column(name="shipping_total_amount")
	private BigDecimal shippingTotalAmount;

	@Column(name="shipping_total_quantity")
	private int shippingTotalQuantity;

	//bi-directional many-to-one association to ShippingDetail
	@OneToMany(mappedBy="shippingHead", fetch=FetchType.EAGER)
	private List<ShippingDetail> shippingDetails;

	//bi-directional many-to-one association to Provider
	@ManyToOne
	@JoinColumn(name="provider_id")
	private Provider provider;

	public ShippingHead() {
	}

	public int getShippingId() {
		return this.shippingId;
	}

	public void setShippingId(int shippingId) {
		this.shippingId = shippingId;
	}

	public String getPaymentDocumentNumber() {
		return this.paymentDocumentNumber;
	}

	public void setPaymentDocumentNumber(String paymentDocumentNumber) {
		this.paymentDocumentNumber = paymentDocumentNumber;
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

	public String getShippingPaymentFile() {
		return this.shippingPaymentFile;
	}

	public void setShippingPaymentFile(String shippingPaymentFile) {
		this.shippingPaymentFile = shippingPaymentFile;
	}

	public String getShippingStatus() {
		return this.shippingStatus;
	}

	public void setShippingStatus(String shippingStatus) {
		this.shippingStatus = shippingStatus;
	}

	public BigDecimal getShippingTotalAmount() {
		return this.shippingTotalAmount;
	}

	public void setShippingTotalAmount(BigDecimal shippingTotalAmount) {
		this.shippingTotalAmount = shippingTotalAmount;
	}

	public int getShippingTotalQuantity() {
		return this.shippingTotalQuantity;
	}

	public void setShippingTotalQuantity(int shippingTotalQuantity) {
		this.shippingTotalQuantity = shippingTotalQuantity;
	}

	public List<ShippingDetail> getShippingDetails() {
		return this.shippingDetails;
	}

	public void setShippingDetails(List<ShippingDetail> shippingDetails) {
		this.shippingDetails = shippingDetails;
	}

	public ShippingDetail addShippingDetail(ShippingDetail shippingDetail) {
		getShippingDetails().add(shippingDetail);
		shippingDetail.setShippingHead(this);

		return shippingDetail;
	}

	public ShippingDetail removeShippingDetail(ShippingDetail shippingDetail) {
		getShippingDetails().remove(shippingDetail);
		shippingDetail.setShippingHead(null);

		return shippingDetail;
	}

	public Provider getProvider() {
		return this.provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

}