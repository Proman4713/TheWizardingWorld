package me.proman4713.thewizardingworld.Items;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import me.proman4713.thewizardingworld.Blocks.ModBlocks;
import me.proman4713.thewizardingworld.TheWizardingWorld;

public class ModCreativeModeTabs {
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
			DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TheWizardingWorld.MOD_ID);

	public static final Supplier<CreativeModeTab> WIZARDING_WORLD_TAB = CREATIVE_MODE_TABS.register("wizarding_world_tab",
			() -> CreativeModeTab.builder()
					.icon(() -> new ItemStack(ModItems.STANDARD_OLLIVANDERS_WAND.get()))
					.title(Component.translatable("creativetab.thewizardingworld.wizarding_world"))
					.displayItems((pParameters, pOutput) -> {
						pOutput.accept(ModItems.STANDARD_OLLIVANDERS_WAND.get());
						pOutput.accept(ModBlocks.HARRY_POTTER_HEAD.get());
						pOutput.accept(ModItems.PHOENIX_FEATHER.get());
					}).build());

	public static void register(IEventBus eventBus) {
		CREATIVE_MODE_TABS.register(eventBus);
	}
}