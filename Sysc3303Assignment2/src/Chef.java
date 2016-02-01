
public class Chef implements Runnable{

	private Table table;
	private String ingredient;
	private String name;
	public Chef(Table t, String i, String n)	{	
		table = t;	
		ingredient = i;
		name = n;
		}

	public void makeSandwich() throws InterruptedException
	{
		table.putIngredients(ingredient);
		System.out.println("I, " + name + " found the " + ingredient +" !!!\n Can I eat it?\n I can?!!\n Thank you Jesus!!!");
	}
	
	@Override
	public void run() {
		for(int i = 0; i < 20; i++)
		{
			try {
				table.makeSandwich(ingredient, name);
				Thread.sleep(1000);
				}
				catch (Exception e) {	}
			}	
		}	
}