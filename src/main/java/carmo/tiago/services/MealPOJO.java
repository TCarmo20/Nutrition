package carmo.tiago.services;

public class MealPOJO {

	private ProteinPOJO protein;
	private CarbsPOJO carbs;
	private FatPOJO fat;
	private UserPOJO user;

	public MealPOJO() {

	}

	public ProteinPOJO getProtein() {
		return protein;
	}

	public void setProtein(ProteinPOJO protein) {
		this.protein = protein;
	}

	public CarbsPOJO getCarbs() {
		return carbs;
	}

	public void setCarbs(CarbsPOJO carbs) {
		this.carbs = carbs;
	}

	public FatPOJO getFat() {
		return fat;
	}

	public void setFat(FatPOJO fat) {
		this.fat = fat;
	}

	public UserPOJO getUser() {
		return user;
	}

	public void setUser(UserPOJO user) {
		this.user = user;
	}

}
