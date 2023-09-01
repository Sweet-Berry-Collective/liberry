package dev.sweetberry.liberry.datagen;

import dev.sweetberry.liberry.Liberry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

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

	public @Nullable ContextHolder apply(Identifier baseId, Map<String, ?> metadata) {
		if (Liberry.isExcluded(id, baseId.getNamespace())) {
			Liberry.debugLog("Excluding id "+baseId+" for "+id+" due to config");
			return null;
		}
		if (exclusions.contains(baseId.getNamespace())) {
			Liberry.debugLog("Excluding id "+baseId+" for "+id+" due to mod author request");
			return null;
		}
		var registrationContext = new RegistrationContext();
		var resourceContext = new ResourceContext();
		function.onInstantiate(baseId, metadata, registrationContext, resourceContext);
		registrationContexts.add(registrationContext);
		resourceContexts.add(resourceContext);
		return new ContextHolder(registrationContext, resourceContext);
	}

	public @Nullable ContextHolder apply(Identifier id) {
		return apply(id, new HashMap<>());
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

	public record ContextHolder(RegistrationContext registrationContext, ResourceContext resourceContext) {

	}
}
