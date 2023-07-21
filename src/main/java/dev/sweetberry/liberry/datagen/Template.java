package dev.sweetberry.liberry.datagen;

import dev.sweetberry.liberry.Liberry;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import java.util.Map;

public record Template(Identifier id, ResourceType type) {
	/**
	 * Properties:
	 * <br>
	 * - id: The ID for the base model (ex: modid:block/some_block)
	 * */
	public static final Template BLOCKSTATE_STAIRS = new Template(Liberry.id("datagen/blockstate/stairs"), ResourceType.CLIENT_RESOURCES);
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


	public void apply(ResourceContext context, Identifier path, String... properties) {
		context.template(type, id, path, properties);
	}

	public void apply(ResourceContext context, Identifier path, Map<String, String> properties) {
		context.template(type, id, path, properties);
	}
}
