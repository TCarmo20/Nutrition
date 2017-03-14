package carmo.tiago.persistence;

import carmo.tiago.ui.LoginApp;

public class NutPlanFacade {

	public static void createPlan(NutPlanEntity nutPlan) {
		LoginApp.getInstance().getEm().getTransaction().begin();
		LoginApp.getInstance().getEm().persist(nutPlan);
		LoginApp.getInstance().getEm().getTransaction().commit();
		LoginApp.getInstance().getEm().getTransaction().begin();
		UserEntity user = nutPlan.getUser();
		user.getPlans().add(nutPlan);
		LoginApp.getInstance().getEm().persist(user);
		LoginApp.getInstance().getEm().getTransaction().commit();
	}

}
