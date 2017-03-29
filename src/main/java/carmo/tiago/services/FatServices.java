package carmo.tiago.services;

import java.util.ArrayList;
import java.util.List;
import carmo.tiago.persistence.FatEntity;
import carmo.tiago.persistence.FoodEntity;
import carmo.tiago.persistence.FoodFacade;

public class FatServices {

	public static List<FatPOJO> getAllFat() {
		List<FoodEntity> listFood = FoodFacade.getAllFood();
		List<FatPOJO> pojoList = new ArrayList<FatPOJO>();
		for (FoodEntity fat : listFood) {
			if (fat instanceof FatEntity) {
				pojoList.add(entityToPOJOFat((FatEntity) fat));
			}
		}
		return pojoList;
	}

	private static FatPOJO entityToPOJOFat(FatEntity fat) {
		FatPOJO fatPOJO = new FatPOJO();
		fatPOJO.setCalories(fat.getCalories());
		fatPOJO.setCarbs(fat.getCarbs());
		fatPOJO.setFat(fat.getFat());
		fatPOJO.setName(fat.getName());
		fatPOJO.setProtein(fat.getProtein());
		return fatPOJO;
	}

}
