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

	public static void deletePlan(long planId) {
		NutPlanEntity nutPlan = LoginApp.getInstance().getEm().find(NutPlanEntity.class, planId);
		LoginApp.getInstance().getEm().getTransaction().begin();
		LoginApp.getInstance().getEm().remove(nutPlan);
		LoginApp.getInstance().getEm().getTransaction().commit();
		LoginApp.getInstance().getEm().getTransaction().begin();
		UserEntity user = nutPlan.getUser();
		user.getPlans().remove(nutPlan);
		LoginApp.getInstance().getEm().persist(user);
		LoginApp.getInstance().getEm().getTransaction().commit();
	}

	public static NutPlanEntity getPlanByName(String name) {
		return (NutPlanEntity) LoginApp.getInstance().getEm().createNamedQuery(NutPlanEntity.FIND_SPECIFIC_NAME)
		.setParameter(1, name).getSingleResult();
	}

}
