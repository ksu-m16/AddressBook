
public class ArgsVerifier {
	
	public static boolean verifyName(String name) {
		return name.length() != 0;
}

public static boolean verifyEmail(String email) {
	return email.indexOf("@") > 0 ? true : false;
}

public static boolean verifyPhone(String phone) {
	return phone.matches("([\\s\\(\\)\\d\\+\\-]+)");
}
}
