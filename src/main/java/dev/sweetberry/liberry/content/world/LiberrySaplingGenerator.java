package dev.sweetberry.liberry.content.world;

import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class LiberrySaplingGenerator extends SaplingGenerator {
	public final RegistryKey<ConfiguredFeature<?, ?>> BEES;
	public final RegistryKey<ConfiguredFeature<?, ?>> NO_BEES;

	public LiberrySaplingGenerator(RegistryKey<ConfiguredFeature<?, ?>> noBees, RegistryKey<ConfiguredFeature<?, ?>> bees) {
		BEES = bees;
		NO_BEES = noBees;
	}

	public LiberrySaplingGenerator(Identifier baseId) {
		this(getId(baseId, false), getId(baseId, true));
	}

	private static RegistryKey<ConfiguredFeature<?, ?>> getId(Identifier baseId, boolean bees) {
		return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, baseId.extendPath(bees ? "_bees" : ""));
	}

	@Nullable
	@Override
	protected RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(RandomGenerator random, boolean bees) {
		return bees ? BEES : NO_BEES;
	}
}
