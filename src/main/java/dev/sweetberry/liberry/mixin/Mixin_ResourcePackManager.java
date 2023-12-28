package dev.sweetberry.liberry.mixin;

import dev.sweetberry.liberry.pack.LiberryPackProvider;
import net.minecraft.resource.pack.ResourcePackManager;
import net.minecraft.resource.pack.ResourcePackProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;
import java.util.Set;

@Mixin(ResourcePackManager.class)
public class Mixin_ResourcePackManager {
	@Shadow @Final @Mutable
	private Set<ResourcePackProvider> providers;

	@Inject(
		method = "<init>",
		at = @At("RETURN")
	)
	private void liberry$addPacks(ResourcePackProvider[] providers, CallbackInfo ci) {
		HashSet<ResourcePackProvider> set = new HashSet<>(this.providers);
		set.add(new LiberryPackProvider());
		this.providers = set;
	}
}
