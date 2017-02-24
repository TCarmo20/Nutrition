package carmo.tiago.services;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import carmo.tiago.persistence.UserEntity;
import carmo.tiago.ui.LoginApp;

public class UserServices {

	public UserServices() {
	}

	public static void addUser(String name, String email, String password) throws Exception {
		UserEntity user = new UserEntity();
		user.setName(name);
		user.setPassword(password);
		user.setEmail(email);
		LoginApp.getEm().getTransaction().begin();
		LoginApp.getEm().persist(user);
		LoginApp.getEm().getTransaction().commit();
	}

	public static UserEntity getUserId(long id) throws Exception {
		UserEntity user = LoginApp.getEm().find(UserEntity.class, id);
		return user;
	}

	public static UserEntity getUser(String email) throws Exception {
		Query q = LoginApp.getEm().createNamedQuery(UserEntity.FIND_SPECIFIC_EMAIL).setParameter(1, email);
		return (UserEntity) q.getSingleResult();
	}

	public List<UserEntity> getAllUsers() throws Exception {
		TypedQuery<UserEntity> allUsers = LoginApp.getEm().createNamedQuery(UserEntity.FIND_ALL, UserEntity.class);
		return allUsers.getResultList();
	}

	public static void updateUser(UserEntity loggedUser, String email, String name) throws Exception {
		UserEntity userOr = getUserId(loggedUser.getUserId());
		userOr.setEmail(email);
		userOr.setName(name);
		LoginApp.getEm().getTransaction().begin();
		LoginApp.getEm().persist(userOr);
		LoginApp.getEm().getTransaction().commit();
	}

}
