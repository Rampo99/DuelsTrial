package net.rampo.duelstrial.duel;

import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.rampo.duelstrial.DuelsTrial;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class Duel {

    private UUID player1;
    private UUID player2;
    private Kit kit;
    private States state;

    private Location oldPlayer1Location;
    private Location oldPlayer2Location;


    public Duel(UUID player1, UUID player2, Kit kit) {
        this.player1 = player1;
        this.player2 = player2;
        this.kit = kit == null ? DuelsTrial.kits.get(DuelsTrial.defaultKitName) : kit;
        this.state = States.INVITED;
    }

    public void start(){
        this.state = States.STARTING;
        Player playerOne = DuelsTrial.plugin.getServer().getPlayer(this.player1);
        Player playerTwo = DuelsTrial.plugin.getServer().getPlayer(this.player2);
        if(playerOne == null || playerTwo == null){
            return;
        }
        this.oldPlayer1Location = playerOne.getLocation();
        this.oldPlayer2Location = playerTwo.getLocation();

        playerOne.teleport(DuelManager.player1Spawn);
        playerTwo.teleport(DuelManager.player2Spawn);

        playerOne.setInvulnerable(true);
        playerTwo.setInvulnerable(true);
        playerOne.sendMessage(Component.text("§aThe duel is starting!"));
        playerTwo.sendMessage(Component.text("§aThe duel is starting!"));

        playerOne.getInventory().clear();
        playerTwo.getInventory().clear();

        playerOne.getInventory().setArmorContents(null);
        playerTwo.getInventory().setArmorContents(null);

        addKit(playerOne);
        addKit(playerTwo);

        BukkitRunnable runnable = new BukkitRunnable() {
            int countdown = 5;
            @Override
            public void run() {
                Player playerOne = Bukkit.getPlayer(player1);
                Player playerTwo = Bukkit.getPlayer(player2);
                if (playerOne == null || playerTwo == null) {
                    this.cancel();
                    return;
                }
                if (countdown == 0) {
                    playerOne.sendMessage(Component.text("§aGO!"));
                    playerTwo.sendMessage(Component.text("§aGO!"));
                    playerOne.setInvulnerable(false);
                    playerTwo.setInvulnerable(false);
                    state = States.ONGOING;
                    this.cancel();
                } else {
                    playerOne.sendMessage(Component.text("§a" + countdown));
                    playerTwo.sendMessage(Component.text("§a" + countdown));
                    countdown--;
                }
            }
        };
        runnable.runTaskTimer(DuelsTrial.plugin, 0, 20);
    }

    public void end(UUID winner, UUID loser){
        this.state = States.FINISHED;
        DuelsTrial.playerData.get(winner).addWin();
        DuelsTrial.playerData.get(loser).addLoss();
        Player player1 = DuelsTrial.plugin.getServer().getPlayer(this.player1);
        Player player2 = DuelsTrial.plugin.getServer().getPlayer(this.player2);
        if(player1 != null){
            if(this.player1.equals(winner)) player1.sendMessage(Component.text("§aYou won the duel!"));
            else player1.sendMessage(Component.text("§cYou lost the duel!"));
            clearInventory(player1);
            player1.teleport(oldPlayer1Location);
        }

        if(player2 != null){
            if(this.player2.equals(winner)) player2.sendMessage(Component.text("§aYou won the duel!"));
            else player2.sendMessage(Component.text("§cYou lost the duel!"));
            clearInventory(player2);
            player2.teleport(oldPlayer2Location);
        }
        DuelManager.duelsQueue.poll();
        DuelManager.startDuel();
    }

    public void cancel(UUID leaver){
        this.state = States.FINISHED;
        UUID otherPlayer = player1.equals(leaver) ? player2 : player1;
        DuelsTrial.playerData.get(otherPlayer).addWin();
        DuelsTrial.playerData.get(leaver).addLoss();
        Player player = DuelsTrial.plugin.getServer().getPlayer(otherPlayer);
        if(player != null){
            player.sendMessage(Component.text("§cYour opponent left the duel!"));
            player.teleport(oldPlayer1Location);
        }
        DuelManager.duelsQueue.poll();
        DuelManager.startDuel();
    }

    private void addKit(Player player){
        if(this.kit == null){
            player.sendMessage(Component.text("§cThis duel has no kit!"));
            return;
        }
        for(Integer key : this.kit.getInventoryContent().keySet()){
            player.getInventory().setItem(key, this.kit.getInventoryContent().get(key));
        }
        player.getInventory().setBoots(this.kit.getBoots());
        player.getInventory().setLeggings(this.kit.getLeggings());
        player.getInventory().setChestplate(this.kit.getChestPlate());
        player.getInventory().setHelmet(this.kit.getHelmet());
    }

    private void clearInventory(Player player){
        player.getInventory().clear();
        player.getInventory().setBoots(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setHelmet(null);
    }
}
