package carmo.tiago.ui;

public enum Sex {
	
	MALE("Male"), FEMALE("Female");
	
	private String sexString;
	
	private Sex(String s){
		sexString = s;
	}

	public String getSexString() {
		return sexString;
	}
	
}
