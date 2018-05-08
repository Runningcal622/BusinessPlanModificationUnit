package s4.Commands;

import Client.Client;

public class setEditable implements Command
{

	@Override
	public void action(int year, Client client)
	{
		client.requestBusinessPlan(client.person.getDepartment(), year);
		client.business.setEditable(true);
	}

}
