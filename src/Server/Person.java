package Server;
import java.io.Serializable;

public class Person implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6294919862844351781L;
	public String username;
	public String password;
	public String department;
	public boolean admin;
	public Person() //holds variables, has getters and setters, no other methods
	{
		
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public Person(String username, String password, String department, boolean admin)
	{
		this.username=username;
		this.password=password;
		this.department=department;
		this.admin=admin;
	}
}
