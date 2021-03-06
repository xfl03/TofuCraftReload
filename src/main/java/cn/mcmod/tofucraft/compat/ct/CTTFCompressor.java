package cn.mcmod.tofucraft.compat.ct;

import cn.mcmod.tofucraft.RecipeLoader;
import cn.mcmod.tofucraft.api.recipes.CompressorRecipes;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.tofucraft.tofuCompressor")
@ZenRegister
public class CTTFCompressor {
	@ZenMethod
	public static void RemoveRecipe(IIngredient input) {
		Object itemInput=null;
		if (input instanceof IItemStack) {
			itemInput=CraftTweakerMC.getItemStack(input);
		} 
		else if(input instanceof IOreDictEntry) {
			itemInput=((IOreDictEntry)input).getName();
		}
		if(itemInput!=null)
			RecipeLoader.actions.add(new Removal(itemInput));
	}
	
	@ZenMethod
	public static void AddRecipe(IIngredient input,IItemStack output) {
		Object itemInput=null;
		if (input instanceof IItemStack) {
			itemInput=CraftTweakerMC.getItemStack(input);
		} 
		else if(input instanceof IOreDictEntry) {
			itemInput=((IOreDictEntry)input).getName();
		}
		if(itemInput!=null)
			RecipeLoader.actions.add(new Addition(itemInput,CraftTweakerMC.getItemStack(output)));
	}
	
	@ZenMethod
	public static void ClearAllRecipe() {
		RecipeLoader.actions.add(new ClearAllRecipe());
	}
	
    private static final class Removal implements IAction
    {
        private final Object itemInput;

        private Removal(Object itemInput)
        {
            this.itemInput = itemInput;
        }

        @Override
        public void apply()
        {
        	CompressorRecipes.ClearRecipe(itemInput);
        }

        @Override
        public String describe()
        {
            return "Removing a recipe for ToFu Compressor";
        }
    }
	
    private static final class Addition implements IAction
    {
        private final Object itemInput;
        private final ItemStack itemOutput;

        private Addition(Object itemInput, ItemStack itemOutput)
        {
            this.itemInput = itemInput;
            this.itemOutput = itemOutput;
        }

        @Override
        public void apply()
        {
        	CompressorRecipes.addRecipe(itemInput, itemOutput);
        }

        @Override
        public String describe()
        {
            return "Adding a recipe for ToFu Compressor";
        }
    }

	
	private static final class ClearAllRecipe implements IAction
    {
        @Override
        public void apply()
        {
        	CompressorRecipes.ClearAllRecipe();
        }

        @Override
        public String describe()
        {
            return "Removing all recipes from ToFu Compressor";
        }
    }
}
