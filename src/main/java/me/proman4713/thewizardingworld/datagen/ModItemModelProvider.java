package me.proman4713.thewizardingworld.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import me.proman4713.thewizardingworld.Items.ModItems;
import me.proman4713.thewizardingworld.TheWizardingWorld;

public class ModItemModelProvider extends ItemModelProvider {
	public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, TheWizardingWorld.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		basicItem(ModItems.PHOENIX_FEATHER.get());
		basicItem(ModItems.STANDARD_OLLIVANDERS_WAND.get());
	}
}
