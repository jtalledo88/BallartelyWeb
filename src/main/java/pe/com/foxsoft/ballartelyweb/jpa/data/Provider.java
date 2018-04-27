package pe.com.foxsoft.ballartelyweb.jpa.data;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


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

	@Column(name="provider_phon_number")
	private String providerPhonNumber;

	@Column(name="provider_ruc")
	private String providerRuc;

	@Column(name="provider_social_reason")
	private String providerSocialReason;

	@Column(name="provider_status")
	private String providerStatus;

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

	public String getProviderPhonNumber() {
		return this.providerPhonNumber;
	}

	public void setProviderPhonNumber(String providerPhonNumber) {
		this.providerPhonNumber = providerPhonNumber;
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

}