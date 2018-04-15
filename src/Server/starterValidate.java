package Server;

public class starterValidate
{
	public static void main(String[] args)
	{
		Server server = new Server();
		server.readDisk();
		System.out.println(server.businessPlans.size());
		System.out.println(server.people.size());

	}
}
