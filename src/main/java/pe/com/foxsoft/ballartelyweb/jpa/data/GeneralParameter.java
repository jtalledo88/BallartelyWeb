package pe.com.foxsoft.ballartelyweb.jpa.data;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the general_parameter database table.
 * 
 */
@Entity
@Table(name="general_parameter")
@NamedQuery(name="GeneralParameter.findAll", query="SELECT g FROM GeneralParameter g")
public class GeneralParameter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="param_id")
	private int paramId;

	@Column(name="param_code")
	private String paramCode;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="param_creation_date")
	private Date paramCreationDate;

	@Column(name="param_description")
	private String paramDescription;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="param_modification_date")
	private Date paramModificationDate;

	@Column(name="param_status")
	private String paramStatus;

	@Column(name="param_type")
	private String paramType;

	@Column(name="param_value")
	private String paramValue;

	//bi-directional many-to-one association to Client
	@OneToMany(mappedBy="clientStatus")
	private List<Client> clients1;

	//bi-directional many-to-one association to Client
	@OneToMany(mappedBy="clientType")
	private List<Client> clients2;

	//bi-directional many-to-one association to Client
	@OneToMany(mappedBy="documentType")
	private List<Client> clients3;

	//bi-directional many-to-one association to ProductLabel
	@OneToMany(mappedBy="productLabelStatus")
	private List<ProductLabel> productLabels;

	public GeneralParameter() {
	}

	public int getParamId() {
		return this.paramId;
	}

	public void setParamId(int paramId) {
		this.paramId = paramId;
	}

	public String getParamCode() {
		return this.paramCode;
	}

	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}

	public Date getParamCreationDate() {
		return this.paramCreationDate;
	}

	public void setParamCreationDate(Date paramCreationDate) {
		this.paramCreationDate = paramCreationDate;
	}

	public String getParamDescription() {
		return this.paramDescription;
	}

	public void setParamDescription(String paramDescription) {
		this.paramDescription = paramDescription;
	}

	public Date getParamModificationDate() {
		return this.paramModificationDate;
	}

	public void setParamModificationDate(Date paramModificationDate) {
		this.paramModificationDate = paramModificationDate;
	}

	public String getParamStatus() {
		return this.paramStatus;
	}

	public void setParamStatus(String paramStatus) {
		this.paramStatus = paramStatus;
	}

	public String getParamType() {
		return this.paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public String getParamValue() {
		return this.paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public List<Client> getClients1() {
		return this.clients1;
	}

	public void setClients1(List<Client> clients1) {
		this.clients1 = clients1;
	}

	public Client addClients1(Client clients1) {
		getClients1().add(clients1);
		clients1.setClientStatus(this);

		return clients1;
	}

	public Client removeClients1(Client clients1) {
		getClients1().remove(clients1);
		clients1.setClientStatus(null);

		return clients1;
	}

	public List<Client> getClients2() {
		return this.clients2;
	}

	public void setClients2(List<Client> clients2) {
		this.clients2 = clients2;
	}

	public Client addClients2(Client clients2) {
		getClients2().add(clients2);
		clients2.setClientType(this);

		return clients2;
	}

	public Client removeClients2(Client clients2) {
		getClients2().remove(clients2);
		clients2.setClientType(null);

		return clients2;
	}

	public List<Client> getClients3() {
		return this.clients3;
	}

	public void setClients3(List<Client> clients3) {
		this.clients3 = clients3;
	}

	public Client addClients3(Client clients3) {
		getClients3().add(clients3);
		clients3.setDocumentType(this);

		return clients3;
	}

	public Client removeClients3(Client clients3) {
		getClients3().remove(clients3);
		clients3.setDocumentType(null);

		return clients3;
	}

	public List<ProductLabel> getProductLabels() {
		return this.productLabels;
	}

	public void setProductLabels(List<ProductLabel> productLabels) {
		this.productLabels = productLabels;
	}

	public ProductLabel addProductLabel(ProductLabel productLabel) {
		getProductLabels().add(productLabel);
		productLabel.setProductLabelStatus(this);

		return productLabel;
	}

	public ProductLabel removeProductLabel(ProductLabel productLabel) {
		getProductLabels().remove(productLabel);
		productLabel.setProductLabelStatus(null);

		return productLabel;
	}

}