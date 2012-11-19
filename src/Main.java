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
			String s = new String(getKbdString());
//			System.out.println("s" + s);
			if (s.equals("exit")) {
				break;
			}

			if (s.equals("add")) {
				String name = "";
				String phone = "";
				String email = "";

				System.out.println("Enter name: ");
				name = new String(getKbdString());

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
				continue;

			}

			if (s.startsWith("delete")) {

				if (s.length() > 6 && s.charAt(6) != ' ') {
					System.out.println("Unknown command.");
					printHelp();
					continue;
				}

				String name = s.substring(6).trim();
				if (name.equals("")) {
					System.out.println("Command format: delete <name>.");
					continue;
				}

				try {
					if (ab.delete(name)) {
						System.out.println("Contact " + name
								+ " successfully deleted.");
						continue;
					}
				} catch (IOException e) {
					System.out.println("Failed to delete contact.");
				}

				System.out.println("Contact " + name + " not found.");
				continue;
			}

			if (s.equals("get")) {
				ab.printBook();
				continue;
			}

			if (s.startsWith("search")) {

				if (s.length() > 6 && s.charAt(6) != ' ') {
					System.out.println("Unknown command.");
					printHelp();
					continue;
				}

				String name = s.substring(6).trim();

				if (name.equals("")) {
					System.out.println("Command format: search <name>.");
					continue;
				}

				Contact c = ab.search(name);

				if (c == null) {
					System.out.println("Contact " + name + " not found.");
					continue;
				}
				System.out.println(c.toString());
				continue;
			}

			if (s.equals("help")) {
				printHelp();
				continue;
			}

			else {
				System.out.println("Unknown command.");
				printHelp();
			}

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
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return sb.toString();
	}

}
