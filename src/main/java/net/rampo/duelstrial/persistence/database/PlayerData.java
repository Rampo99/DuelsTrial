package net.rampo.duelstrial.persistence.database;

import lombok.Builder;
import lombok.Data;
import net.rampo.duelstrial.DuelsTrial;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@Data
@Builder
public class PlayerData {
    private UUID uuid;
    private int wins;
    private int losses;
    private int kills;
    private int deaths;
    private int winStreak;
    private int currentWinStreak;

    public void addWinStreak() {
        this.currentWinStreak++;
        if (this.currentWinStreak > this.winStreak) {
            this.winStreak = this.currentWinStreak;
        }
    }

    public void resetWinStreak() {
        this.currentWinStreak = 0;
    }

    public static PlayerData getPlayer(String name) {
        Player player = Bukkit.getPlayer(name);
        if (player != null) {
            return DuelsTrial.playerData.get(player.getUniqueId());
        }
        return null;
    }

}
