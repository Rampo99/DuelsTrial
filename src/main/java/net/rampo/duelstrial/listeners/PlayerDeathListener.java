package net.rampo.duelstrial.listeners;

import net.rampo.duelstrial.DuelsTrial;
import net.rampo.duelstrial.duel.Duel;
import net.rampo.duelstrial.duel.DuelManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.UUID;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        UUID uuid = player.getUniqueId();
        if(player.getKiller() == null) return;
        Player killer = player.getKiller();
        Duel duel = DuelManager.getDuel(uuid);
        if(duel == null) return;
        event.setKeepInventory(true);
        Runnable runnable = () -> {
            player.spigot().respawn();
            duel.end(killer.getUniqueId(), uuid);
        };

        DuelsTrial.plugin.getServer().getScheduler().scheduleSyncDelayedTask(DuelsTrial.plugin, runnable, 20);

    }
}
