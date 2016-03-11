import java.util.Arrays;

	public class Table {
	//	Capacity of Table
	private static final int recipe = 3;
	private Object[]	table = new Object[recipe];
	private int in, count, round = 0;

	//	State of table
	private boolean canPlace = true;

	//method for agent
	public synchronized void putIngredients(String newIngredient) throws InterruptedException
	{
		//check if a chef has made a sandwich 
		while(!canPlace){	wait();	}
		//place ingredient on table and increment index and count of ingredients
		table[in] = newIngredient;
		in = (in+1)%recipe;
		count++;
		//	if there are as many items on the table as those in the recipe, don't allow any more to be added
		if(count == recipe)	canPlace = false;
		notifyAll();
		//end of critical section
		System.out.println("Ingredient added to the table:" + newIngredient);
		}

	//method for chefs
	public synchronized void makeSandwich(String ingredient, String chefName) throws InterruptedException
	{
		//check if table already has item and if there is only one free space on table
		while(Arrays.asList(table).contains(ingredient)||count<2||!canPlace){	wait();	}
		//the round variable tracks how long the program has been running
		round++;
		//	Reset table
		table = new Object[recipe];
		canPlace = true;
		in = 0;
		count = 0;
		notifyAll();
		//end of critical section
		System.out.println(chefName + " wins round " + round + " using " + ingredient+"\n");
	}
}