package net.rampo.duelstrial.duel;

import net.kyori.adventure.text.Component;
import net.rampo.duelstrial.DuelsTrial;
import net.rampo.duelstrial.persistence.database.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class DuelManager {

    private static HashMap<UUID,Duel> invites = new HashMap<>();
    private static HashMap<UUID, BukkitTask> invitesCountdown = new HashMap<>();

    public static Queue<Duel> duelsQueue = new LinkedList<>();

    public static Location player1Spawn;
    public static Location player2Spawn;

    public static void addInvite(UUID player1, UUID player2, Kit kit){
        Duel duel = new Duel(player1, player2, kit);
        invites.put(player1, duel);
        Runnable runnable = () -> {
            invites.remove(player1);
            invitesCountdown.remove(player1);
        };
        BukkitTask task = Bukkit.getScheduler().runTaskLaterAsynchronously(DuelsTrial.plugin, runnable,60*20);
        invitesCountdown.put(player1, task);
    }

    public static boolean acceptInvite(UUID player){

        Duel duel = invites.remove(player);
        if(duel == null) {
            return false;
        }
        if(!duel.getState().equals(States.INVITED)){
            return false;
        }

        duel.setState(States.ACCEPTED);
        invitesCountdown.get(player).cancel();
        invitesCountdown.remove(player);
        duelsQueue.add(duel);
        return true;
    }

    public static void removePlayer(Player leaver){
        //cancel invite
        Duel duel = invites.remove(leaver.getUniqueId());
        BukkitTask task = invitesCountdown.remove(leaver.getUniqueId());
        if(task != null){
            task.cancel();
        }
        if(duel != null) {
            UUID otherPlayer = duel.getPlayer1().equals(leaver.getUniqueId()) ? duel.getPlayer2() : duel.getPlayer1();
            Player player = Bukkit.getPlayer(otherPlayer);
            if (player != null) {
                player.sendMessage(Component.text("§c" + leaver.getName() + " §7has left the server, the invite has been cancelled."));
            }
        }
        //remove from queue
        duel = duelsQueue.stream().filter(duel1 -> duel1.getPlayer1().equals(leaver.getUniqueId()) || duel1.getPlayer2().equals(leaver.getUniqueId())).findFirst().orElse(null);
        if(duel != null){
            duelsQueue.remove(duel);
            if (duel.getState().equals(States.ACCEPTED)) {
                UUID otherPlayer = duel.getPlayer1().equals(leaver.getUniqueId()) ? duel.getPlayer2() : duel.getPlayer1();
                Player player = Bukkit.getPlayer(otherPlayer);
                if (player != null) {
                    player.sendMessage(Component.text("§c" + leaver.getName() + " §7has left the server, you have been removed from the queue."));
                }
            } else {
                duel.cancel(leaver.getUniqueId());
            }
        }
    }

    public static void startDuel(){
        Duel duel = duelsQueue.peek();
        if(duel == null){
            return;
        }
        duel.start();

    }

    public static Duel getDuel(UUID uuid){
        return duelsQueue.stream().filter(duel -> duel.getPlayer1().equals(uuid) || duel.getPlayer2().equals(uuid)).findFirst().orElse(null);
    }

    public static boolean isInDuel(UUID uuid){
        return duelsQueue.stream().anyMatch(duel -> duel.getPlayer1().equals(uuid) || duel.getPlayer2().equals(uuid));
    }

    public static boolean isInvited(UUID inviter, UUID invited){
        Duel duel = invites.get(inviter);
        if(duel == null){
            return false;
        }
        return duel.getPlayer1().equals(inviter) && duel.getPlayer2().equals(invited);
    }
}
