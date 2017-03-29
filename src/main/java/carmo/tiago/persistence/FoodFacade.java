package carmo.tiago.persistence;

import java.util.List;

import javax.persistence.TypedQuery;

import carmo.tiago.ui.LoginApp;

public class FoodFacade {

	public static void addFood(Object food) {
		LoginApp.getInstance().getEm().getTransaction().begin();
		LoginApp.getInstance().getEm().persist(food);
		LoginApp.getInstance().getEm().getTransaction().commit();
	}

	public static FoodEntity getFoodByName(String name) {
		return (FoodEntity) LoginApp.getInstance().getEm().createNamedQuery(FoodEntity.FIND_SPECIFIC_NAME)
				.setParameter(1, name).getResultList().get(0);
	}

	public static List<FoodEntity> getAllFood() {
		TypedQuery<FoodEntity> allFood = LoginApp.getInstance().getEm().createNamedQuery(FoodEntity.FIND_ALL,
				FoodEntity.class);
		return allFood.getResultList();
	}

}
