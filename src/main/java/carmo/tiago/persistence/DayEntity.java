package carmo.tiago.persistence;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "DAY")
public class DayEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long dayId;

	@ManyToOne
	private NutPlanEntity nutPlan;

	@ManyToMany
	private Set<MealEntity> meals;
	
	@ManyToOne
	private UserEntity user;

	public DayEntity(NutPlanEntity nutPlan, Set<MealEntity> meals) {
		super();
		this.nutPlan = nutPlan;
		this.meals = meals;
	}

	public DayEntity() {

	}

	@Column(name = "DAY_ID")
	public long getDayId() {
		return dayId;
	}

	public void setDayId(long dayId) {
		this.dayId = dayId;
	}

	public NutPlanEntity getNutPlan() {
		return nutPlan;
	}

	public void setNutPlan(NutPlanEntity nutPlan) {
		this.nutPlan = nutPlan;
	}

	public Set<MealEntity> getMeals() {
		return meals;
	}

	public void setMeals(Set<MealEntity> meals) {
		this.meals = meals;
	}
	
	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

}
