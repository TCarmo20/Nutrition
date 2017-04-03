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
		return mealEnt;
	}

	public static void addMeal(MealPOJO meal) throws Exception {
		meal.setUser(LoginApp.getInstance().getLoggedUser());
		MealFacade.addMeal(POJOToEntityMeal(meal));
	}

	public static List<MealPOJO> getUserMeals(long userId) {
		List<MealPOJO> userMeals = new ArrayList<MealPOJO>();
		Set<MealEntity> planList = UserFacade.getUserMeals(userId);
		for (MealEntity s : planList) {
			userMeals.add(entityToPOJOMeal(s));
		}
		return userMeals;
	}

	public static MealPOJO getMealByName(String name) {
		MealEntity food = MealFacade.getMealByName(name);
		return entityToPOJOMeal(food);
	}

	public static void deleteMeal(MealPOJO meal) throws Exception {
		MealFacade.deleteMeal(meal);
	}

}
