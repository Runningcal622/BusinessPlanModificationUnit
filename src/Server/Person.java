package Server;
import java.io.Serializable;
import java.util.HashMap;

public class Person implements Serializable///, Watcher
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6294919862844351781L;
	public String username;
	public String password;
	public String department;
	public boolean admin;
	public HashMap<BP_Node,Boolean> nodeToChange;
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
		this.nodeToChange = new HashMap<BP_Node,Boolean>();
	}
	//@Override
	public void update(BP_Node node)
	{
		BP_Node found = null;
		for (BP_Node key : nodeToChange.keySet())
		{
			if (node.department.equals(key.department) && node.year==key.year)
			{
				found = key;
			}
		}
		nodeToChange.replace(found, true);

	}
	
	
	//@Override
	public void unUpdate(BP_Node node)
	{
		BP_Node found = null;
		for (BP_Node key : nodeToChange.keySet())
		{
			if (node.department.equals(key.department) && node.year==key.year)
			{
				found = key;
			}
		}
		nodeToChange.replace(found, false);
		
	}
	/**
	 * @return the nodeToChange
	 */
	public HashMap<BP_Node, Boolean> getNodeToChange()
	{
		return nodeToChange;
	}
	/**
	 * @param nodeToChange the nodeToChange to set
	 */
	public void setNodeToChange(HashMap<BP_Node, Boolean> nodeToChange)
	{
		this.nodeToChange = nodeToChange;
	}
	
	
	public void addToChange(BP_Node node)
	{
		this.nodeToChange.put(node, false);
	}
	
	public void remToChange(BP_Node node)
	{
		this.nodeToChange.remove(node);
	}
}
