/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.celestiusmc.plasmamachinegun;

import java.util.HashSet;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;
import sun.rmi.server.Util;

/**
 * Plasma Machine Gun Listener.
 */
public class PMGListener implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPermission("plasmamachinegun.use")) {
            return;
        }

        ItemStack item = event.getItem();

        if (!item.getType().equals(Material.BLAZE_ROD)) {
            return;
        }

        double speed = 20.0;
        Location location = player.getEyeLocation();
        Vector direction = location.getDirection().normalize();

        Fireball fb = player.launchProjectile(Fireball.class);
        fb.teleport(location.add(direction));
        fb.setYield(0f);
        fb.setIsIncendiary(false);

        fb.setDirection(direction);
        fb.setVelocity(direction.multiply(speed));

        //Get potential targets
        Set<LivingEntity> entities = new HashSet<LivingEntity>();
        for (Entity e : player.getNearbyEntities(100.0, 100.0, 100.0)) {
            if (e instanceof LivingEntity) {
                entities.add((LivingEntity) e);
            }
        }

        //Now get the target
        BlockIterator bitr = new BlockIterator(location, 0.0, 100);
        Block b;
        int bx, by, bz;
        Set<LivingEntity> targets = new HashSet<LivingEntity>();
        while (bitr.hasNext()) {
            b = bitr.next();
            bx = b.getX();
            by = b.getY();
            bz = b.getZ();

            if (!isTransparent(b.getType())) {
                break;
            }

            for (LivingEntity e : entities) {
                Location l = e.getLocation();
                int ex = l.getBlockX();
                int ey = l.getBlockY();
                int ez = l.getBlockZ();

                if (Math.abs(bx - ex) < 1
                        && Math.abs(by - ey) < 1
                        && Math.abs(bz - ez) < 1) {
                    targets.add(e);
                    entities.remove(e);
                }
            }
        }
        
        for (LivingEntity target : targets) {
            target.damage(1);
        }
    }

    private boolean isTransparent(Material mat) {
        return (mat.equals(Material.AIR));
    }

}
