package carmo.tiago.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import carmo.tiago.persistence.UserEntity;
import carmo.tiago.persistence.UserFacade;
import carmo.tiago.ui.LoginApp;

public class UserServices {

	public UserServices() {
	}

	public static void addUser(String name, String email, String password, String age, String activityLevel, String sex,
			String height, String weight) throws Exception {
		UserEntity user = new UserEntity();
		user.setName(name);
		user.setPassword(encrypt(password));
		user.setEmail(email);
		user.setActivityLevel(activityLevel);
		user.setAge(age);
		user.setWeight(weight);
		user.setHeight(height);
		user.setSex(sex);
		UserFacade.addUser(user);
	}

	public static UserPOJO getUserByIdPOJO(long id) throws Exception {
		return entityToPOJO(UserFacade.getUserById(id));
	}

	public static UserEntity getUserById(long id) throws Exception {
		return UserFacade.getUserById(id);
	}

	public static UserEntity getUserByEmail(String email) throws Exception {
		return UserFacade.getUserByEmail(email);
	}

	private static UserPOJO entityToPOJO(UserEntity userEntity) {
		UserPOJO user = new UserPOJO();
		user.setName(userEntity.getName());
		user.setEmail(userEntity.getEmail());
		user.setAge(userEntity.getAge());
		user.setActivityLevel(userEntity.getActivityLevel());
		user.setHeight(userEntity.getHeight());
		user.setWeight(userEntity.getWeight());
		user.setSex(userEntity.getSex());
		user.setPassword(userEntity.getPassword());
		return user;
	}

	public static void updateUser(String name, String email, String password, String age, String activityLevel,
			String sex, String height, String weight) throws Exception {
		UserEntity uEntity = getUserById(LoginApp.getInstance().getLoggedUser().getUserId());
		uEntity.setName(name);
		uEntity.setEmail(email);
		uEntity.setSex(sex);
		uEntity.setActivityLevel(activityLevel);
		uEntity.setAge(age);
		uEntity.setHeight(height);
		uEntity.setWeight(weight);
		uEntity.setPassword(password);
		uEntity.setPassword(encrypt(password));
		UserFacade.updateUser(uEntity);
	}

	public static String encrypt(String password) throws NoSuchAlgorithmException {
		MessageDigest digester = MessageDigest.getInstance("MD5");
		digester.update(password.getBytes());
		byte[] hash = digester.digest();
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < hash.length; i++) {
			if ((0xff & hash[i]) < 0x10) {
				hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
			} else {
				hexString.append(Integer.toHexString(0xFF & hash[i]));
			}
		}
		return hexString.toString();
	}

	public static UserPOJO getUserByEmailPOJO(String email) {
		return entityToPOJO(UserFacade.getUserByEmail(email));
	}

}
