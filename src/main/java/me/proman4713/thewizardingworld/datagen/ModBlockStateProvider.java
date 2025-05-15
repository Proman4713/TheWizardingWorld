package me.proman4713.thewizardingworld.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

import me.proman4713.thewizardingworld.Blocks.ModBlocks;
import me.proman4713.thewizardingworld.TheWizardingWorld;

public class ModBlockStateProvider extends BlockStateProvider {
	public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
		super(output, TheWizardingWorld.MOD_ID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		blockWithSixFacesItem(
				ModBlocks.HARRY_POTTER_HEAD,
				"block/harry_potter_head",
				"block/harry_potter_head_bottom",
				"block/harry_potter_head_top",
				"block/harry_potter_head_front",
				"block/harry_potter_head_back",
				"block/harry_potter_head_right",
				"block/harry_potter_head_left",
				ParticleFace.NORTH
		);
	}

	private void blockWithItem(DeferredBlock<?> deferredBlock) {
		simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
	}

	public enum ParticleFace {
		NORTH,
		SOUTH,
		EAST,
		WEST,
		TOP,
		BOTTOM
	}

	private void blockWithSixFacesItem(DeferredBlock<?> deferredBlock, String blockKey, String blockBottomKey, String blockTopKey, String blockNorthKey, String blockSouthKey, String blockEastKey, String blockWestKey, ParticleFace particleFace) {
		String PARTICLE_KEY = "";
		switch (particleFace) {
			case NORTH:
				PARTICLE_KEY = blockNorthKey;
				break;
			case SOUTH:
				PARTICLE_KEY = blockSouthKey;
				break;
			case EAST:
				PARTICLE_KEY = blockEastKey;
				break;
			case WEST:
				PARTICLE_KEY = blockWestKey;
				break;
			case TOP:
				PARTICLE_KEY = blockTopKey;
				break;
			case BOTTOM:
				PARTICLE_KEY = blockBottomKey;
				break;
			default:
				break;
		}

		final ModelFile BLOCK_MODEL = models().cube(
				blockKey,
				modLoc(blockBottomKey),
				modLoc(blockTopKey),
				modLoc(blockNorthKey),
				modLoc(blockSouthKey),
				modLoc(blockEastKey),
				modLoc(blockWestKey)
		);

		simpleBlockItem(deferredBlock.get(), BLOCK_MODEL);
		if (PARTICLE_KEY.equals("")) {
			horizontalBlock(
					deferredBlock.get(),
					BLOCK_MODEL
			);
			return;
		}
		horizontalBlock(
				deferredBlock.get(),
				models().cube(
						blockKey,
						modLoc(blockBottomKey),
						modLoc(blockTopKey),
						modLoc(blockNorthKey),
						modLoc(blockSouthKey),
						modLoc(blockEastKey),
						modLoc(blockWestKey)
				).texture("particle", PARTICLE_KEY)
		);
	}
}
