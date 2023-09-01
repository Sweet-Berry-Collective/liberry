package dev.sweetberry.liberry.component;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.sweetberry.liberry.Liberry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.BoatEntity;

public class LiberryComponents implements EntityComponentInitializer {
	public static final ComponentKey<EntityVariantComponent> ENTITY_VARIANTS = ComponentRegistryV3.INSTANCE.getOrCreate(Liberry.id("entity_variants"), EntityVariantComponent.class);

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registerForEntity(registry, BoatEntity.class);
	}

	/**
	 * Use this to add variants to more entities.
	 * <br>
	 * You need to bring your own rendering because I do not want to touch ASM.
	 * <br>
	 * You can take a look at the boat for an example.
	 * */
	public static void registerForEntity(EntityComponentFactoryRegistry registry, Class<? extends Entity> entity) {
		registry.registerFor(entity, ENTITY_VARIANTS, e -> new EntityVariantComponent());
	}
}
