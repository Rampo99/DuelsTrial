package net.rampo.duelstrial.listeners;

import net.rampo.duelstrial.DuelsTrial;
import net.rampo.duelstrial.duel.Duel;
import net.rampo.duelstrial.duel.DuelManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.UUID;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onDeath(EntityDamageByEntityEvent event){

        if(!(event.getEntity() instanceof Player player)) return;
        if(!(event.getDamager() instanceof Player killer)) return;

        if(player.getHealth() - event.getFinalDamage() > 0) return;
        UUID uuid = player.getUniqueId();

        Duel duel = DuelManager.getDuel(uuid);
        if(duel == null) return;
        event.setCancelled(true);
        duel.end(killer.getUniqueId(), uuid);

    }
}
