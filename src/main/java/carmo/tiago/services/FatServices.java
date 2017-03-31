package carmo.tiago.services;

import java.util.ArrayList;
import java.util.List;
import carmo.tiago.persistence.FatEntity;
import carmo.tiago.persistence.FatFacade;

public class FatServices {

	public static List<FatPOJO> getAllFat() {
		List<FatEntity> listFood = FatFacade.getAllFat();
		List<FatPOJO> pojoList = new ArrayList<FatPOJO>();
		for (FatEntity fat : listFood) {
			pojoList.add(entityToPOJOFat(fat));
		}
		return pojoList;
	}

	public static FatPOJO entityToPOJOFat(FatEntity fat) {
		FatPOJO fatPOJO = new FatPOJO();
		fatPOJO.setCalories(fat.getCalories());
		fatPOJO.setCarbs(fat.getCarbs());
		fatPOJO.setFat(fat.getFat());
		fatPOJO.setName(fat.getName());
		fatPOJO.setProtein(fat.getProtein());
		return fatPOJO;
	}

	public static FatEntity POJOToentityFat(FatPOJO fat) {
		FatEntity food = FatFacade.getFatByName(fat.getName());
		return food;
	}

	public static FatPOJO getFatByName(String name) {
		FatEntity food = FatFacade.getFatByName(name);
		return entityToPOJOFat(food);
	}

}
