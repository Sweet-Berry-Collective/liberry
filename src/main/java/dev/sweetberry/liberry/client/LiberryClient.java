package dev.sweetberry.liberry.client;

import dev.sweetberry.liberry.datagen.DataGeneratorUtils;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.block.extensions.api.client.BlockRenderLayerMap;

public class LiberryClient implements ClientModInitializer {
	@Override
	public void onInitializeClient(ModContainer mod) {
		DataGeneratorUtils.REGISTRY.forEach(generator ->
			generator.registrationContexts.forEach(context ->
				context.entries.forEach(entry -> {
					if (entry.shouldBeTransparent && entry.value instanceof Block block)
						BlockRenderLayerMap.put(RenderLayer.getCutout(), block);
				})
			)
		);
	}
}
