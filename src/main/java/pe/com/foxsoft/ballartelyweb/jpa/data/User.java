package pe.com.foxsoft.ballartelyweb.jpa.data;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id")
	private int userId;

	@Column(name="user_complete_names")
	private String userCompleteNames;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="user_creation_date")
	private Date userCreationDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="user_modification_date")
	private Date userModificationDate;

	@Column(name="user_name")
	private String userName;

	@Column(name="user_password")
	private String userPassword;

	public User() {
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserCompleteNames() {
		return this.userCompleteNames;
	}

	public void setUserCompleteNames(String userCompleteNames) {
		this.userCompleteNames = userCompleteNames;
	}

	public Date getUserCreationDate() {
		return this.userCreationDate;
	}

	public void setUserCreationDate(Date userCreationDate) {
		this.userCreationDate = userCreationDate;
	}

	public Date getUserModificationDate() {
		return this.userModificationDate;
	}

	public void setUserModificationDate(Date userModificationDate) {
		this.userModificationDate = userModificationDate;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return this.userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

}