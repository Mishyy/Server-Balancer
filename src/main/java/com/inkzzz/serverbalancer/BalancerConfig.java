package com.inkzzz.serverbalancer;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

public final class BalancerConfig {

	private final ServerBalancerPlugin plugin;
	private final Set<String> servers;
	private final File file;
	private Configuration configuration;

	public BalancerConfig(final @NotNull ServerBalancerPlugin plugin) {
		this.plugin = plugin;
		this.servers = new HashSet<>();
		this.file = new File(plugin.getDataFolder(), "config.yml");

		try {
			if (!plugin.getDataFolder().exists() && !plugin.getDataFolder().mkdir()) return;
			if (!file.exists()) {
				try (final InputStream inputStream = plugin.getResourceAsStream("config.yml")) {
					Files.copy(inputStream, file.toPath());
				}
			}
		} catch (IOException e) {
			plugin.getLogger().log(Level.SEVERE, "Failed to make config.", e);
		}
	}

	public @NotNull Set<String> getServers() {
		return servers;
	}

	public @NotNull String getReloadMessage() {
		return configuration.getString("reload-message");
	}

	public void load() {
		try {
			this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
		} catch (IOException e) {
			plugin.getLogger().log(Level.WARNING, "Failed to load config.", e);
			return;
		}

		servers.clear();
		for (final String server : configuration.getStringList("servers")) {
			if (plugin.getProxy().getServerInfo(server) != null) {
				servers.add(server);
			}
		}
	}

	public void save() {
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
		} catch (IOException e) {
			plugin.getLogger().log(Level.WARNING, "Failed to save config.", e);
		}
	}

}