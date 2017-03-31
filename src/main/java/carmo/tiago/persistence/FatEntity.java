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
@Table(name = "FAT")
@NamedQueries({ @NamedQuery(name = FatEntity.FIND_ALL, query = "SELECT s FROM FatEntity s"),
		@NamedQuery(name = FatEntity.FIND_SPECIFIC_NAME, query = "SELECT s FROM FatEntity s WHERE s.name LIKE ?1") })
public class FatEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long fatId;
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
	@OneToMany(mappedBy = "fat")
	@JoinColumn(name = "fatId")
	private Set<MealEntity> meals;

	public static final String FIND_ALL = "Fat.findAll";
	public static final String FIND_SPECIFIC_NAME = "Fat.findSpecificName";

	public FatEntity(String name, String calories, String protein, String carbs, String fat, Set<MealEntity> meals) {
		super();
		this.name = name;
		this.calories = calories;
		this.protein = protein;
		this.carbs = carbs;
		this.fat = fat;
		this.meals = meals;
	}

	public FatEntity() {
	}

	// -----------Getters & Setters-------------

	@JoinColumn(name = "FAT_ID")
	public long getFatId() {
		return fatId;
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
