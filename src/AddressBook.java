import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class AddressBook {

	private AddressBook() {
	}

	private String filePath = ".//addressbook.txt";
	private File f;
	private LinkedList<Contact> contacts;

	public static AddressBook getInstance() throws IOException {
		AddressBook ab = new AddressBook();
		ab.f = new File(ab.filePath);
		ab.contacts = ab.getContactListFromFile();
		return ab;
	}

	public void add(String[] args) throws IOException {
		Contact c = new Contact(args[0], args[1], args[2]);
		addContact(c);
	}

	private void addContact(Contact c) throws IOException {

//		if (search(c.getName()) != null) {
//			System.out.println("Contact " + c.getName() + " already exists.");
//			return;
//		}

		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(f, true)));

			out.println(c.serialize());
			contacts.add(c);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	public List<Contact> getAllContacts() {
		Collections.sort(contacts);
		return contacts;
	}

	private LinkedList<Contact> getContactListFromFile() throws IOException {
		if (!f.exists()) {
			f.createNewFile();
		}

		LinkedList<Contact> contactList = new LinkedList<Contact>();

		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(f)));
			while (reader.ready()) {
				contactList.add(Contact.deserialize(reader.readLine()));
			}
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
			}
		}

		return contactList;

	}

	public Contact search(String name) {
		for (Contact c : contacts) {
			if (c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}

	public void delete (String name) throws IOException {
		Contact c = search(name);
		if ( c == null) {
			return;
		}
		delete(c);
	}
	
	private void delete(Contact c) throws IOException {
		contacts.remove(c);
		updateFile();
	}

	private void updateFile() throws IOException {
		PrintWriter out = null;

		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(f, false)));

			for (Contact c : contacts) {
				out.println(c.serialize());
			}
		}

		finally {
			if (out != null) {
				out.close();
			}
		}
		
	}
	
	public void printBook() {

		if (contacts.size() == 0) {
			System.out.println("Addressbook is empty.");
		}

		for (Contact c : contacts) {
			System.out.println(c.toString() + "\n");
		}
	}

}
