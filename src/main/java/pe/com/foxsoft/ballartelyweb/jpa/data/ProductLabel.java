package pe.com.foxsoft.ballartelyweb.jpa.data;

import java.io.Serializable;
import javax.persistence.*;

import pe.com.foxsoft.ballartelyweb.jpa.util.JPAUtil;

import java.util.Date;


/**
 * The persistent class for the product_label database table.
 * 
 */
@Entity
@Table(name="product_label")
@NamedQuery(name=JPAUtil.NAMED_QUERY_ALL_PRODUCT_LABEL, query="SELECT p FROM ProductLabel p join fetch p.generalParameter")
public class ProductLabel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="product_label_id")
	private int productLabelId;

	@Column(name="product_label_code")
	private String productLabelCode;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="product_label_creation_date")
	private Date productLabelCreationDate;

	@Column(name="product_label_description")
	private String productLabelDescription;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="product_label_modification_date")
	private Date productLabelModificationDate;

	//bi-directional many-to-one association to GeneralParameter
	@ManyToOne
	@JoinColumn(name="product_label_status")
	private GeneralParameter generalParameter;

	public ProductLabel() {
	}

	public int getProductLabelId() {
		return this.productLabelId;
	}

	public void setProductLabelId(int productLabelId) {
		this.productLabelId = productLabelId;
	}

	public String getProductLabelCode() {
		return this.productLabelCode;
	}

	public void setProductLabelCode(String productLabelCode) {
		this.productLabelCode = productLabelCode;
	}

	public Date getProductLabelCreationDate() {
		return this.productLabelCreationDate;
	}

	public void setProductLabelCreationDate(Date productLabelCreationDate) {
		this.productLabelCreationDate = productLabelCreationDate;
	}

	public String getProductLabelDescription() {
		return this.productLabelDescription;
	}

	public void setProductLabelDescription(String productLabelDescription) {
		this.productLabelDescription = productLabelDescription;
	}

	public Date getProductLabelModificationDate() {
		return this.productLabelModificationDate;
	}

	public void setProductLabelModificationDate(Date productLabelModificationDate) {
		this.productLabelModificationDate = productLabelModificationDate;
	}

	public GeneralParameter getGeneralParameter() {
		return this.generalParameter;
	}

	public void setGeneralParameter(GeneralParameter generalParameter) {
		this.generalParameter = generalParameter;
	}

}