package dev.sweetberry.liberry.config;

import dev.sweetberry.liberry.Liberry;
import net.minecraft.util.Identifier;
import org.quiltmc.config.api.ReflectiveConfig;
import org.quiltmc.config.api.annotations.Comment;
import org.quiltmc.config.api.values.TrackedValue;
import org.quiltmc.config.api.values.ValueList;
import org.quiltmc.config.api.values.ValueMap;
import org.quiltmc.loader.api.QuiltLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LiberryConfig extends ReflectiveConfig {
	public static final LiberryConfig instance = ConfigHelper.create(new Identifier("liberry", "config"), LiberryConfig.class);

	@Comment(value = {
		"Represents a list of mods to ignore for a specific data generator.",
		"Example:",
		"datagen_exclusions: {",
		"  \"somemod:some_generator\": [ \"some_modid\" ]",
		"}"
	})
	public final TrackedValue<ValueMap<ValueList<String>>> datagen_exclusions = value(ValueMap.builder(ValueList.create("")).build());

	@Comment("Whether or not to debug print data generation")
	public final TrackedValue<Boolean> debug_mode = value(false);

	public static boolean isExcluded(Identifier generator, String namespace) {
		var exclusions = instance.datagen_exclusions.value().get(generator.toString());
		if (exclusions == null)
			return false;
		return exclusions.contains(namespace);
	}

	public static void debugLog(String data) {
		if (instance.debug_mode.value() || QuiltLoader.isDevelopmentEnvironment())
			Liberry.logger.info(data);
	}

	public static void poke() {}
}
