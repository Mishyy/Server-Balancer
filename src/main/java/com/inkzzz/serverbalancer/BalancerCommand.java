package com.inkzzz.serverbalancer;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Luke Denham on 12/03/2016.
 */
public final class BalancerCommand extends Command {

	private final ServerBalancerPlugin plugin;

	public BalancerCommand(final ServerBalancerPlugin plugin) {
		super("balancer", "serverbalancer.command.balancer");
		this.plugin = plugin;
	}

	@Override
	public void execute(final @NotNull CommandSender sender, final @NotNull String[] args) {
		if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
			plugin.getConfig().load();
			sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getReloadMessage())));
		}
	}

}