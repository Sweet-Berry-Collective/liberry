package dev.sweetberry.liberry.datagen;

import dev.sweetberry.liberry.Liberry;
import dev.sweetberry.liberry.config.LiberryConfig;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.resource.loader.api.InMemoryResourcePack;
import org.quiltmc.qsl.resource.loader.api.ResourcePackRegistrationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResourceContext {
	public List<DatagenResource> resources = new ArrayList<>();

	public void string(ResourceType type, Identifier path, String value) {
		resources.add(new BasicStringResource(type, path, value));
	}

	public void template(ResourceType type, Identifier template, Identifier path, Map<String, String> properties) {
		resources.add(new TemplatedResource(type, path, template, properties));
	}

	public interface DatagenResource {
		ResourceType type();

		void applyTo(
			ResourcePackRegistrationContext context,
			InMemoryResourcePack pack
		);
	}

	public record BasicStringResource(
		ResourceType type,
		Identifier path,
		String data
	) implements DatagenResource {
		@Override
		public void applyTo(
			ResourcePackRegistrationContext context,
			InMemoryResourcePack pack
		) {
			var path = this.path.extendPath(".json");
			LiberryConfig.debugLog(path + " -> \n" + data);
			pack.putText(type(), path, data);
		}
	}

	public record TemplatedResource(
		ResourceType type,
		Identifier path,
		Identifier template,
		Map<String, String> properties
	) implements DatagenResource {
		@Override
		public void applyTo(ResourcePackRegistrationContext context, InMemoryResourcePack pack) {
			try {
				var templateString = new String(context.resourceManager().open(template.extendPath(".json")).readAllBytes());
				for (var key : properties.keySet())
					templateString = templateString.replace("${" + key + "}", properties.get(key));
				var path = this.path.extendPath(".json");
				LiberryConfig.debugLog(path + " -> \n" + templateString);
				pack.putText(type(), path, templateString);
			} catch (IOException err) {
				Liberry.logger.atError().setCause(err).log("Unable to find template: " + template);
			}
		}
	}
}
