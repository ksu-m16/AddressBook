import com.google.gson.Gson;

public class Contact implements Comparable<Contact> {
	public Contact(String name, String phone, String email) {
		this.name = name;
		this.phone = phone;
		this.email = email;
	}

	private String name;
	private String phone;
	private String email;

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return name + "\nPhone: " + phone + "\nE-Mail: " + email;
	}

	@Override
	public int compareTo(Contact c) {
		return this.name.compareTo(c.getName());
	}

	public String serialize() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

	public static Contact deserialize(String s) {
		Gson gson = new Gson();
		return gson.fromJson(s, Contact.class);
	}

}
