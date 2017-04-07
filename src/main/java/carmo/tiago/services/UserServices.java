package carmo.tiago.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import carmo.tiago.persistence.UserEntity;
import carmo.tiago.persistence.UserFacade;
import carmo.tiago.ui.LoginApp;

public class UserServices {

	public static void addUser(String name, String email, String password, String dob, String activityLevel, String sex,
			String height, String weight, String profile) throws Exception {
		UserEntity user = new UserEntity();
		user.setName(name);
		user.setPassword(encrypt(password));
		user.setEmail(email);
		user.setActivityLevel(activityLevel);
		user.setDob(dob);
		user.setWeight(weight);
		user.setHeight(height);
		user.setSex(sex);
		user.setProfile(profile);
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

	public static UserPOJO entityToPOJO(UserEntity userEntity) {
		UserPOJO user = new UserPOJO();
		user.setName(userEntity.getName());
		user.setEmail(userEntity.getEmail());
		user.setDob(userEntity.getDob());
		user.setActivityLevel(userEntity.getActivityLevel());
		user.setHeight(userEntity.getHeight());
		user.setWeight(userEntity.getWeight());
		user.setSex(userEntity.getSex());
		user.setPassword(userEntity.getPassword());
		user.setUserId(userEntity.getUserId());
		user.setProfile(userEntity.getProfile());
		return user;
	}

	public static void updateUser(String name, String email, String password, String dob, String activityLevel,
			String sex, String height, String weight, String profile) throws Exception {
		UserEntity uEntity = getUserByEmail(LoginApp.getInstance().getLoggedUser().getEmail());
		uEntity.setName(name);
		uEntity.setEmail(email);
		uEntity.setSex(sex);
		uEntity.setActivityLevel(activityLevel);
		uEntity.setDob(dob);
		uEntity.setHeight(height);
		uEntity.setWeight(weight);
		uEntity.setPassword(password);
		uEntity.setPassword(encrypt(password));
		uEntity.setProfile(profile);
		UserFacade.updateUser(uEntity);
		LoginApp.getInstance().setLoggedUser(entityToPOJO(uEntity));
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

	public static UserPOJO getUserByEmailPOJO(String email) throws Exception {
		return entityToPOJO(UserFacade.getUserByEmail(email));
	}

	public static List<UserPOJO> getAllUsers() throws Exception {
		List<UserEntity> userList = UserFacade.getAllUsers();
		List<UserPOJO> userPOJOList = new ArrayList<UserPOJO>();
		String email = LoginApp.getInstance().getLoggedUser().getEmail();
		for (UserEntity user : userList) {
			if (!user.getEmail().equals(email)) {
				userPOJOList.add(entityToPOJO(user));
			}
		}
		return userPOJOList;
	}

	public static void deleteUser(long userId) throws Exception {
		UserFacade.deleteUser(userId);
	}

}
