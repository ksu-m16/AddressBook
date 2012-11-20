public class ArgsVerifier {

	// Simple name verification: parameter may not be empty.
	public static boolean verifyName(String name) {
		return name.length() != 0;
	}

	// Simple E-Mail verification: parameter should contain "@".
	public static boolean verifyEmail(String email) {
		return email.indexOf("@") > 0 ? true : false;
	}

	// Simple phone number verification:
	// Number may contain only numbers, spaces and symbols "+", "-", "(", ")".
	public static boolean verifyPhone(String phone) {
		return phone.matches("([\\s\\(\\)\\d\\+\\-]+)");
	}
}
