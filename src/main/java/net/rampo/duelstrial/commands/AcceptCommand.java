package net.rampo.duelstrial.commands;

import net.kyori.adventure.text.Component;
import net.rampo.duelstrial.duel.Duel;
import net.rampo.duelstrial.duel.DuelManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AcceptCommand implements CommandExecutor {
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
            Duel duel = DuelManager.acceptInvite(target.getUniqueId());
            if(duel == null){
                player.sendMessage(Component.text("You have no pending invites from " + args[0]));
                return false;
            }
            duel.start();
        }


        player.sendMessage(Component.text("/accept <name>"));
        return false;
    }
}
