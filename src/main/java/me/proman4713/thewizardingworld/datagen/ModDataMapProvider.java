package me.proman4713.thewizardingworld.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

import me.proman4713.thewizardingworld.Items.ModItems;

public class ModDataMapProvider extends DataMapProvider {
	protected ModDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(packOutput, lookupProvider);
	}

	@Override
	protected void gather(HolderLookup.Provider provider) {
		this.builder(NeoForgeDataMaps.FURNACE_FUELS)
				.add(ModItems.PHOENIX_FEATHER.getId(), new FurnaceFuel(216000), false);
	}
}
