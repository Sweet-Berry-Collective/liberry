package dev.sweetberry.liberry;

import dev.sweetberry.liberry.config.LiberryConfig;
import dev.sweetberry.liberry.datagen.DataGeneratorUtils;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Liberry implements ModInitializer {
	public static final Logger logger = LoggerFactory.getLogger("liberry");
	@Override
	public void onInitialize() {
		try {
			LiberryConfig.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		DataGeneratorUtils.registerRegistry();
		DataGeneratorUtils.registerReloaders();
	}

	public static void debugLog(String data) {
		if (LiberryConfig.isDebugMode())
			Liberry.logger.info("[Debug Log] "+data);
	}

	public static boolean isExcluded(Identifier generator, String namespace) {
		var exclusions = LiberryConfig.getExclusions().get(generator.toString());
		if (exclusions == null)
			return false;
		return exclusions.contains(namespace);
	}

	public static Identifier id(String path) {
		return new Identifier("liberry", path);
	}
}
