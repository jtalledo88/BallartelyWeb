package pe.com.foxsoft.ballartelyweb.jpa.data;

import java.io.Serializable;
import javax.persistence.*;

import pe.com.foxsoft.ballartelyweb.jpa.util.JPAUtil;

import java.util.Date;


/**
 * The persistent class for the client database table.
 * 
 */
@Entity
@NamedQuery(name=JPAUtil.NAMED_QUERY_ALL_CLIENT, query="SELECT c FROM Client c join fetch c.clientStatus join fetch c.clientType join fetch c.documentType")
public class Client implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="client_id")
	private int clientId;

	@Column(name="client_address")
	private String clientAddress;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="client_creation_date")
	private Date clientCreationDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="client_modification_date")
	private Date clientModificationDate;

	@Column(name="client_names")
	private String clientNames;

	@Column(name="client_phone_number")
	private String clientPhoneNumber;

	@Column(name="client_social_reason")
	private String clientSocialReason;

	@Column(name="document_number")
	private String documentNumber;

	//bi-directional many-to-one association to GeneralParameter
	@ManyToOne
	@JoinColumn(name="client_status")
	private GeneralParameter clientStatus;

	//bi-directional many-to-one association to GeneralParameter
	@ManyToOne
	@JoinColumn(name="client_type")
	private GeneralParameter clientType;

	//bi-directional many-to-one association to GeneralParameter
	@ManyToOne
	@JoinColumn(name="document_type")
	private GeneralParameter documentType;

	public Client() {
	}

	public int getClientId() {
		return this.clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public String getClientAddress() {
		return this.clientAddress;
	}

	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}

	public Date getClientCreationDate() {
		return this.clientCreationDate;
	}

	public void setClientCreationDate(Date clientCreationDate) {
		this.clientCreationDate = clientCreationDate;
	}

	public Date getClientModificationDate() {
		return this.clientModificationDate;
	}

	public void setClientModificationDate(Date clientModificationDate) {
		this.clientModificationDate = clientModificationDate;
	}

	public String getClientNames() {
		return this.clientNames;
	}

	public void setClientNames(String clientNames) {
		this.clientNames = clientNames;
	}

	public String getClientPhoneNumber() {
		return this.clientPhoneNumber;
	}

	public void setClientPhoneNumber(String clientPhoneNumber) {
		this.clientPhoneNumber = clientPhoneNumber;
	}

	public String getClientSocialReason() {
		return this.clientSocialReason;
	}

	public void setClientSocialReason(String clientSocialReason) {
		this.clientSocialReason = clientSocialReason;
	}

	public String getDocumentNumber() {
		return this.documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public GeneralParameter getClientStatus() {
		return this.clientStatus;
	}

	public void setClientStatus(GeneralParameter clientStatus) {
		this.clientStatus = clientStatus;
	}

	public GeneralParameter getClientType() {
		return this.clientType;
	}

	public void setClientType(GeneralParameter clientType) {
		this.clientType = clientType;
	}

	public GeneralParameter getDocumentType() {
		return this.documentType;
	}

	public void setDocumentType(GeneralParameter documentType) {
		this.documentType = documentType;
	}

}