package carmo.tiago.persistence;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "USERENT")
@NamedQueries({ @NamedQuery(name = UserEntity.FIND_ALL, query = "SELECT s FROM UserEntity s"),
		@NamedQuery(name = UserEntity.FIND_SPECIFIC_EMAIL, query = "SELECT s FROM UserEntity s WHERE s.email LIKE ?1") })
public class UserEntity implements Serializable {

	private static final long serialVersionUID = 3983805850435530749L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;
	@Column(nullable = false)
	private String name;
	@Column(unique = true, nullable = false)
	private String email;
	@Column(nullable = false)
	private String password;
	@Column
	private String dob;
	@Column
	private String height;
	@Column
	private String weight;
	@Column
	private String sex;
	@Column
	private String activityLevel;
	@OneToMany(mappedBy="user")
	private Set<NutPlanEntity> plans;

	public static final String FIND_ALL = "User.findAll";
	public static final String FIND_SPECIFIC_EMAIL = "User.findSpecificEmail";

	public UserEntity(String name, String email, String password, String dob, String height, String weight, String sex,
			String activityLevel, Set<NutPlanEntity> plans) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.dob = dob;
		this.height = height;
		this.weight = weight;
		this.sex = sex;
		this.activityLevel = activityLevel;
		this.plans = plans;
	}

	public UserEntity() {
	}

	// -----------Getters & Setters-------------

	@Column(name = "USER_ID")
	public long getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<NutPlanEntity> getPlans() {
		return plans;
	}

	public void setPlans(Set<NutPlanEntity> plans) {
		this.plans = plans;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getActivityLevel() {
		return activityLevel;
	}

	public void setActivityLevel(String activityLevel) {
		this.activityLevel = activityLevel;
	}

}
