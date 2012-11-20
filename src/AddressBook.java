import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

public class AddressBook {

	public AddressBook() {
	}

	private String filePath = ".//addressbook.txt";
	private File f;
	private TreeMap<String, Contact> contacts;

	public void init() throws IOException {
		f = new File(filePath);
		contacts = getContactListFromFile();
	}

	public void add(String[] args) throws IOException {
		Contact c = new Contact(args[0], args[1], args[2]);
		
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(f, true)));

			out.println(c.serialize());

			contacts.put(c.getName(), c);
		} finally {
			if (out != null) {
				out.close();
			}
		}
		}
		
	private TreeMap<String, Contact> getContactListFromFile()
			throws IOException {
		if (!f.exists()) {
			f.createNewFile();
		}

		TreeMap<String, Contact> contactList = new TreeMap<String, Contact>();

		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(f)));
			while (reader.ready()) {
				Contact c = Contact.deserialize(reader.readLine());
				contactList.put(c.getName(), c);
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
		return contacts.get(name);
	}

	public void delete(String name) throws IOException {
		if (contacts.remove(name) != null) {
			updateFile();
		}
	}

	//Writes all contacts from "contacts" map to file.  
	private void updateFile() throws IOException {
		PrintWriter out = null;

		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(f, false)));

			for (Contact c : contacts.values()) {
				out.println(c.serialize());
			}
		}

		finally {
			if (out != null) {
				out.close();
			}
		}

	}

	// Returns list of all contacts.
	public List<String> getContacts() {
		
		LinkedList<String> contactsList = new LinkedList<String>();
		for (Contact c : contacts.values()) {
			contactsList.add(c.toString());
		}
		return contactsList;
	}

}
