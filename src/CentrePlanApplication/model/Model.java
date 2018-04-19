package CentrePlanApplication.model;

import CentrePlanApplication.Client.*;
import CentrePlanApplication.Server.*;

public class Model
{
	Client client;
	CentrePlanFactory centrehead;

	public Model()
	{
		this.client = new Client(new Server());
		this.centrehead = new CentrePlanFactory();
	}
	
	public Client getClient()
	{
		return client;
	}

	public void setClient(Client client)
	{
		this.client = client;
	}

	public CentrePlanFactory getCentrehead()
	{
		return centrehead;
	}

	public void setCentrehead(CentrePlanFactory centrehead)
	{
		this.centrehead = centrehead;
	}	
}
