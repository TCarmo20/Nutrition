package carmo.tiago.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.joda.time.LocalDate;
import org.joda.time.Years;

import carmo.tiago.persistence.MealEntity;
import carmo.tiago.persistence.MealFacade;
import carmo.tiago.persistence.NutPlanEntity;
import carmo.tiago.persistence.NutPlanFacade;
import carmo.tiago.persistence.UserFacade;
import carmo.tiago.ui.LoginApp;

public class NutPlanServices {
	
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
			plan.setName(name + " (" + new Date() + ")");
			plan.setCarbs(Math.round(carbs));
			plan.setProtein(Math.round(protein));
			plan.setFat(Math.round(fat));
			plan.setObjective(objective);
			plan.setUser(UserServices.getUserByEmail(loggedUser.getEmail()));
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
			plan.setUser(UserServices.getUserByEmail(loggedUser.getEmail()));
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

	public static void deletePlan(NutPlanPOJO rowData) throws Exception {
		NutPlanFacade.deletePlan(rowData);
	}

	public static NutPlanPOJO getPlanByName(String name) {
		NutPlanEntity plan = NutPlanFacade.getPlanByName(name);
		return entityToPOJOPlan(plan);
	}

}
