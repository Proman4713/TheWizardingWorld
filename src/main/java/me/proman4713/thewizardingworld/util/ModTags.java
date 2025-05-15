package me.proman4713.thewizardingworld.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import me.proman4713.thewizardingworld.TheWizardingWorld;

public class ModTags {
	public static class Blocks {
		private static TagKey<Block> createTag(String name) {
			return BlockTags.create(ResourceLocation.fromNamespaceAndPath(TheWizardingWorld.MOD_ID, name));
		}
	}

	public static class Items {
//		public static final TagKey<Item> SampleTag = createTag("sample_tag");
		private static TagKey<Item> createTag(String name) {
			return ItemTags.create(ResourceLocation.fromNamespaceAndPath(TheWizardingWorld.MOD_ID, name));
		}
	}
}
