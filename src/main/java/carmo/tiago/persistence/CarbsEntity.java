package carmo.tiago.persistence;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CARBS")
@NamedQueries({ @NamedQuery(name = CarbsEntity.FIND_ALL, query = "SELECT s FROM CarbsEntity s"),
		@NamedQuery(name = CarbsEntity.FIND_SPECIFIC_NAME, query = "SELECT s FROM CarbsEntity s WHERE s.name LIKE ?1") })
public class CarbsEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long carbsId;
	@Column(nullable = false, unique = true)
	private String name;
	@Column(nullable = false)
	private String calories;
	@Column(nullable = false)
	private String protein;
	@Column(nullable = false)
	private String carbs;
	@Column(nullable = false)
	private String fat;
	@OneToMany(mappedBy = "carbs")
	@JoinColumn(name="carbsId")
	private Set<MealEntity> meals;

	public static final String FIND_ALL = "Carbs.findAll";
	public static final String FIND_SPECIFIC_NAME = "Carbs.findSpecificName";

	public CarbsEntity(String name, String calories, String protein, String carbs, String fat, Set<MealEntity> meals) {
		super();
		this.name = name;
		this.calories = calories;
		this.protein = protein;
		this.carbs = carbs;
		this.fat = fat;
		this.meals = meals;
	}

	public CarbsEntity() {
	}

	// -----------Getters & Setters-------------

	@JoinColumn(name = "CARBS_ID")
	public long getCarbsId() {
		return carbsId;
	}

	public String getCalories() {
		return calories;
	}

	public void setCalories(String calories) {
		this.calories = calories;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProtein() {
		return protein;
	}

	public void setProtein(String protein) {
		this.protein = protein;
	}

	public String getCarbs() {
		return carbs;
	}

	public void setCarbs(String carbs) {
		this.carbs = carbs;
	}

	public String getFat() {
		return fat;
	}

	public void setFat(String fat) {
		this.fat = fat;
	}
	
	public Set<MealEntity> getMeals() {
		return meals;
	}

	public void setMeals(Set<MealEntity> meals) {
		this.meals = meals;
	}

}
