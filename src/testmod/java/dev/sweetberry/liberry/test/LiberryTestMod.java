package dev.sweetberry.liberry.test;

import dev.sweetberry.liberry.datagen.DataGenerator;
import dev.sweetberry.liberry.datagen.DataGeneratorUtils;
import dev.sweetberry.liberry.datagen.Template;
import dev.sweetberry.liberry.datagen.blockstate.BlockStateModel;
import dev.sweetberry.liberry.datagen.blockstate.VariantBlockState;
import dev.sweetberry.liberry.datagen.builtin.WoodType;
import dev.sweetberry.liberry.datagen.tags.TagData;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.resource.ResourceType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class LiberryTestMod implements ModInitializer {
	@Override
	public void onInitialize() {
		System.out.println("test 1");
		var id = new Identifier("liberry_test", "test_generator");
		var generator = new DataGenerator(id, (baseId, metadata, registrationContext, resourceContext) -> {
			var block = new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS));
			var block_item = new BlockItem(block, new FabricItemSettings());
			var block_id = new Identifier("liberry_test", baseId.toString().replace(':', '/'));

			registrationContext.register(Registries.BLOCK, block_id, block, true);
			registrationContext.register(Registries.ITEM, block_id, block_item);

			var model_id = new Identifier(block_id.getNamespace(), "models/block/"+block_id.getPath());
			var item_model_id = new Identifier(block_id.getNamespace(), "models/item/"+block_id.getPath());
			var blockstate_id = new Identifier(block_id.getNamespace(), "blockstates/"+block_id.getPath());
			resourceContext.string(ResourceType.CLIENT_RESOURCES, model_id, """
				{
					"parent": "block/cube_all",
					"textures": {
						"all": "block/dirt"
					}
				}
				""");
			var block_model_id = "liberry_test:block/"+block_id.getPath();
			resourceContext.blockstate(blockstate_id, new VariantBlockState().with("", new BlockStateModel(block_model_id, false)));
			Template.MODEL_DELEGATE.apply(resourceContext, item_model_id,
				"parent", block_model_id
			);

			TagData.put(BlockTags.PICKAXE_MINEABLE, block_id);
		});
		System.out.println("test 2");

		Registry.register(DataGeneratorUtils.REGISTRY, id, generator);

		System.out.println("test 3");
		generator.exclude("owo");

		generator.apply(new Identifier("owo", "uwu"));
		generator.apply(new Identifier("uwu", "owo"));

		System.out.println("test 4");

		WoodType.create(
			new Identifier("liberry_test", "test_wood"),
			MapColor.DIRT,
			MapColor.DIAMOND,
			BlockSoundGroup.AMETHYST_BLOCK,
			false,
			null
		);

		System.out.println("test 5");
	}
}
