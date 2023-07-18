package dev.sweetberry.liberry;

import dev.sweetberry.liberry.config.LiberryConfig;
import dev.sweetberry.liberry.datagen.DataGeneratorUtils;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Liberry implements ModInitializer {
	public static final Logger logger = LoggerFactory.getLogger("liberry");
	@Override
	public void onInitialize(ModContainer mod) {
		LiberryConfig.poke();
		DataGeneratorUtils.registerRegistry();
		DataGeneratorUtils.registerReloaders();
	}
}
