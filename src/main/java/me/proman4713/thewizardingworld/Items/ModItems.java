package me.proman4713.thewizardingworld.Items;

import me.proman4713.thewizardingworld.Items.custom.FuelItem;
import me.proman4713.thewizardingworld.Items.custom.StandardOllivandersWandItem;
import me.proman4713.thewizardingworld.TheWizardingWorld;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class ModItems {
	public static final DeferredRegister.Items ITEMS =
		DeferredRegister.createItems(TheWizardingWorld.MOD_ID);

	public static final DeferredItem<Item> STANDARD_OLLIVANDERS_WAND = ITEMS.register("standard_ollivanders_wand", () ->
			new StandardOllivandersWandItem(new Item.Properties()
				.fireResistant()
				.rarity(Rarity.COMMON)
				.durability(100))
	);

	public static final DeferredItem<Item> PHOENIX_FEATHER = ITEMS.register("phoenix_feather", () ->
			new FuelItem(new Item.Properties()
					.fireResistant()
					.rarity(Rarity.UNCOMMON), 216000) {
				@Override
				public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pFlag) {
					pTooltipComponents.add(Component.translatable("tooltip.thewizardingworld.phoenix_feather"));
					super.appendHoverText(pStack, pContext, pTooltipComponents, pFlag);
				}
			});

	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}
}
