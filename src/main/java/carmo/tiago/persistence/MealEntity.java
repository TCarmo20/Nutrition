package carmo.tiago.persistence;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "MEAL")
@NamedQueries({ @NamedQuery(name = MealEntity.FIND_ALL, query = "SELECT s FROM MealEntity s") })
public class MealEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long mealId;
	@ManyToOne(cascade = CascadeType.PERSIST)
	private ProteinEntity protein;
	@ManyToOne(cascade = CascadeType.PERSIST)
	private CarbsEntity carbs;
	@ManyToOne(cascade = CascadeType.PERSIST)
	private FatEntity fat;
	@ManyToOne(cascade = CascadeType.PERSIST)
	private UserEntity user;

	public static final String FIND_ALL = "Meal.findAll";

	public MealEntity(ProteinEntity protein, CarbsEntity carbs, FatEntity fat, UserEntity user) {
		super();
		this.protein = protein;
		this.carbs = carbs;
		this.fat = fat;
		this.user = user;
	}

	public MealEntity() {

	}

	@JoinColumn(name = "MEAL_ID")
	public long getMealId() {
		return mealId;
	}

	public ProteinEntity getProtein() {
		return protein;
	}

	public void setProtein(ProteinEntity protein) {
		this.protein = protein;
	}

	public CarbsEntity getCarbs() {
		return carbs;
	}

	public void setCarbs(CarbsEntity carbs) {
		this.carbs = carbs;
	}

	public FatEntity getFat() {
		return fat;
	}

	public void setFat(FatEntity fat) {
		this.fat = fat;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

}
