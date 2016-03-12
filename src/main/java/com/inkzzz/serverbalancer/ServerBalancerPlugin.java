package com.inkzzz.serverbalancer;

import com.inkzzz.serverbalancer.commands.BalancerCommand;
import com.inkzzz.serverbalancer.listeners.BalancerListener;
import com.inkzzz.serverbalancer.utils.FileUtil;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Luke Denham on 12/03/2016.
 */
public final class ServerBalancerPlugin extends Plugin {

    private final List<String> SERVERS = new ArrayList<>();
    private String RELOAD_MESSAGE = null;

    @Override
    public void onEnable() {

        if(!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        addDefaults();

        getProxy().getPluginManager().registerListener(this, new BalancerListener(this));
        getProxy().getPluginManager().registerCommand(this, new BalancerCommand(this));

    }

    private void addDefaults() {

        final Configuration configuration = FileUtil.getFile("config.yml");

        if(configuration == null) {
            return;
        }

        boolean save = false;

        if(configuration.getStringList("Servers").isEmpty()) {
            configuration.set("Servers", Arrays.asList("hub1", "hub2"));
            save = true;
        }
        if(configuration.getString("Reload-Message").isEmpty()) {
            configuration.set("Reload-Message", "&aYou have reloaded the configuration file!");
            save = true;
        }

        if(save) {
            FileUtil.save(configuration, "config.yml");
        }

        load(configuration);
    }

    private void load(Configuration configuration) {
        this.getServers().clear();
        for(String server : configuration.getStringList("Servers")) {
            if(getProxy().getServerInfo(server) != null) {
                getServers().add(server);
            }
        }
        this.RELOAD_MESSAGE = configuration.getString("Reload-Message");
    }

    public void reload() {
        addDefaults();
    }

    public List<String> getServers() {
        return this.SERVERS;
    }

    public String getReloadMessage() {
        return this.RELOAD_MESSAGE;
    }

}
