package net.rampo.duelstrial.listeners;

import net.rampo.duelstrial.DuelsTrial;
import net.rampo.duelstrial.persistence.database.DBUtils;
import net.rampo.duelstrial.persistence.database.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        PlayerData playerData = DuelsTrial.playerData.remove(player.getUniqueId());
        if(playerData != null){
            DBUtils.updatePlayer(playerData);
        }
    }
}
