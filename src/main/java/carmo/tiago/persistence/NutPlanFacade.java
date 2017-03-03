package carmo.tiago.persistence;

import carmo.tiago.ui.LoginApp;

public class NutPlanFacade {

	public static void createPlan(double calories, double protein, double carbs, double fat, String objective,
			UserEntity user) {
		NutPlanEntity nutPlan = new NutPlanEntity(calories, protein, carbs, fat, objective, user);
		user.getPlans().add(nutPlan);
		LoginApp.getInstance().getEm().getTransaction().begin();
		LoginApp.getInstance().getEm().persist(nutPlan);
		LoginApp.getInstance().getEm().getTransaction().commit();
		LoginApp.getInstance().getEm().getTransaction().begin();
		LoginApp.getInstance().getEm().persist(user);
		LoginApp.getInstance().getEm().getTransaction().commit();
	}

}
