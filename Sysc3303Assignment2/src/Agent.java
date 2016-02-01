import java.util.*;

public class Agent implements Runnable{

	private Table table;
	private List<String> ingredients;
	//private ArrayList<Ingredient.ingredient> ingredients;
	
	public Agent(Table table, String name) {
		this.table = table;
		ingredients = Arrays.asList("bread","peanutbutter","jam");
	}
	
	@Override
	public void run() {
		for(int i = 0; i<20;i++)
		{
			//agent shuffles list of ingredients and picks the first two
			Collections.shuffle(ingredients);
			try {
				table.putIngredients(ingredients.get(0));
				table.putIngredients(ingredients.get(1));
				Thread.sleep(1000);
				}	catch (InterruptedException e) {	}
			}
		}
	}