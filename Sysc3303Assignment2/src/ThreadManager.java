
public class ThreadManager {

	public ThreadManager() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args)
	{
		Thread chefButter = new Thread(new Chef());
		Thread chefJam = new Thread(new Chef());
		Thread chefBread = new Thread(new Chef());
		Thread agent = new Thread(new Agent());
		
		chefButter.start();
		chefJam.start();
		chefBread.start();
		agent.start();
		
	}
}
