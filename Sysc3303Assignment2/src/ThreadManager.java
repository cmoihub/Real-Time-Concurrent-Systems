public class ThreadManager {
	public static void main(String[] args)
	{
		Table table = new Table();
		Thread chefPeanutButter = new Thread(new Chef(table,"peanutbutter", "Lynn"));
		Thread chefJam = new Thread(new Chef(table,"jam", "Craig"));
		Thread chefBread = new Thread(new Chef(table,"bread", "Sysc"));
		Thread agent = new Thread(new Agent(table,"007"));
		
		agent.start();
		chefPeanutButter.start();
		chefJam.start();
		chefBread.start();
		}
}
