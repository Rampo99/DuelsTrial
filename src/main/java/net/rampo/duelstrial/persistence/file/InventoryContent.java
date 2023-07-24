package net.rampo.duelstrial.persistence.file;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public record InventoryContent(
        @JsonProperty("material") String material,
        @JsonProperty("amount") int amount,
        @JsonProperty("slot") int slot
) {

    public ItemStack itemStack() {
        try {
            return new ItemStack(Material.valueOf(material), amount);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
