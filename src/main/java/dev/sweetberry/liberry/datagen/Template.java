package dev.sweetberry.liberry.datagen;

import dev.sweetberry.liberry.Liberry;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import java.util.Map;

public record Template(Identifier id, ResourceType type) {
	/**
	 * Properties:
	 * <br>
	 * - all: The ID for the texture (ex: modid:block/some_block)
	 * */
	public static final Template MODEL_BLOCK = new Template(Liberry.id("datagen/model/block"), ResourceType.CLIENT_RESOURCES);

	/**
	 * Properties:
	 * <br>
	 * - parent: The ID for the parent model (ex: modid:block/some_block)
	 * */
	public static final Template MODEL_DELEGATE = new Template(Liberry.id("datagen/model/delegate"), ResourceType.CLIENT_RESOURCES);

	/**
	 * Properties:
	 * <br>
	 * - id: The ID for the base model (ex: modid:block/some_block)
	 * */
	public static final Template BLOCKSTATE_STAIRS = new Template(Liberry.id("datagen/blockstate/stairs"), ResourceType.CLIENT_RESOURCES);

	/**
	 * Properties:
	 * <br>
	 * - bottom: Texture for bottom <br>
	 * - side: Texture for side <br>
	 * - top: Texture for top
	 * */
	public static final Template MODEL_STAIRS = new Template(Liberry.id("datagen/model/stairs"), ResourceType.CLIENT_RESOURCES);

	/**
	 * Properties:
	 * <br>
	 * - bottom: Texture for bottom <br>
	 * - side: Texture for side <br>
	 * - top: Texture for top
	 * */
	public static final Template MODEL_STAIRS_INNER = new Template(Liberry.id("datagen/model/stairs_inner"), ResourceType.CLIENT_RESOURCES);

	/**
	 * Properties:
	 * <br>
	 * - bottom: Texture for bottom <br>
	 * - side: Texture for side <br>
	 * - top: Texture for top
	 * */
	public static final Template MODEL_STAIRS_OUTER = new Template(Liberry.id("datagen/model/stairs_outer"), ResourceType.CLIENT_RESOURCES);

	/**
	 * Properties:
	 * <br>
	 * - half: Model ID for when it's only half (ex: modid:block/some_slab) <br>
	 * - full: Model ID for when it's full (ex: modid:block/some_planks) <br>
	 * */
	public static final Template BLOCKSTATE_SLAB = new Template(Liberry.id("datagen/blockstate/slab"), ResourceType.CLIENT_RESOURCES);

	/**
	 * Properties:
	 * <br>
	 * - bottom: Texture for bottom <br>
	 * - side: Texture for side <br>
	 * - top: Texture for top
	 * */
	public static final Template MODEL_SLAB = new Template(Liberry.id("datagen/model/slab"), ResourceType.CLIENT_RESOURCES);

	/**
	 * Properties:
	 * <br>
	 * - bottom: Texture for bottom <br>
	 * - side: Texture for side <br>
	 * - top: Texture for top
	 * */
	public static final Template MODEL_SLAB_TOP = new Template(Liberry.id("datagen/model/slab_top"), ResourceType.CLIENT_RESOURCES);

	/**
	 * Properties:
	 * <br>
	 * - id: The ID for the base model (ex: modid:block/some_block)
	 * */
	public static final Template BLOCKSTATE_PILLAR = new Template(Liberry.id("datagen/blockstate/pillar"), ResourceType.CLIENT_RESOURCES);

	/**
	 * Properties:
	 * <br>
	 * - side: Texture for side <br>
	 * - top: Texture for top
	 * */
	public static final Template MODEL_PILLAR = new Template(Liberry.id("datagen/model/pillar"), ResourceType.CLIENT_RESOURCES);

	/**
	 * Properties:
	 * <br>
	 * - side: Texture for side <br>
	 * - top: Texture for top
	 * */
	public static final Template MODEL_PILLAR_HORIZONTAL = new Template(Liberry.id("datagen/model/pillar_horizontal"), ResourceType.CLIENT_RESOURCES);

	/**
	 * Properties:
	 * <br>
	 * - id: The ID for the base model (ex: modid:block/some_block)
	 * */
	public static final Template BLOCKSTATE_BUTTON = new Template(Liberry.id("datagen/blockstate/button"), ResourceType.CLIENT_RESOURCES);



	public void apply(ResourceContext context, Identifier path, String... properties) {
		context.template(type, id, path, properties);
	}

	public void apply(ResourceContext context, Identifier path, Map<String, String> properties) {
		context.template(type, id, path, properties);
	}
}
