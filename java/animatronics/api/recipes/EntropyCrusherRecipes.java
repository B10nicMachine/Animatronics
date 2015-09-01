package animatronics.api.recipes;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.item.ItemStack;

public class EntropyCrusherRecipes extends ARecipes {

	protected static final List<EntropyCrusherRecipe> recipeList = new ArrayList<EntropyCrusherRecipe>();
	
	public static void addRecipe(ItemStack input, ItemStack output1, ItemStack output2, int needEnergy, float percent, float time, float exp) {
		EntropyCrusherRecipe recipe = new EntropyCrusherRecipe(input, output1, output2, needEnergy, percent, time, exp);
		int index = getIndexRecipe(recipe.getInput());
		if(index == -1) return;
		else {
			FMLLog.warning("[ANIMATRONICS] Found conflict recipe" + recipe + " .");
			recipeList.add(index, recipe);
		}
	}
	
	public List<EntropyCrusherRecipe> getRecipes() {
		return recipeList;
	}
	
	public EntropyCrusherRecipe getRecipe(ItemStack item) {
		return getRecipe(getIndexRecipe(item));
	}
	
	public EntropyCrusherRecipe getRecipe(int index) {
        if (index > -1)
            return recipeList.get(index);
        else
            return null;
    }
	
	
	public static int getIndexRecipe(ItemStack item) {
        if (item == null) {
            return -1;
        }
        boolean check;
        EntropyCrusherRecipe recipe;
        for (int i = 0; i < recipeList.size(); i++) {
            recipe = recipeList.get(i);
            check = compare(item, recipe.getInput());
            if (check) {
                return i;
            }
        }
        return -1;
    }
}