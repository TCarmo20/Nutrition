package carmo.tiago.services;

public class MealPOJO {

	private ProteinPOJO protein;
	private CarbsPOJO carbs;
	private FatPOJO fat;
	private UserPOJO user;
	private String name;
	private String amountProtein;
	private String amountCarbs;
	private String amountFat;
	private long mealId;
	private double totalCal;
	private double totalProt;
	private double totalCarb;
	private double totalFat;

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

	public long getMealId() {
		return mealId;
	}

	public void setMealId(long mealId) {
		this.mealId = mealId;
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
