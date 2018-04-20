package Server;
import java.io.Serializable;
public class BP_Node implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3907005654730289537L;
	public BusinessEntity entity;
	public int year;
	public String department;
	public boolean editable;
	
	public BP_Node() //holds variables, has getters and setters, no methods
	{
		
	}
	public BP_Node(BusinessEntity entity, int year, String department, boolean editable) 
	{
		this.entity=entity;
		this.year=year;
		this.department=department;
		this.editable=editable;
	}
	public BusinessEntity getEntity() {
		return entity;
	}
	public void setEntity(BusinessEntity entity) {
		this.entity = entity;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	
}
