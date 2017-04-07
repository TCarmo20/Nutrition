package carmo.tiago.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Tiago Carmo
 *
 */
@Entity
@Table(name = "PLAN")
@NamedQueries({ @NamedQuery(name = NutPlanEntity.FIND_ALL, query = "SELECT s FROM NutPlanEntity s"),
		@NamedQuery(name = NutPlanEntity.FIND_SPECIFIC_NAME, query = "SELECT s FROM NutPlanEntity s WHERE s.name LIKE ?1") })
public class NutPlanEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long planId;
	@Column(nullable = false)
	private String name;
	@Column
	private double calories;
	@Column
	private double protein;
	@Column
	private double carbs;
	@Column
	private double fat;
	@Column(nullable = false)
	private String objective;
	@ManyToOne
	private UserEntity user;

	public static final String FIND_ALL = "NutPlan.findAll";
	public static final String FIND_SPECIFIC_NAME = "NutPlan.findSpecificName";

	public NutPlanEntity(String name, double calories, double protein, double carbs, double fat, String objective,
			UserEntity user) {
		super();
		this.name = name;
		this.calories = calories;
		this.protein = protein;
		this.carbs = carbs;
		this.fat = fat;
		this.objective = objective;
		this.user = user;
	}

	public NutPlanEntity() {
	}

	// -----------Getters & Setters-------------

	@Column(name = "PLAN_ID")
	public long getPlanId() {
		return planId;
	}

	public double getCalories() {
		return calories;
	}

	public void setCalories(double calories) {
		this.calories = calories;
	}

	public double getProtein() {
		return protein;
	}

	public void setProtein(double protein) {
		this.protein = protein;
	}

	public double getCarbs() {
		return carbs;
	}

	public void setCarbs(double carbs) {
		this.carbs = carbs;
	}

	public double getFat() {
		return fat;
	}

	public void setFat(double fat) {
		this.fat = fat;
	}

	public String getObjective() {
		return objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
