package carmo.tiago.services;

import java.util.Set;

public class UserPOJO {
	
	private long userId;
	private String name;
	private String email;
	private String password;
	private String dob;
	private String height;
	private String weight;
	private String sex;
	private String activityLevel;
	private Set<NutPlanPOJO> plans;
	
	public UserPOJO(){
	}
	
	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getDob() {
		return dob;
	}
	
	public void setDob(String dob) {
		this.dob = dob;
	}
	
	public String getHeight() {
		return height;
	}
	
	public void setHeight(String height) {
		this.height = height;
	}
	
	public String getWeight() {
		return weight;
	}
	
	public void setWeight(String weight) {
		this.weight = weight;
	}
	
	public String getSex() {
		return sex;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getActivityLevel() {
		return activityLevel;
	}
	
	public void setActivityLevel(String activityLevel) {
		this.activityLevel = activityLevel;
	}
	
	public Set<NutPlanPOJO> getPlans() {
		return plans;
	}
	
	public void setPlans(Set<NutPlanPOJO> plans) {
		this.plans = plans;
	}

}
