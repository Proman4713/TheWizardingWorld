package me.proman4713.thewizardingworld.Items.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public abstract class WandItem extends Item {
	public WandItem(Properties pProperties) {
		super(pProperties);
	}

	abstract void castOnBlock(Level level, UseOnContext pContext);

	abstract void castOnAir(Level level, Player player, InteractionHand hand);

	abstract void hitOnBlock(Level level, Player player, InteractionHand hand);

	abstract void hitOnEntity(ItemStack stack, Player player, Entity entity);

	@Override
	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
		pPlayer.swing(pHand);

		if (!pLevel.isClientSide()) {
			castOnAir(pLevel, pPlayer, pHand);

			pPlayer.getItemInHand(pHand).hurtAndBreak(1, ((ServerLevel) pLevel), ((ServerPlayer) pPlayer),
					item -> pPlayer.onEquippedItemBroken(item, EquipmentSlot.MAINHAND));

			pPlayer.getCooldowns().addCooldown(pPlayer.getItemInHand(pHand).getItem(), 100);

			pLevel.playSound(null, pPlayer.blockPosition(), SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 5.0f, 1.0f);
		}

		return InteractionResultHolder.success(pPlayer.getItemInHand(pHand));
	}

	// This runs when the player left clicks an entity (i.e. the wand is in their main hand and they hit an entity)
	@Override
	public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
		if (player != null) {
			player.swing(InteractionHand.MAIN_HAND);
		}

		if (!player.level().isClientSide()) {
			hitOnEntity(stack, player, entity);

			player.getItemInHand(InteractionHand.MAIN_HAND).hurtAndBreak(1, ((ServerLevel) player.level()), ((ServerPlayer) player),
					item -> player.onEquippedItemBroken(item, EquipmentSlot.MAINHAND));

			player.level().playSound(null, player.blockPosition(), SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 5.0f, 1.0f);
		}
		return false;
	}

	@Override
	public InteractionResult useOn(UseOnContext pContext) {
		Level level = pContext.getLevel();
		Player player = pContext.getPlayer();

		// Make the player swing their arm
		if (player != null) {
			player.swing(pContext.getHand());
		}

		if (!level.isClientSide) {
			castOnBlock(level, pContext);

			pContext.getItemInHand().hurtAndBreak(1, ((ServerLevel) level), ((ServerPlayer) pContext.getPlayer()),
					item -> pContext.getPlayer().onEquippedItemBroken(item, EquipmentSlot.MAINHAND));

			player.getCooldowns().addCooldown(pContext.getItemInHand().getItem(), 60);

			level.playSound(null, pContext.getClickedPos(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 5.0f, 1.0f);
		}
		return InteractionResult.SUCCESS;
	}
}