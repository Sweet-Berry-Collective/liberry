package dev.sweetberry.liberry.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.sweetberry.liberry.Liberry;
import dev.sweetberry.liberry.variant.EntityTextureVariantProvider;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class EntityVariantComponent implements AutoSyncedComponent {
	public Identifier variant = Liberry.id("default");

	@Override
	public void readFromNbt(NbtCompound tag) {
		variant = new Identifier(tag.getString("variant"));
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putString("variant", variant.toString());
	}

	public @Nullable Identifier getVariantTexture(Entity entity) {
		var provider = EntityTextureVariantProvider.REGISTRY.get(variant);
		if (provider == null) {
			return null;
		}
		return provider.getTexturePathForEntity(entity);
	}
}
