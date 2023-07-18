package dev.sweetberry.liberry.test;

import dev.sweetberry.liberry.datagen.DataGenerator;
import dev.sweetberry.liberry.datagen.DataGeneratorUtils;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.HashMap;

public class LiberryTestMod implements ModInitializer {
	@Override
	public void onInitialize(ModContainer mod) {
		var id = new Identifier("liberry_test", "test_generator");
		var generator = new DataGenerator(id, (baseId, metadata, registrationContext, resourceContext) -> {
			var block = new Block(QuiltBlockSettings.copyOf(Blocks.OAK_PLANKS));
			var block_item = new BlockItem(block, new QuiltItemSettings());
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
			var map = new HashMap<String, String>();
			map.put("model_id", "liberry_test:block/"+block_id.getPath());
			resourceContext.template(ResourceType.CLIENT_RESOURCES, new Identifier("liberry_test", "template/blockstate"), blockstate_id, map);
			resourceContext.template(ResourceType.CLIENT_RESOURCES, new Identifier("liberry_test", "template/item_model"), item_model_id, map);
		});

		Registry.register(DataGeneratorUtils.REGISTRY, id, generator);

		generator.exclude("owo");

		generator.apply(new Identifier("owo", "uwu"));
		generator.apply(new Identifier("uwu", "owo"));
	}
}
