package dev.sweetberry.liberry.pack;

import com.google.gson.JsonObject;
import net.minecraft.resource.ResourceIoSupplier;
import net.minecraft.resource.ResourceType;
import net.minecraft.resource.pack.ResourcePack;
import net.minecraft.resource.pack.metadata.ResourceMetadataReader;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class GeneratedResourcePack implements ResourcePack {
	private final String NAME;
	private final String DESCRIPTION;
	private final Map<Identifier, Supplier<byte[]>> CLIENT = new HashMap<>();
	private final Map<Identifier, Supplier<byte[]>> SERVER = new HashMap<>();

	public GeneratedResourcePack(String name, String description) {
		NAME = name;
		DESCRIPTION = description;
	}

	@Nullable
	private static <T> ResourceIoSupplier<InputStream> getFile(Map<T, Supplier<byte[]>> map, T t) {
		var file = map.get(t);
		if (file == null)
			return null;
		var bytes = file.get();
		if (bytes == null)
			return null;
		return () -> new ByteArrayInputStream(bytes);
	}

	public Map<Identifier, Supplier<byte[]>> getFilesForType(ResourceType type) {
		if (type == ResourceType.CLIENT_RESOURCES)
			return CLIENT;
		return SERVER;
	}

	public void clear() {
		CLIENT.clear();
		SERVER.clear();
	}

	public void clear(ResourceType type) {
		getFilesForType(type).clear();
	}

	public void put(ResourceType type, Identifier id, Supplier<byte[]> data) {
		getFilesForType(type).put(id, data);
	}

	public void putText(ResourceType type, Identifier id, String text) {
		put(type, id, () -> text.getBytes(Charset.defaultCharset()));
	}

	@Nullable
	@Override
	public ResourceIoSupplier<InputStream> openRoot(String... path) {
		return null;
	}

	@Nullable
	@Override
	public ResourceIoSupplier<InputStream> open(ResourceType type, Identifier id) {
		return getFile(getFilesForType(type), id);
	}

	@Override
	public void listResources(ResourceType type, String namespace, String startingPath, ResourceConsumer consumer) {
		getFilesForType(type).entrySet().stream()
			.filter(entry ->
				entry.getKey().getNamespace().equals(namespace) && entry.getKey().getPath().startsWith(startingPath)
			).forEach(entry -> {
				var bytes = entry.getValue().get();
				if (bytes != null)
					consumer.accept(entry.getKey(), () -> new ByteArrayInputStream(bytes));
			});
	}

	@Override
	public Set<String> getNamespaces(ResourceType type) {
		return getFilesForType(type)
			.keySet()
			.stream()
			.map(Identifier::getNamespace)
			.collect(Collectors.toSet());
	}

	@Nullable
	@Override
	public <T> T parseMetadata(ResourceMetadataReader<T> metaReader) {
		return null;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void close() {}
}
