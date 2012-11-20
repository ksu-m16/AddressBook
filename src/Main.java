import java.io.IOException;

public class Main {

	public static void main(String[] args) {

		AddressBook ab = null;
		try {
			ab = AddressBook.getInstance();
		} catch (IOException e) {
			System.out.println("Failed to load addressbok.");
			System.exit(1);
		}

		while (true) {
			System.out.println("Enter command.");
			String params = new String(getKbdString());

			parseParams(params, ab);
		}
	}



	private static String getKbdString() {

		byte bKbd[] = new byte[256];
		int count = 0;

		try {
			count = System.in.read(bKbd);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}

		return new String(bKbd, 0, count).trim();
//		StringBuilder sb = new StringBuilder();
//		
//		try {
//			while(System.in.available() > 0) {
//				System.out.println("available " + System.in.available());
//				sb.append(System.in.read());
//				
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		return sb.toString();
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
			ab.printBook();
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
		name = new String(getKbdString());

		while (ab.search(name) != null) {
			System.out.println("Contact " + name + " already exists.");
			System.out.println("Enter name: ");
			name = new String(getKbdString());
		}
		
		while (!ArgsVerifier.verifyName(name)) {
			System.out.println("Name may not be empty. Enter name: ");
			name = new String(getKbdString());
		}

		System.out.println("Enter phone: ");
		phone = new String(getKbdString());

		while (!ArgsVerifier.verifyPhone(phone)) {
			System.out.println("Incorrect phone number. Enter phone: ");
			phone = new String(getKbdString());
		}

		System.out.println("Enter email: ");
		email = new String(getKbdString());

		while (!ArgsVerifier.verifyEmail(email)) {
			System.out
					.println("Incorrect E-Mail address. Enter email: ");
			email = new String(getKbdString());
		}

		try {
			ab.add(new String[] { name, phone, email });
			System.out.println("Contact " + name + ", " + phone + ", "
					+ email + " saved.");
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
		
		System.out.println("Press Y if you really want to delete contact " + name + ". Press any other key to cancel deleting.");
		
		String confirm = new String(getKbdString());
		
		if (confirm.toLowerCase().equals("y")) {
		
			try {
				ab.delete(name);
				System.out.println("Contact " + name
							+ " successfully deleted.");

			} catch (IOException e) {
				System.out.println("Failed to delete contact.");
			}
		}
	}
	
	private static void launchSearchDialog(String params, AddressBook ab){
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
		}
		else {
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
	
}
