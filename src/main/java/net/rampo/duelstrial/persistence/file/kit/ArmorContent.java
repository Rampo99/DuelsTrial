package net.rampo.duelstrial.persistence.file.kit;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public record ArmorContent(
        @JsonProperty("helmet") Content helmet,
        @JsonProperty("chestplate") Content chestplate,
        @JsonProperty("leggings") Content leggings,
        @JsonProperty("boots") Content boots
) {
}

record Content(
        @JsonProperty("material") String material,
        @JsonProperty("amount") int amount
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
