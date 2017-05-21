package carmo.tiago.persistence;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
@NamedQueries({ @NamedQuery(name = MealEntity.FIND_ALL, query = "SELECT s FROM MealEntity s"),
		@NamedQuery(name = MealEntity.FIND_SPECIFIC_NAME, query = "SELECT s FROM MealEntity s WHERE s.name LIKE ?1") })
public class MealEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long mealId;
	@Column
	private String name;
	@ManyToOne(cascade = CascadeType.PERSIST)
	private ProteinEntity protein;
	@ManyToOne(cascade = CascadeType.PERSIST)
	private CarbsEntity carbs;
	@ManyToOne(cascade = CascadeType.PERSIST)
	private FatEntity fat;
	@ManyToOne(cascade = CascadeType.PERSIST)
	private UserEntity user;
	@Column
	private String amountProtein;
	@Column
	private String amountCarbs;
	@Column
	private String amountFat;
	@Column
	private double totalCal;
	@Column
	private double totalProt;
	@Column
	private double totalCarb;
	@Column
	private double totalFat;

	public static final String FIND_ALL = "Meal.findAll";
	public static final String FIND_SPECIFIC_NAME = "Meal.findSpecificName";

	public MealEntity(String name, ProteinEntity protein, CarbsEntity carbs, FatEntity fat, UserEntity user) {
		super();
		this.name = name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAmountProtein() {
		return amountProtein;
	}

	public void setAmountProtein(String amountProtein) {
		this.amountProtein = amountProtein;
	}

	public String getAmountCarbs() {
		return amountCarbs;
	}

	public void setAmountCarbs(String amountCarbs) {
		this.amountCarbs = amountCarbs;
	}

	public String getAmountFat() {
		return amountFat;
	}

	public void setAmountFat(String amountFat) {
		this.amountFat = amountFat;
	}

	public double getTotalCal() {
		return totalCal;
	}

	public void setTotalCal(double totalCal) {
		this.totalCal = totalCal;
	}

	public double getTotalProt() {
		return totalProt;
	}

	public void setTotalProt(double totalProt) {
		this.totalProt = totalProt;
	}

	public double getTotalCarb() {
		return totalCarb;
	}

	public void setTotalCarb(double totalCarb) {
		this.totalCarb = totalCarb;
	}

	public double getTotalFat() {
		return totalFat;
	}

	public void setTotalFat(double totalFat) {
		this.totalFat = totalFat;
	}
	
}
