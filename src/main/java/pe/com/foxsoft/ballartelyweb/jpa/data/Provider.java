package pe.com.foxsoft.ballartelyweb.jpa.data;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the provider database table.
 * 
 */
@Entity
@NamedQuery(name="Provider.findAll", query="SELECT p FROM Provider p")
public class Provider implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="provider_id")
	private int providerId;

	@Column(name="provider_address")
	private String providerAddress;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="provider_creation_date")
	private Date providerCreationDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="provider_modification_date")
	private Date providerModificationDate;

	@Column(name="provider_phone_number")
	private String providerPhoneNumber;

	@Column(name="provider_ruc")
	private String providerRuc;

	@Column(name="provider_social_reason")
	private String providerSocialReason;

	@Column(name="provider_status")
	private String providerStatus;

	//bi-directional many-to-one association to Movement
	@OneToMany(mappedBy="provider", fetch=FetchType.EAGER)
	private List<Movement> movements;

	//bi-directional many-to-one association to ShippingHead
	@OneToMany(mappedBy="provider", fetch=FetchType.EAGER)
	private List<ShippingHead> shippingHeads;

	public Provider() {
	}

	public int getProviderId() {
		return this.providerId;
	}

	public void setProviderId(int providerId) {
		this.providerId = providerId;
	}

	public String getProviderAddress() {
		return this.providerAddress;
	}

	public void setProviderAddress(String providerAddress) {
		this.providerAddress = providerAddress;
	}

	public Date getProviderCreationDate() {
		return this.providerCreationDate;
	}

	public void setProviderCreationDate(Date providerCreationDate) {
		this.providerCreationDate = providerCreationDate;
	}

	public Date getProviderModificationDate() {
		return this.providerModificationDate;
	}

	public void setProviderModificationDate(Date providerModificationDate) {
		this.providerModificationDate = providerModificationDate;
	}

	public String getProviderPhoneNumber() {
		return this.providerPhoneNumber;
	}

	public void setProviderPhoneNumber(String providerPhoneNumber) {
		this.providerPhoneNumber = providerPhoneNumber;
	}

	public String getProviderRuc() {
		return this.providerRuc;
	}

	public void setProviderRuc(String providerRuc) {
		this.providerRuc = providerRuc;
	}

	public String getProviderSocialReason() {
		return this.providerSocialReason;
	}

	public void setProviderSocialReason(String providerSocialReason) {
		this.providerSocialReason = providerSocialReason;
	}

	public String getProviderStatus() {
		return this.providerStatus;
	}

	public void setProviderStatus(String providerStatus) {
		this.providerStatus = providerStatus;
	}

	public List<Movement> getMovements() {
		return this.movements;
	}

	public void setMovements(List<Movement> movements) {
		this.movements = movements;
	}

	public Movement addMovement(Movement movement) {
		getMovements().add(movement);
		movement.setProvider(this);

		return movement;
	}

	public Movement removeMovement(Movement movement) {
		getMovements().remove(movement);
		movement.setProvider(null);

		return movement;
	}

	public List<ShippingHead> getShippingHeads() {
		return this.shippingHeads;
	}

	public void setShippingHeads(List<ShippingHead> shippingHeads) {
		this.shippingHeads = shippingHeads;
	}

	public ShippingHead addShippingHead(ShippingHead shippingHead) {
		getShippingHeads().add(shippingHead);
		shippingHead.setProvider(this);

		return shippingHead;
	}

	public ShippingHead removeShippingHead(ShippingHead shippingHead) {
		getShippingHeads().remove(shippingHead);
		shippingHead.setProvider(null);

		return shippingHead;
	}

}