package carmo.tiago.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.joda.time.LocalDate;
import org.joda.time.Years;

import carmo.tiago.persistence.NutPlanEntity;
import carmo.tiago.persistence.NutPlanFacade;
import carmo.tiago.persistence.UserEntity;
import carmo.tiago.persistence.UserFacade;
import carmo.tiago.ui.LoginApp;

public class UserServices {

	public static void addUser(String name, String email, String password, String dob, String activityLevel, String sex,
			String height, String weight) throws Exception {
		UserEntity user = new UserEntity();
		user.setName(name);
		user.setPassword(encrypt(password));
		user.setEmail(email);
		user.setActivityLevel(activityLevel);
		user.setDob(dob);
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
		user.setDob(userEntity.getDob());
		user.setActivityLevel(userEntity.getActivityLevel());
		user.setHeight(userEntity.getHeight());
		user.setWeight(userEntity.getWeight());
		user.setSex(userEntity.getSex());
		user.setPassword(userEntity.getPassword());
		user.setUserId(userEntity.getUserId());
		return user;
	}

	public static void updateUser(String name, String email, String password, String dob, String activityLevel,
			String sex, String height, String weight) throws Exception {
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

	public static UserPOJO getUserByEmailPOJO(String email) {
		return entityToPOJO(UserFacade.getUserByEmail(email));
	}

	public static void createPlan(String name, String objective) throws NumberFormatException, Exception {
		UserPOJO loggedUser = LoginApp.getInstance().getLoggedUser();
		double FA = 0;
		double protein = 0;
		double fat = 0;
		double carbs = 0;
		Random random = new Random();
		LocalDate myDate = new LocalDate(loggedUser.getDob());
		LocalDate now = new LocalDate();
		Years age = Years.yearsBetween(myDate, now);
		String ageString = String.valueOf(age.getYears());
		if (loggedUser.getSex().equals("Male")) {
			double BMR = (10 * Double.parseDouble(loggedUser.getWeight())
					+ (6.25 * Integer.parseInt(loggedUser.getHeight()) - (5 * Integer.parseInt(ageString) + 5)));
			switch (loggedUser.getActivityLevel()) {
			case "Sedentary":
				FA = BMR * 1.2;
				break;
			case "Light activity":
				FA = BMR * 1.3 + (1.4 - 1.3) * random.nextDouble();
				break;
			case "Moderate activity":
				FA = BMR * 1.5 + (1.6 - 1.5) * random.nextDouble();
				break;
			case "Very active":
				FA = BMR * 1.6 + (1.7 - 1.6) * random.nextDouble();
				break;
			case "Extremely Active":
				FA = BMR * 1.9 + (2.0 - 1.9) * random.nextDouble();
				break;
			default:
				throw new Exception();
			}
			if (objective.equals("Hypertrophy")) {
				protein = Double.parseDouble(loggedUser.getWeight()) * 2 + (2.5 - 2) * random.nextDouble();
				FA = FA + 500;
			} else if (objective.equals("Maintenance")) {
				protein = Double.parseDouble(loggedUser.getWeight()) * 1.2;
			} else if (objective.equals("Fat Loss")) {
				FA = FA - 500;
				protein = Double.parseDouble(loggedUser.getWeight()) * 1.4 + (1.6 - 1.4) * random.nextDouble();
			}
			fat = (FA * 0.3) / 9;
			carbs = (FA - (protein * 4 + fat * 9)) / 4;
			NutPlanEntity plan = new NutPlanEntity();
			plan.setCalories(Math.round(FA));
			plan.setName(name);
			plan.setCarbs(Math.round(carbs));
			plan.setProtein(Math.round(protein));
			plan.setFat(Math.round(fat));
			plan.setObjective(objective);
			plan.setUser(getUserByEmail(loggedUser.getEmail()));
			NutPlanFacade.createPlan(plan);
		} else {
			double BMR = (10 * Double.parseDouble(loggedUser.getWeight())
					+ (6.25 * Integer.parseInt(loggedUser.getHeight()) - (5 * Integer.parseInt(ageString) - 161)));
			switch (loggedUser.getActivityLevel()) {
			case "Sedentary":
				FA = BMR * 1.2;
				break;
			case "Light activity":
				FA = BMR * 1.3 + (1.4 - 1.3) * random.nextDouble();
				break;
			case "Moderate activity":
				FA = BMR * 1.5 + (1.6 - 1.5) * random.nextDouble();
				break;
			case "Very active":
				FA = BMR * 1.6 + (1.7 - 1.6) * random.nextDouble();
				break;
			case "Extremely Active":
				FA = BMR * 1.9 + (2.0 - 1.9) * random.nextDouble();
				break;
			default:
				throw new Exception();
			}
			if (objective.equals("Hypertrophy")) {
				protein = Double.parseDouble(loggedUser.getWeight()) * 1.8 + (2 - 1.8) * random.nextDouble();
				FA = FA + 250;
			} else if (objective.equals("Maintenance")) {
				protein = Double.parseDouble(loggedUser.getWeight());
			} else if (objective.equals("Fat Loss")) {
				protein = Double.parseDouble(loggedUser.getWeight()) * 1.2 + (1.4 - 1.2) * random.nextDouble();
				FA = FA - 500;
			}
			fat = (FA * 0.3) / 9;
			carbs = (FA - (protein * 4 + fat * 9)) / 4;
			NutPlanEntity plan = new NutPlanEntity();
			plan.setCalories(Math.round(FA));
			plan.setName(name);
			plan.setCarbs(Math.round(carbs));
			plan.setProtein(Math.round(protein));
			plan.setFat(Math.round(fat));
			plan.setObjective(objective);
			plan.setUser(getUserByEmail(loggedUser.getEmail()));
			NutPlanFacade.createPlan(plan);
		}
	}

	private static NutPlanPOJO entityToPOJOPlan(NutPlanEntity plan) {
		NutPlanPOJO planPOJO = new NutPlanPOJO();
		planPOJO.setCalories(plan.getCalories());
		planPOJO.setCarbs(plan.getCarbs());
		planPOJO.setFat(plan.getFat());
		planPOJO.setObjective(plan.getObjective());
		planPOJO.setProtein(plan.getProtein());
		planPOJO.setName(plan.getName());
		planPOJO.setPlanId(plan.getPlanId());
		return planPOJO;
	}

	public static List<NutPlanPOJO> getUserPlans(long userId) throws Exception {
		List<NutPlanPOJO> userPlans = new ArrayList<NutPlanPOJO>();
		Set<NutPlanEntity> planList = UserFacade.getUserPlans(userId);
		for (NutPlanEntity s : planList) {
			userPlans.add(entityToPOJOPlan(s));
		}
		return userPlans;
	}

}
