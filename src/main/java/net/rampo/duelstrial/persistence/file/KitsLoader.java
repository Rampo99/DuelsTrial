package net.rampo.duelstrial.persistence.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.rampo.duelstrial.DuelsTrial;
import net.rampo.duelstrial.duel.Kit;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class KitsLoader {

    public static boolean loadKits() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            KitsConfig kitsConfig = objectMapper.readValue(new File("plugins/DuelsTrial/kits.json"), KitsConfig.class);
            DuelsTrial.defaultKitName = kitsConfig.defaultKit();
            for (KitContent kitContent : kitsConfig.kits()) {
                String kitName = kitContent.name();
                ArmorContent armorContent = kitContent.armorContent();
                List<InventoryContent> inventoryContent = kitContent.inventoryContent();

                HashMap<Integer, ItemStack> inventoryContentMap = new HashMap<>();
                for (InventoryContent inventoryContentItem : inventoryContent) {
                    inventoryContentMap.put(inventoryContentItem.slot(), inventoryContentItem.itemStack());
                }

                DuelsTrial.kits.put(
                        kitName,
                        Kit.builder()
                        .helmet(armorContent.helmet().itemStack())
                        .chestPlate(armorContent.chestplate().itemStack())
                        .leggings(armorContent.leggings().itemStack())
                        .boots(armorContent.boots().itemStack())
                        .inventoryContent(inventoryContentMap)
                        .build()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
