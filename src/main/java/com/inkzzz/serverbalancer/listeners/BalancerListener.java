package com.inkzzz.serverbalancer.listeners;

import com.inkzzz.serverbalancer.ServerBalancerPlugin;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Created by Luke Denham on 12/03/2016.
 */
public class BalancerListener implements Listener {

    private final ServerBalancerPlugin plugin;

    public BalancerListener(final ServerBalancerPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public final void onConnect(final ServerConnectEvent event) {
        final ProxiedPlayer player = event.getPlayer();
        final ServerInfo target = event.getTarget();

        if(player.hasPermission("serverbalancer.bypass")) {
            return;
        }

        if(target != null && isLobby(target)) {

            final ServerInfo new_target = getLeastActive();

            if(new_target == null) {
                return;
            }

            event.setTarget(new_target);
        }
    }

    private ServerInfo getLeastActive() {

        ServerInfo info = null;

        for(String server : this.plugin.getServers()) {
            final ServerInfo serverInfo = this.plugin.getProxy().getServerInfo(server);
            if(serverInfo == null) {
                continue;
            }
            if(info == null) {
                info = serverInfo;
            }
            else if(info.getPlayers().size() > serverInfo.getPlayers().size()) {
                info = serverInfo;
            }
        }

        return info;
    }

    private boolean isLobby(ServerInfo info) {
        for(String server : this.plugin.getServers()) {
            if(server.equalsIgnoreCase(info.getName())) {
                return true;
            }
        }
        return false;
    }

}
