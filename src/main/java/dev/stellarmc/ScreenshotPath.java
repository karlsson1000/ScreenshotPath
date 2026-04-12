package dev.stellarmc;

import dev.stellarmc.command.ScreenshotPathCommand;
import dev.stellarmc.config.ScreenshotPathConfig;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ScreenshotPath implements ClientModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("ScreenshotPath");

	@Override
	public void onInitializeClient() {
		ScreenshotPathConfig.load();
		ScreenshotPathCommand.register();
		Path target = ScreenshotPathConfig.getScreenshotPath();
		try {
			Files.createDirectories(target);
		} catch (IOException e) {
			LOGGER.error("[ScreenshotPath] Could not create screenshot directory: {}", target, e);
		}
	}
}