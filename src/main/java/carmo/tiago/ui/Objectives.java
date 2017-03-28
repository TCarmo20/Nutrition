package carmo.tiago.ui;

public enum Objectives {
	
	HYPERTROPHY("Hypertrophy"),MAINTENANCE("Maintenance"),FAT_LOSS("Fat Loss");
	
	private String ObjectiveLetter;
	
	private Objectives(String letter){
		ObjectiveLetter = letter;
	}

	public String getObjectiveLetter() {
		return ObjectiveLetter;
	}

}
