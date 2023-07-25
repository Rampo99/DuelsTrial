package net.rampo.duelstrial.persistence.file.arena;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.rampo.duelstrial.duel.DuelManager;
import net.rampo.duelstrial.persistence.file.kit.KitsConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.File;

public class ArenaLoader {

    public static boolean loadArena() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ArenaConfig arenaConfig = objectMapper.readValue(new File("plugins/DuelsTrial/arena.json"), ArenaConfig.class);
            String worldString = arenaConfig.world();
            World world = Bukkit.getWorld(worldString);
            if (world == null) {
                return false;
            }
            Spawn playerOneSpawn = arenaConfig.playerOneSpawn();
            Spawn playerTwoSpawn = arenaConfig.playerTwoSpawn();
            DuelManager.player1Spawn = new Location(world, playerOneSpawn.x(), playerOneSpawn.y(), playerOneSpawn.z());
            DuelManager.player2Spawn = new Location(world, playerTwoSpawn.x(), playerTwoSpawn.y(), playerTwoSpawn.z());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
