package net.rampo.duelstrial.listeners;

import net.rampo.duelstrial.DuelsTrial;
import net.rampo.duelstrial.persistence.database.DBUtils;
import net.rampo.duelstrial.persistence.database.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        PlayerData playerData = DBUtils.getPlayer(player.getUniqueId());
        if (playerData == null) {
            playerData = PlayerData.builder()
                    .uuid(player.getUniqueId())
                    .wins(0)
                    .losses(0)
                    .kills(0)
                    .deaths(0)
                    .winStreak(0)
                    .currentWinStreak(0)
                    .build();
            DBUtils.savePlayer(playerData);
        }
        DuelsTrial.playerData.put(player.getUniqueId(), playerData);
    }

}
