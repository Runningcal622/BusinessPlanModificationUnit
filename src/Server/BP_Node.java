package Server;

import java.io.Serializable;
import java.util.LinkedList;

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
	public LinkedList<Person> observers;

	public BP_Node() // holds variables, has getters and setters, no methods
	{

	}

	public BP_Node(BusinessEntity entity, int year, String department, boolean editable)
	{
		this.entity = entity;
		this.year = year;
		this.department = department;
		this.editable = editable;
		this.observers = new LinkedList<Person>();
	}

	/**
	 * @return the observers
	 */
	public LinkedList<Person> getObservers()
	{
		return observers;
	}

	/**
	 * @param observers
	 *            the observers to set
	 */
	public void setObservers(LinkedList<Person> observers)
	{
		this.observers = observers;
	}

	public BusinessEntity getEntity()
	{
		return entity;
	}

	public void setEntity(BusinessEntity entity)
	{
		this.entity = entity;
	}

	public int getYear()
	{
		return year;
	}

	public void setYear(int year)
	{
		this.year = year;
	}

	public String getDepartment()
	{
		return department;
	}

	public void setDepartment(String department)
	{
		this.department = department;
	}

	public boolean isEditable()
	{
		return editable;
	}

	public void setEditable(boolean editable)
	{
		this.editable = editable;
	}

	// @Override
	public void addWatcher(Person caleb)
	{
		this.observers.add(caleb);
		Person p = (Person) caleb;
		p.addToChange(this);
	}

	// @Override
	public void remWatcher(Person watch)
	{
		this.observers.remove(watch);
		Person p = (Person) watch;
		p.remToChange(this);
	}

	// @Override
	public void update()
	{
		for (Person watch : this.observers)
		{
			watch.update(this);
		}
	}
	

	//@Override
	public boolean equals(BP_Node node)
	{
		if (node.department.equals(this.department) && node.year == this.year)
		{
			return true;
		} else
		{
			return false;
		}
	}
}
