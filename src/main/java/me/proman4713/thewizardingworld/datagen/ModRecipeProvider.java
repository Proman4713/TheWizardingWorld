package me.proman4713.thewizardingworld.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import me.proman4713.thewizardingworld.Blocks.ModBlocks;
import me.proman4713.thewizardingworld.Items.ModItems;
import me.proman4713.thewizardingworld.TheWizardingWorld;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
	public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void buildRecipes(RecipeOutput recipeOutput) {
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.HARRY_POTTER_HEAD.get())
				.pattern("BCB")
				.pattern("BAB")
				.pattern("BBB")
				.define('A', Items.SOUL_SAND)
				.define('B', Items.LEATHER)
				.define('C', Items.NETHER_STAR)
				.unlockedBy("has_nether_star", has(Items.NETHER_STAR))
				.save(recipeOutput);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.STANDARD_OLLIVANDERS_WAND.get())
				.pattern("C  ")
				.pattern(" B ")
				.pattern("  A")
				.define('A', Items.STICK)
				.define('B', ModItems.PHOENIX_FEATHER)
				.define('C', Items.NETHER_STAR)
				.unlockedBy("has_phoenix_feather", has(ModItems.PHOENIX_FEATHER))
				.save(recipeOutput);

//		oreSmelting(
//				recipeOutput,
//				AListOfSmeltableItemsThatCanBeSmeltedToAchieveTheOutput,
//				RecipeCategory.MISC,
//				TheResultItem.get(),
//				0.25f,
//				200,
//				"groupName"
//		);
	}

	protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
	                                  float pExperience, int pCookingTIme, String pGroup) {
		oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
				pExperience, pCookingTIme, pGroup, "_from_smelting");
	}

	protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
	                                  float pExperience, int pCookingTime, String pGroup) {
		oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
				pExperience, pCookingTime, pGroup, "_from_blasting");
	}

	protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
	                                                                   List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
		for(ItemLike itemlike : pIngredients) {
			SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
					.save(recipeOutput, TheWizardingWorld.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
		}
	}
}
