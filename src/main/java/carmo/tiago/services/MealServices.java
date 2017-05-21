package carmo.tiago.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import carmo.tiago.persistence.MealEntity;
import carmo.tiago.persistence.MealFacade;
import carmo.tiago.persistence.UserFacade;
import carmo.tiago.ui.LoginApp;

public class MealServices {

	public static List<MealPOJO> getAllMeals() throws Exception {
		List<MealEntity> listMeals = MealFacade.getAllMeals();
		List<MealPOJO> pojoList = new ArrayList<MealPOJO>();
		for (MealEntity meal : listMeals) {
			pojoList.add(entityToPOJOMeal(meal));
		}
		return pojoList;
	}

	private static MealPOJO entityToPOJOMeal(MealEntity meal) {
		MealPOJO mealPOJO = new MealPOJO();
		mealPOJO.setAmountCarbs(meal.getAmountCarbs());
		mealPOJO.setAmountFat(meal.getAmountFat());
		mealPOJO.setAmountProtein(meal.getAmountProtein());
		mealPOJO.setName(meal.getName());
		mealPOJO.setCarbs(CarbsServices.entityToPOJOCarbs(meal.getCarbs()));
		mealPOJO.setFat(FatServices.entityToPOJOFat(meal.getFat()));
		mealPOJO.setProtein(ProteinServices.entityToPOJOProtein(meal.getProtein()));
		mealPOJO.setMealId(meal.getMealId());
		mealPOJO.setUser(UserServices.entityToPOJO(meal.getUser()));
		mealPOJO.setTotalCal(meal.getTotalCal());
		mealPOJO.setTotalProt(meal.getTotalProt());
		mealPOJO.setTotalCarb(meal.getTotalCarb());
		mealPOJO.setTotalFat(meal.getTotalFat());
		return mealPOJO;
	}

	private static MealEntity POJOToEntityMeal(MealPOJO meal) throws Exception {
		MealEntity mealEnt = new MealEntity();
		mealEnt.setAmountProtein(meal.getAmountProtein());
		mealEnt.setAmountCarbs(meal.getAmountCarbs());
		mealEnt.setAmountFat(meal.getAmountFat());
		mealEnt.setName(meal.getName());
		mealEnt.setUser(UserServices.getUserByEmail(meal.getUser().getEmail()));
		mealEnt.setCarbs(CarbsServices.POJOToentityCarbs(meal.getCarbs()));
		mealEnt.setFat(FatServices.POJOToentityFat(meal.getFat()));
		mealEnt.setProtein(ProteinServices.POJOToentityProtein(meal.getProtein()));
		mealEnt.setTotalCal(meal.getTotalCal());
		mealEnt.setTotalProt(meal.getTotalProt());
		mealEnt.setTotalCarb(meal.getTotalCarb());
		mealEnt.setTotalFat(meal.getTotalFat());
		return mealEnt;
	}

	public static void addMeal(MealPOJO meal) throws Exception {

		double proteinAmount = Double.valueOf(meal.getAmountProtein());
		double carbsamount = Double.valueOf(meal.getAmountCarbs());
		double fatamount = Double.valueOf(meal.getAmountFat());

		double proteinInProtein = Double.valueOf(meal.getProtein().getProtein());
		double carbsInProtein = Double.valueOf(meal.getProtein().getCarbs());
		double fatInProtein = Double.valueOf(meal.getProtein().getFat());
		double caloriesInprotein = Double.valueOf(meal.getProtein().getCalories());

		double protein1 = (proteinInProtein * proteinAmount) / 100;
		double carbs1 = (carbsInProtein * proteinAmount) / 100;
		double fat1 = (fatInProtein * proteinAmount) / 100;
		double calories1 = (caloriesInprotein * proteinAmount) / 100;

		double proteinInCarbs = Double.valueOf(meal.getCarbs().getProtein());
		double carbsInCarbs = Double.valueOf(meal.getCarbs().getCarbs());
		double fatInCarbs = Double.valueOf(meal.getCarbs().getFat());
		double caloriesInCarbs = Double.valueOf(meal.getCarbs().getCalories());

		double protein2 = (proteinInCarbs * carbsamount) / 100;
		double carbs2 = (carbsInCarbs * carbsamount) / 100;
		double fat2 = (fatInCarbs * carbsamount) / 100;
		double calories2 = (caloriesInCarbs * carbsamount) / 100;

		double proteinInFat = Double.valueOf(meal.getFat().getProtein());
		double carbsInFat = Double.valueOf(meal.getFat().getCarbs());
		double fatInFat = Double.valueOf(meal.getFat().getFat());
		double caloriesInFat = Double.valueOf(meal.getFat().getCalories());

		double protein3 = (proteinInFat * fatamount) / 100;
		double carbs3 = (carbsInFat * fatamount) / 100;
		double fat3 = (fatInFat * fatamount) / 100;
		double calories3 = (caloriesInFat * fatamount) / 100;

		meal.setTotalCal(calories1 + calories2 + calories3);
		meal.setTotalProt(protein1 + protein2 + protein3);
		meal.setTotalCarb(carbs1 + carbs2 + carbs3);
		meal.setTotalFat(fat1 + fat2 + fat3);
		meal.setUser(LoginApp.getInstance().getLoggedUser());
		MealFacade.addMeal(POJOToEntityMeal(meal));
	}

	public static List<MealPOJO> getUserMeals(long userId) throws Exception {
		List<MealPOJO> userMeals = new ArrayList<MealPOJO>();
		Set<MealEntity> planList = UserFacade.getUserMeals(userId);
		for (MealEntity s : planList) {
			userMeals.add(entityToPOJOMeal(s));
		}
		return userMeals;
	}

	public static MealPOJO getMealByName(String name) throws Exception {
		MealEntity food = MealFacade.getMealByName(name);
		return entityToPOJOMeal(food);
	}

	public static void deleteMeal(MealPOJO meal) throws Exception {
		MealFacade.deleteMeal(meal);
	}

}
