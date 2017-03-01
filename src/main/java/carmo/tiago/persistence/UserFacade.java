package carmo.tiago.persistence;

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
		UserEntity userOR = LoginApp.getInstance().getEm().find(UserEntity.class, uEntity.getUserId());
		userOR.setName(uEntity.getName());
		userOR.setEmail(uEntity.getEmail());
		userOR.setAge(uEntity.getAge());
		userOR.setActivityLevel(uEntity.getActivityLevel());
		userOR.setHeight(uEntity.getHeight());
		userOR.setWeight(uEntity.getWeight());
		userOR.setSex(uEntity.getSex());
		userOR.setPassword(uEntity.getPassword());
	}

}
