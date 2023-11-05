package dev.sweetberry.liberry.datagen.tags;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.sweetberry.liberry.Liberry;
import dev.sweetberry.liberry.datagen.DataGeneratorUtils;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.registry.tag.TagManagerLoader;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.resource.loader.api.InMemoryResourcePack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TagData {
	public static Map<Identifier, List<Identifier>> tags = new HashMap<>();

	public static void put(Identifier type, Identifier... values) {
		var tags = TagData.tags.getOrDefault(type, new ArrayList<>());
		tags.addAll(List.of(values));
		TagData.tags.put(type, tags);
	}

	public static void put(String type, Identifier... values) {
		put(new Identifier(type), values);
	}

	public static <T> void put(TagKey<T> type, Identifier... values) {
		put(type.id().withPrefix(TagManagerLoader.getRegistryDirectory(type.registry()).substring(5) + "/"), values);
	}

	public static void addToPack(InMemoryResourcePack pack) {
		for (var type : tags.keySet()) {
			var value = new JsonObject();
			value.add("replace", new JsonPrimitive(false));
			value.add("values", DataGeneratorUtils.gson.toJsonTree(
				tags.get(type)
					.stream()
					.map(Identifier::toString)
					.toList()
			));
			var id = type.withPrefix("tags/").extendPath(".json");
			var data = DataGeneratorUtils.gson.toJson(value);
			Liberry.debugLog(id.toString() + " ->\n" + data);
			pack.putText(ResourceType.SERVER_DATA, id, data);
		}
	}
}
