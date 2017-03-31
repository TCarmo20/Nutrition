package carmo.tiago.persistence;

import java.util.List;
import javax.persistence.TypedQuery;
import carmo.tiago.ui.LoginApp;

public class MealFacade {

	public static List<MealEntity> getAllMeals() {
		TypedQuery<MealEntity> allMeal = LoginApp.getInstance().getEm().createNamedQuery(MealEntity.FIND_ALL,
				MealEntity.class);
		return allMeal.getResultList();
	}

	public static void addMeal(MealEntity meal) {
		LoginApp.getInstance().getEm().getTransaction().begin();
		LoginApp.getInstance().getEm().persist(meal);
		LoginApp.getInstance().getEm().getTransaction().commit();
		LoginApp.getInstance().getEm().getTransaction().begin();
		UserEntity user = meal.getUser();
		user.getMeals().add(meal);
		LoginApp.getInstance().getEm().persist(user);
		LoginApp.getInstance().getEm().getTransaction().commit();
		LoginApp.getInstance().getEm().getTransaction().begin();
		ProteinEntity protein = meal.getProtein();
		protein.getMeals().add(meal);
		LoginApp.getInstance().getEm().persist(protein);
		LoginApp.getInstance().getEm().getTransaction().commit();
		LoginApp.getInstance().getEm().getTransaction().begin();
		CarbsEntity carbs = meal.getCarbs();
		carbs.getMeals().add(meal);
		LoginApp.getInstance().getEm().persist(carbs);
		LoginApp.getInstance().getEm().getTransaction().commit();
		LoginApp.getInstance().getEm().getTransaction().begin();
		FatEntity fat = meal.getFat();
		fat.getMeals().add(meal);
		LoginApp.getInstance().getEm().persist(fat);
		LoginApp.getInstance().getEm().getTransaction().commit();
	}

	public static MealEntity getMealByName(String name) {
		return (MealEntity) LoginApp.getInstance().getEm().createNamedQuery(MealEntity.FIND_SPECIFIC_NAME)
				.setParameter(1, name).getSingleResult();
	}

}
