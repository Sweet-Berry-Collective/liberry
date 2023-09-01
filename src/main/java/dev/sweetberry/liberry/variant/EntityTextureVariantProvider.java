package dev.sweetberry.liberry.variant;

import com.mojang.serialization.Lifecycle;
import dev.sweetberry.liberry.Liberry;
import net.minecraft.entity.Entity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface EntityTextureVariantProvider {
	Identifier REGISTRY_ID = Liberry.id("mob_variants");
	RegistryKey<Registry<EntityTextureVariantProvider>> REGISTRY_KEY = RegistryKey.ofRegistry(REGISTRY_ID);
	Registry<EntityTextureVariantProvider> REGISTRY = new SimpleRegistry<>(REGISTRY_KEY, Lifecycle.stable(), false);
	EntityTextureVariantProvider DEFAULT = Registry.register(REGISTRY, Liberry.id("default"), entity -> null);

	@Nullable Identifier getTexturePathForEntity(Entity entity);

	static void registerRegistry() {
		// Hacky things to get it to compile. :3
		Registry.register((Registry<Registry<?>>) Registries.REGISTRY, REGISTRY_ID, REGISTRY);
	}
}
