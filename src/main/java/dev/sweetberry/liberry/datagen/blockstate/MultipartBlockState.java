package dev.sweetberry.liberry.datagen.blockstate;

import dev.sweetberry.liberry.datagen.DataGeneratorUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class MultipartBlockState implements BlockStateData {
	private final ArrayList<MultipartClause> multipart = new ArrayList<>();

	public MultipartBlockState with(HashMap<String, Object> when, BlockStateModel apply) {
		multipart.add(new MultipartClause(apply, when));
		return this;
	}

	@Override
	public String toString() {
		return DataGeneratorUtils.gson.toJson(this);
	}

	private static final class MultipartClause {
		public final BlockStateModel apply;
		public final HashMap<String, Object> when;

		private MultipartClause(BlockStateModel apply, HashMap<String, Object> when) {
			this.apply = apply;
			this.when = when;
		}
	}
}
