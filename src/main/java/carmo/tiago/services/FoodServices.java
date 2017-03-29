package carmo.tiago.services;

import java.util.ArrayList;
import java.util.List;
import carmo.tiago.persistence.FoodEntity;
import carmo.tiago.persistence.FoodFacade;

public class FoodServices {
	
	public static List<FoodPOJO> getAllFood() {
		List<FoodEntity> listFood = FoodFacade.getAllFood();
		List<FoodPOJO> pojoList = new ArrayList<FoodPOJO>();
		for (FoodEntity food : listFood) {
			pojoList.add(entityToPOJOFood(food));
		}
		return pojoList;
	}

	private static FoodPOJO entityToPOJOFood(FoodEntity fat) {
		FoodPOJO foodPOJO = new FoodPOJO();
		foodPOJO.setCalories(fat.getCalories());
		foodPOJO.setCarbs(fat.getCarbs());
		foodPOJO.setFat(fat.getFat());
		foodPOJO.setName(fat.getName());
		foodPOJO.setProtein(fat.getProtein());
		return foodPOJO;
	}

	public static FoodPOJO getFoodByName(String name) {
		FoodEntity food = FoodFacade.getFoodByName(name);
		return entityToPOJOFood(food);
	}
	
}
