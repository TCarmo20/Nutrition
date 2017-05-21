package carmo.tiago.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.jfoenix.controls.JFXListView;
import carmo.tiago.persistence.DayEntity;
import carmo.tiago.persistence.DayFacade;
import carmo.tiago.persistence.UserFacade;
import javafx.scene.control.Label;

public class DayServices {

	public static void addDay(UserPOJO user, JFXListView<Label> dayMeals, Double[] totals) throws Exception {
		DayEntity day = new DayEntity();
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		List<String> dayMealsFinal = new ArrayList<String>();
		for (Label meal : dayMeals.getItems()) {
			dayMealsFinal.add(meal.getText());
		}
		day.setDay(new SimpleDateFormat("E").format(dayOfWeek) + " (" + new Date() + ")");
		day.setDayMeals(dayMealsFinal);
		day.setTotals(totals);
		day.setUser(UserServices.getUserByEmail(user.getEmail()));
		day.setCreationDate(new Date());
		DayFacade.addDay(day);
	}

	public static List<DayPOJO> getUserDays(long userId) throws Exception {
		List<DayPOJO> userDays = new ArrayList<DayPOJO>();
		Set<DayEntity> dayList = UserFacade.getUserDays(userId);
		for (DayEntity s : dayList) {
			userDays.add(entityToPOJODay(s));
		}
		return userDays;
	}

	private static DayPOJO entityToPOJODay(DayEntity s) {
		DayPOJO day = new DayPOJO();
		day.setCreationDate(s.getCreationDate());
		day.setDay(s.getDay());
		day.setTotals(s.getTotals());
		day.setDayId(s.getDayId());
		day.setDayMeals(listToJFX(s.getDayMeals()));
		return day;
	}

	private static JFXListView<Label> listToJFX(List<String> dayMeals) {
		JFXListView<Label> list = new JFXListView<Label>();
		for (String meal : dayMeals) {
			Label lbl = new Label(meal);
			list.getItems().add(lbl);
		}
		return list;
	}

	public static void deleteDay(DayPOJO rowData) throws Exception {
		DayFacade.deleteDay(rowData.getDayId());
	}

	public static DayPOJO getDayByName(String name) {
		DayEntity day = DayFacade.getDayByName(name);
		return entityToPOJODay(day);
	}

}
