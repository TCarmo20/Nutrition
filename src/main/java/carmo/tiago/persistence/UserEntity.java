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
	@Column
	private String name;
	@Column(unique = true)
	private String email;
	@Column
	private String password;
	@Column
	private int age;
	@Column
	private double height;
	@Column
	private int weight;
	@Column
	private String sex;
	@Column
	private int activityLevel;
	@OneToMany
	private Set<NutPlanEntity> plans;

	public static final String FIND_ALL = "User.findAll";
	public static final String FIND_SPECIFIC_EMAIL = "User.findSpecificEmail";

	public UserEntity(String name, String email, String password, int age, double height, int weight, String sex,
			int activityLevel, Set<NutPlanEntity> plans) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.age = age;
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getActivityLevel() {
		return activityLevel;
	}

	public void setActivityLevel(int activityLevel) {
		this.activityLevel = activityLevel;
	}

}
