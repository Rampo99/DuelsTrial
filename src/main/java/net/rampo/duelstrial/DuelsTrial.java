package net.rampo.duelstrial;

import net.rampo.duelstrial.commands.AcceptCommand;
import net.rampo.duelstrial.commands.DuelCommand;
import net.rampo.duelstrial.commands.StatsCommand;
import net.rampo.duelstrial.duel.Kit;
import net.rampo.duelstrial.listeners.PlayerDeathListener;
import net.rampo.duelstrial.listeners.PlayerJoinListener;
import net.rampo.duelstrial.listeners.PlayerQuitListener;
import net.rampo.duelstrial.persistence.database.DBUtils;
import net.rampo.duelstrial.persistence.database.PlayerData;
import net.rampo.duelstrial.persistence.file.arena.ArenaLoader;
import net.rampo.duelstrial.persistence.file.kit.KitsLoader;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

public final class DuelsTrial extends JavaPlugin {

    public static DuelsTrial plugin;

    public static String prefix = "[DuelsTrial]";

    public static HashMap<UUID, PlayerData> playerData = new HashMap<>();

    public static HashMap<String, Kit> kits = new HashMap<>();

    public static String defaultKitName;

    public static Logger log;
    @Override
    public void onEnable() {
        log = getLogger();
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

        log.info(prefix + " Loading arena...");
        result = ArenaLoader.loadArena();
        if (!result) {
            log.severe(prefix + " Error loading arena!");
            return;
        }
        log.info(prefix + " Arena loaded!");

        log.info(prefix + " Connecting to database...");
        result = DBUtils.connect();
        if (!result) {
            log.severe(prefix + " Error connecting to database!");
            return;
        }
        log.info(prefix + " Connected to database!");

        log.info(prefix + " Registering listeners...");
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        log.info(prefix + " Listeners registered!");

        log.info(prefix + " Registering commands...");
        Objects.requireNonNull(getCommand("duel")).setExecutor(new DuelCommand());
        Objects.requireNonNull(getCommand("accept")).setExecutor(new AcceptCommand());
        Objects.requireNonNull(getCommand("stats")).setExecutor(new StatsCommand());
        log.info(prefix + " Commands registered!");

        log.info(prefix + " DuelsTrial is enabled!");
    }
}
