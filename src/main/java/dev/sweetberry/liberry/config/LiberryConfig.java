package dev.sweetberry.liberry.config;

import dev.sweetberry.liberry.Liberry;
import net.minecraft.util.Identifier;
import org.quiltmc.config.api.ReflectiveConfig;
import org.quiltmc.config.api.annotations.Comment;
import org.quiltmc.config.api.values.TrackedValue;
import org.quiltmc.config.api.values.ValueList;
import org.quiltmc.config.api.values.ValueMap;

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

	public static boolean isExcluded(Identifier generator, String namespace) {
		var exclusions = instance.datagen_exclusions.value().get(generator.toString());
		if (exclusions == null)
			return false;
		return exclusions.contains(namespace);
	}

	public static void poke() {}
}
