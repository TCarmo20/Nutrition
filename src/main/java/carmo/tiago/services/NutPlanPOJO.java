package carmo.tiago.services;

public class NutPlanPOJO {
	
	private long planId;
	private double protein;
	private double carbs;
	private double fat;
	private String objective;
	private UserPOJO user;
	
	public NutPlanPOJO(){
	}
	
	public long getPlanId() {
		return planId;
	}
	public void setPlanId(long planId) {
		this.planId = planId;
	}
	public double getProtein() {
		return protein;
	}
	public void setProtein(double protein) {
		this.protein = protein;
	}
	public double getCarbs() {
		return carbs;
	}
	public void setCarbs(double carbs) {
		this.carbs = carbs;
	}
	public double getFat() {
		return fat;
	}
	public void setFat(double fat) {
		this.fat = fat;
	}
	public String getObjective() {
		return objective;
	}
	public void setObjective(String objective) {
		this.objective = objective;
	}
	public UserPOJO getUser() {
		return user;
	}
	public void setUser(UserPOJO user) {
		this.user = user;
	}

}
