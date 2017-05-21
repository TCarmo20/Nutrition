package carmo.tiago.services;

import java.util.Date;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.scene.control.Label;

public class DayPOJO extends RecursiveTreeObject<DayPOJO>{

	private long dayId;
	private String day;
	private Double[] totals;
	private JFXListView<Label> dayMeals;
	private UserPOJO user;
	private Date creationDate;
	
	public DayPOJO(){
		
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

	public JFXListView<Label> getDayMeals() {
		return dayMeals;
	}

	public void setDayMeals(JFXListView<Label> dayMeals) {
		this.dayMeals = dayMeals;
	}

	public UserPOJO getUser() {
		return user;
	}

	public void setUser(UserPOJO user) {
		this.user = user;
	}

	public long getDayId() {
		return dayId;
	}

	public void setDayId(long dayId) {
		this.dayId = dayId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	
	
}
