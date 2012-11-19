import java.io.IOException;


public class Main {


	public static void main(String[] args) throws IOException {
		AddressBook ab = new AddressBook();
		
		
		while(true)
		{    
		  System.out.println("Enter command.");
		  String s = new String(getKbdString());
//		  System.out.println("String: " + s);
		      
		  if(s.equals("exit")) {
		    break;
		  }
		  
		  if (s.startsWith("add")) {
			  String name = "";
			  String phone = "";
			  String email = "";
			  
				  System.out.print("Enter name: ");
				  name = new String(getKbdString());
				  
				  while (! ArgsVerifier.verifyName(name)) {
					  System.out.print("Name may not be empty. Enter name: ");
					  name = new String(getKbdString());
					}

				  System.out.print("Enter phone: ");
				  phone = new String(getKbdString());
				  
				while (! ArgsVerifier.verifyPhone(phone)) {
						 System.out.print("Incorrect phone number. Enter phone: ");
						 phone = new String(getKbdString());
					}
				  
				  System.out.print("Enter email: ");
				  email = new String(getKbdString());
				  
					while (! ArgsVerifier.verifyEmail(email)) {
						System.out.println("Incorrect E-Mail address. Enter email: ");
						email = new String(getKbdString());
					}
				  
				  ab.add(new String[]{name, phone, email});
				  
				  System.out.println("Contact " + name + ", " + phone + ", " + email + " saved.");
		  }
		    
		  if (s.startsWith("delete")) {
			  String name = s.substring(6).trim();
			  if (name.equals("")) {
				  System.out.println("Command format: delete <name>.");
				  continue;
			  }
			  
			  if (ab.delete(name))	{
				  System.out.println("Contact " + name + " successfully deleted.");
				  continue;
			  }
			  
			  System.out.println("Contact " + name + " not found.");
		  }
		  
		  if(s.equals("get")) {
			  ab.printBook(ab.getAllContacts());
		  }
		  
		  if(s.startsWith("search")) {
			  String name = s.substring(6).trim();
			  
			  System.out.println(s);
			  
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
		  }
		  
		  if(s.equals("help")) {
			  System.out.println("Available commands (enter parameters without brackets):");
			  System.out.println("add - creates new contact.");
			  System.out.println("delete <name> - deletes specified contact from addressbook.");
			  System.out.println("search <name> - searches for specified contact.");
			  System.out.println("get - shows list of all contacts.");
			  System.out.println("exit - exits program.");
		  }
		  
		  
		}  
		
		
		
//		ab.add(new String[]{"zacd", "991aa", "aaa@www"});
//		ab.add(new String[]{"vv", "999", "aaawww"});
//		ab.add(new String[]{"bbb", "991", "aaa@www"});
//		ab.add(new String[]{"aab", "996", "rrr@www"});
//		ab.add(new String[]{"aac", "991", "aaa@www"});
//		ab.add(new String[]{"cccc", "991", "aaa@www"});
//		ab.delete("zac");
//		ab.add(new String[]{"ddd", "991", "aaa@www"});
//		ab.add(new String[]{"eee", "991", "aaa@www"});
//		System.out.println(ab.search("aaa"));
		
//		ab.printBook();

	}

	private static String getKbdString()
	{

		byte bKbd[] = new byte[256];
		int count = 0;
	
		try
		{
		  count = System.in.read(bKbd);
		}
		catch(Exception ex)
		{
		  System.out.println(ex.toString()); 
		}
	
		return new String(bKbd, 0, count).trim();
	}
	
}
