package dev.sweetberry.liberry.datagen.blockstate;

import net.minecraft.util.Identifier;

public class BlockStateModel {
	public final String model;
	public final boolean uvlock;

	public BlockStateModel(String model, boolean uvlock) {
		this.model = model;
		this.uvlock = uvlock;
	}

	public BlockStateModel(Identifier model, boolean uvlock) {
		this(model.toString(), uvlock);
	}

	public static class WithX extends BlockStateModel {
		public final float x;

		public WithX(String model, boolean uvlock, float x) {
			super(model, uvlock);
			this.x = x;
		}
		public WithX(Identifier model, boolean uvlock, float x) {
			this(model.toString(), uvlock, x);
		}
	}

	public static class WithY extends BlockStateModel {
		public final float y;

		public WithY(String model, boolean uvlock, float y) {
			super(model, uvlock);
			this.y = y;
		}
		public WithY(Identifier model, boolean uvlock, float y) {
			this(model.toString(), uvlock, y);
		}
	}

	public static class WithZ extends BlockStateModel {
		public final float z;

		public WithZ(String model, boolean uvlock, float z) {
			super(model, uvlock);
			this.z = z;
		}

		public WithZ(Identifier model, boolean uvlock, float z) {
			this(model.toString(), uvlock, z);
		}
	}
}
