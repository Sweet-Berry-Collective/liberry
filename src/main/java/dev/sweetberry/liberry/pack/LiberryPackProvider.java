package dev.sweetberry.liberry.pack;

import dev.sweetberry.liberry.datagen.DataGeneratorUtils;
import net.minecraft.feature_flags.FeatureFlagBitSet;
import net.minecraft.resource.ResourceType;
import net.minecraft.resource.pack.ResourcePackProfile;
import net.minecraft.resource.pack.ResourcePackProvider;
import net.minecraft.resource.pack.ResourcePackSource;
import net.minecraft.text.Text;

import java.util.function.Consumer;

public class LiberryPackProvider implements ResourcePackProvider {
	@Override
	public void register(Consumer<ResourcePackProfile> profileAdder) {
		profileAdder.accept(ResourcePackProfile.of(
			"liberry:resources",
			Text.translatable("liberry.pack.name"),
			true,
			(name) -> DataGeneratorUtils.PACK,
			new ResourcePackProfile.Info(
				Text.translatable("liberry.pack.desc"),
				15,
				FeatureFlagBitSet.empty()
			),
			ResourceType.CLIENT_RESOURCES,
			ResourcePackProfile.InsertionPosition.TOP,
			true,
			ResourcePackSource.PACK_SOURCE_BUILTIN
		));
		profileAdder.accept(ResourcePackProfile.of(
			"liberry:resources",
			Text.translatable("liberry.pack.name"),
			true,
			(name) -> DataGeneratorUtils.PACK,
			new ResourcePackProfile.Info(
				Text.translatable("liberry.pack.desc"),
				15,
				FeatureFlagBitSet.empty()
			),
			ResourceType.SERVER_DATA,
			ResourcePackProfile.InsertionPosition.TOP,
			true,
			ResourcePackSource.PACK_SOURCE_BUILTIN
		));
	}
}
