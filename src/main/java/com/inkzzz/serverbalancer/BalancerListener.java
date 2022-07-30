package com.inkzzz.serverbalancer;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Luke Denham on 12/03/2016.
 */
public final class BalancerListener implements Listener {

	private final ServerBalancerPlugin plugin;

	public BalancerListener(final @NotNull ServerBalancerPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onConnect(final ServerConnectEvent event) {
		final ProxiedPlayer player = event.getPlayer();
		if (player.hasPermission("serverbalancer.bypass")) return;

		final ServerInfo target = event.getTarget();
		if (isLobby(target)) {
			final ServerInfo newTarget = getLeastActive();
			if (newTarget == null) return;

			event.setTarget(newTarget);
		}
	}

	private @Nullable ServerInfo getLeastActive() {
		ServerInfo info = null;
		for (final String server : plugin.getConfig().getServers()) {
			final ServerInfo serverInfo = plugin.getProxy().getServerInfo(server);
			if (serverInfo == null) continue;

			if (info == null || info.getPlayers().size() > serverInfo.getPlayers().size()) info = serverInfo;
		}
		return info;
	}

	private boolean isLobby(ServerInfo info) {
		for (final String server : plugin.getConfig().getServers()) {
			if (server.equalsIgnoreCase(info.getName())) return true;
		}
		return false;
	}

}