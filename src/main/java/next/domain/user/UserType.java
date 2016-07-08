package next.domain.user;

public enum UserType {
	SRELLO(Values.SRELLO), FACEBOOK(Values.FACEBOOK);

	private String type;

	private UserType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public static class Values {
		public static final String SRELLO = "S";
		public static final String FACEBOOK = "F";
	}
}
