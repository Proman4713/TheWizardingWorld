package me.proman4713.thewizardingworld.datagen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.Set;

import me.proman4713.thewizardingworld.Blocks.ModBlocks;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
	protected ModBlockLootTableProvider(HolderLookup.Provider pRegistries) {
		super(Set.of(), FeatureFlags.REGISTRY.allFlags(), pRegistries);
	}

	@Override
	protected void generate() {
		dropSelf(ModBlocks.HARRY_POTTER_HEAD.get());
	}

	@Override
	protected Iterable<Block> getKnownBlocks() {
		return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
	}
}
