package net.rampo.duelstrial;

import lombok.extern.java.Log;
import net.rampo.duelstrial.duel.Kit;
import net.rampo.duelstrial.persistence.database.DBUtils;
import net.rampo.duelstrial.persistence.database.PlayerData;
import net.rampo.duelstrial.persistence.file.KitsLoader;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

@Log
public final class DuelsTrial extends JavaPlugin {

    public static DuelsTrial plugin;

    public static String prefix = "[DuelsTrial]";

    public static HashMap<UUID, PlayerData> playerData = new HashMap<>();

    public static HashMap<String, Kit> kits = new HashMap<>();

    public static String defaultKitName;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        log.info(prefix + " DuelsTrial is enabling!");

        log.info(prefix + " Loading kits...");
        boolean result = KitsLoader.loadKits();
        if (!result) {
            log.severe(prefix + " Error loading kits!");
            return;
        }
        log.info(prefix + " Kits loaded!");

        log.info(prefix + " Connecting to database...");
        result = DBUtils.connect();
        if (!result) {
            log.severe(prefix + " Error connecting to database!");
            return;
        }
        log.info(prefix + " Connected to database!");

    }
}
