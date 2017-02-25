package carmo.tiago.services;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import carmo.tiago.persistence.UserEntity;
import carmo.tiago.ui.LoginApp;

public class UserServices {

	public UserServices() {
	}

	public static void addUser(String name, String email, String password, String age, String activityLevel, String sex,
			String height, String weight) throws Exception {
		UserEntity user = new UserEntity();
		user.setName(name);
		user.setPassword(password);
		user.setEmail(email);
		user.setActivityLevel(activityLevel);
		user.setAge(age);
		user.setWeight(weight);
		user.setHeight(height);
		user.setSex(sex);
		LoginApp.getInstance().getEm().getTransaction().begin();
		LoginApp.getInstance().getEm().persist(user);
		LoginApp.getInstance().getEm().getTransaction().commit();
	}

	public static UserEntity getUserById(long id) throws Exception {
		UserEntity user = LoginApp.getInstance().getEm().find(UserEntity.class, id);
		return user;
	}

	public static UserEntity getUserByEmail(String email) throws Exception {
		Query q = LoginApp.getInstance().getEm().createNamedQuery(UserEntity.FIND_SPECIFIC_EMAIL).setParameter(1,
				email);
		return (UserEntity) q.getSingleResult();
	}

	public List<UserEntity> getAllUsers() throws Exception {
		TypedQuery<UserEntity> allUsers = LoginApp.getInstance().getEm().createNamedQuery(UserEntity.FIND_ALL,
				UserEntity.class);
		return allUsers.getResultList();
	}

	public static void updateUser(UserEntity user, String name, String email, String password, String age,
			String activityLevel, String sex, String height, String weight) throws Exception {
		UserEntity userOr = getUserById(user.getUserId());
		userOr.setEmail(email);
		userOr.setName(name);
		userOr.setSex(sex);
		userOr.setActivityLevel(activityLevel);
		userOr.setAge(age);
		userOr.setHeight(height);
		userOr.setWeight(weight);
		userOr.setPassword(password);
		LoginApp.getInstance().getEm().getTransaction().begin();
		LoginApp.getInstance().getEm().persist(userOr);
		LoginApp.getInstance().getEm().getTransaction().commit();
	}

}
