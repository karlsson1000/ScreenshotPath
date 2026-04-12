package dev.stellarmc.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.stellarmc.ScreenshotPath;
import net.fabricmc.loader.api.FabricLoader;
import java.io.*;
import java.nio.file.*;

public class ScreenshotPathConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("screenshot-path.json");
    private static final Path DEFAULT_PATH = Path.of(System.getProperty("user.home")).resolve("Pictures").resolve("Minecraft");

    private static String screenshotPath = DEFAULT_PATH.toString();

    public static void load() {
        if (!Files.exists(CONFIG_FILE)) {
            save();
            return;
        }
        try (Reader r = Files.newBufferedReader(CONFIG_FILE)) {
            Data d = new Gson().fromJson(r, Data.class);
            if (d != null && d.screenshot_path != null && !d.screenshot_path.isBlank()) {
                screenshotPath = d.screenshot_path;
            }
        } catch (IOException e) {
            ScreenshotPath.LOGGER.error("[ScreenshotPath] Failed to read config, using default path.", e);
        }
    }

    public static void setPath(String path) {
        screenshotPath = path;
        save();
    }

    private static void save() {
        try (Writer w = Files.newBufferedWriter(CONFIG_FILE)) {
            Data d = new Data();
            d.screenshot_path = screenshotPath;
            GSON.toJson(d, w);
        } catch (IOException e) {
            ScreenshotPath.LOGGER.error("[ScreenshotPath] Failed to save config.", e);
        }
    }

    public static Path getScreenshotPath() {
        try {
            Path path = Path.of(screenshotPath).toAbsolutePath();
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            return path;
        } catch (InvalidPathException e) {
            ScreenshotPath.LOGGER.warn("[ScreenshotPath] Invalid path in config, falling back to default.");
            return DEFAULT_PATH.toAbsolutePath();
        } catch (IOException e) {
            ScreenshotPath.LOGGER.warn("[ScreenshotPath] Could not create screenshot directory, falling back to default.");
            return DEFAULT_PATH.toAbsolutePath();
        }
    }

    private static class Data {
        String screenshot_path;
    }
}