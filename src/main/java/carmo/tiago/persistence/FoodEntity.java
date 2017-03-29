package carmo.tiago.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@MappedSuperclass
@Entity
@NamedQueries({ @NamedQuery(name = FoodEntity.FIND_ALL, query = "SELECT s FROM FoodEntity s"),
		@NamedQuery(name = FoodEntity.FIND_SPECIFIC_NAME, query = "SELECT s FROM FoodEntity s WHERE s.name LIKE ?1") })
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract public class FoodEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long foodId;

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

	public static final String FIND_ALL = "Food.findAll";
	public static final String FIND_SPECIFIC_NAME = "Food.findSpecificName";

	public FoodEntity(String name, String calories, String protein, String carbs, String fat) {
		super();
		this.name = name;
		this.calories = calories;
		this.protein = protein;
		this.carbs = carbs;
		this.fat = fat;
	}

	public FoodEntity() {
	}

	// -----------Getters & Setters-------------

	@Column(name = "FOOD_ID")
	public long getFoodId() {
		return foodId;
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

}
