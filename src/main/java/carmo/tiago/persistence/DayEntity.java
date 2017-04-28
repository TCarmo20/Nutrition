package carmo.tiago.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "DAY")
@NamedQueries({ @NamedQuery(name = DayEntity.FIND_ALL, query = "SELECT s FROM DayEntity s"),
		@NamedQuery(name = DayEntity.FIND_SPECIFIC, query = "SELECT s FROM DayEntity s WHERE s.day LIKE ?1") })
public class DayEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long dayId;
	@ManyToOne
	private UserEntity user;
	@Column(nullable = false)
	private String day;
	@Column
	private Double[] totals;
	@Column
	private List<String> dayMeals;
	@Column(nullable = false)
	private Date creationDate;

	public static final String FIND_ALL = "Day.findAll";
	public static final String FIND_SPECIFIC = "Day.findSpecific";

	public DayEntity(String day, UserEntity user, Double[] totals, List<String> dayMeals, Date date) {
		super();
		this.day = day;
		this.user = user;
		this.totals = totals;
		this.dayMeals = dayMeals;
		this.creationDate = date;
	}

	public DayEntity() {

	}

	// -----------Getters & Setters-------------

	@Column(name = "DAY_ID")
	public long getDayId() {
		return dayId;
	}

	public void setDayId(long dayId) {
		this.dayId = dayId;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public Double[] getTotals() {
		return totals;
	}

	public void setTotals(Double[] totals) {
		this.totals = totals;
	}

	public List<String> getDayMeals() {
		return dayMeals;
	}

	public void setDayMeals(List<String> dayMeals) {
		this.dayMeals = dayMeals;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}
