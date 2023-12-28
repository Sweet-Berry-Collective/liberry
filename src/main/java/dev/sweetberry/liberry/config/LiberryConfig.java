package dev.sweetberry.liberry.config;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LiberryConfig {
	private static final LiberryConfig INSTANCE = new LiberryConfig();

	private final Map<String, List<String>> datagenExclusions = new HashMap<>();

	private boolean debugMode = false;

	public static Map<String, List<String>> getExclusions() {
		return INSTANCE.datagenExclusions;
	}
	public static boolean isDebugMode() {
		return INSTANCE.debugMode || FabricLoader.getInstance().isDevelopmentEnvironment();
	}

	public static Path getConfigPath() {
		return FabricLoader.getInstance().getConfigDir().resolve("liberry.json5");
	}

	public static void load() throws IOException {
		var path = getConfigPath();
		var f = path.toFile();
		if (!f.exists())
			write();
		// TODO
	}

	public static void write() throws IOException {
		// TODO
	}
}
