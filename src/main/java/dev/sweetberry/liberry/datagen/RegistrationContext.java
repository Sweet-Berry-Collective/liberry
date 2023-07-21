package dev.sweetberry.liberry.datagen;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.ArrayList;
import java.util.List;

public class RegistrationContext {
	public List<RegistryEntryHolder<?>> entries = new ArrayList<>();

	public Block registerBlockWithItem(Identifier id, Block block, QuiltItemSettings settings) {
		return registerBlockWithItem(id, block, settings, false);
	}

	public Block registerBlockWithItem(Identifier id, Block block, QuiltItemSettings settings, boolean shouldBeTransparent) {
		registerBlock(id, block, shouldBeTransparent);
		registerItem(id, new BlockItem(block, settings));
		return block;
	}

	public Block registerBlock(Identifier id, Block block) {
		return registerBlock(id, block, false);
	}

	public Block registerBlock(Identifier id, Block block, boolean shouldBeTransparent) {
		return register(Registries.BLOCK, id, block, shouldBeTransparent);
	}

	public Item registerItem(Identifier id, Item item) {
		return register(Registries.ITEM, id, item);
	}

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
