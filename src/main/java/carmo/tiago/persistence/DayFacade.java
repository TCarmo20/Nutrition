package carmo.tiago.persistence;

import carmo.tiago.ui.LoginApp;

public class DayFacade {

	public static void addDay(DayEntity day) {
		LoginApp.getInstance().getEm().getTransaction().begin();
		LoginApp.getInstance().getEm().persist(day);
		LoginApp.getInstance().getEm().getTransaction().commit();
		LoginApp.getInstance().getEm().getTransaction().begin();
		UserEntity user = day.getUser();
		user.getDays().add(day);
		LoginApp.getInstance().getEm().persist(user);
		LoginApp.getInstance().getEm().getTransaction().commit();
	}

	public static void deleteDay(long dayId) {
		DayEntity day = LoginApp.getInstance().getEm().find(DayEntity.class, dayId);
		LoginApp.getInstance().getEm().getTransaction().begin();
		LoginApp.getInstance().getEm().remove(day);
		LoginApp.getInstance().getEm().getTransaction().commit();
		LoginApp.getInstance().getEm().getTransaction().begin();
		UserEntity user = day.getUser();
		user.getDays().remove(day);
		LoginApp.getInstance().getEm().persist(user);
		LoginApp.getInstance().getEm().getTransaction().commit();
	}

	public static DayEntity getDayByName(String name) {
		return (DayEntity) LoginApp.getInstance().getEm().createNamedQuery(DayEntity.FIND_SPECIFIC)
				.setParameter(1, name).getSingleResult();
	}

}
