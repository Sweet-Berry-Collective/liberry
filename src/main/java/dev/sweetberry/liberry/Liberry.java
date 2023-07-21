package dev.sweetberry.liberry;

import dev.sweetberry.liberry.config.LiberryConfig;
import dev.sweetberry.liberry.datagen.DataGeneratorUtils;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;
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

	public static void debugLog(String data) {
		if (LiberryConfig.instance.debug_mode.value() || QuiltLoader.isDevelopmentEnvironment())
			Liberry.logger.info("[Debug Log] "+data);
	}

	public static boolean isExcluded(Identifier generator, String namespace) {
		var exclusions = LiberryConfig.instance.datagen_exclusions.value().get(generator.toString());
		if (exclusions == null)
			return false;
		return exclusions.contains(namespace);
	}

	public static Identifier id(String path) {
		return new Identifier("liberry", path);
	}
}
