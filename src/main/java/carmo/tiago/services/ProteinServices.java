package carmo.tiago.services;

import java.util.ArrayList;
import java.util.List;
import carmo.tiago.persistence.FoodEntity;
import carmo.tiago.persistence.FoodFacade;
import carmo.tiago.persistence.ProteinEntity;

public class ProteinServices {

	public static List<ProteinPOJO> getAllProtein() {
		List<FoodEntity> listFood = FoodFacade.getAllFood();
		List<ProteinPOJO> pojoList = new ArrayList<ProteinPOJO>();
		for (FoodEntity protein : listFood) {
			if (protein instanceof ProteinEntity) {
				pojoList.add(entityToPOJOProtein((ProteinEntity) protein));
			}
		}
		return pojoList;
	}

	private static ProteinPOJO entityToPOJOProtein(ProteinEntity protein) {
		ProteinPOJO proteinPOJO = new ProteinPOJO();
		proteinPOJO.setCalories(protein.getCalories());
		proteinPOJO.setCarbs(protein.getCarbs());
		proteinPOJO.setFat(protein.getFat());
		proteinPOJO.setName(protein.getName());
		proteinPOJO.setProtein(protein.getProtein());
		return proteinPOJO;
	}

}
