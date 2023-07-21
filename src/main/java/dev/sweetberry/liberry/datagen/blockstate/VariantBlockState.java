package dev.sweetberry.liberry.datagen.blockstate;

import dev.sweetberry.liberry.datagen.DataGeneratorUtils;

import java.util.HashMap;

public class VariantBlockState implements BlockStateData {
	private final HashMap<String, BlockStateModel> variants = new HashMap<>();

	public VariantBlockState with(String state, BlockStateModel variant) {
		variants.put(state, variant);
		return this;
	}

	@Override
	public String toString() {
		return DataGeneratorUtils.gson.toJson(this);
	}
}
