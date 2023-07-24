package net.rampo.duelstrial.commands;

import net.kyori.adventure.text.Component;
import net.rampo.duelstrial.DuelsTrial;
import net.rampo.duelstrial.persistence.database.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StatsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player player)){
            commandSender.sendMessage(Component.text("Only players can use this command"));
            return false;
        }

        if(strings.length == 0){
            PlayerData playerData = PlayerData.getPlayer(player.getName());
            if(playerData == null){
                player.sendMessage(Component.text("Player not found"));
                return false;
            }
            String message = getStatsMessage(player.getName(), playerData);
            player.sendMessage(Component.text(message));
            return true;
        }

        if(strings.length == 1){
            String playerName = strings[0];
            PlayerData playerData = PlayerData.getPlayer(playerName);
            if(playerData == null){
                player.sendMessage(Component.text("Player not found"));
                return false;
            }
            String message = getStatsMessage(playerName, playerData);
            player.sendMessage(Component.text(message));
            return true;
        }

        player.sendMessage(Component.text("/stats [player]"));
        return false;
    }

    private String getStatsMessage(String playerName, PlayerData playerData){
        return "§6§l" + playerName + "'s stats\n" +
                "§6Wins: §e" + playerData.getWins() + "\n" +
                "§6Losses: §e" + playerData.getLosses() + "\n" +
                "§6Kills: §e" + playerData.getKills() + "\n" +
                "§6Deaths: §e" + playerData.getDeaths() + "\n" +
                "§6Win Streak: §e" + playerData.getWinStreak() + "\n" +
                "§6Current Win Streak: §e" + playerData.getCurrentWinStreak();
    }
}
