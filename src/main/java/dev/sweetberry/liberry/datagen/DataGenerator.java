package dev.sweetberry.liberry.datagen;

import dev.sweetberry.liberry.Liberry;
import dev.sweetberry.liberry.config.LiberryConfig;
import net.minecraft.registry.Registry;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataGenerator {
	public final InstantiationFunction function;
	public final Identifier id;

	public List<RegistrationContext> registrationContexts = new ArrayList<>();
	public List<ResourceContext> resourceContexts = new ArrayList<>();
	public List<String> exclusions = new ArrayList<>();

	public DataGenerator(Identifier id, InstantiationFunction function) {
		this.function = function;
		this.id = id;
	}

	public void exclude(String modid) {
		exclusions.add(modid);
	}

	public void apply(Identifier baseId, Map<String, ?> metadata) {
		if (LiberryConfig.isExcluded(id, baseId.getNamespace())) {
			LiberryConfig.debugLog("Excluding id "+baseId+" for "+id+" due to config");
			return;
		}
		if (exclusions.contains(baseId.getNamespace())) {
			LiberryConfig.debugLog("Excluding id "+baseId+" for "+id+" due to mod author request");
			return;
		}
		var registrationContext = new RegistrationContext();
		var resourceContext = new ResourceContext();
		function.onInstantiate(baseId, metadata, registrationContext, resourceContext);
		registrationContexts.add(registrationContext);
		resourceContexts.add(resourceContext);
	}

	public void apply(Identifier id) {
		apply(id, new HashMap<>());
	}

	@FunctionalInterface
	public interface InstantiationFunction {
		void onInstantiate(
			Identifier baseId,
			Map<String, ?> metadata,
			RegistrationContext registrationContext,
			ResourceContext resourceContext
		);
	}
}
