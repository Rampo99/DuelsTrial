package net.rampo.duelstrial.commands;

import net.kyori.adventure.text.Component;
import net.rampo.duelstrial.DuelsTrial;
import net.rampo.duelstrial.duel.DuelManager;
import net.rampo.duelstrial.duel.Kit;
import net.rampo.duelstrial.persistence.database.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DuelCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if(!(commandSender instanceof Player player)){
            commandSender.sendMessage(Component.text("Only players can use this command"));
            return false;
        }

        if(args.length == 1){
            Player target = player.getServer().getPlayer(args[0]);
            if(target == null){
                player.sendMessage(Component.text("Player not found"));
                return false;
            }
            DuelManager.addInvite(player.getUniqueId(), target.getUniqueId(),null);
            player.sendMessage(Component.text("You have challenged " + args[0] + " to a duel with kit " + DuelsTrial.defaultKitName));
            target.sendMessage(Component.text(player.getName() + " has challenged you to a duel with kit " + DuelsTrial.defaultKitName));
            return true;
        }

        if(args.length == 2){
            Player target = player.getServer().getPlayer(args[0]);
            if(target == null){
                player.sendMessage(Component.text("Player not found"));
                return false;
            }
            Kit kit = DuelsTrial.kits.get(args[1]);
            if(kit == null){
                player.sendMessage(Component.text("Kit not found"));
                return false;
            }
            DuelManager.addInvite(player.getUniqueId(), target.getUniqueId(), kit);
            player.sendMessage(Component.text("You have challenged " + args[0] + " to a duel with kit " + args[1]));
            target.sendMessage(Component.text(player.getName() + " has challenged you to a duel with kit " + args[1]));
            return true;
        }

        player.sendMessage(Component.text("/duel <player> [kit]"));
        return false;
    }
}
