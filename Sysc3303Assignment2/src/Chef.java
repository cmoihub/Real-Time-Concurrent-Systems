
public class Chef implements Runnable{

	private Table table;
	private String ingredient;
	private String name;
	
	public Chef(Table t, String ing, String n)	{	
		table = t;	
		ingredient = ing;
		name = n;
		}
	
	@Override
	public void run() {
		for(int i = 0; i < 20; i++)
		{
			try {
				table.makeSandwich(ingredient, name);
				Thread.sleep(1000);
				}	catch (Exception e) {	}
			}
		}	
	}