import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
	
	public AddressBook() throws IOException {
		f = new File(filePath);
//		contacts = getList();
		contacts = getContactListFromFile();
	}
	
	private String filePath = ".//book.txt";
	private File f;
//	private LinkedList<String> contacts;
	private LinkedList<Contact> contacts;
	
	public void add(String[] args) throws IOException {
		
//		if (! ArgsVerifier.verifyName(args[0])) {
//			System.out.println("Name may not be empty");
//			return;
//		}
//		
//		if (! ArgsVerifier.verifyPhone(args[1])) {
//			System.out.println("Incorrect phone number");
//			return;
//		}
//		
//		if (! ArgsVerifier.verifyEmail(args[2])) {
//			System.out.println("Incorrect E-Mail address");
//			return;
//		}
		
		Contact c = new Contact(args[0], args[1], args[2]);
		addContact(c);
	}
	
//	public List<String> getAllContacts() {
	public List<Contact> getAllContacts() {
		Collections.sort(contacts);
		return contacts;
	}
	
	private void addContact(Contact c) throws IOException {
		
		if (search(c.getName()) != null) {
			System.out.println("Contact " + c.getName() + " already exists.");
			return;
		}
		
		contacts.add(c);
		
//		History h = new History();
//		h.addContact(c);
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		try {
//			c.serialize(baos);

//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		String data = new String(baos.toByteArray());
		String data = c.serialize();

		PrintWriter out1 = new PrintWriter(new BufferedWriter(new FileWriter(f,
				true)));
		out1.println(data);
		out1.close();
		
	}
	
//	private LinkedList<String> getList() throws IOException {
//	
//		LinkedList<String> contactStringList = new LinkedList<String>();
//		
//		for (Contact c : getContactList()) {
//			contactStringList.add(c.toString());
//		}
//		
//		return contactStringList;
//		
//	}
	
	private LinkedList<Contact> getContactListFromFile() throws IOException {
		if (!f.exists()){
			f.createNewFile();
		}
			
		LinkedList<Contact> contactList = new LinkedList<Contact>();
//		History hnew = new History();

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(f)));
		while (reader.ready()) {
//			hnew.deserialize(new ByteArrayInputStream(reader.readLine()
//					.getBytes()));
			contactList.add(Contact.deserialize(reader.readLine()));
		}

//		contactList = (LinkedList<Contact>) hnew.getRecords();
		reader.close();
		
		return contactList;
		
	}
	
//	public String search (String name) {
//		List<Contact> contactList = getContactList();
//		String searchResult = "";
//		
//		for (Contact c : contactList) {
//			if (c.getName().equals(name)) {
//				searchResult = c.toString();
//				break;
//			}
//		}
//		if (searchResult.length() == 0) {
//			System.out.println("Contact " + name + " not found");
//		}
//		return searchResult;
//	}
	
	public Contact search (String name) throws IOException {

		for (Contact c : contacts) {
			if (c.getName().equals(name)) {
				return c;
			}
		}
		
//		System.out.println("Contact " + name + " not found");
			
		return null;
	}
	
	public boolean delete (String name) throws IOException {
		
		Contact toDelete = search(name);
		
		if (toDelete == null) {
			
			System.out.println("No contact with name " + name + " found.");
			return false;
		}
		
		contacts.remove(toDelete);
		
		
//		History h = new History();
		
//		for (Contact c : contacts) {
//			h.addContact(c);
//		}

//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
//		try {
//			h.serialize(baos);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		String data = new String(baos.toByteArray());
		
		PrintWriter out1 = new PrintWriter(new BufferedWriter(new FileWriter(f,
				false)));
		
		
		if (contacts.size() == 0){
			out1.println("");
		}
		
		for (Contact c : contacts) {
			out1.println(c.serialize());
		}
		
		out1.close();
		
		return true;
	}
	
	public void printBook (List<Contact> contacts) throws IOException {
		for (Contact c: contacts) {
			System.out.println(c.toString() + "\n");
		}
	}
	
	
	
}
