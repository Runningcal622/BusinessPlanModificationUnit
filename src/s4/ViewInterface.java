
package s4;

import java.util.ArrayList;

import Client.Client;
import Server.BP_Node;

public interface ViewInterface 
{
	
	public void showHome(Client client);
	public void showEdit(Client client);
	public void showLogin();
	public void showNewUser(Client client);
	public void showClone(BP_Node node, Client client, ArrayList<BP_Node> dep_plans,boolean new_plan);
	public void showSetStatus(Client client,int year);
	public void showSeeUser(Client client);
	
	

}
