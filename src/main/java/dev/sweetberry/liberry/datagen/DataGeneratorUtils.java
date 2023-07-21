package dev.sweetberry.liberry.datagen;

import com.google.gson.Gson;
import com.mojang.serialization.Lifecycle;
import dev.sweetberry.liberry.Liberry;
import dev.sweetberry.liberry.datagen.tags.TagData;
import net.minecraft.registry.*;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.resource.loader.api.InMemoryResourcePack;
import org.quiltmc.qsl.resource.loader.api.ResourceLoader;

public class DataGeneratorUtils {
	public static final Gson gson = new Gson();

	public static final InMemoryResourcePack CLIENT_PACK = new InMemoryResourcePack.Named("Liberry Client Resources");
	public static final InMemoryResourcePack SERVER_PACK = new InMemoryResourcePack.Named("Liberry Server Resources");

	public static final Identifier REGISTRY_ID = Liberry.id("data_generators");
	public static final RegistryKey<Registry<DataGenerator>> REGISTRY_KEY = RegistryKey.ofRegistry(REGISTRY_ID);
	public static final Registry<DataGenerator> REGISTRY = new SimpleRegistry<>(REGISTRY_KEY, Lifecycle.stable(), false);

	public static void registerRegistry() {
		// Hacky things to get it to compile. :3
		Registry.register((Registry<Registry<?>>)Registries.REGISTRY, REGISTRY_ID, REGISTRY);
	}

	public static DataGenerator registerGenerator(DataGenerator generator) {
		return Registry.register(REGISTRY, generator.id, generator);
	}

	public static void registerReloaders() {
		registerReloader(ResourceType.CLIENT_RESOURCES, CLIENT_PACK);
		registerReloader(ResourceType.SERVER_DATA, SERVER_PACK);
	}

	private static void registerReloader(ResourceType type, InMemoryResourcePack pack) {
		ResourceLoader.get(type).getRegisterDefaultResourcePackEvent().register(context -> {
			pack.clearResources();
			REGISTRY.forEach(generator ->
				generator.resourceContexts.forEach(resourceContext ->
					resourceContext.resources
						.forEach(it -> {
							if (it.type() == type)
								it.applyTo(context, pack);
						})
				)
			);
			if (type == ResourceType.SERVER_DATA)
				TagData.addToPack(pack);
			context.addResourcePack(pack);
		});
	}
}
