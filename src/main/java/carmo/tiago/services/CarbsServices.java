package carmo.tiago.services;

import java.util.ArrayList;
import java.util.List;
import carmo.tiago.persistence.CarbsEntity;
import carmo.tiago.persistence.FoodEntity;
import carmo.tiago.persistence.FoodFacade;

public class CarbsServices {

	public static List<CarbsPOJO> getAllCarbs() {
		List<FoodEntity> listFood = FoodFacade.getAllFood();
		List<CarbsPOJO> pojoList = new ArrayList<CarbsPOJO>();
		for (FoodEntity carbs : listFood) {
			if (carbs instanceof CarbsEntity) {
				pojoList.add(entityToPOJOCarbs((CarbsEntity) carbs));
			}
		}
		return pojoList;
	}

	private static CarbsPOJO entityToPOJOCarbs(CarbsEntity carbs) {
		CarbsPOJO carbsPOJO = new CarbsPOJO();
		carbsPOJO.setCalories(carbs.getCalories());
		carbsPOJO.setCarbs(carbs.getCarbs());
		carbsPOJO.setFat(carbs.getFat());
		carbsPOJO.setName(carbs.getName());
		carbsPOJO.setProtein(carbs.getProtein());
		return carbsPOJO;
	}
	
}
