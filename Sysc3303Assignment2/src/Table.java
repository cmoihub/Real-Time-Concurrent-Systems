import java.util.Arrays;

public class Table {
//Capacity of Table
private static final int recipe = 3;
private Object[]	table = new Object[recipe];
private int in, count, round = 0;

//State of table
private boolean canPlace = true;

	public synchronized void putIngredients(String newIngredient) throws InterruptedException
	{
		while(!canPlace){	wait();	}
		//No ingredients are on table
		table[in] = newIngredient;
		in = (in+1)%recipe;
		count++;
		//	if there are as many items on the table as those in the recipe, don't allow any more to be added
		if(count == recipe)	canPlace = false;
		notifyAll();
		//end of critical section
		System.out.println("Ingredient added to the table:" + newIngredient);
		}

	public synchronized void makeSandwich(String ingredient, String name) throws InterruptedException
	{
		//check if table already has item and if there is only one free space on table
		while(Arrays.asList(table).contains(ingredient)||count<2||!canPlace){	wait();	}
		round++;
		canPlace = true;
		//	Clear table
		table = new Object[recipe];
		in = 0;
		count = 0;
		notifyAll();
		//end of critical section
		System.out.println(name + " wins round " + round + " using " + ingredient);
	}
}