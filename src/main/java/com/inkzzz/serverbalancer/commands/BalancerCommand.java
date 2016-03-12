package com.inkzzz.serverbalancer.commands;

import com.inkzzz.serverbalancer.ServerBalancerPlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.plugin.Command;

/**
 * Created by Luke Denham on 12/03/2016.
 */
public class BalancerCommand extends Command {

    private final ServerBalancerPlugin plugin;

    public BalancerCommand(final ServerBalancerPlugin plugin) {
        super("balancer", "serverbalancer.command.balancer");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender s, String[] args) {
        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("reload")) {
                this.plugin.reload();
                s.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', this.plugin.getReloadMessage())));
            }
        }
    }

}
