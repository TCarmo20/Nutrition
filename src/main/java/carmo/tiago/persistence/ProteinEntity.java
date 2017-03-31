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
@Table(name = "PROTEIN")
@NamedQueries({ @NamedQuery(name = ProteinEntity.FIND_ALL, query = "SELECT s FROM ProteinEntity s"),
		@NamedQuery(name = ProteinEntity.FIND_SPECIFIC_NAME, query = "SELECT s FROM ProteinEntity s WHERE s.name LIKE ?1") })
public class ProteinEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long proteinId;
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
	@OneToMany(mappedBy = "protein")
	@JoinColumn(name = "proteinId")
	private Set<MealEntity> meals;

	public static final String FIND_ALL = "Protein.findAll";
	public static final String FIND_SPECIFIC_NAME = "Protein.findSpecificName";

	public ProteinEntity(String name, String calories, String protein, String carbs, String fat, Set<MealEntity> meals) {
		super();
		this.name = name;
		this.calories = calories;
		this.protein = protein;
		this.carbs = carbs;
		this.fat = fat;
		this.meals = meals;
	}

	public ProteinEntity() {
	}

	// -----------Getters & Setters-------------

	@JoinColumn(name = "PROTEIN_ID")
	public long getProteinId() {
		return proteinId;
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
