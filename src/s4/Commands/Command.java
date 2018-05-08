package s4.Commands;

import Client.Client;

public interface Command
{
	public void action(int year, Client client);
}
