package carmo.tiago.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CARBS")
public class CarbsEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long carbsId;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String calories;

	@Column(nullable = false)
	private String protein;

	@Column(nullable = false)
	private String carbs;

	@Column(nullable = false)
	private String fat;

	public CarbsEntity(String name, String calories, String protein, String carbs, String fat) {
		super();
		this.name = name;
		this.calories = calories;
		this.protein = protein;
		this.carbs = carbs;
		this.fat = fat;
	}

	public CarbsEntity() {
	}

	// -----------Getters & Setters-------------

	@Column(name = "CARBS_ID")
	public long getCarbsId() {
		return carbsId;
	}

	public void setCarbsId(long carbsId) {
		this.carbsId = carbsId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCalories() {
		return calories;
	}

	public void setCalories(String calories) {
		this.calories = calories;
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
