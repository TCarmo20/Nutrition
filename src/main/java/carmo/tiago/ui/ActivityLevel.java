package carmo.tiago.ui;

public enum ActivityLevel {

	SEDENTARY("Sedentary"), LIGHT("Light activity"), MODERATE("Moderate activity"), VERY("Very active"), EXTREMELY(
			"Extremely Active");

	private String activityString;

	private ActivityLevel(String s) {
		activityString = s;
	}

	public String getActivityString() {
		return activityString;
	}

}
