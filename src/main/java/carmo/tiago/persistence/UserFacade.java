package carmo.tiago.persistence;

import java.util.List;
import java.util.Set;
import javax.persistence.TypedQuery;
import carmo.tiago.ui.LoginApp;

public class UserFacade {

	public static void addUser(UserEntity user) {
		LoginApp.getInstance().getEm().getTransaction().begin();
		LoginApp.getInstance().getEm().persist(user);
		LoginApp.getInstance().getEm().getTransaction().commit();
	}

	public static UserEntity getUserById(long id) {
		return LoginApp.getInstance().getEm().find(UserEntity.class, id);
	}

	public static UserEntity getUserByEmail(String email) {
		return (UserEntity) LoginApp.getInstance().getEm().createNamedQuery(UserEntity.FIND_SPECIFIC_EMAIL).setParameter(1,
				email).getSingleResult();
	}

	public static void updateUser(UserEntity uEntity) {
		LoginApp.getInstance().getEm().getTransaction().begin();
		UserEntity userOR = LoginApp.getInstance().getEm().find(UserEntity.class, uEntity.getUserId());
		userOR.setName(uEntity.getName());
		userOR.setEmail(uEntity.getEmail());
		userOR.setDob(uEntity.getDob());
		userOR.setActivityLevel(uEntity.getActivityLevel());
		userOR.setHeight(uEntity.getHeight());
		userOR.setWeight(uEntity.getWeight());
		userOR.setSex(uEntity.getSex());
		userOR.setPassword(uEntity.getPassword());
		LoginApp.getInstance().getEm().persist(userOR);
		LoginApp.getInstance().getEm().getTransaction().commit();
	}
	
	public static Set<NutPlanEntity> getUserPlans(long userId){
		UserEntity user = LoginApp.getInstance().getEm().find(UserEntity.class, userId);
		return user.getPlans();
	}
	
	public static Set<MealEntity> getUserMeals(long userId){
		UserEntity user = LoginApp.getInstance().getEm().find(UserEntity.class, userId);
		return user.getMeals();
	}

	public static List<UserEntity> getAllUsers() {
		TypedQuery<UserEntity> allUsers = LoginApp.getInstance().getEm().createNamedQuery(UserEntity.FIND_ALL,
				UserEntity.class);
		return allUsers.getResultList();
	}

	public static void deleteUser(long userId) {
		UserEntity user = LoginApp.getInstance().getEm().find(UserEntity.class, userId);
		LoginApp.getInstance().getEm().getTransaction().begin();
		LoginApp.getInstance().getEm().remove(user);
		LoginApp.getInstance().getEm().getTransaction().commit();
	}

	public static Set<DayEntity> getUserDays(long userId) {
		UserEntity user = LoginApp.getInstance().getEm().find(UserEntity.class, userId);
		return user.getDays();
	}

}
