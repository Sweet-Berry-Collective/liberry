package dev.sweetberry.liberry.datagen;

import com.google.gson.Gson;
import com.mojang.serialization.Lifecycle;
import dev.sweetberry.liberry.Liberry;
import dev.sweetberry.liberry.datagen.tags.TagData;
import dev.sweetberry.liberry.pack.GeneratedResourcePack;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.registry.*;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

public class DataGeneratorUtils {
	public static final Gson GSON = new Gson();

	public static final GeneratedResourcePack PACK = new GeneratedResourcePack("Liberry Resources", "Generated data using Liberry.");

	public static final Identifier REGISTRY_ID = Liberry.id("data_generators");
	public static final RegistryKey<Registry<DataGenerator>> REGISTRY_KEY = RegistryKey.ofRegistry(REGISTRY_ID);
	public static final Registry<DataGenerator> REGISTRY = new SimpleRegistry<>(REGISTRY_KEY, Lifecycle.stable(), false);

	public static void registerRegistry() {
		// Hacky things to get it to compile. :3
		Registry.register((Registry<Registry<?>>)Registries.REGISTRY, REGISTRY_ID, REGISTRY);
	}

	public static DataGenerator registerGenerator(DataGenerator generator) {
		System.out.println(generator.id);
		return Registry.register(REGISTRY, generator.id, generator);
	}

	public static void registerReloaders() {
		registerReloader(ResourceType.CLIENT_RESOURCES);
		registerReloader(ResourceType.SERVER_DATA);
	}

	private static void registerReloader(ResourceType type) {
		ResourceManagerHelper.get(type).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
			@Override
			public Identifier getFabricId() {
				return Liberry.id("resources");
			}

			@Override
			public void reload(ResourceManager manager) {
				PACK.clear(type);
				REGISTRY.forEach(generator -> {
					System.out.println(generator.id);
					generator.resourceContexts.forEach(resourceContext ->
						resourceContext.resources
							.forEach(it -> {
								if (it.type() == type)
									it.applyTo(manager, PACK);
							})
					);
				});
				if (type == ResourceType.SERVER_DATA)
					TagData.addToPack(PACK);
			}
		});
	}
}
