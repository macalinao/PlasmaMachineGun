/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.celestiusmc.plasmamachinegun;

import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * PlasmaMachineGun main class.
 */
public class PlasmaMachineGun extends JavaPlugin {

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "Plugin disabled.");
    }

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new PMGListener(), this);
        getLogger().log(Level.INFO, "Plugin enabled.");
    }
    
}
