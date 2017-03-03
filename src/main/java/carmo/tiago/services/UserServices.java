package carmo.tiago.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import carmo.tiago.persistence.NutPlanFacade;
import carmo.tiago.persistence.UserEntity;
import carmo.tiago.persistence.UserFacade;
import carmo.tiago.ui.LoginApp;

public class UserServices {
	
	private static UserServices instance;

	public UserServices() {
		instance = this;
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

	public void createPlan(String objective) throws NumberFormatException, Exception {
		UserPOJO loggedUser = LoginApp.getInstance().getLoggedUser();
		double FA = 0;
		Random random = new Random();
		if (loggedUser.getSex().equals("Male")) {
			double BMR = (10 * Double.parseDouble(loggedUser.getWeight())
					+ (6.25 * Integer.parseInt(loggedUser.getHeight())
							- (5 * Integer.parseInt(loggedUser.getAge()) + 5)));
			switch (loggedUser.getActivityLevel()) {
			case "Sedentário":
				FA = BMR * 1.2;
				break;
			case "Atividade Leve":
				FA = BMR * 1.3 + (1.4 - 1.3) * random.nextDouble();
				break;
			case "Atividade Moderada":
				FA = BMR * 1.5 + (1.6 - 1.5) * random.nextDouble();
				break;
			case "Muito Ativo":
				FA = BMR * 1.6 + (1.7 - 1.6) * random.nextDouble();
				break;
			case "Extremamente Ativo":
				FA = BMR * 1.9 + (2.0 - 1.9) * random.nextDouble();
				break;
			default:
				throw new Exception();
			}
			NutPlanFacade.createPlan(FA,
					Double.parseDouble(loggedUser.getWeight()) * 2 + (2.5 - 2) * random.nextDouble(), FA,
					(FA * 0.3) / 9, objective, getUserByEmail(loggedUser.getEmail()));
		} else {
			double BMR = (10 * Double.parseDouble(loggedUser.getWeight())
					+ (6.25 * Integer.parseInt(loggedUser.getHeight())
							- (5 * Integer.parseInt(loggedUser.getAge()) - 161)));
			switch (loggedUser.getActivityLevel()) {
			case "Sedentário":
				FA = BMR * 1.2;
				break;
			case "Atividade Leve":
				FA = BMR * 1.3 + (1.4 - 1.3) * random.nextDouble();
				break;
			case "Atividade Moderada":
				FA = BMR * 1.5 + (1.6 - 1.5) * random.nextDouble();
				break;
			case "Muito Ativo":
				FA = BMR * 1.6 + (1.7 - 1.6) * random.nextDouble();
				break;
			case "Extremamente Ativo":
				FA = BMR * 1.9 + (2.0 - 1.9) * random.nextDouble();
				break;
			default:
				throw new Exception();
			}

			NutPlanFacade.createPlan(FA,
					Double.parseDouble(loggedUser.getWeight()) * 2 + (2.5 - 2) * random.nextDouble(), FA,
					(FA * 0.3) / 9, objective, getUserByEmail(loggedUser.getEmail()));
		}
	}

	public static UserServices getInstance() {
		return instance;
	}

}
