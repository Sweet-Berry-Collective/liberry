package dev.sweetberry.liberry.datagen.builtin;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import com.terraformersmc.terraform.boat.api.item.TerraformBoatItemHelper;
import com.terraformersmc.terraform.boat.impl.item.TerraformBoatItem;
import dev.sweetberry.liberry.Liberry;
import dev.sweetberry.liberry.content.block.LiberryFungusBlock;
import dev.sweetberry.liberry.content.world.LiberrySaplingGenerator;
import dev.sweetberry.liberry.datagen.*;
import dev.sweetberry.liberry.datagen.blockstate.BlockStateModel;
import dev.sweetberry.liberry.datagen.blockstate.VariantBlockState;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeRegistry;
import net.minecraft.block.*;
import net.minecraft.block.sapling.SaplingBlock;
import net.minecraft.block.sign.CeilingHangingSignBlock;
import net.minecraft.block.sign.SignBlock;
import net.minecraft.block.sign.WallHangingSignBlock;
import net.minecraft.block.sign.WallSignBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.item.HangingSignItem;
import net.minecraft.item.Item;
import net.minecraft.item.SignItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.block.content.registry.api.BlockContentRegistries;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.HashMap;

public class WoodType {
	private static final DataGenerator GENERATOR = DataGeneratorUtils.registerGenerator(new DataGenerator(
		Liberry.id("wood_type"),
		(baseId, metadata, registrationContext, resourceContext) -> {
			var wood = (MapColor)metadata.get("wood_color");
			var bark = (MapColor)metadata.get("bark_color");
			var sounds = (BlockSoundGroup)metadata.get("sounds");
			var createBoat = (boolean)metadata.get("create_boat");
			var fungusBaseBlock = (Block)metadata.get("fungus_base_block");

			registerContent(baseId, wood, bark, sounds, createBoat, fungusBaseBlock, registrationContext);
			registerClientData(baseId, createBoat, fungusBaseBlock != null, resourceContext);
		}
	));

	private static void registerClientData(
		Identifier baseId,
		boolean createBoat,
		boolean isFungus,
		ResourceContext context
	) {
		final var logName = isFungus ? "stem" : "log";
		final var woodName = isFungus ? "hyphae" : "wood";
		final var leavesName = isFungus ? "wart" : "leaves";
		final var saplingName = isFungus ? "fungus" : "sapling";

		Identifier id;

		id = transformId(baseId, "block/${orig}_planks");
		var planks_id = id.toString();
		context.blockstate(
			transformId(baseId, "blockstates/${orig}_planks"), new VariantBlockState()
			.with("", new BlockStateModel(id, false))
		);
		Template.MODEL_BLOCK.apply(
			context, id.withPrefix("models/"),
			"all", planks_id
		);
		Template.MODEL_DELEGATE.apply(
			context, transformId(baseId, "models/item/${orig}_planks"),
			"parent", planks_id
		);

		id = transformId(baseId, "block/${orig}_stairs");
		Template.BLOCKSTATE_STAIRS.apply(
			context, transformId(baseId, "blockstates/${orig}_stairs"),
			"id", id.toString()
		);
		id = id.withPrefix("models/");
		Template.MODEL_STAIRS.apply(
			context, id,
			"bottom", planks_id,
			"side", planks_id,
			"top", planks_id
		);
		Template.MODEL_STAIRS_INNER.apply(
			context, id.extendPath("_inner"),
			"bottom", planks_id,
			"side", planks_id,
			"top", planks_id
		);
		Template.MODEL_STAIRS_OUTER.apply(
			context, id.extendPath("_outer"),
			"bottom", planks_id,
			"side", planks_id,
			"top", planks_id
		);
		Template.MODEL_DELEGATE.apply(
			context, transformId(baseId, "models/item/${orig}_stairs"),
			"parent", transformId(baseId, "block/${orig}_stairs").toString()
		);

		id = transformId(baseId, "block/${orig}_slab");
		Template.BLOCKSTATE_SLAB.apply(
			context, transformId(baseId, "blockstates/${orig}_slab"),
			"half", id.toString(),
			"full", planks_id
		);
		id = id.withPrefix("models/");
		Template.MODEL_SLAB.apply(
			context, id,
			"bottom", planks_id,
			"side", planks_id,
			"top", planks_id
		);
		Template.MODEL_SLAB_TOP.apply(
			context, id.extendPath("_top"),
			"bottom", planks_id,
			"side", planks_id,
			"top", planks_id
		);
		Template.MODEL_DELEGATE.apply(
			context, transformId(baseId, "models/item/${orig}_slab"),
			"parent", transformId(baseId, "block/${orig}_slab").toString()
		);

		Template.BLOCKSTATE_PILLAR.apply(
			context, transformId(baseId, "blockstates/${orig}_"+logName),
			"id", transformId(baseId, "block/${orig}_"+logName).toString()
		);
		Template.MODEL_PILLAR.apply(
			context, transformId(baseId, "models/block/${orig}"+logName),
			"top", transformId(baseId, "blocks/${orig}_"+logName+"_top").toString(),
			"side", transformId(baseId, "blocks/${orig}_"+logName).toString()
		);
		Template.MODEL_PILLAR_HORIZONTAL.apply(
			context, transformId(baseId, "models/block/${orig}"+logName+"horizontal"),
			"top", transformId(baseId, "blocks/${orig}_"+logName+"_top").toString(),
			"side", transformId(baseId, "blocks/${orig}_"+logName).toString()
		);
		Template.MODEL_DELEGATE.apply(
			context, transformId(baseId, "models/item/${orig}"+logName),
			"parent", transformId(baseId, "models/block/${orig}"+logName).toString()
		);

		Template.BLOCKSTATE_PILLAR.apply(
			context, transformId(baseId, "blockstates/stripped_${orig}_"+logName),
			"id", transformId(baseId, "block/stripped_${orig}_"+logName).toString()
		);
		Template.MODEL_PILLAR.apply(
			context, transformId(baseId, "models/block/stripped_${orig}"+logName),
			"top", transformId(baseId, "blocks/stripped_${orig}_"+logName+"_top").toString(),
			"side", transformId(baseId, "blocks/stripped_${orig}_"+logName).toString()
		);
		Template.MODEL_PILLAR_HORIZONTAL.apply(
			context, transformId(baseId, "models/block/stripped_${orig}"+logName+"horizontal"),
			"top", transformId(baseId, "blocks/stripped_${orig}_"+logName+"_top").toString(),
			"side", transformId(baseId, "blocks/stripped_${orig}_"+logName).toString()
		);
		Template.MODEL_DELEGATE.apply(
			context, transformId(baseId, "models/item/stripped_${orig}"+logName),
			"parent", transformId(baseId, "models/block/stripped_${orig}"+logName).toString()
		);

		Template.BLOCKSTATE_PILLAR.apply(
			context, transformId(baseId, "blockstates/${orig}_"+woodName),
			"id", transformId(baseId, "block/${orig}_"+woodName).toString()
		);
		Template.MODEL_PILLAR.apply(
			context, transformId(baseId, "models/block/${orig}"+woodName),
			"top", transformId(baseId, "blocks/${orig}_"+woodName+"_top").toString(),
			"side", transformId(baseId, "blocks/${orig}_"+woodName).toString()
		);
		Template.MODEL_PILLAR_HORIZONTAL.apply(
			context, transformId(baseId, "models/block/${orig}"+woodName+"horizontal"),
			"top", transformId(baseId, "blocks/${orig}_"+woodName+"_top").toString(),
			"side", transformId(baseId, "blocks/${orig}_"+woodName).toString()
		);
		Template.MODEL_DELEGATE.apply(
			context, transformId(baseId, "models/item/${orig}"+woodName),
			"parent", transformId(baseId, "models/block/${orig}"+woodName).toString()
		);

		Template.BLOCKSTATE_PILLAR.apply(
			context, transformId(baseId, "blockstates/stripped_${orig}_"+woodName),
			"id", transformId(baseId, "block/stripped_${orig}_"+woodName).toString()
		);
		Template.MODEL_PILLAR.apply(
			context, transformId(baseId, "models/block/stripped_${orig}"+woodName),
			"top", transformId(baseId, "blocks/stripped_${orig}_"+woodName+"_top").toString(),
			"side", transformId(baseId, "blocks/stripped_${orig}_"+woodName).toString()
		);
		Template.MODEL_PILLAR_HORIZONTAL.apply(
			context, transformId(baseId, "models/block/stripped_${orig}"+woodName+"horizontal"),
			"top", transformId(baseId, "blocks/stripped_${orig}_"+woodName+"_top").toString(),
			"side", transformId(baseId, "blocks/stripped_${orig}_"+woodName).toString()
		);
		Template.MODEL_DELEGATE.apply(
			context, transformId(baseId, "models/item/stripped_${orig}"+woodName),
			"parent", transformId(baseId, "models/block/stripped_${orig}"+woodName).toString()
		);

		// TODO: button, pressure plate, door, trapdoor, signs, hanging signs, fence, fence gate, leaves, sapling
	}

	private static void registerContent(
		Identifier baseId,
		MapColor wood,
		MapColor bark,
		BlockSoundGroup sounds,
		boolean createBoat,
		@Nullable Block fungusBaseBlock,
		RegistrationContext context
	) {
		final var baseBlock = QuiltBlockSettings.copyOf(Blocks.OAK_PLANKS).sounds(sounds).mapColor(wood);
		final var nonCollidable = QuiltBlockSettings.copyOf(baseBlock).collidable(false);
		final var nonOpaque = QuiltBlockSettings.copyOf(baseBlock).nonOpaque();
		final var hanging = QuiltBlockSettings
			.copyOf(Blocks.OAK_HANGING_SIGN)
			.sounds(sounds)
			.mapColor(wood);
		final var baseItem = new QuiltItemSettings();
		final var singleStack = new QuiltItemSettings().maxCount(1);
		final var isFungus = fungusBaseBlock != null;

		var blockSetType = new BlockSetTypeBuilder().register(baseId);
		var signType = new WoodTypeBuilder().register(baseId, blockSetType);

		var planks = context.registerBlockWithItem(transformId(baseId, "${orig}_planks"), new Block(baseBlock), baseItem);
		context.registerBlockWithItem(transformId(baseId, "${orig}_stairs"), new StairsBlock(planks.getDefaultState(), baseBlock), baseItem);
		context.registerBlockWithItem(transformId(baseId, "${orig}_slab"), new SlabBlock(baseBlock), baseItem);

		context.registerBlockWithItem(transformId(baseId, "${orig}_button"), new AbstractButtonBlock(nonCollidable, blockSetType, 30, true), baseItem);
		context.registerBlockWithItem(transformId(baseId, "${orig}_pressure_plate"), new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, nonCollidable, blockSetType), baseItem);

		createLogBlocks(context, transformId(baseId, isFungus ? "${orig}_stem" : "${orig}_log"), wood, bark, wood, sounds, baseItem);
		createLogBlocks(context, transformId(baseId, isFungus ? "${orig}_hyphae" : "${orig}_wood"), bark, bark, wood, sounds, baseItem);

		context.registerBlockWithItem(transformId(baseId, "${orig}_door"), new DoorBlock(nonOpaque, blockSetType), baseItem, true);
		context.registerBlockWithItem(transformId(baseId, "${orig}_trapdoor"), new TrapdoorBlock(nonCollidable, blockSetType), baseItem, true);

		var sign = context.registerBlock(transformId(baseId, "${orig}_sign"), new SignBlock(nonCollidable, signType));
		var wall_sign = context.registerBlock(transformId(baseId, "${orig}_wall_sign"), new WallSignBlock(nonCollidable, signType));
		context.registerItem(transformId(baseId, "${orig}_sign"), new SignItem(baseItem, sign, wall_sign));

		var hanging_sign = context.registerBlock(transformId(baseId, "hanging_${orig}_sign"), new CeilingHangingSignBlock(hanging, signType));
		var hanging_wall_sign = context.registerBlock(transformId(baseId, "hanging_${orig}_wall_sign"), new WallHangingSignBlock(hanging, signType));
		context.registerItem(transformId(baseId, "${orig}_hanging_sign"), new HangingSignItem(hanging_sign, hanging_wall_sign, baseItem));

		context.registerBlockWithItem(transformId(baseId, "${orig}_fence"), new FenceBlock(baseBlock), baseItem);
		context.registerBlockWithItem(transformId(baseId, "${orig}_fence_gate"), new FenceGateBlock(baseBlock, signType), baseItem);

		context.registerBlockWithItem(transformId(baseId, isFungus ? "${orig}_wart" : "${orig}_leaves"), createLeavesBlock(isFungus), baseItem);

		context.registerBlockWithItem(transformId(baseId, isFungus ? "${orig}_fungus" : "${orig}_sapling"), isFungus ? createFungusBlock(baseId, fungusBaseBlock) : createSaplingBlock(baseId), baseItem);

		if (createBoat) {
			var boat_key = TerraformBoatTypeRegistry.createKey(baseId);

			var boat_item = context.registerItem(
				transformId(baseId, "${orig}_boat"),
				new TerraformBoatItem(boat_key, false, singleStack)
			);
			TerraformBoatItemHelper.registerBoatDispenserBehavior(
				boat_item,
				boat_key,
				false
			);

			var chest_boat_item = context.registerItem(
				transformId(baseId, "${orig}_chest_boat"),
				new TerraformBoatItem(boat_key, true, singleStack)
			);
			TerraformBoatItemHelper.registerBoatDispenserBehavior(
				chest_boat_item,
				boat_key,
				true
			);

			context.register(
				TerraformBoatTypeRegistry.INSTANCE,
				boat_key,
				new TerraformBoatType.Builder().item(boat_item).chestItem(chest_boat_item).planks(planks.asItem()).build()
			);
		}
	}

	private static SaplingBlock createSaplingBlock(Identifier baseId) {
		return new SaplingBlock(
			new LiberrySaplingGenerator(baseId),
			QuiltBlockSettings
				.copyOf(Blocks.OAK_SAPLING)
				.noCollision()
				.ticksRandomly()
				.breakInstantly()
				.sounds(BlockSoundGroup.GRASS)
		);
	}

	private static FungusBlock createFungusBlock(Identifier baseId, Block base) {
		return new LiberryFungusBlock(
			QuiltBlockSettings
				.copyOf(Blocks.CRIMSON_FUNGUS)
				.breakInstantly()
				.noCollision()
				.sounds(BlockSoundGroup.FUNGUS)
				.offsetType(AbstractBlock.OffsetType.XZ),
			baseId,
			base
		);
	}

	private static void createLogBlocks(RegistrationContext context, Identifier baseId, MapColor top, MapColor side, MapColor stripped, BlockSoundGroup sounds, QuiltItemSettings item) {
		var log = context.registerBlockWithItem(baseId, createLogBlock(top, side, sounds), item);
		var stripped_log = context.registerBlockWithItem(transformId(baseId, "stripped_${orig}"), createLogBlock(stripped, stripped, sounds), item);
		BlockContentRegistries.STRIPPABLE.put(log, stripped_log);
	}

	private static Block createLogBlock(MapColor top, MapColor side, BlockSoundGroup sounds) {
		return new PillarBlock(
			QuiltBlockSettings.copyOf(Blocks.OAK_LOG)
				.strength(2.0F)
				.sounds(sounds)
				.mapColor((state) -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? top : side)
		);
	}

	private static Block createLeavesBlock(boolean isFungus) {
		return
			isFungus ?
				new Block(QuiltBlockSettings.copyOf(Blocks.NETHER_WART_BLOCK)) :
				new LeavesBlock(
					QuiltBlockSettings
						.copyOf(Blocks.OAK_LEAVES)
						.strength(0.2F)
						.ticksRandomly()
						.sounds(BlockSoundGroup.AZALEA_LEAVES)
						.nonOpaque()
						.allowsSpawning((a,b,c, type) -> type == EntityType.OCELOT || type == EntityType.PARROT)
						.suffocates((a,b,c) -> false)
						.blockVision((a,b,c) -> false)
				);
	}

	private static Identifier transformId(Identifier baseId, String type) {
		return baseId.withPath(type.replace("${orig}", baseId.getPath()));
	}

	public static DataGenerator.ContextHolder create(
		Identifier baseId,
		MapColor wood,
		MapColor bark,
		BlockSoundGroup sounds,
		boolean createBoat,
		@Nullable Block fungusBaseBlock
	) {
		var metadata = new HashMap<String, Object>();
		metadata.put("wood_color", wood);
		metadata.put("bark_color", bark);
		metadata.put("sounds", sounds);
		metadata.put("create_boat", createBoat);
		metadata.put("fungus_base_block", fungusBaseBlock);

		return GENERATOR.apply(baseId, metadata);
	}
}
