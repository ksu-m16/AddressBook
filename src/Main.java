import java.io.IOException;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		AddressBook ab = new AddressBook();
		try {
			ab.init();
		} catch (IOException e) {
			System.out.println("Failed to load addressbok.");
			System.exit(1);
		}

		while (true) {
			System.out.println("Enter command.");
			String params = new String(getParamsString());

			parseParams(params, ab);
		}
	}

	// Returns string entered from keyboard.
	private static String getParamsString() {

		StringBuilder sb = new StringBuilder();

		try {
			do {
				sb.append((char) System.in.read());
			}

			while (System.in.available() > 0);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return sb.toString().trim();
	}

	private static void parseParams(String params, AddressBook ab) {
		if (params.equals("exit")) {
			System.exit(0);
		}

		if (params.equals("add")) {
			launchAddDialog(ab);
			return;
		}

		if (params.equals("get")) {
			
			printAddressBook(ab.getContacts());
			return;
		}

		if (params.startsWith("delete")) {
			launchDeleteDialog(params, ab);
			return;
		}

		if (params.startsWith("search")) {
			launchSearchDialog(params, ab);
			return;
		}

		if (params.equals("help")) {
			printHelp();
			return;
		}

		System.out.println("Unknown command.");
		printHelp();

	}

	private static void launchAddDialog(AddressBook ab) {
		String name = "";
		String phone = "";
		String email = "";

		System.out.println("Enter name: ");
		name = new String(getParamsString());

		while (ab.search(name) != null) {
			System.out.println("Contact " + name + " already exists.");
			System.out.println("Enter name: ");
			name = new String(getParamsString());
		}

		while (!ParamsVerifier.verifyName(name)) {
			System.out.println("Name may not be empty. Enter name: ");
			name = new String(getParamsString());
		}

		System.out.println("Enter phone: ");
		phone = new String(getParamsString());

		while (!ParamsVerifier.verifyPhone(phone)) {
			System.out.println("Incorrect phone number. Enter phone: ");
			phone = new String(getParamsString());
		}

		System.out.println("Enter email: ");
		email = new String(getParamsString());

		while (!ParamsVerifier.verifyEmail(email)) {
			System.out.println("Incorrect E-Mail address. Enter email: ");
			email = new String(getParamsString());
		}

		try {
			ab.add(new String[] { name, phone, email });
			System.out.println("Contact " + name + ", " + phone + ", " + email
					+ " saved.");
		} catch (IOException e) {
			System.out.println("Failed to add contact.");
		}
	}

	private static void launchDeleteDialog(String params, AddressBook ab) {
		if (params.length() > 6 && params.charAt(6) != ' ') {
			System.out.println("Unknown command.");
			printHelp();
			return;
		}

		String name = params.substring(6).trim();
		if (name.equals("")) {
			System.out.println("Command format: delete <name>.");
			return;
		}

		if (ab.search(name) == null) {
			System.out.println("Contact " + name + " not found.");
			return;
		}

		System.out.println("Press Y if you really want to delete contact "
				+ name + ". Press any other key to cancel deleting.");

		String confirm = new String(getParamsString());

		if (confirm.toLowerCase().equals("y")) {

			try {
				ab.delete(name);
				System.out
						.println("Contact " + name + " successfully deleted.");

			} catch (IOException e) {
				System.out.println("Failed to delete contact.");
			}
		}
	}

	private static void launchSearchDialog(String params, AddressBook ab) {
		if (params.length() > 6 && params.charAt(6) != ' ') {
			System.out.println("Unknown command.");
			printHelp();
			return;
		}

		String name = params.substring(6).trim();

		if (name.equals("")) {
			System.out.println("Command format: search <name>.");
			return;
		}

		Contact c = ab.search(name);

		if (c == null) {
			System.out.println("Contact " + name + " not found.");
		} else {
			System.out.println(c.toString());
		}

	}

	private static void printHelp() {
		System.out
				.println("Available commands (enter parameters without brackets):");
		System.out.println("add - creates new contact.");
		System.out
				.println("delete <name> - deletes specified contact from addressbook.");
		System.out.println("search <name> - searches for specified contact.");
		System.out.println("get - shows list of all contacts.");
		System.out.println("exit - exits program.");
	}
	
	private static void printAddressBook(List<String> list) {
		if (list.size() == 0) {
			System.out.println("Addressbook is empty.");
		}

		for (String s: list) {
			System.out.println(s + "\n");
		}
	}

}
