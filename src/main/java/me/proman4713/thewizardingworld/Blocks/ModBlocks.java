package me.proman4713.thewizardingworld.Blocks;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

import me.proman4713.thewizardingworld.Blocks.custom.ModHorizontalDirectionalBlock;
import me.proman4713.thewizardingworld.Items.ModItems;
import me.proman4713.thewizardingworld.TheWizardingWorld;

public class ModBlocks {
	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(TheWizardingWorld.MOD_ID);

	public static final DeferredBlock<Block> HARRY_POTTER_HEAD = registerBlock("harry_potter_head",
			() -> new ModHorizontalDirectionalBlock(BlockBehaviour.Properties.of()
					.strength(2.0F, 3.0F)
					.sound(SoundType.WOOD)) {
						@Override
						public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
							tooltipComponents.add(Component.translatable("tooltip.thewizardingworld.harry_potter_head"));
							super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
						}
			});

	public static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
		DeferredBlock<T> toReturn = BLOCKS.register(name, block);
		registerBlockItem(name, toReturn);
		return toReturn;
	}

	public static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
		ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
	}

	public static void register(IEventBus eventBus) {
		BLOCKS.register(eventBus);
	}
}