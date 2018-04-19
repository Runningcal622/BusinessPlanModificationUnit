package CentrePlanApplication.Server;
import java.rmi.Remote;
import java.util.LinkedList;
public interface ServerInterface extends Remote 
{
	public void addPerson(Person person);
	public void addBP_Node(BP_Node node);
	public void deleteBP_Node(BP_Node node);
	public Person getPerson(String username, String password);
	public BP_Node getBP_Node(String department, int year);
	public LinkedList<Person> getPeople();
	public LinkedList<BP_Node> getBP();
	public void readDisk();
	public void writeDisk();
	public void setEditStatus(BP_Node businessPlan, boolean editable);
	public void make_BlankBP(int year, String department, Boolean editable);
	public void make_CloneBP(int year, String department, Boolean editable, int new_year);
}
