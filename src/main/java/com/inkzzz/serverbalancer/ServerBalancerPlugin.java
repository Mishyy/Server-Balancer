package com.inkzzz.serverbalancer;

import net.md_5.bungee.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Luke Denham on 12/03/2016.
 */
public final class ServerBalancerPlugin extends Plugin {

	private BalancerConfig config;

	@Override
	public void onLoad() {
		config = new BalancerConfig(this);
		config.load();
	}

	@Override
	public void onEnable() {
		getProxy().getPluginManager().registerListener(this, new BalancerListener(this));
		getProxy().getPluginManager().registerCommand(this, new BalancerCommand(this));
	}

	@Override
	public void onDisable() {
		getConfig().getServers().clear();
	}

	public @NotNull BalancerConfig getConfig() {
		return config;
	}

}