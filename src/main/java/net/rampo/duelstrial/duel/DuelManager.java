package net.rampo.duelstrial.duel;

import net.rampo.duelstrial.DuelsTrial;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Queue;
import java.util.UUID;

public class DuelManager {

    private static HashMap<UUID,Duel> invites = new HashMap<>();
    private static HashMap<UUID, BukkitTask> invitesCountdown = new HashMap<>();

    private static Queue<Duel> duelsQueue;

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

    public static Duel acceptInvite(UUID player){

        Duel duel = invites.get(player);
        if(duel != null){
            if(duel.getState().equals(States.INVITED)){
                duel.setState(States.ACCEPTED);
                invitesCountdown.get(player).cancel();
                invitesCountdown.remove(player);
                return invites.remove(player);
            }

        }
        return null;
    }

}
