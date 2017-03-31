package carmo.tiago.services;

import java.util.ArrayList;
import java.util.List;
import carmo.tiago.persistence.CarbsEntity;
import carmo.tiago.persistence.CarbsFacade;

public class CarbsServices {

	public static List<CarbsPOJO> getAllCarbs() {
		List<CarbsEntity> listFood = CarbsFacade.getAllCarbs();
		List<CarbsPOJO> pojoList = new ArrayList<CarbsPOJO>();
		for (CarbsEntity carbs : listFood) {
			pojoList.add(entityToPOJOCarbs((CarbsEntity) carbs));
		}
		return pojoList;
	}

	public static CarbsPOJO entityToPOJOCarbs(CarbsEntity carbs) {
		CarbsPOJO carbsPOJO = new CarbsPOJO();
		carbsPOJO.setCalories(carbs.getCalories());
		carbsPOJO.setCarbs(carbs.getCarbs());
		carbsPOJO.setFat(carbs.getFat());
		carbsPOJO.setName(carbs.getName());
		carbsPOJO.setProtein(carbs.getProtein());
		return carbsPOJO;
	}

	public static CarbsEntity POJOToentityCarbs(CarbsPOJO carbs) {
		CarbsEntity food = CarbsFacade.getCarbsByName(carbs.getName());
		return food;
	}

	public static CarbsPOJO getCarbsByName(String name) {
		CarbsEntity food = CarbsFacade.getCarbsByName(name);
		return entityToPOJOCarbs(food);
	}

}
