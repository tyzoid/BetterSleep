package com.tyzoid.plugins.bettersleep;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class BetterSleep extends JavaPlugin {
	private SleepManager sm;
	
	public BetterSleep() {
		this.sm = new SleepManager(this);
	}
	
	@Override
	public void onEnable() {
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(sm, this);
	}
}