package carmo.tiago.services;

import java.util.ArrayList;
import java.util.List;
import carmo.tiago.persistence.ProteinEntity;
import carmo.tiago.persistence.ProteinFacade;

public class ProteinServices {

	public static List<ProteinPOJO> getAllProtein() throws Exception {
		List<ProteinEntity> listFood = ProteinFacade.getAllProtein();
		List<ProteinPOJO> pojoList = new ArrayList<ProteinPOJO>();
		for (ProteinEntity protein : listFood) {
			pojoList.add(entityToPOJOProtein((ProteinEntity) protein));
		}
		return pojoList;
	}

	public static ProteinPOJO entityToPOJOProtein(ProteinEntity protein) {
		ProteinPOJO proteinPOJO = new ProteinPOJO();
		proteinPOJO.setCalories(protein.getCalories());
		proteinPOJO.setCarbs(protein.getCarbs());
		proteinPOJO.setFat(protein.getFat());
		proteinPOJO.setName(protein.getName());
		proteinPOJO.setProtein(protein.getProtein());
		return proteinPOJO;
	}

	public static ProteinEntity POJOToentityProtein(ProteinPOJO protein) {
		ProteinEntity food = ProteinFacade.getProteinByName(protein.getName());
		return food;
	}

	public static ProteinPOJO getProteinByName(String name) throws Exception {
		ProteinEntity food = ProteinFacade.getProteinByName(name);
		return entityToPOJOProtein(food);
	}

}
