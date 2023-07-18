package dev.sweetberry.liberry.config;

import net.minecraft.util.Identifier;
import org.quiltmc.config.api.ReflectiveConfig;
import org.quiltmc.loader.api.config.v2.QuiltConfig;

public class ConfigHelper {
	public static <C extends ReflectiveConfig> C create(Identifier id, Class<C> clazz) {
		return QuiltConfig.create(id.getNamespace(), id.getPath(), builder -> builder.format("json5"), clazz);
	}
}
