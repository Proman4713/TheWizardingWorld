package me.proman4713.thewizardingworld.Items.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import org.joml.Vector3f;

import java.util.List;

public class StandardOllivandersWandItem extends WandItem {
	public StandardOllivandersWandItem(Properties pProperties) {
		super(pProperties);
	}

	@Override
	public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pFlag) {
		if (Screen.hasShiftDown()) {
			pTooltipComponents.add(Component.translatable("tooltip.testforgemod.ollivanders_wand.expelliarmus"));
		} else {
			pTooltipComponents.add(Component.translatable("tooltip.testforgemod.ollivanders_wand.lumos"));
		}

		super.appendHoverText(pStack, pContext, pTooltipComponents, pFlag);
	}

	@Override
	void castOnBlock(Level level, UseOnContext pContext) {
		final BlockPos posToChange = pContext.getClickedPos().atY(pContext.getClickedPos().getY() + 1);
		final BlockState previousBlockState = level.getBlockState(posToChange);
		final Player player = pContext.getPlayer();

		if (player != null) {
			player.push(0, 0.7D, 0);
//			player.setIgnoreFallDamageFromCurrentImpulse(true);
			player.hurtMarked = true;

			player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 60, 2, false, false));
		}

		// Replace the block with light
		level.setBlockAndUpdate(posToChange, Blocks.LIGHT.defaultBlockState());

		// Wait a bit and replace it back
		if (level instanceof ServerLevel) {
			// Register a handler that will remove the light after 100 ticks
			NeoForge.EVENT_BUS.register(new LightRemovalHandler(level, posToChange, previousBlockState, 200));
		}
	}

	@Override
	void hitOnBlock(Level level, Player player, InteractionHand hand) {

	}

	@Override
	void castOnAir(Level level, Player player, InteractionHand hand) {
		final ServerLevel serverLevel = ((ServerLevel) level);
		for (int i = 0; i < 50; i++) {
			serverLevel.sendParticles(
					ParticleTypes.GUST_EMITTER_LARGE,
					player.getX(), player.getY() + 1, player.getZ(),
					1, // count = 1 per iteration
					0.0, 0.0, 0.0, // spread = 0 because we're using explicit motion
					(serverLevel.getRandom().nextDouble() - 0.5) * 0.5      // motion (speedX, speedY, speedZ)
			);
		}

		for (int i = 0; i < 50; i++) {
			double spreadX = (serverLevel.getRandom().nextDouble() - 0.5) * 10;
			double spreadZ = (serverLevel.getRandom().nextDouble() - 0.5) * 10;

			serverLevel.sendParticles(
					new DustParticleOptions(new Vector3f(1F, 0F, 0F), 3F),
					player.getX() + spreadX, player.getY() + 1, player.getZ() + spreadZ,
					1, // count
					0.0, 0.0, 0.0, // spread
					0.0            // speed/motion (dust uses this as scale fade velocity)
			);
		}

		final double effectRadius = 10.0;
		List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(effectRadius),
				target -> target != player && !target.isSpectator() && target.isAlive());

		for (LivingEntity target : targets) {
			ItemStack held = target.getMainHandItem();
			if (!held.isEmpty()) {
				target.spawnAtLocation(held.copy());
				target.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);

				serverLevel.sendParticles(
						new DustParticleOptions(new Vector3f(1F, 0F, 0F), 1F),
						target.getX(), target.getY() + 1, target.getZ(),
						5, // count
						0.0, 0.0, 0.0, // spread
						0.0            // speed/motion (dust uses this as scale fade velocity)
				);

				level.playSound(null, target.blockPosition(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.0f, 1.0f);
			}
		}
	}

	@Override
	void hitOnEntity(ItemStack stack, Player player, Entity entity) {
		final LivingEntity target = ((LivingEntity) entity);

		ItemStack held = target.getMainHandItem();
		if (!held.isEmpty()) {
			target.spawnAtLocation(held.copy());
			target.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);

			player.level().playSound(null, target.blockPosition(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.0f, 1.0f);
		}
	}

	// Inner class to handle the delayed light removal
	private static class LightRemovalHandler {
		private final Level level;
		private final BlockPos pos;
		private final BlockState originalState;
		private int ticksLeft;

		public LightRemovalHandler(Level level, BlockPos pos, BlockState originalState, int ticks) {
			this.level = level;
			this.pos = pos;
			this.originalState = originalState;
			this.ticksLeft = ticks;
		}

		@SubscribeEvent
		public void onServerTick(ServerTickEvent.Post event) {
			ticksLeft--;

			if (ticksLeft <= 0) {
				// Check if the block is still light
				if (level.getBlockState(pos).getBlock() == Blocks.LIGHT) {
					// Replace it with the original block
					level.setBlockAndUpdate(pos, originalState);
				}

				// Unregister this handler
				NeoForge.EVENT_BUS.unregister(this);
			}
		}
	}
}
