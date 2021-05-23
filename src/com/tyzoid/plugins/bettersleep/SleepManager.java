package com.tyzoid.plugins.bettersleep;

import java.util.List;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedEnterEvent.BedEnterResult;
import org.bukkit.plugin.Plugin;

public class SleepManager implements Listener {
	private Plugin plugin;

	public SleepManager(Plugin mainPlugin) {
		this.plugin = mainPlugin;
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerSleep(PlayerBedEnterEvent evt) {
		if (! evt.getBedEnterResult().equals(BedEnterResult.OK))
			return;
		
		int playersSleeping = 1;
		
		// Find all player currently sleeping in the current world
		Player sleeper = evt.getPlayer();
		World world = sleeper.getWorld();
		List<Player> players = world.getPlayers();
		for (Player p : players) {
			if (p.equals(sleeper))
				continue;
			
			if (p.isSleeping())
				playersSleeping++;
		}
		
		// Initial ratio will be 50% of world players will be required to sleep
		int playersSleepingRequired = (int) Math.ceil(0.5 * players.size());
		
		if (playersSleeping >= playersSleepingRequired) {
			this.plugin.getLogger().info(playersSleeping + " / " + playersSleepingRequired + " players are sleeping."
				+ " Advancing time to day on world " + world.getName() + ".");

			world.setTime(1000);
		} else {
			this.plugin.getLogger().info(playersSleeping + " / " + playersSleepingRequired + " players are sleeping."
				+ " Waiting for additional players in " + world.getName() + ".");
		}
	}
}
