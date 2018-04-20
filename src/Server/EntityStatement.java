package Server;

import java.util.*;
import java.io.Serializable;

public class EntityStatement implements Serializable
{

	private static final long serialVersionUID = 4L;

	String name;
	String statement;
	Date date;
	EntityStatement oldStatement;

	public EntityStatement()
	{
	}

	public EntityStatement(String name, String statement, Date date, EntityStatement oldStatement)
	{
		super();
		this.name = name;
		this.statement = statement;
		this.date = date;
		this.oldStatement = oldStatement;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getStatement()
	{
		return statement;
	}

	public void setStatement(String statement)
	{
		this.statement = statement;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public EntityStatement getOldStatement()
	{
		return oldStatement;
	}

	public void setOldStatement(EntityStatement oldStatement)
	{
		this.oldStatement = oldStatement;
	}

}
