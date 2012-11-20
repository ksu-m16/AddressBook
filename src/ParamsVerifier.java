public class ParamsVerifier {

	// Simple name verification: parameter may not be empty.
	public static boolean verifyName(String name) {
		return name.length() != 0;
	}

	// Simple E-Mail verification: parameter should contain "@" or be empty.
	public static boolean verifyEmail(String email) {
		return (email.length() == 0) || (email.indexOf("@") >= 0);
	}

	// Simple phone number verification:
	// Number may be empty or contain numbers, spaces and symbols "+", "-", "(",
	// ")".
	public static boolean verifyPhone(String phone) {
		return phone.matches("([\\s\\(\\)\\d\\+\\-]*)");
	}
}
