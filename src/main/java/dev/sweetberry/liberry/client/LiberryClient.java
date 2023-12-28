package dev.sweetberry.liberry.client;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import dev.sweetberry.liberry.datagen.DataGeneratorUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;

public class LiberryClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		DataGeneratorUtils.REGISTRY.forEach(generator ->
			generator.registrationContexts.forEach(context ->
				context.entries.forEach(entry -> {
					if (entry.shouldBeTransparent && entry.value instanceof Block block)
						BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
					if (entry.value instanceof TerraformBoatType)
						TerraformBoatClientHelper.registerModelLayers(entry.id, false);
				})
			)
		);
	}
}
