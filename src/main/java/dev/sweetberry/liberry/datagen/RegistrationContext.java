package dev.sweetberry.liberry.datagen;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class RegistrationContext {
	public List<RegistryEntryHolder<?>> entries = new ArrayList<>();

	public <TValue> TValue register(Registry<TValue> registry, Identifier id, TValue value) {
		var entry = new RegistryEntryHolder<>(id, value);
		entries.add(entry);
		return Registry.register(registry, id, value);
	}

	public <TValue> TValue register(Registry<TValue> registry, Identifier id, TValue value, boolean shouldBeTransparent) {
		var entry = new RegistryEntryHolder<>(id, value, shouldBeTransparent);
		entries.add(entry);
		return Registry.register(registry, id, value);
	}

	public static class RegistryEntryHolder<TValue> {
		public final Identifier id;
		public final TValue value;
		public final boolean shouldBeTransparent;

		public RegistryEntryHolder(Identifier id, TValue value, boolean shouldBeTransparent) {
			this.id = id;
			this.value = value;
			this.shouldBeTransparent = shouldBeTransparent;
		}

		public RegistryEntryHolder(Identifier id, TValue value) {
			this(id, value, false);
		}
	}
}
