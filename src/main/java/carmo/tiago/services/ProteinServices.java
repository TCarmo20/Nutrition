package carmo.tiago.services;

import java.util.ArrayList;
import java.util.List;
import carmo.tiago.persistence.ProteinEntity;
import carmo.tiago.persistence.ProteinFacade;

public class ProteinServices {
	
	public static List<ProteinPOJO> getAllProtein(){
		List<ProteinEntity> list = ProteinFacade.getAllProtein();
		List<ProteinPOJO> pojoList = new ArrayList<ProteinPOJO>();
		for(ProteinEntity protein : list){
			pojoList.add(entityToPOJOProtein(protein));
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
	
	public static ProteinPOJO getProteinByName(String name){
		ProteinEntity protein = ProteinFacade.getProteinByName(name);
		return entityToPOJOProtein(protein);
	}

}
